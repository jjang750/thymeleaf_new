package com.aegisep.thymeleaf.user;

import com.aegisep.thymeleaf.database.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class CustomUserDetailsService implements UserDetailsService {
    @Override
    public CustomUserDetail loadUserByUsername(String username) throws UsernameNotFoundException {

        Session session  = HibernateUtil.getSession();

        Query<User> query = session.createQuery("From User where user_id =:id", User.class);
        query.setParameter("id", username);

        List<User> users = query.list();

        if(users.isEmpty()) {
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

        Session session  = HibernateUtil.getSession();

        Query<User> query = session.createQuery("From User where user_id =:id and passwd = md5(:passwd)", User.class);
        query.setParameter("id", username);
        query.setParameter("passwd", passwd);

        List<User> users = query.list();

        if(users.isEmpty()) {
            throw new UsernameNotFoundException("User ID not found {'"+username+"'}");
        }

        User loginUser = users.get(0);

        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        org.springframework.security.core.userdetails.User.UserBuilder userBuilder = org.springframework.security.core.userdetails.User.builder().passwordEncoder(encoder::encode);

        UserDetails user = userBuilder.username(loginUser.getUser_id()).password(loginUser.getPasswd())
                .roles(loginUser.getAuth_id()).build();

        return new CustomUserDetail(user);
    }
}
