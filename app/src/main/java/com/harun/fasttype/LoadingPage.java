package com.harun.fasttype;

import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class LoadingPage extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 2000;
    TextView textLoading;
    ImageView imageLoading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_page);
        imageLoading = findViewById(R.id.img_Loading);
        textLoading = findViewById(R.id.txt_loading);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent loading = new Intent(LoadingPage.this,StartPage.class);
                startActivity(loading);
                finish();
            }
        },SPLASH_TIME_OUT);
        Animation animation = AnimationUtils.loadAnimation(LoadingPage.this,R.anim.rotate);
        imageLoading.startAnimation(animation);
        Animation animation2 = AnimationUtils.loadAnimation(LoadingPage.this,R.anim.blink_anim);
        textLoading.startAnimation(animation2);


    }
}
