package com.example.library.data.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "BOOK_BORROW")
public class BookBorrow {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "BOOK_ID")
    private Long bookId;

    @Column(name = "BORROWER_ID")
    private Long borrowerId;

    @Column(name = "BORROW_DATE")
    private Date borrowDate;

    @Column(name = "RETURN_DATE")
    private Date returnDate;

    @Column(name = "IN_LIBRARY", columnDefinition="tinyint(1) default 1")
    private boolean inLibrary;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getBorrowerId() {
        return borrowerId;
    }

    public void setBorrowerId(Long borrowerId) {
        this.borrowerId = borrowerId;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public boolean isInLibrary() {
        return inLibrary;
    }

    public void setInLibrary(boolean inLibrary) {
        this.inLibrary = inLibrary;
    }

}
