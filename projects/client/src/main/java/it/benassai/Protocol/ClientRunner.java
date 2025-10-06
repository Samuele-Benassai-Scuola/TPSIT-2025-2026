package it.benassai.Protocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientRunner implements Runnable {
    private Scanner scanner = new Scanner(System.in);

    private BufferedReader socketIn;
    private PrintWriter socketOut;

    public ClientRunner(Socket socket) throws IOException {
        socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        socketOut = new PrintWriter(socket.getOutputStream(), true);
    }

    /*
        Protocollo:
        1) server invia versione
        2) client invia operazione (
            'M': message
            'E': exit
            (ogni altro è ignorato)
        )
        3) (se message) client invia messaggio
        4) server invia messaggio in maiuscolo
        5) ritorna al punto 2
    */

    @Override
    public void run() {
        try {
            String metadata = socketIn.readLine();
            System.out.println(metadata);

            while (true) {
                System.out.println("Si vuole uscire? (se si, digitare 'exit')");
                String operation = scanner.nextLine();

                if (operation.equals("exit")) {
                    socketOut.println("E");
                } 
                else {
                    socketOut.println("M");
                }

                String resultOperation = socketIn.readLine();
                
                switch (resultOperation) {
                    case "exit":
                        return;
                    case "message":
                        System.out.println("Digitare messsaggio");
                        String message = scanner.nextLine();
                        socketOut.println(message);
        
                        String resultMessage = socketIn.readLine();
                        System.out.println("Messaggio in maiuscolo: " + resultMessage);
                        break;
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
