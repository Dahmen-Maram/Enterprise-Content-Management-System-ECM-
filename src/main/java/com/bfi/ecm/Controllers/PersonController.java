package com.bfi.ecm.Controllers;

import com.bfi.ecm.Entities.Person;
import com.bfi.ecm.Mappers.PersonDTO;
import com.bfi.ecm.Services.Serviceinterfaces.PersonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bfi/v1/person")
public class PersonController {
    @Autowired
    private PersonServices personService;


    @GetMapping("/ff")

    public List<PersonDTO> findAllPersons() {
        return personService.findAllPersons()
                .stream()
                .map(PersonDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @PostMapping("/saveperson")
    public void save(@RequestBody Person person) {
        personService.save(person);
    }

    @GetMapping("/{id}")
    public PersonDTO findPersonById(@PathVariable("id") Long id) {
        Person person = personService.findPersonById(id);
        return PersonDTO.fromEntity(person);

    }

    @PutMapping("/updatePerson")
    public void update(@RequestBody PersonDTO personDTO) {
        Person person = personDTO.toEntity(personDTO);
        personService.updatePerson(person);
    }


    @DeleteMapping("/Deleteperson")
    public void delete(@RequestBody Person person) {
        personService.deletePerson(person);
    }
}
