package de.curse.web.utils;

import de.curse.web.Main;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

public class FileUtils {

    public static String getFileContents(String filename) {
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader("plugins/Webinterface" + "/webpages/" + filename));
            StringBuilder stringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();
            while (line != null) {
                stringBuilder.append(line);
                stringBuilder.append(System.lineSeparator());
                line = bufferedReader.readLine();
            }
            return stringBuilder.toString();
        }catch (Exception e) {
            return getFileContents("index.html");
        }
    }

    public static String getLogContents() {
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader("proxy.log.0"));
            String end = "";
            String line = bufferedReader.readLine();

            while (line != null) {
                end = line + "<br>" + end;
                line = bufferedReader.readLine();
            }
            return end;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return "<h1>Log Error!</h1>";
    }
}
