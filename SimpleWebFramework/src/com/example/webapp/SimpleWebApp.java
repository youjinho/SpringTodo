package com.example.webapp;

import com.example.simplewebframework.RequestHandler;
import com.example.simplewebframework.SimpleWebFramework;
import java.io.IOException;

public class SimpleWebApp implements RequestHandler {
	@Override
    public int handleRequest(String httpMethod, String requestURI, SimpleWebFramework server) throws IOException {
        if ("GET".equals(httpMethod)) {
            if ("/".equals(requestURI)) {
                server.sendResponse("<h1>Simple Web Server</h1>\n" 
                				+ "<h3><a href=\"/hello\">hello</a></h3>\n"
                				+ "<h3><a href=\"/world\">world</a></h3>\n");
                return 0;
            } else if ("/hello".equals(requestURI)) {
                server.sendResponse("<h1>hello</h1>");
                return 0;
            } else if ("/world".equals(requestURI)) {
                server.sendResponse("<h1>world</h1>");
                return 0;
            } else {
                server.sendResponse("Not Found", 404, "Not Found");
                return -1;
            }
        } else {
            server.sendResponse("Method Not Allowed", 405, "Method Not Allowed");
            return -1;
        }	
    }
	
    public static void main(String[] args) throws IOException {
    	RequestHandler webapp = new SimpleWebApp();
    	SimpleWebFramework webFramework = new SimpleWebFramework(webapp);
    	webFramework.start(8000);
    }
}
