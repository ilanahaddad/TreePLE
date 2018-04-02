package ca.mcgill.ecse321.TreePLE;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import ca.mcgill.ecse321.TreePLE.service.Report.TestCreateReport;
import ca.mcgill.ecse321.TreePLE.service.Report.TestGetNumSpecies;
import ca.mcgill.ecse321.TreePLE.service.Report.TestGetTreesInLocation;
import ca.mcgill.ecse321.TreePLE.service.Report.TestIsTreeInLocation;



@RunWith(Suite.class)
@SuiteClasses({TestCreateReport.class,TestGetNumSpecies.class,
	TestGetTreesInLocation.class, TestIsTreeInLocation.class})
public class AllReportTests {

}