package com.nimgameprojectc;

import java.io.Serializable;

/*
 * This is the Model abstract class which demonstrate 
 * methods like getter and setter methods for both the player Human Player as well as
 * Artificial Player that will be used in the game for  
 * various functionality like adding, removing, editting, ranking, in game and so on
 * Mohammed Atiq Mohammed Mashaq Shaikh 25/05/2017
 * */

public abstract class NimPlayer implements Serializable{

	private static final long serialVersionUID = 6529685098267757690L;
			
	private String userName,givenName,familyName,playerType;
	private int gamesPlayed,gamesWon,percentage;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getGivenName() {
		return givenName;
	}
	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}
	public String getFamilyName() {
		return familyName;
	}
	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}
	public String getPlayerType() {
		return playerType;
	}
	public void setPlayerType(String playerType) {
		this.playerType = playerType;
	}
	public int getGamesPlayed() {
		return gamesPlayed;
	}
	public void setGamesPlayed(int gamesPlayed) {
		this.gamesPlayed = gamesPlayed;
	}
	public int getGamesWon() {
		return gamesWon;
	}
	public void setGamesWon(int gamesWon) {
		this.gamesWon = gamesWon;
	}
	public int getPercentage() {
		return percentage;
	}
	public void setPercentage(int percentage) {
		this.percentage = percentage;
	}
	
}
