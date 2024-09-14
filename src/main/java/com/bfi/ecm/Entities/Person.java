package com.bfi.ecm.Entities;

import jakarta.persistence.Entity;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class Person extends BaseEntity {
    private String name;
    private String lastName;
    private String gender;
    private String address;
    private String phone;
    private String email;
    private LocalDate birthDate;
    private String password;

    public Person() {
    }


}
