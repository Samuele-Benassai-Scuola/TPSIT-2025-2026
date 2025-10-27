package it.benassai;

import java.net.Socket;
import java.util.Scanner;

import it.benassai.Protocol.ClientProtocol;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        Socket socket;

        System.out.println("Inserire ip");
        String ip = scanner.nextLine();

        System.out.println("Inserire porta");
        int port = Integer.parseInt(scanner.nextLine());

        socket = new Socket(ip, port);

        System.out.println("connesso");

        (new ClientProtocol(socket)).run();

        socket.close();
        scanner.close();
    }
}