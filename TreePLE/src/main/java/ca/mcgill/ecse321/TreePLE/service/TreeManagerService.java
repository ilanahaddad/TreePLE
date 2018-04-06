package ca.mcgill.ecse321.TreePLE.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.TreePLE.model.Location;
import ca.mcgill.ecse321.TreePLE.model.Municipality;
import ca.mcgill.ecse321.TreePLE.model.Tree;

import ca.mcgill.ecse321.TreePLE.model.Tree.LandUse;

import ca.mcgill.ecse321.TreePLE.model.Tree.Status;

import ca.mcgill.ecse321.TreePLE.model.TreeManager;
import ca.mcgill.ecse321.TreePLE.model.User;
import ca.mcgill.ecse321.TreePLE.model.User.UserType;
import ca.mcgill.ecse321.TreePLE.model.VersionManager;
import ca.mcgill.ecse321.TreePLE.persistence.PersistenceXStream;

@Service
public class TreeManagerService {
	private TreeManager tm;
	private VersionManager vm;

	public TreeManagerService(VersionManager vm) {
		List<TreeManager> treemanagers = vm.getTreeManagers();
		for(TreeManager treeM : treemanagers) {
			if(treeM.getIsCurrent()) {
				tm = treeM;
			}
		}
		this.vm = vm;
	}
	public Tree createTree(String ownerName, String species,  double height, double diameter, 
			int age, Location location, 
			Municipality municipality,  Tree.LandUse land) throws InvalidInputException{
		
		if(species==null) {
			throw new InvalidInputException("Error: Species name  cannot be null");
		}
		if(location==null) {
			throw new InvalidInputException("Error: Location cannot be null");
		}if(municipality==null) {
			throw new InvalidInputException("Error: Municipality cannot be null!");
		}
		if(species==" " ) {
			throw new InvalidInputException("Error: Species name is empty!");
		}
		if(location.hasTreeInLocation()) {
			throw new InvalidInputException("Error: There is already a tree in this location!");
		}
		Tree t = new Tree(ownerName, species, height, diameter,age, location, municipality);
		t.setLand(land);

		location.setTreeInLocation(t);
		tm.addTree(t);

		PersistenceXStream.saveToXMLwithXStream(vm);
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
		//PersistenceXStream.saveToXMLwithXStream(municipality);
		PersistenceXStream.saveToXMLwithXStream(vm);
		return municipality;
	}
	/**
	 * This feature is for users to move a tree. They choose a tree and input its new latitude and longitude 
	 * coordinates and if all inputs our correct, the tree will have this new location.
	 * @param tree
	 * @param newLatitude
	 * @param newLongitude
	 */
	public void moveTree(Tree tree, double newLatitude, double newLongitude) throws InvalidInputException{
		if(tree == null) {
			throw new InvalidInputException("Tree cannot be null. Please select a tree.\n");
		}
		/*
		//check if location already exists
		Location loc = null;
		boolean locExists = false;
		List<Location> locations = tm.getLocations();
		for(Location l: locations) {
			if(l.getLatitude()==newLatitude && l.getLongitude()==newLongitude) { //location exists
				loc = l;
				locExists = true;
			}
		}
		if(loc.hasTreeInLocation()) {
			throw new InvalidInputException("There's already a tree in this location.\n");
		}
		if(!locExists){ //create new loc if it didn't already exist in system
			loc = createLocation(newLatitude, newLongitude);
		}
*/
		Location loc = getLocationByCoordinates(newLatitude, newLongitude);
		if(loc.hasTreeInLocation()) {
			throw new InvalidInputException("There's already a tree in this location.\n");
		}
		Location oldLoc = tree.getCoordinates();
		//Erase the tree from the old location
		oldLoc.setTreeInLocation(null);
		//And update the location of the tree
		tree.setCoordinates(loc);
		PersistenceXStream.saveToXMLwithXStream(vm);
		
	}
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

	public List<Municipality> findAllMunicipalities() {

		return tm.getMunicipalities();
	}
	public List<Tree> findAllTrees() {
		return tm.getTrees();
	}
	
	public List<Tree> listTreesBySpecies(String species) throws InvalidInputException{
		if(species==null) {
			throw new InvalidInputException("Error: Species name cannot be null!");
		}
		if(species==" " ) {
			throw new InvalidInputException("Error: Species name is empty!");
		}
		List<Tree> TreeList = tm.getTrees();
		List<Tree> SpeciesList = new ArrayList<Tree>();
		for(Tree t: TreeList) {
			if(t.getSpecies()==species) { 
				SpeciesList.add(t);
			}
		}
		if (SpeciesList.size() == 0) {
			throw new InvalidInputException("Error: There are currently no such species in TreePLE!");
		}
		return SpeciesList;
	}	
	
	public List<Tree> listTreesByLandUse(LandUse landUse) throws InvalidInputException{
		if(landUse==null) {
			throw new InvalidInputException("Error: landUse cannot be null!");
		}
		List<Tree> TreeList = tm.getTrees();
		List<Tree> LandUseList = new ArrayList<Tree>();
		for(Tree t: TreeList) {
			if(t.getLand()==landUse) { 
				LandUseList.add(t);
			}
		}
		if (LandUseList.size() == 0) {
			throw new InvalidInputException("Error: There are currently no such trees with such a land use in TreePLE");
		}
		return LandUseList;
	}	

	public User setUserType(UserType userType) throws InvalidInputException {
		if(userType==null) {
			throw new InvalidInputException("Error: UserType cannot be null!");
		}
		User user = tm.getUser();
		user.setUsertype(userType); 
		PersistenceXStream.saveToXMLwithXStream(vm);
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
		if(tree == null) {
			throw new InvalidInputException("Tree cannot be null. Please select a tree.\n");
		}
		if(newHeight <0 || newDiameter <0 || newAge <0) {
			throw new InvalidInputException("New height, diameter, and age cannot be negative.\n");
		}
		if(newOwnerName == null) {
			throw new InvalidInputException("New owner name cannot be null.\n");
		}
		if(newOwnerName == "") {
			throw new InvalidInputException("New owner name cannot be empty.\n");
		}
		if(newSpecies == null) {
			throw new InvalidInputException("New species name cannot be null.\n");
		}
		if((newSpecies == "") || (newSpecies == " ")|| (newSpecies == "  ")) {
			throw new InvalidInputException("New species name cannot be empty.\n");
		}
		if(newLandUse == null) {
			throw new InvalidInputException("LandUse cannot be null. Please select a land use type.\n");
		}
		//valid chars: [65,90] [97,122]
		//invalid chars: (32, 65); (90,97); >122
		for(int i=0; i<newSpecies.length();i++) {
			char c = newSpecies.charAt(i);
			if((c >32 && c<65) ||(c>90 && c<97) || c>122 ) {
				throw new InvalidInputException("New species name cannot contain numbers or any special character.\n");
			}
		}
		tree.setHeight(newHeight);
		tree.setDiameter(newDiameter);
		tree.setAge(newAge);
		tree.setOwnerName(newOwnerName);
		tree.setSpecies(newSpecies);
		tree.setLand(newLandUse);
		tree.setTreeMunicipality(newMunicipality);
		PersistenceXStream.saveToXMLwithXStream(vm);
	}
	

	public List<Tree> listTreesByMunicipality(Municipality municipality) throws InvalidInputException{
		if(municipality==null) {
			throw new InvalidInputException("Error: Municipality entry cannot be null!");
		}

		List<Tree> TreeList = tm.getTrees();
		List<Tree> MunicipalityList = new ArrayList<Tree>();
		for(Tree t: TreeList) {
			if(t.getTreeMunicipality()==municipality) { 
				MunicipalityList.add(t);
			}
		}
		if (MunicipalityList.size() == 0) {
			throw new InvalidInputException("Error: There are currently no such Municipality in TreePLE!");
		}
		return MunicipalityList;
	}	
	public List<Tree> listTreesByStatus(Status status) throws InvalidInputException{
		if(status==null) {
			throw new InvalidInputException("Error: Status entry cannot be null!");
		}

		List<Tree> TreeList = tm.getTrees();
		List<Tree> StatusList = new ArrayList<Tree>();
		for(Tree t: TreeList) {
			if(t.getStatus()==status) { 
				StatusList.add(t);
			}
		}
		if (StatusList.size() == 0) {
			throw new InvalidInputException("Error: There are currently no trees with that status in TreePLE!");
		}
		return StatusList;
	}
	public UserType getUserTypeByName(String userTypeName) {
		UserType[] userTypes = UserType.values();
		for(UserType ut: userTypes) {
			if(userTypeName.equals(ut.toString())) {
				return ut;
			}
		}
		return null;
	}
	public List<String> getAllSpecies(){
		List<String> species = new ArrayList<String>();
		for(Tree tree: tm.getTrees()) {
			if(!species.contains(tree.getSpecies().toLowerCase())) {
				species.add(tree.getSpecies().toLowerCase());
			}
		}
		return species;
	}

}
