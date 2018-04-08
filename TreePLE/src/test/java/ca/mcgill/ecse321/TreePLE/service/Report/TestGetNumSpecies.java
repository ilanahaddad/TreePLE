package ca.mcgill.ecse321.TreePLE.service.Report;

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
import ca.mcgill.ecse321.TreePLE.model.VersionManager;
import ca.mcgill.ecse321.TreePLE.model.User.UserType;
import ca.mcgill.ecse321.TreePLE.persistence.PersistenceXStream;
import ca.mcgill.ecse321.TreePLE.service.InvalidInputException;
import ca.mcgill.ecse321.TreePLE.service.ReportService;

public class TestGetNumSpecies {
	private VersionManager vm;
	private TreeManager tm;
	private User user;
	private ReportService rs;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		PersistenceXStream.initializeModelManager("output" + File.separator + "data.xml");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		vm = new VersionManager();
		user = new User();
		tm=new TreeManager(true,true, "1.0", 2018,user);
		user.setUsertype(UserType.Professional);
		vm.addTreeManager(tm);
		rs = new ReportService(vm);
		
		String species1 = "White oak";
		String species2 = "Giant sequoia ";
		String species3 = "English yew";

		Location treeLoc1 = new Location (1,1);
		Location treeLoc2 = new Location (2,2);
		Location treeLoc3 = new Location (3,3);
		Location treeLoc4 = new Location (4,4);

		String owner = "Asma";
		Municipality m = new Municipality("Outremont");

		Tree tree1= new Tree(owner, species1, 1.5, 0.5, 0, treeLoc1, m );
		Tree tree2= new Tree(owner, species1, 1.5, 0.5, 0, treeLoc2, m );
		Tree tree3= new Tree(owner, species2, 1.5, 0.5, 0, treeLoc3, m );
		Tree tree4= new Tree(owner, species3, 1.5, 0.5, 0, treeLoc4, m );

		tm.addTree(tree1);
		tm.addTree(tree2);
		tm.addTree(tree3);
		tm.addTree(tree4);
	}

	@After
	public void tearDown() throws Exception {
		vm.delete();
	}

	@Test
	public void testNull() {
		String error=null;

		Location[] perimeter1 = null;
		try {
			rs.getTreesInLocation(perimeter1);
		} catch (InvalidInputException e) {
			error=e.getMessage();
		}
		assertEquals("Error: Perimeter is null", error);


		Location[] perimeter2= {null,new Location(1,4),new Location(4,4),new Location(4,1)};
		try {
			rs.getTreesInLocation(perimeter2);
		} catch (InvalidInputException e) {
			error=e.getMessage();
		}
		assertEquals("Error: Location coordinates are null", error);

	}
	
	@Test
	public void testNumSpecies() throws InvalidInputException {

		Location[] perimeter1 = {new Location(0,0), new Location(0,2.5), 
				new Location(2.5,2.5), new Location(2.5,0)};
		assertEquals(1, rs.getNumSpecies(perimeter1));
		
		Location[] perimeter2 = {new Location(0,0), new Location(0,3.5), 
				new Location(3.5,3.5), new Location(3.5,0)};
		assertEquals(2, rs.getNumSpecies(perimeter2));
		
		Location[] perimeter3 = {new Location(2.5,2.5), new Location(2.5,5), 
				new Location(5,5), new Location(5,2.5)};
		assertEquals(2, rs.getNumSpecies(perimeter3));
		
		Location[] perimeter4 = {new Location(0,0), new Location(0,5), 
				new Location(5,5), new Location(5,0)};
		assertEquals(3, rs.getNumSpecies(perimeter4));

	}
	

}
