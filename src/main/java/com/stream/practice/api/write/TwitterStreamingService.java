package com.stream.practice.api.write;

import static akka.stream.javadsl.Sink.actorRef;
import static com.stream.practice.twitter.utils.SpringExtension.SpringExtProvider;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import com.stream.practice.domain.Tweet;
import com.stream.practice.twitter.utils.TwitterAPIConfiguration;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.stream.Materializer;
import akka.stream.javadsl.Source;
import scala.runtime.BoxedUnit;

@Service
public class TwitterStreamingService {
  private static AnnotationConfigApplicationContext context;
  private ActorSystem system;
  private Materializer actorMaterializer;
  private TwitterStreamClient twitterStreamClient;
  private ActorRef streamingTweetActor;
  
  @Autowired
  public TwitterStreamingService(ActorSystem system, TwitterStreamClient twitterStreamClient, Materializer actorMaterializer) {
    super();
    this.system = system;
    this.twitterStreamClient = twitterStreamClient;
    this.actorMaterializer = actorMaterializer;
  }
  
  public static void main(String[] args) {
    context = new AnnotationConfigApplicationContext(TwitterAPIConfiguration.class);
    TwitterStreamingService service = context.getBean(TwitterStreamingService.class);
    service.start();
  }
  
  public void start() {
    createActors();
    Source<Tweet,BoxedUnit> sourceOfTweets = twitterStreamClient.listenAndStream(new ArrayList<String>(Arrays.asList("akka")));
    sourceOfTweets.runWith(actorRef(streamingTweetActor, Tweet.EmptyTweet()), actorMaterializer);
  }
  
  public void createActors() {
    streamingTweetActor = system.actorOf(SpringExtProvider.get(system).props("StreamingTweetsActor"), "streaming-tweets-actor");
  }
  
}
