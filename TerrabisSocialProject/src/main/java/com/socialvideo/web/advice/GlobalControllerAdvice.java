package com.socialvideo.web.advice;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.mobile.device.Device;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.socialvideo.constant.GoogleConstants;
import com.socialvideo.constant.NotificationStatus;
import com.socialvideo.constant.WebConstants;
import com.socialvideo.data.dto.ActivityIdentity;
import com.socialvideo.data.dto.CurrentUserDTO;
import com.socialvideo.data.dto.NotificationDTO;
import com.socialvideo.data.dto.PersonalizationDTO;
import com.socialvideo.data.dto.QueryDTO;
import com.socialvideo.data.dto.TagDTO;
import com.socialvideo.data.dto.UserDTO;
import com.socialvideo.data.dto.maps.GeocodingDTO;
import com.socialvideo.data.services.DatabaseService;
import com.socialvideo.data.services.NotificationsService;
import com.socialvideo.utilities.CookieHelper;
import com.socialvideo.web.propertyeditors.TagsEditor;


@ControllerAdvice(basePackages = {"com.socialvideo.web.controllers"} )
public class GlobalControllerAdvice {
	
	
	 @Autowired
	 protected NotificationsService nottsService;
	
	 @Autowired
	 protected DatabaseService dbService;
	
	@ModelAttribute(WebConstants.PERSONALIZATION)  
	public PersonalizationDTO init_PERSONALIZATION() throws Exception { 
		PersonalizationDTO persDTO = new PersonalizationDTO();
		persDTO.init();
		return persDTO;
	} 

	@ModelAttribute(WebConstants.CTXPATH)
	public String handleMe(HttpServletRequest request) {
        return request.getContextPath();
    }
	
	
	
	
	
	@ModelAttribute
    public void globalAttributes(Model model, Device device, @AuthenticationPrincipal CurrentUserDTO activeUser, HttpServletRequest request, HttpServletResponse response, @CookieValue(value = "trrbid", required = false) String cookieid, @CookieValue(value = "trrblvs", required = false) String lastvisittime, HttpSession session) {
		
		//GET CURRENT TIME IN SECONDS
		 Long currentTimeseconds = System.currentTimeMillis() / 1000 ;
		 
		  //GET USERID
		  Long userid = null;
		  if(activeUser!=null)
			  userid = activeUser.getId();
		
		  Boolean isnewvisitor = false; //FIRST TIME VISIT
	        Boolean isnewvisit = false; //NEW VISIT WITHIN THE PAST 1 HOUR
	        
	        //SAVE COOKIEID
	        if(cookieid==null) {
	        	cookieid =  UUID.randomUUID().toString();
	        	//years*days*hoursPerDay*minutesPerHour*secondsPerMinute*1000 
	        	CookieHelper.saveCookie("trrbid", cookieid, 10*365*24*60*60*1000, response);
	        	isnewvisitor = true;
	        }
	        
	        //SAVE newvisitflagcookie
	        if(lastvisittime==null)  {
	        	CookieHelper.saveCookie("trrblvs", currentTimeseconds.toString(), 10*1000, response);
	        	isnewvisit = true;
	        }
	        
	        else {
	        	Long lastVisitInSeconds = Long.parseLong(lastvisittime);
	        	//COUNT VISIT AS NEW EVERY 3 HOURS 
	        	if(currentTimeseconds-lastVisitInSeconds>180*60) {
	        		CookieHelper.saveCookie("trrblvs", currentTimeseconds.toString(), 10*1000, response);
	        		isnewvisit = true;
	        	}
	        }
	        

	        
	        
	        //GET IP ADDRESS
	        String ipAddress = request.getHeader("X-FORWARDED-FOR");  
	        if (ipAddress == null) {  
	     	   ipAddress = request.getRemoteAddr();  
	        }

	        
		
		
		
		
	    //ADD ACTIVITY IDENTITY AND DEVICE
		ActivityIdentity identity = new ActivityIdentity(cookieid, session.getId(), ipAddress, userid, isnewvisit, isnewvisitor);
		model.addAttribute(WebConstants.IDENTITY, identity);
		model.addAttribute(WebConstants.DEVICE, device);
	
		if(activeUser!=null) {
			List<NotificationDTO> resultGroupedList = nottsService.selectNotificationsGroupByActivity(userid, NotificationStatus.UNREADED);
			List<NotificationDTO> resultList = nottsService.selectNotificationsOfUser(userid, NotificationStatus.UNREADED, 10);
			model.addAttribute(WebConstants.NOTIFICATIONLIST, resultList);
			model.addAttribute(WebConstants.NOTIFICATIONGROUPEDLIST, resultGroupedList);			
			
			Integer unreadCount = nottsService.selectCountNotificationsOfUser(userid, NotificationStatus.UNREADED);
			Integer allCount = nottsService.selectCountNotificationsOfUser(userid,null);
			model.addAttribute(WebConstants.NOTIFICATIONUNREADEDCOUNT, unreadCount);
			model.addAttribute(WebConstants.NOTIFICATIONALLCOUNT, allCount);
			
		}
	
	
    }
	
	
	@ModelAttribute(WebConstants.NAVBAR_SHOWUPLOADBUTTON)  
	public Boolean init_NAVBAR_SHOWUPLOADBUTTON() throws Exception { 
		return true;
	} 
	
	
	

	@ModelAttribute(WebConstants.NAVBAR_SHOWSEARCHTXT)  
	public Boolean init_NAVBAR_SHOWSEARCHTXT() throws Exception { 
		return false;
	} 

	@ModelAttribute(WebConstants.GMAPRESULT)  
	public GeocodingDTO init_GMAPRESULT() throws Exception { 
		GeocodingDTO gmapresult = new GeocodingDTO(GoogleConstants.initAddress,GoogleConstants.initLocation);
		gmapresult.setEmptyStatus(false);
		gmapresult.setFitBounds(false);
		gmapresult.setZoomlevel(3);
		//SET DEFAULT MARKER LOCATION CENTER OF MAP 
		gmapresult.setMarkerlocation(gmapresult.getLocation());

		return gmapresult;
	} 
	
	
	@ModelAttribute(WebConstants.VIDEOQUERYFRM)  
	public QueryDTO init_VIDEOQUERYFRM() throws Exception { 
			return new QueryDTO();
			//return new QueryDTO();
	} 
	
	

	
	
	
	
	@ModelAttribute(WebConstants.REGISTERFRM)  
	public UserDTO init_REGISTERFRM() throws Exception { 
		return new UserDTO();
	} 
	
	
	@ModelAttribute(WebConstants.LOGINFRM)  
	public UserDTO init_LOGINFRM() throws Exception { 
		return new UserDTO();
	} 
	
	
	
	
/*	@ModelAttribute(WebConstants.DEVICE)  
	public Device device(Device device) throws Exception { 
		return device;
	} 
	
	*/
	
	
	/*
	
	@ModelAttribute
    public void init_FORMS(Model model) {
		System.out.println("GlobalControllerAdvice:init_FORMS");
		model.addAttribute(WebConstants.VIDEOQUERYFRM, new QueryDTO());
		model.addAttribute(WebConstants.REGISTERFRM, new UserDTO());
		model.addAttribute(WebConstants.LOGINFRM, new UserDTO());
    }
	
	*/
	@InitBinder
    protected void initDateFormatBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }
	
	
	@InitBinder
    protected void initTagsEditorBinder(WebDataBinder binder) {
        binder.registerCustomEditor(TagDTO.class, new TagsEditor());
    }
      
	
	
	
	
	
	
	
	
	
	
	/*@InitBinder
	public void dataBinding(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, "dob", new CustomDateEditor(dateFormat, true));
	}
	*/
	
	
	
	
	/*
	@ExceptionHandler(FileNotFoundException.class)
        public ModelAndView myError(Exception exception) {
	    ModelAndView mav = new ModelAndView();
	    mav.addObject("exception", exception);
	    mav.setViewName("error");
	    return mav;
	}*/
} 