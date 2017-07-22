package com.example.cchiv.newsapp;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<ArrayList<News>> {

    ProgressBar progressBar;
    TextView textView;
    private String URLString = "https://content.guardianapis" +
            ".com/search?&api-key=955fdb97-7853-4438-b7b0-67dd531e0c5c&";
    private NewsAdapter newsAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.loading_screen);
        textView = (TextView) findViewById(R.id.message_screen);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        listView = (ListView) findViewById(R.id.list_item);

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
        return new NewsLoader(URLString, this);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<News>> loader, final ArrayList<News> data) {
        progressBar.setVisibility(View.GONE);
        textView.setVisibility(View.GONE);
        newsAdapter.clear();
        newsAdapter.addAll(data);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(data.get(position).getUrl()));

                        startActivity(intent);
                    }
                });
            }
        });
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<News>> loader) {
        newsAdapter.clear();
    }
}
