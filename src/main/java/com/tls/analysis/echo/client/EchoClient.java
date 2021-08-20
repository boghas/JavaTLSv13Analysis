package com.tls.analysis.echo.client;

import javax.net.SocketFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {
    private final static int port = 9000;
    private final static String host = "localhost";

    public static void main(String[] args) {

        try {
            Socket socket = new Socket(host, port);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Connection established with the server...");
            Scanner scanner = new Scanner(System.in);
            while(true) {
                System.out.println("Say someting: ");
                String input = scanner.nextLine();
                if("exit".equalsIgnoreCase(input)) {
                    break;
                }
                out.println(input);
                String response = buffer.readLine();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
