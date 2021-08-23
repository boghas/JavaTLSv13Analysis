package com.tls.analysis.echoTLS.EchoSSLClient;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Scanner;

public class EchoSSLClient {

    private final static int port = 9000;
    public final static String host = "localhost";

    public static void main(String[] args) {

        System.setProperty("javax.net.ssl.keyStore", "src/credentiale/tls_analysis.jks");
        System.setProperty("javax.net.ssl.keyStorePassword", "tlsanalysis");
        System.setProperty("javax.net.ssl.trustStore", "src/credentiale/tls_analysis.jks");
        System.setProperty("javax.net.ssl.trustStorePassword", "tlsanalysis");

        System.out.println("Searching for a connection on port: " + port);

        try {
            SSLSocket client = (SSLSocket) SSLSocketFactory.getDefault().createSocket(host, port);
            System.out.println("Connected to the server.");

            PrintWriter out = new PrintWriter(client.getOutputStream(), true);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("Spune ceva prin SSL: ");
                String input = scanner.nextLine();
                if("exit".equalsIgnoreCase(input)) {
                    break;
                }
                out.println(input);
                String response = bufferedReader.readLine();
                System.out.println("Server: " + response);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
