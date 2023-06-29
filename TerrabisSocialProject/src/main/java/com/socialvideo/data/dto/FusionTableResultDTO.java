package com.socialvideo.data.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FusionTableResultDTO implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	

	private Integer total;
	
	
	private List<PublicVideoPointDTO> points = new ArrayList<>();
	
	
	public FusionTableResultDTO() {}
	

	public Integer getTotal() {
		return total;
	}


	public void setTotal(Integer total) {
		this.total = total;
	}


	public List<PublicVideoPointDTO> getPoints() {
		return points;
	}


	public void setPoints(List<PublicVideoPointDTO> points) {
		this.points = points;
	}
	
	
	
	
	

}
