package ca.mcgill.ecse321.TreePLE;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import ca.mcgill.ecse321.TreePLE.persistence.TestPersistence;
import ca.mcgill.ecse321.TreePLE.service.Forecast.TestCreateForecast;
import ca.mcgill.ecse321.TreePLE.service.Report.TestCreateReport;
import ca.mcgill.ecse321.TreePLE.service.Report.TestGetNumSpecies;
import ca.mcgill.ecse321.TreePLE.service.Survey.TestCreateSurvey;
import ca.mcgill.ecse321.TreePLE.service.Survey.TestEditSurvey;
import ca.mcgill.ecse321.TreePLE.service.TreeManager.TestCreateTreeAndMunicipality;
import ca.mcgill.ecse321.TreePLE.service.TreeManager.TestListTreesByLandUse;
import ca.mcgill.ecse321.TreePLE.service.TreeManager.TestListTreesByMunicipality;
import ca.mcgill.ecse321.TreePLE.service.TreeManager.TestListTreesBySpecies;
import ca.mcgill.ecse321.TreePLE.service.TreeManager.TestListTreesByStatus;
import ca.mcgill.ecse321.TreePLE.service.TreeManager.TestMoveTree;
import ca.mcgill.ecse321.TreePLE.service.TreeManager.TestUpdateTreeData;

@RunWith(Suite.class)
@SuiteClasses({TestPersistence.class, 
	TestCreateForecast.class,
	TestCreateReport.class,TestGetNumSpecies.class,
	TestEditSurvey.class,TestCreateSurvey.class,
	TestCreateTreeAndMunicipality.class, TestListTreesByLandUse.class, TestListTreesByMunicipality.class,
	TestListTreesBySpecies.class, TestListTreesByStatus.class, TestMoveTree.class, TestUpdateTreeData.class
	})
public class AllTests {

}
