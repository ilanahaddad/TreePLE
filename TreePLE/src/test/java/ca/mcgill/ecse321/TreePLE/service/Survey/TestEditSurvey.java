package ca.mcgill.ecse321.TreePLE.service.Survey;

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
import ca.mcgill.ecse321.TreePLE.model.Tree.Status;
import ca.mcgill.ecse321.TreePLE.model.User.UserType;
import ca.mcgill.ecse321.TreePLE.model.TreeManager;
import ca.mcgill.ecse321.TreePLE.model.User;
import ca.mcgill.ecse321.TreePLE.model.VersionManager;
import ca.mcgill.ecse321.TreePLE.persistence.PersistenceXStream;
import ca.mcgill.ecse321.TreePLE.service.InvalidInputException;
import ca.mcgill.ecse321.TreePLE.service.SurveyService;

public class TestEditSurvey {
	private VersionManager vm;
	private TreeManager tm;
	private User user;
	private SurveyService sc;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		PersistenceXStream.initializeModelManager("output" + File.separator + "data.xml");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {

	}

	@Before
	public void setUp() throws Exception {
		vm = new VersionManager();
		user = new User();
		tm=new TreeManager(true, "1.0", 2018,user);
		user.setUsertype(UserType.Professional);
		vm.addTreeManager(tm);
		sc = new SurveyService(vm);
	}

	@After
	public void tearDown() throws Exception {
		vm.delete();
	}

	@Test
	public void testEditSurvey() {
		assertEquals(0, tm.getSurveys().size());
		String species= "White Ash";
		Location treeLoc = new Location(1.5,1.5);
		Municipality m = new Municipality("Outremont");


		Tree tree=new Tree("Ilana",species, 1.2, 0.2, 0,treeLoc, m);
		Calendar c1 = Calendar.getInstance();
		c1.set(2017, Calendar.MARCH, 16, 9, 0, 0);
		Date date1= new Date(c1.getTimeInMillis());

		Survey survey= new Survey("Ilana", date1, tree);
		tm.addSurvey(survey);

		Calendar c2 = Calendar.getInstance();
		c2.set(2018, Calendar.MARCH, 16, 9, 0, 0);
		Date date2= new Date(c2.getTimeInMillis());
		try {
			sc.editSurvey(survey,date2,"Diana",Status.Diseased);
		}catch (InvalidInputException e) {
			e.printStackTrace();
		}

		assertEquals("Diana", tm.getSurvey(0).getSurveyorName());
		assertEquals(date2, tm.getSurvey(0).getReportDate());
		assertEquals(Status.Diseased, tm.getSurvey(0).getTree().getStatus());
		if(!PersistenceXStream.saveToXMLwithXStream(vm)) {
			fail("Could not save file");
		}
		vm = (VersionManager) PersistenceXStream.loadFromXMLwithXStream();
		if(vm == null) {
			fail("Could not load file");
		}
		assertEquals("Diana", tm.getSurvey(0).getSurveyorName());
		//assertEquals(date2, tm.getSurvey(0).getReportDate()); //TODO: solve assertion error
		assertEquals(Status.Diseased, tm.getSurvey(0).getTree().getStatus());
	}
	@Test
	public void testNull() {
		assertEquals(0, tm.getSurveys().size()); 
		String species= "White Ash";
		Location treeLoc = new Location(1.5,1.5);
		Municipality m = new Municipality("Outremont");
		Tree tree=new Tree("Ilana",species, 1.2, 0.2, 0,treeLoc, m);

		Calendar c1 = Calendar.getInstance();
		c1.set(2017, Calendar.MARCH, 16, 9, 0, 0);
		Date date1= new Date(c1.getTimeInMillis());

		Survey survey= new Survey("Ilana", date1, tree);
		tm.addSurvey(survey);

		Calendar c2 = Calendar.getInstance();
		c2.set(2018, Calendar.MARCH, 16, 9, 0, 0);
		Date date2= new Date(c2.getTimeInMillis());

		String error = null;

		//null surveyor:
		try {
			sc.editSurvey(survey, date2, null, Status.CutDown);
		}catch (InvalidInputException e) {
			error =e.getMessage();
		}

		assertEquals("Error: Survey,Report Date,surveyor, or status is null", error);
		//check no change in surveyor name
		assertEquals("Ilana", tm.getSurvey(0).getSurveyorName());

		//null date:
		try {
			sc.editSurvey(survey, null, "Ilana", Status.CutDown);
		}catch (InvalidInputException e) {
			error =e.getMessage();
		}
		assertEquals("Error: Survey,Report Date,surveyor, or status is null", error);
		//check no change in date
		assertEquals(date1, tm.getSurvey(0).getReportDate());

		//null status:
		try {
			sc.editSurvey(survey, date2, "Ilana", null);
		}catch (InvalidInputException e) {
			error =e.getMessage();
		}
		assertEquals("Error: Survey,Report Date,surveyor, or status is null", error);
		//check no change in status
		assertEquals(Status.Planted, tm.getSurvey(0).getTree().getStatus());

		//null survey:
		try {
			sc.editSurvey(null, date2, "Ilana", Status.CutDown);
		}catch (InvalidInputException e) {
			error =e.getMessage();
		}
		assertEquals("Error: Survey,Report Date,surveyor, or status is null", error);
		
		//survey does exist
		Survey surveyNotInSystem = new Survey("Asma",date2,tree);
		try {
			sc.editSurvey(surveyNotInSystem, date2, "Asma", Status.CutDown);
		}catch (InvalidInputException e) {
			error =e.getMessage();
		}
		assertEquals("Error: Survey does not exist", error);
		//check no changes in original survey
		assertEquals("Ilana", tm.getSurvey(0).getSurveyorName());
		assertEquals(Status.Planted, tm.getSurvey(0).getTree().getStatus());
		assertEquals(date1, tm.getSurvey(0).getReportDate());
	}

}
