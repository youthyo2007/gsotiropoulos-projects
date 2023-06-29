package com.socialvideo.data.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.socialvideo.constant.ConstantRoleNames;
import com.socialvideo.data.dto.RoleDTO;
import com.socialvideo.data.dto.UserDTO;




@Entity
@Table(name = "users")
public class UserEntity implements Serializable {
	
private static final long serialVersionUID = 1L;

	
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;



@Column
private String UUID;


@Column
private String username;



@Column(nullable = false)
private String password;


@Column(nullable = false)
private String email;

@Column
private Boolean active = true;

@Column
private Boolean tstflag = true;


@Column
private Integer online = 1;

@Column
private Boolean enabled = true;


@Column
private Boolean emailvalidated = true;


@Column(name="socialid")
private String socialId;


@Column
private String firstname;


@Column
private String lastname;

@Column
private String middlename;

@Column
private Integer videoscount  = 0;

@Column
private Integer uniqueplacescount  = 0;

@Column
private Integer folowercount  = 0;

@Column
private Integer folowingcount  = 0;

@Column
private Integer collectionscount  = 0;

@Column
private Integer channelsfolowingcount  = 0;

@Column
private Integer communitiesfolowingcount  = 0;

@Column
private Integer tweetscount  = 0;

@Column
private Integer reviewscount  = 0;

@Column
private Integer likescount = 0;

@Column
private Integer playscount = 0;

@Column
private Integer ratingscount = 0;

@Column
private Integer playlistscount = 0;

@Column
private Integer favoritescount = 0;

@Column
private Integer watchlatercount = 0;



@Column(name="tokenexpired")
private Boolean tokenExpired;


@ManyToMany
@JoinTable(name = "rolesusers", joinColumns = @JoinColumn(name = "userid_fk", referencedColumnName = "id") , inverseJoinColumns = @JoinColumn(name = "roleid_fk", referencedColumnName = "id") )
private Collection<RoleEntity> roles;


public Boolean getEnabled() {
	return enabled;
}

public void setEnabled(Boolean enabled) {
	this.enabled = enabled;
}

public Boolean getTokenExpired() {
	return tokenExpired;
}

public void setTokenExpired(Boolean tokenExpired) {
	this.tokenExpired = tokenExpired;
}



public Boolean getTstflag() {
	return tstflag;
}

public void setTstflag(Boolean tstflag) {
	this.tstflag = tstflag;
}

public String getMiddlename() {
	return middlename;
}

public void setMiddlename(String middlename) {
	this.middlename = middlename;
}





public UserEntity() {};

public UserEntity(UserDTO DTO) {
	this.LOAD(DTO);
};


public UserEntity(String username, String password, String email, Boolean active, Integer online) {	
this.setUsername(username);
this.password  = password;
this.email  = email;
this.active = active;
this.online = online;

};


public String getPassword() {
	return password;
}


public void setPassword(String password) {
	this.password = password;
}


public Boolean getActive() {
	return active;
}


public void setActive(Boolean active) {
	this.active = active;
}


public Integer getOnline() {
	return online;
}


public void setOnline(Integer online) {
	this.online = online;
}

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public String getFirstname() {
	return firstname;
}

public void setFirstname(String firstname) {
	this.firstname = firstname;
}

public String getLastname() {
	return lastname;
}

public void setLastname(String lastname) {
	this.lastname = lastname;
}

public String getUsername() {
	return username;
}

public void setUsername(String username) {
	this.username = username;
}

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}

public Integer getVideoscount() {
	return videoscount;
}

public void setVideoscount(Integer videoscount) {
	this.videoscount = videoscount;
}

public Integer getUniqueplacescount() {
	return uniqueplacescount;
}

public void setUniqueplacescount(Integer uniqueplacescount) {
	this.uniqueplacescount = uniqueplacescount;
}

public Integer getFolowercount() {
	return folowercount;
}

public void setFolowercount(Integer folowercount) {
	this.folowercount = folowercount;
}

public Integer getFolowingcount() {
	return folowingcount;
}

public void setFolowingcount(Integer folowingcount) {
	this.folowingcount = folowingcount;
}

public Integer getChannelsfolowingcount() {
	return channelsfolowingcount;
}

public void setChannelsfolowingcount(Integer channelsfolowingcount) {
	this.channelsfolowingcount = channelsfolowingcount;
}

public Integer getCommunitiesfolowingcount() {
	return communitiesfolowingcount;
}

public void setCommunitiesfolowingcount(Integer communitiesfolowingcount) {
	this.communitiesfolowingcount = communitiesfolowingcount;
}

public Integer getTweetscount() {
	return tweetscount;
}

public void setTweetscount(Integer tweetscount) {
	this.tweetscount = tweetscount;
}


public UserDTO DTO() {

	UserDTO DTO = new UserDTO();
	
	DTO.setId(this.id);
	DTO.setUUID(this.UUID);
	DTO.setUsername(this.username);
	DTO.setPassword(this.password);
	DTO.setEmail(this.email);
	DTO.setActive(this.active);
	DTO.setOnline(this.online);
	DTO.setEnabled(this.enabled);
	DTO.setEmailvalidated(this.emailvalidated);
	DTO.setTokenExpired(this.tokenExpired);
	DTO.setFirstname(this.firstname);
	DTO.setMiddlename(this.middlename);
	DTO.setLastname(this.lastname);
	DTO.setVideoscount(this.videoscount);
	DTO.setUniqueplacescount(this.uniqueplacescount);
	DTO.setFolowercount(this.folowercount);
	DTO.setFolowingcount(this.folowingcount);
	DTO.setChannelsfolowingcount(this.channelsfolowingcount);
	DTO.setCommunitiesfolowingcount(this.communitiesfolowingcount);
	DTO.setTweetscount(this.tweetscount);
	DTO.setReviewscount(this.reviewscount);
	DTO.setLikescount(this.likescount);
	DTO.setPlayscount(this.playscount);
	DTO.setRatingscount(this.ratingscount);
	DTO.setCollectionscount(this.collectionscount);
	DTO.setPlaylistscount(this.playlistscount);
	DTO.setFavoritescount(this.favoritescount);
	DTO.setWatchlatercount(this.watchlatercount);
	DTO.setTstFlag(this.tstflag);


	DTO.setRole(new RoleDTO(ConstantRoleNames.ROLEUSER));
/*	if((roles!=null) || (!roles.isEmpty())) {
		List<Object> rolesList = Arrays.asList(roles.toArray());
		DTO.setRole(((RoleEntity)rolesList.get(0)).DTO());
	}
	*/
	
	return DTO;
}

public void LOAD(UserDTO DTO) {

	this.username = DTO.getUsername();
	this.UUID = DTO.getUUID();
	this.password = DTO.getPassword();
	this.email = DTO.getEmail();
	this.socialId = DTO.getSocialId();
	this.active = DTO.getActive();
	this.online = DTO.getOnline();
	this.enabled = DTO.getEnabled();
	this.emailvalidated = DTO.getEmailvalidated();
	this.tokenExpired = DTO.getTokenExpired();
	this.firstname = DTO.getFirstname();
	this.middlename = DTO.getMiddlename();
	this.lastname = DTO.getLastname();
	this.videoscount = DTO.getVideoscount();
	this.uniqueplacescount = DTO.getUniqueplacescount();
	this.folowercount = DTO.getFolowercount();
	this.folowingcount = DTO.getFolowingcount();
	this.channelsfolowingcount = DTO.getChannelsfolowingcount();
	this.communitiesfolowingcount = DTO.getCommunitiesfolowingcount();
	this.tweetscount = DTO.getTweetscount();
	this.reviewscount = DTO.getReviewscount();
	this.likescount = DTO.getLikescount();
	this.playscount = DTO.getPlayscount();
	this.ratingscount = DTO.getRatingscount();	
	this.collectionscount = DTO.getCollectionscount();
	this.playlistscount = DTO.getPlaylistscount();
	this.favoritescount = DTO.getFavoritescount();
	this.watchlatercount = DTO.getWatchlatercount();
	this.tstflag = DTO.getTstFlag();

	
}

public void LOADCALC(UserDTO DTO) {
	this.videoscount = DTO.getVideoscount();
	this.uniqueplacescount = DTO.getUniqueplacescount();
	this.folowercount = DTO.getFolowercount();
	this.folowingcount = DTO.getFolowingcount();
	this.channelsfolowingcount = DTO.getChannelsfolowingcount();
	this.communitiesfolowingcount = DTO.getCommunitiesfolowingcount();
	this.tweetscount = DTO.getTweetscount();
	this.reviewscount = DTO.getReviewscount();
	this.likescount = DTO.getLikescount();
	this.playscount = DTO.getPlayscount();
	this.ratingscount = DTO.getRatingscount();
	this.collectionscount = DTO.getCollectionscount();
	this.playlistscount = DTO.getPlaylistscount();
	this.favoritescount = DTO.getFavoritescount();
	this.watchlatercount = DTO.getWatchlatercount();
}


public Collection<RoleEntity> getRoles() {
	return roles;
}

public void setRoles(Collection<RoleEntity> roles) {
	this.roles = roles;
}

public Integer getReviewscount() {
	return reviewscount;
}

public void setReviewscount(Integer reviewscount) {
	this.reviewscount = reviewscount;
}

public Integer getLikescount() {
	return likescount;
}

public void setLikescount(Integer likescount) {
	this.likescount = likescount;
}

public String getUUID() {
	return UUID;
}

public void setUUID(String uUID) {
	UUID = uUID;
}

public Integer getCollectionscount() {
	return collectionscount;
}

public void setCollectionscount(Integer collectionscount) {
	this.collectionscount = collectionscount;
}

public Integer getPlayscount() {
	return playscount;
}

public void setPlayscount(Integer playscount) {
	this.playscount = playscount;
}

public Integer getRatingscount() {
	return ratingscount;
}

public void setRatingscount(Integer ratingscount) {
	this.ratingscount = ratingscount;
}

public Integer getPlaylistscount() {
	return playlistscount;
}

public void setPlaylistscount(Integer playlistscount) {
	this.playlistscount = playlistscount;
}

public Integer getFavoritescount() {
	return favoritescount;
}

public void setFavoritescount(Integer favoritescount) {
	this.favoritescount = favoritescount;
}

public Integer getWatchlatercount() {
	return watchlatercount;
}

public void setWatchlatercount(Integer watchlatercount) {
	this.watchlatercount = watchlatercount;
}

public Boolean getEmailvalidated() {
	return emailvalidated;
}

public void setEmailvalidated(Boolean emailvalidated) {
	this.emailvalidated = emailvalidated;
}

public String getSocialId() {
	return socialId;
}

public void setSocialId(String socialId) {
	this.socialId = socialId;
}



}