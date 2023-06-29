package com.socialvideo.data.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.socialvideo.data.dto.NotificationDTO;


public interface NotificationMapper {

	  
	
	
		public List<NotificationDTO> selectNotificationsGroupByActivity(@Param("userid") Long userid,  @Param("status") Integer status);
		public List<NotificationDTO> selectNotificationsOfUser(@Param("userid") Long userid,  @Param("status") Integer status, @Param("limit") Integer limit);
		public Integer selectCountNotificationsOfUser(@Param("userid") Long userid, @Param("status") Integer status);
	    public List<NotificationDTO> selectNotifications(@Param("userid") Long userid,@Param("dateCreatedFromString") String dateCreatedFrom, @Param("dateCreatedToString") String dateCreatedTo, @Param("status") Integer status,@Param("limit") Integer limit);
	    public void updateNotificationStatus(@Param("userid") Long userid, @Param("status") Integer status);
}
