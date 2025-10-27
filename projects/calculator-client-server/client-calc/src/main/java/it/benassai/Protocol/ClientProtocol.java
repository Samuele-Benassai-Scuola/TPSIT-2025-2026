package it.benassai.Protocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientProtocol implements Runnable {

    private Scanner scanner = new Scanner(System.in);

    private BufferedReader socketIn;
    private PrintWriter socketOut;

    public ClientProtocol(Socket socket) throws IOException {
        socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        socketOut = new PrintWriter(socket.getOutputStream(), true);
    }

    public void run() {
        try {
            String metadata = socketIn.readLine();
            System.out.println(metadata);

            while (true) {
                System.out.println("Operazione (0:exit, 1:+, 2:-, 3:*, 4:/)");
                String operation = scanner.nextLine();
                socketOut.println(operation);

                if (operation.equals("0"))
                    break;

                System.out.println("Digitare numero 1");
                String num1 = scanner.nextLine();
                socketOut.println(num1);

                System.out.println("Digitare numero 2");
                String num2 = scanner.nextLine();
                socketOut.println(num2);


                String result = socketIn.readLine();
                
                System.out.println(result);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

}
