package com.emsi.patientsmvc.security.repositories;

import com.emsi.patientsmvc.security.entities.AppRole;
import com.emsi.patientsmvc.security.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRole,String> {
    AppRole findByRole(String role);


}
