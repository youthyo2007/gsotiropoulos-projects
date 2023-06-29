package com.socialvideo.web.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.socialvideo.constant.WebConstants;
import com.socialvideo.data.dto.AvatarDTO;
import com.socialvideo.data.dto.CurrentUserDTO;
import com.socialvideo.data.dto.FileUploadResultDTO;
import com.socialvideo.data.dto.NotificationSettingsDTO;
import com.socialvideo.data.dto.SettingsDTO;
import com.socialvideo.data.dto.UserDTO;
import com.socialvideo.data.services.AvatarService;
import com.socialvideo.data.services.NotificationsService;
import com.socialvideo.data.services.inter.IUserService;


@EnableAutoConfiguration
@Controller
@RequestMapping(value = {"user/settings"})
public class UserSettingsViewController {

	 private static final Logger logger = Logger.getLogger(UserSettingsViewController.class);


	 

	 
	 @Autowired
	 protected NotificationsService notifService;
	 
	 
	 @Autowired
	 protected IUserService userService;
	 
	 @Autowired
	 protected AvatarService avatarService;
	 
	 
	 
	
	 @ModelAttribute
     public void init_FORM(@AuthenticationPrincipal CurrentUserDTO activeUser, Model model) {
		  //FETCH USER PROFILE DTO
	        UserDTO userDTO = userService.selectUserWithAvatar(activeUser.getId());
	        model.addAttribute(WebConstants.USERDTO,userDTO);
	        
			
			SettingsDTO settingsDTO = new SettingsDTO();
			settingsDTO.setUser(userDTO);
			settingsDTO.setAvatar(userDTO.getAvatar()!=null ? userDTO.getAvatar() : new AvatarDTO(userDTO.getId()));
			
			
			//GET NOTIFICATION SETTINGS
	        NotificationSettingsDTO notifSettingsDTO = notifService.findNotifSettingsByUserID(activeUser.getId());
	        settingsDTO.setNotification(notifSettingsDTO);
			model.addAttribute(WebConstants.SETTINGSFRM,settingsDTO);
     }
	 

	 
    public UserSettingsViewController() {
    }
    
    
	 @ModelAttribute
     public void navBarAttributes(Model model) {
		model.addAttribute(WebConstants.NAVBAR_SHOWUPLOADBUTTON, true);
		model.addAttribute(WebConstants.NAVBAR_SHOWSEARCHTXT, true);
		
     }
    
    
	 
	    //AVATAR SUBMIT
	    @RequestMapping(value = "/avatar-submit", method = RequestMethod.POST,  produces = {MediaType.APPLICATION_JSON_VALUE}) 
	    @ResponseBody
	    public FileUploadResultDTO updateAvatar(@ModelAttribute(WebConstants.SETTINGSFRM) SettingsDTO settingsFrm, @RequestParam("file") MultipartFile filerequest, @AuthenticationPrincipal CurrentUserDTO activeUser) {
	    	
	    	 UserDTO userDTO = userService.selectUser(activeUser.getId());
	    	FileUploadResultDTO response = new FileUploadResultDTO();
	    	
	    	Boolean fileUploadError = false;
	    	Boolean createAvatar = false;
	    	
	    	
	    	AvatarDTO avatarDTO = userDTO.getAvatar();
	    	if (avatarDTO==null) {
	    		avatarDTO = new AvatarDTO(userDTO.getId());
	    		createAvatar = true;
	    	}
	    	
	    	

		    try {
		    
		    //FILE UPLOAD
		    	MultipartFile file = filerequest;


		    	if (!file.isEmpty()) {
			


		    		avatarDTO.setContenttype(file.getContentType());
		    		avatarDTO.setSize(file.getSize());
		    		avatarDTO.setExtension(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1, file.getOriginalFilename().length()).toLowerCase());
		    		avatarDTO.setDatablob(file.getBytes());
		    
		    		//CREATE OR UPDATE AVATAR
		    		if(createAvatar)
		    			avatarDTO = avatarService.create(avatarDTO);
		    		else
		    			avatarDTO = avatarService.update(avatarDTO);
		    		
		    		 response.successAvatar(avatarDTO);
		    		
					    	
		    	} else {
		    		fileUploadError = true;
		    		response.error(file.getOriginalFilename(), "You failed to upload " +"avatar" + " =>  because the file was empty");	    		
		    	}
		    
		    } catch (Exception e) {
		    	e.printStackTrace();
		    	fileUploadError = true;
	            response.error("User:"+userDTO.getUsername(), "You failed to upload " +"avatar" + " => " + e.getMessage());
		   }
		    	    	

		    //return "redirect:/user-"+userDTO.getUUID();
		    return response;
	    
	    }
	    
	    
	    
	    //PROFILE SUBMIT
	    @RequestMapping(value = "/notification-submit", method = RequestMethod.POST) 
	    public String updateNotifications(@ModelAttribute(WebConstants.SETTINGSFRM) SettingsDTO settingsFrm, final BindingResult result, @AuthenticationPrincipal CurrentUserDTO activeUser) {
	    	notifService.updateNotificationSettings(activeUser.getId(),settingsFrm.getNotification());
	    	return "redirect:/user/settings/index?view=notifications";
	    }


	 
    //PROFILE SUBMIT
    @RequestMapping(value = "/profile-submit", method = RequestMethod.POST) 
    public String updateProfile(@ModelAttribute(WebConstants.SETTINGSFRM) SettingsDTO settingsFrm, final BindingResult result, @AuthenticationPrincipal CurrentUserDTO activeUser) {
    	UserDTO updateDTO = userService.findUserById(activeUser.getId());

    	//UPDATE ONLY FIRST NAME AND LAST NAME
    	updateDTO.setUsername(settingsFrm.getUser().getUsername());
    	updateDTO.setFirstname(settingsFrm.getUser().getFirstname());
    	updateDTO.setLastname(settingsFrm.getUser().getLastname());
    	userService.updateUser(updateDTO);
    	//return "redirect:/user/settings/index?"+WebConstants.VIEW+"="+WebConstants.SETTINGSVIEW_PROFILE;
    	return "redirect:/user-"+updateDTO.getUUID();
    }

   
    //SECURITY SUBMIT
    @RequestMapping(value = "/security-submit", method = RequestMethod.POST) 
    /*public String updatePassword(@PathVariable String UUID,Model model,@Valid @ModelAttribute(WebConstants.SETTINGSFRM) SettingsDTO settingsFrm, final BindingResult result, @AuthenticationPrincipal CurrentUserDTO activeUser) {*/
    public String updatePassword(@ModelAttribute(WebConstants.SETTINGSFRM) SettingsDTO settingsFrm, final BindingResult result, @AuthenticationPrincipal CurrentUserDTO activeUser) {
    UserDTO updateDTO = userService.findUserById(activeUser.getId());
    	
    	//UPDATE PASSWORD
    	userService.changeUserPassword(updateDTO.getId(), settingsFrm.getUser().getNewPassword());
    	//return "redirect:/user/settings/index?"+WebConstants.VIEW+"="+WebConstants.SETTINGSVIEW_SECURITY;
    	return "redirect:/user-"+updateDTO.getUUID();
    }
    
    
    
    
    //USER SETTINGS ACCOUNT
    @RequestMapping(value={"/index"}, method=RequestMethod.GET)
    public ModelAndView index(@RequestParam(value = "view", required=false) String view, @RequestParam(value = "openuploadmodal", required=false) String openuploadmodal, @AuthenticationPrincipal CurrentUserDTO activeUser) {
    	 ModelAndView mav = new ModelAndView("user-settings-index");

    	 if(view!=null) {
            mav.addObject(WebConstants.VIEW, view);
        }

       
        return mav;
    }
    
    
    

    
    //USER SETTINGS PRIVACY
    @RequestMapping(value={"/privacy"}, method=RequestMethod.GET)
    public ModelAndView privacy(@PathVariable String UUID) {
         ModelAndView mav = new ModelAndView("user-settings-privacy");
         return mav;
    }

    
    //USER SETTINGS VIDEOS
    @RequestMapping(value={"/videos"}, method=RequestMethod.GET)
    public ModelAndView videos(@PathVariable String UUID) {
        ModelAndView mav = new ModelAndView("user-settings-videos");
        return mav;
    }

    
    //USER SETTINGS VIDEOS
    @RequestMapping(value={"/channels"}, method=RequestMethod.GET)
    public ModelAndView channels(@PathVariable String UUID) {
        ModelAndView mav = new ModelAndView("user-settings-channels");
        return mav;
    }
    
    
}