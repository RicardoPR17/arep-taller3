package com.example.taller3.myspark;

import java.io.IOException;
import java.net.URISyntaxException;

//
public class MyWebServices {
    private static String headers = "HTTP/1.1 200 OK\r\n"
            + "Content-Type:text/html\r\n"
            + "\r\n";

    public static void main(String[] args) throws IOException, URISyntaxException {
        HttpServer.get("/arep", p -> headers + "<h1>Hello AREP " + p + "!</h1>");

        HttpServer.get("/arsw", p -> headers + "<h1>Hello to Vietnam Motherfoka!</h1>");

        HttpServer.get("/ieti", p -> headers + "<h1>Hello IETI °.°</h1>");

        HttpServer.get("/queries", param -> {
            String title = "<title> Parameter tester </title>\r\n";
            String content = "<p>Test " + param + "</p>";
            return headers + title + content;
        });

        HttpServer.post("/post_test", p -> headers + "<h1> Test post " + p + "</h1>");

        HttpServer.setJSONAPIResponse(true);
        HttpServer.getInstance().runServer("prueba");
    }
}
