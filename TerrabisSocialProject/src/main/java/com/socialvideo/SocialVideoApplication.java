package com.socialvideo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

import com.socialvideo.data.services.VimeoRestService;
import com.socialvideo.data.services.inter.IVimeoService;


@SpringBootApplication
@EnableAsync
public class SocialVideoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SocialVideoApplication.class, args);
    }
    
 
    
    @Bean(name="asyncRestTemplate")
    AsyncRestTemplate asyncRestTemplate() {
        return new AsyncRestTemplate();
    }
    
    
  
    @Bean(name="restTemplate")
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    

    @Bean
    IVimeoService vimeoService() {
        return new VimeoRestService();
    }
    
    
    
    
    
    
    
    
    
}
