package com.socialvideo.data.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.socialvideo.data.model.NotificationEntity;
import com.socialvideo.data.model.VideoReviewEntity;




public interface NotificationsRepository extends CrudRepository<NotificationEntity, Long>  {

	

	 @Query("select c from NotificationEntity c where c.userid = :userid")
	 public List<NotificationEntity> findByUserID(@Param("userid") Long userid);

	 

	 @Query("select c from NotificationEntity c where c.userid = :userid and c.status = :status")
	 public List<NotificationEntity> findByUserIDAndStatus(@Param("userid") Long userid, @Param("status") Integer status);


	 
}
