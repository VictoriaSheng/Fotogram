package com.example.victoriasheng.fotogram;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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
                //queue = Volley.newRequestQueue(ctx);
                getLoginResponse();
            }
        });
    }


    public void  getLoginResponse(){
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        String url = "https://ewserver.di.unimi.it/mobicomp/fotogram/login";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        ActivityForVar.setSessionId(response);
                        startActivity(new Intent(MainActivity.this, Bacheca.class));
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, android.R.style.Theme_Holo_Dialog_NoActionBar);
                        builder.setTitle("Error on login")
                                .setMessage("Username o password errate")
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
                EditText edit = (EditText)findViewById(R.id.password);
                String password = edit.getText().toString();
                EditText edit2 = (EditText)findViewById(R.id.nome);
                String username = edit2.getText().toString();
                Map<String, String>  params = new HashMap<String, String>();
                params.put("username", username);
                params.put("password", password);

                return params;
            }
        };
        queue.add(postRequest);
    }

}