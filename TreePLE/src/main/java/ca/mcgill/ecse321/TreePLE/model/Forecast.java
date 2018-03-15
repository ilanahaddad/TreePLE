/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.27.0.3728.d139ed893 modeling language!*/

package ca.mcgill.ecse321.TreePLE.model;
import java.util.*;

// line 39 "../../../../../TreePLE.ump"
public class Forecast
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Forecast Associations
  private List<VersionManager> versions;
  private List<Municipality> municipalities;
  private List<Location> forecastPerimeter;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Forecast(Municipality[] allMunicipalities, Location[] allForecastPerimeter)
  {
    versions = new ArrayList<VersionManager>();
    municipalities = new ArrayList<Municipality>();
    boolean didAddMunicipalities = setMunicipalities(allMunicipalities);
    if (!didAddMunicipalities)
    {
      throw new RuntimeException("Unable to create Forecast, must have at least 1 municipalities");
    }
    forecastPerimeter = new ArrayList<Location>();
    boolean didAddForecastPerimeter = setForecastPerimeter(allForecastPerimeter);
    if (!didAddForecastPerimeter)
    {
      throw new RuntimeException("Unable to create Forecast, must have 4 forecastPerimeter");
    }
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

  public Municipality getMunicipality(int index)
  {
    Municipality aMunicipality = municipalities.get(index);
    return aMunicipality;
  }

  public List<Municipality> getMunicipalities()
  {
    List<Municipality> newMunicipalities = Collections.unmodifiableList(municipalities);
    return newMunicipalities;
  }

  public int numberOfMunicipalities()
  {
    int number = municipalities.size();
    return number;
  }

  public boolean hasMunicipalities()
  {
    boolean has = municipalities.size() > 0;
    return has;
  }

  public int indexOfMunicipality(Municipality aMunicipality)
  {
    int index = municipalities.indexOf(aMunicipality);
    return index;
  }

  public Location getForecastPerimeter(int index)
  {
    Location aForecastPerimeter = forecastPerimeter.get(index);
    return aForecastPerimeter;
  }

  public List<Location> getForecastPerimeter()
  {
    List<Location> newForecastPerimeter = Collections.unmodifiableList(forecastPerimeter);
    return newForecastPerimeter;
  }

  public int numberOfForecastPerimeter()
  {
    int number = forecastPerimeter.size();
    return number;
  }

  public boolean hasForecastPerimeter()
  {
    boolean has = forecastPerimeter.size() > 0;
    return has;
  }

  public int indexOfForecastPerimeter(Location aForecastPerimeter)
  {
    int index = forecastPerimeter.indexOf(aForecastPerimeter);
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

  public boolean isNumberOfMunicipalitiesValid()
  {
    boolean isValid = numberOfMunicipalities() >= minimumNumberOfMunicipalities();
    return isValid;
  }

  public static int minimumNumberOfMunicipalities()
  {
    return 1;
  }

  public boolean addMunicipality(Municipality aMunicipality)
  {
    boolean wasAdded = false;
    if (municipalities.contains(aMunicipality)) { return false; }
    municipalities.add(aMunicipality);
    if (aMunicipality.indexOfForecast(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aMunicipality.addForecast(this);
      if (!wasAdded)
      {
        municipalities.remove(aMunicipality);
      }
    }
    return wasAdded;
  }

  public boolean removeMunicipality(Municipality aMunicipality)
  {
    boolean wasRemoved = false;
    if (!municipalities.contains(aMunicipality))
    {
      return wasRemoved;
    }

    if (numberOfMunicipalities() <= minimumNumberOfMunicipalities())
    {
      return wasRemoved;
    }

    int oldIndex = municipalities.indexOf(aMunicipality);
    municipalities.remove(oldIndex);
    if (aMunicipality.indexOfForecast(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aMunicipality.removeForecast(this);
      if (!wasRemoved)
      {
        municipalities.add(oldIndex,aMunicipality);
      }
    }
    return wasRemoved;
  }

  public boolean setMunicipalities(Municipality... newMunicipalities)
  {
    boolean wasSet = false;
    ArrayList<Municipality> verifiedMunicipalities = new ArrayList<Municipality>();
    for (Municipality aMunicipality : newMunicipalities)
    {
      if (verifiedMunicipalities.contains(aMunicipality))
      {
        continue;
      }
      verifiedMunicipalities.add(aMunicipality);
    }

    if (verifiedMunicipalities.size() != newMunicipalities.length || verifiedMunicipalities.size() < minimumNumberOfMunicipalities())
    {
      return wasSet;
    }

    ArrayList<Municipality> oldMunicipalities = new ArrayList<Municipality>(municipalities);
    municipalities.clear();
    for (Municipality aNewMunicipality : verifiedMunicipalities)
    {
      municipalities.add(aNewMunicipality);
      if (oldMunicipalities.contains(aNewMunicipality))
      {
        oldMunicipalities.remove(aNewMunicipality);
      }
      else
      {
        aNewMunicipality.addForecast(this);
      }
    }

    for (Municipality anOldMunicipality : oldMunicipalities)
    {
      anOldMunicipality.removeForecast(this);
    }
    wasSet = true;
    return wasSet;
  }

  public boolean addMunicipalityAt(Municipality aMunicipality, int index)
  {  
    boolean wasAdded = false;
    if(addMunicipality(aMunicipality))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfMunicipalities()) { index = numberOfMunicipalities() - 1; }
      municipalities.remove(aMunicipality);
      municipalities.add(index, aMunicipality);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveMunicipalityAt(Municipality aMunicipality, int index)
  {
    boolean wasAdded = false;
    if(municipalities.contains(aMunicipality))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfMunicipalities()) { index = numberOfMunicipalities() - 1; }
      municipalities.remove(aMunicipality);
      municipalities.add(index, aMunicipality);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addMunicipalityAt(aMunicipality, index);
    }
    return wasAdded;
  }

  public static int requiredNumberOfForecastPerimeter()
  {
    return 4;
  }

  public static int minimumNumberOfForecastPerimeter()
  {
    return 4;
  }

  public static int maximumNumberOfForecastPerimeter()
  {
    return 4;
  }

  public boolean setForecastPerimeter(Location... newForecastPerimeter)
  {
    boolean wasSet = false;
    ArrayList<Location> verifiedForecastPerimeter = new ArrayList<Location>();
    for (Location aForecastPerimeter : newForecastPerimeter)
    {
      if (verifiedForecastPerimeter.contains(aForecastPerimeter))
      {
        continue;
      }
      verifiedForecastPerimeter.add(aForecastPerimeter);
    }

    if (verifiedForecastPerimeter.size() != newForecastPerimeter.length || verifiedForecastPerimeter.size() != requiredNumberOfForecastPerimeter())
    {
      return wasSet;
    }

    forecastPerimeter.clear();
    forecastPerimeter.addAll(verifiedForecastPerimeter);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    versions.clear();
    ArrayList<Municipality> copyOfMunicipalities = new ArrayList<Municipality>(municipalities);
    municipalities.clear();
    for(Municipality aMunicipality : copyOfMunicipalities)
    {
      aMunicipality.removeForecast(this);
    }
    forecastPerimeter.clear();
  }

}