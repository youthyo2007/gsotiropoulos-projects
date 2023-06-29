package com.socialvideo.data.dto;

import java.io.Serializable;

public class PageNumberDTO implements Serializable {
	


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	private String pageno;

	
	
	
	
	public PageNumberDTO() {
		
		
	}
	
	
	public PageNumberDTO(String pageno) {
		this.pageno = pageno;
		
		
	}
	
	
	
	public String getPageno() {
		return pageno;
	}

	public void setPageno(String pageno) {
		this.pageno = pageno;
	}
	
	
	
	
	

}
