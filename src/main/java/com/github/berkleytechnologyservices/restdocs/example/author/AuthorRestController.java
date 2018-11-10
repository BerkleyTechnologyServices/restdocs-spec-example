package com.github.berkleytechnologyservices.restdocs.example.author;

import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

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
  public Resources<AuthorResource> getAllAuthors() {
    List<AuthorResource> authorResources = resourceAssembler.toResources(this.repository.findAll());
    Resources<AuthorResource> resources = new Resources<>(authorResources);
    resources.add(linkTo(methodOn(AuthorRestController.class).getAllAuthors()).withSelfRel());
    return resources;
  }

  @GetMapping("/{id}")
  public ResponseEntity<AuthorResource> getAuthorById(@PathVariable("id") Long id) {
    return this.repository.findById(id)
        .map(resourceAssembler::toResource)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

}
