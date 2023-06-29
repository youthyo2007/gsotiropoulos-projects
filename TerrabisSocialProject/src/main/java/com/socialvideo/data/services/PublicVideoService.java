
package com.socialvideo.data.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.socialvideo.data.dto.AdvancedFiltersDTO;
import com.socialvideo.data.dto.PaginatorDTO;
import com.socialvideo.data.dto.PublicVideoDTO;
import com.socialvideo.data.dto.QueryDTO;
import com.socialvideo.data.dto.VideoCatalogQueryDTO;
import com.socialvideo.data.dto.VideoDTO;
import com.socialvideo.data.dto.PublicVideoPointDTO;
import com.socialvideo.data.dto.maps.GeocodingDTO;
import com.socialvideo.data.mappers.PublicVideoMapper;



@Service
public class  PublicVideoService   {
    
	@Autowired
	private PublicVideoMapper videoMapper;



	
	
	
	//HOMEPAGE TOTAL DURATION AND TOTAL VIDEOS
	public Integer selectCountTotalVideos() {
		return videoMapper.selectCountTotalVideos();
		
		
	}
	
	
	
	public Integer selectCountTotalVideoMinutes() {
		return videoMapper.selectCountTotalVideoMinutes();
	}
	
	
	
	
	

	public List<PublicVideoDTO> selectCatalogVideosViewPort(GeocodingDTO gmapresult, QueryDTO queryDTO, PaginatorDTO paginator) {
		return videoMapper.selectCatalogVideosViewPort(gmapresult, queryDTO, paginator.getOffset(),paginator.getLimit());
		
	}
	
	
	

	public Integer selectCountCatalogVideosViewPort(GeocodingDTO gmapresult, QueryDTO queryDTO) {
		return videoMapper.selectCountCatalogVideosViewPort(gmapresult, queryDTO);
	}

	public List<PublicVideoDTO> selectTrendingCatalogVideos(VideoCatalogQueryDTO queryDTO, PaginatorDTO paginator) {
		return videoMapper.selectTrendingCatalogVideos(queryDTO, paginator.getOffset(),paginator.getLimit());
	}

	public Integer selectCountTrendingCatalogVideos(VideoCatalogQueryDTO queryDTO) {
		return videoMapper.selectCountTrendingCatalogVideos(queryDTO);
		
	}
	
	
	public List<PublicVideoDTO> selectCatalogVideos(VideoCatalogQueryDTO queryDTO, PaginatorDTO paginator) {
		return videoMapper.selectCatalogVideos(queryDTO, paginator.getOffset(),paginator.getLimit());
	}

	public Integer selectCountCatalogVideos(VideoCatalogQueryDTO queryDTO) {
		return videoMapper.selectCountCatalogVideos(queryDTO);
		
	}
	
	

	public Integer selectCountCollectionVideos(VideoCatalogQueryDTO queryDTO) {
		return videoMapper.selectCountCollectionVideos(queryDTO);
	}



	public List<PublicVideoDTO> selectCollectionVideos(VideoCatalogQueryDTO queryDTO, PaginatorDTO paginator) {
		return videoMapper.selectCollectionVideos(queryDTO, paginator.getOffset(),paginator.getLimit());
	}	

	

	//NEARBY RELATED AND PROMOTED SITE VIDEOS

	public List<PublicVideoDTO> selectNearyByVideos(Long videoid,String latitude, String longitude, Integer range, PaginatorDTO paginator) {
		return videoMapper.selectNearyByVideos(videoid,latitude,longitude,range,paginator.getOffset(),paginator.getLimit());
		
	}

	public List<PublicVideoPointDTO> selectNearyByVideoPoints(Long videoid,String latitude, String longitude, Integer range, PaginatorDTO paginator) {
		return videoMapper.selectNearyByVideoPoints(videoid,latitude,longitude,range,paginator.getOffset(),paginator.getLimit());
	}

	
	public List<PublicVideoDTO> selectRelatedTagVideos(String tags, PaginatorDTO paginator) {
		return videoMapper.selectRelatedTagVideos(tags,paginator.getOffset(),paginator.getLimit());
	}

	
    public PublicVideoDTO findPublicVideoWithReviewsById(Long videoid) {
    	return videoMapper.findPublicVideoWithReviewsById(videoid);
    }

	

    public PublicVideoDTO findPublicVideoById(Long videoid) {
    	return videoMapper.findPublicVideoById(videoid);
    }

	public PublicVideoDTO  findLatestUploadedVideoOfUser(Long userid) {
		return videoMapper.findLatestUploadedVideoOfUser(userid);
	
	}


	public List<PublicVideoPointDTO> findVideoPointsOfVideoList(List<String> videoidslist) {
		return videoMapper.findVideoPointsOfVideoList(videoidslist);
		
	}




	public PublicVideoDTO findPublicVideoByUUID(String uuid) {
		return videoMapper.findPublicVideoByUUID(uuid);
	}



	public List<PublicVideoPointDTO> selectCatalogVideoPointsOfViewport(GeocodingDTO gmapresult,QueryDTO queryDTO, int offset, int limit) {
		return videoMapper.selectCatalogVideoPointsOfViewport(gmapresult,queryDTO,offset,limit);
	}



	public List<PublicVideoDTO> selectUserPendingCatalogVideos(VideoCatalogQueryDTO queryDTO, PaginatorDTO paginator) {
		return videoMapper.selectUserPendingCatalogVideos(queryDTO,paginator.getOffset(),paginator.getLimit());
	}



	public Integer selectCountUserPendingCatalogVideos(VideoCatalogQueryDTO queryDTO) {
		return videoMapper.selectCountUserPendingCatalogVideos(queryDTO);
	}




		
}
