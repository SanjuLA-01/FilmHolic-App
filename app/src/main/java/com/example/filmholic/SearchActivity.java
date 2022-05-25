package com.example.filmholic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    DataBaseHelper DB; //calling to database
    EditText searchTXT; //edit text to enter what user want to search
    Button searchBTN; //button to lookup
    ListView searchLIST; //list view to display relevant items with search
    int count=0; //searched counts
    ArrayList<String> readMovieArr=new ArrayList<>(); //this array list use to take return array list form the database helper
    ArrayList<String> relaventSearchArr=new ArrayList<>(); //this array list use to put values that user have searched
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        DB = new DataBaseHelper(this);
        searchTXT=findViewById(R.id.editSearchText);
        searchBTN=findViewById(R.id.lookupSearchBtn);
        searchLIST=findViewById(R.id.SearchList);

        readMovieArr=DB.searchDetails(); //getting return array list from database helper

        searchBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count>=1){ //if checked times greater or equal to one array list should be clear
                    relaventSearchArr.clear();
                }
                String searchStringEnter=searchTXT.getText().toString().toLowerCase();
                for(int  i=0; i<readMovieArr.size();i++){ //checking user entered string
                    if(readMovieArr.get(i).contains(searchStringEnter)){
                        relaventSearchArr.add(readMovieArr.get(i));
                    }
                }
                DisplaySearch();
                count++;
            }
        });

    }

    private void DisplaySearch(){ //setting array adapter with list view
        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,relaventSearchArr);
        searchLIST.setAdapter(adapter);
    }
}