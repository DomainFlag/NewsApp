package com.example.cchiv.newsapp;

import java.util.ArrayList;

/**
 * Created by Cchiv on 20/07/2017.
 */

public class News {

    private Section section;
    private ArrayList<Article> articles;

    public News(Section fetchedSection, ArrayList<Article> fetchedArticles) {
        section = fetchedSection;
        articles = fetchedArticles;
    }

    public Section getSection() {
        return section;
    }

    public ArrayList<Article> getArticles() {
        return articles;
    }
}
