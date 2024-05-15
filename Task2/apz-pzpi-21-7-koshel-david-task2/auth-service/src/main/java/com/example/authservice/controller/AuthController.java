package com.example.authservice.controller;

import com.example.authservice.dto.AuthRequest;
import com.example.authservice.entity.UserCredential;
import com.example.authservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public String addUser(@RequestBody UserCredential userCredential, @RequestParam String inviteCode){
        if(inviteCode==null || inviteCode.isEmpty())
            return authService.saveOwner(userCredential);
        else
            return authService.saveStaff(userCredential,inviteCode);
    }

    @PostMapping("/token")
    public String getToken(@RequestBody AuthRequest authRequest){
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(),authRequest.getPassword()));
        if(authenticate.isAuthenticated())
            return authService.generateToken(authRequest.getEmail());
        throw new RuntimeException("Invalid Access");

    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token){
        authService.validateToken(token);
        return "Token is valid";
    }

}
