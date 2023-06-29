package com.socialvideo.data.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.socialvideo.data.dto.CollectionDTO;
import com.socialvideo.data.dto.UserDTO;
import com.socialvideo.data.dto.VideoDTO;
import com.socialvideo.data.services.inter.IUserService;
import com.socialvideo.data.services.inter.IVideoService;

@Service
public class AggregationService {
	
	
	

	
	 @Autowired
	 protected IUserService userService;
	 
	 @Autowired
	 protected IVideoService videoService;
	
	 
	 @Autowired
	 protected AvatarService avatarService;
	
	 
	 @Autowired
	 protected ChannelService channelService;

	 @Autowired
	 protected ConnectEntityService cntService;
	 
	 
	 @Autowired
	 protected CollectionService collService;
	 
	 @Autowired
	 protected DatabaseService dbService;
	 
	


	 
	 public void USER_VideoCountPlus(Long userid) {
			UserDTO userDTO = userService.findUserById(userid);
			userDTO.setVideoscount(userDTO.getVideoscount()+1);
			userService.updateCalc(userDTO);
	 }
	 
	
	
	

	 public void USER_VideoCountMinus(Long userid) {
			UserDTO userDTO = userService.findUserById(userid);
			userDTO.setVideoscount(userDTO.getVideoscount()-1);
			userService.updateCalc(userDTO);
	 }
	 
	 
	 
	 public void USER_LikesCountPlus(Long userid) {
			UserDTO userDTO = userService.findUserById(userid);
			userDTO.setVideoscount(userDTO.getLikescount()+1);
			userService.updateCalc(userDTO);
	 }
	 

	 public void USER_LikesCountMinus(Long userid) {
			UserDTO userDTO = userService.findUserById(userid);
			userDTO.setVideoscount(userDTO.getLikescount()-1);
			userService.updateCalc(userDTO);
	 }
	 
	 
	 
	 public void USER_FollowingCountPlus(Long userid) {
			UserDTO userDTO = userService.findUserById(userid);
			userDTO.setVideoscount(userDTO.getFolowingcount()+1);
			userService.updateCalc(userDTO);
	 }
	 

	 public void USER_FollowingCountMinus(Long userid) {
			UserDTO userDTO = userService.findUserById(userid);
			userDTO.setVideoscount(userDTO.getFolowingcount()-1);
			userService.updateCalc(userDTO);
	 }
	 

	 public void USER_FollowerCountPlus(Long userid) {
			UserDTO userDTO = userService.findUserById(userid);
			userDTO.setVideoscount(userDTO.getFolowercount()+1);
			userService.updateCalc(userDTO);
	 }
	 
	 
	 public void USER_FollowerCountMinus(Long userid) {
			UserDTO userDTO = userService.findUserById(userid);
			userDTO.setVideoscount(userDTO.getFolowercount()-1);
			userService.updateCalc(userDTO);
	 }
	 

	 
	 public void USER_ReviewsCountPlus(Long userid) {
			UserDTO userDTO = userService.findUserById(userid);
			userDTO.setVideoscount(userDTO.getReviewscount()+1);
			userService.updateCalc(userDTO);
	 }
	 
	 
	 public void USER_ReviewsCountMinus(Long userid) {
			UserDTO userDTO = userService.findUserById(userid);
			userDTO.setVideoscount(userDTO.getReviewscount()-1);
			userService.updateCalc(userDTO);
	 }
	 
	 
	 public void USER_CollectionsCountPlus(Long userid) {
			UserDTO userDTO = userService.findUserById(userid);
			userDTO.setVideoscount(userDTO.getCollectionscount()+1);
			userService.updateUser(userDTO);
	 }
	 
	 
	 public void USER_CollectionsCountMinus(Long userid) {
			UserDTO userDTO = userService.findUserById(userid);
			userDTO.setVideoscount(userDTO.getCollectionscount()-1);
			userService.updateUser(userDTO);
	 }
	 
	 
	
	 
	 public void COLL_VideoCountPlus(Long videoid, Long collectionid) {
			CollectionDTO collDTO = collService.findCollectionById(collectionid);
			collDTO.setVideoscount(collDTO.getVideoscount()+1);
			collService.updateCalc(collDTO);
	 
			VideoDTO vidDTO = videoService.findVideoById(videoid);
			vidDTO.setCollectionscount(vidDTO.getCollectionscount()+1);
			videoService.updateCalc(vidDTO);

	 
	 }

	 
	 
	 public void COLL_VideoCountMinus(Long videoid, Long collectionid) {
			CollectionDTO collDTO = collService.findCollectionById(collectionid);
			collDTO.setVideoscount(collDTO.getVideoscount()-1);
			collService.updateCalc(collDTO);

			VideoDTO vidDTO = videoService.findVideoById(videoid);
			vidDTO.setCollectionscount(vidDTO.getCollectionscount()-1);
			videoService.updateCalc(vidDTO);

	 }



	public void VIDEO_LikePlus(Long userid, Long videoid) {
		VideoDTO vidDTO = videoService.findVideoById(videoid);
		vidDTO.setLikescount(vidDTO.getLikescount()+1);
		videoService.updateCalc(vidDTO);
	}


	public void VIDEO_SharePlus(Long userid, Long videoid) {
		VideoDTO vidDTO = videoService.findVideoById(videoid);
		vidDTO.setTweetscount(vidDTO.getTweetscount()+1);
		videoService.updateCalc(vidDTO);
	}



	public void VIDEO_PlayPlus(Long userid, Long videoid) {
		VideoDTO vidDTO = videoService.findVideoById(videoid);
		vidDTO.setPlayscount(vidDTO.getPlayscount()+1);
		videoService.updateCalc(vidDTO);
	}
	
	 
	
}
