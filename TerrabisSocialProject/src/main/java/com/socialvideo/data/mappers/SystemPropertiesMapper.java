package com.socialvideo.data.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.socialvideo.data.dto.IDDescrDTO;
import com.socialvideo.data.dto.SystemPropertyDTO;
import com.socialvideo.data.dto.VideoDTO;

public interface SystemPropertiesMapper {
	
	  public List<SystemPropertyDTO> selectSysPropertiesList();
	  public SystemPropertyDTO selectSystemPropertyByKey(@Param("key") String key); 
	  public void updateSystemPropertyByKey(@Param("key") String key, @Param("modifierid") Long modifierid, @Param("value") String value);
	  public void updateSystemPropertyById(@Param("id") Long id, @Param("modifierid") Long modifierid, @Param("value") String value);	  

	 
}
