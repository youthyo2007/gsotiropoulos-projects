package com.socialvideo.data.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.socialvideo.data.model.VideoReviewEntity;




public interface VideoReviewRepository extends CrudRepository<VideoReviewEntity, Long>  {

	

	 @Query("select c from VideoReviewEntity c where c.userid = :userid")
	 public List<VideoReviewEntity> findByUserID(@Param("userid") Long userid);

		
	 @Query("select c from VideoReviewEntity c where c.videoid = :videoid")
	 public List<VideoReviewEntity> findByVideoID(@Param("videoid") Long videoid);

	 

	 
	 @Query("select c from VideoReviewEntity c where  c.userid = :userid and c.videoid = :videoid")
	 public List<VideoReviewEntity> findByUserIDAndVideoID(@Param("userid") Long userid, @Param("videoid") Long videoid);

	 
	 @Query("select c from VideoReviewEntity c where  c.userid = :userid and c.videoid = :videoid and c.id = :reviewid")
	 public VideoReviewEntity findByUserIDAndVideoIDAndReviewID(@Param("userid") Long userid, @Param("videoid") Long videoid, @Param("reviewid") Long reviewid);

	 @Query("select c from VideoReviewEntity c where  c.userid = :userid and c.id = :reviewid")
	 public VideoReviewEntity findByUserIDAndReviewID(@Param("userid") Long userid, @Param("reviewid") Long reviewid);
	 
	 
}
