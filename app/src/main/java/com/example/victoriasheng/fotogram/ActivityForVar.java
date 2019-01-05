package com.example.victoriasheng.fotogram;


public class ActivityForVar{
    private static String sessionId = null;
    private static String username = null;
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
}

