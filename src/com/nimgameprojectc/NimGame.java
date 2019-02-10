package com.nimgameprojectc;

import java.io.Serializable;

/*
 * This class is based on implementation game playing logic which demonstrate 
 * methods like  startGame(), play() and its dependents methods
 * Mohammed Atiq Mohammed Mashaq Shaikh 25/05/2017
 * */

import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;

public class NimGame implements Serializable{

	
	private String userName,familyName,givenName,player1UserName,player2UserName,playerType;
	private int gamesPlayed=0,gamesWon=0,count=0,stoneLeft=0,upperBound=0;
	
	
	//startGame method signifies get the player details and setting up for play
	public void startGame(Scanner scanner,String commandDetails, NimPlayer nimPlayer, 
			ArrayList<?> playerList){
		
		//splitting the game or command details
		String[] splitCommandDetails =  commandDetails.split(",");
		stoneLeft = Integer.parseInt(splitCommandDetails[0]);
		upperBound = Integer.parseInt(splitCommandDetails[1]);
		player1UserName = splitCommandDetails[2];
		player2UserName = splitCommandDetails[3];
		boolean checkPlayer1UserName = checkPlayerUserName(player1UserName,playerList);
		boolean checkPlayer2UserName = checkPlayerUserName(player2UserName,playerList);
		
		//check player's username existence if no show message, if yes get details and play
		if(!checkPlayer1UserName || !checkPlayer2UserName){
			System.out.println("One of the players does not exist.");
		}
		else
		{
		String[] player1Details = getPlayerDetails(player1UserName,playerList);
		String[] player2Details = getPlayerDetails(player2UserName,playerList);
		play(scanner,stoneLeft,upperBound,player1Details,player2Details,playerList);		
		}
		
		return;
	}
	
	//Method to check whether the player is there or not in the array of objects
	public boolean checkPlayerUserName(String playerUserName, ArrayList<?> playerList){
	    boolean matchFlag=false; 
		
		for(int i=0;i<playerList.size();i++){
			if(	playerUserName.equals(((NimPlayer) playerList.get(i)).getUserName())){
				matchFlag=true;
			}
		} 
		return matchFlag;
	}
	
	//Method to get the player details if the player is available in array of objects
	public String[] getPlayerDetails(String playerUserName, ArrayList<?> playerList){
		
		String userName=null,givenName=null,familyName=null,playerType=null;
		
		for(int i=0;i<playerList.size();i++){
			if(	playerUserName.equals(((NimPlayer) playerList.get(i)).getUserName())){
				userName = ((NimPlayer) playerList.get(i)).getUserName();
				givenName = ((NimPlayer) playerList.get(i)).getGivenName();
				familyName = ((NimPlayer) playerList.get(i)).getFamilyName();
				playerType = ((NimPlayer) playerList.get(i)).getPlayerType();
			}
		}
		
		return new String[] {userName, givenName, familyName,playerType};	
	}
	
	//play method actually implements nim game playing logic
	public void play(Scanner scanner,int stoneLeft,int upperBound,String[] player1Details,
					 String[] player2Details, ArrayList<?> playerList){
		
		String p1UserName = player1Details[0];
		String p2UserName = player2Details[0];
		NimAIPlayer nimAIPlayer = new NimAIPlayer();
		
		int removeStone=0;
		boolean flag=true;
		
		//Show both the player details
		System.out.println();
		System.out.println("Initial stone count: "+stoneLeft);
		System.out.println("Maximum stone removal: "+upperBound);
		System.out.println("Player 1: "+player1Details[1]+" "+player1Details[2]);
		System.out.println("Player 2: "+player2Details[1]+" "+player2Details[2]);
		
		//check stone availability
		while(stoneLeft>0){
			
			//Check for player turn, print corresponding number of stones, 
			//remove player inputted no of stones and declare winner
			if(flag==true){
				String player1GivenName = player1Details[1];
				String player1Type = player1Details[3];
				//check for player type and then remove stone appropriately for that player
				if(player1Type.equals("h"))
				{
					printStone(stoneLeft);
					stoneLeft = checkStoneAndRemove(scanner,player1GivenName,removeStone,
							upperBound,stoneLeft);
					checkAndDeclareWinner(stoneLeft,player2Details[1],player2Details[2],p1UserName,
									  p2UserName,playerList);
				}
				else
				{
					printStone(stoneLeft);
					stoneLeft = nimAIPlayer.removeStone(player1GivenName,removeStone,
							upperBound,stoneLeft);
					checkAndDeclareWinner(stoneLeft,player2Details[1],player2Details[2],p1UserName,
						  			  p2UserName,playerList);
				}
				
			flag=false;
			}
			else{
				String player2GivenName = player2Details[1];
				String player2Type = player2Details[3];
				//check for player type and then remove stone appropriately for that player
				if(player2Type.equals("h"))
				{
					printStone(stoneLeft);
					stoneLeft = checkStoneAndRemove(scanner,player2GivenName,removeStone,
							upperBound,stoneLeft);
					checkAndDeclareWinner(stoneLeft,player1Details[1],player1Details[2],p1UserName,
									  p2UserName,playerList);
				}
				else
				{
					printStone(stoneLeft);
					stoneLeft = nimAIPlayer.removeStone(player2GivenName,removeStone,
							upperBound,stoneLeft);
					checkAndDeclareWinner(stoneLeft,player1Details[1],player1Details[2],p1UserName,
							  p2UserName,playerList);
				}
			flag=true;
			}
		} //end of while
		
		
	}

	//Method to print no of available stones 
	public void printStone(int stoneLeft){
		System.out.println();
		System.out.print(stoneLeft +" stones left:");
			for(int i=0;i<stoneLeft;i++)
				System.out.print(" *"); 
		System.out.println();
	}
	
	//Method to check number of stones available and remove stones
	public int checkStoneAndRemove(Scanner scanner,String playerGivenName,int removeStone,
									int upperBound,int stoneLeft){
		
		while(true)
		{

			
			try{
				System.out.println(playerGivenName+"'s turn - remove how many?");
				removeStone = scanner.nextInt(); 
				
				if(!String.valueOf(removeStone).matches("^[0-9]+$") || removeStone<1) {
					//throw exception if the remove stone input is invalid
					throw new InvalidInputException();
				}
				else
				{
					//Check whether the stone is available or not and upper bound of stone removal 
					//from input provided
					if(removeStone <= upperBound && removeStone > 0){
						stoneLeft = removeStone(removeStone, stoneLeft);
						break;
					}
					else
					{
						System.out.println();
						if(removeStone>upperBound)
						System.out.println("Invalid move. "
								+ "You must remove between 1 and "+upperBound+" stones.");
						else
						System.out.println("Invalid move. "
								+ "You must remove between 1 and "+stoneLeft+" stones.");
						printStone(stoneLeft);
					}
				 }
			}
			catch(InputMismatchException e)
			{
				System.out.println();
				System.out.println("Please do not enter negative, 0, "
						+ "fraction, decimal or non-numeric entries");	
				printStone(stoneLeft);
				scanner.next();
			}
			catch(InvalidInputException e)
			{
				System.out.println();
				System.out.println("Please do not enter negative, 0, "
						+ "fraction, decimal or non-numeric entries");
				printStone(stoneLeft);
			}			
		}
		return stoneLeft;
	}
	
	//Method to remove stone from the stone which are left
    int removeStone(int removeStone,int stoneLeft)
	{
		stoneLeft = stoneLeft - removeStone;
		return stoneLeft;
	}
    
    //Method to check, declare winner and update player's gameplayed and gamewo count
    public void checkAndDeclareWinner(int stoneLeft,String playerGivenName,
    				String playerFamilyName,String p1UserName,String p2UserName, 
    				ArrayList<?> playerList){
		
    	//check whether stone left is equal to 0 to declare the winner
		if(stoneLeft==0){
				System.out.println();
				System.out.println("Game Over");
				updatePlayerGameCount(p1UserName, playerList);
				updatePlayerGameCount(p2UserName, playerList);
				updatePlayerWonCount(playerGivenName, playerList);
				System.out.println(playerGivenName+" "+playerFamilyName+" wins!");
		}    	
		return ;
    }
    
	//Method to update player's game played count
    public void updatePlayerGameCount(String playerUserName, ArrayList<?> playerList){
    	
		for(Object i:playerList){
			if(	playerUserName.equals(((NimPlayer) i).getUserName())){
				((NimPlayer) i).setGamesPlayed(((NimPlayer) i).getGamesPlayed()+1);
			}
		}
    }
    
    //Method to update player's game won count
    public void updatePlayerWonCount(String playerGivenName, ArrayList<?> playerList){

		for(Object i:playerList){
			if(playerGivenName.equals(((NimPlayer) i).getGivenName())){
				((NimPlayer) i).setGamesWon(((NimPlayer) i).getGamesWon()+1);
			}
		}
    }

    //Method to calculate percentage used for ranking
    public int calculatePercentage(int gameswon,int gamesplayed){
    	if(gamesplayed==0)
    	return 0;
    	else
    	return (int)Math.round((double)(gameswon * 100)/gamesplayed);
    }
    
    //Method for sorting the player's won percentage in ascending order by Selection Sort 
    public ArrayList<?> ascSelectionSort(ArrayList<?> playerList){
    
        for (int i = 0; i < playerList.size() - 1; i++)  
        {  
            int min = i;  
            for (int j = i + 1; j < playerList.size(); j++){  
                if (((NimPlayer) playerList.get(j)).getPercentage() < 
                		((NimPlayer) playerList.get(min)).getPercentage()){  
                    min = j;//searching for min   
                }  
            }  
            Collections.swap(playerList, i, min);
        }  
    	
    	return playerList;
    }
    
    //Method for sorting the player's won percentage in ascending order
    public ArrayList<?> descSelectionSort(ArrayList<?> playerList){
        
        for (int i = 0; i < playerList.size() - 1; i++)  
        {  
            int max = i;  
            for (int j = i + 1; j < playerList.size(); j++){  
                if (((NimPlayer) playerList.get(j)).getPercentage() > 
                ((NimPlayer) playerList.get(max)).getPercentage()){  
                    max = j;//searching for max
                }  
            }  
            Collections.swap(playerList, i, max);
        }  
    	
    	return playerList;
    }
	
    //Method to sort alphabetically
    public ArrayList<?> alphaSelectionSort(ArrayList<?> playerList)
    {
    	 for (int i = 0; i < playerList.size() - 1; i++)  
         {  
             int min = i;  
             for (int j = i+1; j < playerList.size(); j++){  
                 if ((((NimPlayer) playerList.get(j)).getUserName()).compareTo(
                		 ((NimPlayer) playerList.get(min)).getUserName()) <0){  
                     min = j;//searching for min   
                 }  
             }  
             Collections.swap(playerList, i, min);
         }  
     	
     	return playerList;
    }
    
    
}
