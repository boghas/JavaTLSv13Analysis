package com.tls.analysis.httpsserver;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class HttpsServer {

    private static final int port = 8080;

    public static void main(String[] args) {
        System.setProperty("javax.net.ssl.keyStore","C:\\Bogdan\\Facultate\\Licenta\\credentiale\\tls_analysis.jks");
        System.setProperty("javax.net.ssl.keyStorePassword","tlsanalysis");
        System.setProperty("javax.net.ssl.trustStore","C:\\Bogdan\\Facultate\\Licenta\\credentiale\\tls_analysis.jks");
        System.setProperty("javax.net.ssl.trustStorePassword", "tlsanalysis");

        System.out.println("Starting the server...");
        System.out.println("Listening on port: " + port);

        SSLServerSocket server;

        try {
            SSLServerSocketFactory sslServerSocketFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
            server = (SSLServerSocket) sslServerSocketFactory.createServerSocket(port);

            SSLSocket client = (SSLSocket) server.accept();

            InputStream inputStream = client.getInputStream();
            OutputStream outputStream = client.getOutputStream();
            String html = "<html><head><title>Simple Java HTTP Server</title></head><body><h1>This page was served with Java HTTP Server</h1></body></html>";
            final String CRLF = "\n\r"; // 13, 10
            String response =
                    "HTTP/1.1 200 OK" + CRLF + // Status Line : HTTP VERSION RESPONSE CODE RESPONSE MESSAGE
                            "Content-Length " + html.getBytes().length + CRLF + CRLF + html + CRLF + CRLF; // HEADER
            outputStream.write(response.getBytes());
            inputStream.close();
            outputStream.close();
            client.close();
            server.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
