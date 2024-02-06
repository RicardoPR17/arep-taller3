package com.example.taller3.myspark;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MovieAPI implements APIQuery {
    private static final String API = env.API.getValue();
    private Map<String, String> cache = new ConcurrentHashMap<>();

    public MovieAPI() {
        // Empty constructor for the movie api
    }

    public String queryMovie(String movieTitle) {
        String movie = "";

        if (cache.containsKey(movieTitle.toLowerCase())) {
            movie = cache.get(movieTitle.toLowerCase());
            return movie;
        }

        try {
            // Search the movie information in the OMDb API
            movie = searchMovie(movieTitle);
        } catch (MalformedURLException urlE) {
            System.err.println("Something went wrong with the URL, please verify with the movie " + movieTitle);
            movie = "Unable to retrieve data from the API... Please try again.";
        } catch (IOException ioe) {
            System.err.println("Could not connect to API Server.");
            movie = "Unable to retrieve data from the API... Please try again.";
        }

        return movie;
    }

    /**
     * Search the movie in the OMDb API and load the result in the cache for future
     * queries
     * 
     * @param movieTitle The movie to search
     * @return The data retrieved from the API
     * @throws MalformedURLException When something happens trying to use the API,
     *                               creating the URL to request the data
     * @throws IOException           When the API deny the request or is down
     */
    private String searchMovie(String movieTitle) throws MalformedURLException, IOException {
        String movieData = "";

        URL apiToQuery = new URL(API + movieTitle);
        URLConnection apiConnection = apiToQuery.openConnection();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(apiConnection.getInputStream()))) {
            String inputLine = null;
            while ((inputLine = reader.readLine()) != null) {
                movieData = inputLine;
                if (!inputLine.contains("Movie not found!")) {
                    cache.putIfAbsent(movieTitle.toLowerCase(), movieData);
                } else {
                    movieData = "Movie not found! Please check and try again";
                }
            }
        } catch (IOException x) {
            System.err.println(x);
        }
        return movieData;
    }
}
