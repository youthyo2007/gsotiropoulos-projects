
package com.socialvideo.data.services.statistics;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.socialvideo.constant.VideoPublishStatus;
import com.socialvideo.data.dto.statistics.BIChartCountsPerColumnDTO;
import com.socialvideo.data.dto.statistics.BIChartCountsPerTimeDTO;
import com.socialvideo.data.dto.statistics.BIChartDTO;
import com.socialvideo.data.dto.statistics.BIChartMultiLineDTO;
import com.socialvideo.data.dto.statistics.BIQueryDTO;
import com.socialvideo.data.dto.statistics.StatisticsVideoCountDTO;
import com.socialvideo.data.mappers.StatisticsMapper;
import com.socialvideo.data.mappers.VideoChartMapper;

@Service
public class  ChartVideoService {

	@Autowired
	VideoChartMapper chartVideoMapper;
	
	
	
	
	
	public double videosPerMonthAvg(BIQueryDTO queryDTO) {
		try {
		return chartVideoMapper.videosPerMonthAvg(queryDTO);
		} catch (Exception e) {return 0;}
	}
	
	public double videosPerDayAvg(BIQueryDTO queryDTO) {
		try {
			return chartVideoMapper.videosPerDayAvg(queryDTO);
			} catch (Exception e) {return 0;}
		
	}
	
	
	
	public List<BIChartCountsPerTimeDTO> videosPerDay(BIQueryDTO queryDTO) {
		return chartVideoMapper.videosPerDay(queryDTO);
		
	}
	
	public List<BIChartCountsPerTimeDTO> playsPerDay(BIQueryDTO queryDTO) {
		return chartVideoMapper.playsPerDay(queryDTO);
		
	}

	public List<BIChartCountsPerTimeDTO> sharesPerDay(BIQueryDTO queryDTO) {
		return chartVideoMapper.sharesPerDay(queryDTO);
		
	}

	public List<BIChartCountsPerTimeDTO> ratingsPerDay(BIQueryDTO queryDTO) {
		return chartVideoMapper.ratingsPerDay(queryDTO);
		
	}

	public List<BIChartCountsPerTimeDTO> likesPerDay(BIQueryDTO queryDTO) {
		return chartVideoMapper.likesPerDay(queryDTO);
		
	}
	
	
	
	 public List<BIChartDTO> chartVideosPerMonth(BIQueryDTO query) {
		 
	      List<BIChartCountsPerTimeDTO> rawDataList = chartVideoMapper.chartVideosPerMonth(query); 
	      List<BIChartDTO> resultList = new ArrayList<BIChartDTO>();

	      
	      int previousYear = -1;  
	      for(BIChartCountsPerTimeDTO currentDTO : rawDataList) {
	    	  BIChartDTO currentBI = null; 
	          int currentYear = currentDTO.getYear();
	          
	          if((currentYear>previousYear)) {
	                previousYear = currentYear;
	                currentBI = new BIChartDTO(currentDTO.getYear()+" | "+ currentDTO.getMonthString(),currentDTO.getCount());

	          }
	          else {
	                currentBI = new BIChartDTO(currentDTO.getMonthString(),currentDTO.getCount());
	          }
	          
	          resultList.add(currentBI);
	          
	      }
	       return resultList; 
	  }

	 
	 
	 
	 public List<BIChartDTO> chartVideosPerDay(BIQueryDTO query) {
	 
     List<BIChartCountsPerTimeDTO> rawDataList = chartVideoMapper.chartVideosPerDay(query); 
     List<BIChartDTO> resultList = new ArrayList<BIChartDTO>();
     
     for(BIChartCountsPerTimeDTO currentDTO : rawDataList) {
	   	  BIChartDTO currentBI = null; 
	   	  currentBI = new BIChartDTO(currentDTO.getDay() +"|"+currentDTO.getMonthString(),currentDTO.getCount());
	      resultList.add(currentBI);
     }
      return resultList; 
	 }
	 

	 
	 
	 
	 public List<BIChartDTO> chartPlaysPerDay(BIQueryDTO query) {
		 
	     List<BIChartCountsPerTimeDTO> rawDataList = chartVideoMapper.chartPlaysPerDay(query); 
	     List<BIChartDTO> resultList = new ArrayList<BIChartDTO>();
	     
	     for(BIChartCountsPerTimeDTO currentDTO : rawDataList) {
		   	  BIChartDTO currentBI = null; 
		   	  currentBI = new BIChartDTO(currentDTO.getDay() +"|"+currentDTO.getMonthString(),currentDTO.getCount());
		      resultList.add(currentBI);
	     }
	      return resultList; 
		 }

	 
	 
	 
	 
	 
	 
		public  List<BIChartDTO>  chartVideosPerSource(BIQueryDTO query) {
	        List<BIChartCountsPerColumnDTO> rawDataList = chartVideoMapper.chartVideosPerSource(query); 
	        return rawDataList.stream().map(x -> new BIChartDTO(x.getColumnDesc(), x.getCount())).collect(Collectors.toList());
	        
		}

		public  List<BIChartDTO>  chartVideosPerStatus(Integer published, Integer rejected, Integer other) {
	        List<BIChartDTO> chartDataList = new ArrayList<>();
	        chartDataList.add(new BIChartDTO("published", published));
	        chartDataList.add(new BIChartDTO("rejected", rejected));
	        chartDataList.add(new BIChartDTO("other", other));
	        
	        
	        return chartDataList;
	        
		}
		
		
			
		public StatisticsVideoCountDTO selectStatisticsAllVideosPerformance(BIQueryDTO queryDTO){
			return chartVideoMapper.selectStatisticsAllVideosPerformance(queryDTO);
			
		};
		
		
		
		
		public StatisticsVideoCountDTO selectStatisticsUserVideosPerformance(BIQueryDTO queryDTO) {
			return selectStatisticsUserVideosPerformance(queryDTO);
			
		};
		
		
		
		
		
		
	 	 public List<BIChartMultiLineDTO> chartPublishedVsRejectedVideosPerDay(BIQueryDTO query) {
	 		return chartVideoMapper.chartPublishedVsRejectedVideosPerDay(query);
	 	  }
	 	 
	 	 
	 	 
	 	 
	 	 
	 	 
	 	 
	 	 
	 	 
		
	 
		
		
	 
/*	 public List<BIChartDTO> chartVideosPerDay(BIQueryDTO query) {
		 
	      List<BIChartCountsPerTimeDTO> rawDataList = statsMapper.videosPerDay(query); 
	      List<BIChartDTO> resultList = new ArrayList<BIChartDTO>();

	      
	      int previousMonth = -1;  
	      for(BIChartCountsPerTimeDTO currentDTO : rawDataList) {
	    	  BIChartDTO currentBI = null; 
	          int currentMonth = currentDTO.getMonth();
	          
	          if((currentMonth>previousMonth)) {
	        	  	previousMonth = currentMonth;
		        	   if(currentDTO.getDayString().equals("Mon"))
		        		   currentBI = new BIChartDTO(currentDTO.getMonthString()+" | "+ currentDTO.getDayString() + "|" + currentDTO.getDay(),currentDTO.getCount());
		        	   else
		        		   currentBI = new BIChartDTO(currentDTO.getMonthString()+" | "+ currentDTO.getDayString(),currentDTO.getCount());
		        		   
		        	   
	          }
	          else {
	        	  
	        	   if(currentDTO.getDayString().equals("Mon"))
	        		   currentBI = new BIChartDTO(currentDTO.getDayString()+ "|" + currentDTO.getDay(),currentDTO.getCount());
	        	   else
	        		   currentBI = new BIChartDTO(currentDTO.getDayString(),currentDTO.getCount());
	          }
	          
	          resultList.add(currentBI);
	          
	      }
	       return resultList; 
	  }	 */
	 



	
	
	

	 

}
