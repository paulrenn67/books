package com.fatchickensoftware.books;

import java.io.Serializable;

public class Volume implements Serializable {

    public final String id;
    public final VolumeInfo volumeInfo;

    Volume(String id, VolumeInfo volumeInfo) {
        this.id = id;
        this.volumeInfo = volumeInfo;
    }
}
