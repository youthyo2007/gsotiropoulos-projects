package com.socialvideo.web.controllers;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.socialvideo.constant.NotificationSettingStatus;
import com.socialvideo.constant.QueryConstants;
import com.socialvideo.constant.WebConstants;
import com.socialvideo.data.dto.PaginatorDTO;
import com.socialvideo.data.dto.PublicVideoDTO;
import com.socialvideo.data.dto.UserDTO;
import com.socialvideo.data.dto.VerificationTokenDTO;
import com.socialvideo.data.dto.VideoCatalogQueryDTO;
import com.socialvideo.data.dto.VideoDTO;
import com.socialvideo.data.services.CollectionService;
import com.socialvideo.data.services.EmailService;
import com.socialvideo.data.services.NotificationsService;
import com.socialvideo.data.services.PublicVideoService;
import com.socialvideo.data.services.inter.IUserService;
import com.socialvideo.data.services.inter.IVideoService;
import com.socialvideo.utilities.WebUtility;

@EnableAutoConfiguration
@Controller
@RequestMapping(value = "/user-registration")
public class RegistrationController {
	private static final Logger LOGGER = Logger.getLogger(RegistrationController.class);


    @Autowired
    private IUserService userService;


	 @Autowired
	 protected PublicVideoService videoService;
    
    
    @Autowired
    private CollectionService collectionService;

    @Autowired
    private EmailService emailService;
    
	 @Autowired
	 protected NotificationsService nottsService;

	 
    public RegistrationController() {
        super();
    }

    
    
    //SUCCESS REGISTER PAGE
    @RequestMapping(value={"/success"}, method=RequestMethod.GET)
    public ModelAndView user_registration_success(Device device) {
        ModelAndView mav = new ModelAndView("user-registration-success");

        
        //SELECT 10 LATEST VIDEOS
        List<PublicVideoDTO> resultList = videoService.selectCatalogVideos(new VideoCatalogQueryDTO(null, QueryConstants.FilterLatest, null,null), new PaginatorDTO(10));
        //List videoListPartions = WebUtility.publicPartitionVideoList(device,resultList, WebConstants.GRIDSTYLE_BOX);
        mav.addObject("videoListDTO", resultList);
        
        
        
        
        return mav;
        
    }
    
 
    //SUCCESS REGISTER PAGE
    @RequestMapping(value={"/confirmed"}, method=RequestMethod.GET)
    public ModelAndView user_registration_activated(Device device) {
        ModelAndView mav = new ModelAndView("user-registration-confirmed");

        
        //SELECT 10 LATEST VIDEOS
        List<PublicVideoDTO> resultList = videoService.selectCatalogVideos(new VideoCatalogQueryDTO(null, QueryConstants.FilterLatest, null,null), new PaginatorDTO(10));
        //List videoListPartions = WebUtility.publicPartitionVideoList(device,resultList, WebConstants.GRIDSTYLE_BOX);
        mav.addObject("videoListDTO", resultList);
        
        
        
        
        return mav;
        
    }
    

    
    //SUBMIT REGISTRATION FORM 
    @RequestMapping(value = "/submit", method = RequestMethod.POST) 
    public String register(@ModelAttribute(WebConstants.REGISTERFRM) UserDTO accountDTO) throws Exception {

    	
      //CREATE USER ACCOUNT	
     UserDTO registeredUserDTO = userService.registerNewUserAccount(accountDTO);

     String activationToken = UUID.randomUUID().toString();
     //INITIALISE COLLECTIONS
     collectionService.initUserCollectionsUponRegistration(registeredUserDTO.getId());

     
     //INITIALISE NOTIFICATIONS
     nottsService.createInitialNotificationSettings(registeredUserDTO.getId(),NotificationSettingStatus.EMAIL);
     
     userService.createVerificationTokenForUser(registeredUserDTO, activationToken);
     emailService.newRegistrationEmail(registeredUserDTO, activationToken);

        	
     return "redirect:/user-registration/success"; 
         
    }
    
    
    @RequestMapping(value = "/confirm", method = RequestMethod.GET)
    public String confirmRegistration(@RequestParam("token")  String token,  RedirectAttributes redirectAttributes) {
         VerificationTokenDTO verificationToken = userService.getVerificationToken(token);
        if (verificationToken == null) {
             //String message = messages.getMessage("auth.message.regverification.tokeninvalid", null, locale);
        	String message = "something missing from the equation...";
        	 redirectAttributes.addFlashAttribute("message", message);
            return "redirect:/error/message";
        }
        
         UserDTO user = verificationToken.getUser();
         Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
        	String message = "this activation link has expired.";
        	redirectAttributes.addFlashAttribute("message", message);
            return "redirect:/error/message";          
        }

        userService.confirmUserUponRegistration(user);
        return "redirect:/user-registration/confirmed"; 
    }
    
  
    
    
    
    //USER REGISTRATION SUBMIT PAGE STYLE. REPLACED WITH AJAX STYLE
/*    @RequestMapping(value = "/user/registration-submit", method = RequestMethod.POST) 
    public String register(Model model,@Valid @ModelAttribute("registerFrm") UserDTO accountDTO, final BindingResult result,HttpServletRequest request) {
   	boolean emailExists = isEmailExists(accountDTO);
    	if(emailExists) {
    		result.rejectValue("email", "Duplicate.registerFrm.duplicate", "An account already exists for this email.");
    		return "user-signup"; 
    	}
   
    boolean userNameExists = isUserNameExists(accountDTO);
    	if(userNameExists) {
    		result.rejectValue("username", "Duplicate.registerFrm.duplicate", "An account already exists for this username.");
    		return "user-signup"; 
    	}
    	
   
    	
    	
        if (result.hasErrors()) { 
        	model.addAttribute("registerFrm", accountDTO);
        	return "user-signup"; 
            
        } else { 
        	
        	UserDTO registered = createUserAccount(accountDTO);
        	String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, request.getLocale(), appUrl));
        	
            return "redirect:/user/registration-success"; 
        } 
    }
*/

    
    
    
    
    //CONFIRM REGISTRATION
/*    @RequestMapping(value = "/user/registration-confirm", method = RequestMethod.GET)
    public String confirmRegistration( Locale locale,  Model model, @RequestParam("token")  String token, final RedirectAttributes redirectAttributes) {
         VerificationTokenDTO verificationToken = userService.getVerificationToken(token);
        if (verificationToken == null) {
             String message = messages.getMessage("auth.message.regverification.tokeninvalid", null, locale);
             redirectAttributes.addFlashAttribute("message", message);
            return "redirect:/user-error";
        }

         UserDTO user = verificationToken.getUser();
         Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
        	redirectAttributes.addFlashAttribute("message", messages.getMessage("auth.regverification.tokenexpired", null, locale));
        	redirectAttributes.addFlashAttribute("expired", true);
        	redirectAttributes.addFlashAttribute("token", token);
            return "redirect:/user-error";          
        }

        user.setEnabled(true);
        userService.updateUser(user);
        redirectAttributes.addFlashAttribute("message", messages.getMessage("message.regverification.success", null, locale));
        return "redirect:/signin?lang=" + locale.getLanguage();
    }
    

    //RESEND ACTIVAITON TOKEN
    @RequestMapping(value = "/user/registration-resendtoken", method = RequestMethod.GET)
    @ResponseBody
    public GenericResponseDTO resendRegistrationToken( HttpServletRequest request, @RequestParam("token")  String existingToken) {
         VerificationTokenDTO newToken = userService.generateNewVerificationToken(existingToken);
         UserDTO user = userService.getUserByVerificationToken(newToken.getToken());
         String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
         SimpleMailMessage email = constructResendVerificationTokenEmail(appUrl, request.getLocale(), newToken, user);
         //mailSender.send(email);

        return new GenericResponseDTO(messages.getMessage("message.regverification.tokenresend", null, request.getLocale()));
    }


    //CONSTRUCT VERIFICATION EMAIL TO RESEND
    private  SimpleMailMessage constructResendVerificationTokenEmail( String contextPath,  Locale locale,  VerificationTokenDTO newToken,  UserDTO user) {
        String confirmationUrl = contextPath + "/user/registration-confirm?token=" + newToken.getToken();
        String subject = messages.getMessage("message.emailtext.regverification.resend.subject", null, locale);
        String message = messages.getMessage("message.emailtext.regverification.resend.body", null, locale);
       SimpleMailMessage email = new SimpleMailMessage();
       email.setSubject(subject);
       email.setText(message + " \r\n" + confirmationUrl);
       email.setTo(user.getEmail());
       email.setFrom(env.getProperty("support.email"));
       return email;
   }
    
    
    
    private boolean isEmailExists( UserDTO accountDto) {
        UserDTO registeredUserDTO = null;

        	registeredUserDTO = userService.findUserByEmail(accountDto.getEmail());
       
        return registeredUserDTO!=null ? true : false;
    }

    
    private boolean isUserNameExists( UserDTO accountDto) {
        UserDTO registeredUserDTO = null;

        	registeredUserDTO = userService.findUserByUsername(accountDto.getUsername());
       
        return registeredUserDTO!=null ? true : false;
    }
    
    private UserDTO createUserAccount( UserDTO accountDto) {
        UserDTO registeredUserDTO = null;
        try {
        	registeredUserDTO = userService.registerNewUserAccount(accountDto);
        } catch ( EmailExistsException e) {
            return null;
        }
        return registeredUserDTO;
    }
    */
    
    
/*    
    
    
    //REGISTER INDEX
    @RequestMapping(value={"/signup"}, method=RequestMethod.GET)
    public ModelAndView signup() {
        ModelAndView mav = new ModelAndView("user-signup");
        mav.addObject("viewname", "user-signup");
        return mav;
    }
    
    
    
    
    // Reset password
    @RequestMapping(value = "/user/password-reset", method = RequestMethod.POST)
    @ResponseBody
    public GenericResponseDTO resetPassword( HttpServletRequest request, @RequestParam("email")  String userEmail) throws UserNotFoundException {
         UserDTO user = userService.findUserByEmail(userEmail);
        if (user == null) {
            throw new UserNotFoundException();
        }

         String token = UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(user, token);
         String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
         SimpleMailMessage email = constructResetPasswordTokenEmail(appUrl, request.getLocale(), token, user);
        mailSender.send(email);
        return new GenericResponseDTO(messages.getMessage("message.passwordreset.email", null, request.getLocale()));
    }

    @RequestMapping(value = "/user/password-change", method = RequestMethod.GET)
    public String showChangePasswordPage( Locale locale,  Model model, @RequestParam("id")  long id, @RequestParam("token")  String token) {
         PasswordResetTokenDTO passToken = userService.getPasswordResetToken(token);
         UserDTO user = passToken.getUser();
        if (passToken == null || user.getId() != id) {
             String message = messages.getMessage("auth.message.passwordreset.tokeninvalid", null, locale);
            model.addAttribute("message", message);
            return "redirect:/login?lang=" + locale.getLanguage();
        }

         Calendar cal = Calendar.getInstance();
        if ((passToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            model.addAttribute("message", messages.getMessage("auth.message.passwordreset.tokenexpired", null, locale));
            return "redirect:/login?lang=" + locale.getLanguage();
        }

        //Authentication auth = new UsernamePasswordAuthenticationToken(user, null, userDetailsService.loadUserByUsername(user.getEmail()).getAuthorities());
        //SecurityContextHolder.getContext().setAuthentication(auth);

        return "redirect:/password-update?lang=" + locale.getLanguage();
    }

    @RequestMapping(value = "/user/password-save", method = RequestMethod.POST)
    //@PreAuthorize("hasRole('READ_PRIVILEGE')")
    @ResponseBody
    public GenericResponseDTO savePassword( Locale locale, @RequestParam("password")  String password) {
         UserDTO user = (UserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userService.changeUserPassword(user, password);
        return new GenericResponseDTO(messages.getMessage("message.passwordreset.success", null, locale));
    }

    // change user password

    @RequestMapping(value = "/user/password-update", method = RequestMethod.POST)
    //@PreAuthorize("hasRole('READ_PRIVILEGE')")
    @ResponseBody
    public GenericResponseDTO changeUserPassword( Locale locale, @RequestParam("password")  String password, @RequestParam("oldpassword")  String oldPassword) throws InvalidOldPasswordException {
         UserDTO user = userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (!userService.checkIfValidOldPassword(user, oldPassword)) {
            throw new InvalidOldPasswordException();
        }
        userService.changeUserPassword(user, password);
        return new GenericResponseDTO(messages.getMessage("message.passwordupdate.success", null, locale));
    }

    // NON-API

    private  SimpleMailMessage constructResetPasswordTokenEmail( String contextPath,  Locale locale,  String token,  UserDTO user) {
         String url = contextPath + "/user/password-change?id=" + user.getId() + "&token=" + token;
         String message = messages.getMessage("message.password.resend", null, locale);
         SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(user.getEmail());
        email.setSubject("Reset Password");
        email.setText(message + " \r\n" + url);
        email.setFrom(env.getProperty("support.email"));
        return email;
    }


    
    */
}
