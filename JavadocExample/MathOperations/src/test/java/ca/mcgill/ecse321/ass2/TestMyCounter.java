/**
 * ECSE 321 Assignment 3
 * @version 1.0 Build 4 March, 2018 
 * @author Jessica Udo, 260627159
 * 
 */
package ca.mcgill.ecse321.ass2;

import static org.junit.Assert.*;

import org.junit.Test;

import ca.mcgill.ecse321.Ass2;

public class TestMyCounter {

//	@BeforeClass
//	public static void setUpBeforeClass() throws Exception {
//	}
//
//	@AfterClass
//	public static void tearDownAfterClass() throws Exception {
//	}
//
//	@Before
//	public void setUp() throws Exception {
//	}
//
//	@After
//	public void tearDown() throws Exception {
//	}
//
//	@Test
//	public void test() {
//		fail("Not yet implemented");
//	}


	//	@Test 
	//	/**
	//	 * A method that test to see if the given object is null - DOESN'T WORK PROPERLY
	//	 * E1
	//	 */
	//	public void testInputNull(){
	//		
	//		
	//		String[] strings = null;
	//		//String error = null;
	//		Ass2 ass2 = new Ass2();
	//		assertEquals(0, strings.length);
	//		try {
	//			ass2.myCounter(strings);
	//		} 
	//		catch (InvalidInputException e) {
	//			fail();
	//			//error = e.getMessage();
	//		}
	//		// check error
	//			//assertEquals("Input cannot be empty!", error);
	//	}


	@Test
	/**
	 * A method that test to see code still runs correctly if there's a blank array entry in input (at either position).
	 * E3
	 */
	public void blanksTester() throws InvalidInputException
	{

		Ass2 ass2 = new Ass2();
		String[] strings = {" ", "one", " ", "tris"};
		int result = ass2.myCounter(strings); 
		assertEquals(1, result);

	}

	@Test
	/**
	 * A method that test to see code still runs correctly if there's only one string entry.
	 * E4
	 * 
	 */
	public void singleStringTester() throws InvalidInputException
	{

		Ass2 ass2 = new Ass2();
		String[] strings = {"oisne"};
		int result = ass2.myCounter(strings); 
		assertEquals(1, result);

	}
	@Test
	/**
	 * A method that test to see code still runs correctly if there are duplicate string entries.
	 * E5
	 * 
	 */
	public void duplicateStringTester() throws InvalidInputException
	{

		Ass2 ass2 = new Ass2();
		String[] strings = {"sing", "sing", "sing", "sis"};
		int result = ass2.myCounter(strings); 
		assertEquals(4, result);

	}
	@Test
	/**
	 * Test for character sensitivity
	 * E8
	 * 
	 */
	public void characterSensitivityTester() throws InvalidInputException
	{

		Ass2 ass2 = new Ass2();
		String[] strings = {"siNg", "SING"};
		int result = ass2.myCounter(strings); 
		assertEquals(0, result);

	}
	@Test
	/**
	 * Test for input with single character in array location
	 * E9
	 * 
	 */
	public void singleCharacterTester() throws InvalidInputException
	{

		Ass2 ass2 = new Ass2();
		String[] strings = {"s", "sisi"};
		int result = ass2.myCounter(strings); 
		assertEquals(1, result);

	}
	@Test
	/**
	 * Test for input with special character in array location
	 * E11
	 * 
	 */
	public void specialCharacterTester() throws InvalidInputException
	{

		Ass2 ass2 = new Ass2();
		String[] strings = {"@#&is", "sisi"};
		int result = ass2.myCounter(strings); 
		assertEquals(2, result);

	}

	@Test
	/**
	 * Test for input with duplicate character in array location
	 * E12 & E15 & E16
	 * 
	 */
	public void duplicateCharacterTester() throws InvalidInputException
	{

		Ass2 ass2 = new Ass2();
		String[] strings = {"issssiiisisis", "sisiin"};
		int result = ass2.myCounter(strings); 
		assertEquals(6, result);

	}
	
	
	@Test
	/**
	 * Test for input without "is" or "in".
	 * E14
	 */
	public void myCounter() throws InvalidInputException {

		Ass2 ass2 = new Ass2();
		String[] strings = {"one", "two"};
		int result = ass2.myCounter(strings); 
		//assertEquals(0, rm.getParticipants().size());
		assertEquals(0, result);

	}

}

