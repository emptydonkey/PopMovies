package com.example.android.popularmovies;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;

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
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Mov> movies;

    RecyclerView recyclerView;

    FetchMovies fetchMovies;

    String apiKey = "229dfe17fa3c6de2dbbe27af794364c2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle(R.string.app_name);

        recyclerView = findViewById(R.id.list_movies);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));

        movies = new ArrayList<>();

        fetchMovies = new FetchMovies();


        ImageRecyclerAdapter imageAdapter = new ImageRecyclerAdapter(this, movies);
        recyclerView.setAdapter(imageAdapter);
        fetchMovies.execute();
    }

    public class FetchMovies extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {

            movies = new ArrayList<>();

            String urlBase = "https://api.themoviedb.org/3/movie/popular?api_key=";
            URL url = null;
            try {
                url = new URL(urlBase + apiKey);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            InputStream stream = null;
            BufferedReader reader;

            String jsonResponse = "";
            try {
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();

                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                jsonResponse = buffer.toString();

                //this is to isolate the "results" JSON array so GSON can pars it into Objects
                try {
                    JSONObject jo = new JSONObject(jsonResponse);
                    JSONArray arr = jo.getJSONArray("results");
                    jsonResponse = arr.toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (connection != null) {
                    connection.disconnect();
                }
            } catch (IOException e) {
                Log.d("doInBackground", "Http Request fail");
            }


            return jsonResponse;
        }

        @Override
        protected void onPostExecute(String jsonResponse) {
            super.onPostExecute(jsonResponse);


            Gson gson = new Gson();
            Mov[] movieArray = gson.fromJson(jsonResponse, Mov[].class);

            for (Mov m : movieArray) {
                movies.add(m);
            }

            ImageRecyclerAdapter adapter = ((ImageRecyclerAdapter) recyclerView.getAdapter());
            recyclerView.setAdapter(adapter);
        }
    }
}
