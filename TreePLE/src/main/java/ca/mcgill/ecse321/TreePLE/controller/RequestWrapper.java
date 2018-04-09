package ca.mcgill.ecse321.TreePLE.controller;

import java.util.List;

import ca.mcgill.ecse321.TreePLE.dto.TreeDto;

public class RequestWrapper {
	String name;
	String baseVersion;
	int futureYear;
	List<TreeDto> treesToPlantDto;
	List<Integer> treeIdsToCutDown;
	
	public String getName() {
		return this.name;
	}
	public String getBaseVersion() {
		return this.baseVersion;
	}
	public int getFutureYear() {
		return this.futureYear;
	}
	public List<TreeDto> getTreesToPlantDto(){
		return this.treesToPlantDto;
	}
	public List<Integer> getTreeIdsCutDown(){
		return this.treeIdsToCutDown;
	}
}
