package com.socialvideo.web.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.socialvideo.data.dto.EventDTO;
import com.socialvideo.data.dto.UserDTO;
import com.socialvideo.data.services.AggregationService;
import com.socialvideo.data.services.AvatarService;
import com.socialvideo.data.services.ChannelService;
import com.socialvideo.data.services.CollectionService;
import com.socialvideo.data.services.ConnectEntityService;
import com.socialvideo.data.services.DatabaseService;
import com.socialvideo.data.services.IGoogleMapsService;
import com.socialvideo.data.services.inter.IUserService;
import com.socialvideo.data.services.inter.IVideoService;
import com.socialvideo.web.events.GenericEvent;


@Component
public class GenericListener implements ApplicationListener<GenericEvent> {
	
	
	
	 @Autowired
	 protected AggregationService aggService;
	 	 
	
	
	@Override
	public void onApplicationEvent(GenericEvent event) {
		EventDTO eventDTO = (EventDTO)event.getSource();
		
		Long userid = eventDTO.getUserID();
		Long collectionid = eventDTO.getCollectionID();
		Long videoid = eventDTO.getVideoID();
		
	
		
		
		//VID UPLOADED
		if(eventDTO.eventType==EventDTO.VID_JUSTUPLOADED) {
			//aggService.USER_VideoCountPlus(userid);
		}
		//VID LIKED
		else if(eventDTO.eventType==EventDTO.VID_LIKE) {
			//aggService.VIDEO_LikePlus(userid,videoid);
		}
		//VID SHARED
		else if(eventDTO.eventType==EventDTO.VID_SHARE) {
					//aggService.VIDEO_SharePlus(userid,videoid);
		}
		//VID PLAY
		else if(eventDTO.eventType==EventDTO.VID_PLAY) {
					//aggService.VIDEO_PlayPlus(userid,videoid);
		}
		//COLLECTION CREATED
		else if(eventDTO.eventType==EventDTO.COL_CREATE) {
			//aggService.USER_CollectionsCountPlus(userid);
		}
		//COLLECTION REMOVED
		else if(eventDTO.eventType==EventDTO.COL_DELETE) {
					//aggService.USER_CollectionsCountMinus(userid);
		}
		//COLLECTION VID ADDED
		else if(eventDTO.eventType==EventDTO.COL_VIDADDED) {
					//aggService.COLL_VideoCountPlus(videoid,collectionid);
		}
		//COLLECTION VID REMOVED
		else if(eventDTO.eventType==EventDTO.COL_VIDREMOVED) {
					//aggService.COLL_VideoCountMinus(videoid,collectionid);
		}
				
		
		
		
        
    }
}
