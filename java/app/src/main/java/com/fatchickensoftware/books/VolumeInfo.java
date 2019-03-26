package com.fatchickensoftware.books;

import java.io.Serializable;
import java.util.List;

import static java.util.Collections.emptyList;

public class VolumeInfo implements Serializable {

    public final String title;
    public final String subtitle;
    public final String description;
    public final List<String> authors;
    public final String publisher;
    public final String publishedDate;
    public final ImageLinks imageLinks;

    VolumeInfo(
            String title,
            String subtitle,
            String description,
            List<String> authors,
            String publisher,
            String publishedDate,
            ImageLinks imageLinks) {
        this.title = title;
        this.subtitle = subtitle;
        this.description = description;
        this.authors = authors == null ? emptyList() : authors;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.imageLinks = imageLinks;
    }
}
