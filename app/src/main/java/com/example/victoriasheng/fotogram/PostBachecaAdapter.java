package com.example.victoriasheng.fotogram;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostBachecaAdapter extends ArrayAdapter<JSONObject> {
    public PostBachecaAdapter(Context ctx, int txt){
        super(ctx,txt);
    }
    public PostBachecaAdapter(Context ctx, int resource, List<JSONObject> items ){
        super(ctx,resource,items);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        String dataT="";
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.list_element_profilo, null);
        }
        JSONObject p = getItem(position);
        if (p != null) {
            TextView testoPost = (TextView) v.findViewById(R.id.msgPost);
            TextView dataPost = (TextView) v.findViewById(R.id.timestampPost);
            TextView userPost = (TextView) v.findViewById(R.id.userPost);
            try {
                getImage(p.getString("user"), v);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                testoPost.setText(p.getString("msg"));
                dataT=p.getString("timestamp");
                dataT=dataT.substring(0, 10);
                dataPost.setText(dataT);
                userPost.setText(p.getString("user"));
                byte[] decodedString = Base64.decode(p.getString("img"), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                ImageView image = (ImageView) v.findViewById(R.id.imagePost);
                image.setImageBitmap(decodedByte);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return v;
    }// End of method (getView)

    public void  getImage(final String user, final View v){
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = "https://ewserver.di.unimi.it/mobicomp/fotogram/profile";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jobj = new JSONObject(response);
                            byte[] decodedString = Base64.decode(jobj.getString("img"), Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            ImageView image = (ImageView) v.findViewById(R.id.imgUtentePostBacheca);
                            image.setImageBitmap(decodedByte);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Holo_Dialog_NoActionBar);
                        builder.setTitle("Error on login")
                                .setMessage(error.toString())
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("session_id", ActivityForVar.getSessionId());
                params.put("username", user);
                return params;
            }
        };
        queue.add(postRequest);
    }
}
