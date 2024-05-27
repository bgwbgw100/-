package com.example.demo.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig  {

    private final CustomAuthenticationFailureHandler failureHandler;

    private final CustomUserDetailService userDetailService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .failureHandler(failureHandler)
                        .defaultSuccessUrl("/home", true)
                        .failureUrl("/login?fail=true")
                ).logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/home")
                .permitAll()
        ).authorizeHttpRequests(authorizeRequest ->
                authorizeRequest
                        .requestMatchers(
                                AntPathRequestMatcher.antMatcher("/**/update/**")
                                ,AntPathRequestMatcher.antMatcher("/**/create/**")
                                ,AntPathRequestMatcher.antMatcher("/**/delete/**")
                        ).authenticated()
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/admin/**")).hasAnyRole("ADMIN")
                        .anyRequest().permitAll()


        );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

