package ca.mcgill.ecse321.TreePLE.service;

import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.TreePLE.model.Location;
import ca.mcgill.ecse321.TreePLE.model.SustainabilityReport;
import ca.mcgill.ecse321.TreePLE.model.Tree;
import ca.mcgill.ecse321.TreePLE.model.TreeManager;

@Service
public class ReportService {
	private TreeManager tm;

	public ReportService(TreeManager tm) {
		this.tm=tm;
	}

	public SustainabilityReport createReport(String reporterName, Date reportDate, Location[] perimeter) {
		double[] sustainabilityAttributes = calculateSustainabilityAttributes(perimeter);
		SustainabilityReport report = new SustainabilityReport(reporterName, reportDate, perimeter);
		report.setSustainabilityAttributes(sustainabilityAttributes);
		return report;
	}

	private double[] calculateSustainabilityAttributes(Location[] perimeter) {
		//TODO
		double sumCanopy = 0;
		double sumCarbonSequestration = 0;
		int numTrees= 0;
		for(Tree tree : tm.getTreesInLocation(perimeter)) {			
			//canopy
			double canopy = 2*Math.PI*Math.pow(tree.getDiameter(), 2);
			sumCanopy += canopy;
			//carbon sequestration
			double weight;
			if(tree.getWidth() < 11) {
				weight = 0.25*Math.pow(tree.getWidth(),2)*tree.getHeight();
			}
			else {
				weight = 0.15*Math.pow(tree.getWidth(),2)*tree.getHeight();
			}
			double dryWeight = 0.725 * weight;
			double carbonWeight = 0.5*dryWeight;
			double  CO2Weight = carbonWeight*3.6663;
			double carbonSequestration = CO2Weight/tree.getAge();
			sumCarbonSequestration += carbonSequestration;
			numTrees++;
		}
		double biodiversityIndex = tm.getNumSpecies(perimeter)/numTrees;
		double [] sustainabilityAttributes = {biodiversityIndex, sumCanopy, sumCarbonSequestration};
		return sustainabilityAttributes;
	}



}
