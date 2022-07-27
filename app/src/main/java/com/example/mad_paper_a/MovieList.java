package com.example.mad_paper_a;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MovieList extends AppCompatActivity {

    // create global scope object from DBHandler class
    DBHandler handler;
    ListView movieListView;

    // Question 04 - e
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new DBHandler( getApplicationContext());
        setContentView(R.layout.activity_movie_list);

        movieListView = findViewById(R.id.movieList);
        // get movie list from dbHandler
        List movieDataList = handler.viewMovies();


        // create array adapter to set items to listview
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(
                this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                movieDataList);
        movieListView.setAdapter(adapter);

        // Question 04 - f
        // set click lister to each item in listview
        movieListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                // create explicit intent
                Intent overviewIntent = new Intent(getApplicationContext(), MovieOverview.class);

                // pass movie name into extras as key value pair
                overviewIntent.putExtra("moviename", adapterView.getItemAtPosition(i).toString());

                //start activity
                startActivity(overviewIntent);
            }
        });

    }
}