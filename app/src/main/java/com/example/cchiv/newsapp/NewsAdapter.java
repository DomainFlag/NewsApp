package com.example.cchiv.newsapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cchiv on 20/07/2017.
 */

public class NewsAdapter extends ArrayAdapter<News> {

    private Context ctx;

    public NewsAdapter(@NonNull Context context, @NonNull List<News> objects) {
        super(context, 0, objects);
        ctx = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;

        if(listItemView == null)
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.news_layout, parent, false);

        News news = getItem(position);

        Section section = news.getSection();
        SectionDefault sectionDefault = section.getSectionDefault();
        TextView textView;

        View view = listItemView.findViewById(R.id.section_label);
        view.setBackgroundColor(sectionDefault.getColor());

        textView = (TextView) listItemView.findViewById(R.id.section_label_text);
        textView.setText(sectionDefault.getName());
        textView.setTextColor(sectionDefault.getColor());

        ImageView imageView = (ImageView) listItemView.findViewById(R.id.section_drawable);
        imageView.setImageDrawable(section.getDrawable());

        textView = (TextView) listItemView.findViewById(R.id.title);
        textView.setText(section.getArticle().getTitle());

        RelativeLayout relativeLayout = (RelativeLayout) listItemView.findViewById(R.id.section_main);
        relativeLayout.setOnClickListener(new NewsOnClickListener(section.getArticle().getUrl()));

        final ArrayList<Article> otherNewsTitles = news.getArticles();
        for(int i = 0; i < otherNewsTitles.size(); i++) {
            Article article = otherNewsTitles.get(i);
            switch (i) {
                case 0: {
                    textView = (TextView) listItemView.findViewById(R.id.brief_title_1);
                    break;
                }
                case 1: {
                    textView = (TextView) listItemView.findViewById(R.id.brief_title_2);
                    break;
                }
                case 2: {
                    textView = (TextView) listItemView.findViewById(R.id.brief_title_3);
                    break;
                }
            }
            textView.setText(article.getTitle());
            textView.setOnClickListener(new NewsOnClickListener(article.getUrl()));
        }
        return listItemView;
    }

    public class NewsOnClickListener implements View.OnClickListener {

        private String url;

        public NewsOnClickListener(String fetchedUrl) {
            url = fetchedUrl;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));

            getContext().startActivity(intent);
        }
    }
}
