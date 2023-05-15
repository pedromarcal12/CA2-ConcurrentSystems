package com.mycompany.ca2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import com.mycompany.ca2.Server;

//Class that will handle all client set-ups, thread will be needed to face with different servers running at the same time
public class Handler implements Runnable{

    //List for adding all the users entering the server
    public static ArrayList<Handler> clients = new ArrayList<>();
    private Socket serverSocket;
    public String username;
    private BufferedWriter BFWriter;
    private BufferedReader BFReader;
    String clientMessage;

    public handler(Socket serverSocket) {
        try {
            this.serverSocket=serverSocket;
            this.BFWriter = new BufferedWriter();
            this.BFReader = new BufferedReader();
            this.username = BFReader.readLine();
            clients.add(this);
        // Broadcasting message when a user connects in;
            message("");

        } catch (Exception e) {
            System.out.println("Error on Handler class");
            closeEverything();
        }
    }

    @Override
    public void run() {

        // Reading and displaying message texted by the user
        while(serverSocket.isConnected()) {
            try {
                clientMessage = BFReader.readLine();
                message(clientMessage);
            } catch (IOException e) {
               System.out.println("Error on runnable class");
               closeEverything(serverSocket, BFReader, BFReader);
            }
        }

    }

    public void message(String clientMessage) throws IOException {
        for (Handler Handler : clients) {
          try {
              if (serverSocket.isConnected()) {
                  clientMessage = BFReader.readLine();
              }
              if (!Handler.username.equals(username)) {
                  // Passing string with message as parameter;
                  Handler.BFWriter.write(clientMessage);
                  // Showing that there is no more data;
                  Handler.BFWriter.newLine();
                  // Flush buffer
                  Handler.BFWriter.flush();
              }
          } catch (IOException e) {
              closeEverything(serverSocket, BFReader, BFWriter ) ;
            }
        }
    }
    public void removeUser() throws IOException {
        clients.remove(this);
        message("User" + username + "has logged off");
    }
}
