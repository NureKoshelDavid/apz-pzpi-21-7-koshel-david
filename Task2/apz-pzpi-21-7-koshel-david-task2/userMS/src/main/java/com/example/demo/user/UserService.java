package com.example.demo.user;

import java.util.List;

public interface UserService {

    List<UserCredential> findAll();
    List<UserCredential> findAllBySaloonId(Long id);
    UserCredential getUserById(Long id);
    UserCredential getUserByEmail(String email);
    void createStaff(UserCredential staff);
    boolean deleteUserById(Long id);
    boolean updateUserById(Long id, UserCredential updatedUserCredential);


}
