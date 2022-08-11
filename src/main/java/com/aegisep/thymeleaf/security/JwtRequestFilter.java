package com.aegisep.thymeleaf.security;


import com.aegisep.thymeleaf.Constants;
import com.aegisep.thymeleaf.user.CustomUserDetail;
import com.aegisep.thymeleaf.user.CustomUserDetailsService;
import com.aegisep.thymeleaf.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
@WebFilter(urlPatterns = "/api/*")
@Order(0)
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtRequestFilter.class);

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        if(!path.contains("/api")) {
            log.info("doFilterInternal =" + path);
            filterChain.doFilter(request, response);
            return;
        }

        log.info("doFilterInternal start path : " + path);

        final String authorizationHeader = request.getHeader("Authorization");

        log.info("doFilterInternal Authorization : " + authorizationHeader);

        if(authorizationHeader == null) {
            response.setStatus(401);
            return;
        }

        try {
            SecurityContext context = SecurityContextHolder.getContext();

            CustomUserDetail userDetails = userDetailsService.loadUserByToken(authorizationHeader);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails.getUsername(),
                    userDetails.getPassword(),
                    userDetails.getAuthorities());

            context.setAuthentication(authentication);

        }catch (UsernameNotFoundException ex) {
            log.error(ex.getLocalizedMessage());
            throw new ServletException(ex);
        }




        log.info("doFilterInternal authorizationHeader");
        filterChain.doFilter(request, response);
    }
}

