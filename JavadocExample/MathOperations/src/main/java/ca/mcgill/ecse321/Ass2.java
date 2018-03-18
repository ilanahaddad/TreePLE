package ca.mcgill.ecse321;

import ca.mcgill.ecse321.ass2.InvalidInputException;

//import ass2.InvalidInputException;

/**
 * ECSE 321 Assignment 3
 * @version 1.0 Build 4 March, 2018 
 * @author Jessica Udo, 260627159
 * TASKS:
 * a) Improve its readability by applying some coding best practices.
 * b) Add a Javadoc comment to explain what the method is doing
 * c) Add some good comments and then generate the Javadoc documentation.
 */

public class Ass2 {

	/**
	 * Counts and returns the number of "is" and "in" in a given string.
	 *  @param strings This is the users input.  
	 *                Should be an array of strings.
	 * @return numOfIs+numOfIn the number of strings.
	 * @exception InvalidInputException if input is not an array of Strings.
	 */
	public int myCounter(String[] strings) throws InvalidInputException
	{
		if (strings == null) {
			throw new InvalidInputException("Participant name cannot be empty!");
		}
		int numOfIs = 0;
		int numOfIn = 0;

		for (String input: strings)
			for (int counterPosition=0; counterPosition < input.length()-1; counterPosition++) { 
				//Looping from 0 to the (length of string - 1). 
				//Using (length of string - 1) because, length counts from 0 (not 1)
				if (input.charAt(counterPosition) == 'i' && input.charAt(counterPosition+1) == 's')  
					numOfIs++;
				// if we find an occurrence of "is" in input, we increment the counter numOfIs
				if (input.charAt(counterPosition) == 'i' && input.charAt(counterPosition+1) == 'n') 
					numOfIn++; 
				//if we find an occurrence of "is" in input, we increment the counter numOfIn
			}
		return numOfIs+numOfIn;
	}


	//	//Ran a main class to test that code above works
	//	public static void main (String[] args) throws java.lang.Exception
	//	{
	//		// your code goes here
	//		String[] elements = {"trin", "trissa", "cat", "dog", "mouse", "risisa", "rinininiin"};
	//		System.out.println(myCounter(elements));
	//	}
}

