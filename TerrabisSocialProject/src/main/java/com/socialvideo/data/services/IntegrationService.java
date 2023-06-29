package com.socialvideo.data.services;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Base64;

import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.model.PutObjectResult;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.socialvideo.config.AWSConfiguration;
import com.socialvideo.constant.Constants;
import com.socialvideo.data.dto.PublicVideoDTO;
import com.socialvideo.data.dto.VideoDTO;
import com.socialvideo.data.dto.vimeo.VimeoResultDTO;
import com.socialvideo.data.dto.youtube.YouTubeResultDTO;
import com.socialvideo.data.model.VideoEntity;
import com.socialvideo.data.repositories.VideoRepository;
import com.socialvideo.data.services.inter.IVideoService;

@Service
public class IntegrationService {

	
	@Autowired
	private YouTubeService youtubeService;
	
	
	@Autowired
	private AWSService awsService;

	@Autowired
	private IVideoService videoService;
	
	
	@Autowired
	private VimeoHttpService vimeoService;
	
	@Autowired
	private VideoRepository videoRepository;
	

	public byte[] createGoogleMapStaticImg(String imageURL, String letters, String logo) throws Exception {
        BufferedImage  baseImage = ImageIO.read(new URL(imageURL));
        
        
        
    	//COMBINE
    	BufferedImage combinedImage = new BufferedImage(baseImage.getWidth(), baseImage.getHeight(), BufferedImage.TYPE_INT_ARGB);


    	Graphics g = combinedImage.getGraphics();
    	g.drawImage(baseImage, 0, 0, null);
    	
    	

    	g.setColor(new Color(255, 255, 255, 255));
        g.setFont(new Font("SansSerif", Font.BOLD, 16));
        FontMetrics fm = g.getFontMetrics();
        int lettersX = baseImage.getWidth()/2 - fm.stringWidth(letters)/2;
        int lettersY = baseImage.getHeight()-fm.getHeight()/2;
        g.drawString(letters,lettersX,lettersY);
        
    	g.setColor(new Color(255, 255, 255, 255));
        g.setFont(new Font("SansSerif", Font.BOLD, 16));
        g.drawString(logo, baseImage.getWidth()/2 - fm.stringWidth(logo), baseImage.getHeight()/2);

        
        g.dispose();

        
        
        
        
        
        ByteArrayOutputStream combinedImageBOS = new ByteArrayOutputStream();
    	ImageIO.write(combinedImage, "PNG", combinedImageBOS);
        
    	byte[] imageInByteArray = combinedImageBOS.toByteArray();
    	combinedImageBOS.close();
    	return imageInByteArray;
    	
    }
	
	
	
	
	public String createGoogleMapStaticImgBase64(String imageURL, String letters) throws Exception {
        BufferedImage  baseImage = ImageIO.read(new URL(imageURL));

    	//COMBINE
    	BufferedImage combinedImage = new BufferedImage(baseImage.getWidth(), baseImage.getHeight(), BufferedImage.TYPE_INT_ARGB);

    	Graphics g = combinedImage.getGraphics();
    	g.drawImage(baseImage, 0, 0, null);
    	g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.BOLD, 64));
        FontMetrics fm = g.getFontMetrics();
        int lettersX = baseImage.getWidth()/2 - fm.stringWidth(letters)/2;
        int lettersY = baseImage.getHeight()/2;
        g.drawString(letters,lettersX,lettersY);
        g.dispose();
     
        
        ByteArrayOutputStream combinedImageBOS = new ByteArrayOutputStream();
    	ImageIO.write(combinedImage, "PNG", combinedImageBOS);
        
    	byte[] imageInByteArray = combinedImageBOS.toByteArray();
    	combinedImageBOS.close();
    	String imageBase64String = new String(Base64.getEncoder().encodeToString(imageInByteArray));
    	
  
        return imageBase64String;
    	
    }
	
	
	
	
	public String createRobotCheckImgBase64(PublicVideoDTO videoDTO, String letters) throws Exception {
    	ClassLoader classLoader = getClass().getClassLoader();
        String imageURL = videoDTO.getThumburl();
        System.out.println("createRobotCheckImg:"+imageURL);
        BufferedImage  baseImage = ImageIO.read(new URL(imageURL));

    	//COMBINE
    	BufferedImage combinedImage = new BufferedImage(baseImage.getWidth(), baseImage.getHeight(), BufferedImage.TYPE_INT_ARGB);

    	Graphics g = combinedImage.getGraphics();
    	g.drawImage(baseImage, 0, 0, null);
    	g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.BOLD, 42));
        FontMetrics fm = g.getFontMetrics();
        int lettersX = baseImage.getWidth()/2 - fm.stringWidth(letters)/2;
        int lettersY = baseImage.getHeight()/2;
        g.drawString(letters,lettersX,lettersY);
        g.dispose();
     
        
        ByteArrayOutputStream combinedImageBOS = new ByteArrayOutputStream();
    	ImageIO.write(combinedImage, "PNG", combinedImageBOS);
        
    	byte[] imageInByteArray = combinedImageBOS.toByteArray();
    	combinedImageBOS.close();
    	String imageBase64String = new String(Base64.getEncoder().encodeToString(imageInByteArray));
    	
  
        return imageBase64String;
    	
    }
	
	
	
	
    
    public void createVideoThumbImg(VideoDTO videoDTO) throws Exception {
    	ClassLoader classLoader = getClass().getClassLoader();
        String imageURL = videoDTO.getThumburl();
        System.out.println("createVideoThumbImg:"+imageURL);
        BufferedImage  baseImage = ImageIO.read(new URL(imageURL));
    	BufferedImage  overlayImage = ImageIO.read(classLoader.getResource("play-overlay.png"));

    	//COMBINE
    	BufferedImage combinedImage = new BufferedImage(baseImage.getWidth(), baseImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
    	Graphics g = combinedImage.getGraphics();
    	g.drawImage(baseImage, 0, 0, null);
    	g.drawImage(overlayImage, baseImage.getWidth()/2 - overlayImage.getWidth()/2,baseImage.getHeight()/2 - overlayImage.getHeight()/2, null);
    	
    	//GET INPUT STREAM
        ByteArrayOutputStream combinedImageBOS = new ByteArrayOutputStream();
    	ImageIO.write(combinedImage, "PNG", combinedImageBOS);
    	InputStream combinedImageInpStream = new ByteArrayInputStream(combinedImageBOS.toByteArray());
    	
    	PutObjectResult resultS3 = awsService.addInpustreamtoBucket(AWSConfiguration.VIDEOTHUMBBUCKET, videoDTO.getUUID()+".png", "image/png",Long.parseLong(combinedImageBOS.size()+""), combinedImageInpStream);
   
    }
	

   
    
    public VideoDTO getVideoFromYoutubeID(String youtubeid) {
		VideoDTO videoDTO = null;
    	VideoEntity entity = null;
		

		entity = videoRepository.findByYoutubeId(youtubeid);
		
		if(entity!=null)
			videoDTO = entity.DTO();
		
		return videoDTO;	
		
	}
    
    
    
	
	public VideoDTO getVideoFromVimeoYoutubeURL(String linkedurl) {
		VideoDTO videoDTO = null;
		String videoid = null;
		Boolean isVimeo = false;
		Boolean isYoutube = false;
		VideoEntity entity = null;
		
		if(linkedurl.contains("youtube.com/watch")) {
			videoid =  linkedurl.substring(linkedurl.lastIndexOf("=")+1,linkedurl.length());
			isYoutube = true;
		}
	    	
		else if(linkedurl.contains("youtu.be")) {
			videoid =  linkedurl.substring(linkedurl.lastIndexOf("/")+1,linkedurl.length());
			isYoutube = true;
			
		}
		else if(linkedurl.contains("vimeo")) {
			videoid =  linkedurl.substring(linkedurl.lastIndexOf("/")+1,linkedurl.length());
			isVimeo = true;
		}
		
		
		if(isYoutube)
			entity = videoRepository.findByYoutubeId(videoid);
		else if(isVimeo)
			entity = videoRepository.findByVimeoId(videoid);
			
		
		
		if(entity!=null)
			videoDTO = entity.DTO();
		
		return videoDTO;	
		
	}
	
	
	
	
	public void postInitialTerrabisCommentToYoutube(Credential credential, VideoDTO videoDTO) {
		try {

	   		
   		 	//LOAD CREDENTIAL
			if(credential==null) {
				GoogleAuthorizationCodeFlow flow = GoogleAuthService.initYoutubeAuthorizationCodeFlow();
				credential = flow.loadCredential(GoogleAuthService.TERRABIS_AUTH_USER);
			}
			
			
			
    	    
    	    if(credential!=null) {
    	   			//REFRESH ACCESS TOKEN IN CASE OF EXPIRATION
	    	    	credential.refreshToken();
	    	    	String comment = new String(Constants.newUploadComment);
	    	    	comment = StringUtils.replace(comment,"$videoid$", videoDTO.getId()+"");
	    	    	
	    	   
	    	    	//POST A COMMENT
	    	   		youtubeService.postTopLevelComment(credential, videoDTO, comment);
	    	   		
	    	   		//UPDATE FLAG
	    	   		videoService.updateVideoYoutubeCommentSend(videoDTO.getId(), true, true);
	    	   		
	        }
   	 		} catch (Exception e) { e.printStackTrace(); }		
		
	}
	
	
	
	
	
	
	public Exception integrateDataFromVimeoYoutube(Long recordid,Boolean includeExtraData) {
		
		VideoEntity video = videoRepository.findOne(recordid);
		Exception exception = null;
		
		String linkedurl = video.getLinkedurl();
	    String videourl = "";
	    String videoid = "";
	    String thumburl = "";
	    String duration = "";
	    String ytvmuserid = "";
	    String ytvmusername  = "";
	    String title = "";
	    String description = "";
	    String publishedDate = "";
	    

	    	
	    	
    	if(video.getLinkedurl().contains("youtube.com/watch")) {
    		videoid =  linkedurl.substring(linkedurl.lastIndexOf("=")+1,linkedurl.length());
    		videourl = "https://www.youtube.com/embed/"+videoid;
    		//System.out.println("MODIFYING YOUTUBE VIDEO:"+videoid);
    		
    		try {
    			YouTubeResultDTO result = youtubeService.getVideoInformation(videoid);
    			thumburl = result.getThumbnailuri();
    			duration = result.getDuration();
    			ytvmuserid =  result.getChannelId();
    			ytvmusername =  result.getChannelTitle();
    			title = result.getTitle();
    			description = result.getDescription();
    			publishedDate = result.getPublishedDate();
    			
    			
    			if (result.getTags()!=null && result.getTags().contains("terrabisownershipclaim")) {
    				video.setOwnershipverified(true);
    				video.setUploaderid(video.getUserid());
    				
    			}
    			
    		} catch (Exception e) {exception = e; e.printStackTrace();}
    		
    		video.setYoutubeid(videoid);
    		video.setYoutube(true);
    		video.setVimeo(false);
    		video.setYtvmuserid(ytvmuserid);
    		video.setYtvmusername(ytvmusername);
    		
    		
    		
    	}
    	
    	else if(video.getLinkedurl().contains("youtube.com/embed")) {
    		videoid =  linkedurl.substring(linkedurl.lastIndexOf("/")+1,linkedurl.length());
    		videourl = "https://www.youtube.com/embed/"+videoid;
    		System.out.println("MODIFYING YOUTUBE VIDEO:"+videoid);
    		
    		try {
    			YouTubeResultDTO result = youtubeService.getVideoInformation(videoid);
    			thumburl = result.getThumbnailuri();
    			duration = result.getDuration();
    			ytvmuserid =  result.getChannelId();
    			ytvmusername =  result.getChannelTitle();
    			title = result.getTitle();
    			description = result.getDescription();
    			publishedDate = result.getPublishedDate();
    			
    			
    			if (result.getTags()!=null && result.getTags().contains("terrabisownershipclaim")) {
    				video.setOwnershipverified(true);
    				video.setUploaderid(video.getUserid());
    			}
    			
    		} catch (Exception e) {exception = e; e.printStackTrace();}
    		
    		video.setYoutubeid(videoid);
    		video.setYoutube(true);
    		video.setVimeo(false);
    		video.setYtvmuserid(ytvmuserid);
    		video.setYtvmusername(ytvmusername);
    		
    		
    		
    	}
    	
	    	
    	else if(video.getLinkedurl().contains("youtu.be")) {
    		videoid =  linkedurl.substring(linkedurl.lastIndexOf("/")+1,linkedurl.length());
    		videourl = "https://www.youtube.com/embed/"+videoid;
    		System.out.println("MODIFYING YOUTUBE VIDEO:"+videoid);

    		try {
    			YouTubeResultDTO result = youtubeService.getVideoInformation(videoid);
    			thumburl = result.getThumbnailuri();
    			duration = result.getDuration();
    			ytvmuserid =  result.getChannelId();
    			ytvmusername =  result.getChannelTitle();
    			title = result.getTitle();
    			description = result.getDescription();
    			publishedDate = result.getPublishedDate();
    			
   
    			if (result.getTags()!=null && result.getTags().contains("terrabisownershipclaim")) {
    				video.setOwnershipverified(true);
    				video.setUploaderid(video.getUserid());
    			}
    			
    		} catch (Exception e) {exception = e; e.printStackTrace();}
    		
    		video.setYoutubeid(videoid);
    		video.setYoutube(true);
    		video.setVimeo(false);
    		video.setYtvmuserid(ytvmuserid);
    		video.setYtvmusername(ytvmusername);
    		
    		

    	}
	    	
    	
    	
    	else if(video.getLinkedurl().contains("vimeo")) {
    		videoid =  linkedurl.substring(linkedurl.lastIndexOf("/")+1,linkedurl.length());
    		System.out.println("MODIFYING VIMEO VIDEO:"+videoid);

    		
    		videourl = "https://player.vimeo.com/video/"+videoid;
    		video.setVimeoid(videoid);
    		video.setVimeo(true);
    		video.setYoutube(false);
    		
    		try {
    			VimeoResultDTO result = vimeoService.getVideoInformation(videoid);
    			thumburl = result.getThumbnailuri();
    			duration = result.getDuration();
    			title = result.getTitle();
    			description = result.getDescription();
    			publishedDate = result.getPublishedDate();
    			
    		} catch (Exception e) {exception = e; e.printStackTrace();}
    	
    	}
	    	
	    	
	    video.setVideourl(videourl);
	    video.setThumburl(thumburl);
	    video.setDurationtxt(duration);
	    
	    video.setCollectionscount(0);
	    video.setLikescount(0);
	    video.setPlayscount(0);
	    video.setRatingscount(0);
	    //video.setRatingssum((float) 0);
	    video.setReviewscount(0);
	    video.setTweetscount(0);
	
  
	    if(includeExtraData) {
	    	video.setTitle(title!=null ? title : "");
	    	video.setDescription(description!=null ? description : "");	  
	    }
	    
	    	
	    
	    try {
	    videoRepository.save(video); 
	    } catch (Exception e) { video.setDescription("");  videoRepository.save(video); }

		
	    return exception;
	}
	
	
	
	
}
