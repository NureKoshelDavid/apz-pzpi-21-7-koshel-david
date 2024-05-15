package com.nure.Course.Project.saloon;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Entity
public class Saloon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "Name is required")
    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @NotNull(message = "Address is required")
    @NotEmpty(message = "Address cannot be empty")
    private String region;
    @NotNull(message = "Address is required")
    @NotEmpty(message = "Address cannot be empty")
    private String city;

    @NotNull(message = "Address is required")
    @NotEmpty(message = "Address cannot be empty")
    private String address;
    private String schedule;
    @Column(nullable = false,unique = true)
    private String inviteCode;
    private Long ownerId;
    private boolean isActive=true;


    public Saloon(){

    }

    @PrePersist
    public void generateInviteCode() {
        // Генерация inviteCode (ваш код генерации)
        this.inviteCode = UUID.randomUUID().toString().substring(0, 8);;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }


    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
