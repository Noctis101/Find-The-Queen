package com.company;

/*	Author: Duran Brooks
 *	Date: 21/03/2018
 *
 *	Attempt to changeover offline version to online version.
 *
 *	This version serves as the TCP Client.
 */

import java.io.*;
import java.net.*;

public class Player
{
    @SuppressWarnings("unused")
    private String Username;

    @SuppressWarnings("unused")
    private String Password;

    private String Type;
    private int Choice;
    private int NoOfWins;

    Player()
    {
        Username = " ";
        Password = " ";
        Type = " ";
        Choice = 0;
        NoOfWins = 0;
    }

    Player(String username, String password)
    {
        Username = username;
        Password = password;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public int getChoice() {
        return Choice;
    }

    public void setChoice(int choice) {
        Choice = choice;
    }

    public int getNoOfWins() {
        return NoOfWins;
    }

    public void setNoOfWins(int noOfWins) {
        this.NoOfWins = noOfWins;
    }

    public static void main(String args[]) throws IOException
    {
        String username;
        String password;

        int choice;
        int noOfWins;

        String message;
        String gameMessage1;
        String gameMessage2;
        int gameState1;
        int gameState2;

        Socket clientSocket = new Socket("localhost", 7621);

        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        System.out.println("Please enter your username: ");
        username = inFromUser.readLine();

        System.out.println("\nPlease enter your password: ");
        password = inFromUser.readLine();

        outToServer.writeBytes(username);
        outToServer.writeBytes(password);

        gameMessage1 = inFromServer.readLine();

        if("Username and/or password authorized. Beginning Game!" == gameMessage1)
        {
            username = inFromServer.readLine();

            if(username == "dannyboi")
            {
                gameMessage2 = inFromServer.readLine();
                gameState1 = inFromServer.read();

                while(gameState1 != 0)
                {
                    System.out.println(""+ gameMessage1);
                    System.out.println("");
                    System.out.println(""+ gameMessage2);

                    //while loop of rounds from Server begins here
                    message = inFromServer.readLine();
                    System.out.println(""+ message);

                    message = inFromServer.readLine();
                    System.out.println(""+ message);

                    message = inFromServer.readLine();
                    System.out.println(""+ message);

                    choice = inFromUser.read();
                    outToServer.write(choice);

                    message = inFromServer.readLine();
                    System.out.println(""+ message);

                    message = inFromServer.readLine();
                    System.out.println(""+ message);

                    gameState1 = inFromServer.read();
                }

                gameState2 = inFromServer.read();

                while(gameState2 != 0)
                {
                    message = inFromServer.readLine();
                    System.out.println(""+ message);

                    noOfWins = inFromServer.read();
                    System.out.println("Number of wins:"+ noOfWins);
                }
            }
            else if(username == "matty7")
            {
                gameMessage2 = inFromServer.readLine();
                gameState1 = inFromServer.read();

                while(gameState1 != 0)
                {
                    System.out.println(""+ gameMessage1);
                    System.out.println("");
                    System.out.println(""+ gameMessage2);

                    //while loop of rounds from Server begins here
                    message = inFromServer.readLine();
                    System.out.println(""+ message);

                    message = inFromServer.readLine();
                    System.out.println(""+ message);

                    message = inFromServer.readLine();
                    System.out.println(""+ message);

                    choice = inFromUser.read();
                    outToServer.write(choice);

                    message = inFromServer.readLine();
                    System.out.println(""+ message);

                    message = inFromServer.readLine();
                    System.out.println(""+ message);

                    gameState1 = inFromServer.read();
                }

                gameState2 = inFromServer.read();

                while(gameState2 != 0)
                {
                    message = inFromServer.readLine();
                    System.out.println(""+ message);

                    noOfWins = inFromServer.read();
                    System.out.println("Number of wins:"+ noOfWins);

                    message = inFromServer.readLine();
                    System.out.println(""+ message);
                }
            }
        }
        else if("Username and/or password not authorized. Ending Game!" == gameMessage1)
        {
            System.out.println(""+ gameMessage1);
        }

        clientSocket.close();
    }
}
