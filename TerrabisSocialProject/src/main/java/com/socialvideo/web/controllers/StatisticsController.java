package com.socialvideo.web.controllers;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.math3.util.Precision;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.socialvideo.constant.WebConstants;
import com.socialvideo.data.dto.PaginatorDTO;
import com.socialvideo.data.dto.PublicVideoDTO;
import com.socialvideo.data.dto.VideoDTO;
import com.socialvideo.data.dto.VideoDTOSmall;
import com.socialvideo.data.dto.statistics.BIChartDTO;
import com.socialvideo.data.dto.statistics.BIChartMultiLineDTO;
import com.socialvideo.data.dto.statistics.BIQueryDTO;
import com.socialvideo.data.dto.statistics.KPIPerfomanceTargetDTO;
import com.socialvideo.data.dto.statistics.StatisticsDTO;
import com.socialvideo.data.dto.statistics.StatisticsDayActivityDTO;
import com.socialvideo.data.dto.statistics.StatisticsUsersDashboard1DTO;
import com.socialvideo.data.dto.statistics.StatisticsVideoDashboard1DTO;
import com.socialvideo.data.dto.statistics.StatisticsVideoDashboard2DTO;
import com.socialvideo.data.dto.statistics.StatisticsVideoStatusDTO;
import com.socialvideo.data.services.inter.IUserService;
import com.socialvideo.data.services.inter.IVideoService;
import com.socialvideo.data.services.statistics.ChartUsersService;
import com.socialvideo.data.services.statistics.ChartVideoService;
import com.socialvideo.data.services.statistics.StatisticsSevice;



@EnableAutoConfiguration
@Controller
@RequestMapping(value = "/statistics")


@SessionAttributes({WebConstants.STATSQUERYFRM, WebConstants.STATSPAGINATOR})
public class StatisticsController {

	 private static final Logger logger = Logger.getLogger(StatisticsController.class);
	

	 @Autowired
	 protected IVideoService videoService;

	 @Autowired
	 protected IUserService userService;
	 
	 @Autowired
	 protected ChartUsersService chartUsersService;
	 
	 @Autowired
	 protected ChartVideoService chartVideoService;

	 
	 @Autowired
	 protected StatisticsSevice statsService;

	 
	 @ModelAttribute
     public void navBarAttributes(Model model) {
		model.addAttribute(WebConstants.NAVBAR_SHOWUPLOADBUTTON, true);
		
     }


	 @ModelAttribute(WebConstants.STATSPAGINATOR)
     public PaginatorDTO init_STATSPAGINATOR() {
		 	PaginatorDTO paginatorDTO = new PaginatorDTO(100);
	       return  paginatorDTO;
     }
	 	 
	 
	 
	 @ModelAttribute(WebConstants.STATSQUERYFRM)
     public BIQueryDTO init_STATSQUERYFRM() {
		 BIQueryDTO queryDTO = new BIQueryDTO();
		 queryDTO.thisMonthFilter();
	     return  queryDTO;
     }
	 	 
	 
	 
	 
	 
    public StatisticsController() {
    }

    
    //FILTER AND REDIRECT TO ADMIN VIDEOS PAGE
    @RequestMapping(value={"/videos/dashboard-1/filter"}, method=RequestMethod.POST)
    public String videosDashboard1Filter(Model model, @ModelAttribute(WebConstants.STATSQUERYFRM) BIQueryDTO queryDTO) {
       //UPDATE SESSION WITH QUERY DTO
        model.addAttribute(WebConstants.STATSQUERYFRM, queryDTO);
    	return "redirect:/statistics/videos/dashboard-1/";
    }
    
    
    
    //FILTER AND REDIRECT TO ADMIN VIDEOS PAGE
    @RequestMapping(value={"/videos/dashboard-2/filter"}, method=RequestMethod.POST)
    public String videosDashboard2Filter(Model model, @ModelAttribute(WebConstants.STATSQUERYFRM) BIQueryDTO queryDTO) {
       //UPDATE SESSION WITH QUERY DTO
        model.addAttribute(WebConstants.STATSQUERYFRM, queryDTO);
    	return "redirect:/statistics/videos/dashboard-2/";
    }
        
    
    
    //VIDEO-DASHBOARD-ONE
    @RequestMapping(value={"/videos/dashboard-1"}, method=RequestMethod.GET)
    public ModelAndView videosDashboard1(@RequestParam(value = "reset", required=false) Boolean reset, @ModelAttribute(WebConstants.STATSQUERYFRM) BIQueryDTO queryDTO) {
        ModelAndView mav = new ModelAndView("statistics-video-dashboard-1");
        
    	//RESET QUERY
    	if(reset!=null) {
    		queryDTO = new BIQueryDTO();
    		queryDTO.thisMonthFilter();
    		mav.addObject(WebConstants.STATSQUERYFRM, queryDTO);
    	} 
        
               
        //CREATE COUNTS AND PERCENTAGES
        StatisticsVideoStatusDTO videoStatsPerStatusDTO =  statsService.selectBIVideoAllStatus(queryDTO);
        

        //DERIVE  KPI ON PUBLISHED VIDEOS VS ALL VIDEOS  
        KPIPerfomanceTargetDTO KPIvideosPublished  = new KPIPerfomanceTargetDTO(videoStatsPerStatusDTO.getStatus_PUBLISHED().doubleValue(),videoStatsPerStatusDTO.getTotal().doubleValue());
        
        
        //DERIVE UPLOAD PERFOMANCE KPI PER MONTH VS ALL TIME MONTHLY  
        double videosPerMonthAvg  =  Precision.round(chartVideoService.videosPerMonthAvg(queryDTO),1);
        double videosPerMonthAllTimeAvg  =  Precision.round(chartVideoService.videosPerMonthAvg(new BIQueryDTO()),1);
        KPIPerfomanceTargetDTO KPIvideosUploadedInOneMonth  = new KPIPerfomanceTargetDTO(videosPerMonthAvg,videosPerMonthAllTimeAvg);
        
        //DERIVE UPLOAD PERFOMANCE KPI PER DAY VS ALL TIME DAILY  
        double videosPerDayAvg  = Precision.round(chartVideoService.videosPerDayAvg(queryDTO), 1);
        double videosPerDayAllTimeAvg  = Precision.round(chartVideoService.videosPerDayAvg(new BIQueryDTO()),1);
        KPIPerfomanceTargetDTO KPIvideosUploadedInOneDay  = new KPIPerfomanceTargetDTO(videosPerDayAvg,videosPerDayAllTimeAvg);
        

      ///CREATE VIDEO STATISTICS DTO AND SET DATA 
        Gson gson = new Gson();
        TypeToken<List<BIChartDTO>> typeToken = new TypeToken<List<BIChartDTO>>() {};
        
       StatisticsVideoDashboard1DTO dash1DTO =new StatisticsVideoDashboard1DTO();
       
       List<BIChartDTO> chartVideosPerMonth = chartVideoService.chartVideosPerMonth(queryDTO);
       dash1DTO.setBarChartVideosPerMonth(gson.toJson(chartVideosPerMonth,typeToken.getType()));
       //dash1DTO.setMaxVideosPerMonth(chartVideosPerMonth.stream().collect(Collectors.maxBy(Comparator.comparingInt(BIChartDTO::getColumnvalue))).get().getColumnvalue());
       dash1DTO.setMaxVideosPerMonth(300);
       
       
       List<BIChartDTO> chartVideosPerDay = chartVideoService.chartVideosPerDay(queryDTO) ;
       dash1DTO.setLineChartVideosPerDay(gson.toJson(chartVideosPerDay,typeToken.getType()));
       //dash1DTO.setMaxVideosPerDay(chartVideosPerDay.stream().collect(Collectors.maxBy(Comparator.comparingInt(BIChartDTO::getColumnvalue))).get().getColumnvalue());
       dash1DTO.setMaxVideosPerDay(10);
       
       dash1DTO.setPieChartVideosPerSource(gson.toJson(chartVideoService.chartVideosPerSource(queryDTO),typeToken.getType()));
       dash1DTO.setKPIvideosUploadedInOneMonth(KPIvideosUploadedInOneMonth);
       dash1DTO.setKPIvideosUploadedInOneDay(KPIvideosUploadedInOneDay);
       dash1DTO.setKPIvideosPublished(KPIvideosPublished);

      
      
      //ADD STATISTICS WRAPPER IN RESPONSE
      mav.addObject(WebConstants.STATISTICSDTO,new StatisticsDTO(dash1DTO,videoStatsPerStatusDTO));
      
          
       return mav;
    }
    

    
    
    
    
    //VIDEO-DASHBOARD-TWO
    @RequestMapping(value={"/videos/dashboard-2"}, method=RequestMethod.GET)
    public ModelAndView videosDashboard2(@RequestParam(value = "reset", required=false) Boolean reset,@ModelAttribute(WebConstants.STATSQUERYFRM) BIQueryDTO queryDTO) {
        ModelAndView mav = new ModelAndView("statistics-video-dashboard-2");
        
        //RESET QUERY
    	if(reset!=null) {
    		queryDTO = new BIQueryDTO();
    		queryDTO.thisMonthFilter();
    		mav.addObject(WebConstants.STATSQUERYFRM, queryDTO);
    	} 
        
               
        //CREATE COUNTS AND PERCENTAGES
        StatisticsVideoStatusDTO videoStatsPerStatusDTO =  statsService.selectBIVideoAllStatus(queryDTO);
        
        
        StatisticsVideoDashboard2DTO dashDTO =new StatisticsVideoDashboard2DTO();
        Gson gson = new Gson();
        TypeToken<List<BIChartDTO>> typeToken = new TypeToken<List<BIChartDTO>>() {};
        
        

        //DERIVE  KPI ON PUBLISHED VIDEOS VS ALL VIDEOS  
        KPIPerfomanceTargetDTO KPIvideosPublished  = new KPIPerfomanceTargetDTO(videoStatsPerStatusDTO.getStatus_PUBLISHED().doubleValue(),videoStatsPerStatusDTO.getTotal().doubleValue());
        KPIPerfomanceTargetDTO KPIvideosRejected  = new KPIPerfomanceTargetDTO(videoStatsPerStatusDTO.getStatus_REJECTED().doubleValue(),videoStatsPerStatusDTO.getTotal().doubleValue());
        KPIPerfomanceTargetDTO KPIvideosBroken  = new KPIPerfomanceTargetDTO(videoStatsPerStatusDTO.getHealth_BROKEN().doubleValue(),videoStatsPerStatusDTO.getTotal().doubleValue());
        dashDTO.setKPIvideosPublished(KPIvideosPublished);
        dashDTO.setKPIvideosRejected(KPIvideosRejected);
        dashDTO.setKPIvideosBroken(KPIvideosBroken);
        
        
        
        
        List<BIChartMultiLineDTO> chartPublishedVsRejectedVideosPerDay = chartVideoService.chartPublishedVsRejectedVideosPerDay(queryDTO) ;
        List<BIChartDTO> chartPlaysPerDay = chartVideoService.chartPlaysPerDay(queryDTO) ;
        dashDTO.setPieChartVideosPerStatus(gson.toJson(chartVideoService.chartVideosPerStatus(videoStatsPerStatusDTO.getStatus_PUBLISHED(),videoStatsPerStatusDTO.getStatus_REJECTED(),videoStatsPerStatusDTO.getTotal()),typeToken.getType()));
        dashDTO.setLineChartPlaysPerDay(gson.toJson(chartPlaysPerDay,typeToken.getType()));
        dashDTO.setLineChartPublishedVsRejectedVideos(gson.toJson(chartPublishedVsRejectedVideosPerDay,typeToken.getType()));
        dashDTO.setVideosPerformanceStats(chartVideoService.selectStatisticsAllVideosPerformance(queryDTO));
        
        
        
        
        //GET TOP 10 PLAYED VIDEOS, /GET TOP 10 LIKED VIDEOS, /GET TOP 10 SHARED VIDEOS
        List<VideoDTOSmall> resultList = statsService.selectTopPlayedVideos(queryDTO,10);
        dashDTO.setTopPlayedVideoList(resultList);
        
        
        
             
      
      //ADD STATISTICS WRAPPER IN RESPONSE
      mav.addObject(WebConstants.STATISTICSDTO,new StatisticsDTO(dashDTO,videoStatsPerStatusDTO));
      
      
      
      
      return mav;
        

        
    }
    
    
    
    //FILTER AND REDIRECT TO ADMIN VIDEOS PAGE
    @RequestMapping(value={"/users/dashboard-1/filter"}, method=RequestMethod.POST)
    public String usersDashboard1Filter(Model model, @ModelAttribute(WebConstants.STATSQUERYFRM) BIQueryDTO queryDTO) {
       //UPDATE SESSION WITH QUERY DTO
        model.addAttribute(WebConstants.STATSQUERYFRM, queryDTO);
    	return "redirect:/statistics/users/dashboard-1/";
    }
    
    
    
    //USER-DASHBOARD-ONE
    @RequestMapping(value={"/users/dashboard-1"}, method=RequestMethod.GET)
    public ModelAndView usersDashboard1(@RequestParam(value = "reset", required=false) Boolean reset, @ModelAttribute(WebConstants.STATSQUERYFRM) BIQueryDTO queryDTO) {
        ModelAndView mav = new ModelAndView("statistics-users-dashboard-1");
        
        
      //RESET QUERY
    	if(reset!=null) {
    		queryDTO = new BIQueryDTO();
    		queryDTO.thisMonthFilter();
    		mav.addObject(WebConstants.STATSQUERYFRM, queryDTO);
    	} 
        
        
        Gson gson = new Gson();
        TypeToken<List<BIChartDTO>> typeToken = new TypeToken<List<BIChartDTO>>() {};
        
        StatisticsUsersDashboard1DTO dash1DTO =new StatisticsUsersDashboard1DTO();
        dash1DTO.setTotalUsers(chartUsersService.selectUsersCount(queryDTO));
        List<BIChartDTO> chartRegistrationsPerMonth = chartUsersService.chartRegistrationsPerMonth(queryDTO);
        dash1DTO.setBarChartRegistrationsPerMonth(gson.toJson(chartRegistrationsPerMonth,typeToken.getType()));
        dash1DTO.setMaxUsersPerMonth(chartRegistrationsPerMonth.stream().collect(Collectors.maxBy(Comparator.comparingInt(BIChartDTO::getColumnvalue))).get().getColumnvalue());
        
        
        List<BIChartMultiLineDTO> chartRegVsVideosPerDay = chartUsersService.chartRegVsVideosPerDay(queryDTO);
        dash1DTO.setLineChartRegVsVideos(gson.toJson(chartRegVsVideosPerDay,typeToken.getType()));
        dash1DTO.setMaxRegVsVideosPerDay(chartRegVsVideosPerDay.stream().collect(Collectors.maxBy(Comparator.comparingInt(BIChartMultiLineDTO::getValue1))).get().getValue1());
        
        
        List<BIChartDTO> chartUsersPerDay = chartUsersService.charRegistrationsPerDay(queryDTO);
        dash1DTO.setLineChartUsersPerDay(gson.toJson(chartUsersPerDay,typeToken.getType()));
        dash1DTO.setMaxUsersPerDay(chartUsersPerDay.stream().collect(Collectors.maxBy(Comparator.comparingInt(BIChartDTO::getColumnvalue))).get().getColumnvalue());
        
        List<BIChartDTO> resultList = chartUsersService.chartVideosPerUsers(queryDTO);
        

        dash1DTO.setTop5UsersUploadPercentage(""+
        		((resultList.stream().filter(x->x.getColumn1().equals("5 users")).mapToInt(BIChartDTO::getColumnvalue).sum()/
        		resultList.stream().filter(x->x.getColumn1().equals("total")).mapToInt(BIChartDTO::getColumnvalue).sum())
        		)*100);
        
        
        dash1DTO.setDonutChartVideosPerUsersCount(gson.toJson(resultList.stream().filter(x->!x.getColumn1().equals("total")).collect(Collectors.toList()),typeToken.getType()));
         
        
        
        //ADD STATISTICS WRAPPER IN RESPONSE
        mav.addObject(WebConstants.STATISTICSDTO, new StatisticsDTO(dash1DTO));
        
        
        return mav;
    }
    
    
    


    

    //ACTIVITY-DASHBOARD-ONE
    @RequestMapping(value={"/activity/dashboard-1"}, method=RequestMethod.GET)
    public ModelAndView activityDashboard1(@ModelAttribute(WebConstants.STATSQUERYFRM) BIQueryDTO queryDTO) {
        ModelAndView mav = new ModelAndView("statistics-activity-dashboard-1");
        return mav;
    }
    
    
    

    
    //FILTER AND REDIRECT TO ADMIN VIDEOS PAGE
    @RequestMapping(value={"/activity/dashboard-1/filter"}, method=RequestMethod.POST)
    public String activityDashboard1Filter(Model model, @ModelAttribute(WebConstants.STATSQUERYFRM) BIQueryDTO queryDTO) {
       //UPDATE SESSION WITH QUERY DTO
        model.addAttribute(WebConstants.STATSQUERYFRM, queryDTO);
    	return "redirect:/statistics/activity/dashboard-1/data";
    }

    
    

    
    //ACTIVITY-LIST DAILY
    @RequestMapping(value={"/activity/dashboard-1/data"}, method=RequestMethod.GET)
    public ModelAndView activityDashboard1Data(@RequestParam(value = "reset", required=false) Boolean reset, @RequestParam(value = "page", required=false) String pageid,@ModelAttribute(WebConstants.STATSQUERYFRM) BIQueryDTO queryDTO, @ModelAttribute(WebConstants.STATSPAGINATOR) PaginatorDTO paginator) {
    	
    	ModelAndView mav = new ModelAndView("statistics-activity-dashboard-1-data");


    	//RESET QUERY
    	if(reset!=null) {
    		queryDTO = new BIQueryDTO();
    		queryDTO.todayTommowFilter();
    		mav.addObject(WebConstants.STATSQUERYFRM, queryDTO);
    	}
    	
    	
        //INTIALIZE PAGINATOR
        Integer recordsCount = statsService.selectCountStatisticsDayActivityList(queryDTO);
        paginator = new PaginatorDTO(recordsCount);
        RowBounds rowBounds = paginator.firstPage();
    
        //IF PAGE SELECTED
        if(pageid!=null)
        	paginator.goToPage(Integer.parseInt(pageid));
        
        mav.addObject(WebConstants.STATSPAGINATOR,paginator);
    	
    	
    	
    	 
    	 
    	 List<StatisticsDayActivityDTO> resultList = statsService.selectStatisticsDayActivityList(queryDTO,paginator);
     	 for (int i=0; i<resultList.size(); i++) {
     		 StatisticsDayActivityDTO dailyActivityDTO = resultList.get(i);
     		 String dateid = dailyActivityDTO.getDateid().toString();
     		 if(dailyActivityDTO.getVideocount()>0)
      			dailyActivityDTO.setUploadList(videoService.selectUploadedVideos(new BIQueryDTO(dateid),new PaginatorDTO(10,10)));
     		if(dailyActivityDTO.getLikecount()>0)
     			dailyActivityDTO.setLikeList(videoService.selectLikedVideos(new BIQueryDTO(dateid),new PaginatorDTO(10,10)));
     		 if(dailyActivityDTO.getPlaycount()>0)
     			dailyActivityDTO.setPlayList(videoService.selectPlayedVideos(new BIQueryDTO(dateid),new PaginatorDTO(10,10)));
     		 if(dailyActivityDTO.getRatingcount()>0)
     			dailyActivityDTO.setRatingList(videoService.selectRatedVideos(new BIQueryDTO(dateid),new PaginatorDTO(10,10)));
     		 if(dailyActivityDTO.getSharecount()>0)
     			dailyActivityDTO.setShareList(videoService.selectSharedVideos(new BIQueryDTO(dateid),new PaginatorDTO(10,10)));
     		 if(dailyActivityDTO.getUsercount()>0)
     			dailyActivityDTO.setRegistrationList(userService.selectUsers(new BIQueryDTO(dateid),new PaginatorDTO(10,10)));
     		 
     		 resultList.set(i, dailyActivityDTO);
     	 
     	 }	

         
         //Map<Long, StatisticsDayActivityDTO> activityPerDayMap = resultList.stream().collect(Collectors.toMap(StatisticsDayActivityDTO::getDateid,  c -> c));
         mav.addObject("activityPerDayList", resultList);
        
        return mav;
    }
        
    
    //FILTER AND REDIRECT TO ADMIN VIDEOS PAGE
    @RequestMapping(value={"/activity/dashboard-2/filter"}, method=RequestMethod.POST)
    public String activityDashboard2Filter(Model model, @ModelAttribute(WebConstants.STATSQUERYFRM) BIQueryDTO queryDTO) {
       //UPDATE SESSION WITH QUERY DTO
        model.addAttribute(WebConstants.STATSQUERYFRM, queryDTO);
    	return "redirect:/statistics/activity/dashboard-2/data";
    }

    
    //ACTIVITY-LIST TOP LIKED MOST PLAYED, MOST SHARED
    @RequestMapping(value={"/activity/dashboard-2/data"}, method=RequestMethod.GET)
    public ModelAndView activityDashboard2Data(@RequestParam(value = "recalc", required=false) Boolean recalculate,@RequestParam(value = "reset", required=false) Boolean reset, @RequestParam(value = "page", required=false) String pageid,@ModelAttribute(WebConstants.STATSQUERYFRM) BIQueryDTO queryDTO, @ModelAttribute(WebConstants.STATSPAGINATOR) PaginatorDTO paginator) {
    	
    	ModelAndView mav = new ModelAndView("statistics-activity-dashboard-2-data");

    	//RESET QUERY
    	if(reset!=null) {
    		queryDTO = new BIQueryDTO();
    		queryDTO.todayTommowFilter();
    		mav.addObject(WebConstants.STATSQUERYFRM, queryDTO);
    		
    	}


    	//COUNT
    	Integer count1 = videoService.selectCountPlayedVideos(queryDTO);
    	mav.addObject("playedTotal", count1);
    	Integer count2 = videoService.selectCountLikedVideos(queryDTO);
    	mav.addObject("likedTotal", count2);
    	Integer count3 = videoService.selectCountSharedVideos(queryDTO);
    	mav.addObject("sharedTotal", count3);
    	
    	
    	//FETCH DATA
	   	List<VideoDTO> playedList = videoService.selectPlayedVideos(queryDTO,new PaginatorDTO(20,20));
        mav.addObject("playedList", playedList);
	   	List<VideoDTO> likedList = videoService.selectLikedVideos(queryDTO,new PaginatorDTO(20,20));
	   	mav.addObject("likedList", likedList);
        List<VideoDTO> sharedList = videoService.selectSharedVideos(queryDTO,new PaginatorDTO(20,20));
        mav.addObject("sharedList", sharedList);
           
        
        return mav;
    	
    	
    	
/*    	
    	 //INTIALIZE PAGINATOR
        if(recalculate!=null && recalculate) {
        	paginator = new PaginatorDTO(count1+count2+count3);
        	RowBounds rowBounds = paginator.firstPage();
        }
    	
        
    	 //IF PAGE SELECTED
    	 if(pageid!=null) {
        	paginator.goToPage(Integer.parseInt(pageid));
        	
    	 }
    	 
    	 mav.addObject(WebConstants.STATSPAGINATOR,paginator);*/
    	 

    }
    
    
    
    //TRAFFIC-DASHBOARD-ONE
    @RequestMapping(value={"/traffic/dashboard-1"}, method=RequestMethod.GET)
    public ModelAndView trafficDashboard1(@ModelAttribute(WebConstants.STATSQUERYFRM) BIQueryDTO queryDTO) {
        ModelAndView mav = new ModelAndView("statistics-traffic-dashboard-1");
        return mav;
    }
 
    
    
}