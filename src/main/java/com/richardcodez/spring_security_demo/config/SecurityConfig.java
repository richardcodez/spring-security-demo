package com.richardcodez.spring_security_demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;  // we need class implementation for this service

    @Bean
    public AuthenticationProvider authProvider(){

        // connects with database
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());

        return provider;

    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // using anonymous classes

//        Customizer<CsrfConfigurer<HttpSecurity>> customCsrf = new Customizer<CsrfConfigurer<HttpSecurity>>() {
//            @Override
//            public void customize(CsrfConfigurer<HttpSecurity> configurer) {
//                configurer.disable();
//            }
//        };
//
//        http.csrf(customCsrf);

        // using lambdas

        http.csrf(customizer -> customizer.disable());
        http.authorizeHttpRequests(request -> request.anyRequest().authenticated());
  //        http.formLogin(Customizer.withDefaults());    // no longer needed for stateless
        http.httpBasic(Customizer.withDefaults());
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(){

        UserDetails user = User.withDefaultPasswordEncoder()
                                .username("chite")
                                .password("1234")
                                .roles("USER")
                                .build();

        UserDetails admin = User.withDefaultPasswordEncoder()
                                .username("admin")
                                .password("1234")
                                .roles("ADMIN")
                                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

}
