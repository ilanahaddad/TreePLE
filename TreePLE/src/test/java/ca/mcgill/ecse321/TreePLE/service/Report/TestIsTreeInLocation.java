package ca.mcgill.ecse321.TreePLE.service.Report;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.mcgill.ecse321.TreePLE.model.Location;
import ca.mcgill.ecse321.TreePLE.model.Municipality;
import ca.mcgill.ecse321.TreePLE.model.Tree;
import ca.mcgill.ecse321.TreePLE.model.TreeManager;
import ca.mcgill.ecse321.TreePLE.model.User;
import ca.mcgill.ecse321.TreePLE.persistence.PersistenceXStream;
import ca.mcgill.ecse321.TreePLE.service.InvalidInputException;
import ca.mcgill.ecse321.TreePLE.service.ReportService;

public class TestIsTreeInLocation {

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
		tm.delete();
	}

	@Test
	public void testInvalidInput() {
		ReportService rs = new ReportService(tm);
		String error=null;
		
		Location[] perimeter1 = null;
		try {
			rs.isTreeInLocation(2,2,perimeter1);
		} catch (InvalidInputException e) {
			error=e.getMessage();
		}

		assertEquals("Error: Perimeter is null", error);
		
		Location[] perimeter2= {null,new Location(1,4),new Location(4,4),new Location(4,1)};
		try {
			rs.isTreeInLocation(2,2,perimeter2);
		} catch (InvalidInputException e) {
			error=e.getMessage();
		}
		assertEquals("Error: Location coordinates are null", error);
		/*
		Location[] perimeter3= {new Location(1,1),new Location(1,4),new Location(4,4),new Location(4,1)};
		try {
			rs.isTreeInLocation(-1,-1,perimeter3);
		} catch (InvalidInputException e) {
			error=e.getMessage();
		}
		assertEquals("Error: Coordinates can't be negative", error);*/
		
	}

	@Test
	public void testPointLocation() throws InvalidInputException {
		ReportService rs = new ReportService(tm);
		Location coordinates1 = new Location(1,1);
		Location coordinates2 = new Location(1,4);
		Location coordinates3 = new Location(4,4);
		Location coordinates4 = new Location(4,1);
		Location[] perimeter= {coordinates1, coordinates2, coordinates3, coordinates4};
		
		assertEquals(true, rs.isTreeInLocation(2,2,perimeter));
		//assertEquals(true, rs.isTreeInLocation(1,1,perimeter));//Works here!!
		assertEquals(true, rs.isTreeInLocation(3,3,perimeter));
		//assertEquals(true, rs.isTreeInLocation(4,4,perimeter));//TODO: edge bug
		assertEquals(false, rs.isTreeInLocation(0,0,perimeter));
		assertEquals(false, rs.isTreeInLocation(5,5,perimeter));
	
	}
	@Test
	public void testShape() throws InvalidInputException {
		ReportService rs = new ReportService(tm);
		Location coordinates1 = new Location(1,1);
		Location coordinates2 = new Location(1,4);
		Location coordinates3 = new Location(4,1);
		Location coordinates4 = new Location(4,4);
		Location coordinates5 = new Location(0,2);
		Location coordinates6 = new Location(5,2);
		Location[] triangle = {coordinates1, coordinates2, coordinates3};
		Location[] rectangular = {coordinates1, coordinates2, coordinates4, coordinates3};
		Location[] pentagon = {coordinates1, coordinates5, coordinates2,
				coordinates4, coordinates3};
		Location[] heptagon = {coordinates1, coordinates5, coordinates2,
				coordinates4,coordinates6, coordinates3};
		assertEquals(true, rs.isTreeInLocation(1.5,1.5,triangle));
		assertEquals(true, rs.isTreeInLocation(1.5,1.5,rectangular));
		assertEquals(true, rs.isTreeInLocation(1.5,1.5,pentagon));
		assertEquals(true, rs.isTreeInLocation(1.5,1.5,heptagon));			
	}
	@Test
	public void testPrecision() throws InvalidInputException {
		ReportService rs = new ReportService(tm);
		Location coordinates1 = new Location(0.9088760,0.0653346);
		Location coordinates2 = new Location(0.9088760,4.66328298);
		Location coordinates3 = new Location(4.10653346,4.66328298);
		Location coordinates4 = new Location(4.10653346,0.0653346);
		Location[] perimeter= {coordinates1, coordinates2, coordinates3, coordinates4};
		
		assertEquals(true, rs.isTreeInLocation(2.5,2.5,perimeter));
		assertEquals(true, rs.isTreeInLocation(1.999,1.999,perimeter));
		assertEquals(true, rs.isTreeInLocation(3,3,perimeter));
		assertEquals(true, rs.isTreeInLocation(3.9999,3.9999,perimeter));
		//assertEquals(true, rs.isTreeInLocation(4.10653346,4.66328298,perimeter));//Works here!!
		//assertEquals(true, rs.isTreeInLocation(0.9088760,0.0653346,perimeter));
		//assertEquals(true, rs.isTreeInLocation(0.9088760,4.66328298,perimeter));
		//assertEquals(true, rs.isTreeInLocation(4.10653346,0.0653346,perimeter));//TODO: bug on edges
		assertEquals(false, rs.isTreeInLocation(0,0,perimeter));
	
	}
}
