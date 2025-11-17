package it.benassai;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(3000);

        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("connesso a " + socket.getInetAddress());

            BufferedReader socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter socketOut = new PrintWriter(socket.getOutputStream(), true);

            String message = socketIn.readLine();
            System.out.println(message);

            while (!message.equals("")) {
                message = socketIn.readLine();
                System.out.println(message);
            }
        }
    }
}