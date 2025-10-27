package it.benassai.Protocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import it.benassai.Protocol.Exceptions.ProtocolException;

public class ServerProtocol implements Runnable {
    
    private static final String version = "1.0.0";

    private BufferedReader socketIn;
    private PrintWriter socketOut;

    public ServerProtocol(Socket socket) throws IOException {
        socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        socketOut = new PrintWriter(socket.getOutputStream(), true);
    }

    public void run() {
        try {
            socketOut.println("Server | v" + version);

            while (true) {
                String operation = socketIn.readLine();

                if (operation.equals("0")) {
                    System.out.println("disconnesso");
                    return;
                }
                
                String num1 = socketIn.readLine();
                String num2 = socketIn.readLine();

                String result;
                try {
                    result = "OK:" + calculate(operation, num1, num2);
                } catch (ProtocolException e) {
                    result = "KO:" + e.getMessage();
                }

                socketOut.println(result);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private String calculate(String operation, String num1, String num2) throws ProtocolException {
        double numDouble1;
        double numDouble2;

        try {
            numDouble1 = Double.parseDouble(num1);
            numDouble2 = Double.parseDouble(num2);
        } catch (NumberFormatException e) {
            throw new ProtocolException("INVALID_NUMBER");
        }

        double result;

        switch (operation) {
            case "1":
                result = numDouble1 + numDouble2;
                break;
            case "2":
                result = numDouble1 - numDouble2;
                break;
            case "3":
                result = numDouble1 * numDouble2;
                break;
            case "4":
                if (numDouble2 == 0)
                    throw new ProtocolException("DIV_BY_ZERO");

                result = numDouble1 / numDouble2;
                break;
            default:
                throw new ProtocolException("INVALID_OPERATION");
        }

        return Double.toString(result);
    }

}
