
package com.socialvideo.data.services;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.socialvideo.data.mappers.ConnectEntitiesMapper;

@Service
public class  ConnectEntityService {

	
	
    @Autowired
	private ConnectEntitiesMapper connectEntitiesMapper;
	

	 
	 public Integer isUserLikesVideo(Long userid, Long videoid) {
		 return connectEntitiesMapper.isUserLikesVideo(userid, videoid);
	 } 
	 
	 public Integer isUserRatedVideo(Long userid, Long videoid) {
		 return connectEntitiesMapper.isUserRatedVideo(userid, videoid);
	 } 
	 
	 
    
	public void videoLike(Long userid,Long videoid) {
		 connectEntitiesMapper.videoLike(userid,videoid);
	}


   
	public void videoPlay(Long userid,Long videoid) {
		 connectEntitiesMapper.videoPlay(userid,videoid);
	}

	

	public void videoRate(Long userid, Long videoid, Long rating) {
		connectEntitiesMapper.videoRate(userid,videoid,rating);
	} 

	public void updateVideoRate(Long userid, Long videoid, Long rating) {
		connectEntitiesMapper.updateVideoRate(userid,videoid,rating);
	} 
	
	
	
      
	public void videoRemoveFromAllCollections(Long videoid) {
		 connectEntitiesMapper.videoRemoveFromAllCollections(videoid);
	}

	
    public void userFollowUser(Long userid, Long userfollowedid) {
		 connectEntitiesMapper.userFollowUser(userid, userfollowedid);
	 } 

    
     public Integer isUserFollowingUser(Long userid, Long userfollowedid) {
		 return connectEntitiesMapper.isUserFollowingUser(userid, userfollowedid);
	 } 

    
     public void userUnFollowUser(Long userid, Long userfollowedid) {
		 connectEntitiesMapper.userUnFollowUser(userid, userfollowedid);
	 } 

	
	 public void userFollowChannel(Long userid, Long channelid) {
		 connectEntitiesMapper.userFollowChannel(userid, channelid);
	 } 


	 public void userUnFollowChannel(Long userid, Long channelid) {
		 connectEntitiesMapper.userUnFollowChannel(userid, channelid);
	 } 
	 
	 
	 public void videoPublishChannel(Long videoid, Long channelid) {
		 connectEntitiesMapper.videoPublishChannel(videoid, channelid);
	 } 

	 public void videoUnPublishChannel(Long videoid, Long channelid) {
		 connectEntitiesMapper.videoUnPublishChannel(videoid, channelid);
	 } 

	 public Long videoExistsChannel(Long videoid, Long channelid) {
		 return connectEntitiesMapper.videoExistsChannel(videoid, channelid);
	 } 
	 
	 public void videoAddCollection(Long videoid, Long collectionid) {
		 connectEntitiesMapper.videoAddCollection(videoid, collectionid);
	 } 

	 public void videoRemoveCollection(Long videoid, Long collectionid) {
		 connectEntitiesMapper.videoRemoveCollection(videoid, collectionid);
	 } 

	 public void removeCollectionVideos(Long collectionid) {
		 connectEntitiesMapper.removeCollectionVideos(collectionid);
	 } 

	 
	 
	 public Long videoExistsCollection(Long videoid, Long collectionid) {
		 return connectEntitiesMapper.videoExistsCollection(videoid, collectionid);
	 }

	 
	public void collectionPlay(Long userid,Long collectionid) {
			 connectEntitiesMapper.collectionPlay(userid,collectionid);
	}

	
	public void videoShare(Long userid, Long videoid, Long socialnetworkid) {
		 connectEntitiesMapper.videoShare(userid,videoid,socialnetworkid);
		
	}
	 

}
