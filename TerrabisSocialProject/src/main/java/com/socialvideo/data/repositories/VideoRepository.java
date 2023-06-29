package com.socialvideo.data.repositories;


import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.socialvideo.data.model.UserEntity;
import com.socialvideo.data.model.VideoEntity;


public interface VideoRepository extends CrudRepository<VideoEntity, Long> {


	VideoEntity findByTitle(String title);
	
	VideoEntity findByUserid(Long id);
	
	
	@Query("select t from VideoEntity t where LOWER(t.title) LIKE LOWER(CONCAT('%',:title, '%'))")
	List<VideoEntity> findByTitleLike(@Param("title") String title);
	
	
	@Query("select t from VideoEntity t where  LOWER(t.title) LIKE LOWER(CONCAT('%',:title, '%')) or LOWER(t.description) LIKE LOWER(CONCAT('%',:description, '%'))")
	List<VideoEntity> findByTitleOrDescriptionLike(@Param("title") String title, @Param("description") String description);

	@Query("select t from VideoEntity t where LOWER(t.tags) LIKE LOWER(CONCAT('%',:tags, '%'))")
	List<VideoEntity> findByTagsLike(@Param("tags") String tags);

	
	@Query("select t from VideoEntity t where t.UUID = :UUIDString")
	VideoEntity findByUUID(@Param("UUIDString") String UUIDString);

	
	@Query("select t from VideoEntity t where t.youtubeid = :videoid")
	VideoEntity findByYoutubeId(@Param("videoid") String videoid);
	
	@Query("select t from VideoEntity t where t.vimeoid = :videoid")
	VideoEntity findByVimeoId(@Param("videoid") String videoid);
	
	
}
