package ca.mcgill.ecse321.TreePLE.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.TreePLE.dto.ForecastDto;
import ca.mcgill.ecse321.TreePLE.dto.LocationDto;
import ca.mcgill.ecse321.TreePLE.dto.MunicipalityDto;
import ca.mcgill.ecse321.TreePLE.dto.SurveyDto;
import ca.mcgill.ecse321.TreePLE.dto.SustainabilityReportDto;
import ca.mcgill.ecse321.TreePLE.dto.TreeDto;
import ca.mcgill.ecse321.TreePLE.model.Forecast;
import ca.mcgill.ecse321.TreePLE.model.Location;
import ca.mcgill.ecse321.TreePLE.model.Municipality;
import ca.mcgill.ecse321.TreePLE.model.Survey;
import ca.mcgill.ecse321.TreePLE.model.SustainabilityReport;
import ca.mcgill.ecse321.TreePLE.model.Tree;
import ca.mcgill.ecse321.TreePLE.model.Tree.LandUse;
import ca.mcgill.ecse321.TreePLE.model.Tree.Status;
import ca.mcgill.ecse321.TreePLE.model.User;
import ca.mcgill.ecse321.TreePLE.model.User.UserType;
import ca.mcgill.ecse321.TreePLE.service.ForecastService;
import ca.mcgill.ecse321.TreePLE.service.InvalidInputException;
import ca.mcgill.ecse321.TreePLE.service.ReportService;
import ca.mcgill.ecse321.TreePLE.service.SurveyService;
import ca.mcgill.ecse321.TreePLE.service.TreeManagerService;
import ca.mcgill.ecse321.TreePLE.service.VersionManagerService;

/**
 * This class is what connects our FrontEnd to our BackEnd. It handles all REST services requests made by the user
 * and calls the corresponding service method needed to complete the request. It uses Dto's in order to never expose
 * the frontend to our main model classes. 
 * @author Ilana Haddad
 *
 */
@RestController
public class TreeManagerRestController {
	@Autowired
	private TreeManagerService treeManagerService;
	
	@Autowired
	private SurveyService surveyService;
	
	@Autowired
	private ReportService reportService;
	
	@Autowired
	private VersionManagerService versionManagerService;
	
	@Autowired
	private ForecastService forecastService;

	@Autowired
	private ModelMapper modelMapper;

	@RequestMapping("/")
	public String index() {
		return "TreePLE application root.\n";
	}

	// Conversion methods (not part of the API)
	/**
	 * Converts a Tree to a TreeDto using a model mapper
	 * @param t Tree to convert
	 * @return TreeDto of given tree
	 */
	private TreeDto convertToDto(Tree t) {
		// In simple cases, the mapper service is convenient
		TreeDto treeDto = modelMapper.map(t, TreeDto.class);
		treeDto.setMunicipality(createMunicipalityDtoForTree(t));
		treeDto.setLocation(createLocationDtoForTree(t));
		return treeDto;
	}
	
	/**
	 * Converts a Forecast to a ForecastDto using a model mapper
	 * @param forecast to convert
	 * @return ForecastDto of given forecast
	 */
	private ForecastDto convertToDto(Forecast forecast) {
		return modelMapper.map(forecast, ForecastDto.class);

	}
	
	/**
	 * Converts a SustainabilityReport to a SustainabilityReportDto using a model mapper
	 * @param report to convert
	 * @return SustainabilityReportDto of given SustainabilityReport
	 */
	private SustainabilityReportDto convertToDto(SustainabilityReport report) {
		SustainabilityReportDto reportDto = modelMapper.map(report, SustainabilityReportDto.class);
		reportDto.setBiodiversityIndex(report.getBiodiversityIndex());
		reportDto.setCanopy(report.getCanopy());
		reportDto.setCarbonSequestration(report.getCarbonSequestration());
		return reportDto;
	}
	
	/**
	 * Converts a Location to a LocationDto using a model mapper
	 * @param location to convert
	 * @return LocationDto of given location
	 */
	private LocationDto convertToDto(Location location) {
		return modelMapper.map(location, LocationDto.class);

	}
	/**
	 * Converts a Municipality to a MuniciaplityDto using a model mapper
	 * @param mun municipality to convert
	 * @return MunicipalityDto of given municipality
	 */
	private MunicipalityDto convertToDto(Municipality mun) {
		return modelMapper.map(mun, MunicipalityDto.class);

	}
	/**
	 * Converts a Survey to a SurveyDto using a model mapper
	 * @param survey to convert
	 * @return SurveyDto of given survey
	 */
	private SurveyDto convertToDto(Survey survey) {
		SurveyDto surveyDto= modelMapper.map(survey, SurveyDto.class);
		surveyDto.setTree(createTreeDtoForSurvey(survey));
		
		return surveyDto;
	}
	/**
	 * This method calls the service method that gets a location for a given tree and converts the returned location to a locationDto
	 * @param t tree we want the location of
	 * @return LocationDto of the tree
	 */
	private LocationDto createLocationDtoForTree(Tree t) {
		Location locationForTree = treeManagerService.getLocationForTree(t);
		return convertToDto(locationForTree);
	}

	/**
	 * This method calls the service method that gets a municipality for a given tree 
	 * and converts the returned municipality to a municipalityDto
	 * @param t tree we want the municipality of
	 * @return MuncipalityDto of the tree
	 */
	private MunicipalityDto createMunicipalityDtoForTree(Tree t) {
		Municipality municipalityForTree = treeManagerService.getMunicipalityForTree(t);
		return convertToDto(municipalityForTree);
	}
	/**
	 * This method calls the service method that gets a tree for a given survey 
	 * and converts the returned tree to a treeDto
	 * @param survey we want the tree for
	 * @return TreeDto of the survey
	 */
	private TreeDto createTreeDtoForSurvey(Survey survey) {
		Tree treeForSurvey = surveyService.getTreeForSurvey(survey);
		return convertToDto(treeForSurvey);
	}
	/**
	 * This method converts a municipalityDto to a municipality. It does so looking at whether the municipality names are the same
	 * @param mDto to convert
	 * @return municipality 
	 */
	private Municipality convertToDomainObject(MunicipalityDto mDto) {
		// Mapping DTO to the domain object without using the mapper
		List<Municipality> allMunicipality = treeManagerService.findAllMunicipalities();
		for (Municipality municipality : allMunicipality) {
			if (municipality.getName().equals(mDto.getName())) {
				return municipality;
			}
		}
		return null;
	}
	/**
	 * This method is mapped to the POST request URI to create a tree. It calls the service method createTree
	 * and converts its result to a Dto before returning it to the frontend. 
	 * @param species
	 * @param height
	 * @param diameter
	 * @param munDto
	 * @param latitude
	 * @param longitude
	 * @param ownerName
	 * @param age
	 * @param landuse
	 * @return TreeDto
	 * @throws InvalidInputException
	 */
	@PostMapping(value = {"/newTree/{species}", "/newTree/{species}/"})
	public TreeDto createTree(
			@PathVariable("species") String species,
			@RequestParam(name = "height") double height, 
			@RequestParam(name = "diameter") double diameter,
			@RequestParam(name= "municipality") MunicipalityDto munDto,
			@RequestParam(name="latitude") double latitude,
			@RequestParam(name="longitude") double longitude,
			@RequestParam(name="owner") String ownerName,
			@RequestParam(name="age") int age, 
			@RequestParam(name="landuse") Tree.LandUse landuse ) throws InvalidInputException {
		Location location = treeManagerService.getLocationByCoordinates(latitude, longitude);
		Municipality municipality = treeManagerService.getMunicipalityByName(munDto.getName());
		Tree t = treeManagerService.createTree(ownerName, species, height, diameter, age, location, municipality, landuse);
		return convertToDto(t);
	}
	/**
	 * This method is mapped to the POST request URI to create a survey. It calls the service method createSurvey
	 * and converts its result to a Dto before returning it to the frontend. 
	 * @param reportDate
	 * @param treeID
	 * @param surveyor
	 * @param newTreeStatus
	 * @return SurveyDto
	 * @throws InvalidInputException
	 */
	@PostMapping(value = {"/newSurvey", "/newSurvey/"})
	public SurveyDto createSurvey(
			@RequestParam Date reportDate,
			//@RequestParam(name = "tree") TreeDto treeDto, 
			@RequestParam(name = "tree") int treeID, 
			@RequestParam(name = "surveyor") String surveyor,
			@RequestParam(name = "newTreeStatus") Status newTreeStatus
			) throws InvalidInputException{
		
		Tree tree = surveyService.getTreeById(treeID);
		Survey s = surveyService.createSurvey(reportDate, tree,surveyor, newTreeStatus);
		return convertToDto(s);
	}
	/**
	 * This method is mapped to the POST request URI to create a municipality. It calls the service method createMunicipality
	 * and converts its result to a Dto before returning it to the frontend. 
	 * @param name
	 * @return MunicipalityDto
	 * @throws InvalidInputException
	 */
	@PostMapping(value = {"/newMunicipality/{name}", "/newMunicipality/{name}/"})
	public MunicipalityDto createMunicipality(@PathVariable("name") String name) throws InvalidInputException {
		Municipality m = treeManagerService.createMunicipality(name);
		return convertToDto(m);
	}
	/**
	 * This method is mapped to the GET request URI to list all the municipalities. It calls the service method findAllMunicipalities
	 * and converts its result to a list of Dto's before returning it to the frontend. 
	 * @return List<MunicipalityDto>
	 */
	@GetMapping(value = { "/municipalities/", "/municipalities" })
	public List<MunicipalityDto> findAllMunicipalities() {
		List<MunicipalityDto> municipalities = new ArrayList<MunicipalityDto>();
		for(Municipality m: treeManagerService.findAllMunicipalities()) {
			municipalities.add(convertToDto(m));
		}
		return municipalities;
	}
	/**
	 * This method is mapped to the GET request URI to list all the trees. It calls the service method findAllTrees
	 * and converts its result to a list of Dto's before returning it to the frontend. 
	 * @return List<TreeDto>
	 */
	@GetMapping(value = { "/trees/", "/trees" })
	public List<TreeDto> findAllTrees() {
		List<TreeDto> treeDtos = new ArrayList<TreeDto>();
		List<Tree> trees = treeManagerService.findAllTrees();
		for(Tree t: trees) {
			treeDtos.add(convertToDto(t));
		}
		return treeDtos;
	}
	/**
	 * This method is mapped to the POST request URI to set a user type. It calls the service method setUserType
	 * @param userTypeName
	 * @throws InvalidInputException
	 */
	@PostMapping(value = { "/setUserType/{userTypeName}/", "/setUserType/{userTypeName}" })
	public void setUserType(@PathVariable("userTypeName") String userTypeName) throws InvalidInputException{
		UserType userType= treeManagerService.getUserTypeByName(userTypeName);
		treeManagerService.setUserType(userType);
	}
	/**
	 * This method is mapped to the POST request URI to generate a sustainability report. It calls the service method createReport
	 * and converts its result to a Dto before returning it to the frontend. 
	 * @param reporterName
	 * @param reportDate
	 * @param lat1
	 * @param long1
	 * @param lat2
	 * @param long2
	 * @param lat3
	 * @param long3
	 * @param lat4
	 * @param long4
	 * @return SustainabilityReportDto
	 * @throws InvalidInputException
	 */
	@PostMapping(value = { "/newReport/{reporterName}/", "/newReport/{reporterName}" })
	public SustainabilityReportDto generateSustainabilityReport(@PathVariable("reporterName") String reporterName,
		@RequestParam Date reportDate,
		@RequestParam(name="lat1") double lat1,
		@RequestParam(name="long1") double long1,
		@RequestParam(name="lat2") double lat2,
		@RequestParam(name="long2") double long2,
		@RequestParam(name="lat3") double lat3,
		@RequestParam(name="long3") double long3,
		@RequestParam(name="lat4") double lat4,
		@RequestParam(name="long4") double long4) throws InvalidInputException {
		Location location1 = reportService.getLocationByCoordinates(lat1, long1);
		Location location2 = reportService.getLocationByCoordinates(lat2, long2);
		Location location3 = reportService.getLocationByCoordinates(lat3, long3);
		Location location4 = reportService.getLocationByCoordinates(lat4, long4);
		Location[] perimeter = new Location[4];
		perimeter[0] = location1;
		perimeter[1] = location2;
		perimeter[2] = location3;
		perimeter[3] = location4;
		SustainabilityReport report = reportService.createReport(reporterName, reportDate, perimeter);
		return convertToDto(report);
	}
	/**
	 * This method is mapped to the request URI to list all the species. It calls the service method getAllSpecies
	 * @return List<String> list of species in the system
	 */
	@GetMapping(value = { "/species/", "/species" })
	public List<String> findAllSpecies() {
		return treeManagerService.getAllSpecies();
	}
	/**
	 * This method is mapped to the GET request URI to list all the trees with a certain municipality. 
	 * It calls the service method listTreesByMunicipality and converts its result to a list of Dto's before returning it to the frontend. 
	 * @param mDto municipalityDto for which we need all trees of
	 * @return List<TreeDto> that have mDto as a municipality
	 * @throws InvalidInputException
	 */
	@GetMapping(value = { "/treesByMunicipality/{municipality}", "/treesByMunicipality/{municipality}/" })
	public List<TreeDto> listTreesByMunicipality(@PathVariable("municipality") MunicipalityDto mDto) throws InvalidInputException{
		Municipality mun = convertToDomainObject(mDto);
		List<Tree> TreebyMunList = treeManagerService.listTreesByMunicipality(mun);
		List<TreeDto> TreebyMunListDto = new ArrayList<TreeDto>();
		for(Tree t: TreebyMunList) {
			TreebyMunListDto.add(convertToDto(t));	
		}
		return TreebyMunListDto;

	}
	/**
	 * This method is mapped to the GET request URI to list all the trees with a certain status. 
	 * It calls the service method listTreesByStatus and converts its result to a list of Dto's before returning it to the frontend. 
	 * @param status for which we need all trees of 
	 * @return List<TreeDto> that have status as a status
	 * @throws InvalidInputException
	 */
	@GetMapping(value = { "/treesByStatus/{status}", "/treesByStatus/{status}/" })
	public List<TreeDto> listTreesByStatus(@PathVariable("status") Status status) throws InvalidInputException{
		List<Tree> TreesByStatusList = treeManagerService.listTreesByStatus(status);
		List<TreeDto> TreesByStatusListDto = new ArrayList<TreeDto>();
		for(Tree t: TreesByStatusList) {
			TreesByStatusListDto.add(convertToDto(t));
		}
		return TreesByStatusListDto;

	}
	/**
	 * This method is mapped to the GET request URI to list all the trees with a certain species. 
	 * It calls the service method listTreesBySpecies and converts its result to a list of Dto's before returning it to the frontend. 
	 * @param species for which need all trees of
	 * @return List<TreeDto> that have species as a species
	 * @throws InvalidInputException
	 */
	@GetMapping(value = { "/treesBySpecies/{species}", "/treesBySpecies/{species}/" })
	public List<TreeDto> listTreesBySpecies(@PathVariable("species") String species) throws InvalidInputException{
		List<Tree> TreesBySpeciesList = treeManagerService.listTreesBySpecies(species);
		List<TreeDto> TreesBySpeciesListDto = new ArrayList<TreeDto>();
		for(Tree t: TreesBySpeciesList) {
			TreesBySpeciesListDto.add(convertToDto(t));
		}
		return TreesBySpeciesListDto;
	}
	/**
	 * This method is mapped to the GET request URI to list all the trees with a certain land use type. 
	 * It calls the service method listTreesByLandUse and converts its result to a list of Dto's before returning it to the frontend. 
	 * @param landUse
	 * @return List<TreeDto> that have landUse as a landUse type
	 * @throws InvalidInputException
	 */
	@GetMapping(value = { "/treesByLandUse/{land}", "/treesByLandUse/{land}/" })
	public List<TreeDto> listTreesByLandUse(@PathVariable("land") LandUse landUse) throws InvalidInputException{
		List<Tree> TreesByLandUseList = treeManagerService.listTreesByLandUse(landUse);
		List<TreeDto> TreesByLandUseListDto = new ArrayList<TreeDto>();
		for(Tree t: TreesByLandUseList) {
			TreesByLandUseListDto.add(convertToDto(t));
		}
		return TreesByLandUseListDto;

	}
	/**
	 * This method is mapped to the POST request URI to move a tree. It calls the service method moveTree given the 
	 * new latitude and longitude and the treeID for which to change the location of. 
	 * @param treeID
	 * @param newLat
	 * @param newLong
	 * @throws InvalidInputException
	 */
	@PostMapping(value = { "/moveTree/{id}", "/moveTree/{id}/" })
	public void moveTree(@PathVariable("id") int treeID,
			//@RequestParam(name = "tree") int treeID, 
			@RequestParam(name = "latitude") double newLat,
			@RequestParam(name = "longitude") double newLong) throws InvalidInputException{
		Tree tree = treeManagerService.getTreeById(treeID);
		treeManagerService.moveTree(tree, newLat, newLong);
	}
	/**
	 * This method is mapped to the POST request URI to update a tree's data. It calls the service method updateTreeData
	 * and converts its result to a Dto before returning it to the frontend. 
	 * @param treeID
	 * @param newHeight
	 * @param newDiameter
	 * @param newAge
	 * @param newOwnerName
	 * @param newSpecies
	 * @param newLandUse
	 * @param newMunDto
	 * @return
	 * @throws InvalidInputException
	 */
	@PostMapping(value = { "/updateTreeData/{id}", "/updateTreeData/{id}/" })
	public TreeDto updateTreeData(@PathVariable("id") int treeID,
			@RequestParam(name = "newHeight") double newHeight,
			@RequestParam(name = "newDiameter") double newDiameter,
			@RequestParam(name = "newAge") int newAge,
			@RequestParam(name = "newOwnerName") String newOwnerName,
			@RequestParam(name = "newSpecies") String newSpecies,
			@RequestParam(name = "newLandUse") LandUse newLandUse,
			@RequestParam(name = "newMunicipality") MunicipalityDto newMunDto) throws InvalidInputException{
		Municipality newMunicipality = convertToDomainObject(newMunDto);
		Tree tree = treeManagerService.getTreeById(treeID);
		treeManagerService.updateTreeData(tree, newHeight, newDiameter, newAge, newOwnerName, newSpecies, newLandUse, newMunicipality);
		return convertToDto(tree);
	}
	/**
	 * This method is mapped to the GET request URI to list all the reports in the database. 
	 * It calls the service method getAllSustainabilityReports and converts its result to a list of Dto's before returning it to the frontend. 
	 * @return List<SustainabilityReportDto>
	 */
	@GetMapping(value = { "/reports/", "/reports" })
	public List<SustainabilityReportDto> getAllSustainabilityReports() {
		List<SustainabilityReport> reportsList = reportService.getAllSustainabilityReports();
		List<SustainabilityReportDto> reportsListDto = new ArrayList<SustainabilityReportDto>();
		for(SustainabilityReport r: reportsList) {
			reportsListDto.add(convertToDto(r));
		}
		return reportsListDto;
	}
	/**
	 * This method is mapped to the GET request URI to list all the statuses in the database. 
	 * It calls the service method getAllStatuses and converts its result to a list of Dto's before returning it to the frontend. 
	 * @return List<Tree.Status>
	 */
	@GetMapping(value = { "/statuses/", "/statuses" })
	public List<Tree.Status> getStatuses() {
		List<Tree.Status> statuses = treeManagerService.getAllStatuses();
		return statuses;
	}
	/**
	 * This method is mapped to the GET request URI to list all the land use types in the database. 
	 * It calls the service method getAllLandUseTypes and converts its result to a list of Dto's before returning it to the frontend. 
	 * @return List<Tree.LandUse>
	 */
	@GetMapping(value = { "/landUseTypes/", "/landUseTypes" })
	public List<Tree.LandUse> getLandUseTypes() {
		List<Tree.LandUse> statuses = treeManagerService.getAllLandUseTypes();
		return statuses;
	}
	/**
	 * This method is mapped to the GET request URI to list all the surveys in the database. 
	 * It calls the service method getAllSurveys and converts its result to a list of Dto's before returning it to the frontend.
	 * @return List<SurveyDto>
	 */
	@GetMapping(value = { "/surveys/", "/surveys" })
	public List<SurveyDto> getSurveys(){
		List<Survey> surveysList = surveyService.getAllSurveys();
		List<SurveyDto> surveysListDto = new ArrayList<SurveyDto>();
		for(Survey s: surveysList) {
			surveysListDto.add(convertToDto(s));
		}
		return surveysListDto;
	}
	/**
	 * This method is mapped to the GET request URI to list all the versions of the system. 
	 * It calls the service method getAllVersions and converts its result to a list of Dto's before returning it to the frontend.
	 * @return List<String> version numbers
	 */
	@GetMapping(value = { "/versions/", "/versions" })
	public List<String> getAllSystemVersions(){
		List<String> versions = versionManagerService.getAllVersions();
		return versions;
	}
	/**
	 * This method is mapped to the POST request URI to create a new forecast. It calls the service method updateTreeData
	 * and converts its result to a Dto before returning it to the frontend. 
	 * @param name
	 * @param baseVersion
	 * @param futureYear
	 * @param treesToPlantDto
	 * @param treeIdsToCutDown
	 * @return
	 * @throws InvalidInputException
	 */
	@PostMapping(value = { "/newForecast/{name}", "/newForecast/{name}/" })
	public ForecastDto createNewForecast(@PathVariable("name") String name,
			@RequestParam(name = "baseVersion") String baseVersion,
			@RequestParam(name = "futureYear") int futureYear,
			@RequestParam(name = "treesToPlant", required = false) List<TreeDto> treesToPlantDto,
			@RequestParam(name = "treesToCutDown", required = false) List<Integer> treeIdsToCutDown) throws InvalidInputException{
		List<Tree> treesToCutDown = new ArrayList<Tree>();
		if(treeIdsToCutDown != null) {
			for(int id: treeIdsToCutDown) {
				treesToCutDown.add(treeManagerService.getTreeById(id));
			}
		}
		Forecast forecast = forecastService.createForecast(name, baseVersion, futureYear, treesToPlantDto, treesToCutDown);
		return convertToDto(forecast);
	}

	/**
	 * Method used for forecast, need to create treeDto's in order to pass them to the list of treesToPlant
	 * @param species
	 * @param height
	 * @param diameter
	 * @param munDto
	 * @param latitude
	 * @param longitude
	 * @param ownerName
	 * @param age
	 * @param landuse
	 * @return TreeDto created
	 * @throws InvalidInputException
	 */
	@PostMapping(value = {"/newTreeDto/{species}", "/newTreeDto/{species}/"})
	public TreeDto createTreeDto(
			@PathVariable("species") String species,
			@RequestParam(name = "height") double height, 
			@RequestParam(name = "diameter") double diameter,
			@RequestParam(name= "municipality") MunicipalityDto munDto,
			@RequestParam(name="latitude") double latitude,
			@RequestParam(name="longitude") double longitude,
			@RequestParam(name="owner") String ownerName,
			@RequestParam(name="age") int age, 
			@RequestParam(name="landuse") Tree.LandUse landuse ) throws InvalidInputException {
		LocationDto locationDto = new LocationDto(latitude, longitude);
		TreeDto tDto = new TreeDto(species, height, diameter, age, locationDto, ownerName, munDto,landuse);
		return tDto;
	}
	/**
	 * This method is mapped to the GET request URI to list all the surveys of a given tree, identified by its ID. 
	 * It calls the service method getSurveysForTree and converts its result to a list of Dto's before returning it to the frontend.
	 * @param id
	 * @return List<SurveyDto>
	 * @throws InvalidInputException
	 */
	@GetMapping(value = { "/surveysForTree/{id}", "/surveysForTree/{id}/" })
	public List<SurveyDto> getSurveysForTree(@PathVariable("id") int id) throws InvalidInputException{
		Tree tree = treeManagerService.getTreeById(id);
		List<Survey> surveysForTree = surveyService.getSurveysForTree(tree);
		List<SurveyDto> surveyDtosForTree = new ArrayList<SurveyDto>();
		for(Survey s: surveysForTree) {
			surveyDtosForTree.add(convertToDto(s));
		}
		return surveyDtosForTree;
	}
	/**
	 * This method is mapped to the POST request URI to update the version of the system from the dropdown in the frontend. 
	 * It calls the service method setSelectedVersion and returns the string of selected updated version. 
	 * @param versionNum
	 * @return String version selected and updated. 
	 * @throws InvalidInputException
	 */
	@PostMapping(value = { "/updateVersion/", "/updateVersion" })
	public String updateVersion(
			@RequestParam(name = "versionNum") String versionNum) throws InvalidInputException{
		String newVersion = versionManagerService.setSelectedVersion(versionNum);
		return newVersion;
	}
	/**
	 * This method is mapped to the GET request URI to get the system's version year from selected version. 
	 * It calls the service method getCurrentVersionYear
	 * @return version year
	 * @throws InvalidInputException
	 */
	@GetMapping(value = { "/versionYear", "/versionYear/" })
	public int getVersionYear() throws InvalidInputException{
		int versionYear = versionManagerService.getCurrentVersionYear();
		return versionYear;
	}
	/**
	 * This method is mapped to the GET request URI to get the system's version number from selected version. 
	 * It calls the service method getCurrentVersionNumber
	 * @return String version number
	 * @throws InvalidInputException
	 */
	@GetMapping(value = { "/versionNumber", "/versionNumber/" })
	public String getVersionNumber() throws InvalidInputException{
		String versionNumber = versionManagerService.getCurrentVersionNumber();
		return versionNumber;
	}
	/**
	 * This method is mapped to the GET request URI to list all the user types. It calls the service method getUserTypes
	 * @return List<User.UserType>
	 * @throws InvalidInputException
	 */
	@GetMapping(value = { "/userTypes", "/userTypes/" })
	public List<User.UserType> getUserTypes() throws InvalidInputException{
		List<User.UserType> userTypes = treeManagerService.getUserTypes();
		return userTypes;
	}

}