package de.curse.web.utils;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.io.IOException;
import java.io.OutputStream;

public class Playerlist implements HttpHandler {

    public static String getPlayerList() {
        String response = "";
        for(ProxiedPlayer players : ProxyServer.getInstance().getPlayers()) {
            String banCommand = "ban%20%playername%%201";
            response = response +
                    ("<tr><td><img src=\"https://minotar.net/avatar/%playername%/16\"></td><td>" +
                            "<a href=\"playerinfo?%playername%\"><p>%playername%</a>" +
                            "<input type=\"button\" class=\"smallbutton_kick\" value=\"Kick\" onclick=\"sendAction('kick %playername%')\">" +
                            "<input type=\"button\" class=\"smallbutton_ban\" value=\"Ban\" onclick=\"sendAction('" + banCommand + "')\">" +
                            "</p></td></tr>")
                    .replace("%playername%", players.getName())
                    .replace("%server%", players.getServer().getInfo().getName());
        }
        if(response.trim().equalsIgnoreCase("")) {
            response = "<p>Es sind keine Spieler online!</p>";
        }
        return response;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String playername = httpExchange.getRequestURI().getQuery().toString();
        if(ProxyServer.getInstance().getPlayer(playername).isConnected()) {
            ProxiedPlayer player = ProxyServer.getInstance().getPlayer(playername);
            String response = FileUtils.getFileContents("hidden/playerinfo.html");
            response = response
                    .replace("%name%", player.getName())
                    .replace("%uuid%", player.getUniqueId().toString())
                    .replace("%server%", player.getServer().getInfo().getName());

            httpExchange.sendResponseHeaders(200, response.length());
            OutputStream outputStream = httpExchange.getResponseBody();
            outputStream.write(response.getBytes());
            outputStream.close();
        }
    }
}
