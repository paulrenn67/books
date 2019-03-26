package com.fatchickensoftware.books;

import java.io.Serializable;

public class ImageLinks implements Serializable {

    public final String smallThumbnail;
    public final String thumbnail;

    ImageLinks(String smallThumbnail, String thumbnail) {
        this.smallThumbnail = smallThumbnail;
        this.thumbnail = thumbnail;
    }
}
