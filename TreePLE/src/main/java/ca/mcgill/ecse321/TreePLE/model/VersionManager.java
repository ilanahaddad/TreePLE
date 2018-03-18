/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.26.1-f40f105-3613 modeling language!*/

package ca.mcgill.ecse321.TreePLE.model;
import java.util.*;

// line 58 "../../../../../TreePLE.ump"
public class VersionManager
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //VersionManager Associations
  private List<TreeManager> treeManagers;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public VersionManager()
  {
    treeManagers = new ArrayList<TreeManager>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public TreeManager getTreeManager(int index)
  {
    TreeManager aTreeManager = treeManagers.get(index);
    return aTreeManager;
  }

  public List<TreeManager> getTreeManagers()
  {
    List<TreeManager> newTreeManagers = Collections.unmodifiableList(treeManagers);
    return newTreeManagers;
  }

  public int numberOfTreeManagers()
  {
    int number = treeManagers.size();
    return number;
  }

  public boolean hasTreeManagers()
  {
    boolean has = treeManagers.size() > 0;
    return has;
  }

  public int indexOfTreeManager(TreeManager aTreeManager)
  {
    int index = treeManagers.indexOf(aTreeManager);
    return index;
  }

  public static int minimumNumberOfTreeManagers()
  {
    return 0;
  }

  public boolean addTreeManager(TreeManager aTreeManager)
  {
    boolean wasAdded = false;
    if (treeManagers.contains(aTreeManager)) { return false; }
    treeManagers.add(aTreeManager);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeTreeManager(TreeManager aTreeManager)
  {
    boolean wasRemoved = false;
    if (treeManagers.contains(aTreeManager))
    {
      treeManagers.remove(aTreeManager);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addTreeManagerAt(TreeManager aTreeManager, int index)
  {  
    boolean wasAdded = false;
    if(addTreeManager(aTreeManager))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTreeManagers()) { index = numberOfTreeManagers() - 1; }
      treeManagers.remove(aTreeManager);
      treeManagers.add(index, aTreeManager);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveTreeManagerAt(TreeManager aTreeManager, int index)
  {
    boolean wasAdded = false;
    if(treeManagers.contains(aTreeManager))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTreeManagers()) { index = numberOfTreeManagers() - 1; }
      treeManagers.remove(aTreeManager);
      treeManagers.add(index, aTreeManager);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addTreeManagerAt(aTreeManager, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    treeManagers.clear();
  }

}