package com.stream.practice.domain;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Service("HashTag")
public class HashTag {
  @Getter private final String name;
}
