package com.example.library.data.entity;


import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "BOOK")
public class Book {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "AUTHOR", columnDefinition = "TEXT")
    @Convert(converter = JpaConverterJson.class)
    private Author author;

    @ElementCollection
    @Column(name = "GENRES", columnDefinition = "TEXT")
    @Convert(converter = GenreConverterJson.class)
    private Set<Genre> genres;

    @Column(name = "PAGE_COUNT")
    private int pageCount;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }
}
