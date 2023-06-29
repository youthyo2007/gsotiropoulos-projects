package com.socialvideo.data.dto;

import java.io.Serializable;

public class IDDescrDTO implements Serializable {


	private static final long serialVersionUID = 1L;
	
	
	private Integer id;
	private String descr;
	
	
	
	public IDDescrDTO(Integer id, String descr) {
		this.setId(id);
		this.setDescr(descr);
	}
	
	
	
	
	public IDDescrDTO(){}



	public IDDescrDTO(Integer id){
		this.id = id;
	}
	
	
	

	public Integer getId() {
		return id;
	}





	public void setId(Integer id) {
		this.id = id;
	}





	public String getDescr() {
		return descr;
	}





	public void setDescr(String descr) {
		this.descr = descr;
	}
	
	
	
	
	
	
	

}
