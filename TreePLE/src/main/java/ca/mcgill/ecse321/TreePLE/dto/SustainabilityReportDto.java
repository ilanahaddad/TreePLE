package ca.mcgill.ecse321.TreePLE.dto;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class SustainabilityReportDto {
	private String reporterName;
	private Date reportdate;
	private double biodiversityIndex;
	private double canopy;
	private double carbonSequestration;
	private List<LocationDto> reportPerimeter;
	
	public SustainabilityReportDto(String reporterName, Date reportDate, ArrayList<LocationDto> perimeter ) {
		this.reporterName = reporterName;
		this.reportdate = reportDate;
		this.reportPerimeter = perimeter;
	}
	public String getReporterName() {
		return reporterName;
	}
	public Date getReportDate() {
		return reportdate;
	}
	public List<LocationDto> getReportPerimeter() {
		return reportPerimeter;
	}
}
