package com.github.berkleytechnologyservices.restdocs.example.author;

import com.github.berkleytechnologyservices.restdocs.example.book.BookRestController;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AuthorResourceAssembler extends RepresentationModelAssemblerSupport<Author, AuthorResource> {

  public AuthorResourceAssembler() {
    super(AuthorRestController.class, AuthorResource.class);
  }

  @Override
  public AuthorResource toModel(Author author) {
    AuthorResource resource = new AuthorResource(author);
    resource.add(linkTo(methodOn(AuthorRestController.class).getAuthorById(author.getId())).withSelfRel());
    resource.add(linkTo(methodOn(BookRestController.class).getBooksByAuthor(author.getId())).withRel("books"));
    return resource;
  }

}
