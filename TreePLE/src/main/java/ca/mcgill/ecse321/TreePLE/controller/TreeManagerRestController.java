package ca.mcgill.ecse321.TreePLE.controller;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.TreePLE.service.TreeManagerService;

@RestController
public class TreeManagerRestController {

	@Autowired
	private TreeManagerService service;

	@Autowired
	private ModelMapper modelMapper;

	@RequestMapping("/")
	public String index() {
		return "Event registration application root. Web-based frontend is a TODO. Use the REST API to manage events and participants.\n";
	}
	

	/*
	@PostMapping(value = { "/participants/{name}", "/participants/{name}/" })
	public Tree createParticipant(@PathVariable("name") String name) throws InvalidInputException {
		Participant participant = service.createParticipant(name);
		return convertToDto(participant);
	}

	
	// Conversion methods (not part of the API)
	private EventDto convertToDto(Event e) {
		// In simple cases, the mapper service is convenient
		return modelMapper.map(e, EventDto.class);
	}

	private ParticipantDto convertToDto(Participant p) {
		ParticipantDto participantDto = modelMapper.map(p, ParticipantDto.class);
		participantDto.setEvents(createEventDtosForParticipant(p));
		return participantDto;
	}

	private Participant convertToDomainObject(ParticipantDto pDto) {
		// Mapping DTO to the domain object without using the mapper
		List<Participant> allParticipants = service.findAllParticipants();
		for (Participant participant : allParticipants) {
			if (participant.getName().equals(pDto.getName())) {
				return participant;
			}
		}
		return null;
	}

	private List<EventDto> createEventDtosForParticipant(Participant p) {
		// TODO it is your task to add and implement the getEventsForParticipant service
		// within the EventRegistrationService class, since it is missing now
		List<Event> eventsForParticipant = service.getEventsForParticipant(p);
		List<EventDto> events = new ArrayList<EventDto>();
		for (Event event : eventsForParticipant) {
			events.add(convertToDto(event));
		}
		return events;
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