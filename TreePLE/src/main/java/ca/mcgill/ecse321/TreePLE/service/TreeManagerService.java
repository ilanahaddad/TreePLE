package ca.mcgill.ecse321.TreePLE.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.TreePLE.model.Location;
import ca.mcgill.ecse321.TreePLE.model.Municipality;
import ca.mcgill.ecse321.TreePLE.model.Survey;
import ca.mcgill.ecse321.TreePLE.model.Tree;
import ca.mcgill.ecse321.TreePLE.model.TreeManager;
import ca.mcgill.ecse321.TreePLE.model.User;
import ca.mcgill.ecse321.TreePLE.model.Version;
import ca.mcgill.ecse321.TreePLE.persistence.PersistenceXStream;

@Service
public class TreeManagerService {
	private TreeManager tm;
	
	public TreeManagerService(TreeManager tm) {
		this.tm=tm;
	}
	public Tree createTree(String species,  double height, double diameter, 
			 Location location, User owner, 
			Municipality municipality,  Tree.LandUse land) throws InvalidInputException{
		/*if(species==null || location==null || owner == null || municipality==null) {
			throw new InvalidInputException("Error: Species name, tree location, owner, municipality, or version cannot be null!");
		}*/
		if(location.hasTreeInLocation()) {
		//TODO: write test for this error message
			throw new InvalidInputException("There is already a tree in this location!");
		}
		//TODO: if owner is a local resident, check tree location is contained in owner's perimeter
		
		Version version= new Version("1.0", 2018);
		Tree t = new Tree(species, height, diameter,location, owner, municipality, version);
		t.setLand(land);

		tm.addTree(t);
		PersistenceXStream.saveToXMLwithXStream(tm);
		return t;
	}
	public Municipality createMunicipality(String name) throws InvalidInputException {
		List<Municipality> municipalities = tm.getMunicipalities();
		for(Municipality m: municipalities) {
			if(name == m.getName()) {
				throw new InvalidInputException("Error: Municipality already exists.");
			}
		}
		Municipality municipality = new Municipality(name);
		tm.addMunicipality(municipality);
		return municipality;
	}

	public Location getLocationForTree(Tree t) {
		return t.getCoordinates();
	}
	public User getOwnerForTree(Tree t) {
		return t.getOwner();
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
		return new Location(lati,longi); //return new one if existing one wasnt found in for loop
	}
	public User getOwnerByName(String name) {
		//look through all users and check if name matches
		//TODO: double check that user is a local resident, and if so, 
		//that their perimeter surrounds tree coordinates
		List<User> users = tm.getUsers();
		for(User u: users) {
			if(u.getName().equals(name)) {
				return u;
			}
		}
		return new User(name);
	}
	public List<Municipality> findAllMunicipalities() {
		
		return tm.getMunicipalities();
	}
	public List<Tree> findAllTrees() {
		
		return tm.getTrees();
	}
	
}
