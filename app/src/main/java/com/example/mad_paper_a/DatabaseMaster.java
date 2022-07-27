package com.example.mad_paper_a;

import android.provider.BaseColumns;

// Question 02 - a
// Create a class named Database Master
public class DatabaseMaster {

    // Question 02 - a
    // Implement the class default constructor
    private DatabaseMaster() {
    }

    // Question 02 - b
    // Create an inner class called Users
    // Users class is inner class of DatabaseMaster, because Users class created inside of DatabaseMaster class
  public static class Users implements BaseColumns {

        // Question 02 - b
        // Define columns as constant attributes
        public static final String TABLE_NAME = "users";
        public static final String USER_NAME = "username";
        public static final String PASSWORD = "password";
        public static final String USER_TYPE = "usertype";
  }

    // Question 02 - c
    // Create an inner class called Movie
    public static class Movie implements BaseColumns {

        // Question 02 - c
        // Define columns as constant attributes
        public static final String TABLE_NAME = "movie";
        public static final String MOVIE_NAME = "moviename";
        public static final String MOVIE_YEAR = "movieyear" ;

    }

    // Question 02 - d
    // Create an inner class called Comments
    public static class Comments implements BaseColumns {

        // Question 02 - d
        // Define columns as constant attributes
        public static final String TABLE_NAME = "comments";
        public static final String MOVIE_NAME = "moviename";
        public static final String MOVIE_RATING= "movierating" ;
        public static final String MOVIE_COMMENTS= "moviecomments" ;

    }
}
