package com.github.berkleytechnologyservices.restdocs.example.book;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
  public CollectionModel<BookResource> getAllBooks() {
    CollectionModel<BookResource> resources = resourceAssembler.toCollectionModel(this.repository.findAll());
    resources.add(linkTo(methodOn(BookRestController.class).getAllBooks()).withSelfRel());
    return resources;
  }

  @GetMapping(path = "/search", params = "authorId")
  public CollectionModel<BookResource> getBooksByAuthor(@RequestParam("authorId") Long authorId) {
    CollectionModel<BookResource> resources = resourceAssembler.toCollectionModel(this.repository.findBooksByAuthorId(authorId));
    resources.add(linkTo(methodOn(BookRestController.class).getBooksByAuthor(authorId)).withSelfRel());
    return resources;
  }

  @GetMapping("/{id}")
  public ResponseEntity<BookResource> getBook(@PathVariable("id") Long id) {
    return this.repository.findById(id)
        .map(resourceAssembler::toModel)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }
}
