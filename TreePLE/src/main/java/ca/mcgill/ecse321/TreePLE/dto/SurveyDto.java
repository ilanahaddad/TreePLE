package ca.mcgill.ecse321.TreePLE.dto;

import java.sql.Date;
import java.util.List;

import ca.mcgill.ecse321.TreePLE.model.Tree;
import ca.mcgill.ecse321.TreePLE.model.User;


public class SurveyDto {

	private Date lastReport;
	private TreeDto tree;
	private UserDto surveyor;

	public SurveyDto() {

	}
	public SurveyDto(Date lastReport, TreeDto tree, UserDto surveyor) {
		this.lastReport=lastReport;
		this.surveyor=surveyor;
		this.tree=tree;
	}
	public TreeDto getTree()
	{
		return tree;
	}
	public Date getLastReport()
	{
		return lastReport;
	}
	public UserDto getSurveyer()
	{
		return surveyor;
	}
	public void setTree(TreeDto tree) {
		this.tree = tree;
	}
	public void setUser(UserDto surveyor) {
		this.surveyor=surveyor;
	}

}
