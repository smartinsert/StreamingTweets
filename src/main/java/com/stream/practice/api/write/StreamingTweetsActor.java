package com.stream.practice.api.write;


import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.stream.practice.domain.Tweet;

import akka.actor.UntypedAbstractActor;

@Service("StreamingTweetsActor")
@Scope("prototype")
public class StreamingTweetsActor extends UntypedAbstractActor {

  @Override
  public void onReceive(Object message) throws Throwable {
    if (message instanceof Tweet) {
      Tweet tweet = (Tweet) message;
      System.out.println("Received tweet " + tweet);
    }
  }
  
}
