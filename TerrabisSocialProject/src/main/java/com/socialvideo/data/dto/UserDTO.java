package com.socialvideo.data.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.common.base.Joiner;
import com.socialvideo.utilities.SmartDateTimeUtil;
import com.socialvideo.validation.PasswordMatches;
import com.socialvideo.validation.ValidEmail;
import com.socialvideo.validation.ValidPassword;


@PasswordMatches
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO implements Serializable  {
	
	
	private static final long serialVersionUID = 1L;
	
	
	
	private Long id;

	
	
	
	@JsonIgnore
	private String UUID;
	
	private String username;
	
    private String middlename;
    
    private String socialId;
    
    @ValidEmail
	@NotNull
	@JsonIgnore
    private String email;
 
/*	@ValidPassword
	@NotNull
    @Size(min = 6, max = 15)*/
	@JsonIgnore
    private String password;
 
    
/*	@ValidPassword
	@NotNull
    @Size(min = 6, max = 15)*/
	@JsonIgnore
    private String oldPassword;
    
	@JsonIgnore
    private String newPassword;
	
/*	@NotNull
    @Size(min = 6, max = 15)*/
	@JsonIgnore
    private String passwordConfirm;

	@JsonIgnore
    private Boolean enabled = true;

	@JsonIgnore
    private Boolean tokenExpired;
    
	@JsonIgnore
	private Boolean active = true;

	@JsonIgnore
	private Boolean tstFlag = false;
	
	
	@JsonIgnore
	private Integer online = 1;
	
	@JsonIgnore
	private Boolean emailvalidated = true;

	public Boolean getTstFlag() {
		return tstFlag;
	}



	public void setTstFlag(Boolean tstFlag) {
		this.tstFlag = tstFlag;
	}



	@JsonIgnore
	private String firstname;

	@JsonIgnore
	private String lastname;

	private Integer uniqueplacescount = 0;

	private Integer folowercount = 0;

	private Integer folowingcount = 0;

	private Integer communitiesfolowingcount = 0;

	private Integer collectionscount = 0;
	
	private Integer playlistscount = 0;

	private Integer albumscount = 0;

	private Integer favoritescount = 0;
	
	private Integer watchlatercount = 0;
	
	
	private Integer channelsfolowingcount =0;
	
	private Integer tweetscount =0;
	
	private Integer reviewscount = 0;
	
	private Integer videoscount = 0;

	private Integer followerscount = 0;
	
	private Integer followingcount = 0;
	
	
	private Integer pendingvideoscount = 0;
	
	private Integer rejectedvideoscount = 0;

	
	public Integer getFollowerscount() {
		return followerscount;
	}



	public void setFollowerscount(Integer followerscount) {
		this.followerscount = followerscount;
	}



	public Integer getFollowingcount() {
		return followingcount;
	}



	public void setFollowingcount(Integer followingcount) {
		this.followingcount = followingcount;
	}



	private Integer likescount = 0;

	private Integer playscount = 0;

	private Integer ratingscount = 0;

	@JsonIgnore
	private Date datecreated;

	@JsonIgnore
	private String datestring;
	

	@JsonIgnore
	private Integer datenumber;
	
	@JsonIgnore
	private Date lastlogin;
	
	@JsonIgnore
	private Date datemodified;
	
	@JsonIgnore
	private RoleDTO role;

	private AvatarDTO avatar;	
	
	private GameLevelDTO gameLevel;
	
	@JsonIgnore
	private List<RoleDTO> roles;
	
	
	public String getUUID() {
		return UUID;
	}



	public void setUUID(String uUID) {
		UUID = uUID;
	}



	public UserDTO() {} 
	
	
	
	public UserDTO(String firstname, String lastname, String username, String password, String email, Boolean active, Boolean enabled, Integer online) {	
		this.setFirstname(firstname);
		this.setLastname(lastname);
		this.setUsername(username);
		this.password  = password;
		this.email  = email;
		this.active = active;
		this.online = online;
		this.enabled = enabled;
		

		};
	

	public String getShortAndSmart_DATECREATED() {
			return SmartDateTimeUtil.getDateFormatString(datecreated);
	}	

	

	public String getDateCreatedAsString() {
		return SmartDateTimeUtil.getDateFormatString(datecreated);
	}	
	
	@JsonIgnore
	public String getShortAndSmart_LASTLOGIN() {
		if(lastlogin==null)
			return "never";
		else 
			return SmartDateTimeUtil.getDateString_shortAndSmart(lastlogin);
	}	

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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



	public String getMiddlename() {
		return middlename;
	}

	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}

	public Integer getChannelsfolowingcount() {
		return channelsfolowingcount;
	}

	public void setChannelsfolowingcount(Integer channelsfolowingcount) {
		this.channelsfolowingcount = channelsfolowingcount;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

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



	public RoleDTO getRole() {
		return role;
	}



	public void setRole(RoleDTO role) {
		this.role = role;
	}
	
	
	
	public String getShortdesc() {
		String shortDesc = "";
		if(username!=null && username.trim().length()>0) {
			if (username.length()>20)
				shortDesc = username.substring(0, 19)+"...";
			else
				shortDesc = username;
		} 
		
		else {
			if (email.length()>20)
				shortDesc = email.substring(0, 19)+"...";
			else 
				shortDesc = email;
		}
		
		
		return shortDesc;
		
	}



	public Integer getReviewscount() {
		return reviewscount;
	}



	public void setReviewscount(Integer reviewscount) {
		this.reviewscount = reviewscount;
	}



	public Integer getVideoscount() {
		return videoscount;
	}



	public void setVideoscount(Integer videoscount) {
		this.videoscount = videoscount;
	}



	public Integer getLikescount() {
		return likescount;
	}



	public void setLikescount(Integer likescount) {
		this.likescount = likescount;
	}



	public AvatarDTO getAvatar() {
		return avatar;
	}



	public void setAvatar(AvatarDTO avatar) {
		this.avatar = avatar;
	}



	public String getNewPassword() {
		return newPassword;
	}



	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}



	public Integer getCollectionscount() {
		return collectionscount;
	}



	public void setCollectionscount(Integer collectionscount) {
		this.collectionscount = collectionscount;
	}



	public Integer getPlaylistscount() {
		return playlistscount;
	}



	public void setPlaylistscount(Integer playlistscount) {
		this.playlistscount = playlistscount;
	}


	public Integer getAlbumscount() {
		return albumscount;
	}



	public void setAlbumscount(Integer albumscount) {
		this.albumscount = albumscount;
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



	public GameLevelDTO getGameLevel() {
		return gameLevel;
	}



	public void setGameLevel(GameLevelDTO gameLevel) {
		this.gameLevel = gameLevel;
	}



	public Boolean getEmailvalidated() {
		return emailvalidated;
	}



	public void setEmailvalidated(Boolean emailvalidated) {
		this.emailvalidated = emailvalidated;
	}



	public String getOldPassword() {
		return oldPassword;
	}



	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}


	public List<RoleDTO> getRoles() {
		return roles;
	}



	public void setRoles(List<RoleDTO> roles) {
		this.roles = roles;
	}



	public Date getLastlogin() {
		return lastlogin;
	}



	public void setLastlogin(Date lastlogin) {
		this.lastlogin = lastlogin;
	}



	public Integer getPendingvideoscount() {
		return pendingvideoscount;
	}



	public void setPendingvideoscount(Integer pendingvideoscount) {
		this.pendingvideoscount = pendingvideoscount;
	}



	public Integer getRejectedvideoscount() {
		return rejectedvideoscount;
	}



	public void setRejectedvideoscount(Integer rejectedvideoscount) {
		this.rejectedvideoscount = rejectedvideoscount;
	}



	public String getSocialId() {
		return socialId;
	}



	public void setSocialId(String socialId) {
		this.socialId = socialId;
	}



	public String getDatestring() {
		return datestring;
	}



	public void setDatestring(String datestring) {
		this.datestring = datestring;
	}



	public Integer getDatenumber() {
		return datenumber;
	}



	public void setDatenumber(Integer datenumber) {
		this.datenumber = datenumber;
	}
	

}
