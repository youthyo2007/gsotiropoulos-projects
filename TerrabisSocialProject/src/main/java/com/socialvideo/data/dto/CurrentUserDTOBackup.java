package com.socialvideo.data.dto;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;


public class CurrentUserDTOBackup extends org.springframework.security.core.userdetails.User {

	private static final long serialVersionUID = 1L;
	
	
	private UserDTO user;

/*    public CurrentUserDTO(UserDTO user) {
    	super(user.getEmail(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getRoleListNamesAsCommaSeparated()));
    	this.setUser(user);
    	
    }*/

	
	
	
	
    public CurrentUserDTOBackup(String username, String password, Boolean enabled, boolean b, boolean c, boolean d, List<GrantedAuthority> authorities) {
		super(username,password, enabled, true, true, true, authorities);
	}

	public UserDTO getUser() {
        return user;
    }

    public Long getId() {
        return user!=null ? user.getId() : null;
    }

    public RoleDTO getRole() {
        return getUser().getRole();
    }
    
    
    @Override
    public String toString() {
        return "CurrentUser{" +
                "user=" + getUser() +
                "} " + super.toString();
    }

	public void setUser(UserDTO user) {
		this.user = user;
	}
}