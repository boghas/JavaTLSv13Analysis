package com.tls.analysis.echo.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
    private final static int port = 9000;

    public static void main(String[] args) {
        System.out.println("Server-ul porneste...");
        System.out.println("Ascultam pe port-ul: " + port);

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            Socket client = serverSocket.accept();

            System.out.println("Client conectat!");

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter output = new PrintWriter(client.getOutputStream(), true);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println("De la Client: " + line);
                output.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
