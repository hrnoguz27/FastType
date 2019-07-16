package com.harun.fasttype;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class StartPage extends AppCompatActivity {
    private static final String TAG = "StartPage";
    private ArrayList<String> lNames = new ArrayList<>();
    private ArrayList<String> lShortNames = new ArrayList<>();
    private ArrayList<Integer> limagesUrls = new ArrayList<>();
    static String selectedlang = "";
    ImageButton startBtn,rate,more,instagram,twitter,snapchat,linkedin;
    Button lastScore;
    TextView passing;
    boolean doubleBackToExitPressedOnce = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);
        startBtn = findViewById(R.id.btn_start);
        lastScore = findViewById(R.id.btn_spScore);
//        lastScore = findViewById(R.id.btn_lastScores);
//        rate = findViewById(R.id.btn_rate);
//        more = findViewById(R.id.btn_more);
        passing = findViewById(R.id.pass);
        passing.setText("   <    >");
        getImages();
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedlang == "") {
                    Toast.makeText(getApplicationContext(), "Please Select Language", Toast.LENGTH_LONG).show();

                } else {
                    Intent setLang = new Intent(getApplicationContext(), MainActivity.class);
                    setLang.putExtra("selectedLang", selectedlang);
                    startActivity(setLang);
                }
            }
        });
        lastScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent score = new Intent(StartPage.this,LastScore.class);
                startActivity(score);
            }
        });
//        rate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //goToUrl("");
//            }
//        });
//        more.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //goToUrl("");
//            }
//        });
    }
    @Override
    public void onBackPressed() {

        if(doubleBackToExitPressedOnce){
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "tap one more to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
        // super.onBackPressed();



    }
    private void getImages(){
        Log.d(TAG,"initImagesBitmaps: preparing bitmaps");

        limagesUrls.add(R.mipmap.england);
        lNames.add("English");
        lShortNames.add("EN");

        limagesUrls.add(R.mipmap.turkish);
        lNames.add("Türkçe");
        lShortNames.add("TR");

        limagesUrls.add(R.mipmap.russia);
        lNames.add("Pусский");
        lShortNames.add("RU");

        limagesUrls.add(R.mipmap.germany);
        lNames.add("Deutsch");
        lShortNames.add("DE");

        limagesUrls.add(R.mipmap.french);
        lNames.add("Français");
        lShortNames.add("FR");

        limagesUrls.add(R.mipmap.italy);
        lNames.add("Italiano");
        lShortNames.add("IT");

        limagesUrls.add(R.mipmap.united_arab_emirates);
        lNames.add("العربية");
        lShortNames.add("AR");

        limagesUrls.add(R.mipmap.india);
        lNames.add("हिन्दी");
        lShortNames.add("HI");

        limagesUrls.add(R.mipmap.japan);
        lNames.add("日本の");
        lShortNames.add("JA");

        limagesUrls.add(R.mipmap.china);
        lNames.add("中国");
        lShortNames.add("ZH");

        initRecyclerView();
    }
    private void initRecyclerView(){
        Log.d(TAG,"initRecyclerview: init recyclerview");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        RecyclerView recyclerView = findViewById(R.id.Lrecyclerview);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getApplicationContext(),lNames,lShortNames,limagesUrls);
        recyclerView.setAdapter(adapter);
    }
    private void goToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        Intent WebView = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(WebView);
    }
}
