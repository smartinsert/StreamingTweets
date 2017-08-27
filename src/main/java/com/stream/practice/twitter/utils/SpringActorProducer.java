package com.stream.practice.twitter.utils;

import akka.actor.Actor;
import akka.actor.IndirectActorProducer;
import org.springframework.context.ApplicationContext;

/**
 * An actor producer that lets Spring create the Actor instances.
 */
public class SpringActorProducer implements IndirectActorProducer {
  final ApplicationContext applicationContext;
  final String actorBeanName;
  private Object[] args;

  public SpringActorProducer(ApplicationContext applicationContext, String actorBeanName, Object... args) {
    this.applicationContext = applicationContext;
    this.actorBeanName = actorBeanName;
    this.args = args;
  }

  @Override
  public Actor produce() {
    return (Actor) applicationContext.getBean(actorBeanName, args);
  }

  @SuppressWarnings("unchecked")
  @Override
  public Class<? extends Actor> actorClass() {
    return (Class<? extends Actor>) applicationContext.getType(actorBeanName);
  }
}