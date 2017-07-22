package com.example.cchiv.newsapp;

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

    public static ArrayList<News> fetchNewsData(String urlString) {

        URL url = parseURLString(urlString);

        String output = fetchData(url);

        return parseFetchedData(output);
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
        HttpURLConnection httpURLConnection;
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

    private static ArrayList<News> parseFetchedData(String output) {
        ArrayList<News> arrayList = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(output);
            if(!jsonObject.has("response"))
                return null;
            JSONObject jsonObject1 = jsonObject.getJSONObject("response");
            if(!jsonObject1.has("results"))
                return null;
            JSONArray jsonArray = jsonObject1.getJSONArray("results");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectMain = jsonArray.getJSONObject(i);
                String stringMainTitle = "NaN";
                if (jsonObjectMain.has("webTitle"))
                    stringMainTitle = jsonObjectMain.getString("webTitle");
                String section = "NaN";
                if (jsonObjectMain.has("sectionName"))
                    section = jsonObjectMain.getString("sectionName");
                String stringMainUrl = null;
                if (jsonObjectMain.has("webUrl"))
                    stringMainUrl = jsonObjectMain.getString("webUrl");

                arrayList.add(new News(stringMainTitle, section, stringMainUrl));
            }
        } catch (JSONException e) {
            Log.v(context, e.toString());
        }

        return arrayList;
    }
}
