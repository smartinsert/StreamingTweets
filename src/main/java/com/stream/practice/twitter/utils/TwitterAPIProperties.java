package com.stream.practice.twitter.utils;

import java.io.IOException;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TwitterAPIProperties {
  
  @Autowired
  private Properties properties;
  
  public String consumerAPIKey() throws IOException {
    return property("consumer.api.key").toString();
  }
  
  public String consumerAPISecret() throws IOException {
    return property("consumer.api.secret").toString();
  }
  
  public String accessToken() throws IOException {
    return property("access.token").toString();
  }
  
  public String accessTokenSecret() throws IOException {
    return property("access.token.secret").toString();
  }
  
  private Object property(String key) throws IOException {
    String value = System.getProperty(key);
    if (value != null)
      return value;

    return properties.getProperty(key);
  }
}
