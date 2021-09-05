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
        String tls_version = "";

        System.out.print("Introdu port-ul pe care asculta server-ul: ");
        port = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Server-ul asculta pe port-ul: " + port);

        System.setProperty("javax.net.ssl.keyStore", "src/credentiale/tls_analysis.jks");
        System.setProperty("javax.net.ssl.keyStorePassword", "tlsanalysis");
        System.setProperty("javax.net.ssl.trustStore", "src/credentiale/tls_analysis.jks");
        System.setProperty("javax.net.ssl.trustStorePassword", "tlsanalysis");

        System.out.print("Ce versiune de protocol folosim (TLSv1.2, TLSv1.3): ");
        tls_version = scanner.nextLine();


        try {
            System.out.println("Astepam conexiuni...");
            SSLServerSocket sslServerSocket = (SSLServerSocket) SSLServerSocketFactory.getDefault().createServerSocket(port);
            SSLSocket client = (SSLSocket) sslServerSocket.accept();
            if(tls_version.equals("TLSv1.3")) {
                client.setEnabledProtocols(new String[] {"TLSv1.3"});
                client.setEnabledCipherSuites(new String[] {"TLS_AES_128_GCM_SHA256"});
            } else {
                client.setEnabledProtocols(new String[]{"TLSv1.2"});
            }
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
