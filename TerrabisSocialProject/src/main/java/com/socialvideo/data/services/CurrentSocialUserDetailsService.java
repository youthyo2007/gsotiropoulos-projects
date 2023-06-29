package com.socialvideo.data.services;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;

import com.socialvideo.data.dto.CurrentUserDTO;
import com.socialvideo.data.dto.RoleDTO;
import com.socialvideo.data.dto.UserDTO;

 
 

public class CurrentSocialUserDetailsService implements SocialUserDetailsService {

	@Autowired
    private  UserService userService;
    

	public CurrentSocialUserDetailsService() {}
	
	
  	public SocialUserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
  	
  		
  		System.out.println("CurrentSocialUserDetailsService");
  
  		
  	  UserDTO userDTO = userService.fetchUserUponAuthentication(email);
  	 if(userDTO==null) {
      	System.out.println("user is null");
  		 throw new UsernameNotFoundException(String.format("User with email=%s was not found", email));
  	 }
  	  
  	  
  	 List<GrantedAuthority> authorities =  buildUserAuthority(userDTO.getRoles());
  	 return buildUserForAuthentication(userDTO, authorities);
  		
  	}
  	
  	
  	
  	private SocialUser buildUserForAuthentication(UserDTO user, List<GrantedAuthority> authorities) {
  		
  		CurrentUserDTO springAuthUserDTO = new CurrentUserDTO(user.getUsername(), user.getPassword(), user.getEnabled(), true, true, true, authorities);
  		springAuthUserDTO.setUser(user);

  		
  		//UPDATE LAST LOGIN DATE
  		userService.updateLastLoginToNow(user.getId());
  		
  		
  		return springAuthUserDTO;
  	}

  	private List<GrantedAuthority> buildUserAuthority(List<RoleDTO> userRoles) {

  		HashSet<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

  		// Build user's authorities
  		for (RoleDTO userRole : userRoles) {
  			setAuths.add(new SimpleGrantedAuthority(userRole.getName()));
  		}

  		List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);

  		return Result;
  	}
  	
  	
  	
  	
  	
    
    
    
    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException, DataAccessException {
    	System.out.println("USER ID: IS"+userId);
        UserDetails userDetails = loadUserByUsername(userId);
        return (SocialUserDetails) userDetails;
    }
}