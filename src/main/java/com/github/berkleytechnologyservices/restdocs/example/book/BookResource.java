package com.github.berkleytechnologyservices.restdocs.example.book;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

public class BookResource extends Resource<Book> {

  public BookResource(Book content, Link... links) {
    super(content, links);
  }

}
