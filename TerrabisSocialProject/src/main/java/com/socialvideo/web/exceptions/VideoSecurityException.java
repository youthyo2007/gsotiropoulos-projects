package com.socialvideo.web.exceptions;

public class VideoSecurityException extends Exception {
	
	
	private String redirect;

	
	
	public VideoSecurityException() {}
	public VideoSecurityException(String redirect) {
		this.redirect = redirect;
	}

	
	public String getRedirect() {
		return redirect;
	}


	public void setRedirect(String redirect) {
		this.redirect = redirect;
	}
	
	

}
