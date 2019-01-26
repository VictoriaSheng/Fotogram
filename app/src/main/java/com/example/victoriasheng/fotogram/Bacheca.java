package com.example.victoriasheng.fotogram;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Bacheca extends AppCompatActivity implements View.OnClickListener {
    //variabili private che identificano le parti della bacheca:navigation-bottom e parte superiore
    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;

    private ProfiloFragment profiloFragment;
    private BachecaFragment bachecaFragment;
    private PostFragment postFragment;
    private AggiungiFragment aggiungiFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bacheca);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.main_nav);

        /*AlertDialog.Builder builder = new AlertDialog.Builder(Bacheca.this, android.R.style.Theme_Holo_Dialog_NoActionBar);
        builder.setTitle("TEST")
                .setMessage(ActivityForVar.getSessionId())
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();*/


        //parte dello switch per i fragments
        //inizializzo le variabili per frame e navigation
        mMainFrame=findViewById(R.id.main_frame);
        mMainNav=findViewById(R.id.main_nav);

        //inizializzo i fragments
        bachecaFragment= new BachecaFragment();
        profiloFragment=new ProfiloFragment();
        postFragment= new PostFragment();
        aggiungiFragment=new AggiungiFragment();
        setFragment(bachecaFragment);

        //implementazione dei tasti
        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_bacheca:
                        mMainNav.setItemBackgroundResource(R.color.colorPrimary);
                        setFragment(bachecaFragment);
                        return true;
                    case R.id.nav_prof:
                        mMainNav.setItemBackgroundResource(R.color.colorPrimary);
                        setFragment(profiloFragment);
                        return true;
                    case R.id.nav_post:
                        mMainNav.setItemBackgroundResource(R.color.colorPrimary);
                        setFragment(postFragment);
                        return true;
                    case R.id.nav_aggiungi:
                        mMainNav.setItemBackgroundResource(R.color.colorPrimary);
                        setFragment(aggiungiFragment);
                        return true;
                }
                return false;
            }
        });



       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

    }

    public void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogout: logout();
                break;
        }
    }

    public void logout(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://ewserver.di.unimi.it/mobicomp/fotogram/logout";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Intent intent = new Intent(Bacheca.this, MainActivity.class);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               setFragment(bachecaFragment);
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("session_id", ActivityForVar.getSessionId());
                return params;
            }
        };
        queue.add(stringRequest);
    }

}