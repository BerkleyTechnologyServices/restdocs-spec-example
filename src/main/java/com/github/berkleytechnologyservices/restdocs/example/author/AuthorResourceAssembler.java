package com.github.berkleytechnologyservices.restdocs.example.author;

import com.github.berkleytechnologyservices.restdocs.example.book.BookRestController;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class AuthorResourceAssembler extends ResourceAssemblerSupport<Author, AuthorResource> {

  public AuthorResourceAssembler() {
    super(AuthorRestController.class, AuthorResource.class);
  }

  @Override
  public AuthorResource toResource(Author author) {
    AuthorResource resource = new AuthorResource(author);
    resource.add(linkTo(methodOn(AuthorRestController.class).getAuthorById(author.getId())).withSelfRel());
    resource.add(linkTo(methodOn(BookRestController.class).getBooksByAuthor(author.getId())).withRel("books"));
    return resource;
  }

}
