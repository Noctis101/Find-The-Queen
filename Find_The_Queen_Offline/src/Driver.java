/*	Author: Duran Brooks
 *	Date: 21/03/2018
 *
 *	This is as an offline version of the game
 *	which serves as a test environment.
 *
 *	Following successful review on the part of the
 *	Testers at RealDecoy, the contents of this java file
 *	shall be reorganized to suit an online TCP Client-Server
 *	Game.
 */

import java.util.Scanner;
import java.util.Random;

public class Driver
{
	public static void main(String[] args)
	{
		System.out.println("Let's Play - Find The Queen - Five Rounds\n");
		System.out.println("\t   B-E-G-I-N !");
		
		Rounds();
		
		System.out.println("\nTHANKS FOR PLAYING!");
	}
	
	public static void Rounds()
	{
		int round = 1;
		int queenLocation = 0;
		int guessLocation = 0;
		int p1NoOfWins = 0;
		int p2NoOfWins = 0;
		
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		
		Player p1 = new Player();		
		Player p2 = new Player();
		
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
			System.out.println("\nROUND "+ round);
			
			if("Dealer" == p1.getType())
			{
				System.out.println("\nDealer, please choose a location to hide the Queen:");
				System.out.println("1  ,  2  ,  3\n");
				
				p1.setChoice(sc.nextInt());
				queenLocation = p1.getChoice();
			}
			else
			{			
				System.out.println("\nSpotter, in which location is the Queen? Choose:");
				System.out.println("1  ,  2  ,  3\n");
			
				p1.setChoice(sc.nextInt());
				guessLocation = p1.getChoice();
			}
			
			if("Dealer" == p2.getType())
			{
				System.out.println("\nDealer, please choose a location to hide the Queen:");
				System.out.println("1  ,  2  ,  3\n");
			
				p2.setChoice(sc.nextInt());
				queenLocation = p2.getChoice();
			}
			else
			{			
				System.out.println("\nSpotter, in which location is the Queen? Choose:");
				System.out.println("1  ,  2  ,  3\n");
			
				p2.setChoice(sc.nextInt());
				guessLocation = p2.getChoice();
			}
			
			if("Dealer" == p1.getType() && queenLocation != guessLocation)
			{
				System.out.println("\nSPOTTER GUESSED WRONG!\n");
				
				System.out.println("GOOD JOB, DEALER!");
				System.out.println("NUMBER OF WINS +1");
				
				p1NoOfWins+=1;
				p1.setNoOfWins(p1NoOfWins);
			}
			else if("Dealer" == p1.getType() && queenLocation == guessLocation)
			{
				System.out.println("\nSPOTTER GUESSED RIGHT!\n");
				System.out.println("TOO BAD, DEALER!");
			}
			else if("Spotter" == p1.getType() && queenLocation != guessLocation)
			{
				System.out.println("\nYOU GUESSED WRONG, SPOTTER!\n");
				
				System.out.println("TOO BAD");
			}
			else if("Spotter" == p1.getType() && queenLocation == guessLocation)
			{
				System.out.println("\nYOU GUESSED RIGHT, SPOTTER!\n");
				
				System.out.println("GREAT JOB!");
				System.out.println("NUMBER OF WINS +1");
				
				p1NoOfWins+=1;
				p1.setNoOfWins(p1NoOfWins);
			}
			
			if("Dealer" == p2.getType() && queenLocation != guessLocation)
			{
				System.out.println("\nSPOTTER GUESSED WRONG!\n");
				
				System.out.println("GOOD JOB, DEALER!");
				System.out.println("NUMBER OF WINS +1");
				
				p2NoOfWins+=1;
				p2.setNoOfWins(p2NoOfWins);
			}
			else if("Dealer" == p2.getType() && queenLocation == guessLocation)
			{
				System.out.println("\nSPOTTER GUESSED RIGHT!\n");
				System.out.println("TOO BAD, DEALER!");
			}
			else if("Spotter" == p2.getType() && queenLocation != guessLocation)
			{
				System.out.println("\nYOU GUESSED WRONG, SPOTTER!\n");
				
				System.out.println("TOO BAD");
			}
			else if("Spotter" == p2.getType() && queenLocation == guessLocation)
			{
				System.out.println("\nYOU GUESSED RIGHT, SPOTTER!\n");
				
				System.out.println("GREAT JOB!");
				System.out.println("NUMBER OF WINS +1");
				
				p2NoOfWins+=1;
				p2.setNoOfWins(p2NoOfWins);
			}
			
			if("Dealer" == p1.getType())
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
		
		if(p1NoOfWins > p2NoOfWins)
		{
			System.out.println("\nV-I-C-T-O-R-Y");
			System.out.println("Player 1");
			System.out.println("Number of wins: "+ p1.getNoOfWins());
		}
		else
		{
			System.out.println("\nD-E-F-E-A-T");
			System.out.println("Player 1");
			System.out.println("Number of wins: "+ p1.getNoOfWins());
		}
		
		if(p2NoOfWins > p1NoOfWins)
		{
			System.out.println("\nV-I-C-T-O-R-Y");
			System.out.println("Player 2");;
			System.out.println("Number of wins: "+ p2.getNoOfWins());
		}
		else
		{
			System.out.println("\nD-E-F-E-A-T");
			System.out.println("Player 2");
			System.out.println("Number of wins: "+ p2.getNoOfWins());
		}
	}
}
