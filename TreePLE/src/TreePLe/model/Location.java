/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.26.1-f40f105-3613 modeling language!*/

package TreePLe.model;

// line 11 "../../TreePLE.ump"
public class Location
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Location Attributes
  private double latitude;
  private double longitude;

  //Location Associations
  private Tree treeInLocation;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Location(double aLatitude, double aLongitude)
  {
    latitude = aLatitude;
    longitude = aLongitude;
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

  public Tree getTreeInLocation()
  {
    return treeInLocation;
  }

  public boolean hasTreeInLocation()
  {
    boolean has = treeInLocation != null;
    return has;
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
            "  " + "treeInLocation = "+(getTreeInLocation()!=null?Integer.toHexString(System.identityHashCode(getTreeInLocation())):"null");
  }
}