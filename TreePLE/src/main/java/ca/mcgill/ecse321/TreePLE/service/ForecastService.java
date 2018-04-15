package ca.mcgill.ecse321.TreePLE.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import ca.mcgill.ecse321.TreePLE.persistence.PersistenceXStream;
/**
 * This service class contains all main functionalities related to Forecasts.
 * @author Ilana Haddad
 */
@Service
public class ForecastService {
	@Autowired
	private TreeManagerService tms;
	
	private VersionManager vm;
	/**
	 * The VersionManagerService constructor sets the class's VersionManager to the system's VersionManager. 
	 * @param vm
	 */
	public ForecastService(VersionManager vm) {
		this.vm = vm;
	}
	
	/**
	 * This method creates a forecast given a list of trees to plant and trees to cut down.
	 * It will create two new TreeManager's to add to the system: a forecast version, and a duplicate of the base version.
	 * @param name of person creating the forecast
	 * @param baseVersion for forecast
	 * @param futureYear year of future version
	 * @param treesToPlant list of trees to plant in the new forecast version
	 * @param treesToCutDown list of trees to cut down in the new forecast version
	 * @return Forecast created
	 * @throws InvalidInputException
	 */
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
		if(baseIsForecast(baseTM)) {
			throw new InvalidInputException("The system does not support making a forecast of a forecast.\n");
		}
		baseTM.setIsEditable(false); //disable edits to baseTM
		baseTM.setIsSelected(false); //disable edits to baseTM
		//create new forecast TM with IsEditable as true and isSelected as true
		String forecastVersion = calculateForecastVersion(baseTM);
		TreeManager forecastTM = new TreeManager(true, true, forecastVersion, futureYear, baseTM.getUser()); 
		copyAllContents(baseTM, forecastTM);//copy all data from baseTM to newTM
		vm.addTreeManager(forecastTM);
		
		
		if(treesToPlant!=null) {
			plantTrees(treesToPlant);//plant new trees requested in newTM
		}
		if(treesToCutDown!=null) {
			cutDownTrees(treesToCutDown, baseTM, forecastTM);//cut down trees requested in newTM
		}
		
		//Set new forecastTM as non editable now that trees have been planted and cut down
		forecastTM.setIsEditable(false); 
		forecastTM.setIsSelected(false); //make it false, will be set to true if selected in dropdown
		
		//MAKE DUPLICATE ONLY IF THIS IS THE FORECAST ON THE BASE TM
		double forecastVersionNum = Double.parseDouble(forecastVersion);
		double baseTMNum = Double.parseDouble(baseTM.getVersion());
		if(forecastVersionNum == baseTMNum+0.1) { //ex: if we just created 1.1 from base 1.0
			//calculate the new version number for duplicate TM
			String duplicateVersion = calculateDuplicateVersion(baseTM);
			//make new TM for duplicate of the baseTM: isEditable to true and isSelected to false
			TreeManager	duplicateTM = new TreeManager(true, false, duplicateVersion, baseTM.getVersionYear(), baseTM.getUser() );
			copyAllContents(baseTM, duplicateTM);//copy all data from baseTM to duplicateTM
			vm.addTreeManager(duplicateTM);
		}
		
		Forecast forecast = new Forecast(name,forecastVersion,futureYear); //creator, version, year
		vm.addTreeManager(forecastTM);
		PersistenceXStream.saveToXMLwithXStream(vm);
		return forecast;
	} 
	/**
	 * This method checks if the base treeManager a forecast has been requested on is already a forecast.
	 * It checks this by checking if the decimal of the version number is anything other than 0.
	 * Our forecast versions all have X.1 or X.2, etc. A version that has X.0 is a base version and is not a forecast
	 * @param baseTM base TreeManager requested for forecast
	 * @return true or false depending on whether the requested base is already a forecast
	 */
	public boolean baseIsForecast(TreeManager baseTM) {
		boolean isForecast = false;
		double baseTMNum = Double.parseDouble(baseTM.getVersion()); //ex: 2.1
		int int_base = (int) baseTMNum; //ex: 2
		double dec_base = baseTMNum - int_base; //ex: 2.1 - 2 = 0.1
		if(dec_base !=0) { //if 0.1, then its a forecast
			isForecast = true;
		}
		return isForecast;
	}
	/**
	 * This is a helper method that calculated the version number of the duplicate TreeManager
	 * For example, if a forecast on base 1.0 is requested, the duplicate version number is 2.0 
	 * This duplicate version is only created for the first forecast of a base.
	 * For example it is created if version 1.0 has no forecasts 
	 * (e.g. when creating version 1.1, version 2.0 is created. but for version 1.2, since 2.0 already exists, it is not created again).
	 * this duplicate version is created in order to be the new system's version that is editable by everyone. 
	 * If a forecast on version 1.0 is created, version 1.0 is no longer editable, so we make an exact copy of it, called
	 * 2.0 and that is the new version of the forecast.
	 * @param baseTM
	 * @return duplicate version number cast as a String
	 */
	public String calculateDuplicateVersion(TreeManager baseTM) {
		double baseTMVersionNum = Double.parseDouble(baseTM.getVersion());
		double newVersionNum = baseTMVersionNum + 1;
		String newVersionString = Double.toString(newVersionNum);
		return newVersionString;
	}
	/**
	 * This helper method calculates the version number for a forecast.
	 * For example, if baseTM version is 2.0, then the forecast version must be 2.1
	 * In the event where 2.1, 2.2, 2.3 are already created, we must be creating the version 2.4,
	 * I calculate in this method what the highest forecast version is from 2.0 to 3.0 (not included)
	 * in order to determine the next verison number of the forecast.
	 * @param baseTM
	 * @return forecast version number cast as a String
	 * @throws InvalidInputException
	 */
	public String calculateForecastVersion(TreeManager baseTM) throws InvalidInputException{
		List<TreeManager> treeManagers = vm.getTreeManagers();
		double baseTMVersionNum = Double.parseDouble(baseTM.getVersion());
		//if baseTM is 1.0, find biggest forecast TM first (1.1 or 1.2 if they exist) 
		
		//MAKE LIST OF ALL FORECASTS FROM BASEVERSION TO BASEVERSION+1 (1.0 TO 2.0 for example):
		List<TreeManager> forecastVersionsOfBaseTM = new ArrayList<TreeManager>();
		for(TreeManager tm: treeManagers) {
			double tmNum =  Double.parseDouble(tm.getVersion());
			if(tmNum >= baseTMVersionNum && tmNum < baseTMVersionNum+1) { //ex: [1.0,2.0)
				forecastVersionsOfBaseTM.add(tm);
			}
		}
		//FIND MAX FORECAST IN THAT RANGE:
		double maxForecastVersion = baseTMVersionNum;
		for(TreeManager tmInRange: forecastVersionsOfBaseTM) {
			double tmInRangeNum =  Double.parseDouble(tmInRange.getVersion());
			if (tmInRangeNum > maxForecastVersion) {
				maxForecastVersion = tmInRangeNum;
			}
		}
		if(maxForecastVersion == 0.9) {
			throw new InvalidInputException("You're reached the maximal number of forecasts you can make using this as a base.\n");
		}
		double newVersionNum = maxForecastVersion + 0.1;
		newVersionNum = Math.floor(newVersionNum * 10) / 10;
		String newVersionString = Double.toString(newVersionNum);
		return newVersionString;
	}
	/**
	 * This method copies all contents from one TreeManager to the next.
	 * This method is used when creating the new TreeManagers, they must all have the same base and then depending on 
	 * whether is it a forecast or a duplicate, we can add datan to the copies of the TreeManagers
	 * @param tmToCopy TreeManager we want to copy all data of
	 * @param tmCopiedTo empty TreeManager we want all data copied to
	 */
	public void copyAllContents(TreeManager tmToCopy, TreeManager tmCopiedTo) {
		//copy trees:
		for(Tree t: tmToCopy.getTrees()) {
			Location newL = new Location(t.getCoordinates().getLatitude(), t.getCoordinates().getLongitude());
			Tree newT = new Tree(t.getOwnerName(), t.getSpecies(), t.getHeight(), t.getDiameter(), t.getAge(), newL, t.getTreeMunicipality());
			newT.setLand(t.getLand());
			tmCopiedTo.addTree(newT);
		}
		//copy locations:
		for(Location l: tmToCopy.getLocations()) {
			//Location newL = new Location(l.getLatitude(), l.getLongitude());
			tmCopiedTo.addLocation(l);
		}
		//copy municipalities:
		for(Municipality m: tmToCopy.getMunicipalities()) {
			//Municipality newM = new Municipality(m.getName());
			tmCopiedTo.addMunicipality(m);
		}
		//copy reports
		for(SustainabilityReport r: tmToCopy.getReports()) {
			//SustainabilityReport newR = new SustainabilityReport(r.getReporterName(), r.getDate(), r.getReportPerimeter());
			tmCopiedTo.addReport(r);
		}
		//copy surveys:
		for(Survey s: tmToCopy.getSurveys()) {
			tmCopiedTo.addSurvey(s);
		}
	}
	/**
	 * This method is used to plant trees in the forecast TreeManager version if the user has requested it when creating the forecast
	 * @param treesToPlantDto
	 * @throws InvalidInputException
	 */
	public void plantTrees(List<TreeDto> treesToPlantDto) throws InvalidInputException{
		tms = new TreeManagerService(vm);
		for(TreeDto treeDto: treesToPlantDto) {
			Location location = tms.getLocationByCoordinates(treeDto.getCoordinates().getLatitude(), treeDto.getCoordinates().getLongitude());
			Municipality municipality = tms.getMunicipalityByName(treeDto.getTreeMunicipality().getName());
			tms.createTree(treeDto.getOwnerName(), treeDto.getSpecies(), treeDto.getHeight(), treeDto.getDiameter(),
					treeDto.getAge(), location, municipality, treeDto.getLand());
		}
		
	}
	/**
	 * This method is used to cut down trees in the forecast TreeManager version if the user has requested it when creating the forecast
	 * @param treesToCutDown
	 * @param baseTM
	 * @param forecastTM
	 * @throws InvalidInputException
	 */
	public void cutDownTrees(List<Tree> treesToCutDown, TreeManager baseTM, TreeManager forecastTM) throws InvalidInputException{
		/*Right now the list of trees refers to trees in the baseTM, if we edit those it will change the
		 * trees in the baseTM. Instead we want to change the ones in the forecastTM. 
		 * Therefore, we will have to find the exact copies of trees in the newTM and refer
		 * to those when editing. 
		 */
		for(Tree tree: treesToCutDown) {
			Tree treeToEdit = findExactTreeInForecastTM(tree, baseTM, forecastTM);
			tms.setStatus(treeToEdit, Status.CutDown);
		}
		
	}
	/**
	 * This method is used in the method cutDownTrees in order to convert a tree from the baseTM to the forecastTM
	 * @param tree
	 * @param baseTM
	 * @param forecastTM
	 * @return Tree from the forecast TreeManager
	 */
	public Tree findExactTreeInForecastTM(Tree tree, TreeManager baseTM, TreeManager forecastTM) {
		//Tree treeInForecastTM = null;
		for(Tree tInBase: baseTM.getTrees()) {
			double baseTreeLat = tInBase.getCoordinates().getLatitude();
			double baseTreeLong = tInBase.getCoordinates().getLongitude();
			for(Tree tInForecast: forecastTM.getTrees()) {
				double forecastTreeLat = tInForecast.getCoordinates().getLatitude();
				double forecastTreeLong = tInForecast.getCoordinates().getLongitude();
				if(baseTreeLat==forecastTreeLat && baseTreeLong==forecastTreeLong) {
					return tInForecast;
				}
			}
		}
		return null;
	}
}
