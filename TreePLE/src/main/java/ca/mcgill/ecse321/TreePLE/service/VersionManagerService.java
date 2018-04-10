package ca.mcgill.ecse321.TreePLE.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.TreePLE.model.TreeManager;
import ca.mcgill.ecse321.TreePLE.model.VersionManager;
import ca.mcgill.ecse321.TreePLE.persistence.PersistenceXStream;
@Service
public class VersionManagerService {
	private VersionManager vm;

	public VersionManagerService(VersionManager vm) {
		this.vm = vm;
	}
	
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
	public List<String> getAllVersions() {
		List<TreeManager> treeManagers = vm.getTreeManagers();
		List<String> versions = new ArrayList<String>();
		for(TreeManager tm: treeManagers) {
			versions.add(tm.getVersion());
		}
		return versions;
	}

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

	public List<TreeManager> getTreeManagers() {
		return vm.getTreeManagers();
	}
}
