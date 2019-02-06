package com.example.victoriasheng.fotogram;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
    private DettUtenteFragment dettUtenteFragment;

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
        dettUtenteFragment=new DettUtenteFragment();
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
            case R.id.selectImage: selectImage("profilo");
                break;
            case R.id.postImage : selectImage("post");
                break;
            case R.id.salvaPost: salvaPost();
                break;
            case R.id.seguiOn: segui();
                break;
            case R.id.nonSeguire: notSegui();
                break;
        }
    }

    public void notSegui(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://ewserver.di.unimi.it/mobicomp/fotogram/unfollow";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("CIRFRA2", ActivityForVar.getUserDett());
                        setFragment(bachecaFragment);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String err = new String(error.networkResponse.data);
                String msg = "";
                if(err.startsWith("YOU")){
                    msg = "Non segui questo utente";
                }else if(err.startsWith("USERNAME")){
                    msg= "Utente non trovato";
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(Bacheca.this, android.R.style.Theme_Holo_Dialog_NoActionBar);
                builder.setTitle("")
                        .setMessage(msg)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("session_id", ActivityForVar.getSessionId());
                params.put("username",ActivityForVar.getUserDett());
                Log.d("CIRFRA3", ActivityForVar.getUserDett());
                return params;
            }
        };
        queue.add(stringRequest);
    }

    public void segui(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://ewserver.di.unimi.it/mobicomp/fotogram/follow";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("CIRFRA2", ActivityForVar.getUserDett());
                        setFragment(bachecaFragment);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String err = new String(error.networkResponse.data);
                String msg = "";
                if(err.startsWith("CANN")){
                    msg = "Non puoi seguire te stesso";
                }else if(err.startsWith("ALREADY")){
                    msg= "Utente già seguito";
                }else if(err.startsWith("USER")){
                    msg = "Utente non trovato";
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(Bacheca.this, android.R.style.Theme_Holo_Dialog_NoActionBar);
                builder.setTitle("")
                        .setMessage(msg)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("session_id", ActivityForVar.getSessionId());
                params.put("username",ActivityForVar.getUserDett());
                Log.d("CIRFRA3", ActivityForVar.getUserDett());
                return params;
            }
        };
        queue.add(stringRequest);
    }

    public void salvaPost(){
        Log.d("MYLOG","aaaaa");
        EditText edit = (EditText)findViewById(R.id.postMsg);
        final String msgparam = edit.getText().toString();
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://ewserver.di.unimi.it/mobicomp/fotogram/create_post";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        setFragment(profiloFragment);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("session_id", ActivityForVar.getSessionId());
                params.put("img",imgPostB64);
                params.put("message",msgparam);
                return params;
            }
        };
        queue.add(stringRequest);

    }

    public static final int PICK_IMAGE = 1;
    String imgProfiloB64 = "";
    String imgPostB64 = "";
    String tipoImageSelect = "";
    public void selectImage(String tipo){
        tipoImageSelect = tipo;
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                return;
            }
            try {
                InputStream is = this.getContentResolver().openInputStream(data.getData());
                //byte[] decodedString = Base64.decode(immagine, Base64.DEFAULT);
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                int nRead;
                byte[] databyte = new byte[16384];
                while ((nRead = is.read(databyte, 0, databyte.length)) != -1) {
                    buffer.write(databyte, 0, nRead);
                }
                byte[] decodedString = buffer.toByteArray();

                if(tipoImageSelect.equals("profilo")) {
                    imgProfiloB64 = Base64.encodeToString(decodedString, Base64.DEFAULT);
                    final Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    final AlertDialog.Builder alertadd = new AlertDialog.Builder(this);
                    LayoutInflater factory = LayoutInflater.from(this);
                    final View view = factory.inflate(R.layout.selectimage, null);
                    ImageView image = (ImageView) view.findViewById(R.id.select_image);
                    image.setImageBitmap(decodedByte);
                    alertadd.setView(view);
                    alertadd.setPositiveButton("SALVA", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ImageView image = findViewById(R.id.imageView);
                            image.setImageBitmap(decodedByte);
                            uploadImage();
                        }
                    });
                    alertadd.setNegativeButton("ANNULLA", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alertadd.show();
                }else if(tipoImageSelect.equals("post")) {
                    imgPostB64 = Base64.encodeToString(decodedString, Base64.DEFAULT);
                    final Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    ImageView image = (ImageView) findViewById(R.id.postImage);
                    image.setImageBitmap(decodedByte);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void uploadImage(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://ewserver.di.unimi.it/mobicomp/fotogram/picture_update";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        TextView myAwesomeTextView = (TextView) findViewById(R.id.textView);
                        myAwesomeTextView.setText("OK" + response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                TextView myAwesomeTextView = (TextView) findViewById(R.id.textView);
                myAwesomeTextView.setText("ERROR" + error);
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("session_id", ActivityForVar.getSessionId());
                params.put("picture",imgProfiloB64);
                return params;
            }
        };
        queue.add(stringRequest);
    }
    public void logout(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://ewserver.di.unimi.it/mobicomp/fotogram/logout";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ActivityForVar.setSessionId("");
                        ActivityForVar.setUsername("");
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