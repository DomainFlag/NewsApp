package com.example.cchiv.newsapp;

import android.graphics.drawable.Drawable;

/**
 * Created by Cchiv on 21/07/2017.
 */

public class Section {

    private Article article;
    private SectionDefault sectionDefault;
    private Drawable drawable;

    public Section(Article fetchedArticle, SectionDefault fetchedSectionDefault, Drawable fetchedDrawable) {
        article = fetchedArticle;
        sectionDefault = fetchedSectionDefault;
        drawable = fetchedDrawable;
    }

    public Article getArticle() {
        return article;
    }

    public SectionDefault getSectionDefault() {
        return sectionDefault;
    }

    public Drawable getDrawable() {
        return drawable;
    }
}
