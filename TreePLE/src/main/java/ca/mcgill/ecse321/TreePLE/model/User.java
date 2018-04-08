/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.27.0.3728.d139ed893 modeling language!*/

package ca.mcgill.ecse321.TreePLE.model;

// line 18 "../../../../../TreePLE.ump"
public class User
{
	public enum UserType {LocalResident, Professional}
  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //User Attributes
  private UserType usertype;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public User()
  {
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setUsertype(UserType aUsertype)
  {
    boolean wasSet = false;
    usertype = aUsertype;
    wasSet = true;
    return wasSet;
  }

  public UserType getUsertype()
  {
    return usertype;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "usertype" + "=" + (getUsertype() != null ? !getUsertype().equals(this)  ? getUsertype().toString().replaceAll("  ","    ") : "this" : "null");
  }
}