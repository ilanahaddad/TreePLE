/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.27.0.3728.d139ed893 modeling language!*/

package ca.mcgill.ecse321.TreePLE.model;
import java.sql.Date;
import java.util.*;

// line 30 "../../../../../TreePLE.ump"
public class SustainabilityReport
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //SustainabilityReport Attributes
  private Date date;

  //SustainabilityReport Associations
  private List<Professional> reporter;
  private List<Location> reportPerimeter;
  private Version reportVersion;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public SustainabilityReport(Date aDate, Version aReportVersion, Location... allReportPerimeter)
  {
    date = aDate;
    reporter = new ArrayList<Professional>();
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

  public Professional getReporter(int index)
  {
    Professional aReporter = reporter.get(index);
    return aReporter;
  }

  public List<Professional> getReporter()
  {
    List<Professional> newReporter = Collections.unmodifiableList(reporter);
    return newReporter;
  }

  public int numberOfReporter()
  {
    int number = reporter.size();
    return number;
  }

  public boolean hasReporter()
  {
    boolean has = reporter.size() > 0;
    return has;
  }

  public int indexOfReporter(Professional aReporter)
  {
    int index = reporter.indexOf(aReporter);
    return index;
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

  public static int minimumNumberOfReporter()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Professional addReporter(String aName, int aId)
  {
    return new Professional(aName, aId, this);
  }

  public boolean addReporter(Professional aReporter)
  {
    boolean wasAdded = false;
    if (reporter.contains(aReporter)) { return false; }
    SustainabilityReport existingReport = aReporter.getReport();
    boolean isNewReport = existingReport != null && !this.equals(existingReport);
    if (isNewReport)
    {
      aReporter.setReport(this);
    }
    else
    {
      reporter.add(aReporter);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeReporter(Professional aReporter)
  {
    boolean wasRemoved = false;
    //Unable to remove aReporter, as it must always have a report
    if (!this.equals(aReporter.getReport()))
    {
      reporter.remove(aReporter);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addReporterAt(Professional aReporter, int index)
  {  
    boolean wasAdded = false;
    if(addReporter(aReporter))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfReporter()) { index = numberOfReporter() - 1; }
      reporter.remove(aReporter);
      reporter.add(index, aReporter);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveReporterAt(Professional aReporter, int index)
  {
    boolean wasAdded = false;
    if(reporter.contains(aReporter))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfReporter()) { index = numberOfReporter() - 1; }
      reporter.remove(aReporter);
      reporter.add(index, aReporter);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addReporterAt(aReporter, index);
    }
    return wasAdded;
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
    for(int i=reporter.size(); i > 0; i--)
    {
      Professional aReporter = reporter.get(i - 1);
      aReporter.delete();
    }
    reportPerimeter.clear();
    Version placeholderReportVersion = reportVersion;
    this.reportVersion = null;
    if(placeholderReportVersion != null)
    {
      placeholderReportVersion.removeSustainabilityReport(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "reportVersion = "+(getReportVersion()!=null?Integer.toHexString(System.identityHashCode(getReportVersion())):"null");
  }
}