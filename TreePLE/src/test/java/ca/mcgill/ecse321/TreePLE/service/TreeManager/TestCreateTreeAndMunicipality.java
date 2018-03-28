package ca.mcgill.ecse321.TreePLE.service.TreeManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.mcgill.ecse321.TreePLE.model.Location;
import ca.mcgill.ecse321.TreePLE.model.Municipality;
import ca.mcgill.ecse321.TreePLE.model.Tree;
import ca.mcgill.ecse321.TreePLE.model.Tree.LandUse;
import ca.mcgill.ecse321.TreePLE.model.Tree.Status;
import ca.mcgill.ecse321.TreePLE.model.TreeManager;
import ca.mcgill.ecse321.TreePLE.model.User;
import ca.mcgill.ecse321.TreePLE.persistence.PersistenceXStream;
import ca.mcgill.ecse321.TreePLE.service.InvalidInputException;
import ca.mcgill.ecse321.TreePLE.service.TreeManagerService;


public class TestCreateTreeAndMunicipality {

	private TreeManager tm;
	User user;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		PersistenceXStream.initializeModelManager("output" + File.separator + "data.xml");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
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
	public void testCreateTree() {
		assertEquals(0, tm.getTrees().size()); // import Assert from the `org.junit` package
		String species= "White Ash";
		Location treeLoc = new Location(1.5,1.5);
		String owner = "Ilana";
		Municipality m = new Municipality("Outremont");

		Tree.LandUse land = Tree.LandUse.Residential;
		TreeManagerService tmc = new TreeManagerService(tm);
		try {
			tmc.createTree(owner, species, 1.5, 0.2, 0, treeLoc, m,land);
		} catch (InvalidInputException e) {
			e.printStackTrace();
		}
		checkResultTree(owner,species, 1.5, 0.2,0, treeLoc, m, tm, land);
		tm = (TreeManager) PersistenceXStream.loadFromXMLwithXStream();
		// check file contents

		checkResultTree(owner,species, 1.5, 0.2,0, treeLoc, m, tm, land);
	

	}
	@Test
	public void testCreateTreeNull() {
		assertEquals(0, tm.getTrees().size());
		String species = null;
		Location treeLoc = null;
		String owner = null;
		Municipality m = null;

		String error = null;
		Tree.LandUse land = null;
		TreeManagerService tmc = new TreeManagerService(tm);
		try {
			tmc.createTree(owner, species, 1.5, 0.2,0, treeLoc, m, land );
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals("Error: Species name, tree location, owner, municipality, or version cannot be null!", error);

		// check no change in memory
		assertEquals(0, tm.getTrees().size());

	}
	@Test
	public void testCreateTreeEmpty() {
		assertEquals(0, tm.getTrees().size()); // import Assert from the `org.junit` package
		String species= " ";
		Location treeLoc = new Location(1.5,1.5);
		String owner = "Ilana";
		Municipality m = new Municipality("Outremont");
		Tree.LandUse land = Tree.LandUse.Residential;

		String error=null;
		TreeManagerService tmc = new TreeManagerService(tm);
		try {
			tmc.createTree(owner, species, 1.5, 0.2,0,  treeLoc, m,land);
		} catch (InvalidInputException e) {
			error= e.getMessage();
		}
		assertEquals("Error: Species name is empty!", error);

		// check no change in memory
		assertEquals(0, tm.getTrees().size());


	}
	@Test
	public void testCreateTreeAlreadyExisting() {
		assertEquals(0, tm.getTrees().size()); 
		String species= "White Ash";
		Location treeLoc = new Location(1.5,1.5);
		String owner = "Ilana";
		Municipality m = new Municipality("Outremont");
		Tree.LandUse land = Tree.LandUse.Residential;

		String error=null;
		TreeManagerService tmc = new TreeManagerService(tm);
		try {
			tmc.createTree(owner, species, 1.5, 0.2,0, treeLoc, m,land);
		} catch (InvalidInputException e) {
			error= e.getMessage();
		}
		checkResultTree(owner, species, 1.5, 0.2, 0, treeLoc, m, tm, land);
		try {
			tmc.createTree(owner, species, 1.5, 0.2, 0, treeLoc, m,land);
		} catch (InvalidInputException e) {
			error= e.getMessage();
		}

		assertEquals("Error: There is already a tree in this location!", error);

		// check no change in memory
		assertEquals(1, tm.getTrees().size());


	}

	@Test
	public void testCreateMunicipality() {
		assertEquals(0, tm.getMunicipalities().size()); // import Assert from the `org.junit` package

		String MunName="Oakville";

		TreeManagerService tmc = new TreeManagerService(tm);

		try {
			tmc.createMunicipality(MunName);
		} catch (InvalidInputException e) {
			e.printStackTrace();
		}
		checkResultMunicipality(MunName, tm);

		tm = (TreeManager) PersistenceXStream.loadFromXMLwithXStream();
		// check file contents
		checkResultMunicipality(MunName, tm);

	}
	@Test
	public void testCreateMunicipalityNull() {
		assertEquals(0, tm.getMunicipalities().size());
		String munName= null;
		TreeManagerService tmc = new TreeManagerService(tm);
		String error = null;
		try {

			tmc.createMunicipality(munName);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals("Error: Name of Municipality is null!", error);

		// check no change in memory
		assertEquals(0, tm.getMunicipalities().size());


	}
	@Test
	public void testCreateMunicipalityEmpty() {
		assertEquals(0, tm.getMunicipalities().size());
		String munName= " ";
		TreeManagerService tmc = new TreeManagerService(tm);
		String error = null;
		try {

			tmc.createMunicipality(munName);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals("Error: Name of Municipality is blank!", error);

		// check no change in memory
		assertEquals(0, tm.getMunicipalities().size());


	}
	@Test
	public void testCreateMunicipalityAlreadyExisting() {
		assertEquals(0, tm.getMunicipalities().size());
		String munName= "Oakville";
		TreeManagerService tmc = new TreeManagerService(tm);

		String error = null;

		//create municipality "Oakville"
		try {
			tmc.createMunicipality(munName);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		//check it was created okay
		checkResultMunicipality(munName, tm);

		//try to create the same municipality
		try {
			tmc.createMunicipality(munName);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}

		assertEquals("Error: Municipality already exists.", error);

		//check that second version of the same municipality was not created
		assertEquals(1, tm.getMunicipalities().size());


	}

	private void checkResultTree(String owner, String species, double height, double diam, 
			 int age, Location treeLoc, Municipality m, TreeManager tm2, Tree.LandUse land) {
		assertEquals(1, tm2.getTrees().size());
		assertEquals("White Ash", tm.getTree(0).getSpecies());
		assertEquals(1.5, tm.getTree(0).getHeight(),0);
		assertEquals(0.2, tm.getTree(0).getDiameter(),0);
		assertEquals(treeLoc.getLatitude(), tm.getTree(0).getCoordinates().getLatitude(),0);
		assertEquals(treeLoc.getLongitude(), tm.getTree(0).getCoordinates().getLongitude(),0);
		assertEquals(owner, tm.getTree(0).getOwnerName());
		assertEquals(age, tm.getTree(0).getAge());
		assertEquals(m.getName(), tm.getTree(0).getTreeMunicipality().getName());
		assertEquals(LandUse.Residential, tm.getTree(0).getLand());

	}
	private void checkResultMunicipality(String munName, TreeManager tm2) {

		assertEquals(1, tm2.getMunicipalities().size());
		assertEquals(munName, tm.getMunicipality(0).getName());

	}



}
