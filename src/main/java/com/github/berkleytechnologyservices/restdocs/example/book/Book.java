package com.github.berkleytechnologyservices.restdocs.example.book;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.berkleytechnologyservices.restdocs.example.author.Author;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Value
@Entity
@EqualsAndHashCode(of = "id")
@RequiredArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class Book {

  @Id
  @GeneratedValue
  @JsonIgnore
  Long id;

  @Column(nullable = false)
  String title;

  @Column(nullable = false)
  Integer published;

  @ManyToOne(optional = false)
  @JoinColumn(nullable = false)
  Author author;

}
