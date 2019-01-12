package com.example.victoriasheng.fotogram;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class PostListAdapter extends ArrayAdapter<String> {
    private int adapterLayout;

    public PostListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<JSONObject> posts) {
        super(context, resource, posts);
        adapterLayout = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View v = convertView;
        if(v==null){
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            //ottengo il riferimento al layout che ho creato per una singola cella della ListView
            v = layoutInflater.inflate(adapterLayout,null);
        }
        JSONObject post = getItem(position);

        if(s!=null){
            //popolo l'adapter
            TextView nomeLbl = v.findViewById(R.id.nomeTxtAdapter);
            TextView cognomeLbl = v.findViewById(R.id.cognomeTxtAdapter);
            TextView matricolaLbl = v.findViewById(R.id.matricolaTxtAdapter);

            nomeLbl.setText(s.getNome());
            cognomeLbl.setText(s.getCognome());
            matricolaLbl.setText(String.valueOf(s.getMatricola()));
        }
        return v;
    }
}
