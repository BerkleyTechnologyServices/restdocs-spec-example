package com.github.berkleytechnologyservices.restdocs.example.book;

import com.github.berkleytechnologyservices.restdocs.example.author.AuthorRestController;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class BookResourceAssembler extends RepresentationModelAssemblerSupport<Book, BookResource> {

  public BookResourceAssembler() {
    super(BookRestController.class, BookResource.class);
  }

  @Override
  public BookResource toModel(Book book) {
    BookResource resource = new BookResource(book);
    resource.add(linkTo(methodOn(BookRestController.class).getBook(book.getId())).withSelfRel());
    resource.add(linkTo(methodOn(AuthorRestController.class).getAuthorById(book.getAuthor().getId())).withRel("author"));
    return resource;
  }

}
