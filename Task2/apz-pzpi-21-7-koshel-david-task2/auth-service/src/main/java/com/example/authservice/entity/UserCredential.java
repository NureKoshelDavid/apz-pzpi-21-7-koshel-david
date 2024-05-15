package com.example.authservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCredential {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String name;
    private String surname;
    private String patronymic;
    private String phone;
    private int salary;
    private int allowance;
    private String schedule;
    private Long saloonId;
    private String password;
    private String roles;
    private boolean isApproved=true;
}
