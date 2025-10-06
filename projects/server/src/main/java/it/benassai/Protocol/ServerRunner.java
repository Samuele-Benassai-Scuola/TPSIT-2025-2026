package it.benassai.Protocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerRunner implements Runnable {
    private static final String version = "1.0.0";

    private BufferedReader socketIn;
    private PrintWriter socketOut;

    public ServerRunner(Socket socket) throws IOException {
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
            socketOut.println("Server | v" + version);

            while (true) {
                String operation = socketIn.readLine();
                
                switch (operation) {
                    case "E":
                        socketOut.println("exit");
                        return;
                    case "M":
                        socketOut.println("message");

                        String message = socketIn.readLine();
                        String resultMessage = message.toUpperCase();
                        socketOut.println(resultMessage);
                        break;
                    default:
                        socketOut.println("noop");
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
