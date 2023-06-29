package com.socialvideo.data.dto;

import java.io.Serializable;
import java.util.Date;

public class SystemPropertyDTO implements Serializable {


	private static final long serialVersionUID = 1L;
	
	
	private Long id;
	private String key;
	private String descr;
	private String value;
	private Long modifierid;
	private UserDTOSmall modifier;
	private Date datecreated;
	private Date datemodified;
	
	
	public SystemPropertyDTO(Long id, String descr) {
		this.setId(id);
		this.setDescr(descr);
	}
	
	
	public SystemPropertyDTO(Long id, String descr, String value) {
		this.setId(id);
		this.setDescr(descr);
		
	}
	
	
	
	public SystemPropertyDTO(){}






	public String getDescr() {
		return descr;
	}





	public void setDescr(String descr) {
		this.descr = descr;
	}


	public String getValue() {
		return value;
	}


	public void setValue(String value) {
		this.value = value;
	}


	public String getKey() {
		return key;
	}


	public void setKey(String key) {
		this.key = key;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public UserDTOSmall getModifier() {
		return modifier;
	}


	public void setModifier(UserDTOSmall modifier) {
		this.modifier = modifier;
	}


	public Long getModifierid() {
		return modifierid;
	}


	public void setModifierid(Long modifierid) {
		this.modifierid = modifierid;
	}


	public Date getDatecreated() {
		return datecreated;
	}


	public void setDatecreated(Date datecreated) {
		this.datecreated = datecreated;
	}


	public Date getDatemodified() {
		return datemodified;
	}


	public void setDatemodified(Date datemodified) {
		this.datemodified = datemodified;
	}
	
	
	
	
	
	
	

}
