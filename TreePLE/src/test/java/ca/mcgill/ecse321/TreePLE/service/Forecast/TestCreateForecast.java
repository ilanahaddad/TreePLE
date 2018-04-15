package ca.mcgill.ecse321.TreePLE.service.Forecast;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.mcgill.ecse321.TreePLE.dto.LocationDto;
import ca.mcgill.ecse321.TreePLE.dto.MunicipalityDto;
import ca.mcgill.ecse321.TreePLE.dto.TreeDto;
import ca.mcgill.ecse321.TreePLE.model.Forecast;
import ca.mcgill.ecse321.TreePLE.model.Location;
import ca.mcgill.ecse321.TreePLE.model.Municipality;
import ca.mcgill.ecse321.TreePLE.model.Tree;
import ca.mcgill.ecse321.TreePLE.model.TreeManager;
import ca.mcgill.ecse321.TreePLE.model.User;
import ca.mcgill.ecse321.TreePLE.model.VersionManager;
import ca.mcgill.ecse321.TreePLE.model.Tree.LandUse;
import ca.mcgill.ecse321.TreePLE.model.User.UserType;
import ca.mcgill.ecse321.TreePLE.persistence.PersistenceXStream;
import ca.mcgill.ecse321.TreePLE.service.ForecastService;
import ca.mcgill.ecse321.TreePLE.service.TreeManagerService;
import ca.mcgill.ecse321.TreePLE.service.VersionManagerService;

public class TestCreateForecast {
	private VersionManager vm;
	private TreeManager tm;
	private User user;
	private ForecastService fs;
	private TreeManagerService tms;
	private VersionManagerService vms;
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
		
		Location treeLoc = new Location(45.48743,-73.600902);
		Municipality treeMun = new Municipality("Westmount");
		double height = 10;
		double diameter = 1.5;
		int age = 20;
		Tree tree = new Tree("John", "Maple",height, diameter, age, treeLoc, treeMun);
		tree.setLand(LandUse.Residential);
		tm.addTree(tree);
		tm.addLocation(treeLoc);
		tm.addMunicipality(treeMun);
		
		vm.addTreeManager(tm);
		fs = new ForecastService(vm);
		tms = new TreeManagerService(vm);
		vms = new VersionManagerService(vm);
	}

	@After
	public void tearDown() throws Exception {
		vm.delete();
	}

	@Test
	public void testCreateFirstForecast() {
		assertEquals(1, vm.numberOfTreeManagers());
		assertEquals(1, vm.getTreeManager(0).numberOfTrees());
		TreeManager tm = vm.getTreeManager(0);
		assertEquals(1, tm.numberOfLocations());
		assertEquals(45.48743, tm.getLocation(0).getLatitude(),0);
		assertEquals(-73.600902, tm.getLocation(0).getLongitude(),0);
		assertEquals(1, tm.numberOfMunicipalities());
		assertEquals("Westmount", tm.getMunicipality(0).getName());
		String baseVersion = tm.getVersion();
		
		List<TreeDto> treesToPlant = new ArrayList<TreeDto>();
		LocationDto locationDto = new LocationDto(45.48700,-73.600802);
		MunicipalityDto munDto = new MunicipalityDto("Westmount");
		TreeDto tDto = new TreeDto("Birch", 3.0, 0.3, 30, locationDto, "Ilana", munDto,Tree.LandUse.NonResidential);
		treesToPlant.add(tDto);
		
		List<Tree> treesToCutDown = new ArrayList<Tree>();
		Tree treeToCutDown = tm.getTree(0);
		treesToCutDown.add(treeToCutDown);
		
		Forecast forecast1 = null;
		try {
			forecast1 = fs.createForecast("Ilana", baseVersion, 2021, treesToPlant,treesToCutDown );
		}
		catch(Exception e) {
			e.printStackTrace();
			fail();
		}
		//check vm now has 3 versions:
		assertEquals(3, vm.numberOfTreeManagers());

		
		//check baseTM hasn't changed
		assertEquals("1.0", tm.getVersion());
		assertEquals(2018, tm.getVersionYear());
		assertEquals(false, tm.getIsEditable());
		assertEquals(false, tm.getIsSelected());
		assertEquals(1, tm.numberOfLocations());
		assertEquals(45.48743, tm.getLocation(0).getLatitude(),0);
		assertEquals(-73.600902, tm.getLocation(0).getLongitude(),0);
		assertEquals(1, tm.numberOfMunicipalities());
		assertEquals("Westmount", tm.getMunicipality(0).getName());
		assertEquals(1, tm.numberOfTrees());
		assertEquals(Tree.Status.Planted, tm.getTree(0).getStatus());
	
		//check info of forecast TM
		TreeManager forecastTM = vm.getTreeManager(1);
		assertEquals("1.1", forecastTM.getVersion());
		assertEquals(2021, forecastTM.getVersionYear());
		assertEquals(false, forecastTM.getIsEditable());
		assertEquals(false, forecastTM.getIsSelected());
		assertEquals(2, forecastTM.numberOfLocations());
		assertEquals(1, forecastTM.numberOfMunicipalities());
		assertEquals(2, forecastTM.numberOfTrees());
		assertEquals("John", forecastTM.getTree(0).getOwnerName());
		assertEquals(Tree.Status.CutDown, forecastTM.getTree(0).getStatus());
		assertEquals("Ilana", forecastTM.getTree(1).getOwnerName());
		
		//check info of copy TM
		TreeManager copyTM = vm.getTreeManager(2);
		assertEquals("2.0", copyTM.getVersion());
		assertEquals(2018, copyTM.getVersionYear());
		assertEquals(true, copyTM.getIsEditable());
		assertEquals(false, copyTM.getIsSelected());
		assertEquals(1, copyTM.numberOfLocations());
		assertEquals(45.48743, copyTM.getLocation(0).getLatitude(),0);
		assertEquals(-73.600902, copyTM.getLocation(0).getLongitude(),0);
		assertEquals(1, copyTM.numberOfMunicipalities());
		assertEquals("Westmount", copyTM.getMunicipality(0).getName());
		assertEquals(1, copyTM.numberOfTrees());
		assertEquals(Tree.Status.Planted, copyTM.getTree(0).getStatus());
		
		//check created forecast info
		assertEquals("Ilana", forecast1.getName());
		assertEquals(2021, forecast1.getYear());
		assertEquals("1.1", forecast1.getVersionCreated());
		
		Forecast forecast2 = null;
		try {
			forecast2 = fs.createForecast("Diana", "1.0", 2022, treesToPlant,treesToCutDown );
		}
		catch(Exception e) {
			e.printStackTrace();
			fail();
		}
		//check vm now has 4 versions:
		assertEquals(4, vm.numberOfTreeManagers());
		
		//check new forecast TM versioning info
		TreeManager forecastTM2 = vm.getTreeManager(3);
		assertEquals("1.2", forecastTM2.getVersion());
		assertEquals(2022, forecastTM2.getVersionYear());
		assertEquals(false, forecastTM2.getIsEditable());
		assertEquals(false, forecastTM2.getIsSelected());
		assertEquals(Tree.Status.CutDown, forecastTM2.getTree(0).getStatus());
		
		//check created forecast info
		assertEquals("Diana", forecast2.getName());
		assertEquals(2022, forecast2.getYear());
		assertEquals("1.2", forecast2.getVersionCreated());
		
		Forecast forecast3 = null;
		try {
			forecast3 = fs.createForecast("Thomas", "2.0", 2019, treesToPlant,treesToCutDown );
		}
		catch(Exception e) {
			e.printStackTrace();
			fail();
		}
		//check vm now has 5 versions:
		assertEquals(6, vm.numberOfTreeManagers());
		
		//check new forecast TM versioning info
		TreeManager forecastTM3 = vm.getTreeManager(4);
		assertEquals("2.1", forecastTM3.getVersion());
		assertEquals(2019, forecastTM3.getVersionYear());
		assertEquals(false, forecastTM3.getIsEditable());
		assertEquals(false, forecastTM3.getIsSelected());
		assertEquals(2, forecastTM3.numberOfLocations());
		assertEquals(1, forecastTM3.numberOfMunicipalities());
		assertEquals(2, forecastTM3.numberOfTrees());
		assertEquals("John", forecastTM3.getTree(0).getOwnerName());
		assertEquals("Ilana", forecastTM3.getTree(1).getOwnerName());
		assertEquals(Tree.Status.CutDown, forecastTM3.getTree(0).getStatus());
		
		//check created forecast info
		assertEquals("Thomas", forecast3.getName());
		assertEquals(2019, forecast3.getYear());
		assertEquals("2.1", forecast3.getVersionCreated());
		
		//check info of copy TM
		TreeManager copyTM2 = vm.getTreeManager(5);
		assertEquals("3.0", copyTM2.getVersion());
		assertEquals(2018, copyTM2.getVersionYear());
		assertEquals(true, copyTM2.getIsEditable());
		assertEquals(false, copyTM2.getIsSelected());
		assertEquals(1, copyTM2.numberOfLocations());
		assertEquals(45.48743, copyTM2.getLocation(0).getLatitude(),0);
		assertEquals(-73.600902, copyTM2.getLocation(0).getLongitude(),0);
		assertEquals(1, copyTM2.numberOfMunicipalities());
		assertEquals("Westmount", copyTM2.getMunicipality(0).getName());
		assertEquals(1, copyTM2.numberOfTrees());
		assertEquals(Tree.Status.Planted, copyTM2.getTree(0).getStatus());
		
		try {
			vms.setSelectedVersion("3.0");
			tms.createMunicipality("Hampstead");
		}
		catch(Exception e) {
			e.printStackTrace();
			fail();
		}

	}

}
