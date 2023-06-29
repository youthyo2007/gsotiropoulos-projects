package com.socialvideo.data.dto;

import java.io.Serializable;

public class ExploreDTO implements Serializable { 
	
	
	private static final long serialVersionUID = 1L;
	
	private String  center;

	private String zoom;
	
	private String title;
	
	
	
	public ExploreDTO() {}






	public String getCenter() {
		return center;
	}







	public void setCenter(String center) {
		this.center = center;
	}







	public String getZoom() {
		return zoom;
	}







	public void setZoom(String zoom) {
		this.zoom = zoom;
	}







	public String getTitle() {
		return title;
	}







	public void setTitle(String title) {
		this.title = title;
	}




}
