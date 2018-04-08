package ca.mcgill.ecse321.TreePLE.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.TreePLE.model.TreeManager;
import ca.mcgill.ecse321.TreePLE.model.VersionManager;
@Service
public class VersionManagerService {
	private VersionManager vm;

	public VersionManagerService(VersionManager vm) {
		this.vm = vm;
	}
	
	public void setSelectedVersion(String versionSelected) {
		
	}
	public List<String> getAllVersions() {
		List<TreeManager> treeManagers = vm.getTreeManagers();
		List<String> versions = new ArrayList<String>();
		for(TreeManager tm: treeManagers) {
			versions.add(tm.getVersion());
		}
		return versions;
	}
}
