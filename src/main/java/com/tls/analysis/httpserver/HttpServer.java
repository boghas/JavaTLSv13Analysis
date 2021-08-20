package com.tls.analysis.httpserver;

import com.tls.analysis.httpserver.thread.ServerListenerThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class HttpServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);

    private static final int port = 8080;

    public static void main(String[] args) {
        System.out.println("Server starting..");
        System.out.println("Listening on port: " + port);
        ServerListenerThread serverListenerThread = null;
        try {
            serverListenerThread = new ServerListenerThread(port);
            serverListenerThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
