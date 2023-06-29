package com.socialvideo.data.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.socialvideo.data.dto.AdvancedFiltersDTO;
import com.socialvideo.data.dto.PaginatorDTO;
import com.socialvideo.data.dto.PublicVideoDTO;
import com.socialvideo.data.dto.PublicVideoPointDTO;
import com.socialvideo.data.dto.QueryDTO;
import com.socialvideo.data.dto.VideoCatalogQueryDTO;
import com.socialvideo.data.dto.VideoDTO;
import com.socialvideo.data.dto.maps.GeocodingDTO;

public interface PublicVideoMapper {
	
	
	
			//CATALOG VIDEOS COUNT AND QUERIES
			public Integer selectCountCatalogVideosViewPort(@Param("GMAPRESULT") GeocodingDTO gmapresult, @Param("QUERY") QueryDTO queryDTO);
			public List<PublicVideoDTO> selectCatalogVideosViewPort(@Param("GMAPRESULT") GeocodingDTO gmapresult, @Param("QUERY") QueryDTO queryDTO, @Param("offset") Integer offset,  @Param("limit") Integer limit);
			public List<PublicVideoPointDTO> selectCatalogVideoPointsOfViewport(@Param("GMAPRESULT") GeocodingDTO gmapresult, @Param("QUERY") QueryDTO queryDTO,  @Param("offset") Integer offset,  @Param("limit") Integer limit);
			public List<PublicVideoDTO> selectCatalogVideos(@Param("QUERY") VideoCatalogQueryDTO queryDTO, @Param("offset") Integer offset,  @Param("limit") Integer limit);
			public Integer selectCountCatalogVideos(@Param("QUERY") VideoCatalogQueryDTO queryDTO);
			public List<PublicVideoDTO> selectTrendingCatalogVideos(@Param("QUERY") VideoCatalogQueryDTO queryDTO, @Param("offset") Integer offset,  @Param("limit") Integer limit);
			public Integer selectCountTrendingCatalogVideos(@Param("QUERY") VideoCatalogQueryDTO queryDTO);
			public List<PublicVideoDTO> selectCollectionVideos(@Param("QUERY") VideoCatalogQueryDTO queryDTO, @Param("offset") Integer offset,  @Param("limit") Integer limit);
			public Integer selectCountCollectionVideos(@Param("QUERY") VideoCatalogQueryDTO queryDTO);
			public List<PublicVideoDTO> selectUserPendingCatalogVideos(@Param("QUERY") VideoCatalogQueryDTO queryDTO,  @Param("offset") Integer offset,  @Param("limit") Integer limit);

			
	
			
			//NEARBY  AND RELATED VIDEOS
			public List<PublicVideoDTO> selectRelatedTagVideos(@Param("tags") String tags,@Param("offset") Integer offset,  @Param("limit") Integer limit);
			public List<PublicVideoDTO> selectNearyByVideos(@Param("videoid") Long videoid, @Param("latitude") String latitude, @Param("longitude") String longitude, @Param("range") Integer range, @Param("offset") Integer offset,  @Param("limit") Integer limit);
			public List<PublicVideoPointDTO> selectNearyByVideoPoints(@Param("videoid") Long videoid, @Param("latitude") String latitude, @Param("longitude") String longitude, @Param("range") Integer range, @Param("offset") Integer offset,  @Param("limit") Integer limit);
			

			
			
			//SINGLE VIDEO INFO
			public PublicVideoDTO findPublicVideoById(@Param("id")  Long videoid);
			public PublicVideoDTO findPublicVideoWithReviewsById(@Param("id")  Long videoid);
			public PublicVideoDTO findPublicVideoByUUID(@Param("uuid") String uuid);
			public PublicVideoDTO findLatestUploadedVideoOfUser(@Param("userid")  Long userid);			
			
			
			//VIDEOS OF COLLECTION
			public List<PublicVideoDTO> selectVideosOfCollection(@Param("userid") Long userid, @Param("collectionid") Long collectionid, @Param("type") Integer type, @Param("privacy") Integer privacy,@Param("limit") Integer limit, @Param("filter") String filter);
			public List<PublicVideoDTO> selectVideosOfPlaylist(@Param("userid") Long userid, @Param("collectionid") Long collectionid,  @Param("privacy") Integer privacy,@Param("limit") Integer limit, @Param("filter") String filter);
			public List<PublicVideoPointDTO> findVideoPointsOfVideoList(@Param("videoidslist") List<String> videoidslist);
			
			
			
			
			
			
			//HOME PAGE COUNT OF TOTAL VIDEOS AND TOTAL VIDEO MINUTES
			public Integer selectCountTotalVideos();
			public Integer selectCountTotalVideoMinutes();
			public Integer selectCountUserPendingCatalogVideos(@Param("QUERY") VideoCatalogQueryDTO queryDTO);

	

			
			


			
			
			
	

}
