package it.benassai.Threads;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class HTTPServerRunnable implements Runnable {
    private Socket socket;

    private BufferedReader socketIn;
    private PrintWriter socketOut;

    public HTTPServerRunnable(Socket socket) throws IOException {
        this.socket = socket;

        socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        socketOut = new PrintWriter(socket.getOutputStream(), true);
    }

    @Override
    public void run() {
        try {
            String requestLine;
            List<String> requestHeader = new ArrayList<>();
            List<String> requestBody = new ArrayList<>();

            requestLine = socketIn.readLine();
            String[] requestLineArgs = requestLine.split(" ");

            String line = socketIn.readLine();
            while (!line.equals("")) {
                requestHeader.add(line);
                line = socketIn.readLine();
            }

            if (!requestLineArgs[0].equals("GET")) {
                line = socketIn.readLine();
                while (!line.equals("")) {
                    requestBody.add(line);
                    line = socketIn.readLine();
                }
            }

            //

            String message = "ciao";

            String responseLine = requestLineArgs[2] + " 200 OK";

            List<String> responseHeader = new ArrayList<>();

            responseHeader.add("Content-Type: text/html; charset=UTF-8");
            responseHeader.add("Server: BenaServer");
            responseHeader.add("Content-Length: " + message.getBytes().length);

            String responseBody = message;


            socketOut.println(responseLine);
            for (String head : responseHeader) {
                socketOut.println(head);
            }
            socketOut.println("");
            socketOut.println(responseBody);
            socketOut.println("");
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    
}
