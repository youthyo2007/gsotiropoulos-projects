package com.socialvideo.restsecurity;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;

@Component
public class RESTAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
 
	  private static final Logger LOGGER = LoggerFactory.getLogger(RESTAuthenticationSuccessHandler.class);
	  
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {

    	 response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    	 response.getWriter().print("{\"success\": false}");
         response.getWriter().flush();
    }
}