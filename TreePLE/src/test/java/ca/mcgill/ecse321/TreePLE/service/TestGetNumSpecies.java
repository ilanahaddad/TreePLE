package ca.mcgill.ecse321.TreePLE.service;

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

public class TestGetNumSpecies {
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
	public void testGetNumSpecies() {
		
		Location bottomLeft= new Location(1,1);
		Location topLeft= new Location(1,4);
		Location bottomRight= new Location(4,1);
		Location topRight= new Location(4,4);
		
		String species= "White Ash";
		Location treeLoc1 = new Location(1.5,1.5);
		Location treeLoc2 = new Location (2, 2);
		String owner = "Ilana";
		Municipality m = new Municipality("Outremont");
		
		Tree.LandUse land = Tree.LandUse.Residential;
		TreeManagerService tmc = new TreeManagerService(tm);
		
		Tree tree1= new Tree(owner, species, 1.5, 0.5, 0, treeLoc1, m );
		Tree tree2= new Tree(owner, species, 1.5, 0.5, 0, treeLoc2, m );
		tm.addTree(tree1);
		tm.addTree(tree2);
		
		int result=0;
		
		try {
			result= tmc.getNumSpecies(bottomLeft,topLeft,bottomRight, topRight);
		} catch (InvalidInputException e) {
			e.printStackTrace();
		}
		
		assertEquals(2, result);
		
	}
	@Test
	public void testLocationNull() {
		
		Location bottomLeft= null;
		Location topLeft= new Location(1,4);
		Location bottomRight= new Location(4,1);
		Location topRight= new Location(4,4);
		
		String species= "White Ash";
		Location treeLoc1 = new Location(1.5,1.5);
		Location treeLoc2 = new Location (2, 2);
		String owner = "Ilana";
		Municipality m = new Municipality("Outremont");
		
		Tree.LandUse land = Tree.LandUse.Residential;
		TreeManagerService tmc = new TreeManagerService(tm);
		
		Tree tree1= new Tree(owner, species, 1.5, 0.5, 0, treeLoc1, m );
		Tree tree2= new Tree(owner, species, 1.5, 0.5, 0, treeLoc2, m );
		tm.addTree(tree1);
		tm.addTree(tree2);
		

		String error=null;
		
		try {
			tmc.getNumSpecies(bottomLeft,topLeft,bottomRight, topRight);
		} catch (InvalidInputException e) {
			error=e.getMessage();
		}
		
		assertEquals("Error: Location cannot be null!", error);

		
	}

}
