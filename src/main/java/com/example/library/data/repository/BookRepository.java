package com.example.library.data.repository;

import com.example.library.data.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;


@Component
public interface BookRepository extends JpaRepository<Book, Long> {
    Iterable<Book> findByTitle(String title);
}
