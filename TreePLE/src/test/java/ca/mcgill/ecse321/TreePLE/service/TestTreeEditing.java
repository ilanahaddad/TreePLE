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
import ca.mcgill.ecse321.TreePLE.model.Tree.LandUse;
import ca.mcgill.ecse321.TreePLE.model.TreeManager;
import ca.mcgill.ecse321.TreePLE.model.User;
import ca.mcgill.ecse321.TreePLE.model.User.UserType;
import ca.mcgill.ecse321.TreePLE.persistence.PersistenceXStream;

public class TestTreeEditing {
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
		Location treeLoc = new Location(3,3);
		Municipality treeMun = new Municipality("Outremont");
		double height = 10;
		double diameter = 15;
		int age = 3;
		tms = new TreeManagerService(tm);
		try {
			tree = tms.createTree("John", "White Ash", height, diameter, age, treeLoc, treeMun, LandUse.Residential);
		}
		catch(InvalidInputException e) {
			fail();
		}
	}

	@After
	public void tearDown() throws Exception {
		tm.delete();
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testNegativeHeightDiameterAge(){
		assertEquals(1, tm.numberOfTrees());
		String error = null;
		double newHeight = -1;
		double newDiameter = -2.5;
		int newAge = -30;
		Municipality mun = new Municipality("Westmount");
		try {
			tms.updateTreeData(tree, newHeight, newDiameter, newAge, "John", "White Ash", LandUse.Residential,mun );
		}
		catch(InvalidInputException e) {
			error = e.getMessage();
		}
		//check error
		assertEquals("New height, diameter, and age cannot be negative.\n", error);
		
		//check tree was unchanged:
		assertEquals("John", tree.getOwnerName());
		assertEquals("White Ash", tree.getSpecies());
		assertEquals(10, tree.getHeight(),0);
		assertEquals(15, tree.getDiameter(),0);
		assertEquals(3, tree.getAge());
		assertEquals("Outremont", tree.getTreeMunicipality());
		assertEquals(LandUse.Residential, tree.getLand());
		
		//check no change in memory:
		assertEquals(1, tm.numberOfTrees());
	}
	@Test
	public void testNullOwnerName() {
		
	}

}
