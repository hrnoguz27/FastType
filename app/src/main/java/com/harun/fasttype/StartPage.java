package com.harun.fasttype;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class StartPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);
        int i =0;
        int b = 5;
        int toplam = i+b;
        System.out.println(toplam);
        // comment line test
        System.out.println("Hello this is test string");
    }
}
