package com.example.cchiv.newsapp;

import android.graphics.drawable.Drawable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by Cchiv on 20/07/2017.
 */

public class QueryUtils {

    private static String context = QueryUtils.class.getSimpleName();

    public QueryUtils() {}

    public static ArrayList<News> fetchNewsData(String urlString, ArrayList<SectionDefault> sections) {

        String[] strings = urlString.split("untitled");

        ArrayList<News> arrayList = new ArrayList<>();

        for(int i = 0; i < sections.size(); i++) {
            String newURLString = strings[0] + sections.get(i).getName() + strings[1];

            URL url = parseURLString(newURLString);

            String output = fetchData(url);

            News news = parseFetchedData(output, sections.get(i));

            if(news != null)
                arrayList.add(news);
        }

        return arrayList;
    }

    private static URL parseURLString(String urlString) {
        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            Log.v(context, e.toString());
        }
        return url;
    }

    private static String fetchData(URL url) {
        HttpURLConnection httpURLConnection = null;
        StringBuilder output = new StringBuilder();

        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setReadTimeout(15000);
            httpURLConnection.connect();

            InputStream inputStream = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line = bufferedReader.readLine();
            while(line != null) {
                output.append(line);
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            Log.v(context, e.toString());
        }

        return output.toString();
    }

    private static News parseFetchedData(String output, SectionDefault sectionDefault) {
        News news = null;

        try {
            JSONObject jsonObject = new JSONObject(output);
            if(!jsonObject.has("response"))
                return null;
            JSONObject jsonObject1 = jsonObject.getJSONObject("response");
            if(!jsonObject1.has("results"))
                return null;
            JSONArray jsonArray = jsonObject1.getJSONArray("results");

            JSONObject jsonObjectMain = jsonArray.getJSONObject(0);
            String stringMainTitle = "NaN";
            if(jsonObjectMain.has("webTitle"))
                stringMainTitle = jsonObjectMain.getString("webTitle");
            String stringMainUrl = null;
            if(jsonObjectMain.has("webUrl"))
                stringMainUrl = jsonObjectMain.getString("webUrl");
            Article articleMain = new Article(stringMainTitle, stringMainUrl);

            if(!jsonObjectMain.has("fields"))
                return  null;
            JSONObject jsonObjectMainFields = jsonObjectMain.getJSONObject("fields");
            if(!jsonObjectMainFields.has("thumbnail"))
                return null;
            String thumbnailURL = jsonObjectMainFields.getString("thumbnail");
            Drawable drawable = getURLDrawable(thumbnailURL, 0);
            Section section = new Section(articleMain, sectionDefault, drawable);

            ArrayList<Article> articles = new ArrayList<>();

            for(int i = 1; i < jsonArray.length() && i < 4; i++) {
                JSONObject jsonObjectSecondary = jsonArray.getJSONObject(i);
                String stringSecondaryTitle = "NaN";
                if(jsonObjectSecondary.has("webTitle"))
                    stringSecondaryTitle = jsonObjectSecondary.getString("webTitle");
                String stringSecondaryUrl = "NaN";
                stringSecondaryUrl = jsonObjectSecondary.getString("webUrl");
                articles.add(new Article(stringSecondaryTitle, stringSecondaryUrl));
            }
            news = new News(section ,articles);

        } catch (JSONException e) {
            Log.v(context, e.toString());
        }

        return news;
    }

    private static Drawable getURLDrawable(String url, int index) {
        Drawable drawable = null;
        try {
            InputStream urlContent = (InputStream) new URL(url).getContent();
            drawable = Drawable.createFromStream(urlContent, "image" + index);
        } catch (IOException e) {
            Log.v(context, e.toString());
        }
        return drawable;
    }
}
