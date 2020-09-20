package com.example.library.data.repository;

import com.example.library.data.entity.BookBorrow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface BookBorrowRepository extends JpaRepository<BookBorrow, Long> {
    Iterable<BookBorrow> findByBookIdOrderByIdDesc(Long bookId);
    Iterable<BookBorrow> findByBorrowerId(Long borrowerId);
    Iterable<BookBorrow> findByInLibrary(boolean inLibrary);
    Iterable<BookBorrow> findByBookIdAndBorrowerId(Long bookId, Long borrowerId);
    Iterable<BookBorrow> findByBorrowerIdAndInLibrary(Long borrowerId, boolean inLibrary);
    Iterable<BookBorrow> findByBookIdAndBorrowerIdAndInLibrary(Long bookId, Long borrowerId, boolean inLibrary);
}
