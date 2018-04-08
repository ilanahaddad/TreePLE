package ca.mcgill.ecse321.TreePLE;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import ca.mcgill.ecse321.TreePLE.service.TreeManager.TestCreateLocation;
import ca.mcgill.ecse321.TreePLE.service.TreeManager.TestCreateTreeAndMunicipality;
import ca.mcgill.ecse321.TreePLE.service.TreeManager.TestListTreesByLandUse;
import ca.mcgill.ecse321.TreePLE.service.TreeManager.TestListTreesByMunicipality;
import ca.mcgill.ecse321.TreePLE.service.TreeManager.TestListTreesBySpecies;
import ca.mcgill.ecse321.TreePLE.service.TreeManager.TestListTreesByStatus;
import ca.mcgill.ecse321.TreePLE.service.TreeManager.TestMoveTree;
import ca.mcgill.ecse321.TreePLE.service.TreeManager.TestUpdateTreeData;

@RunWith(Suite.class)
@SuiteClasses({TestCreateTreeAndMunicipality.class, TestListTreesByLandUse.class, 
	TestListTreesByMunicipality.class,TestListTreesBySpecies.class, 
	TestListTreesByStatus.class, TestMoveTree.class, TestUpdateTreeData.class,
	TestCreateLocation.class})
public class AllTreeManagerTests {

}
