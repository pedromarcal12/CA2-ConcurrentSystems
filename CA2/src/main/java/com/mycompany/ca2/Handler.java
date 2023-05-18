package com.mycompany.ca2;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

//Class that will handle all client set-ups, thread will be needed to face with different servers running at the same time
public class Handler implements Runnable{

    //List for adding all the users entering the server
    public static ArrayList<Handler> clients = new ArrayList<>();
    private Socket socket;
    public String username;
    private BufferedWriter BFWriter;
    private BufferedReader BFReader;


    public Handler(Socket socket) {
        try {
            this.socket=socket;
            this.BFWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.BFReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username = BFReader.readLine();
            clients.add(this);
        // Broadcasting message when a user connects in;
            message("User connected : " + username);

        } catch (Exception e) {
            closeEverything(socket, BFReader, BFWriter);
        }
    }

    @Override
    public void run() {
        String messageSendGroup;
        // Reading and displaying message texted by the user
        while(socket.isConnected()) {
            try {
                messageSendGroup = BFReader.readLine();
                message(username + messageSendGroup);
            }
            catch (IOException e) {
            closeEverything(socket, BFReader, BFWriter);
            break;
                }
            }
        }


    public void message(String messageSend) {
        for (Handler Handler : clients) {
          try {
              if (!Handler.username.equals(username)) {
                  // Passing string with message as parameter;
                  Handler.BFWriter.write(messageSend);
                  // Showing that there is no more data;
                  Handler.BFWriter.newLine();
                  // Flush buffer
                  Handler.BFWriter.flush();
              }
          } catch (IOException e) {
              closeEverything(socket, BFReader, BFWriter ) ;
            }
        }
    }
    public void removeUser() {
        clients.remove(this);
        message("User" + username + " has logged off");
    }
    public void closeEverything(Socket serverSocket, BufferedReader BFReader, BufferedWriter BFWriter) {
        removeUser();
        try {
            if (BFReader != null) {
                BFReader.close();
            if (BFWriter != null)
                BFWriter.close();
            if (serverSocket != null)
                serverSocket.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
