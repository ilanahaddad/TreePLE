package ca.mcgill.ecse321.TreePLE.dto;

import java.sql.Date;
import java.util.List;

import ca.mcgill.ecse321.TreePLE.model.Tree;
import ca.mcgill.ecse321.TreePLE.model.User;


public class SurveyDto {

	private Date reportDate;
	private TreeDto tree;
	private String surveyor;

	public SurveyDto() {

	}
	public SurveyDto(Date reportDate, TreeDto tree, String surveyor) {
		this.reportDate=reportDate;
		this.surveyor=surveyor;
		this.tree=tree;
	}
	public TreeDto getTree()
	{
		return this.tree;
	}
	public Date getReportDate()
	{
		return this.reportDate;
	}
	public String getSurveyor()
	{
		return this.surveyor;
	}
	public void setTree(TreeDto tree) {
		this.tree = tree;
	}
	public void setUser(String surveyor) {
		this.surveyor=surveyor;
	}

}
