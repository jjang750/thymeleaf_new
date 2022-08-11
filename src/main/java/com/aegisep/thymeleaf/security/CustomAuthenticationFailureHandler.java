package com.aegisep.thymeleaf.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private static final Logger log = LoggerFactory.getLogger(CustomAuthenticationFailureHandler.class);

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception)
            throws IOException, ServletException {
        // 실패로그를 남긴다
        // 실패이벤트를 발송한다

        log.info("onAuthenticationFailure ");

        if(exception instanceof UsernameNotFoundException) {
            request.setAttribute("message", "아이디 또는 비밀번호가 일치하지 않습니다.");
            request.getSession().setAttribute("message", "아이디 또는 비밀번호가 일치하지 않습니다.");
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/login/failed");
        dispatcher.forward(request, response);
    }
}