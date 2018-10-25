package com.github.berkleytechnologyservices.restdocs.example.book;

import com.github.berkleytechnologyservices.restdocs.example.author.Author;
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

import static com.epages.restdocs.openapi.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.openapi.MockMvcRestDocumentationWrapper.resourceDetails;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BookRestController.class)
@AutoConfigureRestDocs
@TestPropertySource(properties = "spring.jackson.serialization.indent_output=true")
public class BookRestControllerDocumentationTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private BookRepository repository;

  @MockBean(answer = Answers.CALLS_REAL_METHODS)
  private BookResourceAssembler resourceAssembler;

  @Test
  public void getAllBooks() throws Exception {
    Author grisham = new Author(1L, "John", "Grisham");
    Author connelly = new Author(2L, "Michael", "Connelly");

    List<Book> books = Arrays.asList(
        new Book(1L, "A Time To Kill", 1989, grisham),
        new Book(2L, "The Firm", 1991, grisham),
        new Book(3L, "The Pelican Brief", 1992, grisham),
        new Book(4L, "The Black Echo", 1992, connelly),
        new Book(5L, "The Black Ice", 1993, connelly),
        new Book(6L, "The Concrete Blonde", 1994, connelly)
    );

    when(repository.findAll()).thenReturn(books);

    this.mvc.perform(get("/book").accept("application/hal+json"))
        .andExpect(status().isOk())
        .andDo(
            document(
                "get-all-books",
                resourceDetails()
                    .description("Get all books."),
                responseFields(
                    subsectionWithPath("_links").ignored()
                ).andWithPrefix(
                    "_embedded.bookList[].",
                    fieldWithPath("title").description("The title of the book."),
                    fieldWithPath("year").description("The year the book was published."),
                    fieldWithPath("author.firstName").description("The author's first name."),
                    fieldWithPath("author.lastName").description("The author's last name."),
                    subsectionWithPath("_links").ignored()
                ),
                links(
                    linkWithRel("self").description("Link to the books.")
                )
            )
        );
  }

  @Test
  public void getBooksByAuthor() throws Exception {
    Author grisham = new Author(1L, "John", "Grisham");

    List<Book> books = Arrays.asList(
        new Book(1L, "A Time To Kill", 1989, grisham),
        new Book(2L, "The Firm", 1991, grisham),
        new Book(3L, "The Pelican Brief", 1992, grisham)
    );

    when(repository.findBooksByAuthorId(1L)).thenReturn(books);

    this.mvc.perform(get("/book/search").param("authorId", "1").accept("application/hal+json"))
        .andExpect(status().isOk())
        .andDo(
            document(
                "get-books-by-author",
                resourceDetails()
                    .description("Get books by author."),
                requestParameters(
                    parameterWithName("authorId").description("The author's unique identifier.")
                ),
                responseFields(
                    subsectionWithPath("_links").ignored()
                ).andWithPrefix(
                    "_embedded.bookList[].",
                    fieldWithPath("title").description("The title of the book."),
                    fieldWithPath("year").description("The year the book was published."),
                    fieldWithPath("author.firstName").description("The author's first name."),
                    fieldWithPath("author.lastName").description("The author's last name."),
                    subsectionWithPath("_links").ignored()
                ),
                links(
                    linkWithRel("self").description("Link to the books.")
                )
            )
        );
  }

  @Test
  public void getBookById() throws Exception {
    Author author = new Author(1L, "John", "Grisham");
    Book book = new Book(1L, "A Time To Kill", 1989, author);
    author.getBooks().add(book);

    when(repository.findById(1L)).thenReturn(Optional.of(book));

    this.mvc.perform(get("/book/{id}", 1L).accept("application/hal+json"))
        .andExpect(status().isOk())
        .andDo(
            document(
                "get-book-by-id",
                resourceDetails()
                    .description("Get a book by ID."),
                pathParameters(
                    parameterWithName("id").description("The unique identifier of the book.")
                ),
                responseFields(
                    fieldWithPath("title").description("The title of the book."),
                    fieldWithPath("year").description("The year the book was published."),
                    fieldWithPath("author.firstName").description("The author's first name."),
                    fieldWithPath("author.lastName").description("The author's last name."),
                    subsectionWithPath("_links").ignored()
                ),
                links(
                    linkWithRel("self").description("Link to the book."),
                    linkWithRel("author").description("Link to the author.")
                )
            )
        );
  }

}
