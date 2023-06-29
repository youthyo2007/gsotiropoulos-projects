package com.socialvideo.data.services.statistics;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.socialvideo.constant.StatisticsCountType;
import com.socialvideo.data.dto.AdminQueryDTO;
import com.socialvideo.data.dto.PaginatorDTO;
import com.socialvideo.data.dto.VideoDTOSmall;
import com.socialvideo.data.dto.statistics.BIChartCountsPerTimeDTO;
import com.socialvideo.data.dto.statistics.BIQueryDTO;
import com.socialvideo.data.dto.statistics.StatisticsDayActivityDTO;
import com.socialvideo.data.dto.statistics.StatisticsVideoStatusDTO;
import com.socialvideo.data.mappers.VideoChartMapper;
import com.socialvideo.data.mappers.StatisticsMapper;
import com.socialvideo.data.mappers.UserChartMapper;
import com.socialvideo.data.model.StatisticsDayActivityEntity;
import com.socialvideo.data.repositories.StatisticsDayActivityRepository;

@Service
public class StatisticsSevice {

	
	@Autowired
	StatisticsMapper statsMapper;

	@Autowired
	VideoChartMapper chartVideoMapper;
	
	@Autowired
	UserChartMapper chartUserMapper;
	
	
	@Autowired
	StatisticsDayActivityRepository dayActivityRepository;
	


	
	public StatisticsVideoStatusDTO selectUserVideoAllStatus(Long userid) {
		return statsMapper.selectUserVideoAllStatus(userid);
		
	}

	public StatisticsVideoStatusDTO selectAdminVideoAllStatus(AdminQueryDTO queryDTO) {
		return statsMapper.selectAdminVideoAllStatus(queryDTO);
		
	}	
	
	
	
	public StatisticsVideoStatusDTO selectBIVideoAllStatus(BIQueryDTO queryDTO) {
		return statsMapper.selectBIVideoAllStatus(queryDTO);
		
	}	
	
	
	public List<VideoDTOSmall> selectTopPlayedVideos(BIQueryDTO queryDTO, Integer limit) {
		return statsMapper.selectTopPlayedVideos(queryDTO,limit);
		
	}	
	
	
	
	
	
	
	
	
	
	
	
	
	public void updateDayActivity(Long dateid, Integer count, Date activityDate, StatisticsCountType countype) {
		StatisticsDayActivityEntity dayActivity = dayActivityRepository.findDayActivityByDateId(dateid);
		if (dayActivity==null) {
			dayActivity = new StatisticsDayActivityEntity(dateid);
			dayActivity.setActivitydate(activityDate);
		}
			
		
		
		
		switch (countype) {
		case VIDEOCOUNT:
			dayActivity.setVideocount(count);
			break;

		case PLAYCOUNT:
			dayActivity.setPlaycount(count);
			break;

		case LIKECOUNT:
			dayActivity.setLikecount(count);
			break;

		case RATINGCOUNT:
			dayActivity.setRatingcount(count);
			break;
			
		case SHARECOUNT:
			dayActivity.setSharecount(count);
			break;

		case USERCOUNT:
			dayActivity.setUsercount(count);
			break;
			
		case REVIEWCOUNT:
			dayActivity.setReviewcount(count);
			break;
			
		
		default:
			break;
		}
		
		dayActivityRepository.save(dayActivity);
		
		
	}	
	

	
	public void generateDailyStatistics(BIQueryDTO queryDTO) {
    	List<BIChartCountsPerTimeDTO> resultList = chartVideoMapper.videosPerDay(queryDTO);
    	for (BIChartCountsPerTimeDTO countDTO : resultList) {
    		updateDayActivity(Long.parseLong(countDTO.getDateNumber()+""), countDTO.getCount(), countDTO.getDateCreated(),countDTO.getCounttype());
    	}
    	
    	

    	resultList = chartVideoMapper.likesPerDay(queryDTO);
    	for (BIChartCountsPerTimeDTO countDTO : resultList) {
    		updateDayActivity(Long.parseLong(countDTO.getDateNumber()+""), countDTO.getCount(), countDTO.getDateCreated(),countDTO.getCounttype());
    	}
    	
    	resultList = chartVideoMapper.playsPerDay(queryDTO);
    	for (BIChartCountsPerTimeDTO countDTO : resultList) {
    		updateDayActivity(Long.parseLong(countDTO.getDateNumber()+""), countDTO.getCount(), countDTO.getDateCreated(),countDTO.getCounttype());
    	}
    	
    	resultList = chartVideoMapper.ratingsPerDay(queryDTO);
    	for (BIChartCountsPerTimeDTO countDTO : resultList) {
    		updateDayActivity(Long.parseLong(countDTO.getDateNumber()+""), countDTO.getCount(), countDTO.getDateCreated(),countDTO.getCounttype());
    	}


    	resultList = chartVideoMapper.sharesPerDay(queryDTO);
    	for (BIChartCountsPerTimeDTO countDTO : resultList) {
    		updateDayActivity(Long.parseLong(countDTO.getDateNumber()+""), countDTO.getCount(), countDTO.getDateCreated(),countDTO.getCounttype());
    	}


    	resultList = chartUserMapper.registrationsPerDay(queryDTO);
    	for (BIChartCountsPerTimeDTO countDTO : resultList) {
    		updateDayActivity(Long.parseLong(countDTO.getDateNumber()+""), countDTO.getCount(), countDTO.getDateCreated(),countDTO.getCounttype());
    	}

		
	}
	
	
	
	public Date selectStatisticsDayActivityLastDate() {
		return statsMapper.selectStatisticsDayActivityLastDate();
	}
	
	
	
	public List<StatisticsDayActivityDTO> selectStatisticsDayActivityList(BIQueryDTO queryDTO, PaginatorDTO paginator) {
		return statsMapper.selectStatisticsDayActivityList(queryDTO,paginator.getOffset(),paginator.getLimit());
		
	}	
	
	
	public Integer selectCountStatisticsDayActivityList(BIQueryDTO queryDTO) {
		return statsMapper.selectCountStatisticsDayActivityList(queryDTO);
		
	}	
		

	



	
}
