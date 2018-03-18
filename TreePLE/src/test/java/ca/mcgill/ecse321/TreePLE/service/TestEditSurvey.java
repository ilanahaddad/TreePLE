package ca.mcgill.ecse321.TreePLE.service;

import static org.junit.Assert.*;

import java.io.File;
import java.sql.Date;
import java.util.Calendar;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.mcgill.ecse321.TreePLE.model.Location;
import ca.mcgill.ecse321.TreePLE.model.Municipality;
import ca.mcgill.ecse321.TreePLE.model.Survey;
import ca.mcgill.ecse321.TreePLE.model.Tree;
import ca.mcgill.ecse321.TreePLE.model.TreeManager;
import ca.mcgill.ecse321.TreePLE.model.User;
import ca.mcgill.ecse321.TreePLE.persistence.PersistenceXStream;

public class TestEditSurvey {
	private TreeManager tm;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		PersistenceXStream.initializeModelManager("output" + File.separator + "data.xml");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {

	}

	@Before
	public void setUp() throws Exception {
		User user= new User();
		tm=new TreeManager(true, "1.0", 2018, user);
	}

	@After
	public void tearDown() throws Exception {
		tm.delete();

	}
	
	@Test
	public void testEditSurvey() {
		assertEquals(0, tm.getSurveys().size()); // import Assert from the `org.junit` package
		String species= "White Ash";
		Location treeLoc = new Location(1.5,1.5);
		Municipality m = new Municipality("Outremont");


		Tree tree=new Tree("Ilana",species, 1.2, 0.2, 0,treeLoc, m);
		
		Calendar c1 = Calendar.getInstance();
		c1.set(2017, Calendar.MARCH, 16, 9, 0, 0);
		Date date1= new Date(c1.getTimeInMillis());
		
		Calendar c2 = Calendar.getInstance();
		c2.set(2017, Calendar.MARCH, 16, 9, 0, 0);
		Date date2= new Date(c2.getTimeInMillis());
		
		Survey survey= new Survey("Ilana", date1, tree);
		tm.addSurvey(survey);
		
		SurveyService sc= new SurveyService(tm);
		
		try {
			sc.editSurvey(tm.getSurvey(0), "Diana", date2);
		}catch (InvalidInputException e) {
			e.printStackTrace();
		}
		
		assertEquals("Diana", tm.getSurvey(0).getSurveyorName());
		assertEquals(date2, tm.getSurvey(0).getReportDate());
		
		tm = (TreeManager) PersistenceXStream.loadFromXMLwithXStream();
		
		assertEquals("Diana", tm.getSurvey(0).getSurveyorName());
		assertEquals(date2, tm.getSurvey(0).getReportDate());

	}
	@Test
	public void testEditSurveyorNameNull() {
		assertEquals(0, tm.getSurveys().size()); // import Assert from the `org.junit` package
		String species= "White Ash";
		Location treeLoc = new Location(1.5,1.5);
		Municipality m = new Municipality("Outremont");


		Tree tree=new Tree("Ilana",species, 1.2, 0.2, 0,treeLoc, m);
		
		Calendar c1 = Calendar.getInstance();
		c1.set(2017, Calendar.MARCH, 16, 9, 0, 0);
		Date date1= new Date(c1.getTimeInMillis());
		
		Calendar c2 = Calendar.getInstance();
		c2.set(2017, Calendar.MARCH, 16, 9, 0, 0);
		Date date2= new Date(c2.getTimeInMillis());
		
		Survey survey= new Survey("Ilana", date1, tree);
		tm.addSurvey(survey);
		
		SurveyService sc= new SurveyService(tm);
		String error=null;
		
		try {
			sc.editSurvey(survey, null, date2);
		}catch (InvalidInputException e) {
			error=e.getMessage();
		}
		
		assertEquals("Error: Surveyor Name Cannot Be Null!!", error);
		//check no change in surveyor name
		assertEquals("Ilana", survey.getSurveyorName());
		
	}
	@Test
	public void testEditSurveyDateNull() {
		assertEquals(0, tm.getSurveys().size()); // import Assert from the `org.junit` package
		String species= "White Ash";
		Location treeLoc = new Location(1.5,1.5);
		Municipality m = new Municipality("Outremont");


		Tree tree=new Tree("Ilana",species, 1.2, 0.2, 0,treeLoc, m);
		tm.addTree(tree);
		
		Calendar c1 = Calendar.getInstance();
		c1.set(2017, Calendar.MARCH, 16, 9, 0, 0);
		Date date1= new Date(c1.getTimeInMillis());
		
		Calendar c2 = Calendar.getInstance();
		c2.set(2017, Calendar.MARCH, 16, 9, 0, 0);
		Date date2= null;
		
		Survey survey= new Survey("Ilana", date1, tree);
		tm.addSurvey(survey);
		
		SurveyService sc= new SurveyService(tm);
		String error=null;
		
		try {
			sc.editSurvey(survey, "Diana", date2);
		}catch (InvalidInputException e) {
			error=e.getMessage();
		}
		
		assertEquals("Error: Survey Date Cannot Be Null!!", error);

		//check no change in surveyorDate	
		assertEquals(date1, survey.getReportDate());
		
	}
	@Test
	public void testEditSurveySurveyNull() {
		assertEquals(0, tm.getSurveys().size()); // import Assert from the `org.junit` package
		
		Calendar c2 = Calendar.getInstance();
		c2.set(2017, Calendar.MARCH, 16, 9, 0, 0);
		Date date2= new Date(c2.getTimeInMillis());
		
		Survey survey= null;
		
		SurveyService sc= new SurveyService(tm);
		String error=null;
		
		try {
			sc.editSurvey(survey, "Diana", date2);
		}catch (InvalidInputException e) {
			error=e.getMessage();
		}
		
		assertEquals("Error: Survey Date Cannot Be Null!!", error);
		
	}
	@Test
	public void testEditSurveyorNameEmpty() {
		
		String species= "White Ash";
		Location treeLoc = new Location(1.5,1.5);
		Municipality m = new Municipality("Outremont");


		Tree tree=new Tree("Ilana",species, 1.2, 0.2, 0,treeLoc, m);
		
		Calendar c1 = Calendar.getInstance();
		c1.set(2017, Calendar.MARCH, 16, 9, 0, 0);
		Date date1= new Date(c1.getTimeInMillis());
		
		Calendar c2 = Calendar.getInstance();
		c2.set(2017, Calendar.MARCH, 16, 9, 0, 0);
		Date date2= null;
		
		Survey survey= new Survey("Ilana", date1, tree);
		tm.addSurvey(survey);
		
		SurveyService sc= new SurveyService(tm);
		String error=null;
		
		try {
			sc.editSurvey(survey, " ", date2);
		}catch (InvalidInputException e) {
			error=e.getMessage();
		}
		
		assertEquals("Error: Surveyor Name Cannot Be Empty!!", error);

		//check no change in surveyor name
		assertEquals("Ilana", survey.getSurveyorName());
		
	}
	

}
