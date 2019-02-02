package com.example.victoriasheng.fotogram;


import android.content.Intent;

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
}

