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

        System.setProperty("javax.net.ssl.keyStore", "src/credentiale/tls_analysis.jks");
        System.setProperty("javax.net.ssl.keyStorePassword", "tlsanalysis");
        System.setProperty("javax.net.ssl.trustStore", "src/credentiale/tls_analysis.jks");
        System.setProperty("javax.net.ssl.trustStorePassword", "tlsanalysis");

        String hostname;
        int port;
        boolean enable_log = false;
        String log_type = "";
        String log_options = "";
        String log_ssl_parameters = "";

        Scanner scanner = new Scanner(System.in);
        System.out.print("Introdu host-ul server-ului: ");
        hostname = scanner.nextLine();

        System.out.print("Introdu port-ul pe care asculta server-ul: ");
        port = scanner.nextInt();
        scanner.nextLine();

        String line;
        System.out.print("Logam mesajul (true pentru da, false pentru nu): ");
        line = scanner.nextLine();

        if(line.equals("true")) {
            enable_log = true;
            System.out.print("Ce log sa folosim (all sau ssl): ");
            log_type = scanner.nextLine();
            System.out.print("Ce optiune sa alegem pentru log(data, verbose): ");
            log_options = scanner.nextLine();
            if(log_type.equals("ssl")) {
                System.out.println("Optiuni pentru logare de tip ssl: ");
                System.out.print("record, handshake, keygen, session, defaultctx, sslctx, sessioncache, keymanager, trustmanager");
                System.out.print("Alege optiunea: ");
                log_ssl_parameters = scanner.nextLine();
                System.setProperty("javax.net.debug", log_type + ":" + log_options + ":" + log_ssl_parameters);

            } else {
                System.setProperty("javax.net.debug", log_type + ":" + log_options);
            }

        }



        System.out.println("Conectare catre: " + hostname + " pe port-ul: " + port);

        try {
            SSLSocket client = (SSLSocket) SSLSocketFactory.getDefault().createSocket(hostname, port);
            System.out.println("Conectare cu succes!");

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
