package com.socialvideo.data.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.socialvideo.data.dto.TagDTO;

public interface TagCloudMapper {
	
	public List<TagDTO> selectTags(@Param("tagtext") String tagtext,@Param("limit") Integer limit);  
	public List<TagDTO> selectAllTags(@Param("limit") Integer limit);
	public List<String> selectTagsString(@Param("tagtext") String tagtext,@Param("limit") Integer limit);  

	  
	
	
}
