package com.harun.fasttype.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.harun.fasttype.R;

import java.util.List;

public class SelectionAdapter extends RecyclerView.Adapter<SelectionAdapter.LangViewHolder> {

    Context context;
    List<Language> languageList;

    public SelectionAdapter(Context context, List<Language> languageList) {
        this.context = context;
        this.languageList = languageList;
    }

    @NonNull
    @Override
    public LangViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.langrecycler,viewGroup,false);

        return new LangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LangViewHolder langViewHolder,final int i) {
           // langViewHolder.flagbutton.setImageResource(languageList.get(i).getImageId());
            langViewHolder.flagText.setText(languageList.get(i).getLanguage());
    }

    @Override
    public int getItemCount() {
        return languageList.size();
    }

    public class LangViewHolder extends RecyclerView.ViewHolder{
        ImageButton flagbutton;
        TextView flagText;
        public LangViewHolder(@NonNull View itemView) {
            super(itemView);
          //  flagbutton = itemView.findViewById(R.id.btn_flag);
            flagText = itemView.findViewById(R.id.tv_language);
        }
    }
}
