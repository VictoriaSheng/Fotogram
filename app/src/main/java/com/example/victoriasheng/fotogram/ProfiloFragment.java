package com.example.victoriasheng.fotogram;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ProfiloFragment extends Fragment {


    public ProfiloFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        /*AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Holo_Dialog_NoActionBar);
        builder.setTitle("")
                .setMessage(ActivityForVar.getSessionId())
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();*/
        if(!ActivityForVar.isLogged()){
            Intent intent = new Intent(getContext(), MainActivity.class);
            startActivity(intent);
        }
        getDataForPage();
        return inflater.inflate(R.layout.fragment_profilo, container, false);
    }

    public void  getDataForPage(){
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = "https://ewserver.di.unimi.it/mobicomp/fotogram/profile";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jobj = new JSONObject(response);
                            String username = jobj.getString("username");
                            String immagine = jobj.getString("img");
                            JSONArray jarpost = jobj.getJSONArray("posts");
                            ArrayList<JSONObject> arrayList = new ArrayList(jarpost.length());
                            for(int i=0;i < jarpost.length();i++){
                                arrayList.add(jarpost.getJSONObject(i));
                            }

                            TextView myAwesomeTextView = (TextView) getView().findViewById(R.id.user);
                            myAwesomeTextView.setText(username);
                            byte[] decodedString = Base64.decode(immagine, Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            ImageView image = (ImageView) getView().findViewById(R.id.imageView);
                            image.setImageBitmap(decodedByte);
                            ListView posts = (ListView) getView().findViewById(R.id.postlistview);
                            PostProfiloAdapter adapter = new PostProfiloAdapter(getContext(),android.R.layout.list_content, arrayList);
                            posts.setAdapter(adapter);

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
                params.put("username", ActivityForVar.getUsername());
                params.put("session_id", ActivityForVar.getSessionId());

                return params;
            }
        };
        queue.add(postRequest);
    }

}
