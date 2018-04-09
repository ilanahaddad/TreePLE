package ca.mcgill.ecse321.TreePLE.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
import ca.mcgill.ecse321.TreePLE.model.TreeManager;
import ca.mcgill.ecse321.TreePLE.model.User.UserType;
import ca.mcgill.ecse321.TreePLE.service.ForecastService;
import ca.mcgill.ecse321.TreePLE.service.InvalidInputException;
import ca.mcgill.ecse321.TreePLE.service.ReportService;
import ca.mcgill.ecse321.TreePLE.service.SurveyService;
import ca.mcgill.ecse321.TreePLE.service.TreeManagerService;
import ca.mcgill.ecse321.TreePLE.service.VersionManagerService;


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
	private TreeDto convertToDto(Tree t) {
		// In simple cases, the mapper service is convenient
		TreeDto treeDto = modelMapper.map(t, TreeDto.class);
		treeDto.setMunicipality(createMunicipalityDtoForTree(t));
		treeDto.setLocation(createLocationDtoForTree(t));
		return treeDto;
	}
	private ForecastDto convertToDto(Forecast forecast) {
		return modelMapper.map(forecast, ForecastDto.class);

	}
	private SustainabilityReportDto convertToDto(SustainabilityReport report) {
		SustainabilityReportDto reportDto = modelMapper.map(report, SustainabilityReportDto.class);
		reportDto.setBiodiversityIndex(report.getBiodiversityIndex());
		reportDto.setCanopy(report.getCanopy());
		reportDto.setCarbonSequestration(report.getCarbonSequestration());
		return reportDto;
	}
	private LocationDto convertToDto(Location location) {
		return modelMapper.map(location, LocationDto.class);

	}
	private MunicipalityDto convertToDto(Municipality mun) {
		return modelMapper.map(mun, MunicipalityDto.class);

	}
	private SurveyDto convertToDto(Survey survey) {
		SurveyDto surveyDto= modelMapper.map(survey, SurveyDto.class);
		surveyDto.setTree(createTreeDtoForSurvey(survey));
		
		return surveyDto;
	}
	private LocationDto createLocationDtoForTree(Tree t) {
		Location locationForTree = treeManagerService.getLocationForTree(t);
		return convertToDto(locationForTree);
	}

	
	private MunicipalityDto createMunicipalityDtoForTree(Tree t) {
		Municipality municipalityForTree = treeManagerService.getMunicipalityForTree(t);
		return convertToDto(municipalityForTree);
	}

	private TreeDto createTreeDtoForSurvey(Survey survey) {
		Tree treeForSurvey = surveyService.getTreeForSurvey(survey);
		return convertToDto(treeForSurvey);
	}
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
	private Tree convertToDomainObject(TreeDto tDto) { //unused for now
		/*List<Tree> allTrees = treeManagerService.findAllTrees();
		for (Tree tree : allTrees) {
			if (tree.getId()==tDto.getId()) {
				return tree;
			}
		}*/
		return null;
	}
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
		//Location location = new Location(latitude, longitude);
	//	User owner = treeManagerService.getOwnerByName(userDto.getName());
		Municipality municipality = treeManagerService.getMunicipalityByName(munDto.getName());
		Tree t = treeManagerService.createTree(ownerName, species, height, diameter, age, location, municipality, landuse);
		return convertToDto(t);
	}
	@PostMapping(value = {"/newSurvey", "/newSurvey/"})
	public SurveyDto createSurvey(
			@RequestParam Date reportDate,
			//@RequestParam(name = "tree") TreeDto treeDto, 
			@RequestParam(name = "tree") int treeID, 
			@RequestParam(name = "surveyor") String surveyor,
			@RequestParam(name = "newTreeStatus") Tree.Status newTreeStatus
			) throws InvalidInputException{
		
		Tree tree = surveyService.getTreeById(treeID);
		Survey s = surveyService.createSurvey(reportDate, tree,surveyor, newTreeStatus);
		return convertToDto(s);
	}
	//if user doesn't find municipality in dropdown for createTree, they create a new one
	@PostMapping(value = {"/newMunicipality/{name}", "/newMunicipality/{name}/"})
	public MunicipalityDto createMunicipality(@PathVariable("name") String name) throws InvalidInputException {
		Municipality m = treeManagerService.createMunicipality(name);
		return convertToDto(m);
	}
	
	@GetMapping(value = { "/municipalities/", "/municipalities" })
	public List<MunicipalityDto> findAllMunicipalities() {
		List<MunicipalityDto> municipalities = new ArrayList<MunicipalityDto>();
		for(Municipality m: treeManagerService.findAllMunicipalities()) {
			municipalities.add(convertToDto(m));
		}
		return municipalities;
	}
	@GetMapping(value = { "/trees/", "/trees" })
	public List<TreeDto> findAllTrees() {
		List<TreeDto> trees = new ArrayList<TreeDto>();
		for(Tree t: treeManagerService.findAllTrees()) {
			trees.add(convertToDto(t));
		}
		return trees;
	}
	
	@PostMapping(value = { "/setUserType/{userTypeName}/", "/setUserType/{userTypeName}" })
	public void setUserType(@PathVariable("userTypeName") String userTypeName) throws InvalidInputException{
		UserType userType= treeManagerService.getUserTypeByName(userTypeName);
		treeManagerService.setUserType(userType);
	}

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
	
	@GetMapping(value = { "/species/", "/species" })
	public List<String> findAllSpecies() {
		return treeManagerService.getAllSpecies();
	}
	//listTreeByMunicipality as a RESTful service in class TreeManagerRestController.java 
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
	@GetMapping(value = { "/treesByStatus/{status}", "/treesByStatus/{status}/" })
	public List<TreeDto> listTreesByStatus(@PathVariable("status") Status status) throws InvalidInputException{
		List<Tree> TreesByStatusList = treeManagerService.listTreesByStatus(status);
		List<TreeDto> TreesByStatusListDto = new ArrayList<TreeDto>();
		for(Tree t: TreesByStatusList) {
			TreesByStatusListDto.add(convertToDto(t));
		}
		return TreesByStatusListDto;

	}
	@GetMapping(value = { "/treesBySpecies/{species}", "/treesBySpecies/{species}/" })
	public List<TreeDto> listTreesBySpecies(@PathVariable("species") String species) throws InvalidInputException{
		List<Tree> TreesBySpeciesList = treeManagerService.listTreesBySpecies(species);
		List<TreeDto> TreesBySpeciesListDto = new ArrayList<TreeDto>();
		for(Tree t: TreesBySpeciesList) {
			TreesBySpeciesListDto.add(convertToDto(t));
		}
		return TreesBySpeciesListDto;
	}
	@GetMapping(value = { "/treesByLandUse/{land}", "/treesByLandUse/{land}/" })
	public List<TreeDto> listTreesByLandUse(@PathVariable("land") LandUse landUse) throws InvalidInputException{
		List<Tree> TreesByLandUseList = treeManagerService.listTreesByLandUse(landUse);
		List<TreeDto> TreesByLandUseListDto = new ArrayList<TreeDto>();
		for(Tree t: TreesByLandUseList) {
			TreesByLandUseListDto.add(convertToDto(t));
		}
		return TreesByLandUseListDto;

	}
	@PostMapping(value = { "/moveTree/{id}", "/moveTree/{id}/" })
	public void moveTree(@PathVariable("id") int treeID,
			//@RequestParam(name = "tree") int treeID, 
			@RequestParam(name = "latitude") double newLat,
			@RequestParam(name = "longitude") double newLong) throws InvalidInputException{
		Tree tree = treeManagerService.getTreeById(treeID);
		treeManagerService.moveTree(tree, newLat, newLong);
	}
	@PostMapping(value = { "/updateTreeData/{id}", "/updateTreeData/{id}/" })
	public TreeDto updateTreeData(@PathVariable("id") int treeID,
			//@RequestParam(name = "tree") int treeID, 
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
	@GetMapping(value = { "/reports/", "/reports" })
	public List<SustainabilityReportDto> getAllSustainabilityReports() {
		List<SustainabilityReport> reportsList = reportService.getAllSustainabilityReports();
		List<SustainabilityReportDto> reportsListDto = new ArrayList<SustainabilityReportDto>();
		for(SustainabilityReport r: reportsList) {
			reportsListDto.add(convertToDto(r));
		}
		return reportsListDto;
	}
	@GetMapping(value = { "/statuses/", "/statuses" })
	public List<Tree.Status> getStatuses() {
		List<Tree.Status> statuses = treeManagerService.getAllStatuses();
		return statuses;
	}
	@GetMapping(value = { "/landUseTypes/", "/landUseTypes" })
	public List<Tree.LandUse> getLandUseTypes() {
		List<Tree.LandUse> statuses = treeManagerService.getAllLandUseTypes();
		return statuses;
	}
	@GetMapping(value = { "/surveys/", "/surveys" })
	public List<SurveyDto> getSurveys(){
		List<Survey> surveysList = surveyService.getAllSurveys();
		List<SurveyDto> surveysListDto = new ArrayList<SurveyDto>();
		for(Survey s: surveysList) {
			surveysListDto.add(convertToDto(s));
		}
		return surveysListDto;
	}
	
	@GetMapping(value = { "/versions/", "/versions" })
	public List<String> getAllSystemVersions(){
		List<String> versions = versionManagerService.getAllVersions();
		return versions;
	}
	
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
	/*	List<Tree> treesToPlant = new ArrayList<Tree>();
		for(TreeDto tDto:treesToPlantDto ) {
			treesToPlant.add(convertToDomainObject(tDto));
		}*/
		Forecast forecast = forecastService.createForecast(name, baseVersion, futureYear, treesToPlantDto, treesToCutDown);
		return convertToDto(forecast);
	}
	@PostMapping(value = { "/newForecastTest/", "/newForecastTest" })
	public ResponseEntity<RequestWrapper> createNewForecastTest(
			@RequestBody RequestWrapper requestWrapper) throws InvalidInputException{
		String name = requestWrapper.getName();
		String baseVersion = requestWrapper.getBaseVersion();
		int futureYear = requestWrapper.getFutureYear();
		List<TreeDto> treesToPlantDto = requestWrapper.getTreesToPlantDto();
		List<Integer> treeIdsToCutDown = requestWrapper.getTreeIdsCutDown();
		List<Tree> treesToCutDown = new ArrayList<Tree>();
		for(int id: treeIdsToCutDown) {
			treesToCutDown.add(treeManagerService.getTreeById(id));
		}
		Forecast forecast = forecastService.createForecast(name, baseVersion, futureYear, treesToPlantDto, treesToCutDown);
		return new ResponseEntity<RequestWrapper>(requestWrapper, HttpStatus.OK);
	}
	/**
	 * Method used for forecast, need to create treeDto's in order to pass them to the list of treesToPlant
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
		TreeDto tDto = new TreeDto(species, height, diameter, age, locationDto, ownerName, munDto,landuse );
		return tDto;
	}
	@GetMapping(value = { "/surveysForTree/{id}", "/treesBySpecies/{id}/" })
	public List<SurveyDto> getSurveysForTree(@PathVariable("id") int id) throws InvalidInputException{
		Tree tree = treeManagerService.getTreeById(id);
		List<Survey> surveysForTree = surveyService.getSurveysForTree(tree);
		List<SurveyDto> surveyDtosForTree = new ArrayList<SurveyDto>();
		for(Survey s: surveysForTree) {
			surveyDtosForTree.add(convertToDto(s));
		}
		return surveyDtosForTree;
	}
	@PostMapping(value = { "/updateVersion/", "/updateVersion" })
	public String updateVersion(
			@RequestParam(name = "versionNum") String versionNum) throws InvalidInputException{
		String newVersion = versionManagerService.setSelectedVersion(versionNum);
		return newVersion;
	}
	@GetMapping(value = { "/versionYear", "/versionYear/" })
	public int getVersionYear() throws InvalidInputException{
		int versionYear = versionManagerService.getCurrentVersionYear();
		return versionYear;
	}
	@GetMapping(value = { "/versionNumber", "/versionNumber/" })
	public String getVersionNumber() throws InvalidInputException{
		String versionNumber = versionManagerService.getCurrentVersionNumber();
		return versionNumber;
	}
	/*@GetMapping(value = { "/vm", "/vm/" })
	public List<TreeManager> getTreeManagers() throws InvalidInputException{
		List<TreeManager> treeManagers = versionManagerService.getTreeManagers();
		return treeManagers;
	}*/
	

}