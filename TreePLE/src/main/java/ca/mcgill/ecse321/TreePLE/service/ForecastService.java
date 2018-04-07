package ca.mcgill.ecse321.TreePLE.service;

import java.util.List;

import ca.mcgill.ecse321.TreePLE.model.Forecast;
import ca.mcgill.ecse321.TreePLE.model.TreeManager;
import ca.mcgill.ecse321.TreePLE.model.VersionManager;

public class ForecastService {
	private TreeManager tm;
	private VersionManager vm;

	public ForecastService(VersionManager vm) {
		/*List<TreeManager> treemanagers = vm.getTreeManagers();
		for(TreeManager treeM : treemanagers) {
			if(treeM.getIsCurrent()) {
				tm = treeM;
			}
		}*/
	}
	public Forecast createForecast(String baseVersion, String futureVersion, int futureYear ) throws InvalidInputException{
		List<TreeManager> treemanagers = vm.getTreeManagers();
		for(TreeManager tm: treemanagers) {
			if(tm.getVersion().equals(baseVersion)) {
				
			}
		}
		Forecast forecast = new Forecast();
		return forecast;
	}

}
