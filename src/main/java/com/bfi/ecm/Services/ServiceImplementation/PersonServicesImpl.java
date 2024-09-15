package com.bfi.ecm.Services.ServiceImplementation;

import com.bfi.ecm.Entities.Person;
import com.bfi.ecm.Repositories.PersonRepository;
import com.bfi.ecm.Services.PersonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServicesImpl implements PersonServices {

    private PersonRepository personRepository;

    @Autowired
    public PersonServicesImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public List<Person> findAllPersons() {
        return personRepository.findAll();
    }

    @Override
    public void save(Person p) {
        personRepository.save(p);
    }

    @Override
    public Person updatePerson(Person p) {
        Person p1 = personRepository.findById(p.getId()).orElse(null);
        if (p1 != null) {
            p1 = personRepository.save(p1);
        }
        return p1;
    }

    @Override
    public void deletePerson(Person p) {
        personRepository.deleteById(p.getId());
    }

    @Override
    public Person findPersonById(Long id) {
        return personRepository.findById(id).orElse(null);
    }
}
