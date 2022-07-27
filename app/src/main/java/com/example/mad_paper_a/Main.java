package com.example.mad_paper_a;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main extends AppCompatActivity {

    // create global scope object from DBHandler class
    DBHandler handler  = new DBHandler( this);


    // create global scope references for UI components
    Button regsiterBtn, loginBtn;
    EditText txtUsername, txtPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        // assign reference from XML layout elements
        regsiterBtn = findViewById(R.id.btn_register);
        loginBtn = findViewById(R.id.btn_login);
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);

//        //Shared Preferences practise
//        SharedPreferences sharedPreferences = getSharedPreferences(name:"mySharedpref",MODE_PRIVATE);
//        SharedPreferences.Editor myEdit = SharedPreferences.edit();
//        myEdit.putString("name",s1: "Susith");
//

        // Question 04 - a
        // set click lister to register button
        regsiterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(Main.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                handler.registerUser(txtUsername.getText().toString() ,  txtPassword.getText().toString());

            }
        });


        // Question 04 - b
        // set click lister to login button
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                handler.loginUser(txtUsername.getText().toString() ,  txtPassword.getText().toString());

            }
        });

    }
}