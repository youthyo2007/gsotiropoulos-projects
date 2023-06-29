package com.socialvideo.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.socialvideo.validation.EmailValidator;
import com.socialvideo.validation.PasswordMatchesValidator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;




public class WebSecurityConfiguration  {

   /* @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
    	
    	httpSecurity.csrf().disable();
    	httpSecurity.authorizeRequests().antMatchers("/").permitAll();
    }
    
    
    */

    @Bean
    public EmailValidator usernameValidator() {
        return new EmailValidator();
    }

    @Bean
    public PasswordMatchesValidator passwordMatchesValidator() {
        return new PasswordMatchesValidator();
    }
    
    
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(11);
    }
    
 

}