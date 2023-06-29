package com.socialvideo.data.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.socialvideo.data.dto.AdminQueryDTO;
import com.socialvideo.data.dto.CollectionDTO;
import com.socialvideo.data.dto.IDDescrDTO;
import com.socialvideo.data.dto.PaginatorDTO;
import com.socialvideo.data.dto.PublicVideoDTO;
import com.socialvideo.data.dto.QueryDTO;
import com.socialvideo.data.dto.VideoCatalogQueryDTO;
import com.socialvideo.data.dto.VideoDTO;
import com.socialvideo.data.dto.integration.FusionTableVideoDTO;
import com.socialvideo.data.dto.maps.GeocodingDTO;
import com.socialvideo.data.dto.statistics.BIQueryDTO;

public interface VideoMapper {
	
	
	
			//CATALOG VIDEOS COUNT AND QUERIES
			public List<VideoDTO> selectAdminCatalogVideos(@Param("QUERY") AdminQueryDTO queryDTO, @Param("offset") Integer offset,  @Param("limit") Integer limit);
			public Integer selectCountAdminCatalogVideos(@Param("QUERY") AdminQueryDTO queryDTO);
			public Long selectUserIDOfVideo(@Param("videoid") Long videoid);

			//SINGLE VIDEO INFO
			public PublicVideoDTO findPublicVideoById(@Param("id")  Long videoid);
			public VideoDTO findVideoByUUID(@Param("uuid") String UUID);
			public VideoDTO findVideoById(@Param("id") Long videoid);
			public FusionTableVideoDTO findFusionVideoById(@Param("id") Long videoid);
			public Double selectAvgVideoRating(@Param("videoid") Long videoid);
			public Integer selectCountVideoRatings(@Param("videoid") Long videoid);
			public Integer selectCountVideoReviews(@Param("videoid") Long videoid);
			public VideoDTO selectFileVideoForYoutubeChannelUpload(@Param("videoid") Long videoid, @Param("transferstatus") Integer transferstatus);
			
			
			//VIDEO ACTIONS
			public void deleteVideo(@Param("userid") Long userid, @Param("videoid") Long videoid);
			public void clearVideoTypesListOfVideo(@Param("videoid") Long videoid);
			public void insertVideoTypesListOfVideo(@Param("videoid") Long videoid,  @Param("videotypeid") Long videotypeid);	  
			
			
			//BI SHARED LIKED AND PLAYED VIDEOS	
			public List<VideoDTO> selectLikedVideos(@Param("QUERY") BIQueryDTO queryDTO, @Param("offset") Integer offset,  @Param("limit") Integer limit);
			public List<VideoDTO> selectPlayedVideos(@Param("QUERY") BIQueryDTO queryDTO, @Param("offset") Integer offset,  @Param("limit") Integer limit);
			public List<VideoDTO> selectRatedVideos(@Param("QUERY") BIQueryDTO queryDTO, @Param("offset") Integer offset,  @Param("limit") Integer limit);
			public List<VideoDTO> selectSharedVideos(@Param("QUERY") BIQueryDTO queryDTO, @Param("offset") Integer offset,  @Param("limit") Integer limit);
			public List<VideoDTO> selectUploadedVideos(@Param("QUERY") BIQueryDTO queryDTO,@Param("offset") Integer offset,  @Param("limit") Integer limit);
			public Integer selectCountPlayedVideos(@Param("QUERY") BIQueryDTO queryDTO);
			public Integer selectCountSharedVideos(@Param("QUERY") BIQueryDTO queryDTO);
			public Integer selectCountLikedVideos(@Param("QUERY") BIQueryDTO queryDTO);
			

			
			

			
			
			
			
			
			
			
			
			
			
			
			
			
	
	
		
	
		
		
		
		
	
	  //public VideoDTO selectOne(@Param("id") Long id);
	  
	  
	  
	
	
	
	
	
	
	
	
	
	
	  
	  
	
	
/*	
	
	  
	  
	  
	  
	  
	  
	  public List<VideoDTO> selectMapVideos(@Param("GMAPRESULT") GeocodingDTO gmapresult, @Param("QUERY") QueryDTO query,  @Param("offset") Integer offset,  @Param("limit") Integer limit);
	  public List<VideoDTO> selectTopRatingVideosAllTime(@Param("privacy") Integer privacy,@Param("rating") Integer rating, @Param("limit") Integer limit);
	  public List<VideoDTO> selectTopRatingVideosToday(@Param("privacy") Integer privacy,@Param("rating") Integer rating,@Param("limit") Integer limit);
	  public List<VideoDTO> selectTopRatingVideosLastHour(@Param("privacy") Integer privacy,@Param("rating") Integer rating,@Param("limit") Integer limit);
	  public List<VideoDTO> selectTopRatingVideosLastWeek(@Param("privacy") Integer privacy,@Param("rating") Integer rating,@Param("limit") Integer limit);
	  public List<VideoDTO> selectTopRatingVideosLastMonth(@Param("privacy") Integer privacy,@Param("rating") Integer rating,@Param("limit") Integer limit);
	  public List<VideoDTO> selectTopRatingVideosLastYear(@Param("privacy") Integer privacy,@Param("rating") Integer rating,@Param("limit") Integer limit);
	  public VideoDTO findLatestUploadedVideo(@Param("userid") Long userid);
	
	  public Integer selectCountVideosOfMapArea(@Param("GMAPRESULT") GeocodingDTO gmapresult,  @Param("QUERY") QueryDTO query);
	  public List<String> selectTagsOfMapArea(@Param("GMAPRESULT") GeocodingDTO gmapresult);
	  public List<VideoDTO> selectCatalogVideos(@Param("QUERY") VideoCatalogQueryDTO queryDTO, @Param("offset") Integer offset,  @Param("limit") Integer limit);
	  public Integer selectCountCatalogVideos(@Param("QUERY") VideoCatalogQueryDTO queryDTO);
	  public List<VideoDTO> selectAdminCatalogVideos(@Param("QUERY") AdminQueryDTO queryDTO, @Param("offset") Integer offset,  @Param("limit") Integer limit);
	  public Integer selectCountAdminCatalogVideos(@Param("QUERY") AdminQueryDTO queryDTO);
	  public List<VideoDTO> selectMapVideoMarkersOfViewport(@Param("GMAPRESULT") GeocodingDTO gmapresult,    @Param("QUERY") QueryDTO query, @Param("status") Integer status, @Param("offset") Integer offset,  @Param("limit") Integer limit);
	  public List<VideoDTO> selectAllVideoMarkers(@Param("offset") Integer offset,  @Param("limit") Integer limit);

	  public List<VideoDTO> selectPromoteSiteIndexVideos();


	  public List<String> findVideoTitles(@Param("title") String title, @Param("limit") Integer limit);
	  public List<VideoDTO> selectBIVideos(@Param("QUERY") BIQueryDTO queryDTO, @Param("offset") Integer offset,  @Param("limit") Integer limit);
	  public List<VideoDTO> selectVideosOfSource(@Param("youtube") Boolean youtube, @Param("vimeo") Boolean vimeo, @Param("file") Boolean file);
	  public List<VideoDTO> selectVideosOfSourceStatusAndCommentSent(@Param("youtube") Boolean youtube, @Param("vimeo") Boolean vimeo, @Param("file") Boolean file, @Param("status") Integer status,  @Param("commentsent")  Boolean commentsent);

	  //public void updateVideoShootingType(@Param("videoid") Long videoid, @Param("shootingtypeid") Integer shootingtypeid);
	
	  
	  
*/
	  


}
