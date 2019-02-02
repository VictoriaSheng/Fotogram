package com.example.victoriasheng.fotogram;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class AggiungiAmiciAdapter extends ArrayAdapter<JSONObject> {
    public AggiungiAmiciAdapter(Context ctx, int txt){
        super(ctx,txt);
    }
    public AggiungiAmiciAdapter(Context ctx, int resource, List<JSONObject> items ){
        super(ctx,resource,items);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.list_element_user, null);
        }
        JSONObject p = getItem(position);
        if (p != null) {
            TextView testoPost = (TextView) v.findViewById(R.id.usernameUser);
            try {
                testoPost.setText(p.getString("name"));
                byte[] decodedString = Base64.decode(p.getString("picture"), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                ImageView image = (ImageView) v.findViewById(R.id.imgUser);
                image.setImageBitmap(decodedByte);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return v;
    }// End of method (getView)
}
