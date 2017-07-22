package com.example.cchiv.newsapp;

/**
 * Created by Cchiv on 20/07/2017.
 */

public class News {

    private String title;
    private String section;
    private String url;

    public News(String string1, String string2, String string3) {
        title = string1;
        section = string2;
        url = string3;
    }

    public String getTitle() {
        return title;
    }

    public String getSection() {
        return section;
    }

    public String getUrl() {
        return url;
    }
}
