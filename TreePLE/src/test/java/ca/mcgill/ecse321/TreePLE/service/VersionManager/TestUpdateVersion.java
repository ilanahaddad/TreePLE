package ca.mcgill.ecse321.TreePLE.service.VersionManager;

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

public class TestUpdateVersion {
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
	public void testUpdateVersion() {
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
		
		//check forecast worked
		TreeManager forecastTM = vm.getTreeManager(1);
		assertEquals("1.1", forecastTM.getVersion());
		assertEquals(2, forecastTM.numberOfTrees());
		
		//check unchanged base
		TreeManager baseTM = vm.getTreeManager(0);
		assertEquals(1, baseTM.numberOfTrees());
		
		try {
			vms.setSelectedVersion("1.1");
		}
		catch(Exception e) {
			e.printStackTrace();
			fail();
		}
		//check only TM 1.1 has selected as true
		int numTMsSelected = 0;
		for(TreeManager tm: vm.getTreeManagers()) {
			if(tm.getIsSelected()) {
				numTMsSelected++;
			}
		}
		//get TM for selected version:
		String versionSelected = vms.getCurrentVersionNumber();
		TreeManager tmSelected = null;
		for(TreeManager tm: vm.getTreeManagers()) {
			if(tm.getVersion().equals(versionSelected)) {
				tmSelected = tm;
			}
		}
		assertEquals(tmSelected, forecastTM);
		assertEquals(2, tmSelected.numberOfTrees());
		
		assertEquals(1,numTMsSelected );
		List<Tree> treesOfSelectedTM = tms.findAllTrees();
		assertEquals(2, treesOfSelectedTM.size());
	}

}
