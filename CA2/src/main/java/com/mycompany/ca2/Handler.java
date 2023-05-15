package com.mycompany.ca2;

import java.io.*;
import java.net.ServerSocket;
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

    public Handler(Socket serverSocket) throws IOException {
        try {
            this.serverSocket=serverSocket;
            this.BFWriter = new BufferedWriter(new OutputStreamWriter(serverSocket.getOutputStream()));
            this.BFReader = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
            this.username = BFReader.readLine();
            clients.add(this);
        // Broadcasting message when a user connects in;
            message("");

        } catch (Exception e) {
            System.out.println("Error on Handler class");
            closeEverything(serverSocket, BFReader, BFWriter);
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
                try {
                    closeEverything(serverSocket, BFReader, BFWriter);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
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
    public void closeEverything(Socket serverSocket, BufferedReader BFReader, BufferedWriter BFWriter) throws IOException {
        removeUser();
        try {
            if (BFReader != null && BFWriter !=null && serverSocket !=null) {
                BFReader.close();
                BFWriter.close();
                serverSocket.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
