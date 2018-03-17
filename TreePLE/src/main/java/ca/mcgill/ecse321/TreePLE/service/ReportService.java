package ca.mcgill.ecse321.TreePLE.service;

import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.TreePLE.model.Location;
import ca.mcgill.ecse321.TreePLE.model.SustainabilityReport;
import ca.mcgill.ecse321.TreePLE.model.TreeManager;

@Service
public class ReportService {
	private TreeManager tm;

	public ReportService(TreeManager tm) {
		this.tm=tm;
	}

	public SustainabilityReport createReport(String reporterName, Date reportDate, Location[] perimeter) {
		calculateSustainabilityAttributes(perimeter);
		SustainabilityReport report = new SustainabilityReport(reporterName, reportDate, perimeter);
		return report;
	}

	private void calculateSustainabilityAttributes(Location[] perimeter) {
		//TODO
		
	}
	
	

}
