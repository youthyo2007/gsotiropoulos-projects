package com.socialvideo.web.advice;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.socialvideo.data.dto.CurrentUserDTO;

@ControllerAdvice
public class CurrentUserControllerAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(CurrentUserControllerAdvice.class);

    @ModelAttribute("currentUser")
    public CurrentUserDTO getCurrentUser(Authentication authentication) {
        return (authentication == null) ? null : (CurrentUserDTO) authentication.getPrincipal();
    }


}