package com.socialvideo.data.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.socialvideo.data.dto.statistics.BIChartCountsPerColumnDTO;
import com.socialvideo.data.dto.statistics.BIChartCountsPerTimeDTO;
import com.socialvideo.data.dto.statistics.BIQueryDTO;

public interface UserChartMapper {
	
	
	
	

	
	
	public List<BIChartCountsPerTimeDTO> registrationsPerMonth(@Param("QUERY") BIQueryDTO query);
	public List<BIChartCountsPerTimeDTO> registrationsPerDay(@Param("QUERY") BIQueryDTO queryDTO);
	public List<BIChartCountsPerTimeDTO> registrationsPerDaySecond(@Param("QUERY") BIQueryDTO queryDTO);
	public List<BIChartCountsPerTimeDTO> selectUsersVsVideosPerDay(@Param("QUERY") BIQueryDTO queryDTO);
	public List<BIChartCountsPerColumnDTO> selectVideosCountPerUser(@Param("QUERY") BIQueryDTO query);
	public Integer selectUsersCount(@Param("QUERY") BIQueryDTO queryDTO);
		
	  
}
