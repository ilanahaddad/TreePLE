package ca.mcgill.ecse321.TreePLE;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import ca.mcgill.ecse321.TreePLE.service.Forecast.TestCreateForecast;
import ca.mcgill.ecse321.TreePLE.service.VersionManager.TestUpdateVersion;


@RunWith(Suite.class)
@SuiteClasses({TestUpdateVersion.class})
public class AllVersionManagerTests {

}

