package com.mycompany.ca2;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket serverSocket;
    private BufferedReader;
    private BufferedWriter;
    public String username;
    public String password;
    public static void main (String[]args )throws IOException {

        //Where the user can choose the username and enter your password
        Scanner sc = new Scanner(System.in);
        System.out.println("Username: ");
        String username = sc.nextLine();
        Scanner pwd = new Scanner(System.in);
        System.out.println("Password: ");
        String password = pwd.nextLine();

        Socket serverSocket =new Socket("localhost", Integer.parseInt("8080"));
    }

}
