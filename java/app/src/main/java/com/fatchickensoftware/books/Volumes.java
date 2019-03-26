package com.fatchickensoftware.books;

import java.util.List;

import static java.util.Collections.emptyList;

public class Volumes {

    public final List<Volume> items;

    Volumes(List<Volume> items) {
        this.items = items == null ? emptyList() : items;
    }
}
