package com.tls.analysis.httpsserver.thread;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SSLServerListenerThread extends Thread {

    private int port;
    private SSLServerSocket sslServerSocket;

    public SSLServerListenerThread(int port) throws IOException {
        this.port = port;
        this.sslServerSocket = (SSLServerSocket) SSLServerSocketFactory.getDefault().createServerSocket(port);
    }

    @Override
    public void run() {
        try {
            while (true) {
                SSLSocket client = (SSLSocket) sslServerSocket.accept();
                System.out.println(" * Connection accepted: * " + client.getInetAddress());
                InputStream inputStream = client.getInputStream();
                OutputStream outputStream = client.getOutputStream();
                String html = "<html><head><title>Simple Java HTTP Server</title></head><body><h1>This page was served with Java HTTP Server</h1></body></html>";
                final String CRLF = "\n\r"; // 13, 10
                String response =
                        "HTTP/1.1 200 OK" + CRLF + // Status Line : HTTP VERSION RESPONSE CODE RESPONSE MESSAGE
                                "Content-Length " + html.getBytes().length + CRLF + CRLF + html + CRLF + CRLF; // HEADER
                outputStream.write(response.getBytes());
                inputStream.close();
                outputStream.close();
                client.close();
            }
            // sslServerSocket.close()
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
