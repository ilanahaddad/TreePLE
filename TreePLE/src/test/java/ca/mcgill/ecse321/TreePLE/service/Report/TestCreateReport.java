package ca.mcgill.ecse321.TreePLE.service.Report;

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
import ca.mcgill.ecse321.TreePLE.model.SustainabilityReport;
import ca.mcgill.ecse321.TreePLE.model.Tree;
import ca.mcgill.ecse321.TreePLE.model.TreeManager;
import ca.mcgill.ecse321.TreePLE.model.User;
import ca.mcgill.ecse321.TreePLE.model.VersionManager;
import ca.mcgill.ecse321.TreePLE.model.Tree.Status;
import ca.mcgill.ecse321.TreePLE.model.User.UserType;
import ca.mcgill.ecse321.TreePLE.persistence.PersistenceXStream;
import ca.mcgill.ecse321.TreePLE.service.InvalidInputException;
import ca.mcgill.ecse321.TreePLE.service.ReportService;
import ca.mcgill.ecse321.TreePLE.service.SurveyService;

public class TestCreateReport {
	private VersionManager vm;
	private TreeManager tm;
	private User user;
	private ReportService rs;

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
		rs = new ReportService(vm);
		String species= "White Ash";

		Location treeLoc1 = new Location (1,1);
		Location treeLoc2 = new Location (2,2);
		Location treeLoc3 = new Location (3,3);
		Location treeLoc4 = new Location (4,4);

		String owner = "Asma";
		Municipality m = new Municipality("Outremont");

		Tree tree1= new Tree(owner, species, 1.5, 0.5, 0, treeLoc1, m );
		Tree tree2= new Tree(owner, species, 1.5, 0.5, 0, treeLoc2, m );
		Tree tree3= new Tree(owner, species, 1.5, 0.5, 0, treeLoc3, m );
		Tree tree4= new Tree(owner, species, 1.5, 0.5, 0, treeLoc4, m );

		tm.addTree(tree1);
		tm.addTree(tree2);
		tm.addTree(tree3);
		tm.addTree(tree4);
	}

	@After
	public void tearDown() throws Exception {
		vm.delete();
	}

	@Test
	public void testCreateReport() throws InvalidInputException {
		assertEquals(0, tm.getReports().size());
		String reporter = "Asma";
		Calendar c = Calendar.getInstance();
		c.set(2017, Calendar.MARCH, 16, 9, 0, 0);
		Date date= new Date(c.getTimeInMillis());
		Location[] perimeter = {new Location(0,0), new Location(0,5), 
				new Location(5,5), new Location(5,0)};
		rs.createReport(reporter, date, perimeter);
		assertEquals(1, tm.getReports().size());
	}
	
	@Test
	public void testInvalidInput() {
		String error=null;

		String reporter = "Asma";
		Calendar c = Calendar.getInstance();
		c.set(2017, Calendar.MARCH, 16, 9, 0, 0);
		Date date= new Date(c.getTimeInMillis());
		Location[] perimeter = {new Location(0,0), new Location(0,5), 
				new Location(5,5), new Location(5,0)};
		try {
			rs.createReport(reporter, date, null);
		} catch (InvalidInputException e) {
			error=e.getMessage();
		}
		assertEquals("Error: Reporter name, date, or parameter is null", error);

		try {
			rs.createReport(reporter, null, perimeter);
		} catch (InvalidInputException e) {
			error=e.getMessage();
		}
		assertEquals("Error: Reporter name, date, or parameter is null", error);

		try {
			rs.createReport(null, date, perimeter);
		} catch (InvalidInputException e) {
			error=e.getMessage();
		}
		assertEquals("Error: Reporter name, date, or parameter is null", error);


		Location[] perimeter1 = {null, new Location(0,5), 
				new Location(5,5), new Location(5,0)};
		try {
			rs.createReport(reporter, date, perimeter1);
		} catch (InvalidInputException e) {
			error=e.getMessage();
		}
		assertEquals("Error: Location coordinates are null", error);
/*
		Location[] perimeter2 = {new Location(0,0), new Location(0,-5), 
				new Location(-5,-5), new Location(-5,0)};
		try {
			rs.createReport(reporter, date, perimeter2);
		} catch (InvalidInputException e) {
			error=e.getMessage();
		}
		assertEquals("Error: Location coordinates are negative", error);*/
	}
	@Test
	public void testPerimeterErrorEqualLocations() {
		//TODO
	}
	
}
