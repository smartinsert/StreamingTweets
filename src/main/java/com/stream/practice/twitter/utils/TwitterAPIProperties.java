package com.stream.practice.twitter.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.Getter;

@Service
public class TwitterAPIProperties {
  
  @Getter
  @Value("${access.token}")
  private String accessToken;
  
  @Getter
  @Value("${access.token.secret}")
  private String accessTokenSecret;
  
  @Getter
  @Value("${consumer.api.key}")
  private String consumerApiKey;
  
  @Getter
  @Value("${consumer.api.secret}")
  private String consumerApiSecret;
  
}
