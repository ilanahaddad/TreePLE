package ca.mcgill.ecse321.TreePLE;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import ca.mcgill.ecse321.TreePLE.persistence.TestPersistence;
import ca.mcgill.ecse321.TreePLE.service.TestTreeManagerService;

@RunWith(Suite.class)
@SuiteClasses({TestTreeManagerService.class, TestPersistence.class})
public class AllTests {

}
