package com.socialvideo.data.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.socialvideo.data.dto.IDDescrDTO;
import com.socialvideo.data.dto.UserDTO;

public interface RejectReasonMapper {
	
	
	
	  public IDDescrDTO lookupRejectionReason(@Param("id") Long id);		
	  public List<IDDescrDTO> selectRejectReasonsList();
	 
}
