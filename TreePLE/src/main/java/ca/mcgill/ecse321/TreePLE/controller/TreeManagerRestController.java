package ca.mcgill.ecse321.TreePLE.controller;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.format.annotation.DateTimeFormat;
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
import ca.mcgill.ecse321.TreePLE.model.Tree;
import ca.mcgill.ecse321.TreePLE.model.User;
import ca.mcgill.ecse321.TreePLE.service.InvalidInputException;
import ca.mcgill.ecse321.TreePLE.service.SurveyService;
import ca.mcgill.ecse321.TreePLE.service.TreeManagerService;


@RestController
public class TreeManagerRestController {
	@Autowired
	private TreeManagerService treeManagerService;
	
	
	private SurveyService surveyService;

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
		treeDto.setUser(createUserDtoForTree(t));
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
		surveyDto.setUser(createUserDtoForSurvey(survey));
		return surveyDto;
	}
	private LocationDto createLocationDtoForTree(Tree t) {
		Location locationForTree = treeManagerService.getLocationForTree(t);
		return convertToDto(locationForTree);
	}
	private UserDto createUserDtoForTree(Tree t) {
		User userForTree = treeManagerService.getOwnerForTree(t);
		return convertToDto(userForTree);
	}

	private MunicipalityDto createMunicipalityDtoForTree(Tree t) {
		Municipality municipalityForTree = treeManagerService.getMunicipalityForTree(t);
		return convertToDto(municipalityForTree);
	}
	private UserDto createUserDtoForSurvey(Survey survey) {
		User userForSurvey = surveyService.getSurveyorForSurvey(survey);
		return convertToDto(userForSurvey);
	}

	private TreeDto createTreeDtoForSurvey(Survey survey) {
		Tree treeForSurvey = surveyService.getTreeForSurvey(survey);
		return convertToDto(treeForSurvey);
	}
	
	
	@PostMapping(value = {"/newtree/ }", "/newtree/"})
	public TreeDto createTree(@RequestParam(name = "species") String species,
			@RequestParam(name = "height") double height, 
			@RequestParam(name = "diameter") double diameter, 
			@RequestParam(name= "municipality") MunicipalityDto munDto, 
			//@RequestParam(name="location") LocationDto locationDto,
			@RequestParam(name="latitude") double latitude,
			@RequestParam(name="longitude") double longitude,
			@RequestParam(name="owner") UserDto userDto, 
			@RequestParam(name="landuse") Tree.LandUse landuse ) throws InvalidInputException {
		Location location = treeManagerService.getLocationByCoordinates(latitude, longitude);
		//Location location = new Location(latitude, longitude);
		User owner = treeManagerService.getOwnerByName(userDto.getName());
		Municipality municipality = treeManagerService.getMunicipalityByName(munDto.getName());
		Tree t = treeManagerService.createTree(species, height, diameter, location, owner, municipality, landuse);
		return convertToDto(t);
	}
	@PostMapping(value = {"/newSurvey/ }", "/newSurvey/"})
	public SurveyDto createSurvey(
			@RequestParam(name = "reportDate") Date reportDate,
			@RequestParam(name = "tree") TreeDto treeDto, 
			@RequestParam(name = "surveyor") UserDto surveyorDto) throws InvalidInputException {
		Tree tree = surveyService.getTreeById(treeDto.getId());
		User surveyor = surveyService.getSurveyorByName(surveyorDto.getName());
		Survey s = new Survey(reportDate, tree, surveyor);
		return convertToDto(s);
	}
	//if user doesnt find municipality in dropdown for createTree, they create a new one
	@PostMapping(value = {"/newMunicipality/{name}", "/newMunicipality/{name}/"})
	public MunicipalityDto createMunicipality(@PathVariable("name") String munName) throws InvalidInputException {
		Municipality m = new Municipality(munName);
		return convertToDto(m);
	}
	//
	//createLocation w lat/long in url <== input for create tree

	
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