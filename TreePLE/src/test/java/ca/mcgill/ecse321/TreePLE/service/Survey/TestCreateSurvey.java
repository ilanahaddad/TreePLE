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
import ca.mcgill.ecse321.TreePLE.model.Tree;
import ca.mcgill.ecse321.TreePLE.model.Tree.Status;
import ca.mcgill.ecse321.TreePLE.model.TreeManager;
import ca.mcgill.ecse321.TreePLE.model.User;
import ca.mcgill.ecse321.TreePLE.model.VersionManager;
import ca.mcgill.ecse321.TreePLE.model.User.UserType;
import ca.mcgill.ecse321.TreePLE.persistence.PersistenceXStream;
import ca.mcgill.ecse321.TreePLE.service.InvalidInputException;
import ca.mcgill.ecse321.TreePLE.service.SurveyService;

public class TestCreateSurvey {
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
		tm=new TreeManager(true,true, "1.0", 2018,user);
		user.setUsertype(UserType.Professional);
		vm.addTreeManager(tm);
		sc = new SurveyService(vm);
	}

	@After
	public void tearDown() throws Exception {
		vm.delete();
	}

	@Test
	public void testCreateSurvey() {
		assertEquals(0, tm.getSurveys().size()); // import Assert from the `org.junit` package
		String species= "White Ash";
		Location treeLoc = new Location(1.5,1.5);
		Municipality m = new Municipality("Outremont");


		Tree tree=new Tree("Ilana",species, 1.2, 0.2, 0,treeLoc, m);
		
		Calendar c = Calendar.getInstance();
		c.set(2017, Calendar.MARCH, 16, 9, 0, 0);
		Date date= new Date(c.getTimeInMillis());
				
		try {
			sc.createSurvey(date, tree, "Ilana", Tree.Status.Diseased );
		} catch (InvalidInputException e) {
			e.printStackTrace();
		}
		checkResultSurvey(date, tree, "Ilana", Tree.Status.Diseased, tm );
		if(!PersistenceXStream.saveToXMLwithXStream(vm)) {
			fail("Could not save file");
		}
		vm = (VersionManager) PersistenceXStream.loadFromXMLwithXStream();
		if(vm==null) {
			fail("Could not load file");
		}
		// check file contents
		checkResultSurvey(date, tree, "Ilana", Tree.Status.Diseased, tm );
	}
	@Test
	public void testCreateSurveyorNameEmpty() {
		assertEquals(0, tm.getSurveys().size()); // import Assert from the `org.junit` package
		String species= "White ";
		Location treeLoc = new Location(1.5,1.5);
		Municipality m = new Municipality("Outremont");


		Tree tree=new Tree("Ilana",species, 1.2, 0.2, 0,treeLoc, m);
		
		Calendar c = Calendar.getInstance();
		c.set(2017, Calendar.MARCH, 16, 9, 0, 0);
		Date date= new Date(c.getTimeInMillis());

		String error=null;
		
		try {
			sc.createSurvey(date,tree,"",Tree.Status.Diseased);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals("Error: Surveyor name cannot be empty", error);

		// check no change in memory
		assertEquals(0, tm.getSurveys().size());
		
	}
	@Test
	public void testCreateSurveyNullDate() {
		assertEquals(0, tm.getSurveys().size());
		Date date = null;
		Location treeLoc = new Location(1.5,1.5);
		Municipality m = new Municipality("Outremont");
		Tree tree = new Tree("Ilana","White Oak", 1.2, 0.2, 0,treeLoc, m);;
		Status status = Tree.Status.ToBeCutDown;
		
		String error = null;
		
		try {
			sc.createSurvey(date,tree,"Ilana",status);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals("Error: Report date cannot be null.\n", error);

		// check no change in memory
		assertEquals(0, tm.getSurveys().size());
		
	}
	@Test
	public void testCreateSurveyNullTree() {
		assertEquals(0, tm.getSurveys().size());
		Calendar c = Calendar.getInstance();
		c.set(2017, Calendar.MARCH, 16, 9, 0, 0);
		Date date= new Date(c.getTimeInMillis());

		Tree tree = null;
		Status status = Tree.Status.ToBeCutDown;
		
		String error = null;
		
		try {
			sc.createSurvey(date,tree,"Ilana",status);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals("Error: Tree cannot be null.\n", error);

		// check no change in memory
		assertEquals(0, tm.getSurveys().size());
		
	}
	@Test
	public void testCreateSurveyNullSurveyor() {
		assertEquals(0, tm.getSurveys().size());
		Calendar c = Calendar.getInstance();
		c.set(2017, Calendar.MARCH, 16, 9, 0, 0);
		Date date= new Date(c.getTimeInMillis());
		Location treeLoc = new Location(1.5,1.5);
		Municipality m = new Municipality("Outremont");
		Tree tree = new Tree("Ilana","White Oak", 1.2, 0.2, 0,treeLoc, m);;
		Status status = Tree.Status.ToBeCutDown;
		
		String error = null;
		
		try {
			sc.createSurvey(date,tree,null,status);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals("Error: Surveyor name cannot be null.\n", error);

		// check no change in memory
		assertEquals(0, tm.getSurveys().size());
		
	}
	@Test
	public void testCreateSurveyNullStatus() {
		assertEquals(0, tm.getSurveys().size());
		Calendar c = Calendar.getInstance();
		c.set(2017, Calendar.MARCH, 16, 9, 0, 0);
		Date date= new Date(c.getTimeInMillis());
		Location treeLoc = new Location(1.5,1.5);
		Municipality m = new Municipality("Outremont");
		Tree tree = new Tree("Ilana","White Oak", 1.2, 0.2, 0,treeLoc, m);;
		Status status = null;
		
		String error = null;
		
		try {
			sc.createSurvey(date,tree,"ilana",status);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals("Error: New Tree Status cannot be null.\n", error);

		// check no change in memory
		assertEquals(0, tm.getSurveys().size());
		
	}
	@Test
	public void testCreateSurveySameStatus() {
		assertEquals(0, tm.getSurveys().size());
		
		String species= "White Ash";
		Location treeLoc = new Location(1.5,1.5);
		Location[] ownerLoc = new Location[4];
		Location l1_res1 = new Location(1,1);
		Location l2_res1 = new Location(1,2);
		Location l3_res1 = new Location(2,1);
		Location l4_res1 = new Location(2,2);
		ownerLoc[0]=l1_res1;ownerLoc[1]=l2_res1;ownerLoc[2]=l3_res1;ownerLoc[3]=l4_res1;
		Municipality m = new Municipality("Outremont");

		Tree tree=new Tree("Ilana", species, 1.2, 0.2,0, treeLoc,m);
		
		Calendar c = Calendar.getInstance();
		c.set(2017, Calendar.MARCH, 16, 9, 0, 0);
		Date date= new Date(c.getTimeInMillis());
		
		String error = null;
		
		//create survey and set tree status to diseased
		try {
			sc.createSurvey(date,tree,"Ilana",Tree.Status.Diseased);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		//make sure it was created 
		checkResultSurvey(date, tree, "Ilana", Tree.Status.Diseased, tm );
		
		//try to create survey with same status 
		try {
			sc.createSurvey(date,tree,"Ilana",Tree.Status.Diseased);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		// check error
		assertEquals("Error: This tree already has this status", error);

		// check no change in memory
		assertEquals(1, tm.getSurveys().size());
		
	}
	@Test
	public void testCreateUnauthorizedSurvey() {
		assertEquals(0, tm.getSurveys().size());
		tm.getUser().setUsertype(UserType.LocalResident);
		String species= "White Ash";
		Location treeLoc = new Location(1.5,1.5);
		Municipality m = new Municipality("Outremont");

		Tree tree=new Tree("Ilana", species, 1.2, 0.2,0, treeLoc,m);
		
		Calendar c = Calendar.getInstance();
		c.set(2017, Calendar.MARCH, 16, 9, 0, 0);
		Date date= new Date(c.getTimeInMillis());
		
		String error = null;
		
		//create survey and set tree status to diseased
		try {
			sc.createSurvey(date,tree,"Ilana",Tree.Status.Diseased);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
	
		assertEquals("Only a professional can change tree status to 'To Be Cut Down' or 'Diseased'.\n", error);

		// check no change in memory
		assertEquals(0, tm.getSurveys().size());
		
	}
	private void checkResultSurvey(Date date, Tree tree, String surveyor, Status status, TreeManager tm2) {
		assertEquals(1, tm2.getSurveys().size());
		assertEquals(date.toString(), tm.getSurvey(0).getReportDate().toString());
		assertEquals(tree.getId(), tm.getSurvey(0).getTree().getId());
		assertEquals(surveyor, tm.getSurvey(0).getSurveyorName());
		assertEquals(status, tm.getSurvey(0).getTree().getStatus());

	}


	

}
