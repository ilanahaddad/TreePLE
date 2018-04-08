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
import ca.mcgill.ecse321.TreePLE.model.Tree.LandUse;
import ca.mcgill.ecse321.TreePLE.model.TreeManager;
import ca.mcgill.ecse321.TreePLE.model.User;
import ca.mcgill.ecse321.TreePLE.model.VersionManager;
import ca.mcgill.ecse321.TreePLE.model.User.UserType;
import ca.mcgill.ecse321.TreePLE.persistence.PersistenceXStream;
import ca.mcgill.ecse321.TreePLE.service.InvalidInputException;
import ca.mcgill.ecse321.TreePLE.service.TreeManagerService;

public class TestUpdateTreeData {
	private VersionManager vm;
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
		vm = new VersionManager();
		user = new User();
		tm=new TreeManager(true,true, "1.0", 2018,user);
		user.setUsertype(UserType.Professional);
		Location treeLoc = new Location(3,3);
		Municipality treeMun = new Municipality("Outremont");
		double height = 10;
		double diameter = 15;
		int age = 3;
		tree = new Tree("John", "White Ash",height, diameter, age, treeLoc, treeMun);
		tree.setLand(LandUse.Residential);
		tm.addTree(tree);
		vm.addTreeManager(tm);
		tms = new TreeManagerService(vm);
	}

	@After
	public void tearDown() throws Exception {
		vm.delete();
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	@Test
	public void testNullTree(){
		assertEquals(1, tm.numberOfTrees());
		String error = null;
		Tree tree2 = null;
		Municipality mun = new Municipality("Westmount");
		try {
			tms.updateTreeData(tree2, 10, 15, 3, "John", "White Ash", LandUse.Residential,mun );
		}
		catch(InvalidInputException e) {
			error = e.getMessage();
		}
		//check error
		assertEquals("Tree cannot be null. Please select a tree.\n", error);
		
		//check tree was unchanged:
		assertEquals("John", tree.getOwnerName());
		assertEquals("White Ash", tree.getSpecies());
		assertEquals(10, tree.getHeight(),0);
		assertEquals(15, tree.getDiameter(),0);
		assertEquals(3, tree.getAge());
		assertEquals("Outremont", tree.getTreeMunicipality().getName());
		assertEquals(LandUse.Residential, tree.getLand());
		
		//check no change in memory:
		assertEquals(1, tm.numberOfTrees());
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
		assertEquals("Outremont", tree.getTreeMunicipality().getName());
		assertEquals(LandUse.Residential, tree.getLand());
		
		//check no change in memory:
		assertEquals(1, tm.numberOfTrees());
	}
	@Test
	public void testNullOwnerName() {
		assertEquals(1, tm.numberOfTrees());
		String error = null;
		String newOwnerName = null;
		Municipality mun = new Municipality("Westmount");
		try {
			tms.updateTreeData(tree, 10, 15, 3, newOwnerName, "White Ash", LandUse.Residential,mun );
		}
		catch(InvalidInputException e) {
			error = e.getMessage();
		}
		//check error
		assertEquals("New owner name cannot be null.\n", error);
		
		//check tree was unchanged:
		assertEquals("John", tree.getOwnerName());
		assertEquals("White Ash", tree.getSpecies());
		assertEquals(10, tree.getHeight(),0);
		assertEquals(15, tree.getDiameter(),0);
		assertEquals(3, tree.getAge());
		assertEquals("Outremont", tree.getTreeMunicipality().getName());
		assertEquals(LandUse.Residential, tree.getLand());
		
		//check no change in memory:
		assertEquals(1, tm.numberOfTrees());
	}
	@Test 
	public void testEmptyOwnerName() {
		assertEquals(1, tm.numberOfTrees());
		String error = null;
		String newOwnerName = "";
		Municipality mun = new Municipality("Westmount");
		try {
			tms.updateTreeData(tree, 10, 15, 3, newOwnerName, "White Ash", LandUse.Residential,mun );
		}
		catch(InvalidInputException e) {
			error = e.getMessage();
		}
		//check error
		assertEquals("New owner name cannot be empty.\n", error);
		
		//check tree was unchanged:
		assertEquals("John", tree.getOwnerName());
		assertEquals("White Ash", tree.getSpecies());
		assertEquals(10, tree.getHeight(),0);
		assertEquals(15, tree.getDiameter(),0);
		assertEquals(3, tree.getAge());
		assertEquals("Outremont", tree.getTreeMunicipality().getName());
		assertEquals(LandUse.Residential, tree.getLand());
		
		//check no change in memory:
		assertEquals(1, tm.numberOfTrees());
	}
	@Test 
	public void testNullSpeciesName() {
		assertEquals(1, tm.numberOfTrees());
		String error = null;
		String newSpeciesName = null;
		Municipality mun = new Municipality("Westmount");
		try {
			tms.updateTreeData(tree, 10, 15, 3,"Ilana", newSpeciesName, LandUse.Residential,mun );
		}
		catch(InvalidInputException e) {
			error = e.getMessage();
		}
		//check error
		assertEquals("New species name cannot be null.\n", error);
		
		//check tree was unchanged:
		assertEquals("John", tree.getOwnerName());
		assertEquals("White Ash", tree.getSpecies());
		assertEquals(10, tree.getHeight(),0);
		assertEquals(15, tree.getDiameter(),0);
		assertEquals(3, tree.getAge());
		assertEquals("Outremont", tree.getTreeMunicipality().getName());
		assertEquals(LandUse.Residential, tree.getLand());
		
		//check no change in memory:
		assertEquals(1, tm.numberOfTrees());
	}
	@Test
	public void testEmptyAndSpacesSpeciesName() {
		assertEquals(1, tm.numberOfTrees());
		String error = null;
		String[] newSpeciesNames = {"", " ", "  "};
		Municipality mun = new Municipality("Westmount");
		for(String speciesName:newSpeciesNames) {
			try {
				tms.updateTreeData(tree, 10, 15, 3,"Ilana", speciesName, LandUse.Residential,mun );
			}
			catch(InvalidInputException e) {
				error = e.getMessage();
			}	
		}
		
		//check error
		assertEquals("New species name cannot be empty.\n", error);
		
		//check tree was unchanged:
		assertEquals("John", tree.getOwnerName());
		assertEquals("White Ash", tree.getSpecies());
		assertEquals(10, tree.getHeight(),0);
		assertEquals(15, tree.getDiameter(),0);
		assertEquals(3, tree.getAge());
		assertEquals("Outremont", tree.getTreeMunicipality().getName());
		assertEquals(LandUse.Residential, tree.getLand());
		
		//check no change in memory:
		assertEquals(1, tm.numberOfTrees());
	}
	@Test
	public void testInvalidCharactersSpeciesName() {
		assertEquals(1, tm.numberOfTrees());
		String error = null;
		String[] newSpeciesNames = {"Wh!te Ash", "???", "123species45", "American Elm2", "random$@>"};
		Municipality mun = new Municipality("Westmount");
		for(String speciesName:newSpeciesNames) {
			try {
				tms.updateTreeData(tree, 10, 15, 3,"Ilana", speciesName, LandUse.Residential,mun );
			}
			catch(InvalidInputException e) {
				error = e.getMessage();
			}	
		}
		
		//check error
		assertEquals("New species name cannot contain numbers or any special character.\n", error);
		
		//check tree was unchanged:
		assertEquals("John", tree.getOwnerName());
		assertEquals("White Ash", tree.getSpecies());
		assertEquals(10, tree.getHeight(),0);
		assertEquals(15, tree.getDiameter(),0);
		assertEquals(3, tree.getAge());
		assertEquals("Outremont", tree.getTreeMunicipality().getName());
		assertEquals(LandUse.Residential, tree.getLand());
		
		//check no change in memory:
		assertEquals(1, tm.numberOfTrees());
	}
	@Test
	public void testZeroHeightDiameterAge() {
		assertEquals(1, tm.numberOfTrees());
		String error = null;
		double newHeight = 0;
		double newDiameter = 0;
		int newAge = 0;
		Municipality mun = new Municipality("Westmount");
		try {
			tms.updateTreeData(tree, newHeight, newDiameter, newAge, "John", "White Ash", LandUse.Residential,mun );
		}
		catch(InvalidInputException e) {
			error = e.getMessage();
		}
		
		//check tree got updated properly:
		assertEquals("John", tree.getOwnerName());
		assertEquals("White Ash", tree.getSpecies());
		assertEquals(0, tree.getHeight(),0);
		assertEquals(0, tree.getDiameter(),0);
		assertEquals(0, tree.getAge());
		assertEquals("Westmount", tree.getTreeMunicipality().getName());
		assertEquals(LandUse.Residential, tree.getLand());
		
		//check no change in memory:
		assertEquals(1, tm.numberOfTrees());
	}
	@Test 
	public void testNullLandUse() {
		assertEquals(1, tm.numberOfTrees());
		String error = null;
		Municipality mun = new Municipality("Westmount");
		try {
			tms.updateTreeData(tree, 10, 15, 3, "John", "White Ash", null,mun );
		}
		catch(InvalidInputException e) {
			error = e.getMessage();
		}
		//check error
		assertEquals("LandUse cannot be null. Please select a land use type.\n", error);
		
		//check tree was unchanged:
		assertEquals("John", tree.getOwnerName());
		assertEquals("White Ash", tree.getSpecies());
		assertEquals(10, tree.getHeight(),0);
		assertEquals(15, tree.getDiameter(),0);
		assertEquals(3, tree.getAge());
		assertEquals("Outremont", tree.getTreeMunicipality().getName());
		assertEquals(LandUse.Residential, tree.getLand());
		
		//check no change in memory:
		assertEquals(1, tm.numberOfTrees());
		
	}
	public void testNullMunicipality() {
		assertEquals(1, tm.numberOfTrees());
		String error = null;
		Municipality mun = null;
		try {
			tms.updateTreeData(tree, 10, 15, 3, "John", "White Ash", null,mun );
		}
		catch(InvalidInputException e) {
			error = e.getMessage();
		}
		//check error
		assertEquals("LandUse cannot be null. Please select a land use type.\n", error);
		
		//check tree was unchanged:
		assertEquals("John", tree.getOwnerName());
		assertEquals("White Ash", tree.getSpecies());
		assertEquals(10, tree.getHeight(),0);
		assertEquals(15, tree.getDiameter(),0);
		assertEquals(3, tree.getAge());
		assertEquals("Outremont", tree.getTreeMunicipality().getName());
		assertEquals(LandUse.Residential, tree.getLand());
		
		//check no change in memory:
		assertEquals(1, tm.numberOfTrees());
		
	}
	@Test
	public void testSpecialCharacterOwnerNames() {
		assertEquals(1, tm.numberOfTrees());
		String error = null;
		String newOwnerName = "john?&($@";
		Municipality mun = new Municipality("Outremont");
		try {
			tms.updateTreeData(tree, 10, 15, 3,newOwnerName, "White Ash", LandUse.Residential,mun );
		}
		catch(InvalidInputException e) {
			error = e.getMessage();
		}
		
		//check tree changed properly:
		assertEquals("john?&($@", tree.getOwnerName());
		assertEquals("White Ash", tree.getSpecies());
		assertEquals(10, tree.getHeight(),0);
		assertEquals(15, tree.getDiameter(),0);
		assertEquals(3, tree.getAge());
		assertEquals("Outremont", tree.getTreeMunicipality().getName());
		assertEquals(LandUse.Residential, tree.getLand());
		
		//check no change in memory:
		assertEquals(1, tm.numberOfTrees());
	}
	@Test 
	public void testAllValidInputs() {
		assertEquals(1, tm.numberOfTrees());
		String error = null;
		String newSpeciesName = "Pine";
		double newHeight = 1.5;
		double newDiameter = 2;
		int newAge = 4; 
		String newOwnerName = "Ilana";
		Municipality newMun = new Municipality("Westmount");
		try {
			tms.updateTreeData(tree, newHeight, newDiameter, newAge,newOwnerName, newSpeciesName, LandUse.NonResidential,newMun );
		}
		catch(InvalidInputException e) {
			error = e.getMessage();
		}

		//check tree changed all attributes:
		assertEquals(newOwnerName, tree.getOwnerName());
		assertEquals(newSpeciesName, tree.getSpecies());
		assertEquals(newHeight, tree.getHeight(),0);
		assertEquals(newDiameter, tree.getDiameter(),0);
		assertEquals(newAge, tree.getAge());
		assertEquals(newMun.getName(), tree.getTreeMunicipality().getName());
		assertEquals(LandUse.NonResidential, tree.getLand());
		
		//check no change in memory:
		assertEquals(1, tm.numberOfTrees());
	}

}
