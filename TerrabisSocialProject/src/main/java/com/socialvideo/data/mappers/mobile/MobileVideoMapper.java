package com.socialvideo.data.mappers.mobile;

import java.util.List;

import org.apache.ibatis.annotations.Param;


import com.socialvideo.data.dto.mobile.MobileQueryDTO;
import com.socialvideo.data.dto.mobile.MobileVideoDTO;

public interface MobileVideoMapper {
	  public MobileVideoDTO selectVideoByUUID(@Param("uuid") String uuid);
	  public List<MobileVideoDTO> selectVideos(@Param("QUERY") MobileQueryDTO queryDTO);
	  public List<MobileVideoDTO> selectRelatedTagVideos(@Param("tags") String tags, @Param("offset") Integer offset, @Param("limit") Integer limit);
	  public List<MobileVideoDTO> selectNearyByVideos(@Param("QUERY") MobileQueryDTO queryDTO);
	  public Integer selectCountVideos(@Param("QUERY") MobileQueryDTO queryDTO);
	  public Integer selectCountNearyByVideos(@Param("QUERY") MobileQueryDTO queryDTO);
	  
	 
	  
}
