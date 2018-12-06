package com.example.victoriasheng.fotogram;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AggAmici extends AppCompatActivity {
    /*in questa view servono:
    * PlainText per la ricerca degli amici con autocompletamento(OnKeyUp), per aggiungere un amico basta cliccarci sopra o creare un
    * button "follow"
    * il menu fisso in basso*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agg_amici);
    }
}
