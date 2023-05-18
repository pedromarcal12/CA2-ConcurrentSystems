package com.mycompany.ca2;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/** Client class, where the user will connect to the Server
 * Run the Server class first and here after
 */
public class Client {
    private Socket socket;
    public String username;
    private BufferedWriter BFWriter;
    private BufferedReader BFReader;

    // Constructor for our user Client
    public Client(Socket socket, String username) throws IOException {
        try {
            this.socket = socket;
            this.BFWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.BFReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username = username;
           // If error, close all methods
        } catch (IOException e) {
            closeEverything(socket, BFReader, BFWriter);
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
            while (socket.isConnected()) {
                String messageSend = scanner.nextLine();
            BFWriter.write("username:" +  messageSend);
                BFWriter.newLine();
                BFWriter.flush();
            }
            // if error, close all methods
        } catch (IOException e) {
            closeEverything(socket, BFReader, BFWriter);
        }
    }
    public void lookForMessage() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                String groupChatMsg;
                while (socket.isConnected()) {
                    try {
                        groupChatMsg = BFReader.readLine();
                        System.out.println(groupChatMsg);
                    } catch (IOException e) {
                       closeEverything(socket, BFReader, BFWriter);
                    }
                }
            }
        }).start();
    }
    public void closeEverything(Socket socket, BufferedReader BFReader, BufferedWriter BFWriter) {
        try {
            if (BFReader != null) {
                BFReader.close();
                if (BFWriter !=null)
                    BFWriter.close();
                if (socket !=null)
                    socket.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IOException {

        //Where the user can choose the username and enter your password
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Username: ");
        String username = sc.nextLine();
        Socket socket = new Socket("localhost", 1234);
        Client client = new Client(socket, username);
        client.lookForMessage();
        client.sendMessage();
    }
}
