package de.curse.web.utils;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import de.curse.web.Main;
import net.md_5.bungee.api.ProxyServer;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Console implements HttpHandler {

    public static String getLog() {
        return FileUtils.getLogContents();
    }

    public static void sendCommand(String command) {
        ProxyServer.getInstance().getScheduler().schedule(Main.getInstance(), new Runnable() {
            @Override
            public void run() {
                ProxyServer.getInstance().getPluginManager().dispatchCommand(ProxyServer.getInstance().getConsole(), command);
            }
        }, 1, TimeUnit.SECONDS);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        if(httpExchange.getRequestURI().getPath().toString().equalsIgnoreCase("/console_send")) {
            System.out.println(httpExchange.getRequestURI().getQuery());
            sendCommand(httpExchange.getRequestURI().getQuery().toString());
        }
    }
}
