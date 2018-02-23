package ca.mcgill.ecse321.TreePLE.service;

import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.TreePLE.model.Location;
import ca.mcgill.ecse321.TreePLE.model.Municipality;
import ca.mcgill.ecse321.TreePLE.model.Survey;
import ca.mcgill.ecse321.TreePLE.model.Tree;
import ca.mcgill.ecse321.TreePLE.model.TreeManager;
import ca.mcgill.ecse321.TreePLE.model.User;
import ca.mcgill.ecse321.TreePLE.model.Version;
import ca.mcgill.ecse321.TreePLE.persistence.PersistenceXStream;

@Service
public class SurveyService {
	private TreeManager tm;

	public SurveyService(TreeManager tm) {
		this.tm=tm;
	}
	public Survey createSurvey(Date reportDate, Tree tree, User surveyor) throws InvalidInputException{
		/*if(species==null || location==null || owner == null || municipality==null || version == null) {
			throw new InvalidInputException("Error: Species name, tree location, owner, municipality, or version cannot be null!");
		}*/
		Survey s = new Survey(reportDate, tree, surveyor);
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
	public Tree getTreeById(int id) {
		List<Tree> trees = tm.getTrees();
		for(Tree t: trees) {
			if(t.getId()==id) {
				return t;
			}
		}
		return null;
	}
	public User getSurveyorByName(String name) {
		//look through all users and check if name matches
		List<User> users = tm.getUsers();
		for(User u: users) {
			if(u.getName().equals(name)) {
				return u;
			}
		}
		return null;
	}
}
