package com.example.spacefight;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<scoreModel> scores = new ArrayList<>();

    public CustomAdapter(Context context, ArrayList<scoreModel> scores){
        mContext = context;
        this.scores = scores;
    }

    @Override
    public int getCount() {
        return scores.size();
    }

    @Override
    public Object getItem(int position) {
        return scores.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_view_item, parent, false);
        }

        scoreModel tempScore = (scoreModel) getItem(position);

        TextView tvName = (TextView)convertView.findViewById(R.id.tvName);
        TextView tvScore = (TextView)convertView.findViewById(R.id.tvScore);

        tvName.setText(tempScore.getPlayerName());
        tvScore.setText("" +tempScore.getPlayerScore());

        return convertView;
    }
}
