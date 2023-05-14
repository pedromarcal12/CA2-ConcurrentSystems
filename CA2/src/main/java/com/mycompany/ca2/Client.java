package com.mycompany.ca2;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
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

    public static void main (String[]args )throws IOException {

        //Where the user can choose the username and enter your password
        Scanner sc = new Scanner(System.in);
        System.out.println("Username: ");
        String username = sc.nextLine();

        Socket serverSocket =new Socket("localhost", Integer.parseInt("8080"));
    }

}
