package com.paymybuddy.paymybuddy.utils;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // @formatter:off
        final RequestMatcher[] requestMatcherList = new RequestMatcher[] { 
            antMatcher("/css/**"), 
            antMatcher("/images/**"), 
            antMatcher("/home"),
            antMatcher("/user") 
        };
        http.authorizeHttpRequests(requests -> requests
                .requestMatchers(requestMatcherList)
                .permitAll()
                .anyRequest()
                .authenticated())
        .formLogin(login -> login
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .permitAll()
                .defaultSuccessUrl("/home"))
        .logout(logout -> logout
                .logoutRequestMatcher(antMatcher("/logout"))
                .permitAll());
        // @formatter:on
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(antMatcher("/h2-console/**"));
    }
}
