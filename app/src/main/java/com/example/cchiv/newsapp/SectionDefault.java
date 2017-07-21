package com.example.cchiv.newsapp;

/**
 * Created by Cchiv on 21/07/2017.
 */

public class SectionDefault {

    private int color;
    private String name;

    public SectionDefault(String fetchedName, int fetchedColor) {
        name = fetchedName;
        color = fetchedColor;
    }

    public String getName() {
        return name;
    }

    public int getColor() {
        return color;
    }
}
