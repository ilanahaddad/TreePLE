/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.26.1-f40f105-3613 modeling language!*/

package TreePLe.model;
import java.sql.Date;

// line 34 "../../TreePLE.ump"
public class Survey
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Survey Attributes
  private Date lastReport;

  //Survey Associations
  private Tree tree;
  private User surveyer;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Survey(Date aLastReport, Tree aTree, User aSurveyer)
  {
    lastReport = aLastReport;
    boolean didAddTree = setTree(aTree);
    if (!didAddTree)
    {
      throw new RuntimeException("Unable to create survey due to tree");
    }
    boolean didAddSurveyer = setSurveyer(aSurveyer);
    if (!didAddSurveyer)
    {
      throw new RuntimeException("Unable to create survey due to surveyer");
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

  public User getSurveyer()
  {
    return surveyer;
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

  public boolean setSurveyer(User aSurveyer)
  {
    boolean wasSet = false;
    if (aSurveyer == null)
    {
      return wasSet;
    }

    User existingSurveyer = surveyer;
    surveyer = aSurveyer;
    if (existingSurveyer != null && !existingSurveyer.equals(aSurveyer))
    {
      existingSurveyer.removeSurvey(this);
    }
    surveyer.addSurvey(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Tree placeholderTree = tree;
    this.tree = null;
    placeholderTree.removeSurvey(this);
    User placeholderSurveyer = surveyer;
    this.surveyer = null;
    placeholderSurveyer.removeSurvey(this);
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "lastReport" + "=" + (getLastReport() != null ? !getLastReport().equals(this)  ? getLastReport().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "tree = "+(getTree()!=null?Integer.toHexString(System.identityHashCode(getTree())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "surveyer = "+(getSurveyer()!=null?Integer.toHexString(System.identityHashCode(getSurveyer())):"null");
  }
}