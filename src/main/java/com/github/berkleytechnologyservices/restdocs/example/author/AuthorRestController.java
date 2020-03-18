package com.github.berkleytechnologyservices.restdocs.example.author;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/author", produces = "application/hal+json")
public class AuthorRestController {

  private final AuthorRepository repository;
  private final AuthorResourceAssembler resourceAssembler;

  public AuthorRestController(AuthorRepository repository,
                              AuthorResourceAssembler resourceAssembler) {
    this.repository = repository;
    this.resourceAssembler = resourceAssembler;
  }

  @GetMapping
  public CollectionModel<AuthorResource> getAllAuthors() {
    CollectionModel<AuthorResource> resources = resourceAssembler.toCollectionModel(this.repository.findAll());
    resources.add(linkTo(methodOn(AuthorRestController.class).getAllAuthors()).withSelfRel());
    return resources;
  }

  @GetMapping("/{id}")
  public ResponseEntity<AuthorResource> getAuthorById(@PathVariable("id") Long id) {
    return this.repository.findById(id)
        .map(resourceAssembler::toModel)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

}
