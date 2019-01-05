package com.example.victoriasheng.fotogram;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class ProfiloFragment extends Fragment {


    public ProfiloFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getDataForPage();
        return inflater.inflate(R.layout.fragment_profilo, container, false);
    }

    public void  getDataForPage(){
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = "https://ewserver.di.unimi.it/mobicomp/fotogram/profile";
        Map<String, String>  params = new HashMap<String, String>();
        params.put("username", ActivityForVar.getUsername());
        params.put("session_id", ActivityForVar.getSessionId());
        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, parameters,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String username = response.getString("username");
                            String picture = response.getString("picture");
                            TextView myAwesomeTextView = (TextView) getView().findViewById(R.id.user);
                            myAwesomeTextView.setText(username);
                            byte[] decodedString = Base64.decode(picture, Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            ImageView image = (ImageView) getView().findViewById(R.id.imageView);
                            image.setImageBitmap(decodedByte);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {



            }
        });
        jsonObjectRequest.setShouldCache(false);
        queue.add(jsonObjectRequest);
    }

}
