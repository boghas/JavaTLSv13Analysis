package com.tls.analysis.httpsserver;

import com.tls.analysis.httpserver.thread.ServerListenerThread;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class HttpsServer {

    private static final int port = 9000;

    public static void main(String[] args) {
        System.setProperty("javax.net.ssl.keyStore", "C:\\Bogdan\\Facultate\\Licenta\\credentiale\\tls_analysis.jks");
        System.setProperty("javax.net.ssl.keyStorePassword", "tlsanalysis");
        System.setProperty("javax.net.ssl.trustStore", "C:\\Bogdan\\Facultate\\Licenta\\credentiale\\tls_analysis.jks");
        System.setProperty("javax.net.ssl.trustStorePassword", "tlsanalysis");

        System.out.println("Starting the server...");
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


