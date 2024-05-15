package com.example.authservice.service;

import com.example.authservice.entity.UserCredential;
import com.example.authservice.repository.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthService {
    @Autowired
    private UserCredentialRepository userCredentialRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;

    public String saveOwner(UserCredential userCredential) {
        userCredential.setPassword(passwordEncoder.encode(userCredential.getPassword()));
        userCredential.setApproved(false);
        userCredentialRepository.save(userCredential);
        return "User was saved";
    }

    public String saveStaff(UserCredential userCredential,String inviteCode) {
        RestTemplate restTemplate = new RestTemplate();
        userCredential.setPassword(passwordEncoder.encode(userCredential.getPassword()));
        try {
            Long saloonId = restTemplate.getForObject("http://localhost:8081/saloons/invite-code/"+inviteCode,Long.class);
            if(saloonId != null)
                userCredential.setSaloonId(saloonId);
            userCredentialRepository.save(userCredential);

            return "Staff was saved";
        } catch (Exception e){
            return e.getMessage();
        }
    }

    public String generateToken(String username){
        return jwtService.generateToken(username);

    }

    public void validateToken(String token){
        jwtService.validateToken(token);
    }
}
