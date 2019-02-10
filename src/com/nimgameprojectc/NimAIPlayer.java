package com.nimgameprojectc;

/*
 * This class is based on implementation of NimHumanPlayer 
 * for Artificial Intelligence Player specific functionality
 * such as intelligent way of removing removing stone
 * Mohammed Atiq Mohammed Mashaq Shaikh 25/05/2017
 * */

public class NimAIPlayer extends NimPlayer implements Testable{

	private static final long serialVersionUID = 6522685098267757690L;
	
	//intelligent way of removing the stone
	public int removeStone(String playerGivenName, int removeStone, int upperBound, int stoneLeft){
		//check if stone is left if yes proceed
		if(stoneLeft>0){
		//iterate to get the victory guaranteed stone for removal 
	    int i=1;
	    while(i<=upperBound && i<stoneLeft){
	    	  //calculating victory move
	    	  if ((stoneLeft - i - 1) % (upperBound + 1) == 0){
	    		  System.out.println(playerGivenName+"'s turn - remove how many?");
	    		  stoneLeft = stoneLeft - i;
	    		  return stoneLeft;
	    	  }
	    i++;
	    }
		}
		
		System.out.println(playerGivenName+"'s turn - remove how many?");
		stoneLeft = stoneLeft - 1;
		return stoneLeft;
	}
	
	//check advance move for testable 
	public String advancedMove(boolean[] available, String lastMove) {

		    String move=null;
		    
		    return move;
		  }
	
}
