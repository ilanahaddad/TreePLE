package ca.mcgill.ecse321.TreePLE.service;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.awt.Polygon;
import java.sql.Date;
import java.util.ArrayList;
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

	public SustainabilityReport createReport(String reporterName, Date reportDate, Location[] perimeter) throws InvalidInputException{
		if(reporterName==null) {
			throw new InvalidInputException("Error: Name can't be null.");
		}
		double[] sustainabilityAttributes = calculateSustainabilityAttributes(perimeter);
		SustainabilityReport report = new SustainabilityReport(reporterName, reportDate, perimeter);
		//TODO: fix later
		//report.setSustainabilityAttributes(sustainabilityAttributes);
		return report;
	}

	private double[] calculateSustainabilityAttributes(Location[] perimeter) throws InvalidInputException {
		//TODO
		/*
		double sumCanopy = 0;
		double sumCarbonSequestration = 0;
		int numTrees= 0;
		for(Tree tree : tm.getTreesInLocation(perimeter)) {			
			//canopy
			double canopy = 2*Math.PI*Math.pow(tree.getDiameter(), 2);
			double width = tree.getDiameter()/4;
			sumCanopy += canopy;
			//carbon sequestration
			double weight;
			if(width < 11) {
				weight = 0.25*Math.pow(width,2)*tree.getHeight();
			}
			else {
				weight = 0.15*Math.pow(width,2)*tree.getHeight();
			}
			double dryWeight = 0.725 * weight;
			double carbonWeight = 0.5*dryWeight;
			double  CO2Weight = carbonWeight*3.6663;
			double carbonSequestration = CO2Weight/tree.getAge();
			sumCarbonSequestration += carbonSequestration;
			numTrees++;
		}
		double biodiversityIndex = getNumSpecies(perimeter)/numTrees;
		double [] sustainabilityAttributes = {biodiversityIndex, sumCanopy, sumCarbonSequestration};
		return sustainabilityAttributes;*/
		return null;
	}
	public int getNumSpecies (Location [] locations )throws InvalidInputException {
		List<Tree> allTrees= tm.getTrees();
		int  [] x_points= new int [4];
		int [] y_points= new int [4];
		List<String> species= new ArrayList<String>();
		int i=0;
		
		if(locations.length!=4) {
			throw new InvalidInputException("Error: Must Provide 4 boundary coordinates!");
		}
		for(Location l: locations){
			if(l==null) {
				throw new InvalidInputException("Error: Location cannot be null!");
			}
		}
		for(Location l: locations) {
			x_points[i]=(int)l.getLatitude();
			y_points[i]=(int)l.getLongitude();
			i++;
		}
		Polygon perimeter= new Polygon(x_points, y_points, 4);
		for (Tree t: allTrees) {

			if(perimeter.contains((t.getCoordinates().getLatitude()), (t.getCoordinates().getLongitude()))){
				if(!species.contains(t.getSpecies().toLowerCase())) {
					species.add(t.getSpecies().toLowerCase());
				}

			}
		}
		return species.size();

	}




}
