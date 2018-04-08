package ca.mcgill.ecse321.TreePLE.service.TreeManager;

import static org.junit.Assert.*;

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

public class TestListTreesByMunicipality {
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
	public void testListTreesbyMunicipality(){ //Add a tree and test that ListTreesbyMunicipality method can find it
		String error = "";
		List<Tree> treeWithM = null;
		try {
			tmc.createTree(ownerName, species, treeHeight, treeDiameter, 
					treeAge, treeLoc, m, land);
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
			treeWithM  = tmc.listTreesByMunicipality(m);
		} catch (InvalidInputException e) {
			assertEquals(error, "Was not able to list trees by Municipality");
		}

		Tree currentTree = treeWithM.get(0);
		//	Tree secondTree = treeWithM.get(1);
		assertEquals("Jessica", currentTree.getOwnerName());
		assertEquals("White Ash", currentTree.getSpecies());
		assertEquals(10.5, currentTree.getHeight(),0);
		assertEquals(10.0, currentTree.getDiameter(), 0);
		assertEquals(17, currentTree.getAge(), 0);
		assertEquals("Outremont", currentTree.getTreeMunicipality().getName());
		assertEquals(LandUse.Residential, currentTree.getLand());
		assertEquals(2, treeWithM.size());		
	}
	@Test
		public void testlistTreesByMunicipalityNull() { //Tests error handling of ListTreesbyMunicipality method for empty input
			Municipality nullMun = null;
			String error = "";
			
			try {
				tmc.createTree(ownerName2, species2, treeHeight2, treeDiameter2, 
						treeAge2, treeLoc2, nullMun, land2 );
			} catch (InvalidInputException e) {
				error = e.getMessage();
			}
			
			try {
				List<Tree> treeWithM = tmc.listTreesByMunicipality(nullMun);
			} catch (InvalidInputException e) {
				error = e.getMessage();
			}
			// check error
			assertEquals(error, "Error: Municipality entry cannot be null!");
		}
	@Test
		public void testlistTreesByLandUseNoMunicipality() { //Tests error handling of ListTreesbyMunicipality method when no trees are found
			Municipality noMun1 = new Municipality("Outremont");
			Municipality noMun2 = new Municipality("Ville-Marie");;
			String error = "false";
			
			try {
				tmc.createTree(ownerName2, species2, treeHeight2, treeDiameter2, 
						treeAge2, treeLoc2, noMun1, land2);
			} catch (InvalidInputException e) {
				e.printStackTrace();
			}
			
			try {
				List<Tree> treeWithM = tmc.listTreesByMunicipality(noMun2);
			} catch (InvalidInputException e) {
				error = e.getMessage();
			}
			// check error
			assertEquals("Error: There are currently no such Municipality in TreePLE!", error);	
		}
	
	
}
