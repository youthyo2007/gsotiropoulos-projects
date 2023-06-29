package com.socialvideo.data.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UserProfile;
import org.springframework.stereotype.Component;

import com.socialvideo.data.dto.CurrentUserDTO;
import com.socialvideo.data.dto.UserDTO;
import com.socialvideo.data.services.inter.IUserService;
import com.socialvideo.social.data.dto.SocialProfileDTO;

@Component
public class AccountConnectionSignUpService implements ConnectionSignUp {


    //@Autowired
    //private UserRepository usersRepository;
	
	
    @Autowired
    private IUserService userService;

    @Override
    public String execute(Connection<?> connection) {
        //SocialProfileDTO profile = new SocialProfileDTO();
        UserProfile userProfile = connection.fetchUserProfile();
        UserDTO user = userService.loadUserFromSocialProfile(userProfile);
        return user.getEmail();
    }
}