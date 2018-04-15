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
/**
 * This service class contains all the main functionalities related to generating a Sustainability Report. 
 * @authors Ilana Haddad, Asma Alromaih, Diana Serra
 */
@Service
public class ReportService {
	private TreeManager tm;
	private VersionManager vm;
	/**
	 * The ReportService constructor verifies which version has been selected by the user, 
	 * finds the TreeManager associated to that version and sets the class's TreeManager as that one for users
	 * to edit attributes on that TreeManager.
	 * @param vm VersionManager of the application
	 */
	public ReportService(VersionManager vm) {
		List<TreeManager> treemanagers = vm.getTreeManagers();
		for(TreeManager treeM : treemanagers) {
			if(treeM.getIsSelected()) {
				this.tm = treeM;
			}
		}
		this.vm = vm;
	}
	/**
	 * This method creates a sustainability report given the following parameters:
	 * @param reporterName name of reporting person creating the report
	 * @param reportDate date the report was generated 
	 * @param perimeter perimeter for which the sustainability attributes will be calculated for
	 * @return SustainabilityReport created
	 * @throws InvalidInputException 
	 */
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
	/**
	 * This method calculates the biodiversity index given a perimeter of 4 location coordinates
	 * @param perimeter array of 4 locations
	 * @return biodiversity index
	 * @throws InvalidInputException
	 */
	private double calculateBiodiversityIndex(Location[] perimeter) throws InvalidInputException {
		int numTrees = getTreesInLocation(perimeter).size();
		if(numTrees==0) {
			return 0;
		}
		int numSpecies = getNumSpecies(perimeter);
		double biodiversityIndex = numSpecies/numTrees;
		return biodiversityIndex;
	}
	/**
	 * This method calculates the sum of canopies for all trees in the perimeter
	 * @param perimeter array of 4 locations
	 * @return canopy
	 * @throws InvalidInputException
	 */
	private double calculateCanopy(Location[] perimeter) throws InvalidInputException {
		double sumCanopy = 0;
		for(Tree tree : getTreesInLocation(perimeter)) {
			double radius = tree.getDiameter()/2; //radius of the crown
			double canopy = 2*Math.PI*Math.pow(radius, 2);
			sumCanopy += canopy;
		}
		return sumCanopy;
	}
	/**
	 * This method calculates the carbon sequestration in a given area
	 * @param perimeter array of 4 locations
	 * @return carbon sequestration
	 * @throws InvalidInputException
	 */
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
	/**
	 * This method calculates the number of different species in an area
	 * @param perimeter array of 4 locations
	 * @return number of total species in the perimeter
	 * @throws InvalidInputException
	 */
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
	/**
	 * This method finds all trees in the perimeter.
	 * @param perimeter array of 4 locations
	 * @return List<Tree> list of trees in that area
	 * @throws InvalidInputException
	 */
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
	/**
	 * This method checks whether a tree at coordinates x,y isr contained in the perimete
	 * @param x latitude of tree
	 * @param y longitude of tree
	 * @param perimeter array of 4 locations
	 * @return true or false depending on whether these coordinates are contained in the perimeter
	 * @throws InvalidInputException
	 */
	public boolean isTreeInLocation(double x,double y,Location[]perimeter) throws InvalidInputException {
		if(perimeter == null) {
			throw new InvalidInputException("Error: Perimeter is null");
		}
	
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
	/**
	 * This method returns all the sustainability reports generated in the TreeManager
	 * @return List<SustainabilityReport> list of sustainability reports
	 */
	public List<SustainabilityReport> getAllSustainabilityReports(){
		return tm.getReports();
	}
	/**
	 * This method creates a location given a latitude and a longitude
	 * @param latitude
	 * @param longitude
	 * @return Location created 
	 * @throws InvalidInputException if the given latitude and longitude are not in the correct range. 
	 */
	public Location createLocation(double latitude, double longitude) throws InvalidInputException{
		if(latitude < -90 || latitude >90) {
			throw new InvalidInputException("Latitude must be in range [-90,90].\n");
		}
		if(longitude < -180 || longitude >180) {
			throw new InvalidInputException("Longitude must be in range [-180,180].\n");
		}
		Location location = new Location(latitude, longitude);
		tm.addLocation(location);
		PersistenceXStream.saveToXMLwithXStream(vm);
		return location;
	}
	/**
	 * This method checks if a location already exists in the system with that latitude and longitude pairing. 
	 * If it does not, it will call the createLocation method.
	 * @param lati latitude 
	 * @param longi longitude
	 * @return Location created or returned
	 * @throws InvalidInputException
	 */
	public Location getLocationByCoordinates(double lati, double longi) throws InvalidInputException {
		//check if location already exists
		List<Location> locations = tm.getLocations();
		for(Location l: locations) {
			if(l.getLatitude()==lati && l.getLongitude()==longi) { //location exists
				return l;
			}
		}
		//return new one if existing one wasnt found in for loop
		Location location = createLocation(lati,longi);
		return location;
	}
	
}
