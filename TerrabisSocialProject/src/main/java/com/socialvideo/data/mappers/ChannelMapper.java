package com.socialvideo.data.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.socialvideo.data.dto.ChannelDTO;
import com.socialvideo.data.dto.UserDTO;

public interface ChannelMapper {
	
	  public List<ChannelDTO> selectUserChannels(@Param("userid") Long userid,@Param("privacy") Integer privacy,@Param("limit") Integer limit);
	  
	  
	 
}
