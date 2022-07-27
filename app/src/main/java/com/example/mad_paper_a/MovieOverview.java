package com.example.mad_paper_a;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MovieOverview extends AppCompatActivity {

    // create global scope object from DBHandler class
    DBHandler handler;
    public String movieName = "Doctor strange";

    // create global scope references for UI components
    TextView avgRate, overViewName;
    ListView commentListView;
    Button btnSubmit;
    SeekBar seekbar;
    EditText commentText;

    // Question 04 - g
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_overview);
        handler = new DBHandler( getApplicationContext());

        // create Intent object to catch intent
         Intent myIntent = getIntent();

        // extract parsed data to intent
        movieName = myIntent.getStringExtra("moviename");


        // create global scope references for UI components
        commentListView =  findViewById(R.id.commentList);
        avgRate = findViewById(R.id.avgRate);
        btnSubmit =  findViewById(R.id.btnSubmit);
        seekbar = findViewById(R.id.seekBar);
        commentText =  findViewById(R.id.txtComment);
        overViewName =  findViewById(R.id.overviewName);

        overViewName.setText(movieName);




        // get data from DBHandler
        List<List<String>> commentsDataList =  handler.viewComments(movieName);

        List CommentsOnlyList = new ArrayList();
        int sumOFRates = 0;
        for (int x =0; x< commentsDataList.size(); x++){

            CommentsOnlyList.add(commentsDataList.get(x).get(0).toString());
            sumOFRates += Integer.parseInt(commentsDataList.get(x).get(1).toString());
        }

        // set average rate count to text view
        if(commentsDataList.size() == 0){
            avgRate.setText("0.0");
        }
        else {
            avgRate.setText( String.valueOf(sumOFRates/commentsDataList.size()));
        }


        // create array adapter to set items to listview
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(
                this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                CommentsOnlyList);
        commentListView.setAdapter(adapter);


        // Question 04 - h
        // set click lister to submit button
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // insert rate , comment into database
               boolean isSuccess =  handler.insertComments(movieName, seekbar.getProgress(), commentText.getText().toString());

                // Question 04 - i
               if(isSuccess){
                   Toast.makeText(getApplicationContext(), "Comment added successfully", Toast.LENGTH_LONG).show();
               }
               else {
                   Toast.makeText(getApplicationContext(), "Comment insertion process failed", Toast.LENGTH_LONG).show();
               }
            }
        });


    }
}