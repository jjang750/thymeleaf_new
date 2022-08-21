package com.aegisep.thymeleaf.user;

import com.aegisep.thymeleaf.repository.CustomUserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@AllArgsConstructor
@NoArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);
    @Autowired
    CustomUserRepository customUserRepository;

    @Override
    public CustomUserDetail loadUserByUsername(String username) throws UsernameNotFoundException {

        User loginUser = customUserRepository.findByUserid(username);

        if(loginUser == null) {
            log.error("User ID not found {'"+username+"'}");
            throw new UsernameNotFoundException("User ID not found {'"+username+"'}");
        }

        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        org.springframework.security.core.userdetails.User.UserBuilder
                userBuilder = org.springframework.security.core.userdetails.User.builder().passwordEncoder(encoder::encode);

        UserDetails user = userBuilder.username(loginUser.getUser_id()).password(loginUser.getPasswd())
                .roles(loginUser.getAuth_id()).build();

        return new CustomUserDetail(user);
    }

    public CustomUserDetail loadUserByUsernameAndPassword(String username, String passwd) throws UsernameNotFoundException {

        User loginUser = customUserRepository.findByUsernameAndPassword(username, passwd);

        if(loginUser == null) {
            log.error("User ID not found {'"+username+"'}");
            throw new UsernameNotFoundException("User ID not found {'"+username+"'}");
        }

        log.info(loginUser.toString());

        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        org.springframework.security.core.userdetails.User.UserBuilder
                userBuilder = org.springframework.security.core.userdetails.User.builder().passwordEncoder(encoder::encode);

        UserDetails user = userBuilder.username(loginUser.getUser_id()).password(loginUser.getPasswd())
                .roles(loginUser.getAuth_id()).build();

        return new CustomUserDetail(user);
    }

    public CustomUserDetail loadUserByToken(String token) throws UsernameNotFoundException {

        User loginUser = customUserRepository.findByToken(token);

        if(loginUser == null) {
            log.error("User token not found {'"+token+"'}");
            throw new UsernameNotFoundException("User token not found {'"+token+"'}");
        }

        log.info(loginUser.toString());

        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        org.springframework.security.core.userdetails.User.UserBuilder
                userBuilder = org.springframework.security.core.userdetails.User.builder().passwordEncoder(encoder::encode);

        UserDetails user = userBuilder.username(loginUser.getUser_id()).password(loginUser.getPasswd())
                .roles(loginUser.getAuth_id()).build();

        return new CustomUserDetail(user);
    }
}
