package com.aegisep.thymeleaf.user;

import com.aegisep.thymeleaf.database.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

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

        return new CustomUserDetail(loginUser);
    }
}
