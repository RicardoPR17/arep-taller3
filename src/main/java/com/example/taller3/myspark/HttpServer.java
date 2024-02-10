package com.example.taller3.myspark;

import java.net.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.io.*;

public class HttpServer {

    private static HashMap<String, WebServiceInter> services = new HashMap<>();
    private static APIQuery movieSearcher = new MovieAPI();
    private static HttpServer _instance = new HttpServer();
    private String userDir;

    private HttpServer() {
    }

    public static HttpServer getInstance() {
        return _instance;
    }

    /**
     * @param dir The name of the directory in resources directory where the user
     *            files must be search
     */
    private void setUserDir(String dir) {
        this.userDir = dir == null ? "public" : dir;
    }

    public void runServer(String host) throws IOException, URISyntaxException {
        ServerSocket serverSocket = null;
        setUserDir(host);
        try {
            serverSocket = new ServerSocket(Integer.parseInt(env.PORT.getValue()));
        } catch (IOException e) {
            System.err.println("Could not listen on port:  " + env.PORT.getValue() + ".");
            System.exit(1);
        }

        boolean running = true;
        while (running) {
            Socket clientSocket = null;
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream()));
            String inputLine;
            String outputLine = "";
            boolean search = false;
            boolean firstLine = true;
            String method = "";
            String uriStr = "";

            while ((inputLine = in.readLine()) != null) {
                if (firstLine) {
                    if (inputLine.contains("GET")) {
                        method = "GET";
                        uriStr = inputLine.split(" ")[1];
                        break;
                    } else if (inputLine.contains("POST")) {
                        method = "POST";
                        uriStr = inputLine.split(" ")[1];
                        break;
                    }
                }
                System.out.println("Received: " + inputLine);
                if (!in.ready()) {
                    break;
                }
            }

            if (uriStr.contains("/movie?movie=")) {
                search = true;
            }

            URI file = new URI(uriStr);
            System.out.println("Find URI: " + file.getPath());
            String path = file.getPath();
            String query = file.getQuery();
            if (query != null) {
                query = query.split("=")[1];
            } else {
                query = "";
            }

            if (search) {
                getMovieData(out, file);
            } else {
                try {
                    //
                    if (path.startsWith("/action")) {
                        String webURI = path.replace("/action", "");
                        if (services.containsKey(webURI)) {
                            outputLine = services.get(webURI).handle(query);
                        } else if (webURI.contains(".")) {
                            outputLine = htttpClientHtml(webURI, clientSocket.getOutputStream(), userDir);
                        } else {
                            outputLine = httpError();
                        }
                    } else {
                        //
                        outputLine = htttpClientHtml(file.getPath(), clientSocket.getOutputStream());
                    }
                } catch (IOException e) {
                    outputLine = httpError();
                }
            }

            out.println(outputLine);

            out.close();
            in.close();
            clientSocket.close();
        }
        serverSocket.close();
    }

    private static String httpError() {
        String outputLine = "HTTP/1.1 404 Not Found\r\n"
                + "Content-Type:text/html\r\n"
                + "\r\n"
                + "<!DOCTYPE html>\n"
                + "<html>\n"
                + "    <head>\n"
                + "        <title>Requested File Not found</title>\n"
                + "        <meta charset=\"UTF-8\">\n"
                + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                + "    </head>\n"
                + "    <body>\n"
                + "        <h1>Error, file not found</h1>\n"
                + "    </body>\n";
        return outputLine;

    }

    /**
     * 
     * @param path         Path of the file requested
     * @param outputStream Instance of OutputStream from a Socket to send an image
     *                     if is requested
     * @return String with the response for the request, including headers and
     *         content
     * @throws IOException If something related to the file reading goes wrong
     */
    public static String htttpClientHtml(String path, OutputStream outputStream) throws IOException {
        File file = new File(path);
        String fileType = Files.probeContentType(file.toPath());

        String outputLine = "HTTP/1.1 200 OK\r\n"
                + "Content-Type:" + fileType + "\r\n"
                + "\r\n";

        Path filePath = Paths.get("target/classes/public/" + path);
        Charset charset = Charset.forName("UTF-8");
        if (fileType.contains("image")) {
            byte[] bytes = Files.readAllBytes(filePath);
            outputStream.write(outputLine.getBytes());
            outputStream.write(bytes);
        } else {
            BufferedReader reader = Files.newBufferedReader(filePath, charset);
            String line = null;
            while ((line = reader.readLine()) != null) {
                outputLine += line;
                if (!reader.ready()) {
                    break;
                }
            }
        }
        return outputLine;
    }

    /**
     * 
     * @param path         Path of the file requested
     * @param outputStream Instance of OutputStream from a Socket to send an image
     *                     if is requested
     * @param host         Name of the host directory where the files are stored
     * @return String with the response for the request, including headers and
     *         content
     * @throws IOException If something related to the file reading goes wrong
     */
    public static String htttpClientHtml(String path, OutputStream outputStream, String host) throws IOException {
        File file = new File(path);
        String fileType = Files.probeContentType(file.toPath());

        String outputLine = "HTTP/1.1 200 OK\r\n"
                + "Content-Type:" + fileType + "\r\n"
                + "\r\n";
        host += "/";

        Path filePath = Paths.get("target/classes/" + host + path);
        Charset charset = Charset.forName("UTF-8");
        if (fileType.contains("image")) {
            byte[] bytes = Files.readAllBytes(filePath);
            outputStream.write(outputLine.getBytes());
            outputStream.write(bytes);
        } else {
            BufferedReader reader = Files.newBufferedReader(filePath, charset);
            String line = null;
            while ((line = reader.readLine()) != null) {
                outputLine += line;
                if (!reader.ready()) {
                    break;
                }
            }
        }
        return outputLine;
    }

    /**
     * This auxiliar method search the movie with the given URL and send the
     * response to the user who request the data
     * 
     * @param out          The writer to send the response to the usar
     * @param urlWithTitle The URL created with the name of the movie to search in
     *                     the cache
     */
    private static void getMovieData(PrintWriter out, URI uri) {
        // Search the movie with the API
        String movieData = null;
        String movieTitle = null;
        try {
            movieTitle = uri.getQuery().replace("movie=", "");
            if (movieTitle == null)
                throw new NullPointerException();
            movieData = movieSearcher.queryMovie(movieTitle);
        } catch (NullPointerException nullE) {
            movieData = "";
        }

        String response = "HTTP/1.1 200 OK\r\n"
                + "Content-Type:application/json; charset=ISO-8859-1\r\n"
                + "\r\n"
                + movieData;

        out.println(response);
    }

    /**
     * Store the path and action to execute by the server
     * 
     * @param r Path or route requested by the client
     * @param s Action to be executed when this route is called
     */
    public static void get(String r, WebServiceInter s) {
        services.put(r, s);
    }

    public static void post() {
        System.out.println("POST not implemented yet.");
    }
}