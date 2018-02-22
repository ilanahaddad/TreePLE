/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.26.1-f40f105-3613 modeling language!*/

package ca.mcgill.ecse321.TreePLE.model;
import java.util.*;

// line 22 "../../../../../TreePLE.ump"
public class LocalResident extends User
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //LocalResident Associations
  private List<Location> propertyPerimeter;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public LocalResident(String aName, int aId, Location... allPropertyPerimeter)
  {
    super(aName, aId);
    propertyPerimeter = new ArrayList<Location>();
    boolean didAddPropertyPerimeter = setPropertyPerimeter(allPropertyPerimeter);
    if (!didAddPropertyPerimeter)
    {
      throw new RuntimeException("Unable to create LocalResident, must have 4 propertyPerimeter");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public Location getPropertyPerimeter(int index)
  {
    Location aPropertyPerimeter = propertyPerimeter.get(index);
    return aPropertyPerimeter;
  }

  public List<Location> getPropertyPerimeter()
  {
    List<Location> newPropertyPerimeter = Collections.unmodifiableList(propertyPerimeter);
    return newPropertyPerimeter;
  }

  public int numberOfPropertyPerimeter()
  {
    int number = propertyPerimeter.size();
    return number;
  }

  public boolean hasPropertyPerimeter()
  {
    boolean has = propertyPerimeter.size() > 0;
    return has;
  }

  public int indexOfPropertyPerimeter(Location aPropertyPerimeter)
  {
    int index = propertyPerimeter.indexOf(aPropertyPerimeter);
    return index;
  }

  public static int requiredNumberOfPropertyPerimeter()
  {
    return 4;
  }

  public static int minimumNumberOfPropertyPerimeter()
  {
    return 4;
  }

  public static int maximumNumberOfPropertyPerimeter()
  {
    return 4;
  }

  public boolean setPropertyPerimeter(Location... newPropertyPerimeter)
  {
    boolean wasSet = false;
    ArrayList<Location> verifiedPropertyPerimeter = new ArrayList<Location>();
    for (Location aPropertyPerimeter : newPropertyPerimeter)
    {
      if (verifiedPropertyPerimeter.contains(aPropertyPerimeter))
      {
        continue;
      }
      verifiedPropertyPerimeter.add(aPropertyPerimeter);
    }

    if (verifiedPropertyPerimeter.size() != newPropertyPerimeter.length || verifiedPropertyPerimeter.size() != requiredNumberOfPropertyPerimeter())
    {
      return wasSet;
    }

    propertyPerimeter.clear();
    propertyPerimeter.addAll(verifiedPropertyPerimeter);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    propertyPerimeter.clear();
    super.delete();
  }

}