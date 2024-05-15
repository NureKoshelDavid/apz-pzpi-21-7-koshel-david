package com.nure.Course.Project.saloon;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaloonRepository extends JpaRepository<Saloon,Long> {
    List<Saloon> findAllByOwnerId(Long id);

    Saloon getSaloonByOwnerId(Long id);
    Saloon getSaloonByInviteCode(String inviteCode);
}
