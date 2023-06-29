package com.socialvideo.config;

import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;

import com.socialvideo.visualcaptcha.CaptchaServlet;

import javax.servlet.Filter;

@Configuration
public class WebConfig {

	  
	@Bean
    public Java8TimeDialect java8TimeDialect() {
        return new Java8TimeDialect();
    }
	
	
	
	
/*	   @Bean
	   public ServletRegistrationBean captchaRegistrationBean() {
	      ServletRegistrationBean registration = new ServletRegistrationBean(new CaptchaServlet(), "/captcha/*");
	      registration.addUrlMappings("*.captcha");
	      return registration;
	   }
	*/
	
	
    @Bean
    public Filter characterEncodingFilter() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        return characterEncodingFilter;
    }
    
    
}