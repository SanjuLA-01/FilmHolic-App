package com.example.filmholic;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RatingBar;

public class EditMovieTempActivity extends AppCompatActivity {
    DataBaseHelper DB; //calling to database
    EditText mName; //edit text to get name
    EditText mYear; //edit text to get year
    EditText mDirector; //edit text to get director
    EditText mActorActresses; //edit text to get actor actress names
    EditText mReview; //edit text to get review
    RatingBar rating; //rating to get rating
    RadioButton yesBtn; //radio button to put yes for favourite
    RadioButton noBtn; //radio button to put no for favourite
    Button updateBtn; //update button to update database
    String favTXT; //string to favourite or not
    String mIntentName; //string to movie name
    int mIntentId; //primary key of the movie

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_movie_temp);

        DB = new DataBaseHelper(this);
        mName = findViewById(R.id.editTextMovieNameTemp);
        mYear = findViewById(R.id.editTextYearTemp);
        mDirector = findViewById(R.id.editTextDirectorNameTemp);
        mActorActresses = findViewById(R.id.editTeaxtListOfNamesTemp);
        mReview = findViewById(R.id.editTextReviewTemp);
        rating = findViewById(R.id.ratingBarTemp);
        yesBtn = findViewById(R.id.radioButtonYes);
        noBtn = findViewById(R.id.radioButtonNo);
        updateBtn = findViewById(R.id.submitRegisterBtnTemp);


        mIntentId = getIntent().getIntExtra("keymovieidintent",0); //getting movie id from first intent
        mIntentName = getIntent().getStringExtra("Keynameintent"); //getting movie name from first intent
        String mIntentYear = getIntent().getStringExtra("Keyyearintent");
        String mIntentDirector = getIntent().getStringExtra("Keydirectorintent");
        String mIntentActorList = getIntent().getStringExtra("Keyactoractressintent");
        String mIntentRating = getIntent().getStringExtra("Keyratingintent");
        String mIntentReview = getIntent().getStringExtra("Keyreviewintent");
        String mIntentFav = getIntent().getStringExtra("Keyfavintent");

        int RatingValid = Integer.parseInt(mIntentRating); //convert integer rating

        //setting edit text and rating with got details from first intent
        mName.setText(mIntentName);
        mYear.setText(mIntentYear);
        mDirector.setText(mIntentDirector);
        mActorActresses.setText(mIntentActorList);
        mReview.setText(mIntentReview);
        rating.setRating(RatingValid);

        if (mIntentFav.equals("Favourite")) { //checking favourite status is Favourite
            yesBtn.setChecked(true); //setting radio button
            favTXT = "Favourite"; //setting favourite according to details
        }
        if (mIntentFav.equals("Not Favourite")) { //checking favourite status Not Favourite
            noBtn.setChecked(true); //setting radio button
            favTXT = "Not Favourite"; //setting not favourite according to detail
        }

        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //if yes button click NO button should not checked and 'Favourite' should pass to the string
                noBtn.setChecked(false);
                favTXT = "Favourite";
            }
        });

        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //if yes button click YES button should not checked and 'Not Favourite' should pass to the string
                yesBtn.setChecked(false);
                favTXT = "Not Favourite";
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getting details from the edit texts
                String mNameTXT = mName.getText().toString();
                String mYearTXT = mYear.getText().toString();
                String mDirectorTXT = mDirector.getText().toString();
                String mActorListTXT = mActorActresses.getText().toString();
                String mReviewTXT = mReview.getText().toString();
                String mRatingTXT = String.valueOf(Math.round(rating.getRating())); //rating

                if(!mYearTXT.matches("[0-9]+")){ //regex for numbers only

                    AlertDialog alertDialog = new AlertDialog.Builder(EditMovieTempActivity.this).create();
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

                    int yearValidation = Integer.parseInt(mYearTXT); //year convert integer for validations

                    if (yearValidation < 1895) { //checking year should be greater than 1895
                        AlertDialog alertDialog = new AlertDialog.Builder(EditMovieTempActivity.this).create();
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
                        AlertDialog alertDialog = new AlertDialog.Builder(EditMovieTempActivity.this).create();
                        alertDialog.setTitle("Here We Go");
                        alertDialog.setMessage("Movie Details Updated \n Thank You ");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();

                        //updating details of the movie
                        DB.updateEveryDetail(mIntentId, mNameTXT, mYearTXT, mDirectorTXT, mActorListTXT, mRatingTXT, mReviewTXT, favTXT);
                    }
                }

            }
        });

    }

    //tutorial to get data from another intent
   // https://www.youtube.com/watch?v=Yi8mxXsroJ4
}