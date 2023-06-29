package com.socialvideo.data.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ActivityIdentity implements Serializable {
	
	
	private static final long serialVersionUID = 1L;

	
	
	@JsonProperty("sessionid")
	private String sessionid;

	@JsonProperty("cookieid")
	private String cookieid;

	@JsonProperty("ip")
	private String ip;	

	@JsonProperty("userid")
	private Long userid = new Long(-1);
	
	@JsonProperty("isnewvisit")
	private Boolean isnewvisit;
	
	@JsonProperty("isnewvisitor")
	private Boolean isnewvisitor;
	

	

	public ActivityIdentity(String cookieid, String sessionid,  String ip,  Long userid,  Boolean isnewvisit,  Boolean isnewvisitor) {
		this.userid = userid==null ? -1 : userid;
		this.cookieid = cookieid;
		this.sessionid = sessionid;
		this.ip = ip;
		this.isnewvisit = isnewvisit ==  null ? false : isnewvisit;
		this.isnewvisitor = isnewvisitor ==  null ? false : isnewvisitor;
	}

	
	

	public Long getUserid() {
		return userid;
	}


	public void setUserid(Long userid) {
		this.userid = userid;
	}


	public String getCookieid() {
		return cookieid;
	}


	public void setCookieid(String cookieid) {
		this.cookieid = cookieid;
	}


	public String getIp() {
		return ip;
	}


	public void setIp(String ip) {
		this.ip = ip;
	}


	public Boolean getIsnewvisit() {
		return isnewvisit;
	}


	public void setIsnewvisit(Boolean isnewvisit) {
		this.isnewvisit = isnewvisit;
	}


	public String getSessionid() {
		return sessionid;
	}


	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}



	public Boolean getIsnewvisitor() {
		return isnewvisitor;
	}




	public void setIsnewvisitor(Boolean isnewvisitor) {
		this.isnewvisitor = isnewvisitor;
	}



}
