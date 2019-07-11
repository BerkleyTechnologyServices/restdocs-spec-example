package com.github.berkleytechnologyservices.restdocs.example.author;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.resourceDetails;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AuthorRestController.class)
@AutoConfigureRestDocs
@TestPropertySource(properties = "spring.jackson.serialization.indent_output=true")
public class AuthorRestControllerDocumentationTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private AuthorRepository repository;

  @MockBean(answer = Answers.CALLS_REAL_METHODS)
  private AuthorResourceAssembler resourceAssembler;

  @Test
  public void getAllAuthors() throws Exception {
    List<Author> authors = Arrays.asList(
        new Author(1L, "John", "Grisham"),
        new Author(2L, "Michael", "Connelly")
    );

    when(repository.findAll()).thenReturn(authors);

    this.mvc.perform(get("/author").accept("application/hal+json"))
        .andExpect(status().isOk())
        .andDo(
            document(
                "get-all-authors",
                resourceDetails()
                    .description("Get all authors."),
                responseFields(
                    subsectionWithPath("_links").ignored()
                ).andWithPrefix(
                    "_embedded.authorList[].",
                    fieldWithPath("firstName").description("The author's first name."),
                    fieldWithPath("lastName").description("The author's last name."),
                    subsectionWithPath("_links").ignored()
                ),
                links(
                    linkWithRel("self").description("Link to the authors.")
                )
            )
        );
  }

  @Test
  public void getAuthorById() throws Exception {
    Author author = new Author(1L, "John", "Grisham");

    when(repository.findById(1L)).thenReturn(Optional.of(author));

    this.mvc.perform(get("/author/{id}", 1L).accept("application/hal+json"))
        .andExpect(status().isOk())
        .andDo(
            document(
                "get-author-by-id",
                resourceDetails()
                    .description("Get an author by ID."),
                pathParameters(
                    parameterWithName("id").description("The unique identifier of the author.")
                ),
                responseFields(
                    fieldWithPath("firstName").description("The author's first name."),
                    fieldWithPath("lastName").description("The author's last name."),
                    subsectionWithPath("_links").ignored()
                ),
                links(
                    linkWithRel("self").description("Link to the author."),
                    linkWithRel("books").description("Link to the author's books.")
                )
            )
        );
  }
}
