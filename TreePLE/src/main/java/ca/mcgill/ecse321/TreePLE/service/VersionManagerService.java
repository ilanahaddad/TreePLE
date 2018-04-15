package ca.mcgill.ecse321.TreePLE.service;

import java.util.ArrayList;

import java.util.List;

import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.TreePLE.model.TreeManager;
import ca.mcgill.ecse321.TreePLE.model.VersionManager;
import ca.mcgill.ecse321.TreePLE.persistence.PersistenceXStream;
/**
 * This service class contains all main functionalities that have to do with changing the system's versions
 * @author Ilana Haddad
 */
@Service
public class VersionManagerService {
	private VersionManager vm;
	
	/**
	 * The VersionManagerService constructor sets the classe's vm to the system's vm. 
	 * @param vm
	 */
	public VersionManagerService(VersionManager vm) {
		this.vm = vm;
	}
	
	/**
	 * This method changes the attribute isSelected of all TreeManagers in the system in order
	 * for only the version selected's TreeManager to have it as true, and all other ones as false.
	 * @param versionSelected String with version number selected by user
	 * @return String of versionSelected as a success check that the update went through
	 * @throws InvalidInputException if no version of the system exists
	 */
	public String setSelectedVersion(String versionSelected) throws InvalidInputException{
		List<TreeManager> treeManagers = vm.getTreeManagers();
		boolean foundRequestedVersion = false;
		String newVersionSelected = "";
		TreeManager tmSelected = null;
		for(TreeManager tm: treeManagers) { //select new requested tm by version
			if(tm.getVersion().equals(versionSelected)) {
				foundRequestedVersion= true;
				tmSelected= tm;
				tm.setIsSelected(true);
				break;
			}
		}
		if(!foundRequestedVersion) {
			throw new InvalidInputException("No such version of the system exists");
		}
		for(TreeManager tm: treeManagers) {
			if(!tm.equals(tmSelected)) { //set all other tms to not selected
				tm.setIsSelected(false);
			}
		}
		//return the one we want selected:
		for(TreeManager tm: treeManagers) {
			if(tm.getIsSelected()) {//ensure the one we want is the only one selected
				newVersionSelected = tm.getVersion();
				//break;
			}
		}
		PersistenceXStream.saveToXMLwithXStream(vm);
		return newVersionSelected;
	}
	/**
	 * This method returns all versions of the system
	 * @return List<String> of all version numbers associated to all TreeManagers of the system
	 */
	public List<String> getAllVersions() {
		List<TreeManager> treeManagers = vm.getTreeManagers();
		List<String> versions = new ArrayList<String>();
		for(TreeManager tm: treeManagers) {
			versions.add(tm.getVersion());
		}
		return versions;
	}
	/**
	 * This method returns the year associated to the TreeManager version selected
	 * @return year of system
	 */
	public int getCurrentVersionYear() {
		List<TreeManager> treeManagers = vm.getTreeManagers();
		int curVersionYear = 0;
		for(TreeManager tm: treeManagers) {
			if(tm.getIsSelected()) {
				curVersionYear = tm.getVersionYear();
				break;
			}
		}
		return curVersionYear;
	}
	/**
	 * This method returns the version number associated to the TreeManager version selected
	 * @return version number
	 */
	public String getCurrentVersionNumber() {
		List<TreeManager> treeManagers = vm.getTreeManagers();
		String curVersionNumber = null;
		for(TreeManager tm: treeManagers) {
			if(tm.getIsSelected()) {
				curVersionNumber = tm.getVersion();
				break;
			}
		}
		return curVersionNumber;
	}
	/**
	 * This method returns all the TreeManagers of the system
	 * @return List<TreeManager>
	 */
	public List<TreeManager> getTreeManagers() {
		return vm.getTreeManagers();
	}
}
