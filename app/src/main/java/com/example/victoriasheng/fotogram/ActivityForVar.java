package com.example.victoriasheng.fotogram;


import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class ActivityForVar{
    private static String sessionId = "";
    private static String username = "";
    private static String userDett = "";
    public static void setSessionId(String param){
        sessionId = param;
    }
    public static String getSessionId(){
        return sessionId;
    }
    public static void setUsername(String param){
        username = param;
    }
    public static String getUsername(){
        return username;
    }
    public static void setUserDett(String param){
        userDett = param;
    }
    public static String getUserDett(){
        return userDett;
    }
    public static boolean isLogged(){
        if(sessionId.equals("") || username.equals("")){
           return false;
        }else{
            return true;
        }
    }

    public static void saveToFile(Context ctx){
        File directory = ctx.getFilesDir();
        File file = new File(directory, "Variabili2");
        if(file.exists()){
            file.delete();
        }
        try {
            FileOutputStream fout = new FileOutputStream(file);
            String toWrite = sessionId + ";" + username;
            fout.write(toWrite.getBytes());
            fout.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean getFromFile(Context ctx){
        File directory = ctx.getFilesDir();
        File file = new File(directory, "Variabili2");
        if(file.exists()) {
            Log.d("TESTTTTTT","esiste");
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String content = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    content = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
                }
                String[] arvar = content.split(";");
                if (arvar.length == 2) {
                    if (arvar[0].length() > 0) {
                        setSessionId(arvar[0]);
                    } else {
                        return false;
                    }
                    if (arvar[1].length() > 0) {
                        setUsername(arvar[1]);
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }else{
            return false;
        }
    }

    public static void cancFromFile(Context ctx){
        Log.d("LOGOUT","logout");
        File directory = ctx.getFilesDir();
        File file = new File(directory, "Variabili2");
        if(file.exists()){
            file.delete();
        }
    }



}

