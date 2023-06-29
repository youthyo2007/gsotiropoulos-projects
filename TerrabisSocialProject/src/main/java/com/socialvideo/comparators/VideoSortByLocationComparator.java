package com.socialvideo.comparators;

import java.util.Comparator;

import com.socialvideo.data.dto.VideoDTO;

public class VideoSortByLocationComparator implements Comparator<VideoDTO> {

 

    @Override
    public int compare(VideoDTO video1, VideoDTO video2) {
    	int result = 0;
    	
    	
    	if(video1.getLatitude().doubleValue()>video2.getLatitude().doubleValue()) {
    		if(video1.getLongitude().doubleValue()<video2.getLongitude().doubleValue())
    			result = 1;
    		else
    			result = -1;
    	}
    	
    	
    	
    	
    	return result;
    }
}
    	
    
 
    	
    