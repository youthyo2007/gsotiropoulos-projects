package com.socialvideo.data.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.socialvideo.data.dto.CurrentUserDTO;
import com.socialvideo.data.dto.UserDTO;

//@Service
public class CurrentUserDetailsServiceBackup {

/*    private static final Logger LOGGER = LoggerFactory.getLogger(CurrentUserDetailsServiceBackup.class);
    private final UserService userService;

    //@Autowired
    public CurrentUserDetailsServiceBackup(UserService userService) {
        this.userService = userService;
    }
    
    

    //@Override
    public CurrentUserDTO loadUserByUsername(String email) throws UsernameNotFoundException {
        LOGGER.info("Authenticating user with email={}"+email, email.replaceFirst("@.*", "@***"));
        UserDTO userDTO = userService.fetchUserUponAuthentication(email);
        System.out.println("Roles fetched:"+userDTO.getRoleListNamesAsCommaSeparated());
        if(userDTO==null)
        	throw new UsernameNotFoundException(String.format("User with email=%s was not found", email));

        
        
        //return new CurrentUserDTO(userDTO);
    }
*/
}
