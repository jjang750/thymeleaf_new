package com.aegisep.thymeleaf.config;

import com.aegisep.thymeleaf.security.CustomAuthenticationFailureHandler;
import com.aegisep.thymeleaf.security.CustomAuthenticationManager;
import com.aegisep.thymeleaf.security.CustomLoginSuccessHandler;
import com.aegisep.thymeleaf.user.CustomUserDetailsService;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled=true)
public class WebSecurityConfig {

    private static final Logger log = LoggerFactory.getLogger(WebSecurityConfig.class);
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{

        log.info("filterChain start");

        httpSecurity.authorizeRequests()
                .antMatchers("/api", "/index", "/auth", "/login", "/signin", "/signin/**", "/signing", "/assets/**", "/images/**").permitAll()
                .antMatchers("/admin/**").hasAnyRole("ADMIN")
                .antMatchers("/manager/**").hasAnyRole("MANAGER")
                .antMatchers("/user/**").hasAnyRole("USER")
                .antMatchers("/api/**").hasAnyRole("API")

                .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .loginPage("/login")
                    .loginProcessingUrl("/auth")
                    .failureUrl("/index") //your-unsuccessful-authentication-url-here

                .and()
                    .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")
                    .invalidateHttpSession(true) //?????? ?????????\
//                .invalidateHttpSession(true).deleteCookies(Constants.COOKIE_ID)

                .and()// ?????? ??? ????????? ?????? ??????
                    .exceptionHandling()
                    .accessDeniedPage("/index")

                .and()
                .cors().and().csrf().disable()

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
    public UsernamePasswordAuthenticationFilter customAuthenticationFilter(){
        UsernamePasswordAuthenticationFilter customAuthenticationFilter = new UsernamePasswordAuthenticationFilter(customAuthenticationManager());
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
        log.info("customAuthenticationFailureHandler :: CustomAuthenticationFailureHandler ");
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

    @Bean(name="entityManagerFactory")
    public SessionFactory buildSessionFactory() {
        try {
            org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration();
            configuration.configure("hibernate.cfg.xml");
            return configuration.buildSessionFactory();
        }
        catch (Throwable ex) {
            log.error(ex.getLocalizedMessage(), ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
}
