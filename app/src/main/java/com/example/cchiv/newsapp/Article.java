package com.example.cchiv.newsapp;

/**
 * Created by Cchiv on 21/07/2017.
 */

public class Article {

    private String url;
    private String title;

    public Article(String string1, String string2) {
        title = string1;
        url = string2;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }
}
