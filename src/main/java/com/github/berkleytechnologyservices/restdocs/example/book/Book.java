package com.github.berkleytechnologyservices.restdocs.example.book;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.berkleytechnologyservices.restdocs.example.author.Author;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Value
@Entity
@EqualsAndHashCode(of = "id")
@RequiredArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class Book {

  @Id
  @GeneratedValue
  @JsonIgnore
  private final Long id;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private Integer year;

  @ManyToOne
  @JoinColumn(nullable = false)
  private Author author;

}
