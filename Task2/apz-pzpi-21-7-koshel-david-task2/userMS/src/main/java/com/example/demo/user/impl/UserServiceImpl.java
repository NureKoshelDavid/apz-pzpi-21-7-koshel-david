package com.example.demo.user.impl;

import com.example.demo.user.UserCredential;
import com.example.demo.user.UserRepository;
import com.example.demo.user.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserCredential> findAll() {
        return userRepository.findAll();
    }

    @Override
    public List<UserCredential> findAllBySaloonId(Long id) {
        return userRepository.findAllBySaloonId(id);
    }

    @Override
    public UserCredential getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public UserCredential getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void createStaff(UserCredential userCredential) {
        userRepository.save(userCredential);
    }

    @Override
    public boolean deleteUserById(Long id) {
        try {
            userRepository.deleteById(id);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean updateUserById(Long id, UserCredential updatedUserCredential) {
        Optional<UserCredential> saloonOptional = userRepository.findById(id);
        if(saloonOptional.isPresent()){
            UserCredential userCredential = saloonOptional.get();

            userCredential.setEmail(updatedUserCredential.getEmail());
            userCredential.setName(updatedUserCredential.getName());
            userCredential.setSurname(updatedUserCredential.getSurname());
            userCredential.setPatronymic(updatedUserCredential.getPatronymic());
            userCredential.setPhone(updatedUserCredential.getPhone());
            userCredential.setSalary(updatedUserCredential.getSalary());
            userCredential.setAllowance(updatedUserCredential.getAllowance());
            userCredential.setSchedule(updatedUserCredential.getSchedule());
            userCredential.setSaloonId(updatedUserCredential.getSaloonId());
            userCredential.setRoles(updatedUserCredential.getRoles());
            userCredential.setApproved(updatedUserCredential.isApproved());
            userRepository.save(userCredential);
            return true;
        }
        return false;
    }
}
