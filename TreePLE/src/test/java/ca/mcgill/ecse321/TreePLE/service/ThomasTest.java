package ca.mcgill.ecse321.TreePLE.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.mcgill.ecse321.TreePLE.model.LocalResident;
import ca.mcgill.ecse321.TreePLE.model.Location;
import ca.mcgill.ecse321.TreePLE.model.Municipality;
import ca.mcgill.ecse321.TreePLE.model.Tree;
import ca.mcgill.ecse321.TreePLE.model.Tree.LandUse;
import ca.mcgill.ecse321.TreePLE.model.TreeManager;
import ca.mcgill.ecse321.TreePLE.model.User;
import ca.mcgill.ecse321.TreePLE.model.Version;
import ca.mcgill.ecse321.TreePLE.persistence.PersistenceXStream;

public class listTreesBySpeciesTest {

	private TreeManager tm;
	Location treeLoc = new Location(1.5,1.5);
	Municipality m = new Municipality("Outremont");
	Tree.LandUse land = Tree.LandUse.Residential;
	
	User user;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		PersistenceXStream.initializeModelManager("output" + File.separator + "data.xml");
	}
	
	@Before
	public void setUp() throws Exception {
		user = new User();
		tm=new TreeManager(true, "1.0", 2018,user);	
	}

	@After
	public void tearDown() throws Exception {
		tm.delete();
	}

	@Test
	public void testListTreesbySpecies() { //Add a tree and test that ListTreesbySpecies method can find it
		TreeManagerService tmc = new TreeManagerService(tm);
		String species= "WhiteAsh";
		
		tmc.createTree("Thomas", species, 1, 0.2,3, treeLoc, m, land );
		tmc.createTree("Thomas", "Pine", 2, 0.3, 3, treeLoc, m, land );
		tmc.createTree("Thomas", species, 3, 0.4, 3, treeLoc, m, land );

		assertEquals(2, tm.listTreesBySpecies(species));
	}
	
	@Test
	public void testListTreesbySpeciesNull() { //Tests error handling of ListTreesbySpecies method for null input
		TreeManagerService tmc = new TreeManagerService(tm);
		String species= "WhiteAsh";
		String speciesNull = null;
		String error = null;
		Location treeLoc = new Location(1.5,1.5);
		Location[] ownerLoc = new Location[4];
		Location l1_res1 = new Location(1,1);
		Location l2_res1 = new Location(1,2);
		Location l3_res1 = new Location(2,1);
		Location l4_res1 = new Location(2,2);
		ownerLoc[0]=l1_res1;ownerLoc[1]=l2_res1;ownerLoc[2]=l3_res1;ownerLoc[3]=l4_res1;
		User owner = new LocalResident("Ilana",ownerLoc);
		Municipality m = new Municipality("Outremont");
		Tree.LandUse land = Tree.LandUse.Residential;
		tmc.createTree(species, 1, 0.2, treeLoc,owner, m, land );
		try {
			tmc.listTreesBySpecies(speciesNull, 1.5, 0.2, treeLoc,owner, m, land );
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals("Error: Species name cannot be null!", error);
	}
	
	@Test
	public void testlistTreesBySpeciesEmpty() { //Tests error handling of ListTreesbySpecies method for empty input
		TreeManagerService tmc = new TreeManagerService(tm);
		String species= "WhiteAsh";
		String speciesEmpty = " ";
		String error = null;
		Location treeLoc = new Location(1.5,1.5);
		Location[] ownerLoc = new Location[4];
		Location l1_res1 = new Location(1,1);
		Location l2_res1 = new Location(1,2);
		Location l3_res1 = new Location(2,1);
		Location l4_res1 = new Location(2,2);
		ownerLoc[0]=l1_res1;ownerLoc[1]=l2_res1;ownerLoc[2]=l3_res1;ownerLoc[3]=l4_res1;
		User owner = new LocalResident("Ilana",ownerLoc);
		Municipality m = new Municipality("Outremont");
		Tree.LandUse land = Tree.LandUse.Residential;
		tmc.createTree("Thomas", species, 1, 0.2, 3, treeLoc, m, land );
		try {
			tmc.listTreesBySpecies(speciesEmpty);
		} catch (InvalidInputException e) {
			error= e.getMessage();
		}
		assertEquals("Error: Species name is empty!", error);		
	}
	
	@Test
	public void testlistTreesBySpeciesNoSpecies() { //Tests error handling of ListTreesbySpecies method when no trees are found
		TreeManagerService tmc = new TreeManagerService(tm);
		String species= "WhiteAsh";
		String speciesTest = "Pine";
		String error = null;
		
		tmc.createTree("Thomas",species, 1, 0.2,3, treeLoc, m, land );
		try {
			tmc.listTreesBySpecies(speciesTest);
		} catch (InvalidInputException e) {
			error= e.getMessage();
		}
		assertEquals("Error: There are currently no such species in TreePLE!", error);		
	}
	
	@Test
	public void testSetUserType() {
		
		String user="Professional";
		
		TreeManagerService tmc = new TreeManagerService(tm);
		tmc.setUserType(user);
		
		assertEquals("Professional",tmc.setUserType(user));
	}
	
	@Test
	public void testSetUserTypeNull() {
		
		String user= null;
		String error = null;
		
		TreeManagerService tmc = new TreeManagerService(tm);
		
		try {
			tmc.setUserType(user);
		} catch (InvalidInputException e) {
			error= e.getMessage();
		}
		assertEquals("Error: UserType cannot be null!", error);
	}

}
