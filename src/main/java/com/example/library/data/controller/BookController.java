package com.example.library.data.controller;

import com.example.library.data.entity.Book;
import com.example.library.data.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping(value = "/all")
    public Iterable<Book> findAll() {
        return bookRepository.findAll();
    }

    @GetMapping(value = "/id={id}")
    public Optional<Book> findById(@PathVariable final Long id) {
        return bookRepository.findById(id);
    }

    @GetMapping(value = "/title={title}")
    public Iterable<Book> findByTitle(@PathVariable final String title) {
        return bookRepository.findByTitle(title);
    }

    @PostMapping(value = "/add")
    public void add(@RequestBody final Book book) {
        bookRepository.save(book);
    }
}
