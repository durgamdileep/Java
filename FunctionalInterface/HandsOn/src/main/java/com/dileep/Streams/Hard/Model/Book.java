package com.dileep.Streams.Hard.Model;

import java.util.List;

public class Book {
    String title;
    String author;
    int yearPublished;
    List<String> genres; // e.g., ["Fantasy", "Adventure"]

    // constructor, getters


    public Book() {
    }

    public Book(String title, String author, int yearPublished, List<String> genres) {
        this.title = title;
        this.author = author;
        this.yearPublished = yearPublished;
        this.genres = genres;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYearPublished() {
        return yearPublished;
    }

    public void setYearPublished(int yearPublished) {
        this.yearPublished = yearPublished;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", yearPublished=" + yearPublished +
                ", genres=" + genres +
                '}';
    }
}
