package com.example.filmholic;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class RatingACtivity extends AppCompatActivity {

    DataBaseHelper DB; //calling to database
    ListView ratingList; //list view display movies in database
    Button findMovieBtn; //button to find rating of the movie

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_a_ctivity);

        DB = new DataBaseHelper(this);
        ratingList=findViewById(R.id.ratingList);
        findMovieBtn=findViewById(R.id.FindMovieBtn);
        ArrayList<String> movieNamesArray=new ArrayList<>();

        Cursor res = DB.getMovieName(); //calling to movie details to get from database
        if (res.getCount() == 0) { //checking database empty or not if database is empty showing error alert

            AlertDialog alertDialog = new AlertDialog.Builder(RatingACtivity.this).create();
            alertDialog.setTitle("Error");
            alertDialog.setMessage("Nothing To Show");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();

            return;
        }

        while (res.moveToNext()) { //getting movie names from database
            String name = res.getString(1);
            movieNamesArray.add(name);
        }

        //provides adapter view to list view with checkbox
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_single_choice,
                android.R.id.text1, movieNamesArray);

        //setting up list view
        ratingList.setAdapter(adapter);


    }
}