package com.github.berkleytechnologyservices.restdocs.example.book;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

public class BookResource extends EntityModel<Book> {

  public BookResource(Book content, Link... links) {
    super(content, links);
  }

}
