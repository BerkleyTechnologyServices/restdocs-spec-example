package com.github.berkleytechnologyservices.restdocs.example.author;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.berkleytechnologyservices.restdocs.example.book.Book;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
  private final Long id;

  @Column(nullable = false)
  private final String firstName;

  @Column(nullable = false)
  private final String lastName;

  @OneToMany(mappedBy = "author")
  @JsonIgnore
  private final Set<Book> books = new HashSet<>();
}
