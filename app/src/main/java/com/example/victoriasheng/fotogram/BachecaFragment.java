package com.example.victoriasheng.fotogram;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class BachecaFragment extends Fragment {


    public BachecaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(!ActivityForVar.isLogged()){
            Intent intent = new Intent(getContext(), MainActivity.class);
            startActivity(intent);
        }
        getDataForPage();
        return inflater.inflate(R.layout.fragment_bacheca, container, false);
    }

    public void  getDataForPage(){
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = "https://ewserver.di.unimi.it/mobicomp/fotogram/wall";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jobj = new JSONObject(response);
                            final JSONArray jarpost = jobj.getJSONArray("posts");
                            final ArrayList<JSONObject> arrayList = new ArrayList(jarpost.length());
                            for(int i=0;i < jarpost.length();i++){
                                arrayList.add(jarpost.getJSONObject(i));
                            }
                            ListView posts = (ListView) getView().findViewById(R.id.bachecalistview);
                            PostBachecaAdapter adapter = new PostBachecaAdapter(getContext(),android.R.layout.list_content, arrayList);
                            posts.setAdapter(adapter);
                            posts.setOnItemClickListener(
                                    new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                            String c = "";
                                            try {
                                                JSONObject ja = jarpost.getJSONObject(i);
                                               c = ja.getString("user");
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            ActivityForVar.setUserDett(c);
                                            Log.d("Fotogram", "Bacheca getUserDett " + ActivityForVar.getUserDett());
                                            DettUtenteFragment fragment2 = new DettUtenteFragment();
                                            FragmentManager fragmentManager = getFragmentManager();
                                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                            fragmentTransaction.replace(R.id.main_frame, fragment2);
                                            fragmentTransaction.commit();
                                        }
                                    }
                            );


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

                return params;
            }
        };
        queue.add(postRequest);
    }

}
