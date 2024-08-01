package com.cap.cap10.config;

import java.io.IOException;
import java.util.List;
import java.util.UUID;  
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.cap.cap10.entities.Providers;
import com.cap.cap10.entities.User;
import com.cap.cap10.helpers.AppConstants;
import com.cap.cap10.repositories.UserRepo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthAuthenticationSucessHandler implements AuthenticationSuccessHandler {

    Logger logger = LoggerFactory.getLogger(OAuthAuthenticationSucessHandler.class);

    @Autowired
    private UserRepo userRepo;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        
        
                logger.info("OAuthAuthenticationSuccessHandler");

                var oauth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;

                String authorizedClientRegistrationId = oauth2AuthenticationToken.getAuthorizedClientRegistrationId();

                logger.info(authorizedClientRegistrationId);


                 // save the data in database once after the login
                 //Identify from which provider the user is trying login and save in the database accordingly
                 
                 var oauthUser=(DefaultOAuth2User) authentication.getPrincipal();

                 User user = new User();

                 user.setUserId(UUID.randomUUID().toString());
                 user.setRoleList(List.of(AppConstants.ROLE_USER));
                 user.setEmailVerified(true);
                 user.setEnabled(true);
                 user.setPassword("dummy password from "+authorizedClientRegistrationId);

                  //google provider
                 if(authorizedClientRegistrationId.equalsIgnoreCase("google")){


                    user.setEmail(oauthUser.getAttribute("email").toString());
                    user.setProfilePic(oauthUser.getAttribute("picture").toString());
                    user.setName(oauthUser.getAttribute("name").toString());
                    user.setProviderUserId(oauthUser.getName());
                    user.setProviders(Providers.GOOGLE);
                    user.setAbout("This is acount is created by google");

                 }
                 else if (authorizedClientRegistrationId.equalsIgnoreCase("github")) {
                    //github provider

                    String email = oauthUser.getAttribute("email") != null ? 
                     oauthUser.getAttribute("email").toString() : oauthUser.getAttribute("login").toString() + "@gmail.com";

                     String picture = oauthUser.getAttribute("avatar_url").toString();

                     String name = oauthUser.getAttribute("login").toString();

                     String provideUserId = oauthUser.getName();

                     user.setEmail(email);
                     user.setProfilePic(picture);
                     user.setName(name);
                     user.setProviderUserId(provideUserId);
                     user.setProviders(Providers.GITHUB);
                     user.setAbout("This is acount is created by github...");


                 }
                 else{
                    logger.info("OAuthenticationSucessHandler: Unknown Provider");
                 }
                





                 

                 /* 
                DefaultOAuth2User user=(DefaultOAuth2User) authentication.getPrincipal();

                String email=user.getAttribute("email").toString();

                String name = user.getAttribute("name").toString();

                String profilePic = user.getAttribute("picture").toString();


                User user2 = new User();

                user2.setEmail(email);
                user2.setName(name);
                user2.setPassword("password");
                user2.setProfilePic(profilePic);
                user2.setUserId(UUID.randomUUID().toString());
                user2.setProviders(Providers.GOOGLE);
                user2.setEmailVerified(true);
                user2.setEnabled(true);

                user2.setProviderUserId(user.getName());

                user2.setRoleList(List.of(AppConstants.ROLE_USER));
                
                user2.setAbout("This account is created using google");


                

                */


                User user3 = userRepo.findByEmail(user.getEmail()).orElse(null);

                if(user3 == null){

                    userRepo.save(user);
                    logger.info("User saved"+user.getEmail());
                }

                new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");
                
            }

}
