package com.stream.practice.api.write;


import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.stream.practice.domain.Tweet;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;

@Service
@Scope("prototype")
public class StreamingTweetsActor extends AbstractActor {

  @Override
  public Receive createReceive() {
    return ReceiveBuilder.create()
        .match(Tweet.class, command -> {
          Tweet tweet = (Tweet) command;
          System.out.println("Received tweet " + tweet);
        }).build();
  }
  
  
}
