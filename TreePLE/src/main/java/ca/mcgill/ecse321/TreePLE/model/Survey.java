/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.26.1-f40f105-3613 modeling language!*/

package ca.mcgill.ecse321.TreePLE.model;
import java.sql.Date;

// line 34 "../../../../../TreePLE.ump"
public class Survey
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Survey Attributes
  private String surveyorName;
  private Date reportDate;

  //Survey Associations
  private Tree tree;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Survey(String aSurveyorName, Date aReportDate, Tree aTree)
  {
    surveyorName = aSurveyorName;
    reportDate = aReportDate;
    boolean didAddTree = setTree(aTree);
    if (!didAddTree)
    {
      throw new RuntimeException("Unable to create survey due to tree");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setSurveyorName(String aSurveyorName)
  {
    boolean wasSet = false;
    surveyorName = aSurveyorName;
    wasSet = true;
    return wasSet;
  }

  public boolean setReportDate(Date aReportDate)
  {
    boolean wasSet = false;
    reportDate = aReportDate;
    wasSet = true;
    return wasSet;
  }

  public String getSurveyorName()
  {
    return surveyorName;
  }

  public Date getReportDate()
  {
    return reportDate;
  }

  public Tree getTree()
  {
    return tree;
  }

  public boolean setTree(Tree aTree)
  {
    boolean wasSet = false;
    if (aTree == null)
    {
      return wasSet;
    }

    Tree existingTree = tree;
    tree = aTree;
    if (existingTree != null && !existingTree.equals(aTree))
    {
      existingTree.removeSurvey(this);
    }
    tree.addSurvey(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Tree placeholderTree = tree;
    this.tree = null;
    placeholderTree.removeSurvey(this);
  }


  public String toString()
  {
    return super.toString() + "["+
            "surveyorName" + ":" + getSurveyorName()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "reportDate" + "=" + (getReportDate() != null ? !getReportDate().equals(this)  ? getReportDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "tree = "+(getTree()!=null?Integer.toHexString(System.identityHashCode(getTree())):"null");
  }
}