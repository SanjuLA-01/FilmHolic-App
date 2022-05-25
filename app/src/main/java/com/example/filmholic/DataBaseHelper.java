package com.example.filmholic;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {
    public DataBaseHelper(Context context) {
        super(context,"movieData.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table Moviedetails (movieId INTEGER primary key AUTOINCREMENT ,movieName TEXT,movieYear TEXT,Director TEXT,actorsActresses TEXT,rating TEXT,review TEXT,favorites TEXT)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase DB, int oldVersion, int newVersion) {
        DB.execSQL("drop Table if exists Moviedetails");
    }

    //inserting data to database
    public Boolean insertMoviedetails(String movieName,String movieYear, String Director, String actorsActresses, String rating,String review,String favorites){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("movieName",movieName);
        contentValues.put("movieYear",movieYear);
        contentValues.put("Director",Director);
        contentValues.put("actorsActresses",actorsActresses);
        contentValues.put("rating",rating);
        contentValues.put("review",review);
        contentValues.put("favorites",favorites);
        long result= DB.insert("Moviedetails",null,contentValues);
        if(result == -1){
            return false;
        }else {
            return true;
        }
    }

    //get movie details from database alphabetical order
    public Cursor getMovieName(){
        SQLiteDatabase DB= this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Moviedetails ORDER BY movieName ",null);
        return cursor;
    }

    //updating favourite or not
    public Boolean updateFavourites(String movieName,String favorites){
        SQLiteDatabase DB=this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("favorites",favorites);
        Cursor cursor =DB.rawQuery("Select * from Moviedetails where movieName = ?",new String[]{movieName});
        if(cursor.getCount()>0){
            long result=DB.update("Moviedetails",contentValues,"movieName = ?",new String []{movieName});
            if(result==-1){
                return false;
            }
            else{
                return true;
            }
        }else{
            return false;
        }

    }

    //updating every data in database
    public Boolean updateEveryDetail(int movieId,String movieName,String movieYear, String Director, String actorsActresses, String rating,String review,String favorites){
        SQLiteDatabase DB=this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("movieName",movieName);
        contentValues.put("movieYear",movieYear);
        contentValues.put("Director",Director);
        contentValues.put("actorsActresses",actorsActresses);
        contentValues.put("rating",rating);
        contentValues.put("review",review);
        contentValues.put("favorites",favorites);

        Cursor cursor =DB.rawQuery("Select * from Moviedetails where movieId = ?",new String[]{String.valueOf(movieId)});
        if(cursor.getCount()>0){
            long result=DB.update("Moviedetails",contentValues,"movieId = ?",new String []{String.valueOf(movieId)});
            if(result==-1){
                return false;
            }
            else{
                return true;
            }
        }else{
            return false;
        }

    }

    //search data from database
    public ArrayList<String> searchDetails(){
        SQLiteDatabase DB=this.getReadableDatabase();
        Cursor cursor=DB.rawQuery("Select * from Moviedetails",null);
        ArrayList<String> searchData = new ArrayList<>();
        while(cursor.moveToNext()){
            String mname=cursor.getString(1);
            String mdirector=cursor.getString(3);
            String mactorlist=cursor.getString(4);

            String strr=(mname+ "\n" +mdirector+"\n"+mactorlist);
            searchData.add(strr.toLowerCase());
        }
        return searchData;
    }

    //tutorial that i have used
    //https://www.youtube.com/watch?v=9t8VVWebRFM
}
