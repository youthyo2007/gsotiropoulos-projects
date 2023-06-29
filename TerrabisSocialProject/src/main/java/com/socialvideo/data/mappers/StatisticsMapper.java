package com.socialvideo.data.mappers;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.socialvideo.data.dto.AdminQueryDTO;
import com.socialvideo.data.dto.VideoDTOSmall;
import com.socialvideo.data.dto.statistics.BIChartCountsPerColumnDTO;
import com.socialvideo.data.dto.statistics.BIChartCountsPerTimeDTO;
import com.socialvideo.data.dto.statistics.BIQueryDTO;
import com.socialvideo.data.dto.statistics.StatisticsDayActivityDTO;
import com.socialvideo.data.dto.statistics.StatisticsVideoStatusDTO;

public interface StatisticsMapper {
	
	public StatisticsVideoStatusDTO selectUserVideoAllStatus(@Param("userid") Long userid);
	public StatisticsVideoStatusDTO selectAdminVideoAllStatus(@Param("QUERY") AdminQueryDTO queryDTO);
	public StatisticsVideoStatusDTO selectBIVideoAllStatus(@Param("QUERY") BIQueryDTO queryDTO);
	public List<StatisticsDayActivityDTO> selectStatisticsDayActivityList(@Param("QUERY") BIQueryDTO queryDTO, @Param("offset") Integer offset,  @Param("limit") Integer limit);
	public Date selectStatisticsDayActivityLastDate();
	public Integer selectCountStatisticsDayActivityList(@Param("QUERY") BIQueryDTO queryDTO);
	
	
	
	

	public Double videosPerMonthAvg(@Param("QUERY") BIQueryDTO queryDTO);
	public Double videosPerDayAvg(@Param("QUERY") BIQueryDTO queryDTO);
	public List<BIChartCountsPerTimeDTO> chartVideosPerMonth(@Param("QUERY") BIQueryDTO query);
	public List<BIChartCountsPerTimeDTO> chartVideosPerDay(@Param("QUERY") BIQueryDTO queryDTO);
	public List<BIChartCountsPerColumnDTO> chartVideosPerSource(@Param("QUERY") BIQueryDTO query);
	public List<BIChartCountsPerTimeDTO> videosPerDay(@Param("QUERY") BIQueryDTO queryDTO);
	public List<BIChartCountsPerTimeDTO> playsPerDay(@Param("QUERY") BIQueryDTO queryDTO);
	public List<BIChartCountsPerTimeDTO> sharesPerDay(@Param("QUERY") BIQueryDTO queryDTO);
	public List<BIChartCountsPerTimeDTO> ratingsPerDay(@Param("QUERY") BIQueryDTO queryDTO);
	public List<BIChartCountsPerTimeDTO> likesPerDay(@Param("QUERY") BIQueryDTO queryDTO);

	
	public List<BIChartCountsPerTimeDTO> registrationsPerMonth(@Param("QUERY") BIQueryDTO query);
	public List<BIChartCountsPerTimeDTO> registrationsPerDay(@Param("QUERY") BIQueryDTO queryDTO);
	public List<BIChartCountsPerTimeDTO> registrationsPerDaySecond(@Param("QUERY") BIQueryDTO queryDTO);
	public List<BIChartCountsPerTimeDTO> selectUsersVsVideosPerDay(@Param("QUERY") BIQueryDTO queryDTO);
	public List<BIChartCountsPerColumnDTO> selectVideosCountPerUser(@Param("QUERY") BIQueryDTO query);
	public Integer selectUsersCount(@Param("QUERY") BIQueryDTO queryDTO);

	public List<VideoDTOSmall> selectTopPlayedVideos(@Param("QUERY") BIQueryDTO queryDTO,@Param("limit") Integer limit);
	public List<VideoDTOSmall> selectTopLikedVideos(@Param("QUERY") BIQueryDTO queryDTO,@Param("limit") Integer limit);
	public List<VideoDTOSmall> selectTopSharedVideos(@Param("QUERY") BIQueryDTO queryDTO,@Param("limit") Integer limit);
	public List<VideoDTOSmall> selectTopReviewedVideos(@Param("QUERY") BIQueryDTO queryDTO,@Param("limit") Integer limit);
	
	
	  
}
