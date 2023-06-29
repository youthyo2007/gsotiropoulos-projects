package com.socialvideo.data.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.socialvideo.data.dto.AdminQueryDTO;
import com.socialvideo.data.dto.VideoClaimDTO;

public interface VideoClaimMapper {
	
	  public void verifyOwnership(@Param("ownerid") Long ownerid, @Param("originaluploaderid") Long originaluploaderid, @Param("videoid") Long videoid);
	  public Integer selectCountAdminClaims(@Param("QUERY") AdminQueryDTO queryDTO);
	  public List<VideoClaimDTO> selectAdminClaims(@Param("QUERY") AdminQueryDTO queryDTO, @Param("offset") Integer offset,  @Param("limit") Integer limit);
	 
}
