package com.socialvideo.data.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;

public class SettingsDTO implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	
	private NotificationSettingsDTO notification = new NotificationSettingsDTO();
	private UserDTO user;
	private AvatarDTO avatar;
	

	
	
	
	public SettingsDTO() {}
	
	
	

	public UserDTO getUser() {
		return user;
	}


	public void setUser(UserDTO user) {
		this.user = user;
	}


	public AvatarDTO getAvatar() {
		return avatar;
	}


	public void setAvatar(AvatarDTO avatar) {
		this.avatar = avatar;
	}




	public NotificationSettingsDTO getNotification() {
		return notification;
	}




	public void setNotification(NotificationSettingsDTO notification) {
		this.notification = notification;
	}

	
		

}
