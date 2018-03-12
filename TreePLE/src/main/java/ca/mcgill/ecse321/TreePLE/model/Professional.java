/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.26.1-f40f105-3613 modeling language!*/

package ca.mcgill.ecse321.TreePLE.model;
import java.util.*;
import java.sql.Date;

// line 28 "../../../../../TreePLE.ump"
public class Professional extends User
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Professional Associations
  private List<SustainabilityReport> report;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Professional(String aName)
  {
    super(aName);
    report = new ArrayList<SustainabilityReport>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public SustainabilityReport getReport(int index)
  {
    SustainabilityReport aReport = report.get(index);
    return aReport;
  }

  public List<SustainabilityReport> getReport()
  {
    List<SustainabilityReport> newReport = Collections.unmodifiableList(report);
    return newReport;
  }

  public int numberOfReport()
  {
    int number = report.size();
    return number;
  }

  public boolean hasReport()
  {
    boolean has = report.size() > 0;
    return has;
  }

  public int indexOfReport(SustainabilityReport aReport)
  {
    int index = report.indexOf(aReport);
    return index;
  }

  public static int minimumNumberOfReport()
  {
    return 0;
  }

  public SustainabilityReport addReport(Date aDate, Version aReportVersion, Location... allReportPerimeter)
  {
    return new SustainabilityReport(aDate, this, aReportVersion, allReportPerimeter);
  }

  public boolean addReport(SustainabilityReport aReport)
  {
    boolean wasAdded = false;
    if (report.contains(aReport)) { return false; }
    Professional existingReporter = aReport.getReporter();
    boolean isNewReporter = existingReporter != null && !this.equals(existingReporter);
    if (isNewReporter)
    {
      aReport.setReporter(this);
    }
    else
    {
      report.add(aReport);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeReport(SustainabilityReport aReport)
  {
    boolean wasRemoved = false;
    //Unable to remove aReport, as it must always have a reporter
    if (!this.equals(aReport.getReporter()))
    {
      report.remove(aReport);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addReportAt(SustainabilityReport aReport, int index)
  {  
    boolean wasAdded = false;
    if(addReport(aReport))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfReport()) { index = numberOfReport() - 1; }
      report.remove(aReport);
      report.add(index, aReport);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveReportAt(SustainabilityReport aReport, int index)
  {
    boolean wasAdded = false;
    if(report.contains(aReport))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfReport()) { index = numberOfReport() - 1; }
      report.remove(aReport);
      report.add(index, aReport);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addReportAt(aReport, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    for(int i=report.size(); i > 0; i--)
    {
      SustainabilityReport aReport = report.get(i - 1);
      aReport.delete();
    }
    super.delete();
  }

}