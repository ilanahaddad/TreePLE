package ca.mcgill.ecse321.TreePLE;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import ca.mcgill.ecse321.TreePLE.service.Survey.TestEditSurvey;
import ca.mcgill.ecse321.TreePLE.service.Survey.TestCreateSurvey;



@RunWith(Suite.class)
@SuiteClasses({TestEditSurvey.class,TestCreateSurvey.class })
public class AllSurveyTests {

}
