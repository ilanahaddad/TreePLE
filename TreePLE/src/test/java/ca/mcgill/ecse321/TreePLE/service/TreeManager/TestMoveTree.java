package ca.mcgill.ecse321.TreePLE.service.TreeManager;

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
import ca.mcgill.ecse321.TreePLE.model.Tree.LandUse;
import ca.mcgill.ecse321.TreePLE.model.User.UserType;
import ca.mcgill.ecse321.TreePLE.persistence.PersistenceXStream;
import ca.mcgill.ecse321.TreePLE.service.InvalidInputException;
import ca.mcgill.ecse321.TreePLE.service.TreeManagerService;

public class TestMoveTree {
	private TreeManager tm;
	private User user;
	private TreeManagerService tms;
	private Tree tree;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		PersistenceXStream.initializeModelManager("output" + File.separator + "data.xml");
	}

	@Before
	public void setUp() throws Exception {
		user = new User();
		tm = new TreeManager(true, "1.0", 2018, user);
		user.setUsertype(UserType.Professional);
		Location treeLoc = new Location(3,5);
		tm.addLocation(treeLoc);
		Municipality treeMun = new Municipality("Outremont");
		double height = 10;
		double diameter = 15;
		int age = 3;
		tms = new TreeManagerService(tm);
		tree = new Tree("Ben", "American Elm",height, diameter, age, treeLoc, treeMun);
		tree.setLand(LandUse.Residential);
		tm.addTree(tree);
	}

	@After
	public void tearDown() throws Exception {
		tm.delete();
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	@Test
	public void testNullTree() {
		assertEquals(1, tm.numberOfTrees());
		String error = null;
		Tree tree2 = null;
		try {
			tms.moveTree(tree2, 15,10 );
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		//check error
		assertEquals("Tree cannot be null. Please select a tree.\n", error);
		
		//check tree was unchanged:
		assertEquals(3, tree.getCoordinates().getLatitude(),0);
		assertEquals(5, tree.getCoordinates().getLongitude(),0);
		
		//check no change in memory:
		assertEquals(1, tm.numberOfTrees());
	}
	@Test
	public void testErrorLocationAlreadyOccupiedByTree() {
		assertEquals(1, tm.numberOfTrees());
		assertEquals(3, tm.getTree(0).getCoordinates().getLatitude(),0);
		assertEquals(5, tm.getTree(0).getCoordinates().getLongitude(),0);
		String error = null;
		Location tree2Loc = new Location(30,50);
		Municipality treeMun = new Municipality("Outremont");
		double height = 11;
		double diameter = 16;
		int age = 4;
		Tree tree2 = new Tree("Jack", "American Elm",height, diameter, age, tree2Loc, treeMun);
		tree.setLand(LandUse.Residential);
		//tm.addTree(tree2);
		try { //try to move tree2 at location tree1 is in 
			tms.moveTree(tree2, 3,5);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		//check it throws the correct error
		assertEquals("There's already a tree in this location.\n", error);
		//check no change in memory:
		assertEquals(1, tm.numberOfTrees());
	}
	@Test
	public void testMoveTreeToExistingLocation() {
		
	}
	/*
	@Test 
	public void testInvalidLatitudes() {
		assertEquals(1, tm.numberOfTrees());
		String error = null;
		double[] latitudes = {-91, 91};
		for(int i=0; i<latitudes.length;i++) {
			try {
				tms.moveTree(tree, latitudes[i], 10);
			} catch (InvalidInputException e) {
				error = e.getMessage();
			}
		}
		//check error
		assertEquals("New latitude must be in range [-90,90].\n", error);
		
		//check tree was unchanged:
		assertEquals(3, tree.getCoordinates().getLatitude(),0);
		assertEquals(5, tree.getCoordinates().getLongitude(),0);
		
		//check no change in memory:
		assertEquals(1, tm.numberOfTrees());
	}
	@Test 
	public void testInvalidLongitudes() {
		assertEquals(1, tm.numberOfTrees());
		String error = null;
		double[] longitudes = {-181, 181};
		for(int i=0; i<longitudes.length;i++) {
			try {
				tms.moveTree(tree, 15, longitudes[i]);
			} catch (InvalidInputException e) {
				error = e.getMessage();
			}
		}
		//check error
		assertEquals("New longitude must be in range [-180,180].\n", error);
		
		//check tree was unchanged:
		assertEquals(3, tree.getCoordinates().getLatitude(),0);
		assertEquals(5, tree.getCoordinates().getLongitude(),0);
		
		//check no change in memory:
		assertEquals(1, tm.numberOfTrees());
	}
	@Test
	public void testValidOnBoundaries() {
		assertEquals(1, tm.numberOfTrees());
		String error = null;
		double latitude = 90;
		double longitude = -180;
		try {
			tms.moveTree(tree, latitude,longitude );
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		//check tree moved:
		assertEquals(latitude, tree.getCoordinates().getLatitude(),0);
		assertEquals(longitude, tree.getCoordinates().getLongitude(),0);
		
		//check no change in memory:
		assertEquals(1, tm.numberOfTrees());
	}*/
	@Test
	public void testAllValidInputs() {
		assertEquals(1, tm.numberOfTrees());
		String error = null;
		double latitude = 90;
		double longitude = -180;
		try {
			tms.moveTree(tree, latitude,longitude );
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		//check tree moved:
		assertEquals(latitude, tree.getCoordinates().getLatitude(),0);
		assertEquals(longitude, tree.getCoordinates().getLongitude(),0);
		
		//check the old location contains no tree
		Location oldTreeLoc = new Location(3,5);
		assertEquals(false,oldTreeLoc.hasTreeInLocation());

		//check no change in memory:
		assertEquals(1, tm.numberOfTrees());
	}

}
