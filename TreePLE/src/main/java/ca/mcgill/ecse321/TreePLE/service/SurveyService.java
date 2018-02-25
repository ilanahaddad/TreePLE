package ca.mcgill.ecse321.TreePLE.service;

import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.TreePLE.model.Survey;
import ca.mcgill.ecse321.TreePLE.model.Tree.Status;
import ca.mcgill.ecse321.TreePLE.model.Tree;
import ca.mcgill.ecse321.TreePLE.model.TreeManager;
import ca.mcgill.ecse321.TreePLE.model.User;
import ca.mcgill.ecse321.TreePLE.persistence.PersistenceXStream;

@Service
public class SurveyService {
	private TreeManager tm;

	public SurveyService(TreeManager tm) {
		this.tm=tm;
	}
	//status newtree status
	public Survey createSurvey(Date reportDate, Tree tree, User surveyor, Status newTreeStatus) throws InvalidInputException{
		//Check if any of the fields are null, and throw corresponding exception
		if(reportDate == null||tree ==null||surveyor==null||newTreeStatus==null) {
			throw new InvalidInputException("Error: Report Date, tree, surveyor, or status is null");
		}
		
		//check if status is already the one requested
		if(newTreeStatus == tree.getStatus()) { 
			throw new InvalidInputException("Error: This tree already has this status");

		}
		
		Survey s = new Survey(reportDate, tree, surveyor);
	
		tree.setStatus(Tree.Status.Diseased);
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
