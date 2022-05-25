package com.example.filmholic;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

public class DisplayMovieActivity extends AppCompatActivity {

    DataBaseHelper DB; //calling to database
    ListView listViewAddFav; //listview to display movie names
    Button buttonAddFav; //button to add favourites
    ArrayList<String> checkedItem = new ArrayList<String>(); //this array list use to add checked items
    String fav= "Favourite"; //checked items should update with this string


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_movie);

        DB = new DataBaseHelper(this);
        listViewAddFav = findViewById(R.id.listViewAddF);
        buttonAddFav = findViewById(R.id.BtnAddFav);
        ArrayList<String> MovieNamesArr = new ArrayList<String>(); //this array list use to store all movies names for list view

        Cursor res = DB.getMovieName(); //calling to movie details to get from database
        if (res.getCount() == 0) { //checking database empty or not if database is empty showing error alert

            AlertDialog alertDialog = new AlertDialog.Builder(DisplayMovieActivity.this).create();
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
            MovieNamesArr.add(name);
        }

        //provides adapter view to list view with checkbox
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice,
                android.R.id.text1, MovieNamesArr);

        //setting up list view
        listViewAddFav.setAdapter(adapter);


        listViewAddFav.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected=((TextView)view).getText().toString();
                if(checkedItem.contains(selected)){
                    checkedItem.remove(selected); //unchecked items remove from the array
                }else {
                    checkedItem.add(selected); //checked items add to array
                }
            }
        });


        buttonAddFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog alertDialog = new AlertDialog.Builder(DisplayMovieActivity.this).create();
                alertDialog.setTitle("Here We Go");
                alertDialog.setMessage("Your Favourites Added");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();

                //updating favourite to checked movies
                for(int i=0;i<checkedItem.size();i++){
                    DB.updateFavourites(checkedItem.get(i),fav);
                }
            }
        });

    }

    //tutorial that i have used
    //https://www.youtube.com/watch?v=9t8VVWebRFM

    //listview tutorial with checkbox
    //https://www.youtube.com/watch?v=a-dvLs1g3Ec

}
