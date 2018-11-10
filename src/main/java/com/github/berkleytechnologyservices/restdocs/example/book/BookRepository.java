package com.github.berkleytechnologyservices.restdocs.example.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

  List<Book> findBooksByAuthorId(Long authorId);

}
