package com.harun.fasttype;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ScoreAdapter extends BaseAdapter {
    private Context context;
    private List<Score> scoreList;

    public ScoreAdapter(Context context, List<Score> scoreList) {
        this.context = context;
        this.scoreList = scoreList;
    }

    @Override
    public int getCount() {
        return scoreList.size();
    }

    @Override
    public Object getItem(int position) {
        return scoreList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(context,R.layout.scorelist,null);
        TextView index,score,date;
        index = view.findViewById(R.id.index);
        score = view.findViewById(R.id.listscore);
        date = view.findViewById(R.id.list_date);
        index.setText(String.valueOf(position+1));
        score.setText(String.valueOf(scoreList.get(position).getScore())+" Words");
        date.setText(scoreList.get(position).getScoreDate());
        return view;
    }

}
