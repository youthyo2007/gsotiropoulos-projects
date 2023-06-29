package com.socialvideo.web.exceptions;

public class SecurityException extends Exception {
	
	
	private String redirect;
	
	
	public SecurityException() {}
	public SecurityException(String redirect) {
		this.redirect = redirect;
	}

	
	public String getRedirect() {
		return redirect;
	}


	public void setRedirect(String redirect) {
		this.redirect = redirect;
	}
	
	

}
