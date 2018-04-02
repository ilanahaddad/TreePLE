package ca.mcgill.ecse321.TreePLE.service.TreeManager;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.mcgill.ecse321.TreePLE.model.TreeManager;
import ca.mcgill.ecse321.TreePLE.model.User;
import ca.mcgill.ecse321.TreePLE.model.User.UserType;
import ca.mcgill.ecse321.TreePLE.persistence.PersistenceXStream;
import ca.mcgill.ecse321.TreePLE.service.InvalidInputException;
import ca.mcgill.ecse321.TreePLE.service.TreeManagerService;

public class TestCreateLocation {
	private TreeManager tm;
	private User user;
	private TreeManagerService tms;

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
		user.setUsertype(UserType.Professional);
		tms = new TreeManagerService(tm);
	}

	@After
	public void tearDown() throws Exception {
		tm.delete();
	}

	@Test 
	public void testInvalidLatitudes() {
		assertEquals(0, tm.numberOfLocations());
		String error = null;
		double[] latitudes = {-91, 91};
		for(int i=0; i<latitudes.length;i++) {
			try {
				tms.createLocation(latitudes[i], 10);
			} catch (InvalidInputException e) {
				error = e.getMessage();
			}
		}
		//check error
		assertEquals("Latitude must be in range [-90,90].\n", error);
		
		//check no location was created:
		assertEquals(0, tm.numberOfLocations());
	}
	@Test 
	public void testInvalidLongitudes() {
		assertEquals(0, tm.numberOfLocations());
		String error = null;
		double[] longitudes = {-181, 181};
		for(int i=0; i<longitudes.length;i++) {
			try {
				tms.createLocation(15, longitudes[i]);
			} catch (InvalidInputException e) {
				error = e.getMessage();
			}
		}
		//check error
		assertEquals("Longitude must be in range [-180,180].\n", error);
		
		//check no location was created:
		assertEquals(0, tm.numberOfLocations());
	}
	@Test
	public void testValidOnBoundaries() {
		assertEquals(0, tm.numberOfLocations());
		String error = null;
		double latitude = 90;
		double longitude = -180;
		try {
			tms.createLocation(latitude, longitude);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		//check no error message:
		assertEquals(null, error);
		
		//check new location was created with given values
		assertEquals(latitude, tm.getLocation(0).getLatitude(),0);
		assertEquals(longitude, tm.getLocation(0).getLongitude(),0);
		
		//check new location was added to tm:
		assertEquals(1, tm.numberOfLocations());
	}

}
