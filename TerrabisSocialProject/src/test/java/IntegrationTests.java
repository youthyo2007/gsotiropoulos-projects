import java.io.BufferedReader;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.socialvideo.SocialVideoApplication;
import com.socialvideo.constant.*;
import com.socialvideo.data.dto.*;
import com.socialvideo.data.dto.maps.BoundsDTO;
import com.socialvideo.data.dto.maps.PointDTO;
import com.socialvideo.data.dto.statistics.BIChartCountsPerTimeDTO;
import com.socialvideo.data.dto.statistics.BIQueryDTO;
import com.socialvideo.data.dto.vimeo.VimeoResultDTO;
import com.socialvideo.data.mappers.StatisticsMapper;
import com.socialvideo.data.model.CollectionEntity;
import com.socialvideo.data.model.NotificationSettingsEntity;
import com.socialvideo.data.model.UserEntity;
import com.socialvideo.data.repositories.CollectionRepository;
import com.socialvideo.data.repositories.RoleRepository;
import com.socialvideo.data.repositories.UserRepository;
import com.socialvideo.data.services.*;
import com.socialvideo.data.services.inter.IUserService;
import com.socialvideo.data.services.inter.IVideoService;
import com.socialvideo.data.services.mobile.MobileVideoService;
import com.socialvideo.data.services.statistics.ChartUsersService;
import com.socialvideo.data.services.statistics.ChartVideoService;
import com.socialvideo.data.services.statistics.StatisticsSevice;
import com.socialvideo.data.services.tasks.TaskVideoService;
import com.socialvideo.utilities.TimeUtility;

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(loader = SpringApplicationContextLoader.class)
@SpringApplicationConfiguration(classes = SocialVideoApplication.class)
public class IntegrationTests {

	
	
	
	 @Autowired
	 protected IVideoService privateVideoService;
	 
	 @Autowired
	 TaskVideoService taskService;
	 
	
	 @Autowired
	 protected AvatarService avatarService;

	 @Autowired
	 protected GamificationService gameService;
	 
	 
	    @Autowired
	    private UserRepository userRepository;

	    @Autowired
	    private RoleRepository roleRepository;

	    @Autowired
	    private CollectionRepository colRepository;

	
	 @Autowired
	 protected IntegrationService integrationService;
	 
	 
	 @Autowired
	 protected FusionTablesService fusionService;
	 
	
    @Autowired
    VimeoHttpService vimeoService;

    @Autowired
    DatabaseService dbService;
    
    @Autowired
    YouTubeService youtubeService;
    
	 @Autowired
	 protected AWSService awsService;
    
    @Autowired
    VideoService videoService ;

    @Autowired
    PublicVideoService publicVideoService ;
    
    
    
    @Autowired
    IntegrationService integrationServie;
    
    
    @Autowired
    ChartVideoService chartVideoService ;

    
    
    @Autowired
    ChartUsersService chartUserService ;
    
	@Autowired
	StatisticsMapper statsMapper;

	@Autowired
	StatisticsSevice statsService;
	
	
    @Autowired
    EmailService emailService;


    @Autowired
    IUserService userService;
    
    
    @Autowired
    VideoClaimService claimService;
    
    @Autowired
    MobileVideoService mobileService;  
    

	 @Autowired
	 protected NotificationsService nottsService;
   


	 
	    
	    
	    
	    
	   @Test
	    public void empty() throws Exception {
	    	//System.out.println(TimeUtility.incrementMinutes(300).toString());
	    	
	    }
	    

	 
	    
	    
	   //@Test
	    public void batchUpdateVideoDurationFast() throws Exception {
	    	
	    	
	    	for (int i=2948067; i<=2958925; i++) {
	    		
	    		Long videoid = Long.parseLong(i+"");
	    		try {
		    		VideoDTO videoDTO = videoService.findVideoById(videoid);
		    		if((videoDTO!=null) && (videoDTO.getDuration()==null) && (videoDTO.getDurationtxt()!=null)) {
		    		Integer duration =  TimeUtility.convertDurationStringToSeconds(videoDTO.getDurationtxt());
		    		videoService.updateDuration(videoid, duration);
		    		System.out.println("updating video duration:"+videoid+", "+(2958925-i)+" videos left");
		    		
		    		}
	    		} catch (Exception e) {
	    			
	    			e.printStackTrace();

	    		}
	    		
	    		
	    		
	    	}

	    }
	 
	 
    
    //@Test
    public void generateStatistics() throws Exception {
    	 BIQueryDTO queryDTO = new BIQueryDTO();
    	
    	
    	//CALCULATE STATISTICS ONE DAY BEFORE THE LAST CALCULATED ENTRY DATE
    	//Calendar calendar = Calendar.getInstance();
    	//calendar.setTime(statsService.selectStatisticsDayActivityLastDate());
        //queryDTO.setDateCreatedFrom(calendar.getTime());
    	
    	List<BIChartCountsPerTimeDTO> resultList = chartVideoService.videosPerDay(queryDTO);
    	for (BIChartCountsPerTimeDTO countDTO : resultList) {
    		statsService.updateDayActivity(Long.parseLong(countDTO.getDateNumber()+""), countDTO.getCount(), countDTO.getDateCreated(),countDTO.getCounttype());
    	}
    	

    	resultList = chartVideoService.likesPerDay(queryDTO);
    	for (BIChartCountsPerTimeDTO countDTO : resultList) {
    		statsService.updateDayActivity(Long.parseLong(countDTO.getDateNumber()+""), countDTO.getCount(), countDTO.getDateCreated(),countDTO.getCounttype());
    	}
    	
    	resultList = chartVideoService.playsPerDay(queryDTO);
    	for (BIChartCountsPerTimeDTO countDTO : resultList) {
    		statsService.updateDayActivity(Long.parseLong(countDTO.getDateNumber()+""), countDTO.getCount(), countDTO.getDateCreated(),countDTO.getCounttype());
    	}
    	
    	resultList = chartVideoService.ratingsPerDay(queryDTO);
    	for (BIChartCountsPerTimeDTO countDTO : resultList) {
    		statsService.updateDayActivity(Long.parseLong(countDTO.getDateNumber()+""), countDTO.getCount(), countDTO.getDateCreated(),countDTO.getCounttype());
    	}


    	resultList = chartVideoService.sharesPerDay(queryDTO);
    	for (BIChartCountsPerTimeDTO countDTO : resultList) {
    		statsService.updateDayActivity(Long.parseLong(countDTO.getDateNumber()+""), countDTO.getCount(), countDTO.getDateCreated(),countDTO.getCounttype());
    	}


    	resultList = chartUserService.registrationsPerDay(queryDTO);
    	for (BIChartCountsPerTimeDTO countDTO : resultList) {
    		statsService.updateDayActivity(Long.parseLong(countDTO.getDateNumber()+""), countDTO.getCount(), countDTO.getDateCreated(),countDTO.getCounttype());
    	}


    }
    
    
    

    
    //@Test
    public void createInitialNotificationSettingsForAllUsers() throws Exception {
    	for (int i=2947070; i<=2947270; i++) {
    		NotificationSettingsEntity notSettings = nottsService.findNotifSettingsEntityByUserID(Long.parseLong(i+""));
    		if(notSettings==null) {
    			nottsService.createInitialNotificationSettings(Long.parseLong(i+""),NotificationSettingStatus.EMAIL);
    		}
   	 	}
    	
    }
    
    
    
    
    //@Test
    //USER IDS 2947053 - 2947180
    public void assignRoleAprover() throws Exception {
   	 	for (int i=2947053; i<=2947180; i++) {
   	    	System.out.println(i);
   	 		userService.assignRoleToUser(new Long(i), new Long(2946949));
   		 
   	 	}
    	
    }
     
    

     
    
    //@Test
    public void testRelatedvideoIdsList() throws Exception {
    	String relatedVideosIDS =  "2946948 , 2954445 , 2949179 , 2954437 , 2948189 , 2954440 , 2949180 , 2949168 , 2949181 , 2946950";
    	
     	List<PublicVideoPointDTO> relatedPointsList = publicVideoService.findVideoPointsOfVideoList(Arrays.asList(relatedVideosIDS.split(",")));
    	System.out.println(relatedPointsList.size());
    	
    	
    }
    
    
    
    //@Test
    public void uploadFile() throws Exception {
    	taskService.videoUploadFromS3toYoutubeTerrabis();
    	
    }
    
    
    
    
    
    
    //@Test
    public void batchUpdateVideoDuration() throws Exception {
    	
    	
    	List<VideoDTO> videoList = videoService.selectAdminCatalogVideos(new AdminQueryDTO(),0,4000);
    	
    	int counter = 1;
    	for(VideoDTO videoDTO : videoList) {
    		counter++;

    		try {
    		Long videoid = 	videoDTO.getId();
    		Integer duration =  TimeUtility.convertDurationStringToSeconds(videoDTO.getDurationtxt());
    		System.out.println(counter + " UPDATING VIDEO ROW:"+videoDTO.getId());	
    		videoService.updateDuration(videoid, duration);
    		
    		} catch (Exception e) {
    			
    			e.printStackTrace();

    		}
    		}
    		
    	
    		    	
    
    	
    }
    
    

    
    
	 //@Test 
	 public void batchUpdatePassword() throws Exception {
	    	
		 for (int i=2947053; i<=2947180; i++) {
			 userService.changeUserPassword(new Long(i+""), "123");
			 
		 }
			 
	
	    
	    
	 }
	 
	 
	  private  CollectionEntity createUserCollectionIfNotFound(String id, String title, String description, Integer type, Integer privacy) {
	    	CollectionEntity entity = null;
	    	UserEntity userEntity = userRepository.findOne(new Long(id));
	    	if(userEntity!=null) {
	    	
	    		entity = colRepository.findByTypeAndUserId(userEntity.getId(), type);
		        if (entity == null) {
		        	entity = new CollectionEntity(userEntity.getId(),title,description,type,privacy);
		        	colRepository.save(entity);
		        }
	    	}
	        return entity;
	    }
	  
	 
	//@Test 
	 public void batchCreateCollections() throws Exception {
	    	
		 for (int i=2947053; i<=2947180; i++) {
			 	 System.out.println(i);
			     createUserCollectionIfNotFound(i+"","WATCH LATER","A collection of videos to watch in the nearest future",CollectionTypes.WATCHLATER,VideoPrivacyStatus.PUBLIC);
		         createUserCollectionIfNotFound(i+"","FAVORITES BUCKET","A collection of videos to add to playlists in the nearest future",CollectionTypes.FAVORITESBUCKET,VideoPrivacyStatus.PUBLIC);
			 
		 }
			 
	
	    
	    
	 }
	 
	 
	 

    
    
    
    public  BufferedReader readFile(String fileName) throws Exception  {
		 Path file = Paths.get(this.getClass().getResource(fileName).toURI());
	     BufferedReader reader = Files.newBufferedReader(file, Charset.defaultCharset());
	
	     return reader; 
	
	}
    
    
    
    
    

    
    
    
    
    
    private Date getRandomDateBetweenTwoDates () {
    	long beginTime = Timestamp.valueOf("2015-09-01 00:00:00").getTime();
    	long endTime = Timestamp.valueOf("2016-08-15 00:00:00").getTime();
        long diff = endTime - beginTime + 1;
        return  new Date(beginTime + (long) (Math.random() * diff));
    }
    
    
    
    
	 //@Test  TEST USER IDS 2947053 - 2947180
	 public void loadTestUsers() throws Exception {
	    	
		 BufferedReader reader = readFile("namesterra.csv");
		 String line = null;
		 while ((line = reader.readLine()) != null) {
	     	String[] tokens = line.split(",");
	     	UserDTO userDTO = new UserDTO(tokens[0],tokens[1],tokens[2], "-", tokens[2]+"@gmail.com", true,true,1);
	     	userDTO.setUUID(UUID.randomUUID().toString().replace("-", ""));
	     	userDTO.setTstFlag(true);
	     	userService.saveBasicUserAccount(userDTO);
		 }
	    
	    
	 }
	 
 
 
    
    
    
    
    //@Test
    public void IntegerateVideoFromYoutubeOrVimeo() throws Exception {
    	
    		//YouTubeResultDTO result = youtubeService.getVideoInformation("DIyQFPb1GVk");
    		//System.out.println(result.getPublishedDate());
    		
    		
    		
    		VimeoResultDTO vimeoResult = vimeoService.getVideoInformation("176283349");
    		System.out.println(vimeoResult.getPublishedDate());
    		System.out.println(vimeoResult.getTitle());
    		System.out.println(vimeoResult.getDescription());
    		
    	
    	
    }
    
    
    
    
    
    
    
    //@Test
    public void processExcelVideos() throws Exception {
    	
    	BufferedReader reader = readFile("videostock-final.txt");
		 String line = null;
		 
		 	int counter= 0;
	        while ((line = reader.readLine()) != null) {
	        	counter+=1;
	        	
	        	if(counter>12766) {
	        	boolean youtube = false;
	        	boolean vimeo = false;
	        	String videoid = null;
	        	
	        	
	        	String[] tokens = line.split(",");
	        	String latitude = tokens[1];
	        	String longitude = tokens[2];
	        	String tempvideostring = tokens[3];
	        	
	        	System.out.println("line:"+counter);
	        		
	        	try {
	        	if(tempvideostring.contains("youtube")) {
	        		youtube = true;
	        		//videoid = tempvideostring.substring(tempvideostring.lastIndexOf("/")+1,tempvideostring.lastIndexOf("?"));
	        		videoid = tempvideostring.substring(tempvideostring.lastIndexOf("/")+1,tempvideostring.lastIndexOf("/")+12);
	        		//System.out.println(videoid);
	        		
	        	} else if (tempvideostring.contains("vimeo")) {
	        		vimeo = true;
	        		videoid = tempvideostring.substring(tempvideostring.lastIndexOf("/")+1,tempvideostring.lastIndexOf("?"));
	        	}
	        	
	        	} catch (Exception e) {e.printStackTrace();}
	        	
	        	
	        	
	        	//videoid=null;
	        	
	        	if(videoid!=null) {
	        	
	        	
	        	//CHECK IF VIDEO ALREADY EXISTS
	        	boolean videoExists = true;
	        	
	        	try {
	        	if(vimeo)
	        		videoExists = privateVideoService.findByVimeoId(videoid)!=null ? true : false;
	        	else if (youtube)
	        		
	        	videoExists = privateVideoService.findByYoutubeId(videoid)!=null ? true : false;
	        	} catch (Exception e) {e.printStackTrace();}
	        	
	        	
	        	
	        	if(!videoExists) {
				        	//Double newlatitude = Double.parseDouble(latitude)+Double.parseDouble("0.0000120");
				        	
					        try {	
				        	Long latitudeoffset = Long.parseLong(latitude.substring(latitude.lastIndexOf(".")+3,latitude.length()))+Integer.parseInt(RandomStringUtils.randomNumeric(3));
				        	Long longitudeoffset = Long.parseLong(longitude.substring(longitude.lastIndexOf(".")+3,longitude.length()))+Integer.parseInt(RandomStringUtils.randomNumeric(3));
				        	
				        	//longitude = longitude.substring(0,longitude.lastIndexOf(".")+5)+RandomStringUtils.randomNumeric(3).toUpperCase();
				        	latitude = latitude.substring(0,latitude.lastIndexOf(".")+3)+latitudeoffset.toString();
				        	longitude = longitude.substring(0,longitude.lastIndexOf(".")+3)+longitudeoffset.toString();
				        	
					        } catch (Exception e) {e.printStackTrace();}
				        	
				        	
				        	
				        	VideoDTO videoDTO = new VideoDTO();
				        	
				        	Random randomObj = new Random();
				        	Long userID = Long.parseLong((randomObj.nextInt(127)+2947053)+"");
				    	 	videoDTO.setUserid(userID);
				            videoDTO.setFootagedate(new Date());
					        videoDTO.setShootingtypeid("1");
					        
					        try {
					        videoDTO.setLatitude(new BigDecimal(latitude));
					        videoDTO.setLongitude(new BigDecimal(longitude));
					        } catch (Exception e) {e.printStackTrace();}
					        
				    	    videoDTO.setUUID(UUID.randomUUID().toString().replace("-", ""));
				    	    videoDTO.setLink(true);
				    	    videoDTO.setIsfile(false);
				    	    videoDTO.setTstFlag(true);
					        videoDTO.setTitle("");
					        videoDTO.setDescription("");
					        
				    	    
				    	    if(youtube)
				    	    	videoDTO.setLinkedurl("https://www.youtube.com/watch?v="+videoid);
				    	    else
				    	    	videoDTO.setLinkedurl("https://www.vimeo.com/"+videoid);
				    	    
				    	    //VIDEO TYPE IDS
					       	String selectedVideoTypesCommaString = "2";	    	    
				    	    
				    	    //SET STATUSES
				    	    videoDTO.setTransferstatus(VideoTransferStatus.NONE);
				        	videoDTO.setIntegrationstatus(VideoIntegrationStatus.NONE);
				    	    videoDTO.setHealthstatus(VideoHealthStatus.HEALTHY);
				    	    videoDTO.setLoadstatus(VideoLoadStatus.EXCEL);
				    	    videoDTO.setStatus(VideoPublishStatus.EXCELUPLOADED);
				    		if(selectedVideoTypesCommaString.trim().length()>0) {
				    		    	 String[] items = selectedVideoTypesCommaString.split(",");
				    		    	 videoDTO.setVideotypeids(selectedVideoTypesCommaString);
				    		    	 videoDTO.setMarkerimgid(items[0]);
				    		}
				    	    
				    		
				    		//CREATING VIDEO
				    		System.out.println("line:"+counter+" - creating video:"+videoid);
				    		
				    	    //CREATE NEW VIDEO
				    	    videoDTO = privateVideoService.createNewVideo(videoDTO,getRandomDateBetweenTwoDates());
			
				    		   
				    	    /***************UPDATE VIDEO TYPES*****************************************/
				    	    if(selectedVideoTypesCommaString.trim().length()>0) {
				    	    	 String[] items = selectedVideoTypesCommaString.split(",");
				    	         List<String> videoTypesIDList = Arrays.asList(items);
			
				    		    for (String videotypeid : videoTypesIDList) {
				    		    	try {
				    		    		privateVideoService.insertVideoTypesListOfVideo(videoDTO.getId(), Long.parseLong(videotypeid));
				    		    	} catch (Exception e) {e.printStackTrace();}
				    		    }
				    	    }	 
				    	    /***************UPDATE VIDEO TYPES*****************************************/
				    	    
				    	    
				    	    
				    	    //INTEGRATE WITH VIMEO AND YOUTUBE TO LOAD DATA
				    	    integrationService.integrateDataFromVimeoYoutube(videoDTO.getId(),true);	    
				    	    
				    	    
				    	    //CREATE THUMBNAIL
				    	    try {
				    	    
				    	   //REFRESH OBJECT WITH THE LATEST CHANGES FROM INTEGRATION DATA 	
				    	    	
				    	    videoDTO = privateVideoService.findVideoById(videoDTO.getId());
				    		if(videoDTO.getThumburl()!=null) {
				    	    integrationService.createVideoThumbImg(videoDTO);
				    		}
				    	    
				    	    } catch (Exception e) {e.printStackTrace();}

				    	    
				    	    //UPDATE DURATION
				    		try {
				    			if(videoDTO.getDurationtxt()!=null) {
				        		Integer duration =  TimeUtility.convertDurationStringToSeconds(videoDTO.getDurationtxt());
				        		privateVideoService.updateDuration(videoDTO.getId(), duration);
				    			}
				        		
				    	    } catch (Exception e) {e.printStackTrace();}
				    	    
	        	}
	        	
	        	
    	
	        }
	        	
	        }
	        }
    	
    }
    
    
    
    
    
    
    
    
    
  //@Test
  public void fusionTablesSelectBoundsData() throws Exception {
    
		    	
	  FusionTableResultDTO result = fusionService.findPointsOfViewPort(new BoundsDTO(new PointDTO("53.481508", "39.199219"), new PointDTO("37.081476", "-13.535156")), new PointDTO("37.081476", "-13.535156"),new AdvancedFiltersDTO());
	  System.out.println(result.getTotal());
	  
    	
    	
    }
    
    
    
    
   //@Test
/*    public void fusionTablesInsertRow() throws Exception {
	    //GoogleAuthorizationCodeFlow flow = GoogleAuthService.initFusionTablesAdminFlow();
	    Credential credential = null;//flow.loadCredential(GoogleAuthService.TERRABIS_FUSION_AUTH_ADMIN_USER);
	    
	    if(credential==null)
	    	credential.refreshToken();
	    
	    
	    System.out.println(credential.getAccessToken());
    	
    	
	    Fusiontables fusiontables =GoogleAuthService.initFusionTableAPI(credential);
	    FusionTableVideoDTO fusionTableVideoDTO = videoService.findFusionVideoById(Long.parseLong(2948893+""));

	    
	    
	    String sqlStatement = "INSERT INTO "+GoogleConstants.TERRABIS_PRIVATE_FUSION_TABLE_MASTER
	    +"(id,UUID,title,description,latitude,longitude,markerimgid,shootingtypeid,status,healthstatus) VALUES " 
	    +"("+fusionTableVideoDTO.getId()+","+fusionTableVideoDTO.getUUID()+"," 
	    +fusionTableVideoDTO.getUUID()+","
	    +fusionTableVideoDTO.getTitle()+","	    		
	    +fusionTableVideoDTO.getDescription()+","	    		
	    +fusionTableVideoDTO.getLatitude()+","	    		
	    +fusionTableVideoDTO.getLongitude()+","	    		
	    +fusionTableVideoDTO.getMarkerimgid()+","	    		
	    +fusionTableVideoDTO.getShootingtypeid()+","	    		
	    +fusionTableVideoDTO.getStatus()+","	    		
	    +fusionTableVideoDTO.getHealthstatus()+")";	    		
	    		
	    Sql fusionTablesSql = fusiontables.query().sql(sqlStatement);
	    
	    try {
	    	fusionTablesSql.execute();
	      } catch (IllegalArgumentException e) {
	    	  e.printStackTrace();
	        // For google-api-services-fusiontables-v1-rev1-1.7.2-beta this exception will always
	        // been thrown.
	        // Please see issue 545: JSON response could not be deserialized to Sqlresponse.class
	        // http://code.google.com/p/google-api-java-client/issues/detail?id=545
	      }
	    		
	 
	    
	    
	    Sql sql = fusiontables.query().sql("INSERT INTO " + tableId + " (Text,Number,Location,Date) "
	            + "VALUES (" + "'Google Inc', " + "1, " + "'1600 Amphitheatre Parkway Mountain View, "
	            + "CA 94043, USA','" + new DateTime(new Date()) + "')");
    	 
    	 
    	 
    	//GoogleConstants.TERRABISPOINTS_TABLEID;
	    
	    
    }*/
    
    
    
    
    
    
    
    
/*
    
    
    
    //@Test
    public void googlefusionAuth() throws Exception {
	    GoogleAuthorizationCodeFlow flow = GoogleAuthService.initFusionTablesFlow();
	    Credential credential = flow.loadCredential(GoogleAuthService.TERRABIS_AUTH_USER);
	    
	    if(credential==null)
	    	credential.refreshToken();
	    
    	System.out.println(credential.getAccessToken());
	    
	    
    }
    
    
    //@Test
    public void testMobileSQLServices() throws Exception {
    	MobileQueryDTO queryDTO = new MobileQueryDTO(new PointDTO("37.08852590", "25.15495544"),5,0,10);
    	List<MobileVideoDTO> mobileList =  mobileService.selectNearyByVideos(queryDTO);
    	System.out.println(mobileList.size());
    	System.out.println( mobileService.selectCountNearyByVideos(queryDTO));
    	
    	
    	queryDTO = new MobileQueryDTO(null,QueryConstants.QUERYTYPE_MAP,null,0,10);
    	mobileList =  mobileService.selectVideos(queryDTO);
    	System.out.println(mobileList.size());
    	
    	
    	
    }
    
    

    //@Test
    public void testJWToken() throws Exception {

    		try {
    			
    			MobileQueryDTO queryDTO = new MobileQueryDTO(new PointDTO("37.08852590", "25.15495544"),5, 0,10);
    	    	ObjectMapper mapper = new ObjectMapper();
    			String jsonInString = mapper.writeValueAsString(queryDTO);
    			
    	    	
    			//CREATE TOKEN
    	    	//JwtBuilder builder = Jwts.builder();
    	    	builder.setIssuer(MobileConstants.androidIssuerID);
    	    	builder.setExpiration(TimeUtility.incrementMinutes(60*24*365));
    	    	builder.claim("jis", jsonInString);
    	    	//String tokenString = builder.signWith(SignatureAlgorithm.HS256, MobileConstants.JwsSignedKey).compact();
    	    	System.out.println(tokenString);
    	    	
    			
    	    	
    	    	
    	    	//UNMARSHALL TOKEN
    	    	 Jws<Claims> claims = Jwts.parser().setSigningKey(MobileConstants.JwsSignedKey).parseClaimsJws(tokenString);
    	    	 
    	    	 //validate issuer
    	    	 String issuer = claims.getBody().getIssuer();
    	    	 if(!(issuer.equals(MobileConstants.androidIssuerID) || (issuer.equals(MobileConstants.iosIssuerID))))
    	    			 throw new Exception("incorrect issuer");
    	    	 
    	    	 if(!(claims.getBody().containsKey("jis")))
	    			 throw new Exception("jis missing");
	    	
    	    	 
    	    	 String jisString = claims.getBody().get("jis").toString();
    	    	 MobileQueryDTO parsedQueryDTO = mapper.readValue(jisString, MobileQueryDTO.class);
    	    	 //System.out.println(parsedQueryDTO.getLocation().getLatnumber());
    	    	
    			
    		} catch (Exception e) {
    			e.printStackTrace();

    	}
    }
    
    
    
    
    
    
    //@Test
    public void tetsJsonMarshallUnmarshall() throws Exception {
    	
    	MobileQueryDTO queryDTO = new MobileQueryDTO();
    	MobileVideoDTO videoDTO = mobileService.selectVideoByUUID("510b69bded5f43f488559b0aac91294b");
    	MobileUserDTO userDTO =  mobileService.selectUserByUUID("505b228b79b4471b98e0dc06facc35d2");
    	
    	   GameLevelDTO gameLevelDTO = gameService.findGameLevel(userDTO);
	       userDTO.setGameLevel(gameLevelDTO);
	       AvatarDTO avatarDTO = avatarService.findActive(Long.parseLong(userDTO.getId()+""));
	       userDTO.setAvatar(avatarDTO);
    	
    	
    	ObjectMapper mapper = new ObjectMapper();
    	
    
    	
    		try {
    			
    			//String jsonInStringvideoDTO = mapper.writeValueAsString(videoDTO);
    			//System.out.println(jsonInStringvideoDTO);

    			
    			String jsonInStringuserDTO = mapper.writeValueAsString(userDTO);
    			System.out.println(jsonInStringuserDTO);
    			
    			
    			
    			
    		} catch (Exception e) {
    			e.printStackTrace();

    	}
    }
    
    
    
    
    //@Test
    public void batchCreateVideoThumbImgAndIntegrate() throws Exception {
    	
    	AdminQueryDTO query = new AdminQueryDTO();
    	query.getAdvfilter().setUsername("rpierce1956");
    	List<VideoDTO> videoList = videoService.selectAdminCatalogVideos(query,0,2000);
    	System.out.println(videoList.size());	
    	
    	int counter = 1;
    	for(VideoDTO videoDTO : videoList) {
    		try {
    		//System.out.println("INTEGRATE VIDEO DETAILS FOR:"+videoDTO.getId()+"/"+counter+"/"+videoList.size());		
    		//integrationServie.integrateDataFromVimeoYoutube(videoDTO.getId());	
    		System.out.println("CREATING IMG THUMB FOR:"+videoDTO.getId()+"/"+counter+"/"+videoList.size());	
    		counter = counter+1;	
    		integrationServie.createVideoThumbImg(videoDTO);
    		} catch (Exception e) {
    			System.out.println("ERROR FOR:"+videoDTO.getId()+"/"+counter+"/"+videoList.size()); e.printStackTrace();}
    		
    	
    	}
    }
    	
    

    //@Test
    public void testDateFromTo() throws Exception {
    	BIQueryDTO queryDTO = new BIQueryDTO("2016-05-30","2016-06-01");
    	Integer count = videoService.selectCountPlayedVideos(queryDTO);
    	System.out.println(count);
    	
    	
    }
    
    
    
    
    //@Test
    public void drawVideoPlayImage() throws Exception {
     	ClassLoader classLoader = getClass().getClassLoader();
        String imageURL = "https://i.ytimg.com/vi/8GzqYZLGImQ/hqdefault.jpg";
    	 
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
    	
    	//PutObjectResult resultS3 = awsService.addInpustreamtoBucket(AWSConfiguration.VIDEOTHUMBBUCKET, "testthumb.png", "image/png",Long.parseLong(combinedImageBOS.size()+""), combinedImageInpStream);
    	
    	

    	File combinedImageFile = new File("combinedImage.png");
   
    	// Save as new image
    	ImageIO.write(combinedImage, "PNG",  combinedImageFile);
    	
        //UPLOAD FILE TO S3 
	     //PutObjectResult resultS3 = awsService.addFiletoBucket(AWSConfiguration.USERSVIDEOBUCKET, videoDTO.getId()+".png", file);
    }
    
    
    
    
    //@Test
    public void integrateDataFromYoutube() throws Exception {
    	integrationServie.integrateDataFromVimeoYoutube(Long.parseLong(2948754+""));
    	
    }
        
    
    
    

     //@Test
    public void updateYoutubeInfoAndCheckIfbroken() throws Exception {
    	List<VideoDTO> videoList = videoService.selectVideosOfSourceStatusAndCommentSent(true,false,false,null,null);

    	int counter=1;
    	for (VideoDTO video : videoList) {
    		try {
    		YouTubeResultDTO youtubeResult = youtubeService.getVideoInformation(video.getYoutubeid());//BROKEN VIDEO (PRIVATE)
    	    if(youtubeResult.isEmptyStatus()) {
    	    		System.out.println(counter+" Broken video:"+video.getId());
    	    		videoService.updateVideoHealthStatus(video.getId(), null, VideoHealthStatus.BROKEN);
    	    		
    	    } else {
    	    	System.out.println(counter+" Heatlhy video:"+video.getId()+":"+youtubeResult.getChannelTitle());
    	    	videoService.updateVideoYoutubeInfo(video.getId(),youtubeResult.getChannelId(),youtubeResult.getChannelTitle());
    	    	videoService.updateVideoHealthStatus(video.getId(), null, VideoHealthStatus.HEALTHY);
    	    }
    		counter+=1;
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    	} 
    }
    
    
    //@Test
    public void updateVimeoInfoAndCheckIfbroken() throws Exception {
    	List<VideoDTO> videoList = videoService.selectVideosOfSourceStatusAndCommentSent(false,true,false,null,null);

    	int counter=1;
    	for (VideoDTO video : videoList) {
    		try {
    		VimeoResultDTO vimeoResult = vimeoService.getVideoInformation(video.getVimeoid());//BROKEN VIDEO (PRIVATE)
    	    if(vimeoResult.isEmptyStatus()) {
    	    		System.out.println(counter+" Broken video:"+video.getId());
    	    		videoService.updateVideoHealthStatus(video.getId(), null, VideoHealthStatus.BROKEN);
    	    		
    	    } else {
    	    	System.out.println(counter+" Heatlhy video:"+video.getId());
    	    	videoService.updateVideoVimeoInfo(video.getId(),vimeoResult.getUserid(),vimeoResult.getUsername(),vimeoResult.getUserlink());
    	    	videoService.updateVideoHealthStatus(video.getId(), null, VideoHealthStatus.HEALTHY);
    	    //}
    		counter+=1;
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    	} 
    }
    
    
    
    
   
    //@Test
    public void testVimeoYoutubeInformation() throws Exception {
    	VimeoResultDTO vimeoResult = vimeoService.getVideoInformation("1514-10881");
    	//YouTubeResultDTO youtubeResult = youtubeService.getVideoInformation("9ql5iwIybB8");
    	//YouTubeResultDTO youtubeResult = youtubeService.getVideoInformation("ppykk99MYW2fs");//BROKEN VIDEO (PRIVATE)
    	//if(youtubeResult.isEmptyStatus()) {
    		//videoService.updateVideoHealthStatus(new Long(2947163), null, VideoHealthStatus.BROKEN);
    		
    	//}
    	
    	
    	//videoService.updateVideoYoutubeInfo(new Long(2948613),youtubeResult.getChannelId(),youtubeResult.getChannelTitle());

    	
    }    

    
    //@Test
    public void batchUpdateS3AccessProperties() throws Exception {
    		try {
    		
    		awsService.updateS3ObjectPermissions(AWSConfiguration.VIDEOTHUMBBUCKET, "eb2d5f51ce59475e817e1b6a2455ee2d.png",GroupGrantee.AllUsers,Permission.Read);
    		} catch (Exception e) {System.out.println("ERROR UPDATE FILE PROPERTIES"); e.printStackTrace();}
    }
    
    
    
    
    
    
    //@Test
    public void batchUpdateVideoTable() throws Exception {
    	List<VideoDTO> videoList = videoService.selectAdminCatalogVideos(new AdminQueryDTO(),0,2000);
    	
    	for(VideoDTO videoDTO : videoList) {
    		try {
    		System.out.println("UPDATING VIDEO ROW:"+videoDTO.getId());	
    		List<IDDescrDTO> videoTypesList =  dbService.findVideoTypeOfVideo(videoDTO.getId());
    		
    		if((videoTypesList!=null) && (!videoTypesList.isEmpty())) {
    			String videoTypeIdsToCommaSeparated = videoTypesList.stream().map(x->x.getId().toString()).collect(Collectors.joining (","));
    			Integer firstVideoTypeID = videoTypesList.get(0).getId();
    			
    			System.out.println("videoTypeIdsToCommaSeparated:"+videoTypeIdsToCommaSeparated+"-firstVideoTypeID"+firstVideoTypeID);	
    			videoDTO.setVideotypeids(videoTypeIdsToCommaSeparated);
    			videoDTO.setMarkerimgid(firstVideoTypeID.toString());
    			videoService.updateVideoTypeStringAndMargerImgID(videoDTO);
    		}
    		
    		} catch (Exception e) {System.out.println("ERROR UPDATING VIDEO ROW:"+videoDTO.getId()); e.printStackTrace();}
    	}
    }
    

    
    //@Test
    public void checkYoutubeComments() throws Exception {
    	
    	//LOAD CREDENTIAL
	    GoogleAuthorizationCodeFlow flow = GoogleAuthService.initYoutubeAuthorizationCodeFlow();
	    Credential credential = flow.loadCredential(GoogleAuthService.TERRABIS_AUTH_USER);
	    
    	List<VideoDTO> videoList = videoService.selectAdminCatalogVideos(new AdminQueryDTO(VideoPublishStatus.PUBLISHED,false),0,50);
    	
    	
    	for(VideoDTO videoDTO : videoList) {
    		boolean terrabisCommentFlag = false;
    		
    		if(videoDTO.getYoutube()) {
	    		
    			
    			
    			System.out.println("-----------------------FETCHING COMMENT AUTHORS FOR:"+videoDTO.getId()+"----------------------------");
	    		System.out.println("YOUTUBE URL:"+videoDTO.getLinkedurl());
	    		
	    		
	    		try {
	    	    List<CommentThread> commentsList = 	youtubeService.videoCommentsList(credential, videoDTO);
	    	    if(commentsList.isEmpty()) {
	    	    	System.out.println("EMPTY COMMENTS LIST FOR:"+videoDTO.getId());
	    	    	
	    	    }
	    	    
	    	    
	    	    
	    	    for(CommentThread commentThread : commentsList) {
	    	    	CommentSnippet snippet = commentThread.getSnippet().getTopLevelComment().getSnippet();
	    	    	System.out.println(snippet.getAuthorDisplayName());
	    	    	if(snippet.getAuthorDisplayName().contains("Terrabis")) {
	    	    		terrabisCommentFlag = true;
	    	    		System.out.println("TERRABIS COMMENT FOUND!");
	    	    	}
	    	    	
	    	    }
	    	    
	    	    
    	    	if(!terrabisCommentFlag) {

    	    			System.out.println("UPDATING TERRABIS COMMENT FLAG:"+videoDTO.getId());
	    	    		videoService.updateVideoTerrabisCommented(videoDTO, true);
	    	    		System.out.println("POST COMMENT FOR:"+videoDTO.getLinkedurl());
	    	    		integrationServie.postInitialTerrabisCommentToYoutube(credential, videoDTO);

    	    	}
	    	    
	    	    
	    	    
	    	    
	
	    		} catch (Exception e) {}
    		}
    	}
    }
    
    
    

    
    
    
    

    //@Test
    public void generateStatisticsPlayVideos() throws Exception {
    	 BIQueryDTO queryDTO = new BIQueryDTO();
    	
    	
    	//CALCULATE STATISTICS ONE DAY BEFORE THE LAST CALCULATED ENTRY DATE
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(statsService.selectStatisticsDayActivityLastDate());
    	//calendar.add(Calendar.DATE, -1);
        queryDTO.setDateCreatedFrom(calendar.getTime());
    
        System.out.println(calendar.getTime().toString());
        
    	
    	List<BIChartCountsPerTimeDTO> resultList = statsService.playsPerDay(queryDTO);
    	for (BIChartCountsPerTimeDTO countDTO : resultList) {
    		System.out.println("Updating plays per day:"+countDTO.getDateNumber()+" for plays:"+countDTO.getCount());
    		statsService.updateDayActivity(Long.parseLong(countDTO.getDateNumber()+""), countDTO.getCount(), countDTO.getDateCreated(),countDTO.getCounttype());
    	}



    }
    



    //@Test
    public void changePassword() throws Exception {
    	userService.changeUserPassword(new Long(2946971), "villykou");
    	
    }
    
    
    
    //@Test
    public void testEmailLayout() throws Exception {
    	UserDTO user = userService.findUserByEmail("gsotiropoulos2007@gmail.com");
    	UserDTO claimer = userService.findUserByEmail("gallis@novatron.gr");
    	VideoDTO video = videoService.findVideoById(new Long(2946947));
    	
    	
    	
    	 //String newPassword =RandomStringUtils.randomAlphanumeric(8);
    	 //emailService.newPasswordEmail(user, newPassword);
    	 
    	 
    	emailService.resetPasswordTokenEmail(user, "12345");
    	emailService.resetActivationTokenEmail(user, "12345");
    	emailService.newRegistrationEmail(user, "12345");
    	emailService.videoClaimRequestEmail(user.getEmail(), video, claimer, user);
    	emailService.videoClaimSucessEmailToClaimer(user.getEmail(), video, claimer, user);
    	emailService.videoClaimSucessEmailToOriginalUploader(user.getEmail(), video, claimer, user);
    	emailService.videoApprovedEmail(user.getEmail(), video, user);
    	emailService.videoRejectEmail(user.getEmail(), video, user,"-Dynamic Rejection Reason-");

    }
    
    
    
    
    
    
    
    //@Test
    public void massYoutubePostAcomment() throws Exception {
    	
    	List<VideoDTO> videoList = videoService.selectVideosOfSourceStatusAndCommentSent(true,false,false,VideoPublishStatus.PUBLISHED,false);
    	videoList = videoList.stream().limit(9).collect(Collectors.toList());
    	
    	System.out.println(videoList.size());
    	
    	
    	
    	int counter=1;
    	for (VideoDTO videoDTO : videoList) {
    		try {
    			
    			String comment = new String(Constants.newUploadComment);
    	    	comment = StringUtils.replace(comment,"$videoid$", videoDTO.getId()+"");
    	    	//System.out.println(comment);
    	    	
    	    	//POST A COMMENT
         		

    	    	integrationServie.postInitialTerrabisCommentToYoutube(null,videoDTO);
    	    	System.out.println(counter+" Posting a youtube comment for video:"+videoDTO.getYoutubeid());

    		counter+=1;
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    	} 
    	
    }
    
    
    
    
    
    
    //@Test
	public void youtubePostAcomment() {
		

	   
	   	
    	
    	
	   	 try {

	   		
	   		 	//LOAD CREDENTIAL
	    	    GoogleAuthorizationCodeFlow flow = GoogleAuthService.initYoutubeAuthorizationCodeFlow();
	    	    Credential credential = flow.loadCredential(GoogleAuthService.TERRABIS_AUTH_USER);
	   
	    	    
	    	    if(credential!=null) {
	    	    	//GET VIDEO RECORD AND UPDATE TRASNFER STATUS TO PREPARED SYNCHRONIZED PER THREAD 
	    	    	VideoDTO videoDTO = videoService.findVideoById(Long.parseLong(2947428+""));
	    	
	    	   		if(videoDTO!=null) {

	    	   			//REFRESH ACCESS TOKEN IN CASE OF EXPIRATION
		    	    	credential.refreshToken();
		    	    	
		    	    	
		    	    	String comment = new String(Constants.newUploadComment);
		    	    	comment = StringUtils.replace(comment,"$videoid$", videoDTO.getId()+"");
		    	    	
		    	    	
		    	   
		    	    	//POST A COMMENT
		    	   		youtubeService.postTopLevelComment(credential, videoDTO, comment);
		    	   		
	    	   		}
		        }

	   	        
	   	 } catch (Exception e) {
	   		 e.printStackTrace();
	   		 
	   	 }

	      
	    }
    
    
    
    //@Test
    public void testYoutubeVideoInfo() throws Exception {
    	
    	VideoDTO video = videoService.findVideoById(Long.parseLong(2947428+""));
    	YouTubeResultDTO result = youtubeService.getVideoInformation(video.getYoutubeid());  	
    	System.out.println("channelid:"+result.getChannelId());
    	System.out.println("tags:"+result.getTags());
    	
    }
    
    
    
    //@Test
    public void checkOwnershipClaimsAndVerify() {
		
		 try {
		
		List<VideoClaimDTO> openClaimsList = claimService.findClaimsByNotStatus(VideoClaimStatus.SUCCESS);
		for(VideoClaimDTO claim : openClaimsList) {
			VideoDTO video = videoService.findVideoById(claim.getVideoid());
			UserDTO claimer = userService.findUserById(claim.getClaimerid());
			UserDTO uploader = userService.findUserById(claim.getUserid());
			
			if(video.getYoutube()) {
				YouTubeResultDTO result = youtubeService.getVideoTags(video.getYoutubeid());
				if(result.getTags().contains("terrabisownershipclaim")) {
						claimService.verifyOwnership(claim.getClaimerid(), uploader.getId(),claim.getVideoid());
						claimService.claimVerifiedSuccess(claim.getId());
						try {
						emailService.videoClaimSucessEmailToClaimer(claimer.getEmail(), video, claimer, uploader);
						emailService.videoClaimSucessEmailToOriginalUploader(uploader.getEmail(), video, claimer, uploader);
						} catch (Exception e) {e.printStackTrace();}
				} else {
					claimService.claimVerifiedFailure(claim.getId());
					
				}
			}
		}
		
		 } catch (Exception e) {
	   		 e.printStackTrace();
	   		 
	   	 }

	}
    
    
    
    //@Test
    public void testVideoOwnershipVerification() throws Exception {
    	
    	String videoId = "5H8ttKz751Q";
    	YouTubeResultDTO result = youtubeService.getVideoInformation(videoId);  	
     	System.out.println("channelid:"+result.getChannelId());
    	System.out.println("tags:"+result.getTags());
    	if(result.getTags().contains("terrabisownershipclaim"))
    		System.out.println("verified ownership");

    	
    }
    
    
    
    
    
    
    //@Test
	public void testVideosCountPerUser() throws Exception {
    	
    	   Gson gson = new Gson();
           TypeToken<List<BIChartDTO>> typeToken = new TypeToken<List<BIChartDTO>>() {};
        
           BIQueryDTO queryDTO = new BIQueryDTO();

           
		   List<BIChartCountsPerColumnDTO> rawData = statsMapper.selectVideosCountPerUser(queryDTO);
		   int videosCount =  rawData.stream().mapToInt(BIChartCountsPerColumnDTO::getCount).sum();
		   System.out.println(videosCount);
		   
		   
		   Integer usersWhoUploadedVideoCount =  rawData.size();
		   Integer videosOfTop5Users = rawData.parallelStream().limit(5).mapToInt(x ->x.getCount()).sum();
		   Integer videosOfNext10Users = rawData.parallelStream().skip(5).limit(10).mapToInt(x ->x.getCount()).sum();
		   Integer videosOfNext20Users = rawData.parallelStream().skip(15).limit(20).mapToInt(x ->x.getCount()).sum();
		   Integer videosOfNext50Users = rawData.parallelStream().skip(35).limit(50).mapToInt(x ->x.getCount()).sum();
		   Integer videosOfRestUsers = rawData.parallelStream().skip(85).mapToInt(x ->x.getCount()).sum();
    	   
		   
		   List<BIChartDTO> resultList = new ArrayList<>();
		   resultList.add(new BIChartDTO("5 users",videosOfTop5Users));
		   resultList.add(new BIChartDTO("10 users",videosOfNext10Users));
		   resultList.add(new BIChartDTO("20 users",videosOfNext20Users));
		   resultList.add(new BIChartDTO("50 users",videosOfNext50Users));
		   resultList.add(new BIChartDTO("rest",videosOfRestUsers));
	}
    
    
    
    


    //@Test
	public void testVideosVSUsersPerDay() throws Exception {
    	
    	   Gson gson = new Gson();
           TypeToken<List<BIChartDTO>> typeToken = new TypeToken<List<BIChartDTO>>() {};
        
           BIQueryDTO queryDTO = new BIQueryDTO();

           
           
		   List<BIChartCountsPerTimeDTO> rawDataListUsersUnionVideos = statsMapper.selectUsersUnionVideosPerDay(queryDTO); 
		     
		   
		   
		     
		     Map<String, List<BIChartCountsPerTimeDTO>> rawDataMapOfDayKey =
		    		 rawDataListUsersUnionVideos.parallelStream().collect(Collectors.groupingBy(BIChartCountsPerTimeDTO::getDateString));
		     
		     
		     
		     
		     List<BIChartCountsPerTimeDTO> videosVSUsersPerDay = rawDataMapOfDayKey.entrySet().parallelStream()
		    		 .map(x-> new BIChartCountsPerTimeDTO(
		    				 x.getValue().get(0).getDateCreated(),
		    				 x.getKey(),null,null, 0, 0, 0,
		    				 x.getValue().parallelStream().mapToInt(BIChartCountsPerTimeDTO::getCount1).sum(),
		    				 x.getValue().parallelStream().mapToInt(BIChartCountsPerTimeDTO::getCount2).sum()
		    				 )
		    			)
		    		 .collect(Collectors.toList());
		     
		     
		     videosVSUsersPerDay = videosVSUsersPerDay.parallelStream().sorted((x1,x2) -> (x1.getDateCreated().getTime()  <x2.getDateCreated().getTime()) ? -1 : 1).collect(Collectors.toList());
		     
		     
		     videosVSUsersPerDay.stream().forEach(x->System.out.println(x.getDateCreated().toString()+"-users:"+x.getCount1()+"-videos:"+x.getCount2()));
		     
           
    	
	}
    
    
    
    //@Test
	public void statisticsDhjhjhjashboard1() throws Exception {
		
    	BIQueryDTO queryDTO = new BIQueryDTO();
    	
    	
    	List<VideoDTO> videoList = videoService.selectBIVideos(queryDTO, new RowBounds(0, 200000000));
    	//List<VideoDTO> videoList = videoAllList.stream().filter(x -> (x.getDatecreated().before(queryDTO.getDateCreatedTo()) && x.getDatecreated().after(queryDTO.getDateCreatedFrom()))).collect(Collectors.toList());

    	//VIDEOS TOTAL
    	//Long videosRangeTotal =  videoList.stream().collect(Collectors.counting());
     	Long videosTotal =  videoList.stream().collect(Collectors.counting());

    	
    	
    	//GROUP BY DATE
    	Map<Date,Long> groupedByDateMap = videoList.stream()
    	.collect(Collectors.groupingBy( (x) -> x.getDatecreated(), Collectors.counting()));
    	
    	//GROUP BY MONTH
    	Map<Date,Long> groupedByMonthMap = videoList.stream()
    	.collect(Collectors.groupingBy( (x) -> new Date(x.getDatecreated().getYear(), x.getDatecreated().getMonth(), 1), Collectors.counting()));
    	    	
    	
    	
    	
    	//SORTED VIDEOS COUNT BY DATE
    	Map<Date, Long> sortedGroupedByDateMap =
    			groupedByDateMap.entrySet().parallelStream().
    					sorted(Map.Entry.comparingByKey((x1, x2) -> (x1.getTime() <x2.getTime()) ? 1 : -1))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                (e1, e2) -> e1, LinkedHashMap::new));
    	//sortedGroupedByDateMap.forEach((videodate,total)->System.out.println("status:"+videodate+":"+total));

    	
    
    	//SORTED VIDEOS COUNT BY MONTH
    	Map<Date, Long> sortedGroupedByMonthMap =
    			groupedByMonthMap.entrySet().parallelStream().
    					sorted(Map.Entry.comparingByKey((x1, x2) -> (x1.getTime() <x2.getTime()) ? 1 : -1))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                (e1, e2) -> e1, LinkedHashMap::new));
    	//sortedGroupedByMonthMap.forEach((videodate,total)->System.out.println("status:"+videodate+":"+total));

    	
    	////MAX MONTH
    	Optional<Entry<Date,Long>> maxMonthOfUploads = 
    			sortedGroupedByMonthMap.entrySet().stream()
    	                               .collect(Collectors.maxBy(Comparator.comparing(Map.Entry::getValue)));
    	maxMonthOfUploads.ifPresent(System.out::println);
    	
    	
    	
    	
    	//AVERAGE MONTHLY UPLOADS
    	Double avgMonthlyUploads = 
    			sortedGroupedByMonthMap.entrySet().stream()
    	                               .collect(Collectors.averagingLong(Map.Entry::getValue));
    	System.out.println(avgMonthlyUploads);
    	
    	
    	
    	//AVERAGE DAILY UPLOADS
    	Double avgDailyUploads = 
    			sortedGroupedByDateMap.entrySet().stream()
    	                               .collect(Collectors.averagingLong(Map.Entry::getValue));
    	
    	System.out.println(avgDailyUploads);
    	
    	

    	
    	
    	
    	
    	
    	
    	
        	
    	
		//PER STATUS 
		 videoList.parallelStream().collect(Collectors.groupingBy( x->x.getStatus(), Collectors.counting())).
		 forEach((status,total)->System.out.println("status:"+status+"\t"+total));
		

		videoList.parallelStream()
		//.sorted((e1, e2) -> e1.getDatecreated().compareTo(e2.getDatecreated()))
		.map( x-> (x.getDatecreated().getMonth()+1)+"/"+(x.getDatecreated().getYear()-100))
    	.collect(Collectors.groupingBy( x->x, Collectors.counting()))
    	.forEach((month,total)->System.out.println("month:"+month+":"+total));
		
		
		
		
		
		
		
    	
    	
		videoList.parallelStream()
		//.sorted((e1, e2) -> e1.getDatecreated().compareTo(e2.getDatecreated()))
		//.map( x-> (x.getDatecreated().getDay()+1)+"/"+(x.getDatecreated().getMonth()+1)+"/"+(x.getDatecreated().getYear()-100))
    	.collect(Collectors.groupingBy( x->x.getDatecreated(), Collectors.counting()))
    	.entrySet().stream()
    	.sorted(Map.Entry.comparingByKey((e1, e2) -> (e1.getTime() <e2.getTime()) ? 1 : -1))
        .collect(Collectors.toMap(Entry::getKey, Entry::getValue))
    	.forEach((datekey,total)->System.out.println("date:"+datekey.toString()+":"+total));    	
		 


		videoList.parallelStream()
		.sorted((e1, e2) -> (e1.getDatecreated().getTime() <e2.getDatecreated().getTime()) ? 1 : 0)
		.map( x-> (x.getDatecreated().getDay()+1)+"/"+(x.getDatecreated().getMonth()+1)+"/"+(x.getDatecreated().getYear()-100))
    	.collect(Collectors.groupingBy( x->x, Collectors.counting()))
    	.forEach((datekey,total)->System.out.println("date:"+datekey.toString()+":"+total));    	

		
		
		
		
		
		
	}
	
    
    

    //@Test
	public void sendTestEmail() throws Exception {
    	emailService.sentTestEmail();
	}
	
	
	
	
	//@Test
	public void uploadVideoToYoutubeChannel() throws Exception {
			//youtubeService.uploadVideoToTerrabisChannel("test-video.mov");
		}
		
	
	
	
	//@Test
		public void incomingVideoEmail() throws Exception {
			VideoDTO videoDTO = new VideoDTO();
			//videoDTO.DUMMY();
	    	emailService.incomingVideoEmail("gsotiropoulos2007@gmail.com", videoDTO);
	}
		
	
	
	//@Test
	public void rejectVideoEmail() throws Exception {
			VideoDTO videoDTO = new VideoDTO();
			//videoDTO.DUMMY();
	    	emailService.videoRejectEmail("gsotiropoulos2007@gmail.com", videoDTO,null,"Your video doesn\'t meet our quality requirements");
	}
			
	

    //@Test
	public void testYoutubeInfo() throws Exception {
    	YouTubeResultDTO result = youtubeService.getVideoInformation("6n9aaOuHaN8");
    	System.out.println(result.getThumbnailuri());
    	System.out.println(result.getDuration());
    	
	}
    
    
    //@Test
	public void testVimeoThumbnail() throws Exception {
    	VimeoResultDTO vimeoResult = vimeoService.getVideoInformation("134008155");
		System.out.println(vimeoResult.getDuration());
	}
    
    
*/    
    
    
    
    
}