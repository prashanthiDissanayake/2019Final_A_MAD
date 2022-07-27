package com.example.mad_paper_a;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

// Question 03 - a
// Create java class called DBHandler and extend it to SQLiteOpenHelper class to take SQLite features
public class DBHandler extends SQLiteOpenHelper {

//    @BeforeEach
//    public void setup(){
//        DBHandler handler = new DBHandler(context.getApplicationContext());
//    }
////    @Test
//    public void TestFarmer(){
//        boolean result = DBHandler.checkFarmer("Kamal");
//        assert True("This will succeed",result);
//    }


    public static final String DATABASE_NAME = "moviedb";

    // Create Content object to store context
    // Related to Question 03 - C - ii
    public Context context;
    public DBHandler(Context context) {

        super(context, DATABASE_NAME, null, 1);
        this.context = context;
    }

    // Question 03 - a - Override onCreate method
    // onCreate method will execute everytime when an instance of this DBHandler class created
    // Once executed this method , it will create relevant database table. ( Literally when you running this application first time )
    // If the table exists, nothing will happen

//    public void onClick(View view){
//        try{
//            addData(name.getText().toString(),)
//        }
//        catch (exception ex){
//            Toast.makeText(context, "Data is not inserted", Toast.LENGTH_SHORT).show();
//        }
//    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // Prepare SQL Query string to create Users table
        String SQL_CREATE_TABLE_USERS =
                "CREATE TABLE IF NOT EXISTS " + DatabaseMaster.Users.TABLE_NAME + " (" +
                        DatabaseMaster.Users.USER_NAME + " TEXT PRIMARY KEY," +
                        DatabaseMaster.Users.PASSWORD + " TEXT," +
                        DatabaseMaster.Users.USER_TYPE + " TEXT)";

        // Prepare SQL Query string to create Movie table
        String SQL_CREATE_TABLE_Movie =
                "CREATE TABLE IF NOT EXISTS " + DatabaseMaster.Movie.TABLE_NAME + " (" +
                        DatabaseMaster.Movie.MOVIE_NAME + " TEXT," +
                        DatabaseMaster.Movie.MOVIE_YEAR + " INTEGER )";

        // Prepare SQL Query string to create Comments table
        String SQL_CREATE_TABLE_Comments =
                "CREATE TABLE IF NOT EXISTS " + DatabaseMaster.Comments.TABLE_NAME + " (" +
                        DatabaseMaster.Comments.MOVIE_NAME + " TEXT," +
                        DatabaseMaster.Comments.MOVIE_RATING + " INTEGER," +
                        DatabaseMaster.Comments.MOVIE_COMMENTS + " TEXT )";


        // Execute all prepared queries one by one
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_USERS);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_Movie);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_Comments);

    }

    // Question 03 - b
    // parameters -> username,  password
    public void registerUser (String username, String password){

        // Create database reference object with write permission
        SQLiteDatabase db = getWritableDatabase();

        // Create Object to from ContentValues class
        ContentValues values = new ContentValues();

        // add given data as key-value pair
        values.put(DatabaseMaster.Users.USER_NAME, username);
        values.put(DatabaseMaster.Users.PASSWORD, password);

        // Push ContentValues object into  database
        db.insert(DatabaseMaster.Users.TABLE_NAME, null, values);
    }


    // Question 03 - c
    // parameters -> username,  password
    public void loginUser(String username, String password){


        // Check condition of Question 03 - C - i
        if(username.equals("admin")){

            // Create explicit intent to AddMovie Activity
            Intent adminIntent = new Intent(context, AddMovie.class);
            adminIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // Start activity from intent
            context.startActivity(adminIntent);
            return;

        }

        // Create database reference object with read permission
        SQLiteDatabase db = getReadableDatabase();

        // Define a projection columns ( These are the columns will return from query )
        String[] projection = {
                DatabaseMaster.Users.USER_NAME,
                DatabaseMaster.Users.PASSWORD
        };

        // Prepare where conditions to check username ( Value will be inject in later )
        String selection = DatabaseMaster.Users.USER_NAME + " = ?";

        // create array with given username. This array will be pass into query function
        String[] selectionArgs = { username };

        // create cursor object and store returned cursor from query function
        Cursor cursor =  db.query(
                DatabaseMaster.Users.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );


        // check results count equals to 0. it means no users for given username.
        //Question 03 - C - iv
        if(cursor.getCount() == 0){
            Toast.makeText(context, "Login failed : No existing users for given user name !", Toast.LENGTH_LONG).show();
        }
        else {

            // loop through cursor object
            while (cursor.moveToNext()){

                // Extract password from result and check whether its matching or not
                if(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseMaster.Users.PASSWORD)).equals(password)){


                    // Create explicit intent to MovieList Activity
                    Intent MovieList = new Intent(context , MovieList.class);
                    MovieList.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    // Start activity from intent
                    context.startActivity(MovieList);
                }
                else {
                    //Question 03 - C - iv
                    // given username exists in database but password not matching
                    Toast.makeText(context, "Login failed : Password incorrect for given user name !", Toast.LENGTH_LONG).show();
                    break;
                }

            }
        }



    }

    //Question 03 - e
    // Must return boolean value
    public boolean addMovie(String name, Integer year){

        try {
            // Create database reference object with write permission
            SQLiteDatabase db = getWritableDatabase();

            // Create Object to from ContentValues class
            ContentValues values = new ContentValues();

            // add given data as key-value pair
            values.put(DatabaseMaster.Movie.MOVIE_NAME, name);
            values.put(DatabaseMaster.Movie.MOVIE_YEAR, year);

            // Push ContentValues object into  database
            long id = db.insert(DatabaseMaster.Movie.TABLE_NAME, null, values);

            return true;
        }
        catch (Exception ex){

            // something went wrong , then return false
            return false;
        }


    }

    //Question 03 - f
    // Must return boolean value
    public List<String> viewMovies(){

        List MovieNames = new ArrayList<String>();

        // Create database reference object with read permission
        SQLiteDatabase db = getReadableDatabase();

        // Define a projection columns ( These are the columns will return from query )
        String[] projection = {
                DatabaseMaster.Movie.MOVIE_NAME
        };



        // create cursor object and store returned cursor from query function
        Cursor cursor =  db.query(
                DatabaseMaster.Movie.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        // Iterate through while loop and extract data from cursor and push necessary data into List Object
        while (cursor.moveToNext()){
            MovieNames.add(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseMaster.Movie.MOVIE_NAME)));
        }

        return  MovieNames;
    }


    //Question 03 - g
    // Must return boolean value
    public boolean insertComments(String name, Integer rating, String comments){

        try {
            // Create database reference object with write permission
            SQLiteDatabase db = getWritableDatabase();

            // Create Object to from ContentValues class
            ContentValues values = new ContentValues();

            // add given data as key-value pair
            values.put(DatabaseMaster.Comments.MOVIE_NAME, name);
            values.put(DatabaseMaster.Comments.MOVIE_RATING, rating);
            values.put(DatabaseMaster.Comments.MOVIE_COMMENTS, comments);

            // Push ContentValues object into  database
            long id = db.insert(DatabaseMaster.Comments.TABLE_NAME, null, values);

            return true;
        }
        catch (Exception ex){

            // something went wrong , then return false
            return false;
        }

    }


    //Question 03 - h
    // Must return boolean value
    public List<List<String>> viewComments(String name){

        List Comments = new ArrayList<List>();

//        System.out.println("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");
//        System.out.println(name);
        // Create database reference object with read permission
        SQLiteDatabase db = getReadableDatabase();

        // Define a projection columns ( These are the columns will return from query )
        String[] projection = {
                DatabaseMaster.Comments.MOVIE_COMMENTS,
                DatabaseMaster.Comments.MOVIE_RATING
        };

        // Prepare where conditions to check username ( Value will be inject in later )
        String selection = DatabaseMaster.Comments.MOVIE_NAME + " = ?";

        // create array with given username. This array will be pass into query function
        String[] selectionArgs = { name };


        // create cursor object and store returned cursor from query function
        Cursor cursor =  db.query(
                DatabaseMaster.Comments.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        // Iterate through while loop and extract data from cursor and push necessary data into List Object
        while (cursor.moveToNext()){

            // crete List object to add single row data
            List singleComment = new ArrayList<List>();

            singleComment.add(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseMaster.Comments.MOVIE_COMMENTS)));
            singleComment.add(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseMaster.Comments.MOVIE_RATING)));

            // add singleComment object to comments list
            Comments.add(singleComment);
        }

        return  Comments;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
