package com.example.chick.server;

public class JwtResponse {
    private String type;
    private String accessToken;

    public JwtResponse() {
    }

    public JwtResponse(String type, String accessToken) {
        this.type = type;
        this.accessToken = accessToken;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
