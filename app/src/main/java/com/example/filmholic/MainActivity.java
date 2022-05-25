package com.example.filmholic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //button to register movie
        Button registerMovieBtn= findViewById(R.id.RegisterNameBtn);
        registerMovieBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerMovie();
            }
        });

        //button to  display movies
        Button DisplayMovieBtn= findViewById(R.id.displayMovieBtn);
        DisplayMovieBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayMovie();
            }
        });

        //button to favourites
        Button addToFavBtn = findViewById(R.id.FavouriteBtn);
        addToFavBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToFav();
            }
        });

        //button to edit movies
        Button editMoviesBtn = findViewById(R.id.EditMoviesBtn);
        editMoviesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editMovies();
            }
        });

        //button to search movies
        Button searchBtn = findViewById(R.id.SearchBtn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });

        //button to rating
        Button ratingsBtn = findViewById(R.id.RatingsBtn);
        ratingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rating();
            }
        });
    }

    private void registerMovie(){
        Intent intent = new Intent(this,RegisterMovieActivity.class);
        startActivity(intent);
    }

    private void displayMovie(){
        Intent intent = new Intent(this,DisplayMovieActivity.class);
        startActivity(intent);
    }

    private void addToFav(){
        Intent intent = new Intent(this,FavouritesActivity.class);
        startActivity(intent);
    }

    private void editMovies(){
        Intent intent = new Intent(this,EditMoviesActivity.class);
        startActivity(intent);
    }

    private void search(){
        Intent intent = new Intent(this,SearchActivity.class);
        startActivity(intent);
    }


    private void rating(){
        Intent intent = new Intent(this,RatingACtivity.class);
        startActivity(intent);
    }
}