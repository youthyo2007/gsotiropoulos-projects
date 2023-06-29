package com.socialvideo.web.events;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;

import com.socialvideo.data.dto.UserDTO;

@SuppressWarnings("serial")
public class OnRegistrationCompleteEvent extends ApplicationEvent {

    private final String appUrl;
    private final Locale locale;
    private final UserDTO user;

    public OnRegistrationCompleteEvent(final UserDTO user, final Locale locale, final String appUrl) {
        super(user);
        this.user = user;
        this.locale = locale;
        this.appUrl = appUrl;
    }

    
    public String getAppUrl() {
        return appUrl;
    }

    public Locale getLocale() {
        return locale;
    }

    public UserDTO getUser() {
        return user;
    }

}
