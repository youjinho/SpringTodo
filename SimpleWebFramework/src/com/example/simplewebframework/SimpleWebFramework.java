package com.example.simplewebframework;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class SimpleWebFramework {

    private RequestHandler requestHandler;
    private OutputStream out;
    
    public SimpleWebFramework(RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    public void start(int port) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server started on port " + port);

        while (true) {
            Socket socket = serverSocket.accept();
            handleClientSocket(socket);
        }
    }

    private void handleClientSocket(Socket socket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
        	out = socket.getOutputStream();
        	
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

            requestHandler.handleRequest(httpMethod, requestURI, this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendResponse(String responseBody) throws IOException {
        byte[] responseBytes = responseBody.getBytes(StandardCharsets.UTF_8);
        out.write(("HTTP/1.1 200 OK\r\n").getBytes(StandardCharsets.UTF_8));
        out.write(("Content-Length: " + responseBytes.length + "\r\n").getBytes(StandardCharsets.UTF_8));
        out.write("Content-Type: text/html\r\n\r\n".getBytes(StandardCharsets.UTF_8));
        out.write(responseBytes);
        out.flush();
    }

    public void sendResponse(String responseBody, int statusCode, String statusMessage) throws IOException {
        byte[] responseBytes = responseBody.getBytes(StandardCharsets.UTF_8);
        out.write(("HTTP/1.1 " + statusCode + " " + statusMessage + "\r\n").getBytes(StandardCharsets.UTF_8));
        out.write(("Content-Length: " + responseBytes.length + "\r\n").getBytes(StandardCharsets.UTF_8));
        out.write("Content-Type: text/html\r\n\r\n".getBytes(StandardCharsets.UTF_8));
        out.write(responseBytes);
        out.flush();
    }
}
