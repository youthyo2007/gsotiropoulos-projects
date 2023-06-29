package com.socialvideo.comparators;

import java.util.Comparator;

import com.socialvideo.data.dto.PublicVideoDTO;

public class VideoSortByPathSortIndexComparator implements Comparator<PublicVideoDTO> {

 

    @Override
    public int compare(PublicVideoDTO video1, PublicVideoDTO video2) {
    	int result = 0;
    	
    	
    	if(video1.getPathsort()>video2.getPathsort()) 
    			result = 1;
    	else if(video1.getPathsort()<video2.getPathsort()) 
    			result = -1;
    	
    	
    	return result;
    }
}
    	
    
 
    	
    