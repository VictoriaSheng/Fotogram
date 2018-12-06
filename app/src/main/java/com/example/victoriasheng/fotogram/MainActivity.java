package com.example.victoriasheng.fotogram;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // al click del bottone se il nome e la password sono corretti , vado alla bcheca
        //non vuole il link allaregistrazione su server
        Button buttonServer=findViewById(R.id.NameSearchOnServer);
        
    }
}
