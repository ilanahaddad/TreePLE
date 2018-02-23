/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.26.1-f40f105-3613 modeling language!*/

package ca.mcgill.ecse321.TreePLE.model;
import java.util.*;
import java.sql.Date;

// line 51 "../../../../../TreePLE.ump"
public class TreeManager
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TreeManager Associations
  private List<Tree> trees;
  private List<Location> locations;
  private List<Survey> surveys;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TreeManager()
  {
    trees = new ArrayList<Tree>();
    locations = new ArrayList<Location>();
    surveys = new ArrayList<Survey>();
  }

  //------------------------
  // INTERFACE
  //------------------------

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

  public Location getLocation(int index)
  {
    Location aLocation = locations.get(index);
    return aLocation;
  }

  public List<Location> getLocations()
  {
    List<Location> newLocations = Collections.unmodifiableList(locations);
    return newLocations;
  }

  public int numberOfLocations()
  {
    int number = locations.size();
    return number;
  }

  public boolean hasLocations()
  {
    boolean has = locations.size() > 0;
    return has;
  }

  public int indexOfLocation(Location aLocation)
  {
    int index = locations.indexOf(aLocation);
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

  public boolean addTree(Tree aTree)
  {
    boolean wasAdded = false;
    if (trees.contains(aTree)) { return false; }
    trees.add(aTree);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeTree(Tree aTree)
  {
    boolean wasRemoved = false;
    if (trees.contains(aTree))
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

  public static int minimumNumberOfLocations()
  {
    return 0;
  }

  public boolean addLocation(Location aLocation)
  {
    boolean wasAdded = false;
    if (locations.contains(aLocation)) { return false; }
    locations.add(aLocation);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeLocation(Location aLocation)
  {
    boolean wasRemoved = false;
    if (locations.contains(aLocation))
    {
      locations.remove(aLocation);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addLocationAt(Location aLocation, int index)
  {  
    boolean wasAdded = false;
    if(addLocation(aLocation))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfLocations()) { index = numberOfLocations() - 1; }
      locations.remove(aLocation);
      locations.add(index, aLocation);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveLocationAt(Location aLocation, int index)
  {
    boolean wasAdded = false;
    if(locations.contains(aLocation))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfLocations()) { index = numberOfLocations() - 1; }
      locations.remove(aLocation);
      locations.add(index, aLocation);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addLocationAt(aLocation, index);
    }
    return wasAdded;
  }

  public static int minimumNumberOfSurveys()
  {
    return 0;
  }

  public boolean addSurvey(Survey aSurvey)
  {
    boolean wasAdded = false;
    if (surveys.contains(aSurvey)) { return false; }
    surveys.add(aSurvey);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeSurvey(Survey aSurvey)
  {
    boolean wasRemoved = false;
    if (surveys.contains(aSurvey))
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
    trees.clear();
    locations.clear();
    surveys.clear();
  }

}