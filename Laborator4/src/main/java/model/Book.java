package model;

import java.time.LocalDate;
import java.util.Objects;

//Java bean - o clasa cu toate atributele private, care are constructor default si settere si gettere
// si implementeaza interfata Serializable
//POJO - Plain Old Java Object - obiect care nu extinde alta clasa,
// nu implementeaza alta interfata

public class Book {
    private Long id;
    private String author;
    private String title;
    private LocalDate publishedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(author, book.author) && Objects.equals(title, book.title) && Objects.equals(publishedDate, book.publishedDate);
    }

    @Override
    public String toString(){
        return String.format("Book author: %s | title: %s | Published Date: %s.", author, title, publishedDate);
    }
}
