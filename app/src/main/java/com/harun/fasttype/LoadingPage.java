package com.harun.fasttype;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class LoadingPage extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3000;
    TextView textLoading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_page);
        textLoading = findViewById(R.id.txt_loading);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent loading = new Intent(LoadingPage.this,StartPage.class);
                startActivity(loading);
                finish();
            }
        },SPLASH_TIME_OUT);
        Animation animation = AnimationUtils.loadAnimation(LoadingPage.this,R.anim.blink_anim);
        textLoading.startAnimation(animation);


    }
}
