package com.example.taller3.myspark;

import java.io.IOException;
import java.net.URISyntaxException;

//
public class MyWebServices {
    public static void main(String[] args) throws IOException, URISyntaxException {
        HttpServer.get("/arep", () -> {
            String resp = "HTTP/1.1 200 OK\r\n"
                    + "Content-Type:text/html\r\n"
                    + "\r\n"
                    + "<h1>Hello AREP!</h1>";
            return resp;
        });

        HttpServer.get("/arsw", () -> {
            String resp = "HTTP/1.1 200 OK\r\n"
                    + "Content-Type:text/html\r\n"
                    + "\r\n"
                    + "<h1>Hello to Vietnam Motherfoka!</h1>";
            return resp;
        });

        HttpServer.get("/ieti", () -> {
            String resp = "HTTP/1.1 200 OK\r\n"
                    + "Content-Type:text/html\r\n"
                    + "\r\n"
                    + "<h1>Hello IETI °.°</h1>";
            return resp;
        });

        HttpServer.getInstance().runServer();
    }
}
