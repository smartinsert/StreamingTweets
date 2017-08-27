package com.stream.practice.api.write;


import org.springframework.stereotype.Component;

import com.stream.practice.domain.Tweet;

import akka.actor.ActorRef;
import akka.stream.impl.ActorPublisher;

@Component
public class StreamingTweetsActor extends ActorPublisher<Tweet> {
  
  public StreamingTweetsActor(ActorRef impl) {
    super(impl);
  }
  
}
