package com.mycompany.ca2;


import java.io.*;
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

        Socket serverSocket = new Socket("localhost", Integer.parseInt("8080"));
    }

    public Client(Socket serverSocket, String username) throws IOException {
        try {
            this.serverSocket = serverSocket;
            this.BFWriter = new BufferedWriter(new OutputStreamWriter(serverSocket.getOutputStream()));
            this.BFReader = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
            this.username = username;
        } catch (IOException e) {
            closeEverything(serverSocket, BFWriter, BFReader);
        }
    }
    public void sendMessage() {
        try {
            BFWriter.write(username);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
