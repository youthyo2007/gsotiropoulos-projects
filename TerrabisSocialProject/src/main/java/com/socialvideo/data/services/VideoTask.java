package com.socialvideo.data.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.socialvideo.data.model.VideoEntity;
import com.socialvideo.data.repositories.VideoRepository;





@Component
public class VideoTask {

	@Autowired
	private IntegrationService integrationService;

	@Autowired
	private VideoRepository videoRepository;
	
	public VideoTask(){}
	
	
	public void BATCH_integrateDataFromVimeoYoutube() {
		
		Iterable<VideoEntity> iterable = videoRepository.findAll();
		for (VideoEntity video : iterable) {
			if(video.getLink()!=null && video.getLink()) {
				integrationService.integrateDataFromVimeoYoutube(video.getId(),false);
			}
	} 

}
	
}
