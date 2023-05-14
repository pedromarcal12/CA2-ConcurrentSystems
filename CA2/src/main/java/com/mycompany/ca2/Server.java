
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

    public static void main (String[] args ) throws IOException {
        // Initializing server Socket, port number here has to match the port in Handler class
    ServerSocket server = new ServerSocket(8080);
       // Initializing server with server Socket as a parameter
    Server socketServer = new Server(server);
    socketServer.start();
    }

    private ServerSocket socket;

    public Server(ServerSocket socket) {
    this.socket = socket;
}

    // Method to start server and return error message if not connected
    public void start() {

        try {

        // Executing while loop while socket is open
                while(!socket.isClosed()) {

                    Socket serverSocket = socket.accept();
                    System.out.println("New connection : ");
                    Handler handler = new Handler(serverSocket);

                    // Creating threads to run multiple chats
                    Thread threadServer = new Thread(handler);

                }
        } catch (Exception e) {
            System.out.println("Error on Server class");
        }

    }

    //If socket value not equal to null, close it
    public void close() throws IOException {
          if (socket!=null) {
              socket.close();
          }
    }




    }

