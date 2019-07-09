package com.harun.fasttype;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private static final String TAG ="RecyclerViewAdapter";

    //vars
    private ArrayList<String> lNames = new ArrayList<>();
    private ArrayList<String> lShortNames = new ArrayList<>();
    private ArrayList<Integer> limagesUrls = new ArrayList<>();
    private Context lcontext;

    public RecyclerViewAdapter(Context lcontext, ArrayList<String> lNames,ArrayList<String> lShortNames, ArrayList<Integer> limagesUrls) {
        this.lNames = lNames;
        this.lShortNames = lShortNames;
        this.limagesUrls = limagesUrls;
        this.lcontext = lcontext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
       LayoutInflater inflater = LayoutInflater.from(lcontext);
       view = inflater.inflate(R.layout.list_languages,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        Log.d(TAG,"onBindViewHolder: called.");


        viewHolder.btn_Limages.setImageResource(limagesUrls.get(i));
        viewHolder.txt_Lnames.setText(lNames.get(i));
        viewHolder.btn_Limages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"onclick: clicked on an image:"+lNames.get(i));
                Toast.makeText(lcontext,"Selected Language: "+lNames.get(i),Toast.LENGTH_SHORT).show();
                StartPage.selectedlang = lShortNames.get(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageButton btn_Limages;
        TextView txt_Lnames;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btn_Limages = itemView.findViewById(R.id.btn_Limages);
            txt_Lnames = itemView.findViewById(R.id.txt_Lnames);
        }


    }
}
