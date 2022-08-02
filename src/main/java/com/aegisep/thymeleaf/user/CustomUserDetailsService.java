package com.aegisep.thymeleaf.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {
    @Override
    public CustomUserDetail loadUserByUsername(String username) throws UsernameNotFoundException {



        return null;
    }
}
