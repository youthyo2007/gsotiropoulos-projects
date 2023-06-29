package com.socialvideo.data.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.socialvideo.utilities.WebUtility;


@JsonIgnoreProperties(ignoreUnknown = true)
public class VideoManagerDTO implements Serializable {
	
	
	



	private static final long serialVersionUID = 1L;
	
	
	@JsonProperty("playlists")
	private Map<Long, String> playlists =  new LinkedHashMap<>();

	@JsonProperty("albums")
	private Map<Long, String> albums =  new LinkedHashMap<>();
	
	public Map<Long, String> getAlbums() {
		return albums;
	}

	public void setAlbums(Map<Long, String> albums) {
		this.albums = albums;
	}

	public Map<Long, Boolean> getSelectedalbums() {
		return selectedalbums;
	}

	public void setSelectedalbums(Map<Long, Boolean> selectedalbums) {
		this.selectedalbums = selectedalbums;
	}

	@JsonProperty("selectedplaylists")
	private Map<Long, Boolean> selectedplaylists =  new LinkedHashMap<>();

	@JsonProperty("selectedalbums")
	private Map<Long, Boolean> selectedalbums =  new LinkedHashMap<>();
	
	
	@JsonProperty("selectedvideos")
	private Map<Long,Boolean> selectedvideos =  new LinkedHashMap<>();
	
	@JsonProperty("selectedcollection")
	private Long selectedcollection;
	
	@JsonProperty("selectedvideo")
	private Long selectedvideo;

	@JsonProperty("singleselection")
	public Boolean singleselection = false;
	
	
	private String view;
	
	private String filter;
	
	@JsonProperty("watchlatercollection")
	private CollectionDTO watchlatercollection;
	
	@JsonProperty("favoritesbucketcollection")
	private CollectionDTO favoritesbucketcollection;
	
	
	@JsonProperty("newcollection")
	private CollectionDTO newcollection = new CollectionDTO();
	
	
	public VideoManagerDTO() {}

	public void initcollections(List<CollectionDTO> albumscollections, List<CollectionDTO> playlistcollections, CollectionDTO watchLaterCollection, CollectionDTO playlistBucketCollection) {
		selectedplaylists =  new LinkedHashMap<>();
		
		
		selectedplaylists = playlistcollections.parallelStream().collect(Collectors.toMap(CollectionDTO::getId,x->false));
		playlists = playlistcollections.parallelStream().collect(Collectors.toMap(CollectionDTO::getId,CollectionDTO::getTitle));
		selectedalbums = albumscollections.parallelStream().collect(Collectors.toMap(CollectionDTO::getId,x->false));
		albums = albumscollections.parallelStream().collect(Collectors.toMap(CollectionDTO::getId,CollectionDTO::getTitle));
		
		
		
		this.watchlatercollection = watchLaterCollection; 
		this.favoritesbucketcollection = playlistBucketCollection;

		
		
		
		/*		
		for (Iterator<CollectionDTO> iterator = playlistcollections.iterator(); iterator.hasNext();) {
			CollectionDTO collection = (CollectionDTO) iterator.next();
			selectedplaylists.put(collection.getId(),false);
			playlists.put(collection.getId(), collection.getTitle());
	    }
		*/
	}


	public void initvideos(List<PublicVideoDTO> videoslist) {
		selectedvideos =  new LinkedHashMap<>();
		selectedvideos = videoslist.parallelStream().collect(Collectors.toMap(PublicVideoDTO::getId, x->false));

		
		/*for (Iterator<VideoDTO> iterator = videos.iterator(); iterator.hasNext();) {
			VideoDTO video = (VideoDTO) iterator.next();
			selectedvideos.put(video.getId(),false);
	    }*/

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

	public CollectionDTO getWatchlatercollection() {
		return watchlatercollection;
	}

	public void setWatchlatercollection(CollectionDTO watchlatercollection) {
		this.watchlatercollection = watchlatercollection;
	}

	public CollectionDTO getNewcollection() {
		return newcollection;
	}

	public void setNewcollection(CollectionDTO newcollection) {
		this.newcollection = newcollection;
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

	public CollectionDTO getFavoritesbucketcollection() {
		return favoritesbucketcollection;
	}

	public void setFavoritesbucketcollection(CollectionDTO favoritesbucketcollection) {
		this.favoritesbucketcollection = favoritesbucketcollection;
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
	
	
		


}
