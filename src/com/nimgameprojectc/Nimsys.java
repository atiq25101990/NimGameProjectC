package com.nimgameprojectc;


import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * This is the Main class demonstrate 
 * the flow of the project by taking commands from the user
 * and processing it through various methods
 * Mohammed Atiq Mohammed Mashaq Shaikh 25/05/2017
 * */

public class Nimsys implements Serializable{
	
	private static final long serialVersionUID = 6520685098267757690L;
	
	private String userName,familyName,givenName,player1UserName,player2UserName;
	private int gamesPlayed=0,gamesWon=0,stoneLeft=0,upperBound=0;
	private final static String FILENAME = 
			"C://Users//DELL//neon workspace//NimGameProjectC//src//com//nimgameprojectc//players.dat";
	
	public static void main(String args[]){
	
	//declaring and/or instantiating objects
	Nimsys nimSys = new Nimsys();
	NimHumanPlayer nimPlayer = null;
	NimGame nimGame = null;
	ArrayList<NimHumanPlayer> playerList = new ArrayList<NimHumanPlayer>(100);
	Scanner scanner= new Scanner(System.in);
	
	//read player from the file players.dat
	nimSys.readPlayerList(nimPlayer, playerList);
	System.out.println("Welcome to Nim");
	//get input for player
	nimSys.getInput(nimSys,nimPlayer,nimGame,playerList,scanner);

	}

	//get input for Human player as well as AI player
	private void getInput(Nimsys nimSys, NimHumanPlayer nimPlayer, NimGame nimGame, 
			ArrayList<NimHumanPlayer> playerList, Scanner scanner) {
		// TODO Auto-generated method stubs
		
	//declare variables and objects
	String input=null,command=null,commandDetails = null;
	boolean flag = false;
	nimSys=new Nimsys();
	nimGame=new NimGame();
	
	
	//Iterating various commands such as addplayer, removeplayer, startgame and so on 
	do
	{
		
		//taking the input
		System.out.println();
		System.out.print("$");
		input=scanner.nextLine();
		if(input.equals(""))
		{
			input=null;
			input=scanner.nextLine();
		}
		
		//checking for space using regex
	    Pattern pattern = Pattern.compile("\\s");
	    Matcher matcher = pattern.matcher(input);
	    boolean found = matcher.find();
		if(found){
			String[] inputSplit = input.trim().split("\\s+");
			command=inputSplit[0];
			commandDetails=inputSplit[1];
		}
		else
		{
			command=input;
		}
		
		try{
			//sending methods to process			
			if(command.equals("addplayer")){
				//Add Human Player case
				try{
					if(commandDetails==null){
						//calling IncorrectNoofArgument exception class for throwing exception
						throw new IncorrectNoOfArgumentException();
					}
					int commaCount = getCommaCount(commandDetails);
					if(commaCount<2){
						throw new IncorrectNoOfArgumentException();
					}
					else
					{
						nimSys.addPlayer(command,commandDetails,nimPlayer,playerList);
						command = null;
						commandDetails = null;
						input=null;
					}
				}
				catch(IncorrectNoOfArgumentException e)
				{
					System.out.println("Incorrect number of arguments supplied to command.");
				}

			}
			else if(command.equals("addaiplayer")){
				//Add AI Player case
				try{
					if(commandDetails==null){
						//calling IncorrectNoofArgument exception class for throwing exception
						throw new IncorrectNoOfArgumentException();
					}
					int commaCount = getCommaCount(commandDetails);
					if(commaCount<2){
						throw new IncorrectNoOfArgumentException();
					}
					else
					{
						nimSys.addPlayer(command,commandDetails,nimPlayer,playerList);
						command = null;
						commandDetails = null;
						input=null;
					}
				}
				catch(IncorrectNoOfArgumentException e)
				{
					System.out.println("Incorrect number of arguments supplied to command.");
				}

			}
			else if(command.equals("removeplayer")){
				//Remove player case				
				nimSys.removePlayer(scanner,commandDetails,playerList);
				command = null;
				commandDetails = null;
				input=null;
			}
			else if(command.equals("editplayer")){
				//Edit player case
				try{
					if(commandDetails==null){
						throw new IncorrectNoOfArgumentException();
					}
					int commaCount = getCommaCount(commandDetails);
					if(commaCount<2){
						throw new IncorrectNoOfArgumentException();
					}
					else
					{
					nimSys.editPlayer(commandDetails,nimPlayer,playerList);
					command = null;
					commandDetails = null;
					input=null;
					}
				}
				catch(IncorrectNoOfArgumentException e)
				{
					System.out.println("Incorrect number of arguments supplied to command.");
				}
			}
			else if(command.equals("resetstats")){
				//Reset status case
				nimSys.resetStatsPlayer(scanner,commandDetails,nimPlayer,playerList);
				command = null;
				commandDetails = null;
				input=null;
			}
			else if(command.equals("displayplayer")){
				//Display player case
				nimSys.displayPlayer(commandDetails,nimGame,playerList);
				command = null;
				commandDetails = null;
				input=null;
			}
			else if(command.equals("rankings")){
				//Ranking case
				nimSys.rankings(commandDetails,nimPlayer,nimGame,playerList);
				command = null;
				commandDetails = null;
				input=null;
			}
			else if(command.equals("startgame")){
				//Start game case
				
				try{
					if(commandDetails==null){
						//calling IncorrectNoofArgument exception class for throwing exception
						throw new IncorrectNoOfArgumentException();
					}
					int commaCount = getCommaCount(commandDetails);
					if(commaCount<3){
						throw new IncorrectNoOfArgumentException();
					}
					else
					{
					nimGame.startGame(scanner,commandDetails,nimPlayer,playerList);
					command = null;
					commandDetails = null;
					input=null;
					}
				}
				catch(IncorrectNoOfArgumentException e)
				{
					System.out.println("Incorrect number of arguments supplied to command.");	
				}
			}
			else if(command.equals("exit")){
				//Exit case
				System.out.println();
				writePlayerList(playerList);
				System.exit(0);  
			}
			else
			{
				//throw exception if the command inputted is incorrect
				throw new InvalidCommandException();
			}
		}
		catch(InvalidCommandException e)
		{
			System.out.println("'"+command+"' is not a valid command.");
		}
		
		}while(!flag);
	
	}
	
	//reading players details from file players.dat
	private ArrayList<NimHumanPlayer> readPlayerList(NimHumanPlayer nimPlayer,
			ArrayList<NimHumanPlayer> playerList) {
		// TODO Auto-generated method stub
		try {
			//get file name and path
			File f = new File(FILENAME);
			
			//check for existence of file if no then create file with that name and 
			//for future writing process
			if (!f.exists()) {
				//create file
				f.createNewFile();
			}			
			
			//Opening the output stream so as to read from file
			FileInputStream fis = new FileInputStream(f);
			ObjectInputStream ois = new ObjectInputStream(fis);
			
				//Read player details in a object and store into arraylist 
				try{
					while(true){
					nimPlayer = (NimHumanPlayer) ois.readObject();
					playerList.add(nimPlayer);
					}
					
				} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			ois.close();
			fis.close();
		} 
		catch(EOFException e){
			//System.out.println();
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return playerList;
	}

	private void writePlayerList(ArrayList<NimHumanPlayer> playerList) {
		// TODO Auto-generated method stub
		try {
			//get file name and path
			File f = new File(FILENAME);
			
			//check for existence of file if no then create file with that name and write
			if (!f.exists()) {
				//create file
				f.createNewFile();
			}
			
			//Opening the output stream so as to write object into file
			FileOutputStream fos = new FileOutputStream(f);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			//writing object into file
			for(NimHumanPlayer player : playerList)
			oos.writeObject(player);
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	//get comma count to check whether argument for command is valid or not
	private int getCommaCount(String commandDetails) {
		// TODO Auto-generated method stub
		int commaCount = 0;
		for(int i = 0; i < commandDetails.length(); i++) {
		    if(commandDetails.charAt(i) == ','){ 
		    	commaCount++;
		    }
		}
		return commaCount;
	}

	//addPlayer method signifies adding of player details like given name, family name and so on
	private void addPlayer(String command, String commandDetails,
			NimHumanPlayer nimPlayer, ArrayList<NimHumanPlayer> playerList){
		
		
		//create NimHumanPlayer object
		nimPlayer=new NimHumanPlayer();
		
		//splitting command details to get player details
		String[] splitCommandDetails =  commandDetails.trim().split(",");
		userName = splitCommandDetails[0];
		familyName = splitCommandDetails[1];
		givenName = splitCommandDetails[2];
		
		boolean duplicateFlag=false;
		
		//Checking for a player existence
		for(int i=0;i<playerList.size();i++)
		{
			if(	userName.equals(playerList.get(i).getUserName()))
			{
				System.out.println("The player already exists.");
				duplicateFlag=true;
			}
			
		}
		
		if(command.equals("addplayer"))
		{
			//Adding new human player
			if(!duplicateFlag){
				//adding player details to object
				nimPlayer.setUserName(userName);
				nimPlayer.setFamilyName(familyName);
				nimPlayer.setGivenName(givenName);
				nimPlayer.setPlayerType("h");
				nimPlayer.setGamesPlayed(gamesPlayed);
				nimPlayer.setGamesWon(gamesWon);
			
				//adding object to the array
				playerList.add(nimPlayer);
			}	
		}
		else
		{
			//Adding new ai player
			if(!duplicateFlag){
				//adding player details to object
				nimPlayer.setUserName(userName);
				nimPlayer.setFamilyName(familyName);
				nimPlayer.setGivenName(givenName);
				nimPlayer.setPlayerType("a");
				nimPlayer.setGamesPlayed(gamesPlayed);
				nimPlayer.setGamesWon(gamesWon);
			
				//adding object to the array
				playerList.add(nimPlayer);
			}
		}
		
		return ;
	}
	
	//removePlayer method signifies removal of individual or all players
	private void removePlayer(Scanner scanner,String commandDetails,
			ArrayList<NimHumanPlayer> playerList){
		
		//check if username or command details empty
		if(commandDetails != null && !commandDetails.isEmpty()){

		    boolean matchFlag=false;
		   		    
		    //if player found then remove and update the array of objects and its playerList.size()
		    for (int i = 0;i < playerList.size(); i++) {
		    	if(commandDetails.equals(playerList.get(i).getUserName())){
		    		playerList.remove(i);
		            matchFlag=true;    		
		    	}
		    	
			}

		    //If player does not found show the message and make a copy of reduced array of
		    //objects
		    if(!matchFlag)
			{
				System.out.println("The player does not exist.");
			}
		    
		}
		else
		{
			//remove all the players
			System.out.println("Are you sure you want to remove all players? (y/n)");
			String checkRemoveAll = scanner.nextLine();
			if(checkRemoveAll.equals("y"))
			{
			playerList.removeAll(playerList);
			}

		}
		return;
	}
	
	//editPLayer method signifies updation of player information
	private void editPlayer(String commandDetails, NimHumanPlayer nimPlayer,
							ArrayList<NimHumanPlayer> playerList){
		
		
		//splitting command details
		String[] splitCommandDetails =  commandDetails.split(",");
		userName = splitCommandDetails[0];
		familyName = splitCommandDetails[1];
		givenName = splitCommandDetails[2];
	    boolean matchFlag=false; 
		
	    //If match found the update player information
		for(NimHumanPlayer i:playerList){
			if(	userName.equals(i.getUserName())){
				i.setFamilyName(familyName);
				i.setGivenName(givenName);
				matchFlag=true;
			}
		}
		
		//If match not found show message
		if(!matchFlag)
		{
			System.out.println("The player does not exist.");
		}
		
		return ;
	}	
	
	//resetStatsPlayer method signifies reseting individual or all player game statistics
	private void resetStatsPlayer(Scanner scanner,String commandDetails,NimHumanPlayer nimPlayer,
								ArrayList<NimHumanPlayer> playerList){

		nimPlayer = new NimHumanPlayer();
		
		//check if username or commandDetails empty 
		if(commandDetails != null && !commandDetails.isEmpty()){

			boolean matchFlag=false;		    
			//If match found setting the player details to zero
			for(NimHumanPlayer i:playerList){
				if(	commandDetails.equals(i.getUserName())){
		    		i.setGamesPlayed(0);
		    		i.setGamesWon(0);
		            matchFlag=true;    		
		    	}
			}

		    //If match not found then show message
			if(!matchFlag)
			{
				System.out.println("The player does not exist.");
			}
			
		}
		else
		{
			//Reset all player statistics
			System.out.println("Are you sure you want to reset all player statistics? (y/n)");
			String checkResetStatsAll = scanner.nextLine();
			if(checkResetStatsAll.equals("y"))
			{
				for(NimHumanPlayer i:playerList){
					
						i.setGamesPlayed(0);
						i.setGamesWon(0);
					
				}
			}

		}
		return;
	}
	
	//displayPlayer method signifies to show individual or all players information
	private void displayPlayer(String commandDetails,NimGame nimGame, ArrayList<?> playerList){
		
		//Check if username or commandDetails empty
		if(commandDetails != null && !commandDetails.isEmpty()){
			
		    boolean matchFlag=false; 
			
		    //If match found then showing individual player details
			for(int i=0;i<playerList.size();i++){
				if(	commandDetails.equals(((NimPlayer) playerList.get(i)).getUserName())){
					System.out.println(((NimPlayer) playerList.get(i)).getUserName() +","
					+ ((NimPlayer) playerList.get(i)).getGivenName()
					+","+ ((NimPlayer) playerList.get(i)).getFamilyName() +","
					+ ((NimPlayer) playerList.get(i)).getGamesPlayed() +" games,"
					+ ((NimPlayer) playerList.get(i)).getGamesWon() +" wins");
					matchFlag=true;
				}
			}
			
			//If match not found then show message
			if(!matchFlag)
			{
				System.out.println("The player does not exist.");
			}
		}
		else
		{
		//show all players with its details
		
		nimGame=new NimGame();
		playerList = nimGame.alphaSelectionSort(playerList);
		
		if(playerList.size()!=0){
		
		for(int i=0;i<playerList.size();i++){
			System.out.println(((NimPlayer) playerList.get(i)).getUserName() +","
					+ ((NimPlayer) playerList.get(i)).getGivenName()
					+","+ ((NimPlayer) playerList.get(i)).getFamilyName() +","
					+ ((NimPlayer) playerList.get(i)).getGamesPlayed() +" games,"
					+ ((NimPlayer) playerList.get(i)).getGamesWon() +" wins");
				}
			}		
		}
		
		return ;
		
	}
	
	//rankings method signifies player current status in the game as compare to others
	private void rankings(String commandDetails,NimHumanPlayer nimPlayer,NimGame nimGame,
						ArrayList<?> playerList){
		
		nimPlayer = new NimHumanPlayer();
		nimGame = new NimGame();
		
		//Check if command details empty or desc else asc
		if(commandDetails == null || commandDetails.isEmpty() || commandDetails.equals("desc")){
			
			//setting up player percentage
			for(Object i:playerList){
				
				int gameswon = ((NimPlayer) i).getGamesWon();
				int gamesplayed = ((NimPlayer) i).getGamesPlayed();
				int percentage = nimGame.calculatePercentage(gameswon,gamesplayed);
				((NimPlayer) i).setPercentage(percentage);
			}

			playerList = (ArrayList<?>) nimGame.descSelectionSort(playerList);
			
			for(int j=0;j<playerList.size();j++){
				for(int k=j+1;k<playerList.size();k++)
				    if(k!=j && ((NimPlayer) playerList.get(k)).getGamesPlayed() == 0 
				    && ((NimPlayer) playerList.get(j)).getGamesPlayed() == 0)
					playerList = nimGame.alphaSelectionSort(playerList);
			}
			
			String space="";
			
			//showing ranking in descending order
			for(int i=0;i<playerList.size();i++){
				System.out.println(String.format("%d%%%-" + (4 - String.valueOf(
					((NimPlayer) playerList.get(i)).getPercentage()).length())+ "s| %02d games | %s %s",
						((NimPlayer) playerList.get(i)).getPercentage(),space,
						((NimPlayer) playerList.get(i)).getGamesPlayed(),
						((NimPlayer) playerList.get(i)).getGivenName(),
						((NimPlayer) playerList.get(i)).getFamilyName()));
			}
			

			
		}
		else if(commandDetails.equals("asc")){
			
			//setting up player percentage
			for(int i=0;i<playerList.size();i++){
				int gameswon = ((NimPlayer) playerList.get(i)).getGamesWon();
				int gamesplayed = ((NimPlayer) playerList.get(i)).getGamesPlayed();
				int percentage = nimGame.calculatePercentage(gameswon,gamesplayed);
				nimPlayer.setPercentage(percentage);
			}
			
			playerList = nimGame.ascSelectionSort(playerList);
			
			for(int j=0;j<playerList.size();j++){
				for(int k=j+1;k<playerList.size();k++)
				    if(k!=j && ((NimPlayer) playerList.get(k)).getGamesPlayed() == 0
				    && ((NimPlayer) playerList.get(j)).getGamesPlayed() == 0)
					playerList = nimGame.alphaSelectionSort(playerList);
			}
			
			String space="";
			//showing ranking in ascending order
			for(int i=0;i<playerList.size();i++){
				System.out.println(String.format("%d%%%-" + (4 - String.valueOf(
					((NimPlayer) playerList.get(i)).getPercentage()).length())+ "s| %02d games | %s %s",
						((NimPlayer) playerList.get(i)).getPercentage(),space,
						((NimPlayer) playerList.get(i)).getGamesPlayed(),
						((NimPlayer) playerList.get(i)).getGivenName(),
						((NimPlayer) playerList.get(i)).getFamilyName()));
			}
			

			
		}
	}
	
	
}




