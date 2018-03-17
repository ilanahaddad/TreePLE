package ca.mcgill.ecse321.TreePLE.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.TreePLE.model.Location;
import ca.mcgill.ecse321.TreePLE.model.Municipality;
import ca.mcgill.ecse321.TreePLE.model.Tree;
import ca.mcgill.ecse321.TreePLE.model.Tree.LandUse;
import ca.mcgill.ecse321.TreePLE.model.TreeManager;
import ca.mcgill.ecse321.TreePLE.model.User;
import ca.mcgill.ecse321.TreePLE.model.User.UserType;
import ca.mcgill.ecse321.TreePLE.persistence.PersistenceXStream;

@Service
public class TreeManagerService {
	private TreeManager tm;
	
	public TreeManagerService(TreeManager tm) {
		this.tm=tm;
	}
	public Tree createTree(String ownerName, String species,  double height, double diameter, 
			 int age, Location location, 
			Municipality municipality,  Tree.LandUse land) throws InvalidInputException{
		if(species==null || location==null || municipality==null) {
			throw new InvalidInputException("Error: Species name, tree location, owner, municipality, or version cannot be null!");
		}
		if(species==" " ) {
			throw new InvalidInputException("Error: Species name is empty!");
		}
		if(location.hasTreeInLocation()) {
			throw new InvalidInputException("Error: There is already a tree in this location!");
		}
		//TODO: if owner is a local resident, check tree location is contained in owner's perimeter
		
		
		Tree t = new Tree(ownerName, species, height, diameter,age, location, municipality);
		t.setLand(land);
		
		location.setTreeInLocation(t);
		tm.addTree(t);
		
		PersistenceXStream.saveToXMLwithXStream(tm);
		return t;
	}
	public Municipality createMunicipality(String name) throws InvalidInputException {
		List<Municipality> municipalities = tm.getMunicipalities();
		if (name==null) {
			throw new InvalidInputException("Error: Name of Municipality is null!");
		}
		if (name==" ") {
			throw new InvalidInputException("Error: Name of Municipality is blank!");
		}
		for(Municipality m: municipalities) {
			if(name == m.getName()) {
				throw new InvalidInputException("Error: Municipality already exists.");
			}
		}
		Municipality municipality = new Municipality(name);
		tm.addMunicipality(municipality);
		PersistenceXStream.saveToXMLwithXStream(tm);
		return municipality;
	}



	public Location getLocationForTree(Tree t) {
		return t.getCoordinates();
	}

	public Municipality getMunicipalityForTree(Tree t) {
		return t.getTreeMunicipality();
	}
	public Municipality getMunicipalityByName(String name) {
		List<Municipality> municipalities = tm.getMunicipalities();
		for(Municipality m: municipalities) {
			if(m.getName().equals(name)) {
				return m;
				
			}
		}
		return null;
	}
	public Location getLocationByCoordinates(double lati, double longi) {
		//check if location already exists
		List<Location> locations = tm.getLocations();
		for(Location l: locations) {
			if(l.getLatitude()==lati && l.getLongitude()==longi) { //location exists
				return l;
			}
		}
		Location location=new Location(lati,longi); //return new one if existing one wasnt found in for loop
		tm.addLocation(location);
		return location;
				
	}
	

	public List<Municipality> findAllMunicipalities() {
		
		return tm.getMunicipalities();
	}
	public List<Tree> findAllTrees() {
		return tm.getTrees();
	}

	public User setUserType(UserType userType) {
		User user = tm.getUser();
		user.setUsertype(userType);
		return user;
	}
	/**
	 * The feature updateTreeData is intended for the user to be able to correct tree information for better accuracy. 
	 * The attributes of the tree a user can update does not affect the tree’s physical location (use moveTree for that). 
	 * @param tree tree to update
	 * @param newHeight 
	 * @param newDiameter
	 * @param newAge
	 * @param newOwnerName
	 * @param newSpecies
	 * @param newLandUse
	 * @param newMunicipality
	 */
	public void updateTreeData(Tree tree, double newHeight, double newDiameter, int newAge, String newOwnerName, 
			String newSpecies,LandUse newLandUse, Municipality newMunicipality) throws InvalidInputException{
		if(newOwnerName == null) {
			throw new InvalidInputException("Error: New owner name cannot be null.\n");
		}
		if(newHeight <0 || newDiameter <0 || newAge <0) {
			throw new InvalidInputException("Error: New height, diameter, and age cannot be negative.\n");
		}
	}
	
}
