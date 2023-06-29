package com.socialvideo.data.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.socialvideo.data.dto.SystemPropertyDTO;
import com.socialvideo.data.mappers.SystemPropertiesMapper;



@Service
public class SystemPropertiesService {
	
	
	@Autowired
	private SystemPropertiesMapper sysPropertiesMapper;
	
	  public List<SystemPropertyDTO> selectSysPropertiesList() {
		  return sysPropertiesMapper.selectSysPropertiesList();
	  }
	  
	  public SystemPropertyDTO selectSystemPropertyByKey(String key) {
		  return sysPropertiesMapper.selectSystemPropertyByKey(key);
	  } 
	 
	  public void updateSystemPropertyByKey(String key, Long modifierid, String value) {
		  sysPropertiesMapper.updateSystemPropertyByKey(key,modifierid, value);
	  } 
	
	  public void updateSystemPropertyById(Long id, Long modifierid, String value) {
		  sysPropertiesMapper.updateSystemPropertyById(id,modifierid,value);
	  } 
	
	  
}
