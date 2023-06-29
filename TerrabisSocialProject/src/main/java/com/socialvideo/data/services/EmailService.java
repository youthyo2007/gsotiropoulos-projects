package com.socialvideo.data.services;

import java.util.concurrent.Future;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.socialvideo.constant.Constants;
import com.socialvideo.constant.SysPropsConstants;
import com.socialvideo.data.dto.UserDTO;
import com.socialvideo.data.dto.VideoDTO;
import com.socialvideo.data.services.inter.IUserService;


@Service
public class EmailService {
	

	
	
	@Autowired 
	private SystemPropertiesService sysPropsService;
	
	
	@Autowired 
	private IUserService userService;
	
	
	@Autowired 
	private JavaMailSender javaMailSender;
	
	
	
	
	
	
    private String getEmailResource(String mail) throws Exception {
        Resource resource = new ClassPathResource("emailtemplates/"+mail+".html");
        String emailString = FileUtils.readFileToString(resource.getFile());
        return emailString;
    }
    
    
    

	private String getEmailStyle() throws Exception {
	        Resource resource = new ClassPathResource("emailtemplates/style.html");
	        String styleString = FileUtils.readFileToString(resource.getFile());
	        return styleString;
	}


	private String initEmail(String emailTemplate) throws Exception  {
	        String emailString = getEmailResource(emailTemplate);
	        emailString = replaceString(emailString,"$style$", getEmailStyle());
	        
	        //emailString = replaceString(emailString,"$serverPath$", sysPropsService.getServerPath().trim());
	        //emailString = replaceString(emailString,"$eeepbpm_context_path$", sysPropsService.getEeepbpm_context_path().trim());
	    return emailString;
	
	} 


	private String replaceString(String source, String place, String value) {
	    String newSource = "";
	    
	
	    try {
	        if ((value!=null) && (value.trim().length()>0))
	            newSource = StringUtils.replace(source, place, value);
	        else
	           newSource = StringUtils.replace(source, place, "-");
	
	    } catch (Exception e) {};
	
	    return newSource;
	}

	
	@Async
	public void newRegistrationEmail(UserDTO user, String activationToken) throws Exception {
		String emailString = initEmail("new-registration");  
	    emailString = replaceString(emailString,"$shortdescr$", user.getShortdesc());
	    emailString = replaceString(emailString,"$email$", user.getEmail());
	    emailString = replaceString(emailString,"$activatetoken$",activationToken);
	    emailString = replaceString(emailString,"$appURL$", Constants.APPURL);
	    String subject = emailString.substring(emailString.indexOf("<emailSubject>")+14, emailString.indexOf("</emailSubject>"));
	    sentHtmlEmail("info@terrabis", user.getEmail(), subject,emailString);
	}
	
	
	@Async
	public void contactUsEmail(String recipientToEmail, UserDTO user, String message) throws Exception {
		String emailString = initEmail("contactus");  
		if(user==null) {
			emailString = replaceString(emailString,"$actionuser$", "Anonymous");
			emailString = replaceString(emailString,"$actionuseruuid$", "070aacb902da420196337d18ee8f995a");
		}
		else {
			emailString = replaceString(emailString,"$actionuser$", user.getShortdesc());
			emailString = replaceString(emailString,"$actionuseruuid$", user.getUUID());			
		}
		
		emailString = replaceString(emailString,"$message$", message);
	    emailString = replaceString(emailString,"$appURL$", Constants.APPURL);
	    String subject = emailString.substring(emailString.indexOf("<emailSubject>")+14, emailString.indexOf("</emailSubject>"));
	    sentHtmlEmail("info@terrabis", recipientToEmail, subject,emailString);
	}
	
	
	@Async
	public void notificationVideoEmail(String recipientTo, VideoDTO video, UserDTO user, UserDTO actionuser, String message)  {
		try {
	    String emailString = initEmail("notification-video");  
	    emailString = replaceString(emailString,"$user$", user.getShortdesc());
	    
	    
	    
	    if(actionuser==null) {
	    	emailString = replaceString(emailString,"$actionuser$", "An anonymous visitor");
	    	emailString = replaceString(emailString,"$actionuseruuid$", user.getUUID());
	    }
	    else {
	    	emailString = replaceString(emailString,"$actionuser$", actionuser.getShortdesc());
	    	emailString = replaceString(emailString,"$actionuseruuid$", actionuser.getUUID());
	    }
	    
	    
		
		emailString = replaceString(emailString,"$message$", message);
	    
	    emailString = replaceString(emailString,"$videoTitle$", video.getTitle());
	    emailString = replaceString(emailString,"$thumbUrl$", video.getThumburl());
	    emailString = replaceString(emailString,"$videoid$", video.getId().toString());
	    emailString = replaceString(emailString,"$appURL$", Constants.APPURL);
	    String subject = emailString.substring(emailString.indexOf("<emailSubject>")+14, emailString.indexOf("</emailSubject>"));
	    sentHtmlEmail("info@terrabis", recipientTo, subject,emailString);
		} catch (Exception e) {e.printStackTrace();}
		
	}
	
	@Async
	public void notificationNewFollowerEmail(String recipientTo,  UserDTO user, UserDTO follower)  {
		try {
	    String emailString = initEmail("notification-new-follower");  
	    emailString = replaceString(emailString,"$user$", user.getShortdesc());
	    emailString = replaceString(emailString,"$actionuser$", follower.getShortdesc());
	 	emailString = replaceString(emailString,"$actionuseruuid$", follower.getUUID());
	    emailString = replaceString(emailString,"$appURL$", Constants.APPURL);
	    String subject = emailString.substring(emailString.indexOf("<emailSubject>")+14, emailString.indexOf("</emailSubject>"));
	    sentHtmlEmail("info@terrabis", recipientTo, subject,emailString);
		} catch (Exception e) {e.printStackTrace();}
		
	}
	
	
	
	
	@Async
	public void incomingVideoEmail(String recipientTo, VideoDTO video) throws Exception {
	    
	    String emailString = initEmail("incoming-video");  
	    emailString = replaceString(emailString,"$videoTitle$", video.getTitle());
	    emailString = replaceString(emailString,"$videoDescription$", video.getDescription());
	    emailString = replaceString(emailString,"$thumbUrl$", video.getThumburl());
	    emailString = replaceString(emailString,"$appURL$", Constants.APPURL);
	    String subject = emailString.substring(emailString.indexOf("<emailSubject>")+14, emailString.indexOf("</emailSubject>"));
	    sentHtmlEmail("info@terrabis", recipientTo, subject,emailString);
	    
	}

	
	
	
	@Async
	public void videoApprovedEmail(String recipientTo, VideoDTO video, UserDTO user) throws Exception {
	    String emailString = initEmail("video-approved");  
	    emailString = replaceString(emailString,"$shortdescr$", user.getShortdesc());
	    emailString = replaceString(emailString,"$videoTitle$", video.getTitle());
	    emailString = replaceString(emailString,"$thumbUrl$", video.getThumburl());
	    emailString = replaceString(emailString,"$videoid$", video.getUUID().toString());
	    emailString = replaceString(emailString,"$appURL$", Constants.APPURL);
	    String subject = emailString.substring(emailString.indexOf("<emailSubject>")+14, emailString.indexOf("</emailSubject>"));
	    sentHtmlEmail("info@terrabis", recipientTo, subject,emailString);
	    
	}

	@Async
	public void videoRejectEmail(String recipientTo, VideoDTO video,  UserDTO user, String rejectionReasonText) throws Exception {
	    String emailString = initEmail("video-reject");  
	    emailString = replaceString(emailString,"$shortdescr$", user.getShortdesc());
	    emailString = replaceString(emailString,"$videoTitle$", video.getTitle());
	    emailString = replaceString(emailString,"$thumbUrl$", video.getThumburl());
	    emailString = replaceString(emailString,"$rejectionReason$", rejectionReasonText);
	    emailString = replaceString(emailString,"$appURL$", Constants.APPURL);    
	    String subject = emailString.substring(emailString.indexOf("<emailSubject>")+14, emailString.indexOf("</emailSubject>"));
	    sentHtmlEmail("info@terrabis", recipientTo, subject,emailString);
	    
	}

	
	
	
	
	
	public void sentHtmlEmail(String recipientFrom, String recipientTo, String subject, String emailHtmlString) throws Exception {
		
			  MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		      MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8"); 
		      mimeMessageHelper.setFrom(recipientFrom);
		      mimeMessageHelper.setSubject(Constants.developmentMode ? "TERRABISDEV - "+subject : subject);
		      mimeMessageHelper.setText(emailHtmlString,true);
		      if(Constants.developmentMode)
		    	  mimeMessageHelper.setTo(sysPropsService.selectSystemPropertyByKey(SysPropsConstants.EMAILLIST_DEVELOPMENT).getValue().split(","));
		      else
		          mimeMessageHelper.setTo(recipientTo);

		      
		      
		      boolean sendEmailFlag = false;
		      //ENABLE SEND EMAIL FLAG
		      if(Constants.developmentMode) {
		    	  sendEmailFlag = true;
		      } else {
		    	  UserDTO recipientDTO = userService.findUserByEmail(recipientTo);
		    	  if(recipientDTO!=null && !recipientDTO.getTstFlag()) {sendEmailFlag=true;}
		      }
		      
		      
		      
		      
		      //SEND ACTUAL EMAIL
		      if(sendEmailFlag) {
		    	  javaMailSender.send(mimeMessage);
		      }
	
		      
		    
		}


	
	
	public void sentTestEmail() {
		
		 	SimpleMailMessage mailMessage=new SimpleMailMessage();
	        mailMessage.setTo("gsotiropoulos2007@gmail.com");
	        mailMessage.setSubject("Terrabis");
	        mailMessage.setText("Hello Terrabis");
	        javaMailSender.send(mailMessage);
		
	}

	public JavaMailSender getJavaMailSender() {
		return javaMailSender;
	}

	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}



	@Async
	public void videoClaimRequestEmail(String recipientTo, VideoDTO video, UserDTO claimer, UserDTO uploader) throws Exception {
	    String emailString = initEmail("video-claim-request");  
	    emailString = replaceString(emailString,"$claimershortdescr$", claimer.getShortdesc());
	    emailString = replaceString(emailString,"$uploadershortdescr$", uploader.getShortdesc());
	    emailString = replaceString(emailString,"$videoTitle$", video.getTitle());
	    emailString = replaceString(emailString,"$thumbUrl$", video.getThumburl());
	    emailString = replaceString(emailString,"$videoid$", video.getId().toString());
	    emailString = replaceString(emailString,"$appURL$", Constants.APPURL);
	    String subject = emailString.substring(emailString.indexOf("<emailSubject>")+14, emailString.indexOf("</emailSubject>"));
	    sentHtmlEmail("info@terrabis", recipientTo, subject,emailString);
		
	}
	
	@Async
	public void videoClaimSucessEmailToClaimer(String recipientTo, VideoDTO video, UserDTO claimer, UserDTO uploader) throws Exception {
	    String emailString = initEmail("video-claim-success-claimer");  
	    emailString = replaceString(emailString,"$claimershortdescr$", claimer.getShortdesc());
	    emailString = replaceString(emailString,"$uploadershortdescr$", uploader.getShortdesc());
	    emailString = replaceString(emailString,"$videoTitle$", video.getTitle());
	    emailString = replaceString(emailString,"$thumbUrl$", video.getThumburl());
	    emailString = replaceString(emailString,"$videoid$", video.getId().toString());
	    emailString = replaceString(emailString,"$appURL$", Constants.APPURL);
	    String subject = emailString.substring(emailString.indexOf("<emailSubject>")+14, emailString.indexOf("</emailSubject>"));
	    sentHtmlEmail("info@terrabis", recipientTo, subject,emailString);
		
	}

	
	@Async
	public void videoClaimSucessEmailToOriginalUploader(String recipientTo, VideoDTO video, UserDTO claimer, UserDTO uploader) throws Exception {
	    String emailString = initEmail("video-claim-success-uploader");  
	    emailString = replaceString(emailString,"$claimershortdescr$", claimer.getShortdesc());
	    emailString = replaceString(emailString,"$uploadershortdescr$", uploader.getShortdesc());
	    emailString = replaceString(emailString,"$videoTitle$", video.getTitle());
	    emailString = replaceString(emailString,"$thumbUrl$", video.getThumburl());
	    emailString = replaceString(emailString,"$videoid$", video.getId().toString());
	    emailString = replaceString(emailString,"$appURL$", Constants.APPURL);
	    String subject = emailString.substring(emailString.indexOf("<emailSubject>")+14, emailString.indexOf("</emailSubject>"));
	    sentHtmlEmail("info@terrabis", recipientTo, subject,emailString);
		
	}



	@Async
	public void resetActivationTokenEmail(UserDTO user, String token) throws Exception {
		String emailString = initEmail("reset-activation");  
	    emailString = replaceString(emailString,"$shortdescr$", user.getShortdesc());
	    emailString = replaceString(emailString,"$email$", user.getEmail());
	    emailString = replaceString(emailString,"$activatetoken$",token);
	    emailString = replaceString(emailString,"$appURL$", Constants.APPURL);
	    String subject = emailString.substring(emailString.indexOf("<emailSubject>")+14, emailString.indexOf("</emailSubject>"));
	    sentHtmlEmail("info@terrabis", user.getEmail(), subject,emailString);
		
	}

	@Async
	public void resetPasswordTokenEmail(UserDTO user, String token) throws Exception {
		String emailString = initEmail("reset-password");  
	    emailString = replaceString(emailString,"$shortdescr$", user.getShortdesc());
	    emailString = replaceString(emailString,"$email$", user.getEmail());
	    emailString = replaceString(emailString,"$passwordtoken$",token);
	    emailString = replaceString(emailString,"$appURL$", Constants.APPURL);
	    String subject = emailString.substring(emailString.indexOf("<emailSubject>")+14, emailString.indexOf("</emailSubject>"));
	    sentHtmlEmail("info@terrabis", user.getEmail(), subject,emailString);
		
	}



	@Async
	public void newPasswordEmail(UserDTO user, String newPassword) throws Exception {
		String emailString = initEmail("new-password");  
	    emailString = replaceString(emailString,"$shortdescr$", user.getShortdesc());
	    emailString = replaceString(emailString,"$email$", user.getEmail());
	    emailString = replaceString(emailString,"$password$",newPassword);
	    emailString = replaceString(emailString,"$appURL$", Constants.APPURL);
	    String subject = emailString.substring(emailString.indexOf("<emailSubject>")+14, emailString.indexOf("</emailSubject>"));
	    sentHtmlEmail("info@terrabis", user.getEmail(), subject,emailString);
		
	}

	



}
