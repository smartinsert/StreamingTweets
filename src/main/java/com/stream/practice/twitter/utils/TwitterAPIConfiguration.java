package com.stream.practice.twitter.utils;

import static com.typesafe.config.ConfigFactory.load;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.typesafe.config.Config;

import akka.actor.ActorSystem;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

@Configuration
@ComponentScan(basePackages = "com.stream")
@PropertySource("classpath:environment.properties")
public class TwitterAPIConfiguration {
  
  @Autowired
  private TwitterAPIProperties twitterProperties;
  @Autowired
  private ApplicationContext applicationContext;
  
  @Bean
  public TwitterStreamFactory twitterStreamingFactory() {
    return new TwitterStreamFactory(configuration().build());
  }
  
  @Bean
  public TwitterStream twitterStream() throws IOException {
    return twitterStreamingFactory().getInstance();
  }

  @Bean
  public ConfigurationBuilder configuration() {
    ConfigurationBuilder configuration = new ConfigurationBuilder();
    configuration.setOAuthConsumerKey(twitterProperties.getConsumerApiKey());
    configuration.setOAuthConsumerSecret(twitterProperties.getConsumerApiSecret());
    configuration.setOAuthAccessToken(twitterProperties.getAccessToken());
    configuration.setOAuthAccessTokenSecret(twitterProperties.getAccessTokenSecret());
    return configuration;
  }

  @Bean
  public ActorSystem actorSystem() {
    Config conf = load("streaming-tweats-application.conf");
    ActorSystem system = ActorSystem.create("live-tweets", conf);
    SpringExtension.SpringExtProvider.get(system).initialize(applicationContext);
    return system;
  }
  
  @Bean
  public Materializer actorMaterializer() {
    return ActorMaterializer.create(actorSystem());
  }
}
