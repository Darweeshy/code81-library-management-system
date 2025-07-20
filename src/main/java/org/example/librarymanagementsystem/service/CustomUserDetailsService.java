package org.example.librarymanagementsystem.service;

import lombok.RequiredArgsConstructor;
import org.example.librarymanagementsystem.model.SystemUser;
import org.example.librarymanagementsystem.repo.SystemUserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final SystemUserRepository systemUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SystemUser systemUser = systemUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        // Map roles from our Role entity to Spring Security's GrantedAuthority
        Set<GrantedAuthority> authorities = systemUser.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toSet());

        // Return a Spring Security User object
        return new User(systemUser.getUsername(), systemUser.getPassword(), authorities);
    }
}