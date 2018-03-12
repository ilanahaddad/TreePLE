/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.26.1-f40f105-3613 modeling language!*/

package ca.mcgill.ecse321.TreePLE.model;
import java.sql.Date;
import java.util.*;

// line 32 "../../../../../TreePLE.ump"
public class SustainabilityReport
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //SustainabilityReport Attributes
  private Date date;

  //SustainabilityReport Associations
  private Professional reporter;
  private List<Location> reportPerimeter;
  private Version reportVersion;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public SustainabilityReport(Date aDate, Professional aReporter, Version aReportVersion, Location... allReportPerimeter)
  {
    date = aDate;
    boolean didAddReporter = setReporter(aReporter);
    if (!didAddReporter)
    {
      throw new RuntimeException("Unable to create report due to reporter");
    }
    reportPerimeter = new ArrayList<Location>();
    boolean didAddReportPerimeter = setReportPerimeter(allReportPerimeter);
    if (!didAddReportPerimeter)
    {
      throw new RuntimeException("Unable to create SustainabilityReport, must have 4 reportPerimeter");
    }
    boolean didAddReportVersion = setReportVersion(aReportVersion);
    if (!didAddReportVersion)
    {
      throw new RuntimeException("Unable to create sustainabilityReport due to reportVersion");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setDate(Date aDate)
  {
    boolean wasSet = false;
    date = aDate;
    wasSet = true;
    return wasSet;
  }

  public Date getDate()
  {
    return date;
  }

  public Professional getReporter()
  {
    return reporter;
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

  public Version getReportVersion()
  {
    return reportVersion;
  }

  public boolean setReporter(Professional aReporter)
  {
    boolean wasSet = false;
    if (aReporter == null)
    {
      return wasSet;
    }

    Professional existingReporter = reporter;
    reporter = aReporter;
    if (existingReporter != null && !existingReporter.equals(aReporter))
    {
      existingReporter.removeReport(this);
    }
    reporter.addReport(this);
    wasSet = true;
    return wasSet;
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

  public boolean setReportVersion(Version aReportVersion)
  {
    boolean wasSet = false;
    if (aReportVersion == null)
    {
      return wasSet;
    }

    Version existingReportVersion = reportVersion;
    reportVersion = aReportVersion;
    if (existingReportVersion != null && !existingReportVersion.equals(aReportVersion))
    {
      existingReportVersion.removeSustainabilityReport(this);
    }
    reportVersion.addSustainabilityReport(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Professional placeholderReporter = reporter;
    this.reporter = null;
    placeholderReporter.removeReport(this);
    reportPerimeter.clear();
    Version placeholderReportVersion = reportVersion;
    this.reportVersion = null;
    placeholderReportVersion.removeSustainabilityReport(this);
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "reporter = "+(getReporter()!=null?Integer.toHexString(System.identityHashCode(getReporter())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "reportVersion = "+(getReportVersion()!=null?Integer.toHexString(System.identityHashCode(getReportVersion())):"null");
  }
}