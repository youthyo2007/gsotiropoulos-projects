package com.socialvideo.data.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.socialvideo.utilities.WebUtility;


@JsonIgnoreProperties(ignoreUnknown = true)
public class AdminManagerDTO implements Serializable {
	
	
	



	private static final long serialVersionUID = 1L;
	
	
	@JsonProperty("playlists")
	private Map<Long, String> playlists =  new LinkedHashMap<>();
	
	
	@JsonProperty("selectedplaylists")
	private Map<Long, Boolean> selectedplaylists =  new LinkedHashMap<>();
	
	@JsonProperty("selectedvideos")
	private Map<Long,Boolean> selectedvideos =  new LinkedHashMap<>();
	
	@JsonProperty("selectedcollection")
	private Long selectedcollection;

	
	@JsonProperty("selectedvideo")
	private Long selectedvideo;

	@JsonProperty("selecteditem")
	private Long selecteditem;


	@JsonProperty("selecteditemvalue")
	private String selecteditemvalue;
	
	
	@JsonProperty("selecteditemslist")
	private String selecteditemslist;
	
	
	@JsonProperty("rejectionreason")
	private Long rejectionreason;
	
	
	@JsonProperty("rejectionreasontxt")
	private String rejectionreasontxt;

	
	
	@JsonProperty("video")
	private VideoDTO video = new VideoDTO();

	@JsonProperty("singleselection")
	public Boolean singleselection = false;
	
	private String view;
	
	private String filter;
	
	public AdminManagerDTO() {}

	public void initcollections(List<CollectionDTO> collections, CollectionDTO watchLaterCollection, CollectionDTO playlistBucketCollection) {
		selectedplaylists =  new LinkedHashMap<>();
		for (Iterator<CollectionDTO> iterator = collections.iterator(); iterator.hasNext();) {
			CollectionDTO collection = (CollectionDTO) iterator.next();
			selectedplaylists.put(collection.getId(),false);
			playlists.put(collection.getId(), collection.getTitle());
	    }
	}


	public void initvideos(List<VideoDTO> videos) {
		selectedvideos =  new LinkedHashMap<>();
		for (Iterator<VideoDTO> iterator = videos.iterator(); iterator.hasNext();) {
			VideoDTO video = (VideoDTO) iterator.next();
			selectedvideos.put(video.getId(),false);
	    }

	}
	
	
	
	public String getRejectionreasontxt() {
		return rejectionreasontxt;
	}

	public void setRejectionreasontxt(String rejectionreasontxt) {
		this.rejectionreasontxt = rejectionreasontxt;
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


	public Long getSelectedcollection() {
		return selectedcollection;
	}

	public void setSelectedcollection(Long selectedcollection) {
		this.selectedcollection = selectedcollection;
	}



	public Map<Long, Boolean> getSelectedplaylists() {
		return selectedplaylists;
	}

	public void setSelectedplaylists(Map<Long, Boolean> selectedplaylists) {
		this.selectedplaylists = selectedplaylists;
	}

	public Map<Long, Boolean> getSelectedvideos() {
		return selectedvideos;
	}

	public void setSelectedvideos(Map<Long, Boolean> selectedvideos) {
		this.selectedvideos = selectedvideos;
	}

	
	public Map<Long, String> getPlaylists() {
		return playlists;
	}

	public void setPlaylists(Map<Long, String> playlists) {
		this.playlists = playlists;
	}

	public Long getSelectedvideo() {
		return selectedvideo;
	}

	public void setSelectedvideo(Long selectedvideo) {
		this.selectedvideo = selectedvideo;
	}

	public Boolean getSingleselection() {
		return singleselection;
	}

	public void setSingleselection(Boolean singleselection) {
		this.singleselection = singleselection;
	}

	public Long getRejectionreason() {
		return rejectionreason;
	}

	public void setRejectionreason(Long rejectionreason) {
		this.rejectionreason = rejectionreason;
	}

	public Long getSelecteditem() {
		return selecteditem;
	}

	public void setSelecteditem(Long selecteditem) {
		this.selecteditem = selecteditem;
	}

	public String getSelecteditemslist() {
		return selecteditemslist;
	}

	public void setSelecteditemslist(String selecteditemslist) {
		this.selecteditemslist = selecteditemslist;
	}

	public String getSelecteditemvalue() {
		return selecteditemvalue;
	}

	public void setSelecteditemvalue(String selecteditemvalue) {
		this.selecteditemvalue = selecteditemvalue;
	}

		


}
