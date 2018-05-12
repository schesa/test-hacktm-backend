package com.hacktm.volunteer;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.hacktm.event.Event;
import com.hacktm.exception.NotFoundException;
import com.hacktm.exception.AlreadyExistsException;
import com.hacktm.security.SpringSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@RestController
public class VolunteerController {

    @Autowired
    private VolunteerRepository volunteerRepository;

    @GetMapping("/volunteers")
    public MappingJacksonValue getVolunteers(Pageable pageable) {
        List<Volunteer> volunteers = volunteerRepository.findAll(pageable).getContent();
        return setVolunteerFilter(volunteers);
    }

    @GetMapping("/volunteers/{id}")
    public MappingJacksonValue getVolunteerById(@PathVariable long id) {
        Optional<Volunteer> maybeVolunteer = volunteerRepository.findById(id);
        if(!maybeVolunteer.isPresent()) {
            throw new NotFoundException("Volunteer with id " + id + " does not exist!");
        }

        return setVolunteerFilter(maybeVolunteer.get());
    }

    @PostMapping("/volunteers")
    public ResponseEntity<MappingJacksonValue> createVolunteer(@Valid @RequestBody Volunteer volunteer) {
        String email = volunteer.getEmail();
        String password = volunteer.getPassword();

        Optional<Volunteer> maybeVolunteer = volunteerRepository.findByEmail(email);
        if(maybeVolunteer.isPresent()) {
            throw new AlreadyExistsException("Email " + email + " already linked to another volunteer!");
        }

        volunteer.setCreatedDate(new Date());
        String hashedPassword = SpringSecurityConfig.encoder().encode(password);
        volunteer.setPassword(hashedPassword);
        volunteerRepository.save(volunteer);

        return new ResponseEntity<>(setVolunteerFilter(volunteer), HttpStatus.CREATED);
    }

    @PostMapping("/volunteers/{id}")
    public ResponseEntity<MappingJacksonValue> updateVolunteer(@PathVariable long id, @Valid @RequestBody Volunteer volunteer) {
        Optional<Volunteer> maybeVolunteer = volunteerRepository.findById(id);
        if(!maybeVolunteer.isPresent()) {
            throw new NotFoundException("Volunteer with id " + id + " does not exist!");
        }

        Volunteer existingVolunteer = maybeVolunteer.get();
        existingVolunteer.setName(volunteer.getName());
        existingVolunteer.setAge(volunteer.getAge());
        existingVolunteer.setLocation(volunteer.getLocation());
        volunteerRepository.save(existingVolunteer);
        return new ResponseEntity<>(setVolunteerFilter(existingVolunteer), HttpStatus.CREATED);
    }

    @PostMapping("/volunteers/{id}/subscribeEvent")
    public ResponseEntity<Event> subscribeToEvent(@RequestParam long id, @RequestParam boolean value) {
        return null;
    }

    @PostMapping("/volunteers/{id}/subscribeNgo")
    public ResponseEntity<MappingJacksonValue> subscribeToNgo(@RequestParam long id, @RequestParam boolean value) {
        return null;
    }

    @DeleteMapping("/volunteers/{id}")
    public void deleteUser(@PathVariable long id) {
        Optional<Volunteer> maybeVolunteer = volunteerRepository.findById(id);
        if(!maybeVolunteer.isPresent()) {
            throw new NotFoundException("Volunteer with id " + id + " does not exist!");
        }
        volunteerRepository.delete(maybeVolunteer.get());
    }

    private static MappingJacksonValue setVolunteerFilter(Object userObject) {
        MappingJacksonValue mapping = new MappingJacksonValue(userObject);
        SimpleFilterProvider filter = new SimpleFilterProvider().addFilter("VolunteerFilter", getVolunteerFilterProperty());
        mapping.setFilters(filter);
        return mapping;
    }

    private static SimpleBeanPropertyFilter getVolunteerFilterProperty() {
        SimpleBeanPropertyFilter filterProperty = SimpleBeanPropertyFilter.filterOutAllExcept("id","name","age","location","createdDate");
        return filterProperty;
    }

}
