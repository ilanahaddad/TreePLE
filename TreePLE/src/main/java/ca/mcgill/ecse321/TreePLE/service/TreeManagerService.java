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
/**
 * This service class contains all main functionalities of the TreeManager. 
 * @authors Ilana Haddad, Diana Serra, Jessica Udo, Thomas Hannaford
 */
@Service
public class TreeManagerService {
	private TreeManager tm;
	private VersionManager vm;
	/**
	 * The TreeManagerService constructor verifies which version has been selected by the user, 
	 * finds the TreeManager associated to that version and sets the classe's TreeManager as that one for users
	 * to edit attributes on that TreeManager.
	 * @param vm
	 */
	public TreeManagerService(VersionManager vm) {
		List<TreeManager> treemanagers = vm.getTreeManagers();
		for(TreeManager treeM : treemanagers) {
			if(treeM.getIsSelected()) {
				this.tm = treeM;
			}
		}
		this.vm = vm;
	}
	/**
	 * This method checks if the tree manager is editable to the user. Only the latest non-forecasted version is editable.
	 * @param tm current version that is selected
	 * @throws InvalidInputException input exception handling
	 */
	public void checkIfEditable(TreeManager tm)throws InvalidInputException {
		if(!tm.getIsEditable()) {
			throw new InvalidInputException("You cannot edit this version of the system");
		}
	}
	/**
	 * This method refreshes TreeLog to the selected version and checks if it is editable.
	 * @throws InvalidInputException
	 */
	public void refreshSelectedTmAndCheckIfEditable() throws InvalidInputException {
		for(TreeManager treeM : vm.getTreeManagers()) {
			if(treeM.getIsSelected()) {
				this.tm = treeM;
			}
		}
		if(!this.tm.getIsEditable()) {
			throw new InvalidInputException("You cannot edit this version of the system");
		}
	}
	/**
	 * This method only refreshes selected version
	 */
	public void refreshSelectedTM() {
		for(TreeManager treeM : vm.getTreeManagers()) {
			if(treeM.getIsSelected()) {
				this.tm = treeM;
			}
		}
	}
	/**
	 * This method creates a tree with the following attributes
	 * @param ownerName Owner's name
	 * @param species Species of the tree
	 * @param height Height of the tree in meters
	 * @param diameter Diameter of the tree canopy in meters
	 * @param age Age of the tree 
	 * @param location Location of the tree	
	 * @param municipality Municipality where the tree is located
	 * @param land Land use of the tree (Residential or non-residential)
	 * @return returns tree created
	 * @throws InvalidInputException handles input exception error
	 */
	public Tree createTree(String ownerName, String species,  double height, double diameter, 
			int age, Location location, 
			Municipality municipality,  Tree.LandUse land) throws InvalidInputException{
		//checkIfEditable(this.tm);
		refreshSelectedTmAndCheckIfEditable();
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
	/**
	 * This method creates a municipality
	 * @param name name of the municipality
	 * @return adds municipality
	 * @throws InvalidInputException
	 */
	public Municipality createMunicipality(String name) throws InvalidInputException {
		//checkIfEditable(this.tm);
		refreshSelectedTmAndCheckIfEditable();
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
		refreshSelectedTmAndCheckIfEditable();
		if(tree == null) {
			throw new InvalidInputException("Tree cannot be null. Please select a tree.\n");
		}
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
	/**
	 * This method creates a new location in TreeLog
	 * @param latitude latitude of new location
	 * @param longitude longitude of new location
	 * @return returns the location
	 * @throws InvalidInputException
	 */
	public Location createLocation(double latitude, double longitude) throws InvalidInputException{
		refreshSelectedTmAndCheckIfEditable();
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
	 * This method gets the location given the coordinates. If it does not exist it creates a new location.
	 * @param lati latitude of location
	 * @param longi longitude of location
	 * @return returns the location with the longitude and coordinates given
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
	/**
	 * This method gets the location of a given tree using tree ID
	 * @param t desired tree 
	 * @return returns the location of the tree 
	 */
	public Location getLocationForTree(Tree t) {
		return t.getCoordinates();
	}
	/**
	 * This method gets the municipality of a desired tree
	 * @param t desired tree
	 * @return returns the municipality of the tree
	 */
	public Municipality getMunicipalityForTree(Tree t) {
		return t.getTreeMunicipality();
	}
	/**
	 * This method finds the municipality given a name
	 * @param name name of the municipality
	 * @return returns the municipality whose name was called
	 */
	public Municipality getMunicipalityByName(String name) {
		List<Municipality> municipalities = tm.getMunicipalities();
		for(Municipality m: municipalities) {
			if(m.getName().equals(name)) {
				return m;

			}
		}
		return null;
	}
	/**
	 * This method gets a tree given an ID#
	 * @param id id of the tree
	 * @return returns the tree that has that ID#
	 * @throws InvalidInputException returns error if that ID# does not exist in system
	 */
	public Tree getTreeById(int id) throws InvalidInputException{
		List<Tree> trees = tm.getTrees();
		boolean treeFound = false;
		Tree tree = null;
		for(Tree t:trees) {
			if(t.getId()==id) {
				treeFound = true;
				tree = t;
				break;
			}
		}
		if(!treeFound) {
			throw new InvalidInputException("No such tree with this ID exists in the system.\n");
		}
		return tree;
	}
	/**
	 * This method lists all the municipalities in TreeLog
	 * @return returns a list of all the municipalities
	 */
	public List<Municipality> findAllMunicipalities() {
		refreshSelectedTM();
		return this.tm.getMunicipalities();
	}
	/**
	 * This method lists all the trees in TreeLog
	 * @return returns a list of all the trees
	 */
	public List<Tree> findAllTrees() {
	/*	for(TreeManager treeM : vm.getTreeManagers()) {
			if(treeM.getIsSelected()) {
				this.tm = treeM;
			}
		}*/
		refreshSelectedTM();
		return this.tm.getTrees();
	}
	/**
	 * This method lists all the trees by species
	 * @param species desired species type
	 * @return returns all the trees belonging to desired species type
	 * @throws InvalidInputException returns error if species name is null, empty or does not exist currently in TreeLog
	 */
	public List<Tree> listTreesBySpecies(String species) throws InvalidInputException{
		refreshSelectedTM();
		if(species==null) {
			throw new InvalidInputException("Error: Species name cannot be null!");
		}
		if(species==" " ) {
			throw new InvalidInputException("Error: Species name is empty!");
		}
		List<Tree> TreeList = tm.getTrees();
		List<Tree> SpeciesList = new ArrayList<Tree>();
		for(Tree t: TreeList) {
			if(t.getSpecies().equalsIgnoreCase(species)) { 
				SpeciesList.add(t);
			}
		}
		if (SpeciesList.size() == 0) {
			throw new InvalidInputException("Error: There are currently no such species in TreePLE!");
		}
		return SpeciesList;
	}	
	
	/**
	 * This method lists all the trees pertaining to a certain land use
	 * @param landUse desired land use
	 * @return returns a list of all the trees with this land use
	 * @throws InvalidInputException returns error if desired land use does not currently exist in TreeLog
	 */
	public List<Tree> listTreesByLandUse(LandUse landUse) throws InvalidInputException{
		refreshSelectedTM();
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
	/**
	 * This method assigns a type to a user (Professional or resident)
	 * @param userType type of the user (professional or resident)
	 * @return returns user
	 * @throws InvalidInputException returns input error if the type is null
	 */
	public User setUserType(UserType userType) throws InvalidInputException {
		refreshSelectedTmAndCheckIfEditable();
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
		refreshSelectedTmAndCheckIfEditable();
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
	/**
	 * This method lists trees for a certain municipality
	 * @param municipality desired municipality
	 * @return returns a list of the trees belonging to this municipality
	 * @throws InvalidInputException
	 */
	public List<Tree> listTreesByMunicipality(Municipality municipality) throws InvalidInputException{
		refreshSelectedTM();
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
	/**
	 * This method lists all the trees pertaining to a certain status
	 * @param status status of the tree (planted, to be cut-down, cut-down, diseased)
	 * @return returns all the trees with the desired status
	 * @throws InvalidInputException input error is thrown if status is null or status does not currently exist in TreeLog
	 */
	public List<Tree> listTreesByStatus(Status status) throws InvalidInputException{
		refreshSelectedTM();
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
	/**
	 * This method searches through user types to find one that matches the given name
	 * @param userTypeName
	 * @return UserType with that name if found, or null if it doesn't exist
	 */
	public UserType getUserTypeByName(String userTypeName) {
		UserType[] userTypes = UserType.values();
		for(UserType ut: userTypes) {
			if(userTypeName.equals(ut.toString())) {
				return ut;
			}
		}
		return null;
	}
	/**
	 * This method lists all the species type in TreeLog
	 * @return returns a list of the different species type
	 */
	public List<String> getAllSpecies(){
		refreshSelectedTM();
		List<String> species = new ArrayList<String>();
		for(Tree tree: tm.getTrees()) {
			if(!species.contains(tree.getSpecies().toLowerCase())) {
				species.add(tree.getSpecies().toLowerCase());
			}
		}
		return species;
	}
	/**
	 * This method lists all the statuses currently in TreeLog	
	 * @return returns a list of all the different statuses in the system
	 */
	public List<Tree.Status> getAllStatuses(){
		List<Tree.Status> statusesList = new ArrayList<Tree.Status>();
		Tree.Status[] statusesArray = Tree.Status.values();
		for(int i = 0; i<statusesArray.length;i++) {
			statusesList.add(statusesArray[i]);
		}
		return statusesList;
	}
	/**
	 * This method lists all the land use types
	 * @return returns a list of all the different land use types in the system
	 */
	public List<LandUse> getAllLandUseTypes() {
		List<Tree.LandUse> landUseTypesList = new ArrayList<Tree.LandUse>();
		Tree.LandUse[] landUseTypesArray = Tree.LandUse.values();
		for(int i = 0; i<landUseTypesArray.length;i++) {
			landUseTypesList.add(landUseTypesArray[i]);
		}
		return landUseTypesList;
	}
	/**
	 * This method lists all the tree locations currently in TreeLog
	 * @return returns a list of all the locations
	 */
	public List<Location> getAllLocations(){
		refreshSelectedTM();
		return tm.getLocations();
	}
	
	public List<User.UserType> getUserTypes() {
		List<User.UserType> userTypesList = new ArrayList<User.UserType>();
		User.UserType[] userTypesArray = User.UserType.values();
		for(int i = 0; i<userTypesArray.length;i++) {
			userTypesList.add(userTypesArray[i]);
		}
		return userTypesList;
	}
	/**
	 * This method allows for the user to set/change the status of a tree
	 * @param tree desired tree
	 * @param newStatus the new status of the tree (planted, diseased, to be cut-down, cut-down)
	 * @throws InvalidInputException
	 */
	public void setStatus(Tree tree, Tree.Status newStatus) throws InvalidInputException{
		refreshSelectedTmAndCheckIfEditable();
		tree.setStatus(newStatus);
		PersistenceXStream.saveToXMLwithXStream(vm);
	}


}
