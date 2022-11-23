package de.curse.web.utils;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import de.curse.web.Main;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public class WebFileManager implements HttpHandler {

    public WebFileManager() {
        load();
    }

    private void load() {
        File dir = new File(Main.getInstance().getDataFolder() + "//webpages//");
        if(!dir.getParentFile().exists()) {
            dir.getParentFile().mkdirs();
        }
        if(!dir.exists()) {
            dir.mkdirs();
        }
        File[] files = dir.listFiles();
        if(files != null) {
            for(int i = 0; i < files.length; i++) {
                if(!files[i].isDirectory()) {
                    Main.getWebServerManager().getServer().createContext("/" + files[i].getName(), this);
                }
            }
        }
        Main.getWebServerManager().getServer().createContext("/console_send", new Console());
        Main.getWebServerManager().getServer().createContext("/playerinfo", new Playerlist());
        Main.getWebServerManager().getServer().createContext("/", this);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = FileUtils.getFileContents(httpExchange.getRequestURI().getPath().toString());

        response = response
                .replace("%console_view%", Console.getLog())
                .replace("%player_list%", Playerlist.getPlayerList());

        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream outputStream = httpExchange.getResponseBody();
        outputStream.write(response.getBytes());
        outputStream.close();
    }
}
