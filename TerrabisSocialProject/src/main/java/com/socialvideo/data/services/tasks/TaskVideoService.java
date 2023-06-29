package com.socialvideo.data.services.tasks;

import java.io.File;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.services.youtube.model.Video;
import com.socialvideo.config.AWSConfiguration;
import com.socialvideo.constant.Constants;
import com.socialvideo.constant.VideoClaimStatus;
import com.socialvideo.constant.VideoHealthStatus;
import com.socialvideo.constant.VideoIntegrationStatus;
import com.socialvideo.constant.VideoPublishStatus;
import com.socialvideo.constant.VideoTransferStatus;
import com.socialvideo.data.dto.UserDTO;
import com.socialvideo.data.dto.VideoClaimDTO;
import com.socialvideo.data.dto.VideoDTO;
import com.socialvideo.data.dto.statistics.BIQueryDTO;
import com.socialvideo.data.dto.youtube.YouTubeResultDTO;
import com.socialvideo.data.model.VideoEntity;
import com.socialvideo.data.repositories.VideoRepository;
import com.socialvideo.data.services.AWSService;
import com.socialvideo.data.services.EmailService;
import com.socialvideo.data.services.GoogleAuthService;
import com.socialvideo.data.services.IntegrationService;
import com.socialvideo.data.services.VideoClaimService;
import com.socialvideo.data.services.YouTubeService;
import com.socialvideo.data.services.inter.IUserService;
import com.socialvideo.data.services.inter.IVideoService;
import com.socialvideo.data.services.statistics.StatisticsSevice;



@Service
public class TaskVideoService {
	
	 @Autowired
	 protected IntegrationService integrationService;
	
	 @Autowired
	 protected VideoClaimService claimService;
	

	 @Autowired
	 protected IVideoService videoService;

	 @Autowired
	 protected IUserService userService;
	 
	 
	 @Autowired
	 protected EmailService emailService;

	 @Autowired
	 protected StatisticsSevice statsService;
	 
	@Autowired
	private VideoRepository videoRepository;
	 
	 @Autowired
	 protected GoogleAuthService googleAuthService;
	 
	 
	 @Autowired
	 protected YouTubeService youtubeService;
	 
	 @Autowired
	 protected AWSService awsService;
	 

	 private InputStream getResource(String resourcename) throws Exception {
	        Resource resource = new ClassPathResource(resourcename);
	        return resource.getInputStream();
	 }
	 
	 
	synchronized public VideoDTO fetchFIFOVideoForUpload() {
		
		

		//FETCH FIFO VIDEO OF TYPE FILE
		VideoDTO videoDTO = videoService.selectFileVideoForYoutubeChannelUpload(null,null);
		
		
			if(videoDTO!=null) {
				System.out.println("Preparing Video:"+videoDTO.getId()+" for Youtube Upload");
				
				//UPDATE ITS TRANSFER STATUS FROM NONE TO STARTED
				videoService.updateVideoTransferStatus(videoDTO.getId(), VideoTransferStatus.PREPARED);
			}
			
			return videoDTO;
			
		}


	
	
	synchronized public void switchFileVideoRecordToYoutube(Long videoid, String youtubeid) {
		
		
		//FETCH FIFO VIDEO OF TYPE FILE
		VideoEntity videoEntity = videoService.selectVideoEntity(videoid);
		videoEntity.setLink(true);
		videoEntity.setLinkedurl("https://www.youtube.com/watch?v="+youtubeid);
		videoEntity.setTransferstatus(VideoTransferStatus.COMPLETED);
		videoEntity.setIntegrationstatus(VideoIntegrationStatus.S3_YOUTUBE);
		videoEntity.setHealthstatus(VideoHealthStatus.HEALTHY);
		videoEntity.setStatus(VideoPublishStatus.STAGING);


		videoRepository.save(videoEntity);
		//INTEGRATE WITH VIMEO AND YOUTUBE TO LOAD DATA
		integrationService.integrateDataFromVimeoYoutube(videoid,false);	  
		
		
	    //CREATE THUMBNAIL
	    try {
	    	VideoDTO videoDTO = videoService.findVideoById(videoid);
	    	integrationService.createVideoThumbImg(videoDTO);
	    } catch (Exception e) {e.printStackTrace();}
	    
		
	

	}

	

	 
		
		@Scheduled(fixedDelay=120000) //EVERY 3 MINUTES
		public void checkOwnershipClaimsAndVerify() {
			
			if(Constants.developmentMode)
				return;
			
			
			
			 try {
			
			List<VideoClaimDTO> openClaimsList = claimService.findClaimsByNotStatus(VideoClaimStatus.SUCCESS);
			for(VideoClaimDTO claim : openClaimsList) {
				
	
					VideoDTO video = videoService.findVideoById(claim.getVideoid());
					UserDTO claimer = userService.findUserById(claim.getClaimerid());
					UserDTO uploader = userService.findUserById(claim.getUserid());
					
					
					
					if(video.getYoutube()) {
						YouTubeResultDTO result = youtubeService.getVideoTags(video.getYoutubeid());
						if(result.getTags().contains("terrabisownershipclaim-"+claim.getId().toString())) {
								claimService.verifyOwnership(claim.getClaimerid(), uploader.getId(),claim.getVideoid());
								claimService.claimVerifiedSuccess(claim.getId());
								try {
								emailService.videoClaimSucessEmailToClaimer(claimer.getEmail(), video, claimer, uploader);
								emailService.videoClaimSucessEmailToOriginalUploader(uploader.getEmail(), video, claimer, uploader);
								} catch (Exception e) {e.printStackTrace();}
						} else {
							claimService.claimVerifiedFailure(claim.getId());
							
						}
					}
			}
			
			 } catch (Exception e) {
		   		 e.printStackTrace();
		   		 
		   	 }
	
		}
	
	
		@Scheduled(fixedDelay=10800000) //EVERY 3 HOURS 
		public void calculateDailyStatistics() {
		
			if(Constants.developmentMode)
				return;

			
			 try {
			
				 BIQueryDTO queryDTO = new BIQueryDTO();
		    	//CALCULATE STATISTICS ONE DAY BEFORE THE LAST CALCULATED ENTRY DATE
		    	Calendar calendar = Calendar.getInstance();
		    	calendar.setTime(statsService.selectStatisticsDayActivityLastDate());
		    	calendar.add(Calendar.DATE, -1);
		        queryDTO.setDateCreatedFrom(calendar.getTime());
		        
		        
		        //EXECUTE
		        statsService.generateDailyStatistics(queryDTO);
				 
			        
				 
				 
			
			 } catch (Exception e) {
		   		 e.printStackTrace();
		   		 
		   	 }
	
		}
		
		
	
	@Scheduled(fixedDelay=43200000) //EVERY 12 HOURS
	public void videoUploadFromS3toYoutubeTerrabis() {
		
		if(Constants.developmentMode)
			return;


   	 
   	 try {

   		
   		 	//LOAD CREDENTIAL
    	    GoogleAuthorizationCodeFlow flow = GoogleAuthService.initYoutubeAuthorizationCodeFlow();
    	    Credential credential = flow.loadCredential(GoogleAuthService.TERRABIS_AUTH_USER);
   
    	    
    	    if(credential!=null) {
    	    	//GET VIDEO RECORD AND UPDATE TRASNFER STATUS TO PREPARED SYNCHRONIZED PER THREAD 
    	   		VideoDTO videoDTO = fetchFIFOVideoForUpload();
    	
    	   		if(videoDTO!=null) {
	    	   		//CREATE A LOCAL FILE OBJECT
	    	   		File s3toyoutubefile = new File("tmps3toyoutube_"+videoDTO.getId()+".tmp"); 
	    	   		
	    	   		//GET FILE FROM S3
	    	   		awsService.downloadFileFromS3Locally(AWSConfiguration.USERSVIDEOBUCKET, videoDTO.getId()+"."+videoDTO.getExtension(),s3toyoutubefile);
	    	   		
	    	    	//REFRESH ACCESS TOKEN IN CASE OF EXPIRATION
	    	    	credential.refreshToken();
	    	   
	    	   		//UPLOAD FILE TO YOUTUBE
	    	   		Video youtubeVideo = youtubeService.uploadVideoToTerrabisChannel(credential, videoDTO, FileUtils.openInputStream(s3toyoutubefile));

	    	   		
	    	   		//DELETE TMP FILE 
	    	   		Boolean deleteResult = s3toyoutubefile.delete();
	    	   		System.out.println("Deleting"+"tmps3toyoutube_"+videoDTO.getId()+".video Delete result:"+deleteResult);
	    	   		
	    	   		//SWITCH VIDEO RECORD
	    	   		switchFileVideoRecordToYoutube(videoDTO.getId(),youtubeVideo.getId());
	    	   		
    	   		}
	        }

   	        
   	 } catch (Exception e) {
   		 e.printStackTrace();
   		 
   	 }

      
    }

	
}
