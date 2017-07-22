package com.example.cchiv.newsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Cchiv on 20/07/2017.
 */

public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(@NonNull Context context, @NonNull List<News> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;

        if(listItemView == null)
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.news_layout, parent, false);

        News news = getItem(position);

        TextView textView;

        textView = (TextView) listItemView.findViewById(R.id.title);
        textView.setText(news.getTitle());

        textView = (TextView) listItemView.findViewById(R.id.section);
        textView.setText(news.getSection());

        return listItemView;
    }

}
