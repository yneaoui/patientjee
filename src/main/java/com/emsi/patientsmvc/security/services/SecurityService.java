package com.emsi.patientsmvc.security.services;

import com.emsi.patientsmvc.security.entities.AppUser;
import com.emsi.patientsmvc.security.entities.AppRole;
public interface SecurityService {
    AppUser saveNewUser(String username,String password,String rePassword);
    AppRole saveNewRole(String role,String description);
    void addRoleToUser(String username,String role);
    void removeRoleFromUser(String username,String role);
    AppUser loadUserByUsername(String username);
}
