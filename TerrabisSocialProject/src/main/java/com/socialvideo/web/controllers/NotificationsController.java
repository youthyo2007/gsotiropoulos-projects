package com.socialvideo.web.controllers;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.mobile.device.Device;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.socialvideo.constant.NotificationStatus;
import com.socialvideo.constant.WebConstants;
import com.socialvideo.data.dto.ActivityIdentity;
import com.socialvideo.data.dto.CurrentUserDTO;
import com.socialvideo.data.dto.NotificationDTO;
import com.socialvideo.data.services.NotificationsService;

@EnableAutoConfiguration
@Controller
@RequestMapping(value = "/notifications")
public class NotificationsController {
	private static final Logger LOGGER = Logger.getLogger(NotificationsController.class);


	 @Autowired
	 protected NotificationsService nottsService;
    


    public NotificationsController() {
        super();
    }

	 @ModelAttribute
     public void navBarAttributes(Model model) {
		model.addAttribute(WebConstants.NAVBAR_SHOWUPLOADBUTTON, true);
		model.addAttribute(WebConstants.NAVBAR_SHOWSEARCHTXT, true);
		
     }
    
    //USER PAGE INDEX
    @RequestMapping(value={"/index"}, method=RequestMethod.GET)
    public ModelAndView index(Device device, @AuthenticationPrincipal CurrentUserDTO activeUser, @ModelAttribute(WebConstants.IDENTITY) ActivityIdentity identity) throws Exception {

    	ModelAndView mav = new ModelAndView("notifications-index-view");
    	List<NotificationDTO> resultList = nottsService.selectNotificationsOfUser(activeUser.getId(), null, 50);
        mav.addObject("notificationsList", resultList);
        mav.addObject("device", device);
   
        nottsService.updateNotificationStatus(activeUser.getId(), NotificationStatus.READED);
        
        return mav;
        
    }


}
