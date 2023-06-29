
package com.socialvideo.data.services.mobile;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.socialvideo.data.dto.mobile.MobileQueryDTO;
import com.socialvideo.data.dto.mobile.MobileUserDTO;
import com.socialvideo.data.dto.mobile.MobileVideoDTO;
import com.socialvideo.data.mappers.mobile.*;

@Service
public class  MobileVideoService  {
    

	
	@Autowired
	private MobileVideoMapper mobileVideoMapper;

	
	@Autowired
	private MobileUserMapper mobileUserMapper;
	
	
	  public MobileVideoDTO selectVideoByUUID(String uuid){
		  return mobileVideoMapper.selectVideoByUUID(uuid);
	  }

	  public List<MobileVideoDTO> selectVideos(MobileQueryDTO queryDTO) {
		  return mobileVideoMapper.selectVideos(queryDTO);
	  }

	  public List<MobileVideoDTO> selectNearyByVideos(MobileQueryDTO queryDTO) {
		  return mobileVideoMapper.selectNearyByVideos(queryDTO);
	  }

	  
	  public List<MobileVideoDTO> selectRelatedTagVideos(String tags, Integer offset, Integer limit) {
		  return mobileVideoMapper.selectRelatedTagVideos(tags,offset,limit);
	  }

	  public Integer selectCountVideos(MobileQueryDTO queryDTO) {
		  return mobileVideoMapper.selectCountVideos(queryDTO);
	  }

	public Integer selectCountNearyByVideos(MobileQueryDTO queryDTO) {
		  return mobileVideoMapper.selectCountNearyByVideos(queryDTO);
	}

	public MobileUserDTO selectUserByUUID(String uuid) {
		  return mobileUserMapper.selectUserByUUID(uuid);
	}
		
}
