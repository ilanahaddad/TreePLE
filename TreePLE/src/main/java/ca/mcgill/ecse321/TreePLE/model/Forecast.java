/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.27.0.3728.d139ed893 modeling language!*/

package ca.mcgill.ecse321.TreePLE.model;
import java.util.*;

// line 41 "../../../../../TreePLE.ump"
public class Forecast
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Forecast Associations
  private List<VersionManager> versions;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Forecast()
  {
    versions = new ArrayList<VersionManager>();
  }

  //------------------------
  // INTERFACE
  //------------------------

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

}