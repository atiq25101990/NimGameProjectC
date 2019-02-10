package com.nimgameprojectc;

/*
 * This class is based on custom exception when
 * called if incorrect no of argument is passed as a input 
 * Mohammed Atiq Mohammed Mashaq Shaikh 25/05/2017
 * */

public class IncorrectNoOfArgumentException extends Exception{

	public IncorrectNoOfArgumentException() {
		super("Incorrect number of arguments supplied to command.");
	}
	
}
