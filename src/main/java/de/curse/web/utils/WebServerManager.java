package de.curse.web.utils;

import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;

public class WebServerManager {

    private HttpServer server;

    public WebServerManager(int port){
        try {
            server = HttpServer.create(new InetSocketAddress(port), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start() {
        server.start();
    }

    public void stop() {
        server.stop(0);
    }

    public HttpServer getServer() {
        return server;
    }
}