package com.harun.fasttype;

import android.app.Dialog;
import android.content.Context;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    TextView onceki, simdiki, sonraki, tvsayac;
    EditText kelime;
    Spinner spinnerLanguage;
    private ArrayAdapter<String> dataAdapterForLanguages;
    ArrayList<String> kelimelistesi;
    private String[] languages={"English","Türkçe"};
    int ikincisayi;
    int ucuncusayi;
    int temp;
    int counter = 0;
    CircularProgressBar circularProgressBar;
    ImageButton baslatbtn;
    Dialog mydialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        onceki = findViewById(R.id.tv_first);
        simdiki = findViewById(R.id.tv_simdiki);
        sonraki = findViewById(R.id.tv_last);
        kelime = findViewById(R.id.et_kelime);
        tvsayac = findViewById(R.id.tv_sayac);
        baslatbtn = findViewById(R.id.btn_start);
        tvsayac.setVisibility(View.GONE);
        spinnerLanguage = findViewById(R.id.sLanguage);
        dataAdapterForLanguages = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,languages);
        String textFileName;
        mydialog = new Dialog(this);
        kelimelistesi = new ArrayList<>();
        textFileName="data.txt";
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(


                    new InputStreamReader(getAssets().open(textFileName), "UTF-8"));
            // do reading, usually loop until end of file reading
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                //process line
                kelimelistesi.add(mLine);

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
        kelime.setHint("Yazmaya Başla");
    }

    public void baslat(final View view) {
        kelime.setEnabled(true);
        tvsayac.setVisibility(View.VISIBLE);
        baslatbtn.setVisibility(View.GONE);
        baslatbtn.setEnabled(false);
        kelime.requestFocus();
        int animationDuration = 60000; // 2500ms = 2,5s
        circularProgressBar.setProgressWithAnimation(100, animationDuration); // Default duration = 1500ms
        new CountDownTimer(animationDuration, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                // sayac--;
                tvsayac.setText(String.valueOf(millisUntilFinished / 1000));
                System.out.println(millisUntilFinished);
                if(millisUntilFinished<60000 && millisUntilFinished>50000){
                    circularProgressBar.setColor(ContextCompat.getColor(getApplicationContext(),R.color.sixty));
                }
                else if(millisUntilFinished<50000 && millisUntilFinished>40000){
                    circularProgressBar.setColor(ContextCompat.getColor(getApplicationContext(),R.color.fifty));
                }
                else if(millisUntilFinished<40000 && millisUntilFinished>30000){
                    circularProgressBar.setColor(ContextCompat.getColor(getApplicationContext(),R.color.fourty));
                }
                else if(millisUntilFinished<30000 && millisUntilFinished>20000){
                    circularProgressBar.setColor(ContextCompat.getColor(getApplicationContext(),R.color.thirty));
                }
                else if(millisUntilFinished<20000 && millisUntilFinished>10000){
                    circularProgressBar.setColor(ContextCompat.getColor(getApplicationContext(),R.color.twenty));
                }
                else{
                    circularProgressBar.setColor(ContextCompat.getColor(getApplicationContext(),R.color.ten));
                }


            }

            @Override
            public void onFinish() {
                closekeyboard();
                // Toast.makeText(getApplicationContext(),"dogru cevap sayisi: " + String.valueOf(counter),Toast.LENGTH_LONG).show();
                kelime.setEnabled(false);
                baslatbtn.setEnabled(true);
                baslatbtn.setVisibility(View.VISIBLE);
                mydialog.setContentView(R.layout.skordialog);
                TextView kapat;
                Button odon;
                TextView ack_tv;
                odon = mydialog.findViewById(R.id.restart);
                ack_tv = mydialog.findViewById(R.id.acktext);
                ack_tv.setText("Skor: " + String.valueOf(counter) + " /dks");
                odon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mydialog.dismiss();
                    }
                });
                mydialog.show();
                tvsayac.setVisibility(View.GONE);
                //kelime.setHint("Tekrar Oyna!");
                kelime.setHintTextColor(ContextCompat.getColor(getApplicationContext(),R.color.white));

            }
        }.start();
        ikincisayi = randomizer();
        ucuncusayi = randomizer();
        simdiki.setText(kelimelistesi.get(ikincisayi));
        sonraki.setText(kelimelistesi.get(ucuncusayi));
        onceki.setText(" ");
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
                    onceki.setText(kelimelistesi.get(ikincisayi));


                    simdiki.setText(kelimelistesi.get(ucuncusayi));
                    onceki.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colordogru));
                    //  simdiki.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colordogru));
                    temp = ucuncusayi;
                    ikincisayi = ucuncusayi;
                    ucuncusayi = randomizer();
                    sonraki.setText(kelimelistesi.get(ucuncusayi));
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

    private void closekeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private String selectedLanguage(){
        String selected;
        selected="aaaaa";
        return selected;
    }

}

