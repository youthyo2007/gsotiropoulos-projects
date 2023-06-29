package com.socialvideo.data.dto;


public class CaptchaSecurityDTO  { 




	private String uuid;
	private String securitytoken;
	private String expires;
	private String ip;

	
	
	
	
	public CaptchaSecurityDTO() {}



	public String getUuid() {
		return uuid;
	}



	public void setUuid(String uuid) {
		this.uuid = uuid;
	}


	public String getIp() {
		return ip;
	}



	public void setIp(String ip) {
		this.ip = ip;
	}



	public String getExpires() {
		return expires;
	}



	public void setExpires(String expires) {
		this.expires = expires;
	}



	public String getSecuritytoken() {
		return securitytoken;
	}



	public void setSecuritytoken(String securitytoken) {
		this.securitytoken = securitytoken;
	}


	

}
