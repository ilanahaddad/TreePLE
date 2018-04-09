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
	
	public void setSelectedVersion(String versionSelected) throws InvalidInputException{
		List<TreeManager> treeManagers = vm.getTreeManagers();
		boolean foundRequestedVersion = false;
		for(TreeManager tm: treeManagers) { //select new requested tm by version
			if(tm.getVersion().equals(versionSelected)) {
				foundRequestedVersion= true;
				System.out.println("found");
				tm.setIsSelected(true);
				break;
			}
		}
		if(!foundRequestedVersion) {
			throw new InvalidInputException("No such version of the system exists");
		}
		for(TreeManager tm: treeManagers) {
			if(tm.getIsSelected()) { //unselect old tm
				tm.setIsSelected(false);
				break;
			}
		}
		PersistenceXStream.saveToXMLwithXStream(vm);
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
}
