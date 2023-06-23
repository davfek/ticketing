package com.example.ticketing.user;

public class AuthenticationResult {
    private boolean isAuthenticated;
    private String token;

    public AuthenticationResult(boolean isAuthenticated, String token) {
        this.isAuthenticated = isAuthenticated;
        this.token = token;
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }



    public String getToken() {
        return token;
    }


}
