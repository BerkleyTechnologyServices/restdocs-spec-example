package com.github.berkleytechnologyservices.restdocs.example.book;

import com.github.berkleytechnologyservices.restdocs.example.author.AuthorRestController;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class BookResourceAssembler extends ResourceAssemblerSupport<Book, BookResource> {

  public BookResourceAssembler() {
    super(BookRestController.class, BookResource.class);
  }

  @Override
  public BookResource toResource(Book book) {
    BookResource resource = new BookResource(book);
    resource.add(linkTo(methodOn(BookRestController.class).getBook(book.getId())).withSelfRel());
    resource.add(linkTo(methodOn(AuthorRestController.class).getAuthorById(book.getAuthor().getId())).withRel("author"));
    return resource;
  }

}
