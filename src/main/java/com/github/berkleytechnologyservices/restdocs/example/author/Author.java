package com.github.berkleytechnologyservices.restdocs.example.author;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.berkleytechnologyservices.restdocs.example.book.Book;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Value
@Entity
@EqualsAndHashCode(of = "id")
@RequiredArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class Author {

  @Id
  @GeneratedValue
  @JsonIgnore
  Long id;

  @Column(nullable = false)
  String firstName;

  @Column(nullable = false)
  String lastName;

  @OneToMany(mappedBy = "author")
  @JsonIgnore
  Set<Book> books = new HashSet<>();
}
