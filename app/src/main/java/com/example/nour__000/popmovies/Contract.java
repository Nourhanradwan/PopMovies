package com.example.nour__000.popmovies;

import android.provider.BaseColumns;

/**
 * Created by nour__000 on 10/10/2015.
 */
public class Contract {
    public static class Favorites implements BaseColumns {

        public static String TableName = "favorites";
        public static String ID = "_id";
        public static String MOVIE_iD = "movie_id";
        public static String MOVIE_TITLE = "title";
        public static String MOVIE_DATE = "date";
        public static String MOVIE_RATE = "rate";
        public static String MOVIE_POSTER_PATH = "poster_path";
        public static String MOVIE_OVERVIEW = "overview";
        public static String FAVOURITE = "favourite";
        public static String[] AllColumns = new String[]{
                ID,
                MOVIE_iD,
                MOVIE_TITLE,
                MOVIE_OVERVIEW,
                MOVIE_POSTER_PATH,
                MOVIE_DATE,
                MOVIE_RATE
        };


    }
}
