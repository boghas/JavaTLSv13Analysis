package com.tls.analysis.httpserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class HttpServer {

    private static final int port = 8080;

    public static void main(String[] args) {
        System.out.println("Starting the server...");
        System.out.println("Listening on port: " + port);

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            Socket client = serverSocket.accept();

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
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
