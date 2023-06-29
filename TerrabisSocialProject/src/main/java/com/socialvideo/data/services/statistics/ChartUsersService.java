
package com.socialvideo.data.services.statistics;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.socialvideo.data.dto.statistics.BIChartCountsPerColumnDTO;
import com.socialvideo.data.dto.statistics.BIChartCountsPerTimeDTO;
import com.socialvideo.data.dto.statistics.BIChartDTO;
import com.socialvideo.data.dto.statistics.BIChartMultiLineDTO;
import com.socialvideo.data.dto.statistics.BIQueryDTO;
import com.socialvideo.data.mappers.UserChartMapper;

@Service
public class  ChartUsersService {

	@Autowired
	UserChartMapper userChartMapper;
	
	
	
	
	public Integer selectUsersCount(BIQueryDTO queryDTO) {
		return userChartMapper.selectUsersCount(queryDTO);
	}

	
	 public List<BIChartDTO> chartVideosPerUsers(BIQueryDTO queryDTO) {
		        
	   List<BIChartCountsPerColumnDTO> rawData = userChartMapper.selectVideosCountPerUser(queryDTO);
	   int videosCount =  rawData.stream().mapToInt(BIChartCountsPerColumnDTO::getCount).sum();

	   
	   
	   Integer usersWhoUploadedVideoCount =  rawData.size();
	   Integer videosOfTop5Users = rawData.parallelStream().limit(5).mapToInt(x ->x.getCount()).sum();
	   Integer videosOfNext10Users = rawData.parallelStream().skip(5).limit(10).mapToInt(x ->x.getCount()).sum();
	   Integer videosOfNext20Users = rawData.parallelStream().skip(15).limit(20).mapToInt(x ->x.getCount()).sum();
	   Integer videosOfNext50Users = rawData.parallelStream().skip(35).limit(50).mapToInt(x ->x.getCount()).sum();
	   Integer videosOfRestUsers = rawData.parallelStream().skip(85).mapToInt(x ->x.getCount()).sum();
	   
	   
	   List<BIChartDTO> resultList = new ArrayList<>();
	   resultList.add(new BIChartDTO("5 users",videosOfTop5Users));
	   resultList.add(new BIChartDTO("10 users",videosOfNext10Users));
	   resultList.add(new BIChartDTO("20 users",videosOfNext20Users));
	   resultList.add(new BIChartDTO("50 users",videosOfNext50Users));
	   resultList.add(new BIChartDTO("rest",videosOfRestUsers));
	   resultList.add(new BIChartDTO("total",videosCount));
	   
	   
	   return resultList;
	   
	 }
	
	 
	 
	 

		public List<BIChartCountsPerTimeDTO> registrationsPerDay(BIQueryDTO queryDTO) {
			return userChartMapper.registrationsPerDay(queryDTO);
			
		}
	
	 
	 
	
	 public List<BIChartDTO> charRegistrationsPerDay(BIQueryDTO query) {
		 
	     List<BIChartCountsPerTimeDTO> rawDataList = userChartMapper.registrationsPerDaySecond(query); 
	     List<BIChartDTO> resultList = new ArrayList<BIChartDTO>();
	     
	     for(BIChartCountsPerTimeDTO currentDTO : rawDataList) {
		   	  BIChartDTO currentBI = null; 
		   	  currentBI = new BIChartDTO(currentDTO.getDay() +"|"+currentDTO.getMonthString(),currentDTO.getCount());
		      resultList.add(currentBI);
	     }
	      return resultList; 
	 }	
	 

	 public List<BIChartDTO> chartRegistrationsPerMonth(BIQueryDTO query) {
		 
	      List<BIChartCountsPerTimeDTO> rawDataList = userChartMapper.registrationsPerMonth(query); 
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
	 
	 
	 	 public List<BIChartMultiLineDTO> chartRegVsVideosPerDay(BIQueryDTO query) {
	 		    

	 		 List<BIChartCountsPerTimeDTO> videosVSUsersPerDay = userChartMapper.selectUsersVsVideosPerDay(query); 
	 		 
	 	      List<BIChartMultiLineDTO> resultList = new ArrayList<BIChartMultiLineDTO>();

	 	      
	 	      for(BIChartCountsPerTimeDTO currentDTO : videosVSUsersPerDay) {
	 	    	  BIChartMultiLineDTO currentBI = null; 
	 		   	  currentBI = new BIChartMultiLineDTO(currentDTO.getDay() +"|"+currentDTO.getMonthString(),currentDTO.getUsercount(),currentDTO.getVideocount());
	 		      resultList.add(currentBI);
	 	     }
	 	      
	 	      
	 	      
	 	       return resultList; 
	 	     
	 	   }
	 	 
	 	 
	 	 
	 
	 	 
	 	 
	 	 
	 	 
	 	 
	 	 
	 	 
	 	 
	 	 
	 	 
 		 /*     
 	     Map<String, List<BIChartCountsPerTimeDTO>> rawDataMapOfDayKey =
 	    		 rawDataListUsersUnionVideos.parallelStream().collect(Collectors.groupingBy(BIChartCountsPerTimeDTO::getDateString));
 	     
 	     
 	     List<BIChartCountsPerTimeDTO> videosVSUsersPerDay = rawDataMapOfDayKey.entrySet().parallselectUsersVsVideosPerDayelStream()
 	    		 .map(x-> new BIChartCountsPerTimeDTO(
 	    				 x.getValue().get(0).getDateCreated(),
 	    				 x.getKey(),x.getValue().get(0).getDayString(),x.getValue().get(0).getMonthString(), x.getValue().get(0).getDay(), x.getValue().get(0).getMonth(), x.getValue().get(0).getYear(),
 	    				 x.getValue().parallelStream().mapToInt(BIChartCountsPerTimeDTO::getVideocount).sum(),
 	    				 x.getValue().parallelStream().mapToInt(BIChartCountsPerTimeDTO::getUsercount).sum()
 	    				 )
 	    			)
 	    		 .collect(Collectors.toList());
 	    		 
 	    		 
 	    		 //SORTING
 	    		 videosVSUsersPerDay = videosVSUsersPerDay.parallelStream().sorted((x1,x2) -> (x1.getDateCreated().getTime()  <x2.getDateCreated().getTime()) ? -1 : 1).collect(Collectors.toList());
 		 
 	    		 
 	     */
	 
	


}
