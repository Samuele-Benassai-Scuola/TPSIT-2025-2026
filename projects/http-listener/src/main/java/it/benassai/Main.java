package it.benassai;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import it.benassai.Threads.ListenerRunnable;

public class Main {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(3000);

        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("connesso a " + socket.getInetAddress());

            Thread thread = new Thread(new ListenerRunnable(socket));
            thread.start();
        }
    }
}