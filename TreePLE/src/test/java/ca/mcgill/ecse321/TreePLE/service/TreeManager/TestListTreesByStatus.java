package ca.mcgill.ecse321.TreePLE.service.TreeManager;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.mcgill.ecse321.TreePLE.model.Location;
import ca.mcgill.ecse321.TreePLE.model.Municipality;
import ca.mcgill.ecse321.TreePLE.model.Tree;
import ca.mcgill.ecse321.TreePLE.model.Tree.LandUse;
import ca.mcgill.ecse321.TreePLE.model.Tree.Status;
import ca.mcgill.ecse321.TreePLE.persistence.PersistenceXStream;
import ca.mcgill.ecse321.TreePLE.service.InvalidInputException;
import ca.mcgill.ecse321.TreePLE.service.TreeManagerService;
import ca.mcgill.ecse321.TreePLE.model.TreeManager;
import ca.mcgill.ecse321.TreePLE.model.User;
import ca.mcgill.ecse321.TreePLE.model.VersionManager;

public class TestListTreesByStatus {
	private VersionManager vm;
	private TreeManagerService tmc;
	private static TreeManager tm;
	//characteristics for first tree
	String ownerName = "Jessica";
	String species = "White Ash";
	double treeHeight = 10.5;
	double treeDiameter = 10.0;
	int treeAge = 17;
	Location treeLoc = new Location(1.5,1.5);
	Municipality m = new Municipality("Outremont");
	//Municipality m2 = new Municipality("Ville-Marie");
	Tree.LandUse land = Tree.LandUse.Residential;

	//characteristics for second tree
	String ownerName2 = "Trevor";
	String species2 = "Cedar";
	double treeHeight2 = 36.0;
	double treeDiameter2 = 12.4;
	int treeAge2 = 10;
	Location treeLoc2 = new Location(3.0,4.5);
	Tree.LandUse land2 = Tree.LandUse.Residential;

	static User user;

	@Before
	public  void setUp() throws Exception {
		vm = new VersionManager();
		user = new User();
		tm=new TreeManager(true,true, "1.0", 2018,user);
		vm.addTreeManager(tm);
		tmc = new TreeManagerService(vm);
	}


	@After
	public void tearDown() throws Exception {
		vm.delete();
	}

	@Test
	public void testListTreesbyStatus() throws InvalidInputException { //Add a tree and test that ListTreesbyStatus method can find it
		String error = "false";
		Status status = Status.Planted;
		List<Tree> treeWithStatus = null;
		try {
			tmc.createTree(ownerName, species, treeHeight, treeDiameter, 
					treeAge, treeLoc, m, land);

		//	tree1.setStatus() = status;
		} catch (InvalidInputException e) {
			e.printStackTrace();
		}
		//create second tree
		try {
			tmc.createTree(ownerName2, species2, treeHeight2, treeDiameter2, 
					treeAge2, treeLoc2, m, land2);
		} catch (InvalidInputException e) {
			e.printStackTrace();
		}

		try {
			treeWithStatus  = tmc.listTreesByStatus(status);
		} catch (InvalidInputException e) {
			assertEquals(error, "Was not able to list trees by Status");
		}

		Tree currentTree = treeWithStatus.get(0);
		//	Tree secondTree = treeWithM.get(1);
		assertEquals("Jessica", currentTree.getOwnerName());
		assertEquals("White Ash", currentTree.getSpecies());
		assertEquals(10.5, currentTree.getHeight(),0);
		assertEquals(10.0, currentTree.getDiameter(), 0);
		assertEquals(17, currentTree.getAge(), 0);
		assertEquals("Outremont", currentTree.getTreeMunicipality().getName());
		assertEquals(LandUse.Residential, currentTree.getLand());
		assertEquals(2, treeWithStatus.size());		//Proves that a tree created is automatically set to planted
		//in the survey method, you can edit the tree status
	}

	
	
}
