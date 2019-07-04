package com.harun.fasttype;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class StartPage extends AppCompatActivity {
    private static final String TAG = "StartPage";
    private ArrayList<String> lNames = new ArrayList<>();
    private ArrayList<Integer> limagesUrls = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);
        getImages();
    }
    private void getImages(){
        Log.d(TAG,"initImagesBitmaps: preparing bitmaps");
        limagesUrls.add(R.mipmap.england);
        lNames.add("English");

        limagesUrls.add(R.mipmap.turkish);
        lNames.add("Türkçe");

        limagesUrls.add(R.mipmap.germany);
        lNames.add("Deutsch");

        limagesUrls.add(R.mipmap.french);
        lNames.add("Français");

        limagesUrls.add(R.mipmap.italy);
        lNames.add("Italiano");

        limagesUrls.add(R.mipmap.japan);
        lNames.add("日本の");

        limagesUrls.add(R.mipmap.china);
        lNames.add("中国");
        System.out.println(limagesUrls.get(2));
        System.out.println(lNames.get(2));

        initRecyclerView();
    }
    private void initRecyclerView(){
        Log.d(TAG,"initRecyclerview: init recyclerview");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        RecyclerView recyclerView = findViewById(R.id.Lrecyclerview);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getApplicationContext(),lNames,limagesUrls);
        recyclerView.setAdapter(adapter);

    }
}
