package com.socialvideo.data.services;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.Grantee;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.Permission;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.s3.transfer.Download;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.socialvideo.config.AWSConfiguration;
import com.socialvideo.web.listeners.MyS3ProgressListener;





@Service
public class AWSService {
	
	

	 @Autowired
	 private AmazonS3 amazonS3;
	    
	
	
	public  void createBucket(String bucketname) {
		if(!amazonS3.doesBucketExist(bucketname))
			amazonS3.createBucket(bucketname);	
	}
	
	
	public void deleteFile(String bucketname, String filename) throws Exception {
		amazonS3.deleteObject(new DeleteObjectRequest(bucketname, filename));	
		
		
	}
	

	
	public PutObjectResult addInpustreamtoBucket(String bucketname, String filename, String contentType, Long contentLength, InputStream fileStream) throws Exception {
		ObjectMetadata metaS3 = new ObjectMetadata();
		metaS3.setContentType(contentType);
		metaS3.setContentLength(contentLength);
		
		PutObjectRequest request = new PutObjectRequest(bucketname, filename, fileStream,metaS3);
		request.setCannedAcl(CannedAccessControlList.PublicRead);
		PutObjectResult result = amazonS3.putObject(request);
		return result;
		
	}
	
	
	public PutObjectResult addFiletoBucket(String bucketname, String filename, MultipartFile file) throws Exception {
		ObjectMetadata metaS3 = new ObjectMetadata();
		metaS3.setContentType(file.getContentType());;
		metaS3.setContentLength(file.getSize());
		
		PutObjectRequest request = new PutObjectRequest(bucketname, filename, file.getInputStream(),metaS3);
		request.setCannedAcl(CannedAccessControlList.PublicRead);
		PutObjectResult result = amazonS3.putObject(request);
		return result;
		
		
	}
	
	
	public void downloadFileFromS3Locally(String bucketname, String filename, File file) throws Exception {
		TransferManager tx = new TransferManager( new BasicAWSCredentials(AWSConfiguration.AccessKeyID,AWSConfiguration.SecretAccessKey));
		Download mydownload = tx.download(new GetObjectRequest(bucketname, filename), file);
	
		// You can poll your transfer's status to check its progress
		if (mydownload.isDone() == false) {
		       System.out.println("S3 Transfer: " + mydownload.getDescription());
		       System.out.println("  - State: " + mydownload.getState());
		       System.out.println("  - Progress: "
		                       + mydownload.getProgress().getBytesTransferred());
		}
		
		
		// Transfers also allow you to set a <code>ProgressListener</code> to receive
		// asynchronous notifications about your transfer's progress.
		mydownload.addProgressListener(new MyS3ProgressListener());
		
		
		// Or you can block the current thread and wait for your transfer to
		// to complete. If the transfer fails, this method will throw an
		// AmazonClientException or AmazonServiceException detailing the reason.
		mydownload.waitForCompletion();
		 
		// After the download is complete, call shutdownNow to release the resources.
		tx.shutdownNow();
		
		
	}
	
	
	public boolean folderObjectExists(String bucketname, String foldername) throws Exception {
		
		ObjectListing objects = amazonS3.listObjects(new ListObjectsRequest().withBucketName(bucketname));

	    for (S3ObjectSummary objectSummary: objects.getObjectSummaries()) {
	        if (objectSummary.getKey().equals(foldername)) {
	            return true;
	        }
	    }
	    return false;
	}
	

	public boolean fileObjectExists(String bucketname, String foldername, String filename) throws Exception {
		
		ObjectListing objects = amazonS3.listObjects(new ListObjectsRequest().withBucketName(bucketname).withPrefix(foldername));

	    for (S3ObjectSummary objectSummary: objects.getObjectSummaries()) {
	        if (objectSummary.getKey().equals(filename)) {
	            return true;
	        }
	    }
	    return false;
	}
	
	
	public InputStream getfileObject(String bucketname, String filename) throws Exception {
		
		S3Object object = amazonS3.getObject(new GetObjectRequest(bucketname, filename));
		InputStream objectData = object.getObjectContent();
	    return objectData;
	}
	
	
	
	public  PutObjectResult createFolder(String bucketName, String folderName) {
		// create meta-data for your folder and set content-length to 0
		ObjectMetadata metaS3 = new ObjectMetadata();
		metaS3.setContentLength(0);

		// create empty content
		InputStream emptyContent = new ByteArrayInputStream(new byte[0]);

		// create a PutObjectRequest passing the folder name suffixed by /
		PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName,
					folderName + "/", emptyContent, metaS3);

		// send request to S3 to create folder
		PutObjectResult result = amazonS3.putObject(putObjectRequest);
		return result;
	}

	

	public AmazonS3 getAmazonS3() {
		return amazonS3;
	}



	public void setAmazonS3(AmazonS3 amazonS3) {
		this.amazonS3 = amazonS3;
	}

	public void updateS3ObjectPermissions(String bucketname, String filename, GroupGrantee grantee, Permission permission) {
		AccessControlList objectACL = amazonS3.getObjectAcl(bucketname, filename);
		objectACL.getGrantsAsList().stream().forEach(System.out::println);
		objectACL.grantPermission(grantee, permission);
		System.out.println("---------------------------------------");
		objectACL.getGrantsAsList().stream().forEach(System.out::println);
		amazonS3.setObjectAcl(bucketname, filename, objectACL);

	}
   
	
	
	
	
}
