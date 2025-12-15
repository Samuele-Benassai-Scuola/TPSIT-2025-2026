package it.benassai.runnables;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer implements Runnable {
    private ServerSocket serverSocket;

    public WebServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    @Override
    public void run() {
        try {
            try {
                while (true) {
                    Socket socket = serverSocket.accept();
                    System.out.println(socket.getInetAddress());

                    Thread thread = new Thread(new ConnectionServer(socket));
                    thread.start();
                }
            }
            finally {
                serverSocket.close();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
    }
}
