package com.harun.fasttype;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class StartPage extends AppCompatActivity {
    private static final String TAG = "StartPage";
    private ArrayList<String> lNames = new ArrayList<>();
    private ArrayList<String> lShortNames = new ArrayList<>();
    private ArrayList<Integer> limagesUrls = new ArrayList<>();
    static String selectedlang = "";
    ImageButton startBtn;
    boolean doubleBackToExitPressedOnce = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);
        startBtn = findViewById(R.id.btn_start);
        getImages();


        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedlang == ""){
                    Toast.makeText(getApplicationContext(),"Lütfen Dil Seçiniz",Toast.LENGTH_LONG).show();

                }else {
                Intent setLang = new Intent(getApplicationContext(),MainActivity.class);
                setLang.putExtra("selectedLang",selectedlang);
                startActivity(setLang);
                }
            }
        });




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

        limagesUrls.add(R.mipmap.germany);
        lNames.add("Deutsch");
        lShortNames.add("DE");

        limagesUrls.add(R.mipmap.french);
        lNames.add("Français");
        lShortNames.add("FR");

        limagesUrls.add(R.mipmap.italy);
        lNames.add("Italiano");
        lShortNames.add("IT");

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
}
