package ca.mcgill.ecse321.TreePLE.service.Report;

import static org.junit.Assert.*;

import java.awt.Polygon;
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

public class TestGetTreesInLocation {

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
	public void testPerimeterNull() {
		
		Location[] perimeter = null;
		
		String species= "White Ash";
		Location treeLoc1 = new Location(1.5,1.5);
		Location treeLoc2 = new Location (2, 2);
		String owner = "Ilana";
		Municipality m = new Municipality("Outremont");
		
		Tree.LandUse land = Tree.LandUse.Residential;
		ReportService rs = new ReportService(tm);
		
		Tree tree1= new Tree(owner, species, 1.5, 0.5, 0, treeLoc1, m );
		Tree tree2= new Tree(owner, species, 1.5, 0.5, 0, treeLoc2, m );
		tm.addTree(tree1);
		tm.addTree(tree2);
		
		String error=null;
		
		try {
			rs.getTreesInLocation(perimeter);
		} catch (InvalidInputException e) {
			error=e.getMessage();
		}
		
		assertEquals("Error: Perimeter is null", error);	
	}
	
	@Test
	public void testCoordinatesNull() {
		Location coordinates1 = null;
		Location coordinates2 = new Location(1,4);
		Location coordinates3 = new Location(4,1);
		Location coordinates4 = new Location(4,4);
		
		Location[] perimeter= {coordinates1, coordinates2, coordinates3, coordinates4};
		
		String species= "White Ash";
		Location treeLoc1 = new Location(1.5,1.5);
		Location treeLoc2 = new Location (2, 2);
		String owner = "Ilana";
		Municipality m = new Municipality("Outremont");
		
		Tree.LandUse land = Tree.LandUse.Residential;
		ReportService rs = new ReportService(tm);
		
		Tree tree1= new Tree(owner, species, 1.5, 0.5, 0, treeLoc1, m );
		Tree tree2= new Tree(owner, species, 1.5, 0.5, 0, treeLoc2, m );
		tm.addTree(tree1);
		tm.addTree(tree2);
		
		String error=null;
		
		try {
			rs.getTreesInLocation(perimeter);
		} catch (InvalidInputException e) {
			error=e.getMessage();
		}
		
		assertEquals("Error: Location coordinates are null", error);	
	}
	
	@Test
	public void plygon() {
		
		int npoints = 4;
		int[] xpoints = {4,4,1,1};
		int[] ypoints = {4,1,1,4};
		Polygon shape = new Polygon(xpoints, ypoints, npoints);
		
		double x = 2;
		double y = 2;
		assertEquals(true, shape.contains(x, y));
		
		x = 1;
		y = 1;
		assertEquals(true, shape.contains(x, y));
		
		x = 3.999999;
		y = 3.999999;
		assertEquals(true, shape.contains(x, y));
		
		x = 4;
		y = 4;
		//assertEquals(true, shape.contains(x, y));
		
		x = 0;
		y = 0;
		assertEquals(false, shape.contains(x, y));
	}
	
	
	@Test
	public void testNumTrees1() throws InvalidInputException {
		
		Location coordinates1 = new Location(1,1);
		Location coordinates2 = new Location(1,4);
		Location coordinates3 = new Location(4,4);
		Location coordinates4 = new Location(4,1);
		Location[] perimeter= {coordinates1, coordinates2, coordinates3, coordinates4};
		String species= "White Ash";
		
		//All trees within perimeter, none on edge
		Location treeLoc1 = new Location(2.5,2.5);
		Location treeLoc2 = new Location (2, 2);
		Location treeLoc3 = new Location (3, 3);
		
		String owner = "Ilana";
		Municipality m = new Municipality("Outremont");
		Tree.LandUse land = Tree.LandUse.Residential;

		Tree tree1= new Tree(owner, species, 1.5, 0.5, 0, treeLoc1, m );
		Tree tree2= new Tree(owner, species, 1.5, 0.5, 0, treeLoc2, m );
		Tree tree3= new Tree(owner, species, 1.5, 0.5, 0, treeLoc3, m );
		
		tm.addTree(tree1);
		tm.addTree(tree2);
		tm.addTree(tree3);
		
		ReportService rs = new ReportService(tm);
		
		int numTreesInLocation = rs.getTreesInLocation(perimeter).size();
		assertEquals(3, numTreesInLocation);	
	}
}
