package ca.mcgill.ecse321.TreePLE.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.TreePLE.dto.LocationDto;
import ca.mcgill.ecse321.TreePLE.dto.MunicipalityDto;
import ca.mcgill.ecse321.TreePLE.dto.TreeDto;
import ca.mcgill.ecse321.TreePLE.model.Forecast;
import ca.mcgill.ecse321.TreePLE.model.Location;
import ca.mcgill.ecse321.TreePLE.model.Municipality;
import ca.mcgill.ecse321.TreePLE.model.Survey;
import ca.mcgill.ecse321.TreePLE.model.SustainabilityReport;
import ca.mcgill.ecse321.TreePLE.model.Tree;
import ca.mcgill.ecse321.TreePLE.model.TreeManager;
import ca.mcgill.ecse321.TreePLE.model.VersionManager;
import ca.mcgill.ecse321.TreePLE.model.Tree.Status;

@Service
public class ForecastService {
	@Autowired
	private TreeManagerService tms;
	
	@Autowired
	private SurveyService ss;
	
	private VersionManager vm;

	public ForecastService(VersionManager vm) {
		/*List<TreeManager> treemanagers = vm.getTreeManagers();
		for(TreeManager treeM : treemanagers) {
			if(treeM.getIsCurrent()) {
				tm = treeM;
			}
		}*/
		this.vm = vm;
	}
	public Forecast createForecast(String name, String baseVersion, int futureYear,
			List<TreeDto> treesToPlant, List<Tree> treesToCutDown) throws InvalidInputException{
		List<TreeManager> treemanagers = vm.getTreeManagers();
		TreeManager baseTM = null;
		for(TreeManager tm: treemanagers) { //look for demanded base versions in TreeManagers
			if(tm.getVersion().equals(baseVersion)) {
				baseTM =tm;
				break;
			}
		}
		if(baseTM == null) { //if user inputed a version number that doesnt exist (no TM with that version)
			throw new InvalidInputException("No such base version exists.\n");
		}
		baseTM.setIsEditable(false); //disable edits to baseTM
		
		//create new forecast TM with IsEditable as true and isSelected as true
		String forecastVersion = calculateForecastVersion(baseTM);
		TreeManager forecastTM = new TreeManager(true, true, forecastVersion, futureYear, baseTM.getUser()); 
		copyAllContents(baseTM, forecastTM);//copy all data from baseTM to newTM
		plantTrees(treesToPlant);//plant new trees requested in newTM
		cutDownTrees(treesToCutDown);//cut down trees requested in newTM
		//Set new forecastTM as non editable now that trees have been planted and cut down
		forecastTM.setIsEditable(false); 
		forecastTM.setIsSelected(false); //make it false, will be set to true if selected in dropdown
		
		//calculate the new version number for duplicate TM
		String duplicateVersion = calculateDuplicateVersion(baseTM);
		//make new TM for duplicate of the baseTM: isEditable to true and isSelected to false
		TreeManager	duplicateTM = new TreeManager(true, false, duplicateVersion, baseTM.getVersionYear(), baseTM.getUser() );
		copyAllContents(baseTM, duplicateTM);//copy all data from baseTM to duplicateTM
		
		Forecast forecast = new Forecast(name,forecastVersion,futureYear); //creator, version, year
		return forecast;
	}
	public String calculateDuplicateVersion(TreeManager baseTM) {
		double baseTMVersionNum = Double.parseDouble(baseTM.getVersion());
		double newVersionNum = baseTMVersionNum + 1;
		String newVersionString = Double.toString(newVersionNum);
		return newVersionString;
	}
	public String calculateForecastVersion(TreeManager baseTM) {
		double baseTMVersionNum = Double.parseDouble(baseTM.getVersion());
		double newVersionNum = baseTMVersionNum + 0.1;
		String newVersionString = Double.toString(newVersionNum);
		return newVersionString;
	}
	public void copyAllContents(TreeManager tmToCopy, TreeManager tmCopiedTo) {
		//copy trees:
		for(Tree t: tmToCopy.getTrees()) {
			tmCopiedTo.addTree(t);
		}
		//copy locations:
		for(Location l: tmToCopy.getLocations()) {
			tmCopiedTo.addLocation(l);
		}
		//copy municipalities:
		for(Municipality m: tmToCopy.getMunicipalities()) {
			tmCopiedTo.addMunicipality(m);
		}
		//copy reports
		for(SustainabilityReport r: tmToCopy.getReports()) {
			tmCopiedTo.addReport(r);
		}
		//copy surveys:
		for(Survey s: tmToCopy.getSurveys()) {
			tmCopiedTo.addSurvey(s);
		}
	}
	public void plantTrees(List<TreeDto> treesToPlantDto) throws InvalidInputException{
		for(TreeDto treeDto: treesToPlantDto) {
			Location location = convertToDomainObject(treeDto.getCoordinates());
			Municipality municipality = convertToDomainObject(treeDto.getTreeMunicipality());
			tms.createTree(treeDto.getOwner(), treeDto.getSpecies(), treeDto.getHeight(), treeDto.getDiameter(),
					treeDto.getAge(), location, municipality, treeDto.getLand());
		}
	}
	public void cutDownTrees(List<Tree> treesToCutDown) {
		for(Tree tree: treesToCutDown) {
			tree.setStatus(Status.CutDown);
		}
	}
	private Municipality convertToDomainObject(MunicipalityDto mDto) {
		// Mapping DTO to the domain object without using the mapper
		List<Municipality> allMunicipality = tms.findAllMunicipalities();
		for (Municipality municipality : allMunicipality) {
			if (municipality.getName().equals(mDto.getName())) {
				return municipality;
			}
		}
		return null;
	}
	private Location convertToDomainObject(LocationDto lDto) {
		// Mapping DTO to the domain object without using the mapper
		List<Location> allLocations = tms.getAllLocations();
		for (Location location : allLocations) {
			if (location.getLatitude()==lDto.getLatitude() && location.getLongitude()==lDto.getLongitude()) {
				return location;
			}
		}
		return null;
	}

}
