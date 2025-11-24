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
            String[] requestLineArgs = requestLine.split(" ", 3);

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

            String responseLine;
            List<String> responseHeader = new ArrayList<>();
            String responseBody;

            if (requestLineArgs[1].equals("/ciao")) {
                String message = "<strong>ciao</strong> a tutti";

                responseLine = requestLineArgs[2] + " 200 OK";

                responseHeader.add("Content-Type: text/html; charset=UTF-8");
                responseHeader.add("Server: BenaServer");
                responseHeader.add("Content-Length: " + message.getBytes().length);

                responseBody = message;
            }
            else {
                String message = "Risorsa non trovata";

                responseLine = requestLineArgs[2] + " 404 Not Found";

                responseHeader.add("Content-Type: text/html; charset=UTF-8");
                responseHeader.add("Server: BenaServer");
                responseHeader.add("Content-Length: " + message.getBytes().length);

                responseBody = message;
            }

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
