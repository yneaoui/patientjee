package com.emsi.patientsmvc.security.services;

import com.emsi.patientsmvc.security.entities.AppUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private SecurityService securityService;

    public UserDetailsServiceImpl(SecurityService securityService) {
        this.securityService = securityService;
    }
//casting appUser to spring security s User?
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       AppUser appUser=securityService.loadUserByUsername(username);


        /*Collection<GrantedAuthority> authorities=new ArrayList();
        appUser.getAppRoleList().forEach(appRole ->{
            SimpleGrantedAuthority authority=new SimpleGrantedAuthority(appRole.getRole());
        });*/

        Collection<GrantedAuthority>authorities=
                appUser.getAppRoleList()
                        .stream()
                        .map(role->new SimpleGrantedAuthority(role.getRole()))
                        .collect(Collectors.toList());


        User user=new User(appUser.getUsername(),appUser.getPassword(),authorities);
        return user;
    }
}
