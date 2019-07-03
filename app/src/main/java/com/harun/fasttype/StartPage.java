package com.harun.fasttype;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class StartPage extends AppCompatActivity {
    Spinner spinnerL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);

        Spinner spinnerL = (Spinner) findViewById(R.id.sLanguage);
        String[] arraySpinner = new String[] {
                "English","Turkish","Arabic","Chinese","French","German","Greek","Italian",
                "Japanese","Russian","Spanish"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerL.setAdapter(adapter);

        MainActivity language = new MainActivity();




    }
}
