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

  public enum Status { Planted, Deseased, CutDown, ToBeCutDown }
  public enum LandUse { Residential, NonResidential }

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static int nextId = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Tree Attributes
  private String species;
  private double height;
  private Status status;
  private double diameter;
  private LandUse land;

  //Autounique Attributes
  private int id;

  //Tree Associations
  private List<Survey> surveys;
  private Location coordinates;
  private User owner;
  private Municipality treeMunicipality;
  private List<Version> versions;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Tree(String aSpecies, double aHeight, double aDiameter, Location aCoordinates, User aOwner, Municipality aTreeMunicipality, Version... allVersions)
  {
    species = aSpecies;
    height = aHeight;
    status = Status.Planted;
    diameter = aDiameter;
    id = nextId++;
    surveys = new ArrayList<Survey>();
    boolean didAddCoordinates = setCoordinates(aCoordinates);
    if (!didAddCoordinates)
    {
      throw new RuntimeException("Unable to create treeInLocation due to coordinates");
    }
    boolean didAddOwner = setOwner(aOwner);
    if (!didAddOwner)
    {
      throw new RuntimeException("Unable to create tree due to owner");
    }
    boolean didAddTreeMunicipality = setTreeMunicipality(aTreeMunicipality);
    if (!didAddTreeMunicipality)
    {
      throw new RuntimeException("Unable to create listOfTree due to treeMunicipality");
    }
    versions = new ArrayList<Version>();
    boolean didAddVersions = setVersions(allVersions);
    if (!didAddVersions)
    {
      throw new RuntimeException("Unable to create Tree, must have at least 1 versions");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

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

  public User getOwner()
  {
    return owner;
  }

  public Municipality getTreeMunicipality()
  {
    return treeMunicipality;
  }

  public Version getVersion(int index)
  {
    Version aVersion = versions.get(index);
    return aVersion;
  }

  public List<Version> getVersions()
  {
    List<Version> newVersions = Collections.unmodifiableList(versions);
    return newVersions;
  }

  public int numberOfVersions()
  {
    int number = versions.size();
    return number;
  }

  public boolean hasVersions()
  {
    boolean has = versions.size() > 0;
    return has;
  }

  public int indexOfVersion(Version aVersion)
  {
    int index = versions.indexOf(aVersion);
    return index;
  }

  public static int minimumNumberOfSurveys()
  {
    return 0;
  }

  public Survey addSurvey(Date aLastReport, User aSurveyor)
  {
    return new Survey(aLastReport, this, aSurveyor);
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

  public boolean setOwner(User aOwner)
  {
    boolean wasSet = false;
    if (aOwner == null)
    {
      return wasSet;
    }

    User existingOwner = owner;
    owner = aOwner;
    if (existingOwner != null && !existingOwner.equals(aOwner))
    {
      existingOwner.removeTree(this);
    }
    owner.addTree(this);
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

  public boolean isNumberOfVersionsValid()
  {
    boolean isValid = numberOfVersions() >= minimumNumberOfVersions();
    return isValid;
  }

  public static int minimumNumberOfVersions()
  {
    return 1;
  }

  public boolean addVersion(Version aVersion)
  {
    boolean wasAdded = false;
    if (versions.contains(aVersion)) { return false; }
    versions.add(aVersion);
    if (aVersion.indexOfTree(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aVersion.addTree(this);
      if (!wasAdded)
      {
        versions.remove(aVersion);
      }
    }
    return wasAdded;
  }

  public boolean removeVersion(Version aVersion)
  {
    boolean wasRemoved = false;
    if (!versions.contains(aVersion))
    {
      return wasRemoved;
    }

    if (numberOfVersions() <= minimumNumberOfVersions())
    {
      return wasRemoved;
    }

    int oldIndex = versions.indexOf(aVersion);
    versions.remove(oldIndex);
    if (aVersion.indexOfTree(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aVersion.removeTree(this);
      if (!wasRemoved)
      {
        versions.add(oldIndex,aVersion);
      }
    }
    return wasRemoved;
  }

  public boolean setVersions(Version... newVersions)
  {
    boolean wasSet = false;
    ArrayList<Version> verifiedVersions = new ArrayList<Version>();
    for (Version aVersion : newVersions)
    {
      if (verifiedVersions.contains(aVersion))
      {
        continue;
      }
      verifiedVersions.add(aVersion);
    }

    if (verifiedVersions.size() != newVersions.length || verifiedVersions.size() < minimumNumberOfVersions())
    {
      return wasSet;
    }

    ArrayList<Version> oldVersions = new ArrayList<Version>(versions);
    versions.clear();
    for (Version aNewVersion : verifiedVersions)
    {
      versions.add(aNewVersion);
      if (oldVersions.contains(aNewVersion))
      {
        oldVersions.remove(aNewVersion);
      }
      else
      {
        aNewVersion.addTree(this);
      }
    }

    for (Version anOldVersion : oldVersions)
    {
      anOldVersion.removeTree(this);
    }
    wasSet = true;
    return wasSet;
  }

  public boolean addVersionAt(Version aVersion, int index)
  {  
    boolean wasAdded = false;
    if(addVersion(aVersion))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfVersions()) { index = numberOfVersions() - 1; }
      versions.remove(aVersion);
      versions.add(index, aVersion);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveVersionAt(Version aVersion, int index)
  {
    boolean wasAdded = false;
    if(versions.contains(aVersion))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfVersions()) { index = numberOfVersions() - 1; }
      versions.remove(aVersion);
      versions.add(index, aVersion);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addVersionAt(aVersion, index);
    }
    return wasAdded;
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
    User placeholderOwner = owner;
    this.owner = null;
    placeholderOwner.removeTree(this);
    Municipality placeholderTreeMunicipality = treeMunicipality;
    this.treeMunicipality = null;
    placeholderTreeMunicipality.removeListOfTree(this);
    ArrayList<Version> copyOfVersions = new ArrayList<Version>(versions);
    versions.clear();
    for(Version aVersion : copyOfVersions)
    {
      aVersion.removeTree(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "species" + ":" + getSpecies()+ "," +
            "height" + ":" + getHeight()+ "," +
            "diameter" + ":" + getDiameter()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "status" + "=" + (getStatus() != null ? !getStatus().equals(this)  ? getStatus().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "land" + "=" + (getLand() != null ? !getLand().equals(this)  ? getLand().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "coordinates = "+(getCoordinates()!=null?Integer.toHexString(System.identityHashCode(getCoordinates())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "owner = "+(getOwner()!=null?Integer.toHexString(System.identityHashCode(getOwner())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "treeMunicipality = "+(getTreeMunicipality()!=null?Integer.toHexString(System.identityHashCode(getTreeMunicipality())):"null");
  }
}