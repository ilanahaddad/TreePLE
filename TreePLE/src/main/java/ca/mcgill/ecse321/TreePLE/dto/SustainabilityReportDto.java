package ca.mcgill.ecse321.TreePLE.dto;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class SustainabilityReportDto {
	private String reporterName;
	private Date reportDate;
	private double biodiversityIndex;
	private double canopy;
	private double carbonSequestration;
	private List<LocationDto> reportPerimeter;
	
	public SustainabilityReportDto(){
		
	}
	
	public SustainabilityReportDto(String reporterName, Date reportDate, ArrayList<LocationDto> perimeter ) {
		this.reporterName = reporterName;
		this.reportDate = reportDate;
		this.reportPerimeter = perimeter;
	}
	public String getReporterName() {
		return reporterName;
	}
	public Date getReportDate() {
		return reportDate;
	}
	public List<LocationDto> getReportPerimeter() {
		return reportPerimeter;
	}
	public void setBiodiversityIndex(double biodiversityIndex) {
		this.biodiversityIndex = biodiversityIndex;
	}
	public void setCanopy(double canopy) {
		this.canopy = canopy;
	}
	public void setCarbonSequestration(double carbonSequestration) {
		this.carbonSequestration = carbonSequestration;
	}
}
