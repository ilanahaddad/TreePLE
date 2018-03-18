/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.26.1-f40f105-3613 modeling language!*/

package ca.mcgill.ecse321.TreePLE.model;
import java.sql.Date;
import java.util.*;

// line 26 "../../../../../TreePLE.ump"
public class SustainabilityReport
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //SustainabilityReport Attributes
  private String reporterName;
  private Date date;
  private double biodiversityIndex;
  private double canopy;
  private double carbonSequestration;

  //SustainabilityReport Associations
  private List<Location> reportPerimeter;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public SustainabilityReport(String aReporterName, Date aDate, Location... allReportPerimeter)
  {
    reporterName = aReporterName;
    date = aDate;
    reportPerimeter = new ArrayList<Location>();
    boolean didAddReportPerimeter = setReportPerimeter(allReportPerimeter);
    if (!didAddReportPerimeter)
    {
      throw new RuntimeException("Unable to create SustainabilityReport, must have 4 reportPerimeter");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setReporterName(String aReporterName)
  {
    boolean wasSet = false;
    reporterName = aReporterName;
    wasSet = true;
    return wasSet;
  }

  public boolean setDate(Date aDate)
  {
    boolean wasSet = false;
    date = aDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setBiodiversityIndex(double aBiodiversityIndex)
  {
    boolean wasSet = false;
    biodiversityIndex = aBiodiversityIndex;
    wasSet = true;
    return wasSet;
  }

  public boolean setCanopy(double aCanopy)
  {
    boolean wasSet = false;
    canopy = aCanopy;
    wasSet = true;
    return wasSet;
  }

  public boolean setCarbonSequestration(double aCarbonSequestration)
  {
    boolean wasSet = false;
    carbonSequestration = aCarbonSequestration;
    wasSet = true;
    return wasSet;
  }

  public String getReporterName()
  {
    return reporterName;
  }

  public Date getDate()
  {
    return date;
  }

  public double getBiodiversityIndex()
  {
    return biodiversityIndex;
  }

  public double getCanopy()
  {
    return canopy;
  }

  public double getCarbonSequestration()
  {
    return carbonSequestration;
  }

  public Location getReportPerimeter(int index)
  {
    Location aReportPerimeter = reportPerimeter.get(index);
    return aReportPerimeter;
  }

  public List<Location> getReportPerimeter()
  {
    List<Location> newReportPerimeter = Collections.unmodifiableList(reportPerimeter);
    return newReportPerimeter;
  }

  public int numberOfReportPerimeter()
  {
    int number = reportPerimeter.size();
    return number;
  }

  public boolean hasReportPerimeter()
  {
    boolean has = reportPerimeter.size() > 0;
    return has;
  }

  public int indexOfReportPerimeter(Location aReportPerimeter)
  {
    int index = reportPerimeter.indexOf(aReportPerimeter);
    return index;
  }

  public static int requiredNumberOfReportPerimeter()
  {
    return 4;
  }

  public static int minimumNumberOfReportPerimeter()
  {
    return 4;
  }

  public static int maximumNumberOfReportPerimeter()
  {
    return 4;
  }

  public boolean setReportPerimeter(Location... newReportPerimeter)
  {
    boolean wasSet = false;
    ArrayList<Location> verifiedReportPerimeter = new ArrayList<Location>();
    for (Location aReportPerimeter : newReportPerimeter)
    {
      if (verifiedReportPerimeter.contains(aReportPerimeter))
      {
        continue;
      }
      verifiedReportPerimeter.add(aReportPerimeter);
    }

    if (verifiedReportPerimeter.size() != newReportPerimeter.length || verifiedReportPerimeter.size() != requiredNumberOfReportPerimeter())
    {
      return wasSet;
    }

    reportPerimeter.clear();
    reportPerimeter.addAll(verifiedReportPerimeter);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    reportPerimeter.clear();
  }


  public String toString()
  {
    return super.toString() + "["+
            "reporterName" + ":" + getReporterName()+ "," +
            "biodiversityIndex" + ":" + getBiodiversityIndex()+ "," +
            "canopy" + ":" + getCanopy()+ "," +
            "carbonSequestration" + ":" + getCarbonSequestration()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null");
  }
}