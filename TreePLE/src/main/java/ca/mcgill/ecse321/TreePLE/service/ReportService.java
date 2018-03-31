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
import ca.mcgill.ecse321.TreePLE.persistence.PersistenceXStream;

@Service
public class ReportService {
	private TreeManager tm;

	public ReportService(TreeManager tm) {
		this.tm=tm;
	}

	public SustainabilityReport createReport(String reporterName, Date reportDate, Location[] perimeter) throws InvalidInputException{
		if(reporterName == null||reportDate ==null||perimeter==null) {
			throw new InvalidInputException("Error: Report name, date, or parameter is null");
		}
		SustainabilityReport report = new SustainabilityReport(reporterName, reportDate, perimeter);
		double biodiversityIndex = calculateBiodiversityIndex(perimeter);
		double canopy = calculateCanopy(perimeter);
		double carbonSequestration = caculateCarbonSequestration(perimeter);
		report.setBiodiversityIndex(biodiversityIndex);
		report.setCanopy(canopy);
		report.setCarbonSequestration(carbonSequestration);
		tm.addReport(report);
		PersistenceXStream.saveToXMLwithXStream(tm);
		return report;
	}

	private double calculateBiodiversityIndex(Location[] perimeter) throws InvalidInputException {
		int numTrees = getTreesInLocation(perimeter).size();
		int numSpecies = getNumSpecies(perimeter);
		double biodiversityIndex = numSpecies/numTrees;
		return biodiversityIndex;
	}
	
	private double calculateCanopy(Location[] perimeter) throws InvalidInputException {
		double sumCanopy = 0;
		for(Tree tree : getTreesInLocation(perimeter)) {
			double radius = tree.getDiameter()/2; //radius of the crown
			double canopy = 2*Math.PI*Math.pow(radius, 2);
			sumCanopy += canopy;
		}
		return sumCanopy;
	}
	private double caculateCarbonSequestration(Location[] perimeter) throws InvalidInputException {
		double sumCarbonSequestration = 0;
		for(Tree tree : getTreesInLocation(perimeter)) {
			double weight;
			double width = tree.getDiameter()/4; //width of the trunk
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
		}
		return sumCarbonSequestration;
	}

	public int getNumSpecies (Location [] perimeter )throws InvalidInputException {
		List<Tree> treesInLocation = getTreesInLocation(perimeter);
		List<String> species= new ArrayList<String>();
		for (Tree tree: treesInLocation) {
			if(!species.contains(tree.getSpecies().toLowerCase())) {
				species.add(tree.getSpecies().toLowerCase());
			}
		}
		return species.size();
	}

	public List<Tree> getTreesInLocation(Location[] perimeter) throws InvalidInputException{
		List<Tree> treesInLocation = new ArrayList<Tree>();
		int npoints = perimeter.length;
		int xpoints[] = new int[npoints];
		int ypoints[] = new int[npoints];
		for(int i = 0; i<perimeter.length;i++) {
			xpoints[i] = (int) (perimeter[i].getLatitude()*10000000);
			ypoints[i] = (int)(perimeter[i].getLongitude()*10000000);
		}
		Polygon shape = new Polygon(xpoints,ypoints,npoints);
		for(Tree tree : tm.getTrees()){
			double x = tree.getCoordinates().getLatitude()*10000000;
			double y = tree.getCoordinates().getLongitude()*10000000;
			if(shape.contains(x,y)) {
				treesInLocation.add(tree);
			}
		}
		return treesInLocation;
	}
	
}
