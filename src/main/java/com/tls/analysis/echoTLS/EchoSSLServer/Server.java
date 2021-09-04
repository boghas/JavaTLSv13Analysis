package com.tls.analysis.echoTLS.EchoSSLServer;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import java.io.*;
import java.util.Scanner;

public class Server {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int port;
        boolean log_true = false;

        System.out.print("Insert the port to listen to: ");
        port = scanner.nextInt();

        System.out.println("Server starting on localhost listening on port: " + port);

        System.setProperty("javax.net.ssl.keyStore", "src/credentiale/tls_analysis.jks");
        System.setProperty("javax.net.ssl.keyStorePassword", "tlsanalysis");
        System.setProperty("javax.net.ssl.trustStore", "src/credentiale/tls_analysis.jks");
        System.setProperty("javax.net.ssl.trustStorePassword", "tlsanalysis");


        // Enable log
        //System.setProperty("javax.net.debug", "ssl:handshake:data");
        // Log everything
        // System.setProperty("javax.net.debug", "all:verbose");


        try {
            System.out.println("Waiting for connections...");
            SSLServerSocket sslServerSocket = (SSLServerSocket) SSLServerSocketFactory.getDefault().createServerSocket(port);
            SSLSocket client = (SSLSocket) sslServerSocket.accept();
            //client.setEnabledProtocols(new String[] {"TLSv1.3"});
            client.setEnabledProtocols(new String[] {"TLSv1.2"});
            //client.setEnabledCipherSuites(new String[] {"TLS_AES_128_GCM_SHA256"});
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
