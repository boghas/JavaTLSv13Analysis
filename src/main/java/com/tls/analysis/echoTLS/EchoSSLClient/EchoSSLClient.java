package com.tls.analysis.echoTLS.EchoSSLClient;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Scanner;

public class EchoSSLClient {

    public static void main(String[] args) {

        String hostname;
        int port;
        String certificates_type;

        Scanner scanner = new Scanner(System.in);
        System.out.print("Insert the server to connect to: ");
        hostname = scanner.nextLine();

        System.out.print("Insert the port the server is listening to: ");
        port = scanner.nextInt();
        scanner.nextLine();

        System.out.print("What types of certificates to use: JKS or PKS: ");
        certificates_type = scanner.nextLine();

        if(certificates_type.equalsIgnoreCase("JKS")) {
            System.setProperty("javax.net.ssl.keyStore", "src/credentiale/tls_analysis.jks");
            System.setProperty("javax.net.ssl.keyStorePassword", "tlsanalysis");
            System.setProperty("javax.net.ssl.trustStore", "src/credentiale/tls_analysis.jks");
            System.setProperty("javax.net.ssl.trustStorePassword", "tlsanalysis");
        }
        else if(certificates_type.equalsIgnoreCase("PKS")) {
            System.setProperty("javax.net.ssl.keyStore", "C:\\Bogdan\\OC\\keys\\client_ks.pkcs12");
            System.setProperty("javax.net.ssl.keyStorePassword", "password");
            System.setProperty("javax.net.ssl.trustStore", "C:\\Bogdan\\OC\\keys\\client_ks.pkcs12");
            System.setProperty("javax.net.ssl.trustStorePassword", "password");
            System.out.println("This type of certificates is not yet valid!");
        }

        System.out.println("Searching for a connection on: " + hostname + " on port: " + port);

        try {
            SSLSocket client = (SSLSocket) SSLSocketFactory.getDefault().createSocket(hostname, port);
            System.out.println("Connected to the server.");

            PrintWriter out = new PrintWriter(client.getOutputStream(), true);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
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
