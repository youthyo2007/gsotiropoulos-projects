package com.socialvideo.data.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.socialvideo.data.dto.IDDescrDTO;

public interface LookupMapper {
	
	  public List<IDDescrDTO> selectVideoTypeList();
	  public List<IDDescrDTO> selectVideoShootingTypeList();
	  public List<IDDescrDTO> lookupVideoTypeListOfVideo(@Param("videoid") Long videoid);
	  public IDDescrDTO lookupSocialNetwork(@Param("id") Long id);
	  public IDDescrDTO lookupRating(@Param("id") Long id);
	  
	  
	  
	  
	  
	  
	  
	  
	  


	 
}
