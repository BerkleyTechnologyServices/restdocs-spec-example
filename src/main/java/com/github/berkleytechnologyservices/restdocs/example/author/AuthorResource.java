package com.github.berkleytechnologyservices.restdocs.example.author;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

public class AuthorResource extends EntityModel<Author> {

  public AuthorResource(Author content, Link... links) {
    super(content, links);
  }

}
