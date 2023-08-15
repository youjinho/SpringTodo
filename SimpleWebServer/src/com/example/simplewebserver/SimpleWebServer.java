package com.example.simplewebserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class SimpleWebServer {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8000);
        System.out.println("Server started on port 8000");

        while (true) {
            Socket socket = serverSocket.accept();
            handleClientSocket(socket);
        }
    }

    private static void handleClientSocket(Socket socket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             OutputStream out = socket.getOutputStream()) {

            String line = in.readLine();
            if (line == null) {
                return;
            }

            String[] requestParts = line.split(" ");
            if (requestParts.length < 3) {
                return;
            }

            String httpMethod = requestParts[0];
            String requestURI = requestParts[1];

            if ("GET".equals(httpMethod)) {
                if ("/".equals(requestURI)) {
                    sendResponse(out, "<h1>Simple Web Server</h1>\n" 
                    				+ "<h3><a href=\"/hello\">hello</a></h3>\n"
                    				+ "<h3><a href=\"/world\">world</a></h3>\n");
                } else if ("/hello".equals(requestURI)) {
                    sendResponse(out, "<h1>hello</h1>");
                } else if ("/world".equals(requestURI)) {
                    sendResponse(out, "<h1>world</h1>");
                } else {
                    sendResponse(out, "Not Found", 404, "Not Found");
                }
            } else {
                sendResponse(out, "Method Not Allowed", 405, "Method Not Allowed");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendResponse(OutputStream out, String responseBody) throws IOException {
        sendResponse(out, responseBody, 200, "OK");
    }

    private static void sendResponse(OutputStream out, String responseBody, int statusCode, String statusMessage) throws IOException {
        byte[] responseBytes = responseBody.getBytes(StandardCharsets.UTF_8);
        out.write(("HTTP/1.1 " + statusCode + " " + statusMessage + "\r\n").getBytes(StandardCharsets.UTF_8));
        out.write(("Content-Length: " + responseBytes.length + "\r\n").getBytes(StandardCharsets.UTF_8));
        out.write("Content-Type: text/html\r\n\r\n".getBytes(StandardCharsets.UTF_8));
        out.write(responseBytes);
        out.flush();
    }
}
