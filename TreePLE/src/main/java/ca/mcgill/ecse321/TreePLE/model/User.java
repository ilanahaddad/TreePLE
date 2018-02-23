/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.26.1-f40f105-3613 modeling language!*/

package ca.mcgill.ecse321.TreePLE.model;
import java.util.*;
import java.sql.Date;

// line 19 "../../../../../TreePLE.ump"
public class User
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static int nextId = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //User Attributes
  private String name;

  //Autounique Attributes
  private int id;

  //User Associations
  private List<Tree> trees;
  private List<Survey> surveys;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public User(String aName)
  {
    name = aName;
    id = nextId++;
    trees = new ArrayList<Tree>();
    surveys = new ArrayList<Survey>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public int getId()
  {
    return id;
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

  public Survey getSurvey(int index)
  {
    Survey aSurvey = surveys.get(index);
    return aSurvey;
  }

  public List<Survey> getSurveys()
  {
    List<Survey> newSurveys = Collections.unmodifiableList(surveys);
    return newSurveys;
  }

  public int numberOfSurveys()
  {
    int number = surveys.size();
    return number;
  }

  public boolean hasSurveys()
  {
    boolean has = surveys.size() > 0;
    return has;
  }

  public int indexOfSurvey(Survey aSurvey)
  {
    int index = surveys.indexOf(aSurvey);
    return index;
  }

  public static int minimumNumberOfTrees()
  {
    return 0;
  }

  public Tree addTree(String aSpecies, double aHeight, double aDiameter, Location aCoordinates, Municipality aTreeMunicipality, Version... allVersions)
  {
    return new Tree(aSpecies, aHeight, aDiameter, aCoordinates, this, aTreeMunicipality, allVersions);
  }

  public boolean addTree(Tree aTree)
  {
    boolean wasAdded = false;
    if (trees.contains(aTree)) { return false; }
    User existingOwner = aTree.getOwner();
    boolean isNewOwner = existingOwner != null && !this.equals(existingOwner);
    if (isNewOwner)
    {
      aTree.setOwner(this);
    }
    else
    {
      trees.add(aTree);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeTree(Tree aTree)
  {
    boolean wasRemoved = false;
    //Unable to remove aTree, as it must always have a owner
    if (!this.equals(aTree.getOwner()))
    {
      trees.remove(aTree);
      wasRemoved = true;
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

  public static int minimumNumberOfSurveys()
  {
    return 0;
  }

  public Survey addSurvey(Date aReportDate, Tree aTree)
  {
    return new Survey(aReportDate, aTree, this);
  }

  public boolean addSurvey(Survey aSurvey)
  {
    boolean wasAdded = false;
    if (surveys.contains(aSurvey)) { return false; }
    User existingSurveyor = aSurvey.getSurveyor();
    boolean isNewSurveyor = existingSurveyor != null && !this.equals(existingSurveyor);
    if (isNewSurveyor)
    {
      aSurvey.setSurveyor(this);
    }
    else
    {
      surveys.add(aSurvey);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeSurvey(Survey aSurvey)
  {
    boolean wasRemoved = false;
    //Unable to remove aSurvey, as it must always have a surveyor
    if (!this.equals(aSurvey.getSurveyor()))
    {
      surveys.remove(aSurvey);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addSurveyAt(Survey aSurvey, int index)
  {  
    boolean wasAdded = false;
    if(addSurvey(aSurvey))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfSurveys()) { index = numberOfSurveys() - 1; }
      surveys.remove(aSurvey);
      surveys.add(index, aSurvey);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveSurveyAt(Survey aSurvey, int index)
  {
    boolean wasAdded = false;
    if(surveys.contains(aSurvey))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfSurveys()) { index = numberOfSurveys() - 1; }
      surveys.remove(aSurvey);
      surveys.add(index, aSurvey);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addSurveyAt(aSurvey, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    for(int i=trees.size(); i > 0; i--)
    {
      Tree aTree = trees.get(i - 1);
      aTree.delete();
    }
    for(int i=surveys.size(); i > 0; i--)
    {
      Survey aSurvey = surveys.get(i - 1);
      aSurvey.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "name" + ":" + getName()+ "]";
  }
}