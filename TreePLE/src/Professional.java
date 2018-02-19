/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.26.1-f40f105-3613 modeling language!*/


import java.util.*;

// line 25 "TreePLE.ump"
public class Professional extends User
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Professional Associations
  private SustainabilityReport report;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Professional(String aName, int aId, SustainabilityReport aReport)
  {
    super(aName, aId);
    boolean didAddReport = setReport(aReport);
    if (!didAddReport)
    {
      throw new RuntimeException("Unable to create reporter due to report");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public SustainabilityReport getReport()
  {
    return report;
  }

  public boolean setReport(SustainabilityReport aReport)
  {
    boolean wasSet = false;
    if (aReport == null)
    {
      return wasSet;
    }

    SustainabilityReport existingReport = report;
    report = aReport;
    if (existingReport != null && !existingReport.equals(aReport))
    {
      existingReport.removeReporter(this);
    }
    report.addReporter(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    SustainabilityReport placeholderReport = report;
    this.report = null;
    placeholderReport.removeReporter(this);
    super.delete();
  }

}