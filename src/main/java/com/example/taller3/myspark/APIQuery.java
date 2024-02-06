package com.example.taller3.myspark;

public interface APIQuery {
    /**
     * This method use an API or service provider to query the information of a
     * movie like year, director, nominations, awards, etc.
     * 
     * @param movieTitle The title of the movie to search
     * @return The information related to the movie, obtain by the API or the
     *         service provider implemented.
     */
    public String queryMovie(String movieTitle);
}
