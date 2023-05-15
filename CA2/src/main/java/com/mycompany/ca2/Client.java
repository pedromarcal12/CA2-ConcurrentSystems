package com.mycompany.ca2;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/** Client class, where the user will connect to the Server
 * Run the Server class first and here after
 */
public class Client {
    private Socket serverSocket;
    public String username;
    private BufferedWriter BFWriter;
    private BufferedReader BFReader;

    public static void main(String[] args) throws IOException {

        //Where the user can choose the username and enter your password
        Scanner sc = new Scanner(System.in);
        System.out.println("Username: ");
        String username = sc.nextLine();
        Socket serverSocket = new Socket(1234);
        Client client = new Client(serverSocket, username);
        client.lookForMessage();
        client.sendMessage();
    }

    // Constructor for our user Client
    public Client(Socket serverSocket, String username) throws IOException {
        try {
            this.serverSocket = serverSocket;
            this.BFWriter = new BufferedWriter(new OutputStreamWriter(serverSocket.getOutputStream()));
            this.BFReader = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
            this.username = username;
           // If error, close all methods
        } catch (IOException e) {
            closeEverything(serverSocket, BFReader, BFWriter);
        }
    }

    // Method for sending message to the server with try-catch loop
    public void sendMessage() {
        try {
            BFWriter.write(username);
            BFWriter.newLine();
            BFWriter.flush();
            Scanner scanner = new Scanner(System.in);
            // while loop to display name of the users connected within our server
            while (!serverSocket.isConnected()) {
                String message = scanner.nextLine();
                BFWriter.write("Username" + ":" + message);
                BFWriter.newLine();
                BFWriter.flush();
            }
            // if error, close all methods
        } catch (IOException e) {
            closeEverything(serverSocket, BFReader, BFWriter);
        }
    }
    public void lookForMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String groupChatMsg;

                while (serverSocket.isConnected()) {
                    try {
                        groupChatMsg = BFReader.readLine();
                        System.out.println(groupChatMsg);
                    } catch (IOException e) {
                       closeEverything(serverSocket, BFReader, BFWriter);
                    }
                }
            }
        }).start();
    }
    public void closeEverything(Socket serverSocket, BufferedReader BFReader, BufferedWriter BFWriter) {
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
