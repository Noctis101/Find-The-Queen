/*	Author: Duran Brooks
 *	Date: 21/03/2018
 *
 *	Attempt to changeover offline version to online version.
 *
 *	This version serves as the TCP Server.
 *	
 *	Limitations:	(1) "dannyboi" hard-coded to player 1
 *			(2) "matty7" hard-coded player 2
 *			(3) Without a GUI, players will share the console.
 */

import java.io.*;
import java.net.*;
import java.util.Random;

public class Driver
{	
	public void main(String args[]) throws IOException
	{
		private String savedUserName1 = "dannyboi";
		private String savedUserName2 = "matty7";
	
		private String savedUser1PW = "dre@margh_shelled";
		private String savedUser2PW = "win&win99";
	
		String connectMessage = "Username and/or password authorized. Beginning Game!";
		String errorMessage = "Username and/or password not authorized. Ending Game!";
		String startMessage = "Let's Play - Find The Queen - Five Rounds";
	
		String message;
		int noOfWins = 0;
		int gameState1 = 0;
		int gameState2 = 0;
	
		Player p1 = new Player(savedUserName1,savedUser1PW);
		Player p2 = new Player(savedUserName2,savedUser2PW);
	
		int round = 1;
		int queenLocation = 0;
		int guessLocation = 0;
		int p1NoOfWins = 0;
		int p2NoOfWins = 0;
		
		String username = " ";
		String password = " ";
		
		@SuppressWarnings("resource")
		ServerSocket Socket = new ServerSocket(7621);

		while(true)
		{
			System.out.println("Waiting for players to connect...");
			
			Socket connectionSocket = Socket.accept();
			
			new ServerThread(socket).start();
		}
		
		Socket.close();
	}
	
	public class ServerThread extends Thread
	{
		Socket socket;
		
		ServerThread(Socket socket)
		{
			this.socket = socket;
		}
		
		@Override
		public void run()
		{
		
			BufferedReader fromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			DataOutputStream toClient = new DataOutputStream(connectionSocket.getOutputStream());
			
			username = fromClient.readLine();
			password = fromClient.readLine();
			
			if(username == savedUserName1 && password == savedUser1PW)
			{
				System.out.println("Player Connected!\n");
				gameState1 = 1;
				
				toClient.writeBytes(username);
				
				toClient.writeBytes(connectMessage);
				toClient.writeBytes(startMessage);
				toClient.writeByte(gameState1);
				
				/* Rounds no longer a separate function
				* due to the toClient function being within
				* main.
				*/

				Random rand = new Random();

				int num1 = rand.nextInt(10) + 1;
				int num2 = rand.nextInt(10) + 1;

				if(num1 > num2)
				{
					p1.setType("Dealer");
					p2.setType("Spotter");
				}
				else if(num2 > num1)
				{
					p1.setType("Spotter");
					p2.setType("Dealer");
				}
				else if(num1 == num2)
				{
					p1.setType("Dealer");	//Here, player one becomes the dealer due to the bias
					p2.setType("Spotter");	//of being player one. 
				}
					
				while(round < 6)
				{
					message = "\nROUND "+ round;
					toClient.writeBytes(message);

					if("Dealer" == p1.getType())
					{
						message = "\nDealer, please choose a location to hide the Queen:";
						toClient.writeBytes(message);

						message = "1  ,  2  ,  3\n";
						toClient.writeBytes(message);

						p1.setChoice(fromClient.read());
						queenLocation = p1.getChoice();
					}
					else
					{			
						message = "\nSpotter, in which location is the Queen? Choose:";
						toClient.writeBytes(message);

						message = "1  ,  2  ,  3\n";
						toClient.writeBytes(message);

						p1.setChoice(fromClient.read());
						guessLocation = p1.getChoice();
					}

					if("Dealer" == p2.getType())
					{
						message = "\nDealer, please choose a location to hide the Queen:";
						toClient.writeBytes(message);

						message = "1  ,  2  ,  3\n";
						toClient.writeBytes(message);

						p2.setChoice(fromClient.read());
						queenLocation = p2.getChoice();
					}
					else
					{			
						message = "\nSpotter, in which location is the Queen? Choose:";
						toClient.writeBytes(message);

						message = "1  ,  2  ,  3\n";
						toClient.writeBytes(message);

						p2.setChoice(fromClient.read());
						guessLocation = p2.getChoice();
					}
						
					if("Dealer" == p1.getType() && queenLocation != guessLocation)
					{
						message = "\nSPOTTER GUESSED WRONG!\n";
						toClient.writeBytes(message);

						message = "GOOD JOB, DEALER!\nNUMBER OF WINS +1";
						toClient.writeBytes(message);

						p1NoOfWins+=1;
						p1.setNoOfWins(p1NoOfWins);
					}
					else if("Dealer" == p1.getType() && queenLocation == guessLocation)
					{
						message = "\nSPOTTER GUESSED RIGHT!\n";
						toClient.writeBytes(message);

						message = "TOO BAD, DEALER!";
						toClient.writeBytes(message);
					}
					else if("Spotter" == p1.getType() && queenLocation != guessLocation)
					{
						message = "\nYOU GUESSED WRONG, SPOTTER!\n";
						toClient.writeBytes(message);

						message = "TOO BAD";
						toClient.writeBytes(message);
					}
					else if("Spotter" == p1.getType() && queenLocation == guessLocation)
					{
						message = "\nYOU GUESSED RIGHT, SPOTTER!\n";
						toClient.writeBytes(message);

						message = "GREAT JOB!\nNUMBER OF WINS +1";
						toClient.writeBytes(message);

						p1NoOfWins+=1;
						p1.setNoOfWins(p1NoOfWins);
					}
						
					if("Dealer" == p2.getType() && queenLocation != guessLocation)
					{
						message = "\nSPOTTER GUESSED WRONG!\n";
						toClient.writeBytes(message);

						message = "GOOD JOB, DEALER!\nNUMBER OF WINS +1";
						toClient.writeBytes(message);

						p2NoOfWins+=1;
						p2.setNoOfWins(p2NoOfWins);
					}	
					else if("Dealer" == p2.getType() && queenLocation == guessLocation)
					{
						message = "\nSPOTTER GUESSED RIGHT!\n";
						toClient.writeBytes(message);

						message = "TOO BAD, DEALER!";
						toClient.writeBytes(message);
					}
					else if("Spotter" == p2.getType() && queenLocation != guessLocation)
					{
						message = "\nYOU GUESSED WRONG, SPOTTER!\n";
						toClient.writeBytes(message);

						message = "TOO BAD";
						toClient.writeBytes(message);
					}
					else if("Spotter" == p2.getType() && queenLocation == guessLocation)
					{
						message = "\nYOU GUESSED RIGHT, SPOTTER!\n";
						toClient.writeBytes(message);

						message = "GREAT JOB!\nNUMBER OF WINS +1";
						toClient.writeBytes(message);

						p2NoOfWins+=1;
						p2.setNoOfWins(p2NoOfWins);
					}
						
					if("Dealer" == p1.getType()) //handles changing players role each round
					{
						p1.setType("Spotter");
						p2.setType("Dealer");
					}
					else if("Spotter" == p1.getType())
					{
						p1.setType("Dealer");
						p2.setType("Spotter");
					}
						
					round++;
				}

				gameState1 = 0;
				toClient.writeByte(gameState1);

				/* Decider no longer a separate function
				* due to the toClient function being within
				* main.
				*/
				
				if(p1NoOfWins > p2NoOfWins)
				{
					message = "\nV-I-C-T-O-R-Y";
					toClient.writeBytes(message);

					noOfWins = p1.getNoOfWins();
					toClient.writeByte(noOfWins);
				}
				else
				{
					message = "\nD-E-F-E-A-T";
					toClient.writeBytes(message);

					noOfWins = p1.getNoOfWins();
					toClient.writeByte(noOfWins);
				}

				if(p2NoOfWins > p1NoOfWins)
				{
					message = "\nV-I-C-T-O-R-Y";
					toClient.writeBytes(message);

					noOfWins = p2.getNoOfWins();
					toClient.writeByte(noOfWins);
				}
				else
				{
					message = "\nD-E-F-E-A-T";
					toClient.writeBytes(message);

					noOfWins = p2.getNoOfWins();
					toClient.writeByte(noOfWins);
				}

				gameState2 = 0;
				toClient.writeByte(gameState2);
			}	
			else if(username == savedUserName2 && password == savedUser2PW)
			{
				System.out.println("Player Connected!\n");
				gameState1 = 1;

				toClient.writeBytes(username);

				toClient.writeBytes(connectMessage);
				toClient.writeBytes(startMessage);
				toClient.writeByte(gameState1);

				Random rand = new Random();

				int num1 = rand.nextInt(10) + 1;
				int num2 = rand.nextInt(10) + 1;
					
				if(num1 > num2)
				{
					p1.setType("Dealer");
					p2.setType("Spotter");
				}
				else if(num2 > num1)
				{
					p1.setType("Spotter");
					p2.setType("Dealer");
				}
				else if(num1 == num2)
				{
					p1.setType("Dealer");	//Here, player one becomes the dealer due to the bias
					p2.setType("Spotter");	//of being player one. 
				}
					
				while(round < 6)
				{
					message = "\nROUND "+ round;
					toClient.writeBytes(message);

					if("Dealer" == p1.getType())
					{
						message = "\nDealer, please choose a location to hide the Queen:";
						toClient.writeBytes(message);

						message = "1  ,  2  ,  3\n";
						toClient.writeBytes(message);

						p1.setChoice(fromClient.read());
						queenLocation = p1.getChoice();
					}
					else
					{			
						message = "\nSpotter, in which location is the Queen? Choose:";
						toClient.writeBytes(message);

						message = "1  ,  2  ,  3\n";
						toClient.writeBytes(message);
						
						p1.setChoice(fromClient.read());
						guessLocation = p1.getChoice();
					}

					if("Dealer" == p2.getType())
					{
						message = "\n\nDealer, please choose a location to hide the Queen:";
						toClient.writeBytes(message);

						message = "1  ,  2  ,  3\n";
						toClient.writeBytes(message);

						p2.setChoice(fromClient.read());
						queenLocation = p2.getChoice();
					}
					else
					{			
						message = "Spotter, in which location is the Queen? Choose:";
						toClient.writeBytes(message);

						message = "1  ,  2  ,  3\n";
						toClient.writeBytes(message);

						p2.setChoice(fromClient.read());
						guessLocation = p2.getChoice();
					}

					if("Dealer" == p1.getType() && queenLocation != guessLocation)
					{
						message = "\nSPOTTER GUESSED WRONG!\n";
						toClient.writeBytes(message);

						message = "GOOD JOB, DEALER!\nNUMBER OF WINS +1";
						toClient.writeBytes(message);

						p1NoOfWins++;
						p1.setNoOfWins(p1NoOfWins);
					}
					else if("Dealer" == p1.getType() && queenLocation == guessLocation)
					{
						message = "\nSPOTTER GUESSED RIGHT!\n";
						toClient.writeBytes(message);

						message = "TOO BAD, DEALER!";
						toClient.writeBytes(message);
					}
					else if("Spotter" == p1.getType() && queenLocation != guessLocation)
					{
						message = "\nYOU GUESSED WRONG, SPOTTER!\n";
						toClient.writeBytes(message);

						message = "TOO BAD";
						toClient.writeBytes(message);
					}
					else if("Spotter" == p1.getType() && queenLocation == guessLocation)
					{
						message = "\nYOU GUESSED RIGHT, SPOTTER!\n";
						toClient.writeBytes(message);

						message = "GREAT JOB!\nNUMBER OF WINS +1";
						toClient.writeBytes(message);

						p1NoOfWins++;
						p1.setNoOfWins(p1NoOfWins);
					}

					if("Dealer" == p2.getType() && queenLocation != guessLocation)
					{
						message = "\nSPOTTER GUESSED WRONG!\n";
						toClient.writeBytes(message);

						message = "GOOD JOB, DEALER!\nNUMBER OF WINS +1";
						toClient.writeBytes(message);

						p2NoOfWins++;
						p2.setNoOfWins(p2NoOfWins);
					}
					else if("Dealer" == p2.getType() && queenLocation == guessLocation)
					{
						message = "\nSPOTTER GUESSED RIGHT!\n";
						toClient.writeBytes(message);

						message = "TOO BAD, DEALER!";
						toClient.writeBytes(message);
					}
					else if("Spotter" == p2.getType() && queenLocation != guessLocation)
					{
						message = "\nYOU GUESSED WRONG, SPOTTER!\n";
						toClient.writeBytes(message);

						message = "TOO BAD";
						toClient.writeBytes(message);
					}
					else if("Spotter" == p2.getType() && queenLocation == guessLocation)
					{
						message = "\nYOU GUESSED RIGHT, SPOTTER!\n";
						toClient.writeBytes(message);

						message = "GREAT JOB!\nNUMBER OF WINS +1";
						toClient.writeBytes(message);

						p2NoOfWins++;
						p2.setNoOfWins(p2NoOfWins);
					}
						
					if("Dealer" == p1.getType()) //handles changing players role each round
					{
						p1.setType("Spotter");
						p2.setType("Dealer");
					}
					else if("Spotter" == p1.getType())
					{
						p1.setType("Dealer");
						p2.setType("Spotter");
					}

					round++;
				}

				gameState1 = 0;
				toClient.writeByte(gameState1);
				
				if(p1NoOfWins > p2NoOfWins)
				{
					message = "\nV-I-C-T-O-R-Y";
					toClient.writeBytes(message);

					noOfWins = p1.getNoOfWins();
					toClient.writeByte(noOfWins);

					message = "\nTHANKS FOR PLAYING!";
					toClient.writeBytes(message);
				}
				else
				{
					message = "\nD-E-F-E-A-T";
					toClient.writeBytes(message);

					noOfWins = p1.getNoOfWins();
					toClient.writeByte(noOfWins);

					message = "\nTHANKS FOR PLAYING!";
					toClient.writeBytes(message);
				}

				if(p2NoOfWins > p1NoOfWins)
				{
					message = "\nV-I-C-T-O-R-Y";
					toClient.writeBytes(message);

					noOfWins = p2.getNoOfWins();
					toClient.writeByte(noOfWins);

					message = "\nTHANKS FOR PLAYING!";
					toClient.writeBytes(message);
				}
				else
				{
					message = "\nD-E-F-E-A-T";
					toClient.writeBytes(message);

					noOfWins = p2.getNoOfWins();
					toClient.writeByte(noOfWins);

					message = "\nTHANKS FOR PLAYING!";
					toClient.writeBytes(message);
				}

					gameState2 = 0;
					toClient.writeByte(gameState2);
				}
			else
			{
				toClient.writeBytes(errorMessage);
				break;
			}
		}
	}		
}
