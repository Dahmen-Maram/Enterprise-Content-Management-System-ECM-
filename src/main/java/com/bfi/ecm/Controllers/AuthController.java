package com.bfi.ecm.Controllers;

import com.bfi.ecm.Entities.Auth;
import com.bfi.ecm.Mappers.AuthDto;
import com.bfi.ecm.Services.ServiceImplementation.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bfi/v1/auth")
public class AuthController {
    @Autowired
    private AuthServiceImpl authService;

    @PostMapping("/login")
    public Auth login(@RequestBody AuthDto auth) {
        boolean isAuthenticated = authService.authenticate(auth.getEmail(), auth.getPassword());
        Auth authObj = new Auth();
        authObj.setEmail(auth.getEmail());
        authObj.setPassword(auth.getPassword());
        if (isAuthenticated) {
            authObj.setMessage("login successful");
        } else {
            authObj.setMessage("Invalid credentials");
        }

        return authObj;
    }

}