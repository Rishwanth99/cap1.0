package com.cap.cap10.helpers;



import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class Helper {


    public static String getEmailOfLoggeduser(Authentication authentication){


        // google or github logged user
        // then it we need fetch the email by using id stored in getName() field
        if(authentication instanceof OAuth2AuthenticationToken){

            //step 1 we need to whethe user is from google or github

            var oauth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;

            var clientId = oauth2AuthenticationToken.getAuthorizedClientRegistrationId();

            var oauthUser =(OAuth2User) authentication.getPrincipal();

            String username="";
            
            //logged user is googel client
            if(clientId.equalsIgnoreCase("google")){


               username= oauthUser.getAttribute("email").toString();

            }
            else if(clientId.equalsIgnoreCase("github")){
                // LOgged user is github client

                username= oauthUser.getAttribute("email") != null ? 
                oauthUser.getAttribute("email").toString() : oauthUser.getAttribute("login").toString() + "@gmail.com";

            }
            return username;
        }
        else{
            return authentication.getName();
        } 

        
    }


    //get link for email verification
    public static String getLinkForEmailVerification(String emailToken) {
        
        
        String  link = "http://localhost:8080/auth/verify?token=" + emailToken;

        return link;
    }
}
