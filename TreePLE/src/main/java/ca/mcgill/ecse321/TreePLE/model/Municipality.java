/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.27.0.3728.d139ed893 modeling language!*/

package ca.mcgill.ecse321.TreePLE.model;
import java.util.*;

// line 37 "../../../../../TreePLE.ump"
public class Municipality
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Municipality Attributes
  private String name;

  //Municipality Associations
  private List<Tree> listOfTrees;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Municipality(String aName)
  {
    name = aName;
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

  public static int minimumNumberOfListOfTrees()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
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