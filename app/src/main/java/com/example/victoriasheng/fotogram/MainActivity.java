package com.example.victoriasheng.fotogram;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonServer=findViewById(R.id.NameSearchOnServer);
        buttonServer.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                EditText edit = (EditText)findViewById(R.id.password);
                String password = edit.getText().toString();
                EditText edit2 = (EditText)findViewById(R.id.nome);
                String username = edit2.getText().toString();
                //queue = Volley.newRequestQueue(ctx);
                getJsonResponsePost();
            }
        });
    }


    public void  getJsonResponsePost(){
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        Map<String, String> params = new HashMap();
        params.put("username", "feng");
        params.put("password", "feng");

        JSONObject parameters = new JSONObject(params);
        String url = "https://ewserver.di.unimi.it/mobicomp/fotogram/login";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, parameters,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        EditText editText = (EditText)findViewById(R.id.nome);
                        editText.setText("aaa".toString(), TextView.BufferType.EDITABLE);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                EditText editText = (EditText)findViewById(R.id.nome);
                editText.setText(error.toString(), TextView.BufferType.EDITABLE);
            }
        });
        queue.add(jsonObjectRequest);
    }

}