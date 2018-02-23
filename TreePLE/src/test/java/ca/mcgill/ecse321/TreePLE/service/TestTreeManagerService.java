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


public class TestTreeManagerService {
	//TODO: ADD SURVEY 
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
		tm=new TreeManager();
	}

	@After
	public void tearDown() throws Exception {
		tm.delete();
	}

	@Test
	public void testCreateTree() {
		assertEquals(0, tm.getTrees().size()); // import Assert from the `org.junit` package
		String species= "White Ash";
		Location treeLoc = new Location(1.5,1.5);
		Location[] ownerLoc = new Location[4];
		Location l1_res1 = new Location(1,1);
		Location l2_res1 = new Location(1,2);
		Location l3_res1 = new Location(2,1);
		Location l4_res1 = new Location(2,2);
		ownerLoc[0]=l1_res1;ownerLoc[1]=l2_res1;ownerLoc[2]=l3_res1;ownerLoc[3]=l4_res1;
		User owner = new LocalResident("Ilana",ownerLoc);
		Municipality m = new Municipality("Outremont");
		Version v1 = new Version("1.0",2018);
		Tree.LandUse land = Tree.LandUse.Residential;
		TreeManagerService tmc = new TreeManagerService(tm);
		try {
			tmc.createTree(species, 1.5, 0.2, treeLoc,owner, m,v1,land);
		} catch (InvalidInputException e) {
			e.printStackTrace();
		}
		checkResultTree(species, 1.5, 0.2, treeLoc,owner, m,v1, tm, land);
		tm = (TreeManager) PersistenceXStream.loadFromXMLwithXStream();
		// check file contents
		checkResultTree(species, 1.5, 0.2, treeLoc,owner, m,v1, tm, land);
	
	}
	@Test
	public void testCreateTreeNull() {
		assertEquals(0, tm.getTrees().size());
		String species = null;
		Location treeLoc = null;
		User owner = null;
		Municipality m = null;
		Version v1 =null;
		
		String error = null;
		Tree.LandUse land = null;
		TreeManagerService tmc = new TreeManagerService(tm);
		try {
			tmc.createTree(species, 1.5, 0.2, treeLoc,owner, m,v1, land);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals("Error: Species name, tree location, owner, municipality, or version cannot be null!", error);

		// check no change in memory
		assertEquals(0, tm.getTrees().size());
		
	}
	

	private void checkResultTree(String species, double height, double diam, 
			 Location treeLoc, User owner,
			Municipality m, Version v1, TreeManager tm2, Tree.LandUse land) {
		assertEquals(1, tm2.getTrees().size());
		assertEquals("White Ash", tm.getTree(0).getSpecies());
		assertEquals(1.5, tm.getTree(0).getHeight(),0);
		assertEquals(0.2, tm.getTree(0).getDiameter(),0);
		assertEquals(1, tm.getTree(0).getId());
		assertEquals(treeLoc.getLatitude(), tm.getTree(0).getCoordinates().getLatitude(),0);
		assertEquals(treeLoc.getLongitude(), tm.getTree(0).getCoordinates().getLongitude(),0);
		assertEquals(owner.getName(), tm.getTree(0).getOwner().getName());
		assertEquals(m.getName(), tm.getTree(0).getTreeMunicipality().getName());
		assertEquals(v1.getIdNumber(), tm.getTree(0).getVersion(0).getIdNumber());
		assertEquals(LandUse.Residential, tm.getTree(0).getLand());
		
	}

	

}
