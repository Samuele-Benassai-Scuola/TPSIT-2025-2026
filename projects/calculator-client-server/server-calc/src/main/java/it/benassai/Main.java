package it.benassai;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import it.benassai.Protocol.ServerProtocol;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Server Started");

        ServerSocket serverSocket = new ServerSocket(3000);

        try {
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("connesso");

                // Binda ad altra porta
                Thread thread = new Thread(new ServerProtocol(socket));
                thread.start();
            }
        }
        finally {
            serverSocket.close();
        }
    }
}