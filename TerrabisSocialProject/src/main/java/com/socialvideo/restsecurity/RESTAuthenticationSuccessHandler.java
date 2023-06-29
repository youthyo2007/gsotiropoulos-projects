package com.socialvideo.restsecurity;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.socialvideo.data.dto.CurrentUserDTO;

 
@Component
public class RESTAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
 
	
    private static final Logger LOGGER = LoggerFactory.getLogger(RESTAuthenticationSuccessHandler.class);
    
	
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
    	
    	CurrentUserDTO userDetails = (CurrentUserDTO)authentication.getPrincipal();
    	 response.setStatus(HttpServletResponse.SC_OK);
    	 String firstname = userDetails.getUser().getFirstname()!=null ? userDetails.getUser().getFirstname() : " ";

    	 //response.addHeader("userid",userDetails.getUser().getId()+"");
    	 //response.addHeader("firstname",firstname);
    	 
    	 String responseString = "{\"success\":\"true\",\"userid\":"+userDetails.getUser().getId()+",\"firstname\":\""+firstname +"\"}";
    	 response.getWriter().print(responseString);
         response.getWriter().flush();
        //clearAuthenticationAttributes(request);
    }
}