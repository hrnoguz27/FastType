package com.harun.fasttype;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private static final String TAG ="RecyclerViewAdapter";

    //vars
    private ArrayList<String> lNames = new ArrayList<>();
    private ArrayList<Integer> limagesUrls = new ArrayList<>();
    private Context lcontext;

    public RecyclerViewAdapter(Context lcontext, ArrayList<String> lNames, ArrayList<Integer> limagesUrls) {
        this.lNames = lNames;
        this.limagesUrls = limagesUrls;
        this.lcontext = lcontext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_languages,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        Log.d(TAG,"onBindViewHolder: called.");
        Glide.with(lcontext)
                .asBitmap()
                .load(limagesUrls.get(i))
                .into(viewHolder.btn_Limages);

        viewHolder.txt_Lnames.setText(lNames.get(i));
        viewHolder.btn_Limages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"onclick: clicked on an image:"+lNames.get(i));
                Toast.makeText(lcontext,lNames.get(i),Toast.LENGTH_SHORT).show();
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
