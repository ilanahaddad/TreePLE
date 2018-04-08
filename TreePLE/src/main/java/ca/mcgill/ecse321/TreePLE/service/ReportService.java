package ca.mcgill.ecse321.TreePLE.service;


import java.awt.Polygon;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.TreePLE.model.Location;
import ca.mcgill.ecse321.TreePLE.model.SustainabilityReport;
import ca.mcgill.ecse321.TreePLE.model.Tree;
import ca.mcgill.ecse321.TreePLE.model.TreeManager;
import ca.mcgill.ecse321.TreePLE.model.VersionManager;
import ca.mcgill.ecse321.TreePLE.persistence.PersistenceXStream;

@Service
public class ReportService {
	private TreeManager tm;
	private VersionManager vm;

	public ReportService(VersionManager vm) {
		List<TreeManager> treemanagers = vm.getTreeManagers();
		for(TreeManager treeM : treemanagers) {
			if(treeM.getIsCurrent()) {
				tm = treeM;
			}
		}
		this.vm = vm;
	}

	public SustainabilityReport createReport(String reporterName, Date reportDate, Location[] perimeter) throws InvalidInputException{
		if(reporterName == null||reportDate ==null||perimeter==null) {
			throw new InvalidInputException("Error: Reporter name, date, or parameter is null");
		}
		for(int i = 0; i<perimeter.length;i++) {
			if(perimeter[i]==null) {
				throw new InvalidInputException("Error: Location coordinates are null");
			}
		}
		Location loc1 = perimeter[0];
		Location loc2 = perimeter[1];
		Location loc3 = perimeter[2];
		Location loc4 = perimeter[3];
		if(loc1.equals(loc2) || loc1.equals(loc3) || loc1.equals(loc4)
				|| loc2.equals(loc3) || loc2.equals(loc4)|| loc3.equals(loc4)) {
			throw new InvalidInputException("All coordinates must be different.\n");
			
		}
		SustainabilityReport report = new SustainabilityReport(reporterName, reportDate, perimeter);
		double biodiversityIndex = calculateBiodiversityIndex(perimeter);
		double canopy = calculateCanopy(perimeter);
		double carbonSequestration = caculateCarbonSequestration(perimeter);
		report.setBiodiversityIndex(biodiversityIndex);
		report.setCanopy(canopy);
		report.setCarbonSequestration(carbonSequestration);
		tm.addReport(report);
		PersistenceXStream.saveToXMLwithXStream(vm);
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
		if(perimeter == null) {
			throw new InvalidInputException("Error: Perimeter is null");
		}
		for(int i = 0; i<perimeter.length;i++) {
			if(perimeter[i]==null) {
				throw new InvalidInputException("Error: Location coordinates are null");
			}
		}
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
		if(perimeter == null) {
			throw new InvalidInputException("Error: Perimeter is null");
		}
		for(int i = 0; i<perimeter.length;i++) {
			if(perimeter[i]==null) {
				throw new InvalidInputException("Error: Location coordinates are null");
			}
		}
		List<Tree> treesInLocation = new ArrayList<Tree>();
		for(Tree tree : tm.getTrees()){
			double y = tree.getCoordinates().getLatitude();
			double x = tree.getCoordinates().getLongitude();
			if(isTreeInLocation(x,y,perimeter)){
				treesInLocation.add(tree);
			}
		}
		return treesInLocation;
	}
	
	public boolean isTreeInLocation(double x,double y,Location[]perimeter) throws InvalidInputException {
		if(perimeter == null) {
			throw new InvalidInputException("Error: Perimeter is null");
		}
	/*	if(x<0||y<0) {
			throw new InvalidInputException("Error: Coordinates can't be negative");
		}*/
		boolean isTreeInLocation = false;
		int npoints = perimeter.length;
		int ypoints[] = new int[npoints];
		int xpoints[] = new int[npoints];
		for(int i = 0; i<perimeter.length;i++) {
			if(perimeter[i]==null) {
				throw new InvalidInputException("Error: Location coordinates are null");
			}
			ypoints[i] = (int) (perimeter[i].getLatitude()*1000000);
			xpoints[i] = (int)(perimeter[i].getLongitude()*1000000);
		}
		Polygon shape = new Polygon(xpoints,ypoints,npoints);
		if(shape.contains((int)(x*1000000),(int)(y*1000000))) {
			isTreeInLocation= true;
		}
		return isTreeInLocation;
		
	}
	public List<SustainabilityReport> getAllSustainabilityReports(){
		return tm.getReports();
	}
	
}
