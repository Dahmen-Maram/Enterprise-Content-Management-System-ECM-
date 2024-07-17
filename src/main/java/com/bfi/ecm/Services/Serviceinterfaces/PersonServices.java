package com.bfi.ecm.Services.Serviceinterfaces;

import com.bfi.ecm.Entities.Person;

import java.util.List;

public interface PersonServices {
    public List<Person> findAllPersons();

    public void save(Person p);

    public Person updatePerson(Person p);

    public void deletePerson(Person p);

    public Person findPersonById(Long id);
}
