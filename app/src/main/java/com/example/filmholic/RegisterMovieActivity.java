package com.example.filmholic;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterMovieActivity extends AppCompatActivity {

    EditText movieName, movieYear, Director, actorsActresses, rating, review; //edit text to enter movie details
    Button saveBtn; //button to register a movie
    DataBaseHelper DB; //calling to database


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_movie);

        movieName = findViewById(R.id.editTextMovieName);
        movieYear = findViewById(R.id.editTextYear);
        Director = findViewById(R.id.editTextDirectorName);
        actorsActresses = findViewById(R.id.editTeaxtListOfNames);
        rating = findViewById(R.id.editTeastRating);
        review = findViewById(R.id.editTextReview);

        saveBtn = findViewById(R.id.submitRegisterBtn);

        DB = new DataBaseHelper(this);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String movieNameTXT = movieName.getText().toString();
                String movieYearTXT = movieYear.getText().toString();
                String directorTXT = Director.getText().toString();
                String actorActressesTXT = actorsActresses.getText().toString();
                String ratingTXT = rating.getText().toString();
                String reviewTXT = review.getText().toString();
                String favouriteAuto = "Not Favourite"; //at the beginning all movies saved in database with 'Not Favourite'

                if(!movieYearTXT.matches("[0-9]+")){ //regex numbers only for year

                    AlertDialog alertDialog = new AlertDialog.Builder(RegisterMovieActivity.this).create();
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage("Integers Only for Year");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();

                }else {

                    if(!ratingTXT.matches("[0-9]+")){ //regex numbers only for rating

                        AlertDialog alertDialog = new AlertDialog.Builder(RegisterMovieActivity.this).create();
                        alertDialog.setTitle("Error");
                        alertDialog.setMessage("Integers Only for Rating");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    }else {

                        int yearValidation = Integer.parseInt(movieYearTXT); //convert year to integer for validations
                        int ratingValidation = Integer.parseInt(ratingTXT); //convert rating to integer for validations

                        if (yearValidation < 1895) { //checking if year is greater than 1895

                            AlertDialog alertDialog = new AlertDialog.Builder(RegisterMovieActivity.this).create();
                            alertDialog.setTitle("Error");
                            alertDialog.setMessage("Please Enter Valid Year \n Year Should be greater than 1895");
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();

                        } else {
                            if (10 >= ratingValidation && ratingValidation >= 1) { //checking if rating is between number of 1 -10

                                //if all ok then adding details to database
                                Boolean checkInsertDetails = DB.insertMoviedetails(movieNameTXT, movieYearTXT, directorTXT, actorActressesTXT, ratingTXT, reviewTXT, favouriteAuto);

                                if (checkInsertDetails == true) {

                                    AlertDialog alertDialog = new AlertDialog.Builder(RegisterMovieActivity.this).create();
                                    alertDialog.setTitle("Here We Go");
                                    alertDialog.setMessage("New Movie Registered Thank You ");
                                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            });
                                    alertDialog.show();

                                } else {

                                    AlertDialog alertDialog = new AlertDialog.Builder(RegisterMovieActivity.this).create();
                                    alertDialog.setTitle("Error");
                                    alertDialog.setMessage("New Movie Not Registered");
                                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            });
                                    alertDialog.show();
                                }
                            } else {
                                AlertDialog alertDialog = new AlertDialog.Builder(RegisterMovieActivity.this).create();
                                alertDialog.setTitle("Error");
                                alertDialog.setMessage("Please Enter Valid Rating \n Rating Should be between (1 - 10) ");
                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                alertDialog.show();
                            }
                        }
                    }
                }
            }

        });

    }

    //tutorial that i have used
    //https://www.youtube.com/watch?v=9t8VVWebRFM

}