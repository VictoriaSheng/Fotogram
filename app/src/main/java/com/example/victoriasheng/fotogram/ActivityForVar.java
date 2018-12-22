package com.example.victoriasheng.fotogram;


public class ActivityForVar{
    private static String sessionId = null;
    public static void setSessionId(String param){
        sessionId = param;
    }
    public static String getSessionId(){
        return sessionId;
    }
}

