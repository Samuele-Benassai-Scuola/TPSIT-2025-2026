package it.benassai;

import java.io.IOError;
import java.io.IOException;

import it.benassai.runnables.WebServer;

public class Main {
    public static void main(String[] args) throws IOException {
        WebServer webServer = new WebServer(8080);

        webServer.run();
    }
}