package ca.mcgill.ecse321.TreePLE.service;

import java.sql.Date;
import java.util.List;

import ca.mcgill.ecse321.TreePLE.model.Location;
import ca.mcgill.ecse321.TreePLE.model.Municipality;
import ca.mcgill.ecse321.TreePLE.model.Survey;
import ca.mcgill.ecse321.TreePLE.model.Tree;
import ca.mcgill.ecse321.TreePLE.model.TreeManager;
import ca.mcgill.ecse321.TreePLE.model.User;
import ca.mcgill.ecse321.TreePLE.model.Version;
import ca.mcgill.ecse321.TreePLE.persistence.PersistenceXStream;

public class SurveyService {
	private TreeManager tm;

	public SurveyService(TreeManager tm) {
		this.tm=tm;
	}
	public Survey createSurvey(Date lastReport, Tree tree, User surveyor) throws InvalidInputException{
		/*if(species==null || location==null || owner == null || municipality==null || version == null) {
			throw new InvalidInputException("Error: Species name, tree location, owner, municipality, or version cannot be null!");
		}*/
		Survey s = new Survey(lastReport, tree, surveyor);
		tm.addSurvey(s);
		PersistenceXStream.saveToXMLwithXStream(tm);
		return s;
	}
	public Tree getTreeForSurvey(Survey survey) {
		return survey.getTree();

	}
	public User getSurveyorForSurvey(Survey survey) {
		return survey.getSurveyor();
	}
}
