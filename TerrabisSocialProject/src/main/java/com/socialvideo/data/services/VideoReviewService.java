
package com.socialvideo.data.services;

import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.socialvideo.constant.VideoReviewStatus;
import com.socialvideo.data.dto.VideoReviewDTO;
import com.socialvideo.data.mappers.VideoReviewMapper;
import com.socialvideo.data.model.VideoReviewEntity;
import com.socialvideo.data.repositories.VideoReviewRepository;



@Service
public class  VideoReviewService  {
    

	@Autowired
	private VideoReviewMapper videoReviewMapper;
	

	@Autowired
	private VideoReviewRepository reviewRepository;

	
	@Autowired
	private UserService userService;


	@Autowired
	private EmailService emailService;
	
	
	

	
	
	public Long createReview(Long userid, Long videoid, String review, String timeStamp) {
		

		VideoReviewEntity reviewEntity = new VideoReviewEntity(userid,videoid,review);
		reviewEntity.setStatus(VideoReviewStatus.OK);
		
		//Calendar calendar = Calendar.getInstance();
		//calendar.setTimeInMillis(Long.parseLong(timeStamp));
		//reviewEntity.setDatecreated(calendar.getTime());

		
		reviewEntity.setDatecreated(new Date());
		reviewRepository.save(reviewEntity);
		return reviewEntity.getId();
	}

	public void removeReview(Long userid, Long reviewid) {
		VideoReviewEntity reviewEntity =reviewRepository.findByUserIDAndReviewID(userid, reviewid);
		if(reviewEntity!=null)
			reviewRepository.delete(reviewEntity);
	}
	
	
	public void markReviewOffend(Long reviewid) {
		VideoReviewEntity reviewEntity =reviewRepository.findOne(reviewid);
		reviewEntity.setStatus(VideoReviewStatus.OFFEND);
		reviewRepository.save(reviewEntity);
	}
	
	
	
	public VideoReviewMapper getVideoReviewMapper() {
		return videoReviewMapper;
	}

	public void setVideoReviewMapper(VideoReviewMapper videoReviewMapper) {
		this.videoReviewMapper = videoReviewMapper;
	}

	public VideoReviewRepository getReviewRepository() {
		return reviewRepository;
	}

	public void setReviewRepository(VideoReviewRepository reviewRepository) {
		this.reviewRepository = reviewRepository;
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
	
	
	
	

		
		
		
}
		

		
		

