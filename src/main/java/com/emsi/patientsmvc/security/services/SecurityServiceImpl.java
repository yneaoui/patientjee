package com.emsi.patientsmvc.security.services;

import com.emsi.patientsmvc.security.entities.AppRole;
import com.emsi.patientsmvc.security.entities.AppUser;
import com.emsi.patientsmvc.security.repositories.AppRoleRepository;
import com.emsi.patientsmvc.security.repositories.AppUserRepository;
import groovy.util.logging.Slf4j;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class SecurityServiceImpl implements SecurityService {
    private AppUserRepository appUserRepository;
    private AppRoleRepository appRoleRepository;
    private PasswordEncoder passwordEncoder;
    @Override
    public AppUser saveNewUser(String username, String password, String rePassword) {
        if(!password.equals(rePassword))throw new RuntimeException("Password not match");
        String hashedPWD=passwordEncoder.encode(password);

AppUser appUser=new AppUser();
appUser.setUserId(UUID.randomUUID().toString());
appUser.setUsername(username);
appUser.setPassword(hashedPWD);
appUser.setActive(true);
AppUser savedAppUser=appUserRepository.save(appUser);

        return savedAppUser;
    }

    @Override
    public AppRole saveNewRole(String role, String description) {

        AppRole appRole = appRoleRepository.findByRole(role);
        if(appRole!=null)throw new RuntimeException("Role "+role+" already exists!");
        appRole=new AppRole();
        appRole.setRole(role);
        appRole.setDescription(description);
        AppRole savedAppRole=appRoleRepository.save(appRole);
        return savedAppRole;
    }

    @Override
    public void addRoleToUser(String username, String role) {
        AppUser appUser=appUserRepository.findByUsername(username);
        if(appUser==null)throw new RuntimeException("User "+username+" not found!");
        AppRole appRole=appRoleRepository.findByRole(role);
        if(appRole==null)throw new RuntimeException("Role "+role+" not found!");
        appUser.getAppRoleList().add(appRole);
    }

    @Override
    public void removeRoleFromUser(String username, String role) {
        AppUser appUser=appUserRepository.findByUsername(username);
        if(appUser!=null)throw new RuntimeException("User "+username+" not found!");
        AppRole appRole=appRoleRepository.findByRole(role);
        if(appRole!=null)throw new RuntimeException("Role "+role+" not found!");
        appUser.getAppRoleList().remove(appRole);
    }

    @Override
    public AppUser loadUserByUsername(String username) {

        return appUserRepository.findByUsername(username);
    }
}
