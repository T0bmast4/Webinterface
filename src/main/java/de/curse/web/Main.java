package de.curse.web;

import de.curse.web.utils.FileUtils;
import de.curse.web.utils.WebFileManager;
import de.curse.web.utils.WebServerManager;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

public final class Main extends Plugin {

    private static Main instance;
    public static String PREFIX = "§aWebinterface§7 | ";

    private static WebServerManager webServerManager;

    @Override
    public void onEnable() {
        instance = this;
        webServerManager = new WebServerManager(40);
        webServerManager.start();
        new WebFileManager();
        new FileUtils();
    }

    @Override
    public void onDisable() {
        webServerManager.stop();
    }

    public static Main getInstance(){
        return instance;
    }

    public static WebServerManager getWebServerManager() {
        return webServerManager;
    }
}
