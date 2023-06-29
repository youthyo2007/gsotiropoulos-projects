package com.socialvideo.data.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.socialvideo.data.dto.statistics.BIChartCountsPerColumnDTO;
import com.socialvideo.data.dto.statistics.BIChartCountsPerTimeDTO;
import com.socialvideo.data.dto.statistics.BIChartMultiLineDTO;
import com.socialvideo.data.dto.statistics.BIQueryDTO;
import com.socialvideo.data.dto.statistics.StatisticsVideoCountDTO;

public interface VideoChartMapper {


	public Double videosPerMonthAvg(@Param("QUERY") BIQueryDTO queryDTO);
	public Double videosPerDayAvg(@Param("QUERY") BIQueryDTO queryDTO);
	public List<BIChartCountsPerTimeDTO> chartVideosPerMonth(@Param("QUERY") BIQueryDTO query);
	public List<BIChartCountsPerTimeDTO> chartVideosPerDay(@Param("QUERY") BIQueryDTO queryDTO);
	public List<BIChartCountsPerColumnDTO> chartVideosPerSource(@Param("QUERY") BIQueryDTO query);
	public List<BIChartCountsPerTimeDTO> videosPerDay(@Param("QUERY") BIQueryDTO queryDTO);
	public List<BIChartCountsPerTimeDTO> playsPerDay(@Param("QUERY") BIQueryDTO queryDTO);
	public List<BIChartCountsPerTimeDTO> chartPlaysPerDay(@Param("QUERY") BIQueryDTO queryDTO);
	public List<BIChartCountsPerTimeDTO> sharesPerDay(@Param("QUERY") BIQueryDTO queryDTO);
	public List<BIChartCountsPerTimeDTO> ratingsPerDay(@Param("QUERY") BIQueryDTO queryDTO);
	public List<BIChartCountsPerTimeDTO> likesPerDay(@Param("QUERY") BIQueryDTO queryDTO);
	public List<BIChartMultiLineDTO> chartPublishedVsRejectedVideosPerDay(@Param("QUERY") BIQueryDTO queryDTO);
	public StatisticsVideoCountDTO selectStatisticsAllVideosPerformance(@Param("QUERY") BIQueryDTO queryDTO);
	public StatisticsVideoCountDTO selectStatisticsUserVideosPerformance(@Param("QUERY") BIQueryDTO queryDTO);


	
	
	
	  
}
