
package com.socialvideo.data.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.socialvideo.constant.Constants;
import com.socialvideo.constant.NotificationSettingStatus;
import com.socialvideo.constant.NotificationStatus;
import com.socialvideo.data.dto.CollectionDTO;
import com.socialvideo.data.dto.IDDescrDTO;
import com.socialvideo.data.dto.NotificationDTO;
import com.socialvideo.data.dto.NotificationEnum;
import com.socialvideo.data.dto.NotificationSettingsDTO;
import com.socialvideo.data.dto.UserDTO;
import com.socialvideo.data.dto.VideoDTO;
import com.socialvideo.data.mappers.NotificationMapper;
import com.socialvideo.data.model.NotificationEntity;
import com.socialvideo.data.model.NotificationSettingsEntity;
import com.socialvideo.data.repositories.NotificationSettingsRepository;
import com.socialvideo.data.repositories.NotificationsRepository;
import com.socialvideo.data.services.inter.IVideoService;



@Service
public class  NotificationsService  {
    

	@Autowired
	private NotificationMapper nottsMapper;

	@Autowired
	private NotificationsRepository nottsRepository;

	@Autowired
	private NotificationSettingsRepository nottSettingsRepository;	
	
	
	@Autowired
	private PublicVideoService publicService;
	
	@Autowired
	private IVideoService privateVideoService;
	
	@Autowired
	private UserService userService;

	
	@Autowired
	private CollectionService collService;

	@Autowired
	private DatabaseService dbService;
	
	@Autowired
	private EmailService emailService;
	
	
	
	
	
	
	public List<NotificationDTO> selectNotificationsGroupByActivity(Long userid, Integer status) {
		return 	nottsMapper.selectNotificationsGroupByActivity(userid, status);
	}
	
	
	public List<NotificationDTO> selectNotificationsOfUser(Long userid, Integer status, Integer limit) {
		return 	nottsMapper.selectNotificationsOfUser(userid, status, limit);
	}
	
	
	
	
	public Integer selectCountNotificationsOfUser(Long userid, Integer status) {
		return 	nottsMapper.selectCountNotificationsOfUser(userid, status);
		
	}
	
	
	
	public List<NotificationDTO> selectNotifications(Long userid, String dateCreatedFrom, String dateCreatedTo, Integer status, Integer limit) {
		return 	nottsMapper.selectNotifications(userid, dateCreatedFrom, dateCreatedTo, status, limit);
		
		
	}
	

	public NotificationSettingsEntity findNotifSettingsEntityByUserID(Long userid) {
		return nottSettingsRepository.findByUserID(userid);
	}
	
	
	
	public NotificationSettingsDTO findNotifSettingsByUserID(Long userid) {
		return nottSettingsRepository.findByUserID(userid).DTO();
	}
	
	
	
	
	
	
	
	
	public void notify(NotificationEnum activity, Long userid, Long actionuserid, Long videoid, Long collectionid, Long ratingid, Long socialnetworkid) {
		

		//GET NOTIFICATION SETTINGS
		NotificationSettingsDTO settings = Constants.developmentMode ?  new NotificationSettingsDTO(NotificationSettingStatus.EMAIL) : findNotifSettingsByUserID(userid);

		
		
		
		//FETCH ACTION USER AND USER TO BE NOTIFIED
		UserDTO userDTO = userService.findUserById(userid);
		UserDTO actionUserDTO = actionuserid!=null ? userService.findUserById(actionuserid) : null;
		VideoDTO videoDTO = videoid!=null ? privateVideoService.findVideoById(videoid) : null;
		


		NotificationEntity   entity = new NotificationEntity(activity,userid,actionuserid,videoid,collectionid,ratingid,socialnetworkid,new Date());
		entity.setStatus(NotificationStatus.UNREADED);
		
		
		
		switch (activity) {
		case VIDEOREVIEW:
			if(settings.getVideoreview()!=NotificationSettingStatus.NONE){	nottsRepository.save(entity);}
			if(settings.getVideoreview()==NotificationSettingStatus.EMAIL) {
				String message = "commented";
				emailService.notificationVideoEmail(userDTO.getEmail(), videoDTO, userDTO, actionUserDTO, message);
			} 
				
			break;

		case COLLECTIONADDED:
			if(settings.getVideocollectionadded()!=NotificationSettingStatus.NONE){	nottsRepository.save(entity);}
			if(settings.getVideocollectionadded()==NotificationSettingStatus.EMAIL) {
				CollectionDTO collectionDTO = collService.findCollectionById(collectionid);
				String message = "added to his <strong>"+collectionDTO.getTitle()+"</strong> collection";
				emailService.notificationVideoEmail(userDTO.getEmail(), videoDTO, userDTO, actionUserDTO, message);
				
			} 
			break;

		case VIDEORATE:
			if(settings.getVideorate()!=NotificationSettingStatus.NONE){	nottsRepository.save(entity);}
			if(settings.getVideorate()==NotificationSettingStatus.EMAIL) {
				IDDescrDTO rateDTO = dbService.lookupRating(ratingid); 
				String message = "says '"+rateDTO.getDescr() +"' about";
				emailService.notificationVideoEmail(userDTO.getEmail(), videoDTO, userDTO, actionUserDTO, message);
			} 
			break;

		case VIDEOSHARE:
			if(settings.getVideoshare()!=NotificationSettingStatus.NONE){	nottsRepository.save(entity);}
			if(settings.getVideoshare()==NotificationSettingStatus.EMAIL) {
				IDDescrDTO socialnetworkDTO = dbService.lookupSocialNetwork(ratingid); 
				String message = "shared in "+socialnetworkDTO.getDescr();
				emailService.notificationVideoEmail(userDTO.getEmail(), videoDTO, userDTO, actionUserDTO, message);
			} 
			break;

		case VIDEOLIKE:
			if(settings.getVideolike()!=NotificationSettingStatus.NONE){	nottsRepository.save(entity);}
			if(settings.getVideolike()==NotificationSettingStatus.EMAIL) {
				String message = "likes";
				emailService.notificationVideoEmail(userDTO.getEmail(), videoDTO, userDTO, actionUserDTO, message);
			} 
			break;
			
		case NEWFOLLOWER:
			if(settings.getNewfollower()!=NotificationSettingStatus.NONE){	nottsRepository.save(entity);}
			if(settings.getNewfollower()==NotificationSettingStatus.EMAIL) {
				String message = "follow";
				emailService.notificationNewFollowerEmail(userDTO.getEmail(), userDTO, actionUserDTO);
			} 
			break;
			
		default:
			break;
		}
		

	
	}






	public NotificationsRepository getNottsRepository() {
		return nottsRepository;
	}






	public void setNottsRepository(NotificationsRepository nottsRepository) {
		this.nottsRepository = nottsRepository;
	}






	public UserService getUserService() {
		return userService;
	}






	public void setUserService(UserService userService) {
		this.userService = userService;
	}






	public EmailService getEmailService() {
		return emailService;
	}






	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}


	public void updateNotificationStatus(Long userid, Integer notificationStatus) {
		nottsMapper.updateNotificationStatus(userid,notificationStatus);
		
	}


	public void updateNotificationSettings(Long userid, NotificationSettingsDTO newSettingsDTO) {
		NotificationSettingsEntity entity = nottSettingsRepository.findByUserID(userid);
		entity.LOADEDIT(newSettingsDTO);
		nottSettingsRepository.save(entity);
	
		
	}


	public void createInitialNotificationSettings(long userid, Integer status) {
		NotificationSettingsEntity entity = new NotificationSettingsEntity(userid,status);
		nottSettingsRepository.save(entity);
	}

	
	
		
		
		
}
		

		
		

