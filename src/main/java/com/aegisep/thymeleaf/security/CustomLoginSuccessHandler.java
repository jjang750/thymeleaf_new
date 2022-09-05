package com.aegisep.thymeleaf.security;

import com.aegisep.thymeleaf.Constants;
import com.aegisep.thymeleaf.user.CustomUserDetail;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;

        CustomUserDetail detail = (CustomUserDetail) token.getDetails();

        request.getSession(true).setAttribute(Constants.SESSION_ID, detail);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String auth = (token.getAuthorities().size() > 0)?
                token.getAuthorities().toArray()[0].toString():
                "/index";

        if(auth.contains("ADMIN")) {
            auth = "/admin";
        } else if (auth.contains("MANAGER")) {
            auth = "/manager";
        } else {
            auth = "/index";
        }

        response.sendRedirect(auth);
    }

}