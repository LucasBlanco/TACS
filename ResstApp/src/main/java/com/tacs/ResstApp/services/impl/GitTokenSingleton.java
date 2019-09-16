package com.tacs.ResstApp.services.impl;

public class GitTokenSingleton {

    static private String _token;

    public static String getToken(){
        return _token;
    }

    public static void setToken(String token){
        _token = token;
    }

}
