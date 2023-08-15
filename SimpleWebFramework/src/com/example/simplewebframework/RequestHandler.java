package com.example.simplewebframework;

import java.io.IOException;

public interface RequestHandler {
    int handleRequest(String httpMethod, String requestURI, SimpleWebFramework webFramework) throws IOException;
}
