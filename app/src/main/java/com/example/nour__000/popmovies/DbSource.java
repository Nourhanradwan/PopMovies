package com.example.nour__000.popmovies;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nour__000 on 10/10/2015.
 */
public class DbSource {
    SQLiteDatabase db;
    DbHelper dbhelper;

    public DbSource(Context context) {
        dbhelper = new DbHelper(context);
        db = dbhelper.getWritableDatabase();

    }

    public boolean addmovie(int id, String title, String date, String overview, float rate, String posterPath) {
        boolean check = false;
        ContentValues values = new ContentValues();
        values.put(Contract.Favorites.MOVIE_iD, id);
        values.put(Contract.Favorites.MOVIE_TITLE, title);
        values.put(Contract.Favorites.MOVIE_OVERVIEW, overview);
        values.put(Contract.Favorites.MOVIE_DATE, date);
        values.put(Contract.Favorites.MOVIE_RATE, rate);
        values.put(Contract.Favorites.MOVIE_POSTER_PATH, posterPath);
        long returnedId = db.insert(Contract.Favorites.TableName, Contract.Favorites.ID, values);
        if (id > -1)
            check = true;
        return check;

    }

    public boolean unfavorite(int id) {
        boolean check = false;
        long deletedId = db.delete(Contract.Favorites.TableName, Contract.Favorites.MOVIE_iD + "=?", new String[]{String.valueOf(id)});
        if (deletedId > 0)
            check = true;
        return check;
    }

    public List<Response.ResultsEntity> allFavs() {
        List<Response.ResultsEntity> posters = new ArrayList<>();
        Cursor cursor = db.query(Contract.Favorites.TableName, new String[]{Contract.Favorites.MOVIE_POSTER_PATH}, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            while (cursor.moveToNext()) {
                Response.ResultsEntity poster = new Response.ResultsEntity();
                poster.setPoster_path(cursor.getString(cursor.getColumnIndex(Contract.Favorites.MOVIE_POSTER_PATH)));
                posters.add(poster);

            }
        }
        return posters;
    }

}
