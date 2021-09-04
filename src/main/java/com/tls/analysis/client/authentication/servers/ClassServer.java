package com.tls.analysis.client.authentication.servers;

import java.io.*;
import java.net.*;
import javax.net.*;

public abstract class ClassServer implements Runnable {

    private ServerSocket server = null;

    /**
     * Construieste un obiect de tip ClassServer pe baza unui server socket
     * Obtine bitii unui fisier prin metoda "getBytes"
     */

    protected ClassServer(ServerSocket ss) {
        server = ss;
        newListener();
    }

    /** Intoarce un array de biti care contine bitii fisierului cerut
     * Poate arunca 2 exceptii:
     * FileNotFoundException  - daca nu a putut fi accesat fisierul cerut
     * IOException - daca sunt erori in citirea clasei
     */

    public abstract byte[] getBytes(String path) throws IOException, FileNotFoundException;

    /** Thread-ul care accepta conexiuni catre server, parseaza header-ul pentru a obtine numele fisierului
     * si trimite inapoi bitii fisierului (sau o eroare daca fisierul nu a putut fi gasit sau raspunsul a fost malformat
     */

    public void run() {
        Socket socket;

        // Accepta o conexiune
        try {
            socket = server.accept();
        } catch (IOException e) {
            System.out.println("Class Server a picat...");
            e.printStackTrace();
            return;
        }

        // Creem un nou thread pentru a accepta conexiunea
        newListener();

        try {
            OutputStream rawOut = socket.getOutputStream();
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(rawOut)));

            try {
                // Obtine calea catre fisier din header
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String path = getPath(in);

                // Obtine bitii
                byte[] bytecodes = getBytes(path);

                // Trimitem bitii ca si raspuns - presupunem HTTP/1.0 sau o varianta mai recenta

                try {
                    out.print("HTTP/1.0 200 OK\r\n");
                    out.print("Content-Length: " + bytecodes.length + "\r\n");
                    out.print("Content-Type: text/html\r\n\r\n");
                    out.flush();
                    rawOut.write(bytecodes);
                    rawOut.flush();
                } catch (IOException ie) {
                    ie.printStackTrace();
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
                // Scriem mesajul de eroare
                out.println("HTTP/1.0 400 " + e.getMessage() + "\r\n");
                out.println("Content-Type: text/html\r\n\r\n");
                out.flush();
            }
        } catch (IOException ex) {
            System.out.println("Error writing respose: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {

            }
        }
    }

    // Creeam un nou thread pe care sa ascultam
    private void newListener() { (new Thread(this)).start(); }

    // Intoarce calea catre fisierul obtinut din parsarea header-ului

    private static String getPath(BufferedReader in) throws IOException {
        String line = in.readLine();
        String path = "";

        if(line.startsWith("GET /")) {
            line = line.substring(5, line.length()-1).trim();
            int index = line.indexOf(' ');
            if(index != -1) {
                path = line.substring(0, index);
            }
        }

        // Parcurgem restul header-ului
        do {
            line = in.readLine();
        } while ((line.length() != 0) && (line.charAt(0) != '\r') && (line.charAt(0) != '\n'));

        if (path.length() != 0) {
            return path;
        } else {
            throw new IOException("Header Malformat");
        }
    }
}
