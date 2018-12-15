package com.example.victoriasheng.fotogram;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.view.View;
import com.android.volley.RequestQueue;
import org.json.JSONObject;

import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.NetworkResponse;
import com.android.volley.AuthFailureError;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.json.JSONException;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import com.android.volley.toolbox.JsonObjectRequest;
public class MainActivity extends AppCompatActivity {

    RequestQueue requestQueue = Volley.newRequestQueue(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // al click del bottone se il nome e la password sono corretti , vado alla bcheca
        //non vuole il link allaregistrazione su server
        Button buttonServer=findViewById(R.id.NameSearchOnServer);
        buttonServer.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                EditText edit = (EditText)findViewById(R.id.password);
                String password = edit.getText().toString();
                EditText edit2 = (EditText)findViewById(R.id.nome);
                String username = edit2.getText().toString();
                String url = "https://ewserver.di.unimi.it/mobicomp/fotogram/login";
                JSONObject postparams = new JSONObject();
                try {
                    postparams.put("username", username);
                    postparams.put("password", password);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                final String result = "";
                JsonObjectRequest request = new JsonObjectRequest(JsonRequest.Method.POST, url, postparams, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response){

                        Log.d("Reponse", response.toString());

                        EditText editText = (EditText)findViewById(R.id.nome);
                        editText.setText(response.toString(), TextView.BufferType.EDITABLE);

                        //startActivity(new Intent(MainActivity.this, Bacheca.class));

                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        volleyError.printStackTrace();
                        Log.d("Error = ", volleyError.toString());
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        return null;
                    }

                };
                requestQueue.add(request);
            }
        });
    }











//creo un bottone che mi porta alla bacheca per ora, una volta implementato il login si può togliere
  /*  public void Bacheca(View v) {
        Intent intent=new Intent(MainActivity.this,Bacheca.class);//creo l'intent per colleare la classe NuovaMatricola
        startActivity(intent);
    }*/
}
