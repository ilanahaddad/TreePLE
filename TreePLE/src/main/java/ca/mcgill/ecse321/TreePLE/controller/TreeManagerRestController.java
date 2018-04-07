package ca.mcgill.ecse321.TreePLE.controller;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.TreePLE.dto.LocationDto;
import ca.mcgill.ecse321.TreePLE.dto.MunicipalityDto;
import ca.mcgill.ecse321.TreePLE.dto.SurveyDto;
import ca.mcgill.ecse321.TreePLE.dto.SustainabilityReportDto;
import ca.mcgill.ecse321.TreePLE.dto.TreeDto;
import ca.mcgill.ecse321.TreePLE.dto.UserDto;
import ca.mcgill.ecse321.TreePLE.model.Location;
import ca.mcgill.ecse321.TreePLE.model.Municipality;
import ca.mcgill.ecse321.TreePLE.model.Survey;
import ca.mcgill.ecse321.TreePLE.model.SustainabilityReport;
import ca.mcgill.ecse321.TreePLE.model.Tree;
import ca.mcgill.ecse321.TreePLE.model.Tree.LandUse;
import ca.mcgill.ecse321.TreePLE.model.Tree.Status;
import ca.mcgill.ecse321.TreePLE.model.User;
import ca.mcgill.ecse321.TreePLE.model.User.UserType;
import ca.mcgill.ecse321.TreePLE.model.VersionManager;
import ca.mcgill.ecse321.TreePLE.service.InvalidInputException;
import ca.mcgill.ecse321.TreePLE.service.ReportService;
import ca.mcgill.ecse321.TreePLE.service.SurveyService;
import ca.mcgill.ecse321.TreePLE.service.TreeManagerService;


@RestController
public class TreeManagerRestController {
	@Autowired
	private TreeManagerService treeManagerService;
	
	@Autowired
	private SurveyService surveyService;
	
	@Autowired
	private ReportService reportService;
	
	//@Autowired
	//private VersionManager versionManager;

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

	private UserDto convertToDto(User user) {
		return modelMapper.map(user, UserDto.class);

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
	public MunicipalityDto createMunicipality(@PathVariable("name") String munName) throws InvalidInputException {
		Municipality m = treeManagerService.createMunicipality(munName);
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
	//TODO: ADD DTO HERE
	@PostMapping(value = { "/newReport/{reporterName}/", "/setUserType/{reporterName}" })
	public SustainabilityReport generateSustainabilityReport(@PathVariable("reporterName") String reporterName,
		@RequestParam Date reportDate,
		@RequestParam(name="lat1") double lat1,
		@RequestParam(name="long1") double long1,
		@RequestParam(name="lat2") double lat2,
		@RequestParam(name="long2") double long2,
		@RequestParam(name="lat3") double lat3,
		@RequestParam(name="long3") double long3,
		@RequestParam(name="lat4") double lat4,
		@RequestParam(name="long4") double long4) throws InvalidInputException {
		Location location1 = treeManagerService.getLocationByCoordinates(lat1, long1);
		Location location2 = treeManagerService.getLocationByCoordinates(lat2, long2);
		Location location3 = treeManagerService.getLocationByCoordinates(lat3, long3);
		Location location4 = treeManagerService.getLocationByCoordinates(lat4, long4);
		Location[] perimeter = new Location[4];
		perimeter[0] = location1;
		perimeter[1] = location2;
		perimeter[2] = location3;
		perimeter[3] = location4;
		SustainabilityReport report = null;
		report = reportService.createReport(reporterName, reportDate, perimeter);
		return report;
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
	@PostMapping(value = { "/moveTree/", "/moveTree/" })
	public void moveTree(
			@RequestParam(name = "tree") int treeID, 
			@RequestParam(name = "latitude") double newLat,
			@RequestParam(name = "longitude") double newLong) throws InvalidInputException{
	
		Tree tree = treeManagerService.getTreeById(treeID);
		treeManagerService.moveTree(tree, newLat, newLong);
	}
	@PostMapping(value = { "/updateTreeData/", "/updateTreeData/" })
	public TreeDto updateTreeData(
			@RequestParam(name = "tree") int treeID, 
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
		for(SustainabilityReport sr: reportsList) {
			//reportsListDto.add(convertToDto(sr));
		}
		return reportsListDto;

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
	private Tree convertToDomainObject(TreeDto tDto) {
		List<Tree> allTrees = treeManagerService.findAllTrees();
		for (Tree tree : allTrees) {
			if (tree.getId()==tDto.getId()) {
				return tree;
			}
		}
		return null;
	}

}