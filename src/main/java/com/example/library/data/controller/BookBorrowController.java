package com.example.library.data.controller;

import com.example.library.data.entity.Book;
import com.example.library.data.entity.BookBorrow;
import com.example.library.data.entity.Borrower;
import com.example.library.data.repository.BookBorrowRepository;
import com.example.library.data.repository.BookRepository;
import com.example.library.data.repository.BorrowerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Date;

@RestController
@RequestMapping("/book_borrow")
public class BookBorrowController {

    @Autowired
    private BookBorrowRepository bookBorrowRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BorrowerRepository borrowerRepository;

    @GetMapping(value = "/all")
    public Iterable<BookBorrow> findAll() {
        return bookBorrowRepository.findAll();
    }

    @GetMapping(value = "/id={id}")
    private Optional<BookBorrow> findById(Long id) {
        return bookBorrowRepository.findById(id);
    }

    @GetMapping(value = "/book_id={bookId}")
    private Iterable<BookBorrow> findByBookIdOrderByIdDesc(Long bookId) {
        return bookBorrowRepository.findByBookIdOrderByIdDesc(bookId);
    }

    @GetMapping(value = "/borrower_id={borrowerId}")
    public Iterable<BookBorrow> findByBorrowerId(Long borrowerId) {
        return bookBorrowRepository.findByBorrowerId(borrowerId);
    }

    @GetMapping(value = "/in_library={inLibrary}")
    public Iterable<BookBorrow> findByInLibrary(boolean inLibrary) {
        return bookBorrowRepository.findByInLibrary(inLibrary);
    }

    @GetMapping(value = "/book_id={bookId}&&borrower_id={borrowerId}")
    public Iterable<BookBorrow> findByBookIdAndBorrowerId(Long bookId, Long borrowerId) {
        return bookBorrowRepository.findByBookIdAndBorrowerId(bookId, borrowerId);
    }

    @GetMapping(value = "/borrower_id={borrowerId}&&in_library={inLibrary}")
    public Iterable<BookBorrow> findByBorrowerIdAndInLibrary(Long borrowerId, boolean inLibrary) {
        return bookBorrowRepository.findByBorrowerIdAndInLibrary(borrowerId, inLibrary);
    }

    @GetMapping(value = "/book_id={bookId}&borrower_id={borrowerId}&&in_library={inLibrary}")
    public Iterable<BookBorrow> findByBookIdAndBorrowerIdAndInLibrary(Long bookId, Long borrowerId, boolean inLibrary) {
        return bookBorrowRepository.findByBookIdAndBorrowerIdAndInLibrary(bookId, borrowerId, inLibrary);
    }

    @PostMapping(value = "/add")
    public void borrowBook(@RequestBody final BookBorrow bookBorrow) {
        Long bookId = bookBorrow.getBookId();
        Long borrowerId = bookBorrow.getBorrowerId();

        // Verify book
        Optional<Book> book = bookRepository.findById(bookId);
        if (!book.isPresent()) {
            return;
        }
        // Verify borrower
        Optional<Borrower> borrower = borrowerRepository.findById(borrowerId);
        if (!borrower.isPresent()) {
            return;
        }
        long borrowingLimit = borrower.get().getBorrowingLimit();

        // If this borrower already reached the borrowing limits, he/she cannot borrow any more.
        Iterable<BookBorrow> borrowingRecords = findByBorrowerIdAndInLibrary(borrowerId, false);
        Long borrowerBookCounts = borrowingRecords.spliterator().getExactSizeIfKnown();
        if (borrowerBookCounts >= borrowingLimit) {
            return;
        }

        // If this book is not in library currently, the borrower cannot borrow it.
        Iterable<BookBorrow> records = findByBookIdOrderByIdDesc(bookId);
        boolean borrowable = false;
        if (records.spliterator().getExactSizeIfKnown() == 0) {
            borrowable = true;
        } else {
            while (records.iterator().hasNext()) {
                if (records.iterator().next().isInLibrary() == true) {
                    borrowable = true;
                    break;
                }
            }
        }
        if (borrowable == false) {
            return;
        }

        // Set borrow date if it is not given
        if (bookBorrow.getBorrowDate() == null) {
            bookBorrow.setBorrowDate(new Date());
        }
        // Set status
        bookBorrow.setInLibrary(false);

        // Submit
        bookBorrowRepository.save(bookBorrow);
    }


    @PutMapping(value = "/return")
    public void returnBook(@RequestBody final BookBorrow bookBorrow) {

        // no need return
        if (bookBorrow.isInLibrary() == true) return;

        Long bookId = bookBorrow.getBookId();
        Long borrowerId = bookBorrow.getBorrowerId();
        Long id = bookBorrow.getId();

        // Check ReturnDate
        if (bookBorrow.getReturnDate() == null) {
            bookBorrow.setReturnDate(new Date());
        }

        // Verify book
        Optional<Book> book = bookRepository.findById(bookId);
        if (!book.isPresent()) {
            return;
        }

        // Verify borrower
        Optional<Borrower> borrower = borrowerRepository.findById(borrowerId);
        if (!borrower.isPresent()) {
            return;
        }

        // If no borrow records
        Iterable<BookBorrow> borrowRecords = findByBookIdAndBorrowerIdAndInLibrary(bookId, borrowerId, false);
        if (borrowRecords.spliterator().getExactSizeIfKnown() == 0) return;

        // If id is not given, try to find out the id.
        if (id == 0) {
            // The return date should be null
            boolean returned = true;
            while (borrowRecords.iterator().hasNext()) {
                BookBorrow item = borrowRecords.iterator().next();
                if (item.getReturnDate() == null) {
                    returned = false;
                    id = item.getId();
                    bookBorrow.setId(id);
                    bookBorrow.setBorrowDate(item.getBorrowDate());
                    break;
                }
            }
            // Not borrowed out
            if (returned == true) return;
            // Not found
            if (id == 0) return;
        }

        bookBorrow.setInLibrary(true);
        System.out.println("Now We will return book.");
        bookBorrowRepository.saveAndFlush(bookBorrow);
    }

}
