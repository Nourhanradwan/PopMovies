package com.example.nour__000.popmovies;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

/**
 * Created by Omar on 05/10/2015.
 */
public class MovieDB extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "PopularMoviesDB.db";
    private static final String TABLE_NAME = "movies";
    private static final String TRAILERS_TABLE = "trailers";
    private static final String REVIEWS_TABLE = "reviews";


    private Context context;
    //private static final Uri CONTENT_URI =Uri.parse("content://"+"com.example.omar.popularmoviesapp");

    public MovieDB(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //create statement of SQL
        //db = db.openOrCreateDatabase(DATABASE_NAME, null);
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (id TEXT PRIMARY KEY, thumb TEXT NOT NULL, title TEXT NOT NULL, description TEXT NOT NULL, date TEXT NOT NULL, vote TEXT NOT NULL, duration TEXT NOT NULL, isFavorite INTEGER NOT NULL);");
        db.execSQL("CREATE TABLE " + TRAILERS_TABLE + " (id TEXT NOT NULL, key TEXT NOT NULL, name TEXT NOT NULL);");
        db.execSQL("CREATE TABLE " + REVIEWS_TABLE + " (id TEXT NOT NULL, author TEXT NOT NULL, content TEXT NOT NULL);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertRow(String id, String thumb, String title, String description, String date, String vote, String duration, int isFavorite){


        // First, get the DB from this class to write in it:
        SQLiteDatabase db = this.getWritableDatabase();

        //OPTION 1:
        //ContentValues is obj holds a row values to be stored in columns:
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("thumb", thumb);
        contentValues.put("title",title);
        contentValues.put("description",description);
        contentValues.put("date",date);
       // contentValues.put("vote",vote);
        contentValues.put("duration",duration);
        contentValues.put("isFavorite", isFavorite);

        //Insert data into the database by passing a ContentValues object to the insert() method:
        db.insertOrThrow(TABLE_NAME, null, contentValues);

    }

    public Cursor getFavoritesTable(){

        //get the DB from this class to read it:
        SQLiteDatabase db = this.getReadableDatabase();
        //use "query" to get the data,
        //The results of the query are returned to you in a Cursor object:
        // Define a projection that specifies which columns from the database you will actually use after this query.
        //String[] projection = {"movieID","movieThumb"};

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE isFavorite == 1;", null);
        return cursor;

    }

    public boolean isDuplicatedInFavorites (String id){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE id == " + id + " AND isFavorite == 1;", null);
        return !(cursor.getCount()<=0);
    }

    public boolean isDuplicatedInGeneral (String id){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE id == " + id + ";", null);
        return !(cursor.getCount()<=0);
    }

    public Cursor getRowById (String id){

        //get the DB from this class to read it:
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE id == " + id + ";", null);
        return cursor;
    }

    public void updateFavoriteById (String id, int isFavorite) {

        SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("UPDATE " + TABLE_NAME + " SET isFavorite = " + isFavorite + " WHERE id == " + id + ";");
    }

    public void deleteRow (String id){

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE id == " + id + ";");
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public int numberOfFavoriteRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        return (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME, "isFavorite == 1");
    }

    public void setTrailersById (String id, String[] names, String[] keys){

        SQLiteDatabase db = this.getWritableDatabase();
        for (int i=0; i<names.length;i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("id", id);
            contentValues.put("name", names[i]);
            contentValues.put("key", keys[i]);
            db.insertOrThrow(TRAILERS_TABLE, null, contentValues);
            contentValues.clear();
        }
    }

    public Cursor getTrailersById (String id){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TRAILERS_TABLE + " WHERE id == " + id + ";", null);
        return cursor;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public int numberOfTrailersRows(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        return (int) DatabaseUtils.queryNumEntries(db, TRAILERS_TABLE, "id == " + id);
    }

    public void setReviewsById (String id, String[] authors, String[] contents){

        SQLiteDatabase db = this.getWritableDatabase();
        for (int i=0; i<authors.length;i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("id", id);
            contentValues.put("author", authors[i]);
            contentValues.put("content", contents[i]);
            db.insertOrThrow(REVIEWS_TABLE, null, contentValues);
        }
    }

    public Cursor getReviewsById (String id){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + REVIEWS_TABLE + " WHERE id == " + id + ";", null);
        return cursor;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public int numberOfReviewsRows(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        return (int) DatabaseUtils.queryNumEntries(db, REVIEWS_TABLE, "id == " + id);
    }
}
