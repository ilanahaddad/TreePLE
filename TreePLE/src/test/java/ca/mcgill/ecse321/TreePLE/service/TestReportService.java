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
import ca.mcgill.ecse321.TreePLE.model.SustainabilityReport;
import ca.mcgill.ecse321.TreePLE.model.Tree;
import ca.mcgill.ecse321.TreePLE.model.TreeManager;
import ca.mcgill.ecse321.TreePLE.model.User;
import ca.mcgill.ecse321.TreePLE.model.Tree.Status;
import ca.mcgill.ecse321.TreePLE.persistence.PersistenceXStream;

public class TestReportService {
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
		User user = new User();
		tm = new TreeManager(true, "1", 2018, user);

		//Create a bunch of trees

		String owner1 = "Asma";
		String owner2 = "Ilana";
		String owner3 = "Jessica";
		String owner4 = "Diana";
		String owner5 = "Thomas";

		String species1 = "White oak";
		String species2 = "Giant sequoia ";
		String species3 = "English yew";
		String species4 = "Quaking aspen";
		String species5 = "White oak";

		double height1 = 2;
		double height2 = 2;
		double height3 = 2;
		double height4 = 2;
		double height5 = 2;

		double diameter1 = 0.5;
		double diameter2 = 0.5;
		double diameter3 = 0.5;
		double diameter4 = 0.5;
		double diameter5 = 0.5;

		int age1 = 1;
		int age2 = 1;
		int age3 = 11;
		int age4 = 11;
		int age5 = 11;

		Location treeLoc1 = new Location(1,1);
		Location treeLoc2 = new Location(1,2);
		Location treeLoc3 = new Location(2,1);
		Location treeLoc4 = new Location(2,2);
		Location treeLoc5 = new Location(1.5,1.5);

		Municipality m1 = new Municipality("Outremont");
		//No need for the rest of municipalities
		Municipality m2 = new Municipality("Lachine");
		Municipality m3 = new Municipality("LaSalle");
		Municipality m4 = new Municipality("Verdun");
		Municipality m5 = new Municipality("Westmount");

		Tree tree1 = new Tree(owner1, species1, height1, diameter1, age1, treeLoc1, m1);
		Tree tree2 = new Tree(owner2, species2, height2, diameter2, age2, treeLoc2, m1);
		Tree tree3 = new Tree(owner3, species3 , height3, diameter3, age3, treeLoc3, m1);
		Tree tree4 = new Tree(owner4, species4, height4, diameter4, age4, treeLoc4, m1);
		Tree tree5 = new Tree(owner5, species5, height5, diameter5, age5, treeLoc5, m1);

		tm.addTree(tree1);
		tm.addTree(tree2);
		tm.addTree(tree3);
		tm.addTree(tree4);
		tm.addTree(tree5);

	}

	@After
	public void tearDown() throws Exception {
		tm.delete();
	}

	@Test
	public void testCreateReport() {
		//TODO: check if there was never a report generated

		String reporter = "Asma";
		Calendar c = Calendar.getInstance();
		c.set(2017, Calendar.MARCH, 16, 9, 0, 0);
		Date date= new Date(c.getTimeInMillis());
		Location coordinate1 = new Location(0,0);
		Location coordinate2 = new Location(3,0);
		Location coordinate3 = new Location(0,3);
		Location coordinate4 = new Location(3,3);
		Location[] perimeter = {coordinate1,coordinate2,coordinate3,coordinate4};
		/*if(isExistingReport(perimeter, version))
			SustainabilityReport existingReport = getReportByLocation(perimeter);
		 */
		ReportService rs = new ReportService(tm);
		SustainabilityReport report = rs.createReport(reporter, date, perimeter);
		double[] SustainabilityAttributes = report.getSustainabilityAttributes();
		double biodiversityIndex = SustainabilityAttributes[0];
		double canopy = SustainabilityAttributes[1];
		double carbonSequestration = SustainabilityAttributes[2];

		assertEquals(1,biodiversityIndex,0);
		assertEquals(1,canopy,0);
		assertEquals(1,carbonSequestration,0);
	}

	@Test
	public void testExistingReport() {
		Location coordinate1 = new Location(0,0);
		Location coordinate2 = new Location(3,0);
		Location coordinate3 = new Location(0,3);
		Location coordinate4 = new Location(3,3);
		Location[] perimeter = {coordinate1,coordinate2,coordinate3,coordinate4};

		assertEquals(false, tm.isExistingReport(perimeter));

		String reporter = "Asma";
		Calendar c = Calendar.getInstance();
		c.set(2017, Calendar.MARCH, 16, 9, 0, 0);
		Date date= new Date(c.getTimeInMillis());
		ReportService rs = new ReportService(tm);
		SustainabilityReport report = rs.createReport(reporter, date, perimeter);
		assertEquals(true, tm.isExistingReport(perimeter));
	}


}
