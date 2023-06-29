package com.socialvideo.data.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import javax.persistence.Column;

import org.apache.ibatis.session.RowBounds;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.socialvideo.data.dto.maps.BoundsDTO;
import com.socialvideo.data.dto.maps.PointDTO;


@JsonIgnoreProperties(ignoreUnknown = true)
public class AdminQueryDTO implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	

	@JsonProperty("advfilter")
	private AdvancedFiltersDTO advfilter = new AdvancedFiltersDTO();
	
	@JsonProperty("filter")
	private String filter = "all";

	@JsonProperty("view")
	private String view = "published";

	@JsonProperty("subfilter")
	private String subfilter = "";
	

	@JsonProperty("filteron")
	private Boolean filteron = false;
	
	
	public AdminQueryDTO() {}
	
	
	
	
	public AdminQueryDTO(Integer publishStatus,Boolean terrabiscomment) {
		advfilter = new AdvancedFiltersDTO();
		advfilter.setStatus(publishStatus);
		advfilter.setTerrabiscomment(terrabiscomment);
	}

	
	
	
	public AdvancedFiltersDTO getAdvfilter() {
		return advfilter;

	}
	
	
	public void setAdvfilter(AdvancedFiltersDTO advfilter) {
		this.advfilter = advfilter;
	}


	public String getFilter() {
		return filter;
	}



	public void setFilter(String filter) {
		this.filter = filter;
	}



	public String getSubfilter() {
		return subfilter;
	}



	public void setSubfilter(String subfilter) {
		this.subfilter = subfilter;
	}



	public String getView() {
		return view;
	}



	public void setView(String view) {
		this.view = view;
	}


	public Boolean getFilteron() {
		boolean result = false;
		if((advfilter.getUsername()!=null)&& (advfilter.getUsername().trim().length()>0))
			result =true;
		if((advfilter.getVideotitle()!=null)&& (advfilter.getVideotitle().trim().length()>0))
			result =true;
		if((advfilter.getVideoid()!=null)&& (advfilter.getVideoid().trim().length()>0))
			result =true;

		this.filteron = result;
		
		return result;
		
	}


	public void setFilteron(Boolean filteron) {
		this.filteron = filteron;
	} 
	

	
	


}
