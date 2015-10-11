package com.example.nour__000.popmovies;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by nour__000 on 10/10/2015.
 */
public class DbHelper extends SQLiteOpenHelper {
    public static String DatabaseName = "movies.db";
    public static int DatabaseVersion = 1;

    public DbHelper(Context context) {
        super(context, DatabaseName, null, DatabaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String mFavoriteTable = "CREATE TABLE " + Contract.Favorites.TableName + "(" +
                Contract.Favorites.ID + " INTEGER PRIMARY KEY," +
                Contract.Favorites.MOVIE_iD + " INTEGER  ," +
                Contract.Favorites.MOVIE_TITLE + " TEXT NOT NULL," +
                Contract.Favorites.MOVIE_DATE + " TEXT  ," +
                Contract.Favorites.MOVIE_OVERVIEW + " TEXT ," +
                Contract.Favorites.MOVIE_RATE + " FLOAT ," +
                Contract.Favorites.MOVIE_POSTER_PATH + " TEXT );";
        db.execSQL(mFavoriteTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Contract.Favorites.TableName);
        onCreate(db);
    }
}
