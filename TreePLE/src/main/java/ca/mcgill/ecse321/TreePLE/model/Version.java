/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.26.1-f40f105-3613 modeling language!*/

package ca.mcgill.ecse321.TreePLE.model;
import java.util.*;
import java.sql.Date;

// line 46 "../../../../../TreePLE.ump"
public class Version
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Version Attributes
  private String idNumber;
  private int date;

  //Version Associations
  private List<Tree> trees;
  private List<SustainabilityReport> sustainabilityReports;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Version(String aIdNumber, int aDate)
  {
    idNumber = aIdNumber;
    date = aDate;
    trees = new ArrayList<Tree>();
    sustainabilityReports = new ArrayList<SustainabilityReport>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setIdNumber(String aIdNumber)
  {
    boolean wasSet = false;
    idNumber = aIdNumber;
    wasSet = true;
    return wasSet;
  }

  public boolean setDate(int aDate)
  {
    boolean wasSet = false;
    date = aDate;
    wasSet = true;
    return wasSet;
  }

  public String getIdNumber()
  {
    return idNumber;
  }

  public int getDate()
  {
    return date;
  }

  public Tree getTree(int index)
  {
    Tree aTree = trees.get(index);
    return aTree;
  }

  public List<Tree> getTrees()
  {
    List<Tree> newTrees = Collections.unmodifiableList(trees);
    return newTrees;
  }

  public int numberOfTrees()
  {
    int number = trees.size();
    return number;
  }

  public boolean hasTrees()
  {
    boolean has = trees.size() > 0;
    return has;
  }

  public int indexOfTree(Tree aTree)
  {
    int index = trees.indexOf(aTree);
    return index;
  }

  public SustainabilityReport getSustainabilityReport(int index)
  {
    SustainabilityReport aSustainabilityReport = sustainabilityReports.get(index);
    return aSustainabilityReport;
  }

  public List<SustainabilityReport> getSustainabilityReports()
  {
    List<SustainabilityReport> newSustainabilityReports = Collections.unmodifiableList(sustainabilityReports);
    return newSustainabilityReports;
  }

  public int numberOfSustainabilityReports()
  {
    int number = sustainabilityReports.size();
    return number;
  }

  public boolean hasSustainabilityReports()
  {
    boolean has = sustainabilityReports.size() > 0;
    return has;
  }

  public int indexOfSustainabilityReport(SustainabilityReport aSustainabilityReport)
  {
    int index = sustainabilityReports.indexOf(aSustainabilityReport);
    return index;
  }

  public static int minimumNumberOfTrees()
  {
    return 0;
  }

  public boolean addTree(Tree aTree)
  {
    boolean wasAdded = false;
    if (trees.contains(aTree)) { return false; }
    trees.add(aTree);
    if (aTree.indexOfVersion(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aTree.addVersion(this);
      if (!wasAdded)
      {
        trees.remove(aTree);
      }
    }
    return wasAdded;
  }

  public boolean removeTree(Tree aTree)
  {
    boolean wasRemoved = false;
    if (!trees.contains(aTree))
    {
      return wasRemoved;
    }

    int oldIndex = trees.indexOf(aTree);
    trees.remove(oldIndex);
    if (aTree.indexOfVersion(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aTree.removeVersion(this);
      if (!wasRemoved)
      {
        trees.add(oldIndex,aTree);
      }
    }
    return wasRemoved;
  }

  public boolean addTreeAt(Tree aTree, int index)
  {  
    boolean wasAdded = false;
    if(addTree(aTree))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTrees()) { index = numberOfTrees() - 1; }
      trees.remove(aTree);
      trees.add(index, aTree);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveTreeAt(Tree aTree, int index)
  {
    boolean wasAdded = false;
    if(trees.contains(aTree))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTrees()) { index = numberOfTrees() - 1; }
      trees.remove(aTree);
      trees.add(index, aTree);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addTreeAt(aTree, index);
    }
    return wasAdded;
  }

  public static int minimumNumberOfSustainabilityReports()
  {
    return 0;
  }

  public SustainabilityReport addSustainabilityReport(Date aDate, Location... allReportPerimeter)
  {
    return new SustainabilityReport(aDate, this, allReportPerimeter);
  }

  public boolean addSustainabilityReport(SustainabilityReport aSustainabilityReport)
  {
    boolean wasAdded = false;
    if (sustainabilityReports.contains(aSustainabilityReport)) { return false; }
    Version existingReportVersion = aSustainabilityReport.getReportVersion();
    boolean isNewReportVersion = existingReportVersion != null && !this.equals(existingReportVersion);
    if (isNewReportVersion)
    {
      aSustainabilityReport.setReportVersion(this);
    }
    else
    {
      sustainabilityReports.add(aSustainabilityReport);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeSustainabilityReport(SustainabilityReport aSustainabilityReport)
  {
    boolean wasRemoved = false;
    //Unable to remove aSustainabilityReport, as it must always have a reportVersion
    if (!this.equals(aSustainabilityReport.getReportVersion()))
    {
      sustainabilityReports.remove(aSustainabilityReport);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addSustainabilityReportAt(SustainabilityReport aSustainabilityReport, int index)
  {  
    boolean wasAdded = false;
    if(addSustainabilityReport(aSustainabilityReport))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfSustainabilityReports()) { index = numberOfSustainabilityReports() - 1; }
      sustainabilityReports.remove(aSustainabilityReport);
      sustainabilityReports.add(index, aSustainabilityReport);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveSustainabilityReportAt(SustainabilityReport aSustainabilityReport, int index)
  {
    boolean wasAdded = false;
    if(sustainabilityReports.contains(aSustainabilityReport))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfSustainabilityReports()) { index = numberOfSustainabilityReports() - 1; }
      sustainabilityReports.remove(aSustainabilityReport);
      sustainabilityReports.add(index, aSustainabilityReport);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addSustainabilityReportAt(aSustainabilityReport, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    ArrayList<Tree> copyOfTrees = new ArrayList<Tree>(trees);
    trees.clear();
    for(Tree aTree : copyOfTrees)
    {
      if (aTree.numberOfVersions() <= Tree.minimumNumberOfVersions())
      {
        aTree.delete();
      }
      else
      {
        aTree.removeVersion(this);
      }
    }
    for(int i=sustainabilityReports.size(); i > 0; i--)
    {
      SustainabilityReport aSustainabilityReport = sustainabilityReports.get(i - 1);
      aSustainabilityReport.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "idNumber" + ":" + getIdNumber()+ "," +
            "date" + ":" + getDate()+ "]";
  }
}