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
import android.widget.Toast;

import java.util.ArrayList;

public class FavouritesActivity extends AppCompatActivity {

    DataBaseHelper DB; //calling to database
    ListView listViewRemoveFav; //list view to display favourites movies
    Button buttonRemoveFav; //button to remove the favorites from database
    ArrayList<String> checkedItem = new ArrayList<String>(); //this array list is use to store checked items
    String fav="Not Favourite"; //string that should save after the remove favourite from database

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        DB = new DataBaseHelper(this);
        listViewRemoveFav = findViewById(R.id.listRemoveFav);
        buttonRemoveFav = findViewById(R.id.removeFavBtn);
        ArrayList<String> FavMovieNamesArr = new ArrayList<String>(); //this array list use to display movies in list view

        Cursor res = DB.getMovieName(); //calling to movie details to get from database
        if (res.getCount() == 0) { //checking database empty or not, if empty showing error alert

            AlertDialog alertDialog = new AlertDialog.Builder(FavouritesActivity.this).create();
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

        while (res.moveToNext()) { //getting movie names from database and added them to array list if their favorite status is 'Favourites'
            String name = res.getString(1);
            String favRes = res.getString(7);
            if(favRes.equals("Favourite")){
                FavMovieNamesArr.add(name);
            }
        }

        //provides adapter view to list view with checkbox
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice,
                android.R.id.text1, FavMovieNamesArr);

        listViewRemoveFav.setAdapter(adapter);

        //to get initially checked checkbox
        for(int i =0; i<FavMovieNamesArr.size();i++){
            listViewRemoveFav.setItemChecked(i,true);
        }

        listViewRemoveFav.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected=((TextView)view).getText().toString();
                if(checkedItem.contains(selected)){
                    checkedItem.remove(selected); //remove unchecked items from array
                }else {
                    checkedItem.add(selected); //add clicked items to array
                }
            }
        });

        buttonRemoveFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog alertDialog = new AlertDialog.Builder(FavouritesActivity.this).create();
                alertDialog.setTitle("Here We Go");
                alertDialog.setMessage("Your Favourites Updated");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();

                //add 'Not Favourite' to unchecked items
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