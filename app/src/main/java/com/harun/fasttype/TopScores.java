package com.harun.fasttype;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TopScores extends AppCompatActivity {
    ListView listView;
    SharedPreferences sharedPreferences;
    List<Score> scoreList;
    ScoreAdapter scoreAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_scores);
        scoreList = new ArrayList<>();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String jsonlist = sharedPreferences.getString("shScoreList","");
        Gson gson = new Gson();
        if (jsonlist.length() > 0){
            Type type = new TypeToken<List<Score>>(){}.getType();
            scoreList = gson.fromJson(jsonlist,type);
        }
        listView = findViewById(R.id.lv_screlist);
        scoreAdapter = new ScoreAdapter(this,scoreList);
        listView.setAdapter(scoreAdapter);
//        for (Score score:scoreList){
//            System.out.println(score.getScore());
//            System.out.println(score.getScoreDate());
//        }


    }
}
