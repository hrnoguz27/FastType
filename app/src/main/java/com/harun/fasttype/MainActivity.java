package com.harun.fasttype;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    // Defining Items
    private static final String TAG = "";
    TextView mainword, tvcounter;
    EditText et_word;
    ImageButton btn_start;
    String str_score,sLanguage,textFileName;
    ArrayList<String> wordlist;
    int rnd_mainword,scoreTrueCount=0,scoreFalseCount=0,adscount=0;
    CircularProgressBar circularProgressBar;
    Dialog finishDialog;
    SharedPreferences sharedPreferences;
    private AdView mAdView;
    private InterstitialAd interstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        // Added Main Activity Banner Ads
        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        // Added Main Activity Interstitial Ads

        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        interstitialAd.loadAd(new AdRequest.Builder().build());

        interstitialAd.setAdListener(new AdListener()
            {
                @Override
                public void onAdClosed(){
                    finishDialog.dismiss();
                    interstitialAd.loadAd(new AdRequest.Builder().build());
                }
            }
        );

        // Found Items With id
        mainword = findViewById(R.id.tv_main);
        et_word = findViewById(R.id.et_word);
        tvcounter = findViewById(R.id.tv_counter);
        btn_start = findViewById(R.id.btn_start);
        tvcounter.setVisibility(View.GONE);
        finishDialog = new Dialog(this);
        wordlist = new ArrayList<>();
        Intent getLang = getIntent();
        circularProgressBar = findViewById(R.id.progress_circular);

        // Selecting TextFileName
        textFileName=null;
        sLanguage = getLang.getStringExtra("selectedLang");
        System.out.println(sLanguage);
        textFileName = "words"+sLanguage+".txt";

        // Reading Data from Text File
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(


                    new InputStreamReader(getAssets().open(textFileName), "UTF-8"));
            // do reading, usually loop until end of file reading
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                //process line
                wordlist.add(mLine.trim().toLowerCase());

            }
        } catch (IOException e) {
            Log.d(TAG,"Unable to select text file");
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
        // Keyboard opening automatically
        et_word.requestFocus();
        // tvcouter visible = false

        tvcounter.setVisibility(View.INVISIBLE);
        et_word.setHint("Start Type");
    }
    public void start(final View view) {
        // Keyboard turns on automatically
        et_word.requestFocus();
        et_word.setHint("");
        mainword.setText("Main Word");
        // tvcouter visible = true
        tvcounter.setVisibility(View.VISIBLE);
        // btn_start visible = false
        btn_start.setVisibility(View.GONE);
        //btn_start enable = false
        btn_start.setEnabled(false);
        // 60 seconds Timer with animation
        final int[] animationDuration = {10000}; // 60000ms = 60s
        circularProgressBar.setProgressWithAnimation(100, animationDuration[0]); // Default duration = 1500ms
        new CountDownTimer(animationDuration[0], 1000) {

            // When the Timer start
            @Override
            public void onTick(long millisUntilFinished) {

                tvcounter.setText(String.valueOf(millisUntilFinished / 1000));
                // System.out.println(millisUntilFinished);

                // coloring to animation with 'colorful' function
                colorful(millisUntilFinished);
            }
            // When the timer stop
            @Override
            public void onFinish()  {
                // circular animation going to beginnig
                circularProgressBar.setProgressWithAnimation(0);
                // Keyboard turns off automatically with closekeyboard function
                closekeyboard();
                mainword.setText("Main Word");
                et_word.setText("");
                // User is blocked from typing
                et_word.setEnabled(false);
                //btn_start enable = true
                btn_start.setEnabled(true);
                //btn_start visible = true
                btn_start.setVisibility(View.VISIBLE);
                finishDialog.setContentView(R.layout.skordialog);
                System.out.println(String.valueOf(scoreTrueCount) + " TRUE");
                System.out.println(String.valueOf(scoreFalseCount) + " FALSE");
                // Defining dialog items
                Button restart,shareScore,gToMenu,btnLastScore;
                TextView tv_scoredialog,tv_truescoredialog,tv_falsescoredialog;

                // Found Dialog Items With id
                shareScore = finishDialog.findViewById(R.id.shareScore);
                btnLastScore = finishDialog.findViewById(R.id.btn_lastScores);
                restart = finishDialog.findViewById(R.id.restart);
                gToMenu = finishDialog.findViewById(R.id.gToMenu);
                tv_scoredialog = finishDialog.findViewById(R.id.txt_score);
                tv_truescoredialog = finishDialog.findViewById(R.id.txt_scoretrue);
                tv_falsescoredialog = finishDialog.findViewById(R.id.txt_scorefalse);
                tv_scoredialog.setText("Score: " + String.valueOf(scoreTrueCount+scoreFalseCount) + " WPM");
                tv_truescoredialog.setText(String.valueOf(scoreTrueCount) + " TRUE");

                tv_falsescoredialog.setText(String.valueOf(scoreFalseCount) + " FALSE");

                // Click events of  dialog buttons are written
                restart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adscount++;
                        et_word.setHint("Try Fast");
                        et_word.setEnabled(true);
                        if(adscount%2==0){
                            if(interstitialAd.isLoaded()){
                                interstitialAd.show();
                            }else{
                                finishDialog.dismiss();
                                //startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            }
                        }else{
                            finishDialog.dismiss();
                        }


                    }
                });
                shareScore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent share = new Intent(Intent.ACTION_SEND);
                        share.setType("text/plain");
                        String shareBody = "MY SCORE IS "+ str_score +" TRUE WORD PER MIN\n"+"If you believe, you can type faster, challenge me. " +
                                "Share your score and join this race. #fasttype ";
//                        String shareTitle;
//                        share.putExtra(Intent.EXTRA_SUBJECT,shareTitle);
                        share.putExtra(Intent.EXTRA_TEXT,shareBody);
                        startActivity(Intent.createChooser(share,"FAST TYPE"));
                    }
                });
                btnLastScore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(),TopScores.class);
                        startActivity(intent);
                    }
                });
                gToMenu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent gToMain = new Intent(MainActivity.this,StartPage.class);
                        startActivity(gToMain);

                    }
                });
                finishDialog.show();

                // Defining scoreList for show Last 10 scores button
                List<Score> scoreList = new ArrayList<>();
                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String jsonlist = sharedPreferences.getString("shScoreList","");
                Gson gson = new Gson();
                if (jsonlist.length() > 0){
                    Type type = new TypeToken<List<Score>>(){}.getType();
                    scoreList = gson.fromJson(jsonlist,type);
                }
                SharedPreferences.Editor editor = sharedPreferences.edit();
                str_score = String.valueOf(scoreTrueCount);
                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                Score Sscore = new Score(Integer.valueOf(str_score),formatter.format(date));
                scoreList.add(Sscore);
                scoreList = scoreList.subList(Math.max(scoreList.size() - 10, 0), scoreList.size());
                String json = gson.toJson(scoreList);
                editor.putString("shScoreList",json);
                editor.commit();
                // tvcounter visible = false
                tvcounter.setVisibility(View.GONE);
                // Reset The score
                    scoreTrueCount=0;
                    scoreFalseCount=0;

            }
        }.start();
        // Selecting random word
        rnd_mainword = randomizer();
        // Setting word to tv_main
        mainword.setText(wordlist.get(rnd_mainword));
        // Operations when text changes
        et_word.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                // Checking mainwords and user's word equal
                if (wordlist.get(rnd_mainword).equals(s.toString())) {
                    // if correct, increases the score
                    scoreTrueCount++;
                    rnd_mainword = randomizer();
                    mainword.setText(wordlist.get(rnd_mainword));
                    et_word.setText("");
                } else {
                    if(wordlist.get(rnd_mainword).length() == s.length()){
                        Toast.makeText(getApplicationContext(),"Typed Wrong",Toast.LENGTH_SHORT).show();
                        scoreFalseCount++;
                        rnd_mainword = randomizer();
                        mainword.setText(wordlist.get(rnd_mainword));
                        et_word.setText("");
                    }
                }
            }
        });
    }
    public int randomizer() {
        Random rand = new Random();
        int n = rand.nextInt(wordlist.size());
        System.out.println(n);
        return n;
    }
    public int adsrandom() {
        Random rand = new Random();
        int n = (int)(Math.random() * 3 + 1);
        System.out.println(n);
        return n;
    }
    public void colorful(long sec){
        if(sec<60000 && sec>50000){
            circularProgressBar.setColor(ContextCompat.getColor(getApplicationContext(),R.color.sixty));
        }
        else if(sec<50000 && sec>40000){
            circularProgressBar.setColor(ContextCompat.getColor(getApplicationContext(),R.color.fifty));
        }
        else if(sec<40000 && sec>30000){
            circularProgressBar.setColor(ContextCompat.getColor(getApplicationContext(),R.color.fourty));
        }
        else if(sec<30000 && sec>20000){
            circularProgressBar.setColor(ContextCompat.getColor(getApplicationContext(),R.color.thirty));
        }
        else if(sec<20000 && sec>10000){
            circularProgressBar.setColor(ContextCompat.getColor(getApplicationContext(),R.color.twenty));
        }
        else{
            circularProgressBar.setColor(ContextCompat.getColor(getApplicationContext(),R.color.ten));
        }
    }
    private void closekeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}

