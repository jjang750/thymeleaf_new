package com.aegisep.thymeleaf.security;

import com.aegisep.thymeleaf.user.CustomUserDetail;
import com.aegisep.thymeleaf.user.CustomUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class CustomAuthenticationManager implements AuthenticationManager {

    private static final Logger log = LoggerFactory.getLogger(CustomAuthenticationManager.class);

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        log.info("authentication.getPrincipal {} ", authentication.getPrincipal().toString());
        log.info("authentication.getCredentials {} ", authentication.getCredentials());

        CustomUserDetail userDetails = userDetailsService.loadUserByUsernameAndPassword(authentication.getPrincipal().toString(), authentication.getCredentials().toString());
        // 각종 처리를 구현
        // 비번이 일치하는지
        // 아이디로 회원을 조회 했을 때 존재하는 회원인지
        // 기타 등등과 적절한 예외 처리
        return new UsernamePasswordAuthenticationToken(userDetails.getUsername()
                , userDetails.getPassword()
                , userDetails.getAuthorities());
    }
}
