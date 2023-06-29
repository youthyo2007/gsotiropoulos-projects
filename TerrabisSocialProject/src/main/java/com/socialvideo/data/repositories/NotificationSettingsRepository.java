package com.socialvideo.data.repositories;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.socialvideo.data.model.NotificationSettingsEntity;




public interface NotificationSettingsRepository extends CrudRepository<NotificationSettingsEntity, Long>  {

	

	 @Query("select c from NotificationSettingsEntity c where c.userid = :userid")
	 public NotificationSettingsEntity findByUserID(@Param("userid") Long userid);

	 

}
