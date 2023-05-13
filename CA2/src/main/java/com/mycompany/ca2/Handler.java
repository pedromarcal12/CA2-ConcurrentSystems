package com.mycompany.ca2;

import java.net.Socket;
import java.util.ArrayList;
import com.mycompany.ca2.Server;

//Class that will handle all client set-ups, thread will be needed to face with different servers running at the same time
public class Handler implements Runnable{

    //List for adding all the users entering the server

    public static ArrayList<Handler> handler = new ArrayList<>();
    private Socket serverSocket;
    public String username;


    public Handler(Socket serverSocket) {
        try {
            this.serverSocket=serverSocket;

        } catch (Exception e) {

        }
    }


    @Override
    public void run() {


    }
}
