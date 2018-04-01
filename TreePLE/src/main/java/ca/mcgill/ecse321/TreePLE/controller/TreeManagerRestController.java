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
import ca.mcgill.ecse321.TreePLE.dto.TreeDto;
import ca.mcgill.ecse321.TreePLE.dto.UserDto;
import ca.mcgill.ecse321.TreePLE.model.Location;
import ca.mcgill.ecse321.TreePLE.model.Municipality;
import ca.mcgill.ecse321.TreePLE.model.Survey;
import ca.mcgill.ecse321.TreePLE.model.SustainabilityReport;
import ca.mcgill.ecse321.TreePLE.model.Tree;
import ca.mcgill.ecse321.TreePLE.model.User;
import ca.mcgill.ecse321.TreePLE.model.User.UserType;
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

	@Autowired
	private ModelMapper modelMapper;

	@RequestMapping("/")
	public String index() {
		return "TreePLE application root. Web-based frontend is a TODO. Use the REST API to manage events and participants.\n";
	}


/*
	@PostMapping(value = { "/participants/{name}", "/participants/{name}/" })
	public Tree createParticipant(@PathVariable("name") String name) throws InvalidInputException {
		Participant participant = service.createParticipant(name);
		return convertToDto(participant);
	}

*/
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
			//@RequestParam(name = "species") String species,
			@RequestParam(name = "height") double height, 
			//@RequestParam(name = "diameter") double diameter,
			@RequestParam(name= "municipality") MunicipalityDto munDto,
			@RequestParam(name = "diameter") double diameter,
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
			//@PathVariable(name="reportDate") @DateTimeFormat(pattern= "yyyy-MM-dd") Date reportDate,
			//@PathVariable("reportDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date reportDate,
			
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
	
	//TODO: ADD DTO HERE
	@PostMapping(value = { "/setUserType/{userType}/", "/setUserType/{userType}" })
	public User setUserType(@PathVariable("userType") UserType userType) throws InvalidInputException{
		User user = treeManagerService.setUserType(userType);
		return user;
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
		@RequestParam(name="long4") double long4) {
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
		try {
			report = reportService.createReport(reporterName, reportDate, perimeter);
		} catch (InvalidInputException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return report;
	}
	
	
	
/*	DONT DELETE: NOT SURE IF WE NEED THESE: 
 * private Participant convertToDomainObject(ParticipantDto pDto) {
		// Mapping DTO to the domain object without using the mapper
		List<Participant> allParticipants = service.findAllParticipants();
		for (Participant participant : allParticipants) {
			if (participant.getName().equals(pDto.getName())) {
				return participant;
			}
		}
		return null;
	}

	private RegistrationDto convertToDto(Registration r, Participant p, Event e) {
		// Now using the mapper would not help too much
		// RegistrationDto registrationDto = modelMapper.map(r, RegistrationDto.class);
		// Manual conversion instead
		EventDto eDto = convertToDto(e);
		ParticipantDto pDto = convertToDto(p);
		return new RegistrationDto(pDto, eDto);
	}*/

}