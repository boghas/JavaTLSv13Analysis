package com.tls.analysis.echoTLS.EchoSSLServer;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import java.io.*;
import java.net.Socket;

public class Server {

    public final static int port = 9000;

    public static void main(String[] args) {

        System.setProperty("javax.net.ssl.keyStore", "C:\\Bogdan\\Facultate\\Licenta\\credentiale\\tls_analysis.jks");
        System.setProperty("javax.net.ssl.keyStorePassword", "tlsanalysis");
        System.setProperty("javax.net.ssl.trustStore", "C:\\Bogdan\\Facultate\\Licenta\\credentiale\\tls_analysis.jks");
        System.setProperty("javax.net.ssl.trustStorePassword", "tlsanalysis");


        System.out.println("SSL server starting...");
        System.out.println("Listening on port: " + port);

        try {
            SSLServerSocket sslServerSocket = (SSLServerSocket) SSLServerSocketFactory.getDefault().createServerSocket(port);
            SSLSocket client = (SSLSocket) sslServerSocket.accept();
            System.out.println("Connection established with: " + client.getInetAddress());

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter output = new PrintWriter(client.getOutputStream(), true);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println("From client: " + line);
                output.println(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
