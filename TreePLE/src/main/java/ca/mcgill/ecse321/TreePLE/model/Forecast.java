/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.26.1-f40f105-3613 modeling language!*/

package ca.mcgill.ecse321.TreePLE.model;
import java.util.*;

// line 41 "../../../../../TreePLE.ump"
public class Forecast
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Forecast Associations
  private List<Tree> treesInForecastArea;
  private List<Tree> treesToCutDown;
  private List<Tree> treesToPlant;
  private List<Municipality> municipalities;
  private List<Location> forecastPerimeter;
  private Version baseVersion;
  private Version forecastVersion;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Forecast(Version aBaseVersion, Version aForecastVersion, Municipality[] allMunicipalities, Location[] allForecastPerimeter)
  {
    treesInForecastArea = new ArrayList<Tree>();
    treesToCutDown = new ArrayList<Tree>();
    treesToPlant = new ArrayList<Tree>();
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
    if (!setBaseVersion(aBaseVersion))
    {
      throw new RuntimeException("Unable to create Forecast due to aBaseVersion");
    }
    if (!setForecastVersion(aForecastVersion))
    {
      throw new RuntimeException("Unable to create Forecast due to aForecastVersion");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public Tree getTreesInForecastArea(int index)
  {
    Tree aTreesInForecastArea = treesInForecastArea.get(index);
    return aTreesInForecastArea;
  }

  public List<Tree> getTreesInForecastArea()
  {
    List<Tree> newTreesInForecastArea = Collections.unmodifiableList(treesInForecastArea);
    return newTreesInForecastArea;
  }

  public int numberOfTreesInForecastArea()
  {
    int number = treesInForecastArea.size();
    return number;
  }

  public boolean hasTreesInForecastArea()
  {
    boolean has = treesInForecastArea.size() > 0;
    return has;
  }

  public int indexOfTreesInForecastArea(Tree aTreesInForecastArea)
  {
    int index = treesInForecastArea.indexOf(aTreesInForecastArea);
    return index;
  }

  public Tree getTreesToCutDown(int index)
  {
    Tree aTreesToCutDown = treesToCutDown.get(index);
    return aTreesToCutDown;
  }

  public List<Tree> getTreesToCutDown()
  {
    List<Tree> newTreesToCutDown = Collections.unmodifiableList(treesToCutDown);
    return newTreesToCutDown;
  }

  public int numberOfTreesToCutDown()
  {
    int number = treesToCutDown.size();
    return number;
  }

  public boolean hasTreesToCutDown()
  {
    boolean has = treesToCutDown.size() > 0;
    return has;
  }

  public int indexOfTreesToCutDown(Tree aTreesToCutDown)
  {
    int index = treesToCutDown.indexOf(aTreesToCutDown);
    return index;
  }

  public Tree getTreesToPlant(int index)
  {
    Tree aTreesToPlant = treesToPlant.get(index);
    return aTreesToPlant;
  }

  public List<Tree> getTreesToPlant()
  {
    List<Tree> newTreesToPlant = Collections.unmodifiableList(treesToPlant);
    return newTreesToPlant;
  }

  public int numberOfTreesToPlant()
  {
    int number = treesToPlant.size();
    return number;
  }

  public boolean hasTreesToPlant()
  {
    boolean has = treesToPlant.size() > 0;
    return has;
  }

  public int indexOfTreesToPlant(Tree aTreesToPlant)
  {
    int index = treesToPlant.indexOf(aTreesToPlant);
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

  public Version getBaseVersion()
  {
    return baseVersion;
  }

  public Version getForecastVersion()
  {
    return forecastVersion;
  }

  public static int minimumNumberOfTreesInForecastArea()
  {
    return 0;
  }

  public boolean addTreesInForecastArea(Tree aTreesInForecastArea)
  {
    boolean wasAdded = false;
    if (treesInForecastArea.contains(aTreesInForecastArea)) { return false; }
    treesInForecastArea.add(aTreesInForecastArea);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeTreesInForecastArea(Tree aTreesInForecastArea)
  {
    boolean wasRemoved = false;
    if (treesInForecastArea.contains(aTreesInForecastArea))
    {
      treesInForecastArea.remove(aTreesInForecastArea);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addTreesInForecastAreaAt(Tree aTreesInForecastArea, int index)
  {  
    boolean wasAdded = false;
    if(addTreesInForecastArea(aTreesInForecastArea))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTreesInForecastArea()) { index = numberOfTreesInForecastArea() - 1; }
      treesInForecastArea.remove(aTreesInForecastArea);
      treesInForecastArea.add(index, aTreesInForecastArea);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveTreesInForecastAreaAt(Tree aTreesInForecastArea, int index)
  {
    boolean wasAdded = false;
    if(treesInForecastArea.contains(aTreesInForecastArea))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTreesInForecastArea()) { index = numberOfTreesInForecastArea() - 1; }
      treesInForecastArea.remove(aTreesInForecastArea);
      treesInForecastArea.add(index, aTreesInForecastArea);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addTreesInForecastAreaAt(aTreesInForecastArea, index);
    }
    return wasAdded;
  }

  public static int minimumNumberOfTreesToCutDown()
  {
    return 0;
  }

  public boolean addTreesToCutDown(Tree aTreesToCutDown)
  {
    boolean wasAdded = false;
    if (treesToCutDown.contains(aTreesToCutDown)) { return false; }
    treesToCutDown.add(aTreesToCutDown);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeTreesToCutDown(Tree aTreesToCutDown)
  {
    boolean wasRemoved = false;
    if (treesToCutDown.contains(aTreesToCutDown))
    {
      treesToCutDown.remove(aTreesToCutDown);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addTreesToCutDownAt(Tree aTreesToCutDown, int index)
  {  
    boolean wasAdded = false;
    if(addTreesToCutDown(aTreesToCutDown))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTreesToCutDown()) { index = numberOfTreesToCutDown() - 1; }
      treesToCutDown.remove(aTreesToCutDown);
      treesToCutDown.add(index, aTreesToCutDown);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveTreesToCutDownAt(Tree aTreesToCutDown, int index)
  {
    boolean wasAdded = false;
    if(treesToCutDown.contains(aTreesToCutDown))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTreesToCutDown()) { index = numberOfTreesToCutDown() - 1; }
      treesToCutDown.remove(aTreesToCutDown);
      treesToCutDown.add(index, aTreesToCutDown);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addTreesToCutDownAt(aTreesToCutDown, index);
    }
    return wasAdded;
  }

  public static int minimumNumberOfTreesToPlant()
  {
    return 0;
  }

  public boolean addTreesToPlant(Tree aTreesToPlant)
  {
    boolean wasAdded = false;
    if (treesToPlant.contains(aTreesToPlant)) { return false; }
    treesToPlant.add(aTreesToPlant);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeTreesToPlant(Tree aTreesToPlant)
  {
    boolean wasRemoved = false;
    if (treesToPlant.contains(aTreesToPlant))
    {
      treesToPlant.remove(aTreesToPlant);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addTreesToPlantAt(Tree aTreesToPlant, int index)
  {  
    boolean wasAdded = false;
    if(addTreesToPlant(aTreesToPlant))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTreesToPlant()) { index = numberOfTreesToPlant() - 1; }
      treesToPlant.remove(aTreesToPlant);
      treesToPlant.add(index, aTreesToPlant);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveTreesToPlantAt(Tree aTreesToPlant, int index)
  {
    boolean wasAdded = false;
    if(treesToPlant.contains(aTreesToPlant))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTreesToPlant()) { index = numberOfTreesToPlant() - 1; }
      treesToPlant.remove(aTreesToPlant);
      treesToPlant.add(index, aTreesToPlant);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addTreesToPlantAt(aTreesToPlant, index);
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

  public boolean setBaseVersion(Version aNewBaseVersion)
  {
    boolean wasSet = false;
    if (aNewBaseVersion != null)
    {
      baseVersion = aNewBaseVersion;
      wasSet = true;
    }
    return wasSet;
  }

  public boolean setForecastVersion(Version aNewForecastVersion)
  {
    boolean wasSet = false;
    if (aNewForecastVersion != null)
    {
      forecastVersion = aNewForecastVersion;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    treesInForecastArea.clear();
    treesToCutDown.clear();
    treesToPlant.clear();
    ArrayList<Municipality> copyOfMunicipalities = new ArrayList<Municipality>(municipalities);
    municipalities.clear();
    for(Municipality aMunicipality : copyOfMunicipalities)
    {
      aMunicipality.removeForecast(this);
    }
    forecastPerimeter.clear();
    baseVersion = null;
    forecastVersion = null;
  }

}