/*	Author: Duran Brooks
 *	Date: 21/03/2018
 *
 *	Here is the Player class, which will be utilized
 *	in creating the profile for the two players in the
 *	offline version of the game.
 */

public class Player
{
	private String Type;  // stores the interchangeable roles of 'Dealer' and 'Spotter'
	private int Choice;	  // records player's choice during each round.
	private int NoOfWins; // records the player's number of wins throughout the game

	Player()
	{
		Type = " ";
		Choice = 0;
		NoOfWins = 0;
	}
	
	public String getType(){
		return Type;
	}

	public void setType(String type){
		Type = type;
	}

	public int getChoice(){
		return Choice;
	}

	public void setChoice(int choice){
		this.Choice = choice;
	}
	
	public int getNoOfWins() {
		return NoOfWins;
	}

	public void setNoOfWins(int noOfWins) {
		this.NoOfWins = noOfWins;
	}
}
