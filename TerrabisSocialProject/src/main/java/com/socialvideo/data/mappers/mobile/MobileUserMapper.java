package com.socialvideo.data.mappers.mobile;

import org.apache.ibatis.annotations.Param;

import com.socialvideo.data.dto.mobile.MobileUserDTO;

public interface MobileUserMapper {
	  public MobileUserDTO selectUserByUUID(@Param("uuid") String uuid);
	  public MobileUserDTO selectUserByID(@Param("id") Integer id);
		 
	  
	  
	  
	  
}
