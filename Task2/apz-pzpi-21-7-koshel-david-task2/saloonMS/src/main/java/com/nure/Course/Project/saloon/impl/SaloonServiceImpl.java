package com.nure.Course.Project.saloon.impl;

import com.nure.Course.Project.saloon.Saloon;
import com.nure.Course.Project.saloon.SaloonRepository;
import com.nure.Course.Project.saloon.SaloonService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SaloonServiceImpl implements SaloonService {

    //private List<Saloon> saloons = new ArrayList<>();

    SaloonRepository saloonRepository;

    public SaloonServiceImpl(SaloonRepository saloonRepository) {
        this.saloonRepository = saloonRepository;
    }

    @Override
    public List<Saloon> findAllByOwnerId(Long id) {
        return saloonRepository.findAllByOwnerId(id);
    }

    @Override
    public List<Saloon> findAll() {
        return saloonRepository.findAll();
    }

    @Override
    public void createSaloon(Saloon saloon) {
        saloonRepository.save(saloon);

    }

    @Override
    public Saloon getSaloonById(Long id) {
        return saloonRepository.findById(id).orElse(null);
    }

    @Override
    public Saloon getSaloonByOwnerId(Long id) {
        return saloonRepository.getSaloonByOwnerId(id);
    }

    @Override
    public Saloon getSaloonByInviteCode(String inviteCode) {
        return saloonRepository.getSaloonByInviteCode(inviteCode);
    }

    @Override
    public boolean deleteSaloonById(Long id) {
        try {
            saloonRepository.deleteById(id);
            return true;
        } catch (Exception e){
            return false;
        }

    }

    @Override
    public boolean updateSaloonById(Long id, Saloon updatedSaloon) {
        Optional<Saloon> saloonOptional = saloonRepository.findById(id);
            if(saloonOptional.isPresent()){
                Saloon saloon = saloonOptional.get();

                saloon.setName(updatedSaloon.getName());
                saloon.setRegion(updatedSaloon.getRegion());
                saloon.setCity(updatedSaloon.getCity());
                saloon.setAddress(updatedSaloon.getAddress());
                saloon.setSchedule(updatedSaloon.getSchedule());
                saloon.setOwnerId(updatedSaloon.getOwnerId());
                saloonRepository.save(saloon);
                return true;
            }
        return false;
        }
}

