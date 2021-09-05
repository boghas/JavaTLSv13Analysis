package com.tls.analysis.client.authentication.client;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.security.KeyStore;
import java.util.Scanner;

public class SSLSocketClientAuth {

    public static void main(String[] args) {

        String host = "";
        int port = -1;
        String path = "";
        boolean enable_log = false;
        String log_type = "";
        String log_options = "";
        String log_ssl_parameters = "";


        Scanner scanner = new Scanner(System.in);

        System.out.print("Introdu hostname-ul la care sa ne conectam: ");
        host = scanner.nextLine();

        System.out.print("Introdu port-ul pe care asculta server-ul: ");
        port = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Introdu numele fisierului pe care vrei sa-l accesezi: ");
        path = scanner.nextLine();

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


        try {
            SSLSocketFactory factory = null;
            try {
                SSLContext ctx;
                KeyManagerFactory kmf;
                KeyStore ks;
                char[] passphrase = "tlsanalysis".toCharArray();

                ctx = SSLContext.getInstance("TLS");
                kmf = KeyManagerFactory.getInstance("SunX509");
                ks = KeyStore.getInstance("JKS");

                ks.load(new FileInputStream("src/credentiale/tls_analysis.jks"), passphrase);

                kmf.init(ks, passphrase);
                ctx.init(kmf.getKeyManagers(), null, null);

                factory = ctx.getSocketFactory();
            } catch (Exception e) {
                throw new IOException(e.getMessage());
            }
            SSLSocket socket = (SSLSocket) factory.createSocket(host, port);

            socket.startHandshake();

            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
            out.println("GET /" + path + " HTTP/1.0");
            out.println();
            out.flush();

            if(out.checkError()) {
                System.out.println("SSLSocketClient: java.io.PrintWriter error");
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String inputLine;
            while((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
            }

            in.close();
            out.close();
            socket.close();
    } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
