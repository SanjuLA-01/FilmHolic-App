package com.example.filmholic;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SlashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slash);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SlashActivity.this,MainActivity.class); //slash activity
                startActivity(intent); //slashActivity on 3 seconds
                finish();
            }
        }, 4000);


        //https://www.youtube.com/watch?v=JLIFqqnSNmg

        ImageView imageView=findViewById(R.id.logoFH);
        Animation animation= AnimationUtils.loadAnimation(SlashActivity.this,R.anim.rotate_move_down); //animation
        imageView.startAnimation(animation);


    }
}