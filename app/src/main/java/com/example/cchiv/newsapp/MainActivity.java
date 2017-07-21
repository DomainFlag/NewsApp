package com.example.cchiv.newsapp;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<ArrayList<News>> {

    private String URLString = "https://content.guardianapis.com/search?section=untitled&show-fields=thumbnail&api-key=955fdb97-7853-4438-b7b0-67dd531e0c5c&";

    private NewsAdapter newsAdapter;

    private ArrayList<SectionDefault> sections = new ArrayList<>();

    ProgressBar progressBar;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.loading_screen);
        textView = (TextView) findViewById(R.id.message_screen);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        sections.add(new SectionDefault("politics", ContextCompat.getColor(this, R.color.politics)));
        sections.add(new SectionDefault("music", ContextCompat.getColor(this, R.color.music)));
        sections.add(new SectionDefault("science", ContextCompat.getColor(this, R.color.science)));
        sections.add(new SectionDefault("culture", ContextCompat.getColor(this, R.color.culture)));
        sections.add(new SectionDefault("environment", ContextCompat.getColor(this, R.color.environment)));
        sections.add(new SectionDefault("technology", ContextCompat.getColor(this, R.color.social)));

        ListView listView = (ListView) findViewById(R.id.list_item);

        ArrayList<News> arrayList = new ArrayList<>();

        newsAdapter = new NewsAdapter(this, arrayList);

        listView.setAdapter(newsAdapter);

        if(networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(0, null, this);
        } else {
            progressBar.setVisibility(View.GONE);
            textView.setText("No Internet Connection");
        }
    }

    @Override
    public Loader<ArrayList<News>> onCreateLoader(int id, Bundle args) {
        progressBar.setVisibility(View.VISIBLE);
        textView.setVisibility(View.VISIBLE);
        return new NewsLoader(URLString, this, sections);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<News>> loader, ArrayList<News> data) {
        progressBar.setVisibility(View.GONE);
        textView.setVisibility(View.GONE);
        newsAdapter.addAll(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<News>> loader) {
        newsAdapter.clear();
    }
}
