package com.mycompany.ca2;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.regex.Pattern;

/** Client class, where the user will connect to the Server
 * Run the Server class first and here after
 * password info are all marked as a coment because it did not work
 */
public class Client {
    private Socket socket;
    public String username;
    /*public String password;*/
    private BufferedWriter BFWriter;
    private BufferedReader BFReader;
    /*private static final String PASSWORD_REGEX =
        "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,16}$";
 
    private static final Pattern PASSWORD_PATTERN =
                                Pattern.compile(PASSWORD_REGEX); */

    // Constructor for our user Client
    public Client(Socket socket, String username /*String password*/) throws IOException {
        try {
            /*this.password = password;*/
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
            BFWriter.write(":" + messageSend);
                BFWriter.newLine();
                BFWriter.flush();
            }
            // if error, close all methods
        } catch (IOException e) {
            closeEverything(socket, BFReader, BFWriter);
        }
    }
    public void lookForMessage() {

        new Thread(() -> {
            String groupChatMsg;
            while (socket.isConnected()) {
                try {
                    groupChatMsg = BFReader.readLine();
                    System.out.println(groupChatMsg);
                } catch (IOException e) {
                    closeEverything(socket, BFReader, BFWriter);
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
    /* public void password() {
        if (PASSWORD_PATTERN.matcher(password).matches()) {
            System.out.print("The Password " + password + " is valid");
        }
        else {
            System.out.print("The Password " + password + " isn't valid");
        }
    } */

    public static void main(String[] args) throws IOException {

       
        Scanner pwd = new Scanner(System.in);
        //Where the user can choose the username and enter your password
        Scanner user = new Scanner(System.in);
        System.out.println("Enter Username: ");
        String username = user.nextLine();
        System.out.println("Enter Password: ");
        //String password = pwd.nextLine();
        Socket socket = new Socket("localhost", 1235);
        Client client = new Client(socket, username /*password*/ );
        client.lookForMessage();
        /*client.password();*/
        client.sendMessage();
        
      
}
}