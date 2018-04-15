/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.27.0.3728.d139ed893 modeling language!*/

package ca.mcgill.ecse321.TreePLE.model;
import java.util.*;

// line 42 "../../../../../TreePLE.ump"
public class Forecast
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Forecast Attributes
  private String name;
  private String versionCreated;
  private int year;

  //Forecast Associations
  private List<VersionManager> versions;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Forecast(String aName, String aVersionCreated, int aYear)
  {
    name = aName;
    versionCreated = aVersionCreated;
    year = aYear;
    versions = new ArrayList<VersionManager>();
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

  public boolean setVersionCreated(String aVersionCreated)
  {
    boolean wasSet = false;
    versionCreated = aVersionCreated;
    wasSet = true;
    return wasSet;
  }

  public boolean setYear(int aYear)
  {
    boolean wasSet = false;
    year = aYear;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public String getVersionCreated()
  {
    return versionCreated;
  }

  public int getYear()
  {
    return year;
  }

  public VersionManager getVersion(int index)
  {
    VersionManager aVersion = versions.get(index);
    return aVersion;
  }

  public List<VersionManager> getVersions()
  {
    List<VersionManager> newVersions = Collections.unmodifiableList(versions);
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

  public int indexOfVersion(VersionManager aVersion)
  {
    int index = versions.indexOf(aVersion);
    return index;
  }

  public static int minimumNumberOfVersions()
  {
    return 0;
  }

  public boolean addVersion(VersionManager aVersion)
  {
    boolean wasAdded = false;
    if (versions.contains(aVersion)) { return false; }
    versions.add(aVersion);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeVersion(VersionManager aVersion)
  {
    boolean wasRemoved = false;
    if (versions.contains(aVersion))
    {
      versions.remove(aVersion);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addVersionAt(VersionManager aVersion, int index)
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

  public boolean addOrMoveVersionAt(VersionManager aVersion, int index)
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
    versions.clear();
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "versionCreated" + ":" + getVersionCreated()+ "," +
            "year" + ":" + getYear()+ "]";
  }
}