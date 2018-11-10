package com.github.berkleytechnologyservices.restdocs.example.book;

import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/book", produces = "application/hal+json")
public class BookRestController {

  private final BookRepository repository;
  private final BookResourceAssembler resourceAssembler;

  public BookRestController(BookRepository repository,
                            BookResourceAssembler resourceAssembler) {
    this.repository = repository;
    this.resourceAssembler = resourceAssembler;
  }

  @GetMapping
  public Resources<BookResource> getAllBooks() {
    List<BookResource> bookResources = resourceAssembler.toResources(this.repository.findAll());
    Resources<BookResource> resources = new Resources<>(bookResources);
    resources.add(linkTo(methodOn(BookRestController.class).getAllBooks()).withSelfRel());
    return resources;
  }

  @GetMapping(path = "/search", params = "authorId")
  public Resources<BookResource> getBooksByAuthor(@RequestParam("authorId") Long authorId) {
    List<BookResource> bookResources = resourceAssembler.toResources(this.repository.findBooksByAuthorId(authorId));
    Resources<BookResource> resources = new Resources<>(bookResources);
    resources.add(linkTo(methodOn(BookRestController.class).getBooksByAuthor(authorId)).withSelfRel());
    return resources;
  }

  @GetMapping("/{id}")
  public ResponseEntity<BookResource> getBook(@PathVariable("id") Long id) {
    return this.repository.findById(id)
        .map(resourceAssembler::toResource)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }
}
