
package com.mycompany.ca2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Pedro Henrique Simoes Marcal - 2020300
 * YouChat
 * A multiple group chat application
 * Server method, where the server will be initialized and started.
 * Run here first and Client after.
   */
public class Server {

    private ServerSocket serverSocket;
    public static void main (String[] args ) throws IOException {
        // Initializing server Socket, port number here has to match the port in Handler class
    ServerSocket serverSocket = new ServerSocket(1111);
       // Initializing server with server Socket as a parameter
    Server server = new Server(serverSocket);
    server.start();
    }

    public Server(ServerSocket serverSocket) {
    this.serverSocket = serverSocket;
}

    // Method to start server and return error message if not connected
    public void start() {

        try {

        // Executing while loop while socket is open
                while(!serverSocket.isClosed()) {

                    Socket socket = serverSocket.accept();
                    System.out.println("New connection!");
                    Handler handler = new Handler(socket);

                    // Creating threads to run multiple chats
                    Thread threadServer = new Thread(handler);
                    threadServer.start();
                }
        } catch (Exception e) {
            System.out.println("Error on Server class");
        }

    }

    //If socket value not equal to null, close it
    public void close() throws IOException {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            System.out.println("Error closing");
        }


    }
    }

