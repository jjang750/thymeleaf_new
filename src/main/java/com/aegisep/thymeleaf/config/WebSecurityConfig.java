package com.aegisep.thymeleaf.config;

import com.aegisep.thymeleaf.security.*;
import com.aegisep.thymeleaf.user.CustomUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled=true)
public class WebSecurityConfig {

    private static final Logger log = LoggerFactory.getLogger(WebSecurityConfig.class);

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{

        log.info("filterChain start");

        httpSecurity.authorizeRequests()
                .antMatchers("/index", "/auth", "/login", "/resources/**", "/static/**").permitAll()
                .antMatchers("/admin/**").hasAnyRole("ADMIN")
                .antMatchers("/manager/**").hasAnyRole("MANAGER")
                .antMatchers("/user/**").hasAnyRole("USER")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/auth")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true).deleteCookies("JSESSIONID")
                .and()
                .csrf().disable()

                .addFilterBefore(customAuthenticationFilter(),
                        UsernamePasswordAuthenticationFilter.class);

        log.info("filterChain end");

        return httpSecurity.build();
    }
    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CustomAuthenticationFilter customAuthenticationFilter() throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(customAuthenticationManager());
        customAuthenticationFilter.setFilterProcessesUrl("/auth");
        customAuthenticationFilter.setAuthenticationSuccessHandler(customLoginSuccessHandler());
        customAuthenticationFilter.setAuthenticationFailureHandler(customAuthenticationFailureHandler());
        customAuthenticationFilter.afterPropertiesSet();
        return customAuthenticationFilter;
    }

    @Bean
    public CustomLoginSuccessHandler customLoginSuccessHandler() {
        log.info("customLoginSuccessHandler :: CustomLoginSuccessHandler ");
        return new CustomLoginSuccessHandler();
    }

    @Bean
    public CustomAuthenticationFailureHandler customAuthenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

    @Bean
    public CustomAuthenticationManager customAuthenticationManager() {
        log.info("customAuthenticationManager :: CustomAuthenticationManager ");
        return new CustomAuthenticationManager();
    }

    @Bean
    public CustomUserDetailsService getUserDetailsService() {
        return new CustomUserDetailsService();
    }

}
