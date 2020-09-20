package com.example.library.data.controller;

import com.example.library.data.entity.Borrower;
import com.example.library.data.repository.BorrowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/borrower")
public class BorrowerController {

    @Autowired
    BorrowerRepository borrowerRepository;

    @GetMapping(value = "/all")
    public Iterable<Borrower> findAll() {
        return borrowerRepository.findAll();
    }

    @GetMapping(value = "/id={id}")
    public Optional<Borrower> findById(@PathVariable final Long id) {
        return borrowerRepository.findById(id);
    }

    @GetMapping(value = "/first_name={firstName}&&last_name={lastName}")
    public Iterable<Borrower> findByName(@PathVariable final String firstName, @PathVariable final String lastName) {
        return borrowerRepository.findByFirstNameAndLastName(firstName, lastName);
    }

    @PostMapping(value = "/add")
    public void add(@RequestBody final Borrower borrower) {
        borrowerRepository.save(borrower);
    }
}
