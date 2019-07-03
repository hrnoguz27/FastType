package com.harun.fasttype;

import android.app.Dialog;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.harun.fasttype.Adapters.Language;
import com.harun.fasttype.Adapters.SelectionAdapter;

import java.util.ArrayList;
import java.util.List;

public class StartPage extends AppCompatActivity {
    Dialog mydialogx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);
        mydialogx = new Dialog(StartPage.this);

        final String[] arraySpinner = new String[] {
                "English","Turkish","Arabic","Chinese","French","German","Greek","Italian",
                "Japanese","Russian","Spanish"
        };





    }

    public void select(View view){
        ShowDialog showDialog = new ShowDialog();
        showDialog.show(getSupportFragmentManager(),"");
    }


}
