/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.26.1-f40f105-3613 modeling language!*/

package ca.mcgill.ecse321.TreePLE.model;
import java.util.*;

// line 38 "../../../../../TreePLE.ump"
public class Municipality
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Municipality Attributes
  private String name;

  //Municipality Associations
  private List<Forecast> forecasts;
  private List<Tree> listOfTrees;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Municipality(String aName)
  {
    name = aName;
    forecasts = new ArrayList<Forecast>();
    listOfTrees = new ArrayList<Tree>();
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

  public String getName()
  {
    return name;
  }

  public Forecast getForecast(int index)
  {
    Forecast aForecast = forecasts.get(index);
    return aForecast;
  }

  public List<Forecast> getForecasts()
  {
    List<Forecast> newForecasts = Collections.unmodifiableList(forecasts);
    return newForecasts;
  }

  public int numberOfForecasts()
  {
    int number = forecasts.size();
    return number;
  }

  public boolean hasForecasts()
  {
    boolean has = forecasts.size() > 0;
    return has;
  }

  public int indexOfForecast(Forecast aForecast)
  {
    int index = forecasts.indexOf(aForecast);
    return index;
  }

  public Tree getListOfTree(int index)
  {
    Tree aListOfTree = listOfTrees.get(index);
    return aListOfTree;
  }

  public List<Tree> getListOfTrees()
  {
    List<Tree> newListOfTrees = Collections.unmodifiableList(listOfTrees);
    return newListOfTrees;
  }

  public int numberOfListOfTrees()
  {
    int number = listOfTrees.size();
    return number;
  }

  public boolean hasListOfTrees()
  {
    boolean has = listOfTrees.size() > 0;
    return has;
  }

  public int indexOfListOfTree(Tree aListOfTree)
  {
    int index = listOfTrees.indexOf(aListOfTree);
    return index;
  }

  public static int minimumNumberOfForecasts()
  {
    return 0;
  }

  public boolean addForecast(Forecast aForecast)
  {
    boolean wasAdded = false;
    if (forecasts.contains(aForecast)) { return false; }
    forecasts.add(aForecast);
    if (aForecast.indexOfMunicipality(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aForecast.addMunicipality(this);
      if (!wasAdded)
      {
        forecasts.remove(aForecast);
      }
    }
    return wasAdded;
  }

  public boolean removeForecast(Forecast aForecast)
  {
    boolean wasRemoved = false;
    if (!forecasts.contains(aForecast))
    {
      return wasRemoved;
    }

    int oldIndex = forecasts.indexOf(aForecast);
    forecasts.remove(oldIndex);
    if (aForecast.indexOfMunicipality(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aForecast.removeMunicipality(this);
      if (!wasRemoved)
      {
        forecasts.add(oldIndex,aForecast);
      }
    }
    return wasRemoved;
  }

  public boolean addForecastAt(Forecast aForecast, int index)
  {  
    boolean wasAdded = false;
    if(addForecast(aForecast))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfForecasts()) { index = numberOfForecasts() - 1; }
      forecasts.remove(aForecast);
      forecasts.add(index, aForecast);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveForecastAt(Forecast aForecast, int index)
  {
    boolean wasAdded = false;
    if(forecasts.contains(aForecast))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfForecasts()) { index = numberOfForecasts() - 1; }
      forecasts.remove(aForecast);
      forecasts.add(index, aForecast);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addForecastAt(aForecast, index);
    }
    return wasAdded;
  }

  public static int minimumNumberOfListOfTrees()
  {
    return 0;
  }

  public Tree addListOfTree(String aOwnerName, String aSpecies, double aHeight, double aDiameter, int aAge, Location aCoordinates)
  {
    return new Tree(aOwnerName, aSpecies, aHeight, aDiameter, aAge, aCoordinates, this);
  }

  public boolean addListOfTree(Tree aListOfTree)
  {
    boolean wasAdded = false;
    if (listOfTrees.contains(aListOfTree)) { return false; }
    Municipality existingTreeMunicipality = aListOfTree.getTreeMunicipality();
    boolean isNewTreeMunicipality = existingTreeMunicipality != null && !this.equals(existingTreeMunicipality);
    if (isNewTreeMunicipality)
    {
      aListOfTree.setTreeMunicipality(this);
    }
    else
    {
      listOfTrees.add(aListOfTree);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeListOfTree(Tree aListOfTree)
  {
    boolean wasRemoved = false;
    //Unable to remove aListOfTree, as it must always have a treeMunicipality
    if (!this.equals(aListOfTree.getTreeMunicipality()))
    {
      listOfTrees.remove(aListOfTree);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addListOfTreeAt(Tree aListOfTree, int index)
  {  
    boolean wasAdded = false;
    if(addListOfTree(aListOfTree))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfListOfTrees()) { index = numberOfListOfTrees() - 1; }
      listOfTrees.remove(aListOfTree);
      listOfTrees.add(index, aListOfTree);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveListOfTreeAt(Tree aListOfTree, int index)
  {
    boolean wasAdded = false;
    if(listOfTrees.contains(aListOfTree))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfListOfTrees()) { index = numberOfListOfTrees() - 1; }
      listOfTrees.remove(aListOfTree);
      listOfTrees.add(index, aListOfTree);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addListOfTreeAt(aListOfTree, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    ArrayList<Forecast> copyOfForecasts = new ArrayList<Forecast>(forecasts);
    forecasts.clear();
    for(Forecast aForecast : copyOfForecasts)
    {
      if (aForecast.numberOfMunicipalities() <= Forecast.minimumNumberOfMunicipalities())
      {
        aForecast.delete();
      }
      else
      {
        aForecast.removeMunicipality(this);
      }
    }
    for(int i=listOfTrees.size(); i > 0; i--)
    {
      Tree aListOfTree = listOfTrees.get(i - 1);
      aListOfTree.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "]";
  }
}