package com.socialvideo.data.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.amazonaws.services.s3.model.PutObjectResult;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.googleapis.media.MediaHttpUploaderProgressListener;
import com.google.api.client.http.InputStreamContent;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Comment;
import com.google.api.services.youtube.model.CommentSnippet;
import com.google.api.services.youtube.model.CommentThread;
import com.google.api.services.youtube.model.CommentThreadSnippet;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoSnippet;
import com.google.api.services.youtube.model.VideoStatus;
import com.google.api.services.youtube.model.CommentListResponse;
import com.google.api.services.youtube.model.CommentThreadListResponse;
import com.jayway.jsonpath.JsonPath;
import com.socialvideo.constant.GoogleConstants;
import com.socialvideo.data.dto.VideoDTO;
import com.socialvideo.data.dto.youtube.YouTubeResultDTO;
import com.socialvideo.utilities.WebUtility;
import com.socialvideo.utilities.YouTubeDurationUtils;






@Service
public class YouTubeService  {
	 
	  private static YouTube youtube;
	 
	  @Autowired
	  private RestTemplate restTemplate;
	  
	  @Autowired
	  protected WebUtility webUtility;
	 

	 
	   /**
	     * Define a global variable that specifies the MIME type of the video
	     * being uploaded.
	     */
	    private static final String VIDEO_FILE_FORMAT = "video/*";
	 
	 
	

	public void deleteVideoFromTerrabisChannel(VideoDTO videoDTO) throws Exception {
	 	//LOAD CREDENTIAL
	    GoogleAuthorizationCodeFlow flow = GoogleAuthService.initYoutubeAuthorizationCodeFlow();
	    Credential credential = flow.loadCredential(GoogleAuthService.TERRABIS_AUTH_USER);

	    
	    if(credential!=null) {
        	
	    	//REFRESH TOKEN
	    	credential.refreshToken();

				 
	  	      // This object is used to make YouTube Data API requests.
	  	      youtube = new YouTube.Builder(GoogleAuthService.HTTP_TRANSPORT, GoogleAuthService.JSON_FACTORY, credential).setApplicationName("terrabis-delete-video").build();
	    	
    	     //DELETE VIDEO
	  	    YouTube.Videos.Delete deleteVideoRequest  = youtube.videos().delete(videoDTO.getYoutubeid());
	  	    
	  	    //EXECUTE
	  	    deleteVideoRequest.execute();
		
	}
	    
	}
		
	 
	 private InputStream getResource(String resourcename) throws Exception {
	        Resource resource = new ClassPathResource(resourcename);
	        return resource.getInputStream();
	 }

	 

		public void postTopLevelComment(Credential credential, VideoDTO videoDTO, String text) {
		
			
			try {
				
				
				youtube = new YouTube.Builder(GoogleAuthService.HTTP_TRANSPORT, GoogleAuthService.JSON_FACTORY, credential).setApplicationName("terrabis-post-comment").build();
				

	            // Create a comment snippet with text.
	            CommentSnippet commentSnippet = new CommentSnippet();
	            commentSnippet.setTextOriginal(text);
	            
	            
	            // Create a top-level comment with snippet.
	            Comment topLevelComment = new Comment();
	            topLevelComment.setSnippet(commentSnippet);

	            
	            // Create a comment thread snippet with channelId and videoId and top-level // comment.
	            CommentThreadSnippet commentThreadSnippet = new CommentThreadSnippet();
	            commentThreadSnippet.setChannelId(videoDTO.getYtvmuserid());
	            commentThreadSnippet.setVideoId(videoDTO.getYoutubeid());
	            commentThreadSnippet.setTopLevelComment(topLevelComment);
	            
	            // Create a comment thread with snippet.
	            CommentThread commentThread = new CommentThread();
	            commentThread.setSnippet(commentThreadSnippet);
	            
	            
	            // Insert video comment
	            // Call the YouTube Data API's commentThreads.insert method to
	            // create a comment.
	            CommentThread videoCommentInsertResponse = youtube.commentThreads().insert("snippet", commentThread).execute();

	            
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		} 
		 
	 
	 
		
		public List<CommentThread> videoCommentsList(Credential credential, VideoDTO videoDTO) {
		
			List<CommentThread> videoCommentsThreadList = null;
	
			try {
				
				
				youtube = new YouTube.Builder(GoogleAuthService.HTTP_TRANSPORT, GoogleAuthService.JSON_FACTORY, credential).setApplicationName("terrabis-list-comment").build();
				
				
				CommentThreadListResponse videoCommentsListResponse  = youtube.commentThreads().list("snippet").setVideoId(videoDTO.getYoutubeid()).setTextFormat("plainText").execute();
				videoCommentsThreadList = videoCommentsListResponse.getItems();
				
				
	            
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			return videoCommentsThreadList;
			
			
		} 
		
		
		

	 public Video uploadVideoToTerrabisChannel(Credential credential, VideoDTO videoDTO, InputStream videoInputStream) throws Exception {
		 Video returnedVideo = null;
		 
		 
		 
/*		 youtube = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, new HttpRequestInitializer() {          
			  @Override
			  public void initialize(HttpRequest request) throws IOException {
			    credential.initialize(request);
			    request.setIOExceptionHandler(new HttpBackOffIOExceptionHandler(new ExponentialBackOff()));
			  }
			});*/
		 
		 
		 try {

			 
	      // This object is used to make YouTube Data API requests.
	      youtube = new YouTube.Builder(GoogleAuthService.HTTP_TRANSPORT, GoogleAuthService.JSON_FACTORY, credential).setApplicationName("terrabis-upload-video").build();
		 
		 // Add extra information to the video before uploading.
         Video videoObjectDefiningMetadata = new Video();

         // Set the video to be publicly visible. This is the default
         // setting. Other supporting settings are "unlisted" and "private."
         VideoStatus status = new VideoStatus();
         status.setPrivacyStatus("public");
         videoObjectDefiningMetadata.setStatus(status);

         // Most of the video's metadata is set on the VideoSnippet object.
         VideoSnippet snippet = new VideoSnippet();

         
         // This code uses a Calendar instance to create a unique name and
         // description for test purposes so that you can easily upload
         // multiple files. You should remove this code from your project
         // and use your own standard names instead.
         Calendar cal = Calendar.getInstance();
         snippet.setTitle(videoDTO.getTitle());
         snippet.setDescription(videoDTO.getDescription());
         
         
		 
		 
		 
         // Set the keyword tags that you want to associate with the video.
         List<String> tags =videoDTO.getTagslist();
         tags.add("terrabis-meta-videoid:"+videoDTO.getId());
         tags.add("terrabis-meta-userid:"+videoDTO.getUserid());
         snippet.setTags(tags);

         // Add the completed snippet object to the video resource.
         videoObjectDefiningMetadata.setSnippet(snippet);

         //READ THE VIDEO FILE
         InputStreamContent mediaContent = new InputStreamContent(VIDEO_FILE_FORMAT, videoInputStream);

         
      // Insert the video. The command sends three arguments. The first
         // specifies which information the API request is setting and which
         // information the API response should return. The second argument
         // is the video resource that contains metadata about the new video.
         // The third argument is the actual video content.
         YouTube.Videos.Insert videoInsert = youtube.videos()
                 .insert("snippet,statistics,status", videoObjectDefiningMetadata, mediaContent);

         // Set the upload type and add an event listener.
         MediaHttpUploader uploader = videoInsert.getMediaHttpUploader();

         // Indicate whether direct media upload is enabled. A value of
         // "True" indicates that direct media upload is enabled and that
         // the entire media content will be uploaded in a single request.
         // A value of "False," which is the default, indicates that the
         // request will use the resumable media upload protocol, which
         // supports the ability to resume an upload operation after a
         // network interruption or other transmission failure, saving
         // time and bandwidth in the event of network failures.
         uploader.setDirectUploadEnabled(true);
         
         MediaHttpUploaderProgressListener progressListener = new MediaHttpUploaderProgressListener() {
             public void progressChanged(MediaHttpUploader uploader) throws IOException {
                 switch (uploader.getUploadState()) {
                     case INITIATION_STARTED:
                         System.out.println(videoDTO.getTitle()+":Initiation Started");
                         break;
                     case INITIATION_COMPLETE:
                         System.out.println(videoDTO.getTitle()+":Initiation Completed");
                         break;
                     case MEDIA_IN_PROGRESS:
                         System.out.println(videoDTO.getTitle()+":Upload in progress");
                         System.out.println(videoDTO.getTitle()+":Upload percentage: " + uploader.getNumBytesUploaded());
                         break;
                     case MEDIA_COMPLETE:
                         System.out.println(videoDTO.getTitle()+":Upload Completed!");
                         break;
                     case NOT_STARTED:
                         System.out.println(videoDTO.getTitle()+":Upload Not Started!");
                         break;
                 }
             }
         };
         
         uploader.setProgressListener(progressListener);

         // Call the API and upload the video.
         returnedVideo = videoInsert.execute();

         // Print data about the newly inserted video from the API response.
         System.out.println("\n================== Returned Video ==================\n");
         System.out.println("  - Id: " + returnedVideo.getId());
         System.out.println("  - Title: " + returnedVideo.getSnippet().getTitle());
         System.out.println("  - Tags: " + returnedVideo.getSnippet().getTags());
         System.out.println("  - Privacy Status: " + returnedVideo.getStatus().getPrivacyStatus());
         System.out.println("  - Video Count: " + returnedVideo.getStatistics().getViewCount());
         

         
		 
		 } catch (GoogleJsonResponseException e) {
	            e.printStackTrace();
	            System.err.println("GoogleJsonResponseException code: " + e.getDetails().getCode() + " : "
	                    + e.getDetails().getMessage());

	        } catch (IOException e) {
	            System.err.println("IOException: " + e.getMessage());
	            e.printStackTrace();
	        } catch (Throwable t) {
	            System.err.println("Throwable: " + t.getMessage());
	            t.printStackTrace();
	        }
		 
		 
		 
		    return returnedVideo; 
		 
		 
	 }
	 
	 
	 
	 
	 
	 
	 public void uploadVideoToTerrabisChannel(Credential credential, InputStream videostream) throws Exception {

		 
		 
		 try {

			 
	      // This object is used to make YouTube Data API requests.
	      youtube = new YouTube.Builder(GoogleAuthService.HTTP_TRANSPORT, GoogleAuthService.JSON_FACTORY, credential).setApplicationName("terrabis-upload-video").build();
		 
		 
		 // Add extra information to the video before uploading.
         Video videoObjectDefiningMetadata = new Video();

         // Set the video to be publicly visible. This is the default
         // setting. Other supporting settings are "unlisted" and "private."
         VideoStatus status = new VideoStatus();
         status.setPrivacyStatus("public");
         videoObjectDefiningMetadata.setStatus(status);

         // Most of the video's metadata is set on the VideoSnippet object.
         VideoSnippet snippet = new VideoSnippet();

         
         // This code uses a Calendar instance to create a unique name and
         // description for test purposes so that you can easily upload
         // multiple files. You should remove this code from your project
         // and use your own standard names instead.
         Calendar cal = Calendar.getInstance();
         snippet.setTitle("Test Upload via Java on " + cal.getTime());
         snippet.setDescription("Video uploaded via YouTube Data API V3 using the Java library " + "on " + cal.getTime());
		 
		 
		 
         // Set the keyword tags that you want to associate with the video.
         List<String> tags = new ArrayList<String>();
         tags.add("test");
         tags.add("example");
         tags.add("java");
         tags.add("YouTube Data API V3");
         tags.add("erase me");
         snippet.setTags(tags);

         // Add the completed snippet object to the video resource.
         videoObjectDefiningMetadata.setSnippet(snippet);

         //READ THE VIDEO FILE
         InputStreamContent mediaContent = new InputStreamContent(VIDEO_FILE_FORMAT,videostream);

         
      // Insert the video. The command sends three arguments. The first
         // specifies which information the API request is setting and which
         // information the API response should return. The second argument
         // is the video resource that contains metadata about the new video.
         // The third argument is the actual video content.
         YouTube.Videos.Insert videoInsert = youtube.videos()
                 .insert("snippet,statistics,status", videoObjectDefiningMetadata, mediaContent);

         // Set the upload type and add an event listener.
         MediaHttpUploader uploader = videoInsert.getMediaHttpUploader();

         // Indicate whether direct media upload is enabled. A value of
         // "True" indicates that direct media upload is enabled and that
         // the entire media content will be uploaded in a single request.
         // A value of "False," which is the default, indicates that the
         // request will use the resumable media upload protocol, which
         // supports the ability to resume an upload operation after a
         // network interruption or other transmission failure, saving
         // time and bandwidth in the event of network failures.
         uploader.setDirectUploadEnabled(true);
         
         MediaHttpUploaderProgressListener progressListener = new MediaHttpUploaderProgressListener() {
             public void progressChanged(MediaHttpUploader uploader) throws IOException {
                 switch (uploader.getUploadState()) {
                     case INITIATION_STARTED:
                         System.out.println("Initiation Started");
                         break;
                     case INITIATION_COMPLETE:
                         System.out.println("Initiation Completed");
                         break;
                     case MEDIA_IN_PROGRESS:
                         System.out.println("Upload in progress");
                         System.out.println("Upload percentage: " + uploader.getNumBytesUploaded());
                         break;
                     case MEDIA_COMPLETE:
                         System.out.println("Upload Completed!");
                         break;
                     case NOT_STARTED:
                         System.out.println("Upload Not Started!");
                         break;
                 }
             }
         };
         
         uploader.setProgressListener(progressListener);

         // Call the API and upload the video.
         Video returnedVideo = videoInsert.execute();

         // Print data about the newly inserted video from the API response.
         System.out.println("\n================== Returned Video ==================\n");
         System.out.println("  - Id: " + returnedVideo.getId());
         System.out.println("  - Title: " + returnedVideo.getSnippet().getTitle());
         System.out.println("  - Tags: " + returnedVideo.getSnippet().getTags());
         System.out.println("  - Privacy Status: " + returnedVideo.getStatus().getPrivacyStatus());
         System.out.println("  - Video Count: " + returnedVideo.getStatistics().getViewCount());

         
         
         
		 
		 } catch (GoogleJsonResponseException e) {
	            e.printStackTrace();
	            System.err.println("GoogleJsonResponseException code: " + e.getDetails().getCode() + " : "
	                    + e.getDetails().getMessage());

	        } catch (IOException e) {
	            System.err.println("IOException: " + e.getMessage());
	            e.printStackTrace();
	        } catch (Throwable t) {
	            System.err.println("Throwable: " + t.getMessage());
	            t.printStackTrace();
	        }
		 
		 
	 }
	 
	 
	 
	 
	 public YouTubeResultDTO getVideoTags(String videoid) throws Exception  {
		  
		  YouTubeResultDTO resultDTO = new YouTubeResultDTO();

		  Map<String, String> vars = new HashMap<String, String>();
		  vars.put("part", "snippet,contentDetails");
		  vars.put("key", GoogleConstants.API_KEY);
		  String jsonString = restTemplate.getForObject(GoogleConstants.YOUTUBE_URI+"/videos?id=" + videoid+"&part={part}&key={key}",String.class, vars);
	    	try {
		    	String tags = JsonPath.read(jsonString, "$.items[0].snippet.tags").toString();
		    	resultDTO.setTags(tags);
			} catch (Exception e) {
				resultDTO.setTags("");
			}

			 return resultDTO;
	  } 
	 
	 
	 
	  public YouTubeResultDTO getVideoChannel(String videoid) throws Exception  {
		  
		  YouTubeResultDTO resultDTO = new YouTubeResultDTO();

		  Map<String, String> vars = new HashMap<String, String>();
		  vars.put("part", "snippet,contentDetails");
		  vars.put("key", GoogleConstants.API_KEY);
		  String jsonString = restTemplate.getForObject(GoogleConstants.YOUTUBE_URI+"/videos?id=" + videoid+"&part={part}&key={key}",String.class, vars);

		  String channelID =  JsonPath.read(jsonString, "$.items[0].snippet.channelId").toString();
		  resultDTO.setChannelId(channelID);
	    
		 return resultDTO;
		 
	  }
	 
	 
	 
	 

	  public YouTubeResultDTO getVideoInformation(String videoid) throws Exception  {
		  
		  YouTubeResultDTO resultDTO = new YouTubeResultDTO();

		  Map<String, String> vars = new HashMap<String, String>();
		  vars.put("part", "snippet,contentDetails");
		  vars.put("key", GoogleConstants.API_KEY);
		  String jsonString = restTemplate.getForObject(GoogleConstants.YOUTUBE_URI+"/videos?id=" + videoid+"&part={part}&key={key}",String.class, vars);
	      //System.out.println("YOUTUBE INFO:"+jsonString);
		  
	   String totalResults = JsonPath.read(jsonString, "$.pageInfo.totalResults").toString();
	   if(Integer.parseInt(totalResults)>0) {
	      
		   		resultDTO.setEmptyStatus(false);
		   	
			  	String thumbnail = JsonPath.read(jsonString, "$.items[0].snippet.thumbnails.high.url").toString();
		    	thumbnail = thumbnail.replace("\\", "");
		    	thumbnail = thumbnail.replace("[\"", "");
		    	thumbnail = thumbnail.replace("\"]","");
		    	resultDTO.setThumbnailuri(thumbnail);
			    	
			    	
			    	String channelID =  JsonPath.read(jsonString, "$.items[0].snippet.channelId").toString();
			    	resultDTO.setChannelId(channelID);
			    	
			    	String channelTitle =  JsonPath.read(jsonString, "$.items[0].snippet.channelTitle").toString();
			    	resultDTO.setChannelTitle(channelTitle);
			    	
			    	
			    	String title =  JsonPath.read(jsonString, "$.items[0].snippet.title").toString();
			    	resultDTO.setTitle(title);
			    	
			    	
			    	String description =  JsonPath.read(jsonString, "$.items[0].snippet.description")!=null ? JsonPath.read(jsonString, "$.items[0].snippet.description").toString() : "" ;
			    	resultDTO.setDescription(description);
			    	
			    	
			    	String publishedDate =  JsonPath.read(jsonString, "$.items[0].snippet.publishedAt").toString();
			    	resultDTO.setPublishedDate(publishedDate.substring(0, publishedDate.indexOf("T")));
			    	
			    	
			    	
			    	
			    	try {
				    	String tags = JsonPath.read(jsonString, "$.items[0].snippet.tags").toString();
				    	resultDTO.setTags(tags);
					} catch (Exception e) {
						resultDTO.setTags("");
					}
		
			    	
			    	
			        String duration =  JsonPath.read(jsonString, "$.items[0].contentDetails.duration").toString();
			    	duration = YouTubeDurationUtils.convertYouTubeDuration(duration);
			    	resultDTO.setDuration(duration);
	   } else {
		   
		   resultDTO.setEmptyStatus(true);
		   
	   }
		  
	

		 return resultDTO;
		 
	  }


 	 
	  
}
