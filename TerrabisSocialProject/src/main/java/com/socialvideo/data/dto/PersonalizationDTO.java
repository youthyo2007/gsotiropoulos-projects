package com.socialvideo.data.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.socialvideo.constant.AdvertConstants;
import com.socialvideo.constant.WebConstants;




@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonalizationDTO implements Serializable {
	

	private static final long serialVersionUID = 1L;

	
	@JsonProperty("preference")
	private Map<String, Object> preference = new HashMap<>();
	
	public static String KEY_SIDEBARCOLLAPSE = "SIDEBARCOLLAPSE-";
	public static String KEY_GRIDSTYLE = "GRIDSTYLE-"; 
	public static String KEY_ADVERT_SHOWN = "ADVERTSHOWN-"; 
	

	
	public String view; 
	public String filter; 
	public String grid; 
	public String pageid; 
	
	public String googleMapType = "hybrid"; 
	
	public String mapMode = "normal"; 

	public Boolean clustererMode = true;
	public Boolean searchMapField = true;
	
	public String item(String key){
		if(!getPreference().containsKey(key))
			return new String(WebConstants.GRIDSTYLE_BOX);
		else
			return getPreference().get(key).toString();
	}
	

	
	public PersonalizationDTO(){
	}

	public void init(){
		
		//GRID STYLES
		getPreference().put(KEY_SIDEBARCOLLAPSE+"video-base-map-view", new Boolean(false));
		
		getPreference().put(KEY_GRIDSTYLE+"video-one-view-mobile", new String(WebConstants.GRIDSTYLE_BOX));
		getPreference().put(KEY_GRIDSTYLE+"video-one-view-desktop", new String(WebConstants.GRIDSTYLE_BOX));
		getPreference().put(KEY_GRIDSTYLE+"video-catalog-view", new String(WebConstants.GRIDSTYLE_BOX));
		getPreference().put(KEY_GRIDSTYLE+"trending-catalog-view", new String(WebConstants.GRIDSTYLE_BOXUSR));		
		getPreference().put(KEY_GRIDSTYLE+"playlist-catalog-view", new String(WebConstants.GRIDSTYLE_BOX));
		getPreference().put(KEY_GRIDSTYLE+"video-map-normal-view-mobile", new String(WebConstants.GRIDSTYLE_BOX));
		getPreference().put(KEY_GRIDSTYLE+"video-map-normal-view-desktop", new String(WebConstants.GRIDSTYLE_BOX));
		getPreference().put(KEY_GRIDSTYLE+"video-map-list-view", new String(WebConstants.GRIDSTYLE_DETAIL));
		getPreference().put(KEY_GRIDSTYLE+"user-index-videos", new String(WebConstants.GRIDSTYLE_BOX));
		getPreference().put(KEY_GRIDSTYLE+"user-index-favorites", new String(WebConstants.GRIDSTYLE_BOX));
		getPreference().put(KEY_GRIDSTYLE+"user-index-watchlater", new String(WebConstants.GRIDSTYLE_BOX));
		getPreference().put(KEY_GRIDSTYLE+"video-organizer-view", new String(WebConstants.GRIDSTYLE_BOX));
		getPreference().put(KEY_GRIDSTYLE+"user-index-playlists", new String(WebConstants.GRIDSTYLE_THUMB));
		
		
		//ADMINISTRAITON PERSONALIZATION
		getPreference().put(KEY_GRIDSTYLE+"admin-video-view", new String(WebConstants.GRIDSTYLE_ANALYTIC));
		getPreference().put(KEY_GRIDSTYLE+"admin-user-view", new String(WebConstants.GRIDSTYLE_DATATABLE));
		getPreference().put(KEY_GRIDSTYLE+"admin-claims-view", new String(WebConstants.GRIDSTYLE_BOX));		
		
		//ADVERT SHOWN FLAGS
		getPreference().put(KEY_ADVERT_SHOWN+AdvertConstants.MAPOVERLAYADVERT, new Boolean(false));
		
		
	}


	public Map<String, Object> getPreference() {
		return preference;
	}



	public void setPreference(Map<String, Object> preference) {
		this.preference = preference;
	}



	public String getView() {
		return view;
	}



	public void setView(String view) {
		this.view = view;
	}



	public String getFilter() {
		return filter;
	}



	public void setFilter(String filter) {
		this.filter = filter;
	}



	public String getGrid() {
		return grid;
	}



	public void setGrid(String grid) {
		this.grid = grid;
	}

	
	
	


	public void updateFilterViewGrid(String filter, String view, String grid, String pageid, String pagename) {
		 this.setView(view);
         this.setFilter(filter);
         this.setPageid(pageid);
          
        //IF GRID IS CHANGING THEN UPDATE SESSION
        if(grid!=null) {
        	this.setGrid(grid);
            this.getPreference().put(PersonalizationDTO.KEY_GRIDSTYLE+pagename, grid);
        }
	}



	public String getGoogleMapType() {
		return googleMapType;
	}



	public void setGoogleMapType(String googleMapType) {
		this.googleMapType = googleMapType;
	}



	public String getMapMode() {
		return mapMode;
	}



	public void setMapMode(String mapMode) {
		this.mapMode = mapMode;
	}



	public Boolean getClustererMode() {
		return clustererMode;
	}



	public void setClustererMode(Boolean clustererMode) {
		this.clustererMode = clustererMode;
	}



	public Boolean getSearchMapField() {
		return searchMapField;
	}



	public void setSearchMapField(Boolean searchMapField) {
		this.searchMapField = searchMapField;
	}



	public String getPageid() {
		return pageid;
	}



	public void setPageid(String pageid) {
		this.pageid = pageid;
	};
	

	
	
	
	
	
	
	

}
