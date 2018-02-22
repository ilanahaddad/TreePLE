/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.27.0.3728.d139ed893 modeling language!*/

package ca.mcgill.ecse321.TreePLE.model;

// line 11 "../../../../../TreePLE.ump"
public class Location
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Location Attributes
  private double latitude;
  private double longitude;

  //Location Associations
  private TreeManager treeManager;
  private Tree treeInLocation;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Location(double aLatitude, double aLongitude, TreeManager aTreeManager)
  {
    latitude = aLatitude;
    longitude = aLongitude;
    boolean didAddTreeManager = setTreeManager(aTreeManager);
    if (!didAddTreeManager)
    {
      throw new RuntimeException("Unable to create location due to treeManager");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setLatitude(double aLatitude)
  {
    boolean wasSet = false;
    latitude = aLatitude;
    wasSet = true;
    return wasSet;
  }

  public boolean setLongitude(double aLongitude)
  {
    boolean wasSet = false;
    longitude = aLongitude;
    wasSet = true;
    return wasSet;
  }

  public double getLatitude()
  {
    return latitude;
  }

  public double getLongitude()
  {
    return longitude;
  }

  public TreeManager getTreeManager()
  {
    return treeManager;
  }

  public Tree getTreeInLocation()
  {
    return treeInLocation;
  }

  public boolean hasTreeInLocation()
  {
    boolean has = treeInLocation != null;
    return has;
  }

  public boolean setTreeManager(TreeManager aTreeManager)
  {
    boolean wasSet = false;
    if (aTreeManager == null)
    {
      return wasSet;
    }

    TreeManager existingTreeManager = treeManager;
    treeManager = aTreeManager;
    if (existingTreeManager != null && !existingTreeManager.equals(aTreeManager))
    {
      existingTreeManager.removeLocation(this);
    }
    treeManager.addLocation(this);
    wasSet = true;
    return wasSet;
  }

  public boolean setTreeInLocation(Tree aNewTreeInLocation)
  {
    boolean wasSet = false;
    if (treeInLocation != null && !treeInLocation.equals(aNewTreeInLocation) && equals(treeInLocation.getCoordinates()))
    {
      //Unable to setTreeInLocation, as existing treeInLocation would become an orphan
      return wasSet;
    }

    treeInLocation = aNewTreeInLocation;
    Location anOldCoordinates = aNewTreeInLocation != null ? aNewTreeInLocation.getCoordinates() : null;

    if (!this.equals(anOldCoordinates))
    {
      if (anOldCoordinates != null)
      {
        anOldCoordinates.treeInLocation = null;
      }
      if (treeInLocation != null)
      {
        treeInLocation.setCoordinates(this);
      }
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    TreeManager placeholderTreeManager = treeManager;
    this.treeManager = null;
    if(placeholderTreeManager != null)
    {
      placeholderTreeManager.removeLocation(this);
    }
    Tree existingTreeInLocation = treeInLocation;
    treeInLocation = null;
    if (existingTreeInLocation != null)
    {
      existingTreeInLocation.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "latitude" + ":" + getLatitude()+ "," +
            "longitude" + ":" + getLongitude()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "treeManager = "+(getTreeManager()!=null?Integer.toHexString(System.identityHashCode(getTreeManager())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "treeInLocation = "+(getTreeInLocation()!=null?Integer.toHexString(System.identityHashCode(getTreeInLocation())):"null");
  }
}