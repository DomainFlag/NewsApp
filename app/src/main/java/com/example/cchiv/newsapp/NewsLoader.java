package com.example.cchiv.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Cchiv on 20/07/2017.
 */

public class NewsLoader extends AsyncTaskLoader<ArrayList<News>>{

    private String url;

    public NewsLoader(String urlString, Context context) {
        super(context);
        url = urlString;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<News> loadInBackground() {
        return QueryUtils.fetchNewsData(url);
    }
}
