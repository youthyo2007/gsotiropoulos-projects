package com.socialvideo.data.repositories;


import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.socialvideo.data.model.AvatarEntity;
import com.socialvideo.data.model.UserEntity;


public interface AvatarRepository extends CrudRepository<AvatarEntity, Long>  {

	List<AvatarEntity> findByUserid(Long userid);
	
	@Query("select t from AvatarEntity t where t.userid = :userid and t.enabled = true")
	AvatarEntity findActive(@Param("userid") Long userid);

}
