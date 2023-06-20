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

    public void setAuthenticated(boolean authenticated) {
        isAuthenticated = authenticated;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
