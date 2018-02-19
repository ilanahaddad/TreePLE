/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.26.1-f40f105-3613 modeling language!*/


import java.sql.Date;
import java.util.*;

// line 29 "TreePLE.ump"
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

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public SustainabilityReport(Date aDate, Location... allReportPerimeter)
  {
    date = aDate;
    reporter = new ArrayList<Professional>();
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

  public static int minimumNumberOfReporter()
  {
    return 0;
  }

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

  public void delete()
  {
    for(int i=reporter.size(); i > 0; i--)
    {
      Professional aReporter = reporter.get(i - 1);
      aReporter.delete();
    }
    reportPerimeter.clear();
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null");
  }
}