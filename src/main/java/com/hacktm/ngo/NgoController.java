package com.hacktm.ngo;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.hacktm.exception.AlreadyExistsException;
import com.hacktm.exception.NotFoundException;
import com.hacktm.security.SpringSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class NgoController {

    @Autowired
    private NgoRepository ngoRepository;

    @GetMapping("/ngos")
    public MappingJacksonValue getNgos(Pageable pageable) {
        List<Ngo> ngos = ngoRepository.findAll(pageable).getContent();
        return setNgoFilter(ngos);
    }

    @GetMapping("/ngos/{id}")
    public MappingJacksonValue getById(@PathVariable long id) {
        Optional<Ngo> maybeNgo = ngoRepository.findById(id);
        if(!maybeNgo.isPresent()) {
            throw new NotFoundException("Ngo with id: " + id + " doest not exist!");
        }
        return setNgoFilter(maybeNgo.get());
    }

    @PostMapping("/ngos")
    public ResponseEntity<MappingJacksonValue> createNgo(@Valid @RequestBody Ngo ngo) {
        String email = ngo.getEmail();
        String password = ngo.getPassword();

        Optional<Ngo> maybeNgo = ngoRepository.findByEmail(email);
        if(maybeNgo.isPresent()) {
            throw new AlreadyExistsException("Email " + email + " already linked to another ngo!");
        }

        ngo.setCreatedDate(new Date());
        String hashedPassword = SpringSecurityConfig.encoder().encode(ngo.getPassword());
        ngo.setPassword(hashedPassword);
        ngoRepository.save(ngo);
        return new ResponseEntity<>(setNgoFilter(ngo), HttpStatus.CREATED);
    }

    @PostMapping("/ngos/{id}")
    public ResponseEntity<MappingJacksonValue> updateNgo(@PathVariable long id, @Valid @RequestBody Ngo ngo) {
        Optional<Ngo> maybeNgo = ngoRepository.findById(id);
        if(!maybeNgo.isPresent()) {
            throw new NotFoundException("Ngo with id: " + id + " doest not exist!");
        }

        Ngo existingNgo = maybeNgo.get();
        existingNgo.setName(ngo.getName());
        existingNgo.setPurpose(ngo.getPurpose());
        existingNgo.setLocation(ngo.getLocation());
        existingNgo.setWebsite(ngo.getWebsite());
        ngoRepository.save(existingNgo);
        return new ResponseEntity<>(setNgoFilter(existingNgo), HttpStatus.CREATED);
    }

    @DeleteMapping("/ngos/{id}")
    public void deleteById(@PathVariable long id) {
        Optional<Ngo> maybeNgo = ngoRepository.findById(id);
        if(!maybeNgo.isPresent()) {
            throw new NotFoundException("Ngo with id: " + id + " doest not exist!");
        }
        ngoRepository.delete(maybeNgo.get());
    }

    private static MappingJacksonValue setNgoFilter(Object ngoObject) {
        MappingJacksonValue mapping = new MappingJacksonValue(ngoObject);
        SimpleFilterProvider filter = new SimpleFilterProvider().addFilter("NgoFilter", getNgoFilterProperty());
        mapping.setFilters(filter);
        return mapping;
    }

    private static SimpleBeanPropertyFilter getNgoFilterProperty() {
        SimpleBeanPropertyFilter filterProperty = SimpleBeanPropertyFilter.filterOutAllExcept("id","name","purpose","website","location", "createdDate");
        return filterProperty;
    }
}
