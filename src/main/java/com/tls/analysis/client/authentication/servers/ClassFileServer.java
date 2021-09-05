package com.tls.analysis.client.authentication.servers;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.*;
import java.security.*;
import java.util.Scanner;
import javax.net.*;
import javax.net.ssl.*;

public class ClassFileServer extends ClassServer {

    private String docroot;
    private static int DefaultServerPort = 2001;

    /** Construieste un obiect de tip ClassFileServer
     * Parametrii: calea catre fisier
     */

    public ClassFileServer(ServerSocket ss, String docroot) {
        super(ss);
        this.docroot = docroot;
    }

    /** Intoarce un array de biti ce contine bitii fisierului cerut
     * Arunca FileNotFoundException daca fisierul nu a putut fi incarcat.
     */

    public byte[] getBytes(String path) throws IOException {
        System.out.println("reading: " + path);
        File f = new File(docroot + File.separator + path);
        int length = (int)(f.length());
        if (length == 0) {
            throw new IOException("Dimensiunea fisierului este 0: " + path);
        } else {
            FileInputStream fin = new FileInputStream(f);
            DataInputStream in = new DataInputStream(fin);

            byte[] bytecodes = new byte[length];
            in.readFully(bytecodes);
            return bytecodes;
        }
    }

    /** Metoda Main care creeaza server-ul.
     *
     */

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int port;
        String docroot = "src/test_file/";
        String protocol;
        boolean clientauth;

        System.out.print("Introdu portul pe care sa asculte server-ul: ");
        port = in.nextInt();
        in.nextLine();


        String line;
        System.out.print("Alege protocolul folosit (TLS petru comunicatie securizata, enter pentru comunicatie in plain text): ");
        line = in.nextLine();
        if(line.equals("TLS")) {
            protocol = "TLS";
            System.out.print("Server-ul foloseste ClientAuthentication (true pentru da, false pentru nu): ");
            line = in.nextLine();

        } else {
            protocol = "PlainSocket";
        }

        System.out.println("Server-ul porneste...");
        System.out.println("Asculta pe port-ul: " + port);

        if(line.equals("true")) {
            clientauth = true;
        } else {
            clientauth = false;
        }

        try {
            ServerSocketFactory ssf = ClassFileServer.getServerSocketFactory(protocol);
            ServerSocket ss = ssf.createServerSocket(port);

            if(clientauth == true) {
                ((SSLServerSocket)ss).setNeedClientAuth(true);
            }
            new ClassFileServer(ss, docroot);
        } catch (IOException e) {
            System.out.println("Eroare pornind server-ul: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static ServerSocketFactory getServerSocketFactory(String protocol) {
        if(protocol.equals("TLS")) {
            SSLServerSocketFactory ssf = null;
            try {
                // Setam key manager-ul ca sa faca autentificarea server-ului.
                SSLContext ctx;
                KeyManagerFactory kmf;
                KeyStore ks;
                char[] passphrase = "password".toCharArray();

                ctx = SSLContext.getInstance("TLS");
                kmf = KeyManagerFactory.getInstance("SunX509");
                ks = KeyStore.getInstance("JKS");

                ks.load(new FileInputStream("src/credentiale/OC/keys/server_ks.pkcs12"), passphrase);
                kmf.init(ks, passphrase);
                ctx.init(kmf.getKeyManagers(), null, null);

                ssf = ctx.getServerSocketFactory();

                return ssf;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return ServerSocketFactory.getDefault();
        }
        return null;
    }
}
