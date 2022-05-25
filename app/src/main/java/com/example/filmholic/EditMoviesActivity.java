package com.example.filmholic;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class EditMoviesActivity extends AppCompatActivity {

    DataBaseHelper DB; //calling to database
    ListView listEditMOvie; //list view to display movie
    int movieIdIntent; //primary key of the movie
    String clickedMovie; //clicked movie name for the list view
    String nameIntent;  //name checking with clicked movie name
    String yearIntent; //year of the selected movie
    String directorIntent; //director of the selected movie
    String actorActressIntent; //actor actress list of the selected movie
    String ratingIntent; //rating of the selected movie
    String reviewIntent; //review of the selected movie
    String favIntent; //favourite status of the selected movie
    String nameIntentpass; //name of the selected movie

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_movies);

        DB = new DataBaseHelper(this);
        listEditMOvie = findViewById(R.id.EditList);
        ArrayList<String> EditMoviesArr = new ArrayList<String>(); //this array list use to for list view display movies

        Cursor res = DB.getMovieName();
        if (res.getCount() == 0) { //checking database is empty or not ,if database is empty showing alert

            AlertDialog alertDialog = new AlertDialog.Builder(EditMoviesActivity.this).create();
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

        while (res.moveToNext()) { //adding movie names to array list
            String name = res.getString(1);
            EditMoviesArr.add(name);

        }

        //provides adapter view
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1, EditMoviesArr);

        //setiing up list view
        listEditMOvie.setAdapter(adapter);

        listEditMOvie.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickedMovie = EditMoviesArr.get(position); //getting name of the clicked movie

                Cursor res = DB.getMovieName();
                while(res.moveToNext()){
                    nameIntent = res.getString(1);
                    if (nameIntent.equals(clickedMovie)) { //checking clicked movie name with database and if name in the database getting data from the relevant movie
                        movieIdIntent=res.getInt(0);
                        nameIntentpass=res.getString(1);
                        yearIntent = res.getString(2);
                        directorIntent = res.getString(3);
                        actorActressIntent = res.getString(4);
                        ratingIntent = res.getString(5);
                        reviewIntent = res.getString(6);
                        favIntent = res.getString(7);
                    }
                }

                //passing movie data to next intent as key and value pairs
                Intent intent = new Intent(EditMoviesActivity.this,EditMovieTempActivity.class);
                intent.putExtra("keymovieidintent",movieIdIntent);
                intent.putExtra("Keynameintent",nameIntentpass);
                intent.putExtra("Keyyearintent",yearIntent);
                intent.putExtra("Keydirectorintent",directorIntent);
                intent.putExtra("Keyactoractressintent",actorActressIntent);
                intent.putExtra("Keyratingintent",ratingIntent);
                intent.putExtra("Keyreviewintent",reviewIntent);
                intent.putExtra("Keyfavintent",favIntent);
                startActivity(intent);
            }
        });

    }

    //tutorial to pass data through intents
    //https://www.youtube.com/watch?v=Yi8mxXsroJ4


}