package com.stream.practice.domain;

import static java.util.Arrays.asList;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Service("Tweet")
public class Tweet {
  private final Author author;
  private final long timestamp;
  private final String body;
  
  public Set<HashTag> hashtags() {
    return asList(body.split(" "))
        .stream()
        .filter(tweet -> tweet.startsWith("#"))
        .map(tweet -> new HashTag(tweet))
        .collect(Collectors.toSet());
  }
  
  public static final HashTag AKKA = new HashTag("#akka");
  public static Tweet EmptyTweet() {
    return new Tweet(new Author(""), 0L, "");
  }
}