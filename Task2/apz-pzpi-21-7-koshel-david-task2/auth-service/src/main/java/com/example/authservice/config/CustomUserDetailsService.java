package com.example.authservice.config;

import com.example.authservice.entity.UserCredential;
import com.example.authservice.repository.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserCredentialRepository userCredentialRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserCredential> credential = userCredentialRepository.findByEmail(email);
        return credential.map(CustomUserDetails::new).orElseThrow(()->new UsernameNotFoundException("User not found with email: "+email));
    }
}
