package com.aegisep.thymeleaf.user;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public CustomUserDetail loadUserByUsername(String username) throws UsernameNotFoundException {

        Session session  = sessionFactory.openSession();

        Query<User> query = session.createQuery("From User where user_id =:id", User.class);
        query.setParameter("id", username);

        List<User> users = query.list();

        if(users.isEmpty()) {
            log.error("User ID not found {'"+username+"'}");
            throw new UsernameNotFoundException("User ID not found {'"+username+"'}");
        }
        User loginUser = users.get(0);

        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        org.springframework.security.core.userdetails.User.UserBuilder userBuilder = org.springframework.security.core.userdetails.User.builder().passwordEncoder(encoder::encode);

        UserDetails user = userBuilder.username(loginUser.getUser_id()).password(loginUser.getPasswd())
                .roles(loginUser.getAuth_id()).build();

        return new CustomUserDetail(user);
    }

    public CustomUserDetail loadUserByUsernameAndPassword(String username, String passwd) throws UsernameNotFoundException {

        Session session  = sessionFactory.openSession();

        Query<User> query = session.createQuery("From User where user_id =:id and passwd = md5(:passwd)", User.class);
        query.setParameter("id", username);
        query.setParameter("passwd", passwd);

        List<User> users = query.list();

        if(users.isEmpty()) {
            log.error("User ID not found {'"+username+"'}");
            throw new UsernameNotFoundException("User ID not found {'"+username+"'}");
        }

        User loginUser = users.get(0);

        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        org.springframework.security.core.userdetails.User.UserBuilder userBuilder = org.springframework.security.core.userdetails.User.builder().passwordEncoder(encoder::encode);

        UserDetails user = userBuilder.username(loginUser.getUser_id()).password(loginUser.getPasswd())
                .roles(loginUser.getAuth_id()).build();

        return new CustomUserDetail(user);
    }

    public CustomUserDetail loadUserByEmail(String email) throws UsernameNotFoundException {

        Session session  = sessionFactory.openSession();

        Query<User> query = session.createQuery("From User where email =:email", User.class);
        query.setParameter("email", email);

        List<User> users = query.list();

        if(users.isEmpty()) {
            log.error("email not found {'"+email+"'}");
            throw new UsernameNotFoundException("email not found {'"+email+"'}");
        }
        User loginUser = users.get(0);

        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        org.springframework.security.core.userdetails.User.UserBuilder userBuilder = org.springframework.security.core.userdetails.User.builder().passwordEncoder(encoder::encode);

        UserDetails user = userBuilder.username(loginUser.getUser_id()).password(loginUser.getPasswd())
                .roles(loginUser.getAuth_id()).build();

        return new CustomUserDetail(user);
    }

    public CustomUserDetail loadUserByToken(String token) throws UsernameNotFoundException {

        Session session  = sessionFactory.openSession();

        Query<User> query = session.createQuery("From User where token =:token", User.class);
        query.setParameter("token", token);

        List<User> users = query.list();

        if(users.isEmpty()) {
            log.error("token not found {'"+token+"'}");
            throw new UsernameNotFoundException("token not found {'"+token+"'}");
        }
        User loginUser = users.get(0);

        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        org.springframework.security.core.userdetails.User.UserBuilder userBuilder = org.springframework.security.core.userdetails.User.builder().passwordEncoder(encoder::encode);

        UserDetails user = userBuilder.username(loginUser.getUser_id()).password(loginUser.getPasswd())
                .roles(loginUser.getAuth_id()).build();

        return new CustomUserDetail(user);
    }
}
