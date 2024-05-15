package com.nure.Course.Project.saloon;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import java.util.ArrayList;
import java.util.List;
import java.util.SplittableRandom;

@RestController
public class SaloonController {
    private SaloonService saloonService;

    public SaloonController(SaloonService saloonService) {
        this.saloonService = saloonService;
    }

    @GetMapping("/saloons/all/{id}")
    public List<Saloon> findAllByOwnerId(@PathVariable Long id){
        return saloonService.findAllByOwnerId(id);
    }

    @GetMapping("/saloons/all")
    public List<Saloon> findAll(){
        return saloonService.findAll();
    }

    @PostMapping("/saloons")
    public ResponseEntity<String> createSaloon(@Valid @RequestBody Saloon saloon, BindingResult result){
        if (result.hasErrors()) {
            // Обрабатываем ошибки валидации
            StringBuilder errors = new StringBuilder();
            result.getAllErrors().forEach(error -> errors.append(error.getDefaultMessage()).append("\n"));
            return ResponseEntity.badRequest().body(errors.toString());
        }
        saloonService.createSaloon(saloon);
        return ResponseEntity.ok("Saloon was added successfully");
    }

    @GetMapping("/saloons/{id}")
    public Saloon getSaloonById(@PathVariable Long id){
        Saloon saloon = saloonService.getSaloonById(id);
        return saloon;
    }

    @DeleteMapping("/saloons/{id}")
    public ResponseEntity<String> deleteSaloonById(@PathVariable Long id){
        boolean deleted = saloonService.deleteSaloonById(id);
        if (deleted)
            return new ResponseEntity<>("Saloon was deleted successfully", HttpStatus.OK);
        return new ResponseEntity<>("Saloon with that Id was not found",HttpStatus.NOT_FOUND);
    }

    @PutMapping("/saloons/{id}")
    public ResponseEntity<String> updateSaloonById(@PathVariable Long id, @RequestBody Saloon updatedSaloon){
        boolean updated = saloonService.updateSaloonById(id,updatedSaloon);
        if (updated)
            return new ResponseEntity<>("Saloon was updated successfully", HttpStatus.OK);
        return new ResponseEntity<>("Saloon with that Id was not found",HttpStatus.NOT_FOUND);
    }

    @GetMapping("/saloons/invite-code/{inviteCode}")
    public Long getSaloonIdByInviteCode(@PathVariable String inviteCode){
        Saloon saloon;
        try{
            saloon = saloonService.getSaloonByInviteCode(inviteCode);
        }
        catch (Exception e){
            return null;
        }
        return saloon.getId();
    }
}
