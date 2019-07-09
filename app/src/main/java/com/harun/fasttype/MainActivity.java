package com.harun.fasttype;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    TextView onceki, simdiki, sonraki, tvsayac,txtLastScores;
    EditText kelime;
    String score;
    ArrayList<String> kelimelistesi;
    private String[] languages={"English","Türkçe"};
    int ikincisayi;
    int ucuncusayi;
    int temp;
    int adscounter=0;
    int counter = 0;
    CircularProgressBar circularProgressBar;
    ImageButton baslatbtn;
    Dialog finishDialog,scoreDialog;
    public String textFileName;
    String sLanguage;
    //QuickSort qSort = new QuickSort();
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        //qSort.sort(lasttenscores,0,lasttenscores.length);
        //onceki = findViewById(R.id.tv_first);
        simdiki = findViewById(R.id.tv_simdiki);
        //sonraki = findViewById(R.id.tv_last);
        kelime = findViewById(R.id.et_kelime);
        tvsayac = findViewById(R.id.tv_sayac);
        baslatbtn = findViewById(R.id.btn_start);
        tvsayac.setVisibility(View.GONE);

        finishDialog = new Dialog(this);
        kelimelistesi = new ArrayList<>();
        textFileName=null;
        Intent getLang = getIntent();
        Log.i("gelendil",getLang.getStringExtra("selectedLang"));
        sLanguage = getLang.getStringExtra("selectedLang");
        System.out.println(sLanguage);
        textFileName = "words"+sLanguage+".txt";
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(


                    new InputStreamReader(getAssets().open(textFileName), "UTF-8"));
            // do reading, usually loop until end of file reading
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                //process line
                kelimelistesi.add(mLine.trim().toLowerCase());

            }
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
        kelime.requestFocus();
        tvsayac.setVisibility(View.INVISIBLE);
        circularProgressBar = findViewById(R.id.progress_circular);
        kelime.setHint("Start Type");
    }





    public void baslat(final View view) {
        kelime.setEnabled(true);
        tvsayac.setVisibility(View.VISIBLE);
        baslatbtn.setVisibility(View.GONE);
        baslatbtn.setEnabled(false);
        kelime.requestFocus();
        adscounter++;
        final int[] animationDuration = {60000}; // 2500ms = 2,5s
        circularProgressBar.setProgressWithAnimation(100, animationDuration[0]); // Default duration = 1500ms
        new CountDownTimer(animationDuration[0], 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                // sayac--;
                kelime.requestFocus();
                tvsayac.setText(String.valueOf(millisUntilFinished / 1000));
                System.out.println(millisUntilFinished);
                colorful(millisUntilFinished);
            }
            @Override
            public void onFinish()  {
                circularProgressBar.setProgressWithAnimation(0);
                closekeyboard();
                // Toast.makeText(getApplicationContext(),"dogru cevap sayisi: " + String.valueOf(counter),Toast.LENGTH_LONG).show();
                kelime.setEnabled(false);
                baslatbtn.setEnabled(true);
                baslatbtn.setVisibility(View.VISIBLE);
                finishDialog.setContentView(R.layout.skordialog);

                TextView kapat;
//                if(adscounter%2==0){
//                    Intent adIntent = new Intent(getApplicationContext(),AdsActivity.class);
//                    adIntent.putExtra("adscounter",adscounter);
//                    startActivity(adIntent);
//                }
                Button restart,shareScore,gToMenu,btnLastScore;
                TextView ack_tv;
                shareScore = finishDialog.findViewById(R.id.shareScore);
                btnLastScore = finishDialog.findViewById(R.id.btn_lastScores);
                restart = finishDialog.findViewById(R.id.restart);
                gToMenu = finishDialog.findViewById(R.id.gToMenu);
                ack_tv = finishDialog.findViewById(R.id.txt_score);
                ack_tv.setText("Score: " + String.valueOf(counter) + " /WPM");
                restart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finishDialog.dismiss();
                    }
                });
                System.out.println(counter);
                List<Score> scoreList = new ArrayList<>();
                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String jsonlist = sharedPreferences.getString("shScoreList","");
                Gson gson = new Gson();
                if (jsonlist.length() > 0){
                    Type type = new TypeToken<List<Score>>(){}.getType();
                    scoreList = gson.fromJson(jsonlist,type);
                }

                SharedPreferences.Editor editor = sharedPreferences.edit();
                score= String.valueOf(counter);

                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                Score Sscore = new Score(Integer.valueOf(score),formatter.format(date));
                scoreList.add(Sscore);
                scoreList = scoreList.subList(Math.max(scoreList.size() - 10, 0), scoreList.size());
                String json = gson.toJson(scoreList);
                editor.putString("shScoreList",json);
                editor.commit();

                shareScore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent share = new Intent(Intent.ACTION_SEND);
                        share.setType("text/plain");
                        String shareBody = "MY SCORE IS "+score+" WPM\n"+"If you believe you can type faster than me, you can challenge me. " +
                                "If you want to participate in this race, share your score with your friends and join this race.";
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
                tvsayac.setVisibility(View.GONE);
                counter=0;
                //kelime.setHint("Tekrar Oyna!");
                kelime.setHintTextColor(ContextCompat.getColor(getApplicationContext(),R.color.white));

            }
        }.start();
        ikincisayi = randomizer();
        ucuncusayi = randomizer();
        simdiki.setText(kelimelistesi.get(ikincisayi));
//        sonraki.setText(kelimelistesi.get(ucuncusayi));
        //onceki.setText(" ");
        // kelime.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);

        kelime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                //onceki.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.coloryanlis));


            }

            @Override
            public void afterTextChanged(Editable s) {
                System.out.println(kelimelistesi.get(ikincisayi).equals(s));
                System.out.println(s);
                if (kelimelistesi.get(ikincisayi).equals(s.toString())) {
                    counter++;
                    //onceki.setText(kelimelistesi.get(ikincisayi));


                    simdiki.setText(kelimelistesi.get(ucuncusayi));
                    //onceki.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colordogru));
                    //  simdiki.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colordogru));
                    temp = ucuncusayi;
                    ikincisayi = ucuncusayi;
                    ucuncusayi = randomizer();
                    //sonraki.setText(kelimelistesi.get(ucuncusayi));
                    kelime.setText("");
                    System.out.println("eşitlendi");
                } else {
                    // simdiki.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.coloryanlis));
                }

            }
        });
    }

    public int randomizer() {
        Random rand = new Random();
        int n = rand.nextInt(kelimelistesi.size());
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

