package ca.mcgill.ecse321.TreePLE.service.TreeManager;

import static org.junit.Assert.assertEquals;


import java.util.List;

import org.junit.After;

import org.junit.Before;

import org.junit.Test;


import ca.mcgill.ecse321.TreePLE.model.Location;
import ca.mcgill.ecse321.TreePLE.model.Municipality;
import ca.mcgill.ecse321.TreePLE.model.Tree;
import ca.mcgill.ecse321.TreePLE.model.Tree.LandUse;
import ca.mcgill.ecse321.TreePLE.persistence.PersistenceXStream;
import ca.mcgill.ecse321.TreePLE.service.InvalidInputException;
import ca.mcgill.ecse321.TreePLE.service.TreeManagerService;
import ca.mcgill.ecse321.TreePLE.model.TreeManager;
import ca.mcgill.ecse321.TreePLE.model.User;
import ca.mcgill.ecse321.TreePLE.model.VersionManager;


public class TestListTreesBySpecies {
	private VersionManager vm;
	private TreeManagerService tmc;
	private TreeManager tm;
	Location treeLoc = new Location(1.5,1.5);
	Municipality m = new Municipality("Outremont");
	Tree.LandUse land = Tree.LandUse.Residential;
	User user;

	@Before
	public void setUp() throws Exception {
		vm = new VersionManager();
		user = new User();
		tm=new TreeManager(true, "1.0", 2018,user);	
		vm.addTreeManager(tm);
		tmc = new TreeManagerService(vm);
	}

	@After
	public void tearDown() throws Exception {
		vm.delete();
	}

	@Test
	public void testListTreesbySpecies() { //Add a tree and test that ListTreesbySpecies method can find it
		String species= "WhiteAsh";
		String error = "false";
		List<Tree> trees = null;
		
		try {
			tmc.createTree("Thomas", species, 1, 0.2,3, treeLoc, m, land );
		} catch (InvalidInputException e) {
			assertEquals(error, "Was not able to create tree");
		}
		
		try {
			trees = tmc.listTreesBySpecies(species);
		} catch (InvalidInputException e) {
			assertEquals(error, "Was not able to list trees by species");
		}
		assertEquals(1, trees.size());
		Tree currentTree = trees.get(0);
		assertEquals("Thomas", currentTree.getOwnerName());
		assertEquals(species, currentTree.getSpecies());
		assertEquals(1, currentTree.getHeight(),0);
		assertEquals(0.2, currentTree.getDiameter(), 0);
		assertEquals(3, currentTree.getAge(), 0);
		assertEquals("Outremont", currentTree.getTreeMunicipality().getName());
		assertEquals(LandUse.Residential, currentTree.getLand());
		
	}
	
	@Test
	public void testListTreesbySpeciesNull() { //Tests error handling of ListTreesbySpecies method for null input
		String species= null;
		String error = "false";
		
		try {
			tmc.createTree("Thomas", "WhiteAsh", 1, 0.2,3, treeLoc, m, land );
		} catch (InvalidInputException e) {
			assertEquals(error, "Was not able to create tree");
		}
		
		try {
			List<Tree> trees = tmc.listTreesBySpecies(species);
		} catch (InvalidInputException e) {
			error = e.getMessage();;
		}
		// check error
		assertEquals("Error: Species name cannot be null!", error);
		
	}
	
	
	@Test
	public void testlistTreesBySpeciesEmpty() { //Tests error handling of ListTreesbySpecies method for empty input
		String species= " ";
		String error = "false";
		
		try {
			tmc.createTree("Thomas", "WhiteAsh", 1, 0.2,3, treeLoc, m, land );
		} catch (InvalidInputException e) {
			assertEquals(error, "Was not able to create tree");
		}
		
		try {
			List<Tree> trees = tmc.listTreesBySpecies(species);
		} catch (InvalidInputException e) {
			error = e.getMessage();;
		}
		// check error
		assertEquals("Error: Species name is empty!", error);
	}
	
	
	@Test
	public void testlistTreesBySpeciesNoSpecies() { //Tests error handling of ListTreesbySpecies method when no trees are found
		String species= "Pine";
		String error = "false";
		
		try {
			tmc.createTree("Thomas", "WhiteAsh", 1, 0.2,3, treeLoc, m, land );
		} catch (InvalidInputException e) {
			assertEquals(error, "Was not able to create tree");
		}
		
		try {
			List<Tree> trees = tmc.listTreesBySpecies(species);
		} catch (InvalidInputException e) {
			error = e.getMessage();;
		}
		// check error
		assertEquals("Error: There are currently no such species in TreePLE!", error);	
	}
}
