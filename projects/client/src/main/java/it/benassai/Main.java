package it.benassai;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import it.benassai.Protocol.ClientRunner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Hello world!");

        Socket socket;

        System.out.println("Inserire ip");
        String ip = scanner.nextLine();

        System.out.println("Inserire porta");
        int port = Integer.parseInt(scanner.nextLine());

        socket = new Socket(ip, port);

        System.out.println("connesso");

        (new ClientRunner(socket)).run();

        socket.close();
    }
}