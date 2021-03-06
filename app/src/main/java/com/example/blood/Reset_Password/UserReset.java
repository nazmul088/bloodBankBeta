package com.example.blood.Reset_Password;

public class UserReset {
    private String email;
    private String token;
    private String password;
    private String message;

    public UserReset() {
    }


    public UserReset(String email, String token, String password) {
        this.email = email;
        this.token = token;
        this.password = password;
    }

    public UserReset(String email) {
        this.email = email;
    }

    public UserReset(String token, String password) {
        this.token = token;
        this.password = password;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
