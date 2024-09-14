package com.bfi.ecm.Services.ServiceImplementation;

import com.bfi.ecm.Entities.Person;
import com.bfi.ecm.Repositories.PersonRepository;
import com.bfi.ecm.Services.AuthServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthServices {

    @Autowired
    private PersonRepository personRepository;

    public boolean authenticate(String email, String password) {
        Person user = personRepository.findByEmail(email);
        if (user != null) {
            return user.getPassword().equals(password);
        }
        return false;
    }

}
