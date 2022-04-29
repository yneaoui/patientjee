package com.emsi.patientsmvc.security.repositories;

import com.emsi.patientsmvc.security.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser,String> {
    AppUser findByUsername(String Username);


}
