package com.socialvideo.data.repositories;


import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.socialvideo.data.model.UserEntity;
import com.socialvideo.data.model.VideoClaimEntity;
import com.socialvideo.data.model.VideoEntity;



public interface VideoClaimRepository extends CrudRepository<VideoClaimEntity, Long>  {

	

	 @Query("select c from VideoClaimEntity c where c.userid = :userid")
	 List<VideoClaimEntity> findClaimsForUser(@Param("userid") Long userid);
	 
	 @Query("select c from VideoClaimEntity c where c.claimerid = :claimerid")
	 List<VideoClaimEntity> findClaimsOfUser(@Param("claimerid") Long claimerid);
	 
	 @Query("select c from VideoClaimEntity c where c.videoid = :videoid")
	 List<VideoClaimEntity> findClaimsOfVideo(@Param("videoid") Long videoid);
	 
	 @Query("select c from VideoClaimEntity c where c.status = :status")
	 List<VideoClaimEntity> findClaimsByStatus(@Param("status") Integer status);

	 @Query("select c from VideoClaimEntity c where c.status != :status")
	 List<VideoClaimEntity> findClaimsByNotStatus(@Param("status") Integer status);
	 
	 @Query("select c from VideoClaimEntity c where  c.claimerid = :claimerid and c.videoid = :videoid")
	 VideoClaimEntity findByClaimerIDAndVideoID(@Param("claimerid") Long claimerid, @Param("videoid") Long videoid);
	
	 @Query("select c from VideoClaimEntity c where c.videoid = :videoid")
	 VideoClaimEntity findByVideoID(@Param("videoid") Long videoid);

	 
}
