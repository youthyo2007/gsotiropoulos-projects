package com.socialvideo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;



@Configuration
public class AWSConfiguration {
	
	public static final String VIDEOTHUMBBUCKET = "terrabisvideothumb";
	public static final String USERSVIDEOBUCKET = "terrabisusersvideobucket";
	public static final String S3STORAGEURI = "https://s3.amazonaws.com";
	
		
	private static String UserS3 = "terrabisusr";
	public static String AccessKeyID =  "AKIAIHYSRHI25V5WWUJA";
	public static String SecretAccessKey = "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";
	private static String RegionS3 = "s3-eu-west-1.amazonaws.com";
	 
    @Bean(name="awsCredentials")
    AWSCredentials getAwsCredentials() {
    	try {
    		return new BasicAWSCredentials(AccessKeyID,SecretAccessKey);
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    		return null;
    		
    	}
    }

    @Bean(name="amazonS3")
    AmazonS3 getAmazonS3() {
    	try {
    		return new AmazonS3Client(getAwsCredentials());
    	} catch (Exception e) {
    		e.printStackTrace();
    		return null;
    	}
    }
    
    
    
    
}
