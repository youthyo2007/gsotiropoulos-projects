package com.socialvideo.data.services.inter;

import java.util.Date;
import java.util.List;

import com.socialvideo.data.dto.AdminQueryDTO;
import com.socialvideo.data.dto.CurrentUserDTO;
import com.socialvideo.data.dto.PaginatorDTO;
import com.socialvideo.data.dto.PublicVideoDTO;
import com.socialvideo.data.dto.VideoDTO;
import com.socialvideo.data.dto.integration.FusionTableVideoDTO;
import com.socialvideo.data.dto.statistics.BIQueryDTO;
import com.socialvideo.data.model.VideoEntity;


public interface IVideoService {


	//CATALOG VIDEOS COUNT AND QUERIES
	public List<VideoDTO> selectAdminCatalogVideos(AdminQueryDTO queryDTO, PaginatorDTO paginator);
	public List<VideoDTO> selectAdminCatalogVideos(AdminQueryDTO queryDTO, int offset, int limit);
	public Integer selectCountAdminCatalogVideos(AdminQueryDTO queryDTO);
	public Long selectUserIDOfVideo(Long videoid);
	
	
	
	
	//ACTIONS CREATE INSERT, UPDATE AND DELETES
	public VideoDTO createNewVideo(VideoDTO videoDTO,Date createdDate);
	public void updateAdminVideo(VideoDTO videoDTO, Long modifierid, Boolean updateLink);
	public void updateUserVideo(PublicVideoDTO videoDTO, Long modifierid, Boolean updateLink);
	public void deleteVideo(Long userid, Long videoid);
	public void clearVideoTypesListOfVideo(Long videoid);
	public void insertVideoTypesListOfVideo(Long videoid, Long videotypeid);	
	public void updateCalc(VideoDTO videoDTO);
	public void updateVideoStatus(Long videoid, Long modifierid, Integer status);
	public void updateDuration(Long videoid, Integer duration);
	public void promoteVideoToSiteIndex(Long videoid, boolean promote);
	public void promoteVideoToMap(Long videoid, boolean promote);
	public void updateVideoTransferStatus(Long videoid, Integer transferstatus);
	public void computeReviewCount(Long videoid);
	public void countLikeVideo(Long userid, Long videoid);
	public void countPlayVideo(Long userid, Long videoid);
	public void countShareVideo(Long userid, Long videoid);
	public void computeRatingCount(Long videoid);
	public void rejectVideo(Long videoid, Long modifierid, Long rejectionreason, String rejectionReasonText);
	public void approveVideo(Long videoid, Long approverid);
	public void updateVideoYoutubeCommentSend(Long videoid, Boolean terrabiscomment, Boolean commentSend);
	public void updateVideoHealthStatus(Long videoid, Long modifierid, Integer status);
	public Boolean canEditVideo(CurrentUserDTO authenticatedUser, Long videoid);	
	public void setMarkAsUnwanted(Long videoid,Boolean value);
	
	
	
	//SINGLE VIDEO FIND
	public VideoDTO findVideoByUUID(String UUID);
	public VideoDTO findVideoById(Long videoid);
	public VideoDTO selectFileVideoForYoutubeChannelUpload(Long videoid, Integer transferstatus);
	public VideoEntity selectVideoEntity(Long videoid);	
	public FusionTableVideoDTO findFusionVideoById(Long videoid);
	public VideoDTO findByYoutubeId(String videoid);
	public VideoDTO findByVimeoId(String videoid);
	
	
	//LIKED AND SHARED VIDEOS
	public List<VideoDTO> selectLikedVideos(BIQueryDTO queryDTO,PaginatorDTO paginator);
	public List<VideoDTO> selectPlayedVideos(BIQueryDTO queryDTO,PaginatorDTO paginator);
	public List<VideoDTO> selectRatedVideos(BIQueryDTO queryDTO,PaginatorDTO paginator);
	public List<VideoDTO> selectSharedVideos(BIQueryDTO queryDTO,PaginatorDTO paginator);
	public List<VideoDTO> selectUploadedVideos(BIQueryDTO queryDTO,PaginatorDTO paginator);
	public Integer selectCountPlayedVideos(BIQueryDTO queryDTO);
	public Integer selectCountSharedVideos(BIQueryDTO queryDTO);
	public Integer selectCountLikedVideos(BIQueryDTO queryDTO);


	

	
	
	

	
}
