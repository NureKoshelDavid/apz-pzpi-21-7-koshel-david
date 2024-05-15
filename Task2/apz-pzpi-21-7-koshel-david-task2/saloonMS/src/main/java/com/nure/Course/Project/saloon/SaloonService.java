package com.nure.Course.Project.saloon;

import java.util.List;

public interface SaloonService {
    List<Saloon> findAllByOwnerId(Long id);
    List<Saloon> findAll();
    void createSaloon(Saloon saloon);
    Saloon getSaloonById(Long id);
    Saloon getSaloonByOwnerId(Long id);
    Saloon getSaloonByInviteCode(String inviteCode);

    boolean deleteSaloonById(Long id);

    boolean updateSaloonById(Long id, Saloon updatedSaloon);


}
