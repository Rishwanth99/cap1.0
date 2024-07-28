package com.cap.cap10.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cap.cap10.services.impl.SecurityCustomUserDetailService;


@Configuration
public class securityConfig {


    /* 

    @Bean
    public UserDetailsService userDetailsService(){

        UserDetails user1 = User
        .withDefaultPasswordEncoder()
        .username("admin123")
        .password("admin123")
        .roles("ADMIN","USER")
        .build();

        
        UserDetails user2 = User
        .withDefaultPasswordEncoder()
        .username("user123")
        .password("password")
        .build();

            var inMemoryUserDetailsManager = new InMemoryUserDetailsManager(user1,user2);

        return inMemoryUserDetailsManager;
    }

    */

    @Autowired
    private SecurityCustomUserDetailService userDetailService;

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){


            DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
            // userdatils object
            daoAuthenticationProvider.setUserDetailsService(userDetailService);
            //password endcoder object
            daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
    }
}
