package com.tacs.ResstApp.services.impl;

public class GitCredentials {

    static private String _token;
    static private String _userName;

    public static String getToken() {
        return _token;
    }

    public static void setToken(String token) {
        _token = token;
    }

    public static String getUserName() {
        return _userName;
    }

    public static void setUserName(String userName) {
        _userName = userName;
    }
}
