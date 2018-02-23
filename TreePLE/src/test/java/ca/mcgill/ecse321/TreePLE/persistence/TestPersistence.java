package ca.mcgill.ecse321.TreePLE.persistence;

import static org.junit.Assert.*;

import java.io.File;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.mcgill.ecse321.TreePLE.model.LocalResident;
import ca.mcgill.ecse321.TreePLE.model.Location;
import ca.mcgill.ecse321.TreePLE.model.Municipality;
import ca.mcgill.ecse321.TreePLE.model.Tree;
import ca.mcgill.ecse321.TreePLE.model.TreeManager;
import ca.mcgill.ecse321.TreePLE.model.User;
import ca.mcgill.ecse321.TreePLE.model.Version;
import ca.mcgill.ecse321.TreePLE.model.Tree.LandUse;

public class TestPersistence {
	//TODO: ADD SURVEY 
	private TreeManager tm;
	@Before
	public void setUp() throws Exception {
		tm = new TreeManager();

		Location l1 = new Location(1.5,1.5);
		//	Location[] l_res1 = new ArrayList<Location>();
		Location[] l_res1 = new Location[4];
		Location l1_res1 = new Location(1,1);
		Location l2_res1 = new Location(1,2);
		Location l3_res1 = new Location(2,1);
		Location l4_res1 = new Location(2,2);
		l_res1[0]=l1_res1;l_res1[1]=l2_res1;l_res1[2]=l3_res1;l_res1[3]=l4_res1;

		Municipality m = new Municipality("Outremont");
		User res1 = new LocalResident("Ilana", l_res1);
		Version v1 = new Version("1.0",2018);
		
		Tree t1 = new Tree("White Ash",1.5, 0.2,l1,res1,m,v1);
		t1.setLand(LandUse.Residential);
		tm.addTree(t1);
		tm.addLocation(l1);
	}

	@After
	public void tearDown() throws Exception {
		tm.delete();
	}

	@Test
	public void test() {
		// initialize model file
		PersistenceXStream.initializeModelManager("output"+File.separator+"data.xml");
		// save model that is loaded during test setup
		if (!PersistenceXStream.saveToXMLwithXStream(tm))
			fail("Could not save file.");

		// clear the model in memory
		tm.delete();
		assertEquals(0, tm.getTrees().size());
		assertEquals(0, tm.getLocations().size());
		
		// load model
		tm = (TreeManager) PersistenceXStream.loadFromXMLwithXStream();
		if (tm == null)
			fail("Could not load file.");
		
		Location l1 = new Location(1.5,1.5);
		Location[] l_res1 = new Location[4];
		Location l1_res1 = new Location(1,1);
		Location l2_res1 = new Location(1,2);
		Location l3_res1 = new Location(2,1);
		Location l4_res1 = new Location(2,2);
		l_res1[0]=l1_res1;l_res1[1]=l2_res1;l_res1[2]=l3_res1;l_res1[3]=l4_res1;
		Municipality m = new Municipality("Outremont");
		User owner = new LocalResident("Ilana", l_res1);
		Version v1 = new Version("1.0",2018);
		double height = 1.5;
		double diameter = 0.2;
		
		// check tree attributes
		assertEquals(1, tm.getTrees().size());
		assertEquals("White Ash", tm.getTree(0).getSpecies());
		assertEquals(height, tm.getTree(0).getHeight(),0);
		assertEquals(diameter, tm.getTree(0).getDiameter(),0);
		assertEquals(1, tm.getTree(0).getId());
		assertEquals(m.getName(), tm.getTree(0).getTreeMunicipality().getName());
		assertEquals(l1.getLatitude(), tm.getTree(0).getCoordinates().getLatitude(),0);
		assertEquals(l1.getLongitude(), tm.getTree(0).getCoordinates().getLongitude(),0);
		assertEquals(v1.getIdNumber(), tm.getTree(0).getVersion(0).getIdNumber());
		assertEquals(owner.getName(), tm.getTree(0).getOwner().getName());
		assertEquals(LandUse.Residential, tm.getTree(0).getLand());
		
		//check Location
		assertEquals(1, tm.getLocations().size());
		assertEquals(1.5, tm.getLocation(0).getLatitude(),0);
		assertEquals(1.5, tm.getLocation(0).getLongitude(),0);
		

	}

}
