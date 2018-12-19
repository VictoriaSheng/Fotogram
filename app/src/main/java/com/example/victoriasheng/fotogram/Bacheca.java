package com.example.victoriasheng.fotogram;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import java.lang.reflect.Field;

public class Bacheca extends AppCompatActivity {
    //lista dei post in ordine di data e menu in fondo fisso

    //parte dei fragments
    //variabili private
    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;

    private BachecaFragment bachecaFragment;
    private ProfiloFragment profiloFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //inizializzo le variabili
        mMainFrame=(FrameLayout)findViewById(R.id.main_frame);
        mMainNav= (BottomNavigationView)findViewById(R.id.main_nav);

        bachecaFragment=new BachecaFragment();
        profiloFragment=new ProfiloFragment();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bacheca);
        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_bacheca:
                        mMainNav.setItemBackgroundResource(R.color.colorPrimary);
                        setFragment(bachecaFragment);
                        return true;
                    case R.id.nav_prof://nav_profilo non melo prende
                        mMainNav.setItemBackgroundResource(R.color.colorAccent);
                        setFragment(profiloFragment);
                        return true;
                    default:
                        return false;
                }
            }
        });
       /*  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.main_nav);
  
       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

    }
    public void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }



}