/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.26.1-f40f105-3613 modeling language!*/

package ca.mcgill.ecse321.TreePLE.model;
import java.sql.Date;

// line 36 "../../../../../TreePLE.ump"
public class Survey
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Survey Attributes
  private Date lastReport;

  //Survey Associations
  private Tree tree;
  private User surveyor;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Survey(Date aLastReport, Tree aTree, User aSurveyor)
  {
    lastReport = aLastReport;
    boolean didAddTree = setTree(aTree);
    if (!didAddTree)
    {
      throw new RuntimeException("Unable to create survey due to tree");
    }
    boolean didAddSurveyor = setSurveyor(aSurveyor);
    if (!didAddSurveyor)
    {
      throw new RuntimeException("Unable to create survey due to surveyor");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setLastReport(Date aLastReport)
  {
    boolean wasSet = false;
    lastReport = aLastReport;
    wasSet = true;
    return wasSet;
  }

  public Date getLastReport()
  {
    return lastReport;
  }

  public Tree getTree()
  {
    return tree;
  }

  public User getSurveyor()
  {
    return surveyor;
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

  public boolean setSurveyor(User aSurveyor)
  {
    boolean wasSet = false;
    if (aSurveyor == null)
    {
      return wasSet;
    }

    User existingSurveyor = surveyor;
    surveyor = aSurveyor;
    if (existingSurveyor != null && !existingSurveyor.equals(aSurveyor))
    {
      existingSurveyor.removeSurvey(this);
    }
    surveyor.addSurvey(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Tree placeholderTree = tree;
    this.tree = null;
    placeholderTree.removeSurvey(this);
    User placeholderSurveyor = surveyor;
    this.surveyor = null;
    placeholderSurveyor.removeSurvey(this);
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "lastReport" + "=" + (getLastReport() != null ? !getLastReport().equals(this)  ? getLastReport().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "tree = "+(getTree()!=null?Integer.toHexString(System.identityHashCode(getTree())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "surveyor = "+(getSurveyor()!=null?Integer.toHexString(System.identityHashCode(getSurveyor())):"null");
  }
}