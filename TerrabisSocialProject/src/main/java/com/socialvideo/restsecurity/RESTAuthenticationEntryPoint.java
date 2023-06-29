package com.socialvideo.restsecurity;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;


 
@Component
public class RESTAuthenticationEntryPoint implements AuthenticationEntryPoint {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(RESTAuthenticationEntryPoint.class);
    
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
    	LOGGER.error("authentication entry point");
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
}




