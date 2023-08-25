package com.paymybuddy.paymybuddy.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    InMemoryUserDetailsManager userDetailsService() {
        final UserDetails user1 = User.withUsername("user1").password(passwordEncoder().encode("user1Pass")).roles("USER").build();
        final UserDetails user2 = User.withUsername("user2").password(passwordEncoder().encode("user2Pass")).roles("USER").build();
        final UserDetails admin = User.withUsername("admin").password(passwordEncoder().encode("adminPass")).roles("ADMIN").build();
        return new InMemoryUserDetailsManager(user1, user2, admin);
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> {
            try {
                csrf.disable()
                        .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests.requestMatchers("/admin/**").hasRole("ADMIN")
                                .requestMatchers("/anonymous*").anonymous().requestMatchers("/login*").permitAll().anyRequest().authenticated())
                        .formLogin(formLogin -> formLogin.loginPage("/login.html").loginProcessingUrl("/perform_login").defaultSuccessUrl("/home.html", true)
                                .failureUrl("/login.html?error=true").failureHandler(authenticationFailureHandler()))
                        .logout(logout -> logout.logoutUrl("/perform_logout").deleteCookies("JSESSIONID").logoutSuccessHandler(logoutSuccessHandler()));
            } catch (final Exception e) {
                e.printStackTrace();
            }
        });
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private AuthenticationFailureHandler authenticationFailureHandler() {
        // TODO Auto-generated method stub
        return null;
    }

    private LogoutSuccessHandler logoutSuccessHandler() {
        // TODO Auto-generated method stub
        return null;
    }
}
