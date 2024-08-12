package com.cap.cap10.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


import com.cap.cap10.services.impl.SecurityCustomUserDetailService;


@Configuration
public  class securityConfig {


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

    @Autowired
    private OAuthAuthenticationSucessHandler handler;

    @Autowired
    private AuthFailureHandler authFailureHandler ;

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

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{

        httpSecurity.authorizeHttpRequests(authorize ->{
            //authorize.requestMatchers("/home").permitAll();

            authorize.requestMatchers("/user/**").authenticated();
            authorize.anyRequest().permitAll();
        });

        
        httpSecurity.formLogin(formlogin ->{

            formlogin.loginPage("/login")
            .loginProcessingUrl("/authenticate")
            .defaultSuccessUrl("/user/profile", true)
          //  .failureForwardUrl("/login?error=true")
            .usernameParameter("email")
            .passwordParameter("password")
            .failureHandler(authFailureHandler);
        });

        




        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.logout(logoutForm ->{

            logoutForm.logoutUrl("/logout");
            logoutForm.logoutSuccessUrl("/login?logout=true");
        });


        //oauth2 configuration
        httpSecurity.oauth2Login(oauth ->{
            oauth.loginPage("/login");
            oauth.successHandler(handler);
        });

        return httpSecurity.build();
    }
}
