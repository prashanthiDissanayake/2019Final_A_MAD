package com.example.mad_paper_a;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddMovie extends AppCompatActivity {

    // create global scope object from DBHandler class
    DBHandler handler;

    // create global scope references for UI components
    Button btnAdd;
    EditText name, year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);

        handler = new DBHandler( getApplicationContext());

        // assign reference from XML layout elements
        btnAdd = findViewById(R.id.btnAdd);
        name = findViewById(R.id.txtMovieName);
        year = findViewById(R.id.txtMovieYear);


        // Question 04 - C
        // set click lister to register button
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean isSuccess =  handler.addMovie( name.getText().toString(), Integer.parseInt(year.getText().toString()));

                // Question 04 - d
                if(isSuccess){
                    Toast.makeText(getApplicationContext(), "Movie added successfully", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Movie insertion process failed", Toast.LENGTH_LONG).show();
                }
            }

//            public void onClick (View view){
//                AlertDialog.Builder dialog = new AlertDialog.Builder(AddMovie.this);
//
//                dialog.setTitle("My profile");
//                dialog.setPositiveButton("ok",newDialogInterface.onClickListner())
//
//            }
        });
    }
}