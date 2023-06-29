package com.socialvideo.data.mappers;

import org.apache.ibatis.annotations.Param;

public interface ConnectEntitiesMapper {
	
	
	  public void userFollowUser(@Param("userid") Long userid, @Param("userfollowedid") Long userfollowedid); 
	  public Integer isUserFollowingUser(@Param("userid") Long userid, @Param("userfollowedid") Long userfollowedid);
	  public void userUnFollowUser(@Param("userid") Long userid, @Param("userfollowedid") Long userfollowedid); 
	  public void userFollowChannel(@Param("userid") Long userid, @Param("channelid") Long channelid); 
	  public void userUnFollowChannel(@Param("userid") Long userid, @Param("channelid") Long channelid); 
	  public void videoPublishChannel(@Param("videoid") Long videoid, @Param("channelid") Long channelid); 
	  public void videoUnPublishChannel(@Param("videoid") Long videoid, @Param("channelid") Long channelid); 
	  public void videoAddCollection(@Param("videoid") Long videoid, @Param("collectionid") Long collectionid);
	  public void videoRemoveCollection(@Param("videoid") Long videoid, @Param("collectionid") Long collectionid);
	  public void videoRemoveFromAllCollections(@Param("videoid") Long videoid);
	  public Long videoExistsCollection(@Param("videoid") Long videoid, @Param("collectionid") Long collectionid);
	  public Long videoExistsChannel(@Param("videoid") Long videoid, @Param("channelid") Long channelid);
	  public void removeCollectionVideos(@Param("collectionid") Long collectionid);
	  public void videoLike(@Param("userid") Long userid, @Param("videoid") Long videoid);
	  public void videoPlay(@Param("userid") Long userid, @Param("videoid") Long videoid);	
	  public void videoRate(@Param("userid") Long userid, @Param("videoid") Long videoid,  @Param("rating") Long rating);
	  public void updateVideoRate(@Param("userid") Long userid, @Param("videoid") Long videoid,  @Param("rating") Long rating);
	  public Integer isUserLikesVideo(@Param("userid") Long userid, @Param("videoid") Long videoid);
	  public Integer isUserRatedVideo(@Param("userid") Long userid, @Param("videoid") Long videoid);
	  public void collectionPlay(@Param("userid") Long userid, @Param("collectionid") Long collectionid);
	  public void videoShare(@Param("userid") Long userid, @Param("videoid") Long videoid, @Param("socialnetworkid") Long socialnetworkid);
	  
}
