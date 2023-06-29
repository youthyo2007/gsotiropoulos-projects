package com.socialvideo.web.controllers;

import java.text.NumberFormat;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.socialvideo.constant.Constants;
import com.socialvideo.constant.QueryConstants;
import com.socialvideo.constant.WebConstants;
import com.socialvideo.data.dto.Activity;
import com.socialvideo.data.dto.Activity.ActivityEnum;
import com.socialvideo.data.dto.ActivityIdentity;
import com.socialvideo.data.dto.PaginatorDTO;
import com.socialvideo.data.dto.PublicVideoDTO;
import com.socialvideo.data.dto.SystemPropertyDTO;
import com.socialvideo.data.dto.VideoCatalogQueryDTO;
import com.socialvideo.data.dto.VideoDTO;
import com.socialvideo.data.services.PublicVideoService;
import com.socialvideo.data.services.SystemPropertiesService;
import com.socialvideo.data.services.inter.IVideoService;

@EnableAutoConfiguration
@Controller
public class IndexViewController {

	 private static final Logger logger = Logger.getLogger(IndexViewController.class);

	 
	 @Autowired
	 protected PublicVideoService videoService;

	 @Autowired
	 protected ObjectMapper jacksonMapper;
	 
	 @Autowired
	 protected SystemPropertiesService systemPropertiesService;


	 @ModelAttribute
     public void navBarAttributes(Model model) {
		model.addAttribute(WebConstants.NAVBAR_SHOWUPLOADBUTTON, true);
		model.addAttribute(WebConstants.NAVBAR_SHOWSEARCHTXT, false);
		
     }
	 
	 
    public IndexViewController() {
    }
  

    

    
    //SITE INDEX
    @RequestMapping(value={"","/index"}, method=RequestMethod.GET)  
    public ModelAndView index(Device device,@ModelAttribute(WebConstants.IDENTITY) ActivityIdentity identity) throws Exception {
        
    	
    	
    	ModelAndView mav = new ModelAndView("site-index-"+ (device.isMobile() || device.isTablet()  ? "mobile" : "desktop"));
    	//ModelAndView mav = new ModelAndView("site-index-mobile");
    	mav.addObject("viewname", "site-index");
        
        
        //GENERATE RANDOM BACKGROUND
        Random generator = new Random();
        int randNum = 1+ generator.nextInt(4);
        
      
        mav.addObject("randomWallPaperNo", randNum);
        
        //GET PROMOTE SITE VIDEOS
        //List<VideoDTO> promoteVideoDTOList = videoService.selectPromoteSiteIndexVideos();
        
        
        //FETCH LATEST VIDEOS
        PaginatorDTO paginator  = new PaginatorDTO(100, 8);
        paginator.firstPage();
        VideoCatalogQueryDTO queryDTO = new VideoCatalogQueryDTO(null, QueryConstants.FilterLatest, null,null);	 
        List<PublicVideoDTO> promoteVideoDTOList = videoService.selectCatalogVideos(queryDTO, paginator);
        mav.addObject("promoteVideoDTOList", promoteVideoDTOList);
        

        //ADD SYSTEM PROPERTIES
        List<SystemPropertyDTO> systemPropertiesList = systemPropertiesService.selectSysPropertiesList();
        
        
        //STREAM WITH LAMDAS CONVERTION FROM LIST TO MAP
        Map<String, String> systemPropertiesMap = systemPropertiesList.stream().collect(Collectors.toMap(SystemPropertyDTO::getKey, c -> c.getValue()));
        mav.addObject(WebConstants.SYSPROPSDTO,systemPropertiesMap);
        
        
        //ADD TOTAL VIDEOS AND TOTAL VIDEO MINUTES
        mav.addObject("totalVideos", NumberFormat.getIntegerInstance().format(videoService.selectCountTotalVideos()));
        mav.addObject("totalVideoHours",  NumberFormat.getIntegerInstance().format(videoService.selectCountTotalVideoMinutes()/60));
        
        
       Activity activity = new Activity(ActivityEnum.INDEXVIEW, identity,null,null,null,null,null);
       logger.info(Constants.LOGGERPREFIX+jacksonMapper.writeValueAsString(activity));

        
        
        return mav;
    }
    
    
    
    
    
    
    
    //SITE INDEX
/*    @RequestMapping(value={"","/index"}, method=RequestMethod.GET)
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response, @CookieValue(value = "trrbid", required = false) String cookieid, @CookieValue(value = "trrbnvs", required = false) String newvisitcookie, HttpSession session) {
        ModelAndView mav = new ModelAndView("site-index");
        mav.addObject("viewname", "site-index");
        
        Boolean newvisitor = false; //FIRST TIME VISIT
        Boolean newvisit = false; //NEW VISIT WITHIN THE PAST 1 HOUR
        
        //SAVE COOKIEID
        if(cookieid==null) {
        	cookieid =  UUID.randomUUID().toString();
        	//years*days*hoursPerDay*minutesPerHour*secondsPerMinute*1000 
        	CookieHelper.saveCookie("trrbid", cookieid, 10*365*24*60*60*1000, response);
        	newvisitor = true;
        }
        
        
        
        
        //SAVE newvisitflagcookie
        if(newvisitcookie==null) {
        	CookieHelper.saveCookie("trrbnvs", "-", 30*60*1000, response);
        	newvisit = true;
        }
        
        
        
        
        //GET IP ADDRESS
        String ipAddress = request.getHeader("X-FORWARDED-FOR");  
        if (ipAddress == null) {  
     	   ipAddress = request.getRemoteAddr();  
        }

        

        try {
            Activity activity = new Activity(ActivityEnum.INDEXVIEW,cookieid, session.getId(),ipAddress,null,null,null,newvisitor,null,null);
            logger.info("[TERRABIS]-"+jacksonMapper.writeValueAsString(activity));

			
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        //GET PROMOTE SITE VIDEOS
        List<VideoDTO> promoteVideoDTOList = videoService.selectPromoteSiteIndexVideos();
        mav.addObject("promoteVideoDTOList", promoteVideoDTOList);
        
        return mav;
    }
*/

    
    //SITE INDEX
    @RequestMapping(value={"/development"}, method=RequestMethod.GET)
    public ModelAndView development() {
        ModelAndView mav = new ModelAndView("development-index");
        mav.addObject("viewname", "development-index");
        

        return mav;
    }
    
    
    
}
    
    
    
    
