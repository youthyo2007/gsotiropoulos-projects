package com.socialvideo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;

@Component
public class AWSS3Loader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean bucketSetup = true;

    
    @Autowired
    private AmazonS3 amazonS3;
    

    // API

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        
    	
    	if (bucketSetup) {
            return;
        }
    	
        //CREATE INITIAL ROLES
    	try {
        createBucketIfNotFound(AWSConfiguration.USERSVIDEOBUCKET);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
       
	}

    private  void createBucketIfNotFound(String bucketname) {
		if(!amazonS3.doesBucketExist(AWSConfiguration.USERSVIDEOBUCKET))
			amazonS3.createBucket(AWSConfiguration.USERSVIDEOBUCKET);
}
   
    
    
    
    
}