
package com.socialvideo.data.services;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.socialvideo.data.dto.ChannelDTO;
import com.socialvideo.data.dto.VideoDTO;
import com.socialvideo.data.dto.UserDTO;
import com.socialvideo.data.mappers.ChannelMapper;
import com.socialvideo.data.mappers.VideoMapper;
import com.socialvideo.data.model.UserEntity;
import com.socialvideo.data.repositories.UserRepository;



@Service
public class  ChannelService {
    
	@Autowired
	private ChannelMapper channelMapper;
	
	
	
	public List<ChannelDTO> selectUserChannels(Long userid, Integer privacy, Integer limit) {
		return channelMapper.selectUserChannels(userid,privacy,limit); 
	}
	
	
    

}
