/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.26.1-f40f105-3613 modeling language!*/

package ca.mcgill.ecse321.TreePLE.model;
import java.util.*;
import java.sql.Date;

// line 2 "../../../../../TreePLE.ump"
public class Tree
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum Status { Planted, Diseased, CutDown, ToBeCutDown }
  public enum LandUse { Residential, NonResidential }

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static int nextId = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Tree Attributes
  private String ownerName;
  private String species;
  private double height;
  private Status status;
  private double diameter;
  private LandUse land;
  private int age;

  //Autounique Attributes
  private int id;

  //Tree Associations
  private List<Survey> surveys;
  private Location coordinates;
  private Municipality treeMunicipality;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Tree(String aOwnerName, String aSpecies, double aHeight, double aDiameter, int aAge, Location aCoordinates, Municipality aTreeMunicipality)
  {
    ownerName = aOwnerName;
    species = aSpecies;
    height = aHeight;
    status = Status.Planted;
    diameter = aDiameter;
    age = aAge;
    id = nextId++;
    surveys = new ArrayList<Survey>();
    boolean didAddCoordinates = setCoordinates(aCoordinates);
    if (!didAddCoordinates)
    {
      throw new RuntimeException("Unable to create treeInLocation due to coordinates");
    }
    boolean didAddTreeMunicipality = setTreeMunicipality(aTreeMunicipality);
    if (!didAddTreeMunicipality)
    {
      throw new RuntimeException("Unable to create listOfTree due to treeMunicipality");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setOwnerName(String aOwnerName)
  {
    boolean wasSet = false;
    ownerName = aOwnerName;
    wasSet = true;
    return wasSet;
  }

  public boolean setSpecies(String aSpecies)
  {
    boolean wasSet = false;
    species = aSpecies;
    wasSet = true;
    return wasSet;
  }

  public boolean setHeight(double aHeight)
  {
    boolean wasSet = false;
    height = aHeight;
    wasSet = true;
    return wasSet;
  }

  public boolean setStatus(Status aStatus)
  {
    boolean wasSet = false;
    status = aStatus;
    wasSet = true;
    return wasSet;
  }

  public boolean setDiameter(double aDiameter)
  {
    boolean wasSet = false;
    diameter = aDiameter;
    wasSet = true;
    return wasSet;
  }

  public boolean setLand(LandUse aLand)
  {
    boolean wasSet = false;
    land = aLand;
    wasSet = true;
    return wasSet;
  }

  public boolean setAge(int aAge)
  {
    boolean wasSet = false;
    age = aAge;
    wasSet = true;
    return wasSet;
  }

  public String getOwnerName()
  {
    return ownerName;
  }

  public String getSpecies()
  {
    return species;
  }

  public double getHeight()
  {
    return height;
  }

  public Status getStatus()
  {
    return status;
  }

  public double getDiameter()
  {
    return diameter;
  }

  public LandUse getLand()
  {
    return land;
  }

  public int getAge()
  {
    return age;
  }

  public int getId()
  {
    return id;
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

  public Location getCoordinates()
  {
    return coordinates;
  }

  public Municipality getTreeMunicipality()
  {
    return treeMunicipality;
  }

  public static int minimumNumberOfSurveys()
  {
    return 0;
  }

  public Survey addSurvey(String aSurveyorName, Date aReportDate)
  {
    return new Survey(aSurveyorName, aReportDate, this);
  }

  public boolean addSurvey(Survey aSurvey)
  {
    boolean wasAdded = false;
    if (surveys.contains(aSurvey)) { return false; }
    Tree existingTree = aSurvey.getTree();
    boolean isNewTree = existingTree != null && !this.equals(existingTree);
    if (isNewTree)
    {
      aSurvey.setTree(this);
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
    //Unable to remove aSurvey, as it must always have a tree
    if (!this.equals(aSurvey.getTree()))
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

  public boolean setCoordinates(Location aNewCoordinates)
  {
    boolean wasSet = false;
    if (aNewCoordinates == null)
    {
      //Unable to setCoordinates to null, as treeInLocation must always be associated to a coordinates
      return wasSet;
    }
    
    Tree existingTreeInLocation = aNewCoordinates.getTreeInLocation();
    if (existingTreeInLocation != null && !equals(existingTreeInLocation))
    {
      //Unable to setCoordinates, the current coordinates already has a treeInLocation, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    Location anOldCoordinates = coordinates;
    coordinates = aNewCoordinates;
    coordinates.setTreeInLocation(this);

    if (anOldCoordinates != null)
    {
      anOldCoordinates.setTreeInLocation(null);
    }
    wasSet = true;
    return wasSet;
  }

  public boolean setTreeMunicipality(Municipality aTreeMunicipality)
  {
    boolean wasSet = false;
    if (aTreeMunicipality == null)
    {
      return wasSet;
    }

    Municipality existingTreeMunicipality = treeMunicipality;
    treeMunicipality = aTreeMunicipality;
    if (existingTreeMunicipality != null && !existingTreeMunicipality.equals(aTreeMunicipality))
    {
      existingTreeMunicipality.removeListOfTree(this);
    }
    treeMunicipality.addListOfTree(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    for(int i=surveys.size(); i > 0; i--)
    {
      Survey aSurvey = surveys.get(i - 1);
      aSurvey.delete();
    }
    Location existingCoordinates = coordinates;
    coordinates = null;
    if (existingCoordinates != null)
    {
      existingCoordinates.setTreeInLocation(null);
    }
    Municipality placeholderTreeMunicipality = treeMunicipality;
    this.treeMunicipality = null;
    placeholderTreeMunicipality.removeListOfTree(this);
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "ownerName" + ":" + getOwnerName()+ "," +
            "species" + ":" + getSpecies()+ "," +
            "height" + ":" + getHeight()+ "," +
            "diameter" + ":" + getDiameter()+ "," +
            "age" + ":" + getAge()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "status" + "=" + (getStatus() != null ? !getStatus().equals(this)  ? getStatus().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "land" + "=" + (getLand() != null ? !getLand().equals(this)  ? getLand().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "coordinates = "+(getCoordinates()!=null?Integer.toHexString(System.identityHashCode(getCoordinates())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "treeMunicipality = "+(getTreeMunicipality()!=null?Integer.toHexString(System.identityHashCode(getTreeMunicipality())):"null");
  }
}