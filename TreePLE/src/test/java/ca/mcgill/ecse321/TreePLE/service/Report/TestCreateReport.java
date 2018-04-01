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
import ca.mcgill.ecse321.TreePLE.model.Tree.Status;
import ca.mcgill.ecse321.TreePLE.persistence.PersistenceXStream;
import ca.mcgill.ecse321.TreePLE.service.InvalidInputException;
import ca.mcgill.ecse321.TreePLE.service.ReportService;

public class TestCreateReport {
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
	public void testInvalidInput() {
		ReportService rs = new ReportService(tm);
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
		assertEquals("Error: Report name, date, or parameter is null", error);
		
		try {
			rs.createReport(reporter, null, perimeter);
		} catch (InvalidInputException e) {
			error=e.getMessage();
		}
		assertEquals("Error: Report name, date, or parameter is null", error);
		
		try {
			rs.createReport(null, date, perimeter);
		} catch (InvalidInputException e) {
			error=e.getMessage();
		}
		assertEquals("Error: Report name, date, or parameter is null", error);


		Location[] perimeter1 = {null, new Location(0,5), 
				new Location(5,5), new Location(5,0)};
		try {
			rs.createReport(reporter, date, perimeter1);
		} catch (InvalidInputException e) {
			error=e.getMessage();
		}
		assertEquals("Error: Location coordinates are null", error);
		
		Location[] perimeter2 = {new Location(0,0), new Location(0,-5), 
				new Location(-5,-5), new Location(-5,0)};
		try {
			rs.createReport(reporter, date, perimeter2);
		} catch (InvalidInputException e) {
			error=e.getMessage();
		}
		assertEquals("Error: Location coordinates are negative", error);
		
	}

	@Test
	public void testCalculation() {
		//tree1:
		double widthTree1 = 0.5;
		//surround only this tree 
		Location coordinate1t1 = new Location(1-widthTree1,1-widthTree1);
		Location coordinate2t1 = new Location(1+widthTree1,1-widthTree1);
		Location coordinate3t1 = new Location(1-widthTree1,1+widthTree1);
		Location coordinate4t1 = new Location(1+widthTree1,1+widthTree1);
		Location[] perimetert1 = {coordinate1t1,coordinate2t1,coordinate3t1,coordinate4t1};

		String reporter = "Asma";
		Calendar c = Calendar.getInstance();
		c.set(2017, Calendar.MARCH, 16, 9, 0, 0);
		Date date= new Date(c.getTimeInMillis());
		ReportService rs = new ReportService(tm);
		SustainabilityReport report = null;
		try {
			report = rs.createReport(reporter, date, perimetert1);
		} catch (InvalidInputException e1) {
			e1.printStackTrace();
		}
		tm.addReport(report);

		assertEquals(1,tm.getReport(0).getBiodiversityIndex(),0);
		assertEquals(5*0.5*Math.PI,tm.getReport(0).getCanopy(),0);
		double carbonSequestration = (0.5*(0.725 * 50)*3.6663)/5;
		assertEquals(carbonSequestration,tm.getReport(0).getCarbonSequestration(),0);


		//tree2:
		double widthTree2 = 0.5;
		//surround only this tree 
		Location coordinate1t2 = new Location(2-widthTree2,2-widthTree2);
		Location coordinate2t2 = new Location(2+widthTree2,2-widthTree2);
		Location coordinate3t2 = new Location(2-widthTree2,2+widthTree2);
		Location coordinate4t2 = new Location(2+widthTree2,2+widthTree2);
		Location[] perimetert2 = {coordinate1t2,coordinate2t2,coordinate3t2,coordinate4t2};

		try {
			report = rs.createReport(reporter, date, perimetert2);
		} catch (InvalidInputException e) {
			e.printStackTrace();
		}
		tm.addReport(report);

		assertEquals(1,tm.getReport(0).getBiodiversityIndex(),0);
		assertEquals(5*0.5*Math.PI,tm.getReport(0).getCanopy(),0);
		carbonSequestration = (0.5*(0.725 * 55)*3.6663)/5;
		assertEquals(carbonSequestration,tm.getReport(0).getCarbonSequestration(),0);

	}

	@Test
	public void testNullName(){
		String reporter = null;
		Calendar c = Calendar.getInstance();
		c.set(2017, Calendar.MARCH, 16, 9, 0, 0);
		Date date= new Date(c.getTimeInMillis());
		Location coordinate1 = new Location(0,0);
		Location coordinate2 = new Location(3,0);
		Location coordinate3 = new Location(0,3);
		Location coordinate4 = new Location(3,3);
		Location[] perimeter = {coordinate1,coordinate2,coordinate3,coordinate4};
		ReportService rs = new ReportService(tm);
		String error = null;
		try {
			SustainabilityReport report = rs.createReport(reporter, date, perimeter);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		assertEquals("Error: Name can't be null.", error);

	}

	@Test
	public void testValidName(){
		String reporter = " ";
		Calendar c = Calendar.getInstance();
		c.set(2017, Calendar.MARCH, 16, 9, 0, 0);
		Date date= new Date(c.getTimeInMillis());
		Location coordinate1 = new Location(0,0);
		Location coordinate2 = new Location(3,0);
		Location coordinate3 = new Location(0,3);
		Location coordinate4 = new Location(3,3);
		Location[] perimeter = {coordinate1,coordinate2,coordinate3,coordinate4};
		ReportService rs = new ReportService(tm);
		SustainabilityReport report = null;
		try {
			report = rs.createReport(reporter, date, perimeter);
		} catch (InvalidInputException e) {
			e.printStackTrace();
		}
		tm.addReport(report);
		assertEquals(" ", tm.getReport(0).getReporterName());
		reporter = "asma";
		try {
			report = rs.createReport(reporter, date, perimeter);
		} catch (InvalidInputException e) {
			e.printStackTrace();
		}
		assertEquals("asma", tm.getReport(0).getReporterName());
	}

	@Test
	public void testInvalidPerimeter() {
		String reporter = "Asma";
		Calendar c = Calendar.getInstance();
		c.set(2017, Calendar.MARCH, 16, 9, 0, 0);
		Date date= new Date(c.getTimeInMillis());
		Location coordinate1 = new Location(3,0);
		Location coordinate2 = new Location(0,0);
		Location coordinate3 = new Location(3,3);
		Location coordinate4 = new Location(0,3);
		Location[] perimeter = {coordinate1,coordinate2,coordinate3,coordinate4};
		ReportService rs = new ReportService(tm);
		String error = null;
		try {
			SustainabilityReport report = rs.createReport(reporter, date, perimeter);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		assertEquals("Error: Invalid coordinates.", error);

	}

	@Test
	public void testValidPerimeter() {
		String reporter = " ";
		Calendar c = Calendar.getInstance();
		c.set(2017, Calendar.MARCH, 16, 9, 0, 0);
		Date date= new Date(c.getTimeInMillis());
		Location coordinate1 = new Location(0,0);
		Location coordinate2 = new Location(3,0);
		Location coordinate3 = new Location(0,3);
		Location coordinate4 = new Location(3,3);
		Location[] perimeter = {coordinate1,coordinate2,coordinate3,coordinate4};
		ReportService rs = new ReportService(tm);
		SustainabilityReport report = null;
		try {
			report = rs.createReport(reporter, date, perimeter);
		} catch (InvalidInputException e) {
			e.printStackTrace();
		}
		tm.addReport(report);
		assertEquals(coordinate1, tm.getReport(0).getReportPerimeter(0));
		assertEquals(coordinate2, tm.getReport(0).getReportPerimeter(1));
		assertEquals(coordinate3, tm.getReport(0).getReportPerimeter(2));
		assertEquals(coordinate4, tm.getReport(0).getReportPerimeter(3));

	}

	@Test
	public void testNullDate() {
		String reporter = " ";
		Date date= null;
		Location coordinate1 = new Location(0,0);
		Location coordinate2 = new Location(3,0);
		Location coordinate3 = new Location(0,3);
		Location coordinate4 = new Location(3,3);
		Location[] perimeter = {coordinate1,coordinate2,coordinate3,coordinate4};
		ReportService rs = new ReportService(tm);
		String error = null;
		try {
			SustainabilityReport report = rs.createReport(reporter, date, perimeter);;
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		assertEquals("Error: Date can't be null.", error);

	}

	@Test
	public void testValidDate() {
		String reporter = " ";
		Calendar c = Calendar.getInstance();
		c.set(2017, Calendar.MARCH, 16, 9, 0, 0);
		Date date= new Date(c.getTimeInMillis());
		Location coordinate1 = new Location(0,0);
		Location coordinate2 = new Location(3,0);
		Location coordinate3 = new Location(0,3);
		Location coordinate4 = new Location(3,3);
		Location[] perimeter = {coordinate1,coordinate2,coordinate3,coordinate4};
		ReportService rs = new ReportService(tm);
		SustainabilityReport report = null;
		try {
			report = rs.createReport(reporter, date, perimeter);
		} catch (InvalidInputException e) {
			e.printStackTrace();
		}
		tm.addReport(report);
		assertEquals(date, tm.getReport(0).getDate());
	}



}
