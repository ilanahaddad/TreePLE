package ca.mcgill.ecse321.TreePLE.service;

import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.TreePLE.model.Survey;
import ca.mcgill.ecse321.TreePLE.model.Tree.Status;
import ca.mcgill.ecse321.TreePLE.model.Tree;
import ca.mcgill.ecse321.TreePLE.model.TreeManager;
import ca.mcgill.ecse321.TreePLE.model.VersionManager;
import ca.mcgill.ecse321.TreePLE.model.User.UserType;
import ca.mcgill.ecse321.TreePLE.persistence.PersistenceXStream;
/**
 * This service class contains all the main functionalities of the Survey class
 * @author Ilana Haddad, Diana Serra
 *
 */
@Service
public class SurveyService {
	private TreeManager tm;
	private VersionManager vm;
	/**
	 * The SurveyService constructor verifies which version has been selected by the user, 
	 * finds the TreeManager associated to that version and sets the class's TreeManager as that one for users
	 * to edit attributes on that TreeManager.
	 * @param vm VersionManager of the application
	 */
	public SurveyService(VersionManager vm) {
		List<TreeManager> treemanagers = vm.getTreeManagers();
		for(TreeManager treeM : treemanagers) {
			if(treeM.getIsSelected()) {
				this.tm = treeM;
			}
		}
		this.vm = vm;
	}
	/**
	 * This method checks if the tree manager is editable to the user. Only the latest non-forecasted version is editable.
	 * @param tm current version that is selected
	 * @throws InvalidInputException input exception handling
	 */
	public void checkIfEditable(TreeManager tm)throws InvalidInputException {
		if(!tm.getIsEditable()) {
			throw new InvalidInputException("You cannot edit this version of the system");
		}
	}
	/**
	 * This method refreshes TreeLog to the selected version and checks if it is editable.
	 * @throws InvalidInputException
	 */
	public void refreshSelectedTmAndCheckIfEditable() throws InvalidInputException {
		for(TreeManager treeM : vm.getTreeManagers()) {
			if(treeM.getIsSelected()) {
				this.tm = treeM;
			}
		}
		if(!this.tm.getIsEditable()) {
			throw new InvalidInputException("You cannot edit this version of the system");
		}
	}
	/**
	 * This method creates a new survey for a certain tree
	 * @param reportDate date of the survey (current date)
	 * @param tree desired tree to be surveyed
	 * @param surveyor name of the surveyor
	 * @param newTreeStatus new status of the tree (planted, diseased, to be cut-down, cut-down)
	 * @return returns updated survey
	 * @throws InvalidInputException returns error if inputs are null, empty or if status has not been changed
	 */
	public Survey createSurvey(Date reportDate, Tree tree, String surveyor, Status newTreeStatus) throws InvalidInputException{
		//Check if any of the fields are null, and throw corresponding exception
		refreshSelectedTmAndCheckIfEditable();
		if(reportDate == null) {
			throw new InvalidInputException("Error: Report date cannot be null.\n");
		}
		if(tree == null) {
			throw new InvalidInputException("Error: Tree cannot be null.\n");
		}
		if(surveyor == null) {
			throw new InvalidInputException("Error: Surveyor name cannot be null.\n");
		}
		if(newTreeStatus == null) {
			throw new InvalidInputException("Error: New Tree Status cannot be null.\n");
		}
		if(surveyor=="") {
			throw new InvalidInputException("Error: Surveyor name cannot be empty");
		}
		//check if status is already the one requested
		if(newTreeStatus == tree.getStatus()) { 
			throw new InvalidInputException("Error: This tree already has this status");

		}
		if((newTreeStatus == Status.Diseased|| newTreeStatus == Status.ToBeCutDown)&& tm.getUser().getUsertype() == UserType.LocalResident) {
			throw new InvalidInputException("Only a professional can change tree status to 'To Be Cut Down' or 'Diseased'.\n");
		}
		Survey s = new Survey(surveyor, reportDate, tree);
		s.setNewStatus(getSurveyStatusFromTreeStatus(newTreeStatus));
		tree.setStatus(newTreeStatus);
		tm.addSurvey(s);

		PersistenceXStream.saveToXMLwithXStream(vm);
		return s;
	}
	/**
	 * This method converts a Tree.Status enum to a Survey.Status enum in order to set a survey's status given a tree's status
	 * @param treeStatus tree's status to convert
	 * @return survey status with the same name 
	 */
	public Survey.Status getSurveyStatusFromTreeStatus(Tree.Status treeStatus){
		String treeStatusString = treeStatus.toString();
		for(Survey.Status surveyStatus: Survey.Status.values()) {
			String surveyStatusString = surveyStatus.toString();
			if(surveyStatusString.equals(treeStatusString)) {
				return surveyStatus;
			}
		}
		return null;
	}
	/**
	 * This method looks for the tree the survey is associated to
	 * @param survey to find tree for
	 * @return tree associated to given survey
	 */
	public Tree getTreeForSurvey(Survey survey) {
		return survey.getTree();
	}
	/**
	 * This method gets the tree given an ID
	 * @param id ID#
	 * @return returns desired tree based on ID#
	 */
	public Tree getTreeById(int id) {
		List<Tree> trees = tm.getTrees();
		for(Tree t:trees) {
			if(t.getId()==id) {
				return t;
			}
		}
		return null;
	}
	/**
	 * This method allows the user to edit the date, surveyor and/or status of the tree
	 * @param survey desired survey to be edited
	 * @param editDate new survey date
	 * @param editor new surveyor
	 * @param editedTreeStatus new status to edited
	 * @throws InvalidInputException returns error if inputs or null or if survey does not exist in TreeLog
	 */
	public void editSurvey(Survey survey,Date editDate, 
			String editor, Status editedTreeStatus) throws InvalidInputException{
		refreshSelectedTmAndCheckIfEditable();
		//Check if any of the fields are null, and throw corresponding exception
		if(survey == null||editDate ==null||editor==null||editedTreeStatus==null) {
			throw new InvalidInputException("Error: Survey,Report Date,surveyor, or status is null");
		}
		//Checks if the survey is in the system:
		for(Survey surveyInSystem : tm.getSurveys()) {
			if(survey.equals(surveyInSystem)) {
				surveyInSystem.setSurveyorName(editor);
				surveyInSystem.setReportDate(editDate);
				surveyInSystem.getTree().setStatus(editedTreeStatus);
				PersistenceXStream.saveToXMLwithXStream(tm);
			}
			else{
				throw new InvalidInputException("Error: Survey does not exist");
			}
		}
		PersistenceXStream.saveToXMLwithXStream(vm);
	}
	/**
	 * This method returns all the surveys in TreeLog
	 * @return returns all surveys in system
	 */
	public List<Survey> getAllSurveys(){
		return tm.getSurveys();
	}
	/**
	 * This method returns all the surveys for a certain tree
	 * @param t desired tree
	 * @return returns all the surveys for the desired tree
	 * @throws InvalidInputException returns error if no surveys exist for this tree
	 */
	public List<Survey> getSurveysForTree(Tree t) throws InvalidInputException{
		List<Survey> surveys = null;
		if(t.hasSurveys()) {
			surveys = t.getSurveys();
		}
		if(surveys == null) {
			throw new InvalidInputException("There are no surveys for this tree yet.\n");
		}
		return surveys;
	}
	

}
