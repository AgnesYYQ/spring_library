package com.example.library.data.repository;

import com.example.library.data.entity.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;


@Component
public interface BorrowerRepository extends JpaRepository<Borrower, Long> {
    Iterable<Borrower> findByFirstNameAndLastName(String firstName, String lastName);
}
