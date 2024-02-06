package com.example.taller3.myspark;

public enum env {
    PORT("17000"), API("http://www.omdbapi.com/?i=tt3896198&apikey=6754e60&t=");

    private String value;

    private env(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
