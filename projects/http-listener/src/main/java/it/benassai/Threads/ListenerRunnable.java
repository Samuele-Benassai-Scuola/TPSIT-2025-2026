package it.benassai.Threads;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ListenerRunnable implements Runnable {
    private Socket socket;

    private BufferedReader socketIn;
    private PrintWriter socketOut;

    public ListenerRunnable(Socket socket) throws IOException {
        this.socket = socket;

        socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        socketOut = new PrintWriter(socket.getOutputStream(), true);
    }

    @Override
    public void run() {
        try {
            String message;
            while (true) {
                message = socketIn.readLine();
                if (message != null)
                    System.out.println(message);
            }
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    
}
