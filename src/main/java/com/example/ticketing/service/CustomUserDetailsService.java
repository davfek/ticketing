package com.example.ticketing.service;

import com.example.ticketing.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.example.ticketing.user.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private static final String roleExternal="ROLE_EXTERNAL";
    private static final String roleInternal="ROLE_INTERNAL";
    private static final String roleAdmin="ROLE_ADMIN";
    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User with name:" + username + "not found.");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();

        switch (user.get().getUserRole()){
            case ADMIN:
                authorities.add(new SimpleGrantedAuthority(roleAdmin));
                authorities.add(new SimpleGrantedAuthority(roleExternal));
                authorities.add(new SimpleGrantedAuthority(roleInternal));
                break;
            case INTERNAL:
                authorities.add(new SimpleGrantedAuthority(roleExternal));
                authorities.add(new SimpleGrantedAuthority(roleInternal));
                break;
            default:
                authorities.add(new SimpleGrantedAuthority(roleExternal));
        }

        return new org.springframework.security.core.userdetails.User(
                user.get().getUsername(),
                user.get().getPassword(),
                authorities
        );


    }

}
