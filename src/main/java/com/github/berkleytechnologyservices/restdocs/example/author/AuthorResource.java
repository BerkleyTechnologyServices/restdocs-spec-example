package com.github.berkleytechnologyservices.restdocs.example.author;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

public class AuthorResource extends Resource<Author> {

  public AuthorResource(Author content, Link... links) {
    super(content, links);
  }

}
