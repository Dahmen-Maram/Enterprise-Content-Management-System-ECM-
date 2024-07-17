package com.bfi.ecm.Mappers;

import com.bfi.ecm.Entities.Person;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Getter
@Setter
@Builder


public class PersonDTO {
    private String name;
    private String lastName;
    private String gender;
    private String address;
    private int phone;
    private String email;
    private LocalDate birthDate;

    public static PersonDTO fromEntity(Person person) {
        PersonDTO personDto = null;
        if (person == null) {
            return null;
        } else return personDto.builder()
                .name(person.getName())
                .lastName(person.getLastName())
                .gender(person.getGender())
                .address(person.getAddress())
                .phone(person.getPhone())
                .email(person.getEmail())
                .birthDate(person.getBirthDate())
                .build();

    }

    public Person toEntity(PersonDTO personDto) {
        Person person = new Person();
        if (personDto == null) {
            return null;
        } else {
            person.setName(personDto.getName());
            person.setLastName(personDto.getLastName());
            person.setGender(personDto.getGender());
            person.setAddress(personDto.getAddress());
            person.setPhone(personDto.getPhone());
            person.setEmail(personDto.getEmail());
            person.setBirthDate(personDto.getBirthDate());
            return person;
        }
    }

}
