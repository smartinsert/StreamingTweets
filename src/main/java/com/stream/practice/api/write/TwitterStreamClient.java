package com.stream.practice.api.write;

import static akka.stream.javadsl.Source.fromPublisher;

import java.util.List;

import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.stream.practice.domain.Author;
import com.stream.practice.domain.Tweet;

import akka.actor.ActorRef;
import akka.japi.Pair;
import akka.stream.Materializer;
import akka.stream.OverflowStrategy;
import akka.stream.javadsl.Keep;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import scala.runtime.BoxedUnit;
import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;

@Component
public class TwitterStreamClient {
  private TwitterStream twitterStream;
  private Materializer actorMaterializer;

  @Autowired
  public TwitterStreamClient(TwitterStream twitterStream, Materializer actorMaterializer) {
    super();
    this.twitterStream = twitterStream;
    this.actorMaterializer = actorMaterializer;
  }

  public Source<Tweet,BoxedUnit> listenAndStream(List<String> searchQuery) {
      Pair<ActorRef, Publisher<Tweet>> tweetSinkWithPublisher = Source
                                                    .<Tweet>actorRef(1000, OverflowStrategy.fail())
                                                    .toMat(Sink.asPublisher(true), Keep.both())
                                                    .run(actorMaterializer);
    
      twitterStream.addListener(new StatusListener() {

      @Override
      public void onException(Exception ex) {

      }

      @Override
      public void onTrackLimitationNotice(int numberOfLimitedStatuses) {

      }

      @Override
      public void onStatus(Status status) {
        tweetSinkWithPublisher.first().tell(new Tweet(new Author(status.getUser().getScreenName()),
                status.getCreatedAt().getTime(), status.getText()), ActorRef.noSender());
      }

      @Override
      public void onStallWarning(StallWarning warning) {

      }

      @Override
      public void onScrubGeo(long userId, long upToStatusId) {

      }

      @Override
      public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {

      }

    });
      
      FilterQuery filterQuery = new FilterQuery();
      filterQuery.track(searchQuery.toArray(new String[0]));
      twitterStream.filter(filterQuery);
      
      return fromPublisher(tweetSinkWithPublisher.second());
  }

  public void stopStream() {
    twitterStream.cleanUp();
    twitterStream.shutdown();
  }
}
