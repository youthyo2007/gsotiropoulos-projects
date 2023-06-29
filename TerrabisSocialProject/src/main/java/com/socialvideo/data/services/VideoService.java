
package com.socialvideo.data.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.socialvideo.constant.VideoPublishStatus;
import com.socialvideo.data.dto.AdminQueryDTO;
import com.socialvideo.data.dto.CurrentUserDTO;
import com.socialvideo.data.dto.PaginatorDTO;
import com.socialvideo.data.dto.PublicVideoDTO;
import com.socialvideo.data.dto.VideoDTO;
import com.socialvideo.data.dto.integration.FusionTableVideoDTO;
import com.socialvideo.data.dto.statistics.BIQueryDTO;
import com.socialvideo.data.mappers.VideoMapper;
import com.socialvideo.data.model.VideoEntity;
import com.socialvideo.data.repositories.VideoRepository;
import com.socialvideo.data.services.inter.IVideoService;



@Service
public class  VideoService  implements   IVideoService {
    
	@Autowired
	private VideoMapper videoMapper;

	
	@Autowired
	private VideoRepository videoRepository;
	



	@Override
	public Long selectUserIDOfVideo(Long videoid) {
		return videoMapper.selectUserIDOfVideo(videoid);
		
		
	}
	
	
	
	@Override
	public List<VideoDTO> selectAdminCatalogVideos(AdminQueryDTO queryDTO, PaginatorDTO paginator) {
		return videoMapper.selectAdminCatalogVideos(queryDTO,paginator.getOffset(),paginator.getLimit());
	}

	

	@Override
	public List<VideoDTO> selectAdminCatalogVideos(AdminQueryDTO queryDTO, int offset, int limit) {
		return videoMapper.selectAdminCatalogVideos(queryDTO, offset,limit);
	}
	
	
	
	@Override
	public Integer selectCountAdminCatalogVideos(AdminQueryDTO queryDTO) {
		return videoMapper.selectCountAdminCatalogVideos(queryDTO);
	}



/*************************** ACTIONS CREATE INSERT, UPDATE AND DELETES *************************************************************************/
 	@Override
	public  VideoDTO createNewVideo(VideoDTO videoDTO,Date createdDate)  {
		VideoEntity videoEntity = new VideoEntity();
		//CategoryEntity catEntity = catRepository.findOne(Long.parseLong(videoDTO.getSelectedCategory())); 
		videoEntity.LOAD(videoDTO);
		videoEntity.setDatecreated(createdDate!=null ? createdDate : new Date());
		videoEntity = videoRepository.save(videoEntity);
		//RETURN BACK DTO WHICH HAS THE VIDEO ID NUMBER OF THE NEWLY CREATED RECORD
		return videoEntity.DTO();			

	}

	
	@Override
	public void updateAdminVideo(VideoDTO videoDTO, Long modifierid,Boolean updateLink) {
		VideoEntity videoEntity = videoRepository.findOne(videoDTO.getId());
		//CategoryEntity catEntity = catRepository.findOne(Long.parseLong(videoDTO.getSelectedCategory()));
		videoEntity.LOADVIDEOEDIT(videoDTO,updateLink);
		videoEntity.setModifierid(modifierid);
		videoEntity.setDatemodified(new Date());
		videoRepository.save(videoEntity);
	}


	@Override
	public void updateUserVideo(PublicVideoDTO videoDTO, Long modifierid,Boolean updateLink) {
		VideoEntity videoEntity = videoRepository.findOne(videoDTO.getId());
		//CategoryEntity catEntity = catRepository.findOne(Long.parseLong(videoDTO.getSelectedCategory()));
		videoEntity.LOADVIDEOEDIT(videoDTO,updateLink);
		videoEntity.setModifierid(modifierid);
		videoEntity.setDatemodified(new Date());
		videoRepository.save(videoEntity);
	}

	
	
	@Override
	public void deleteVideo(Long userid, Long videoid) {
		videoMapper.deleteVideo(userid,videoid);
	    videoMapper.clearVideoTypesListOfVideo(videoid);
		
	}
	
	
	@Override	
	 public void clearVideoTypesListOfVideo(Long videoid) {
		  videoMapper.clearVideoTypesListOfVideo(videoid);
	 }
	
	@Override		
	 public void insertVideoTypesListOfVideo(Long videoid, Long videotypeid) {
		  videoMapper.insertVideoTypesListOfVideo(videoid,videotypeid);
		  
	  }

	
	@Override
	public void updateCalc(VideoDTO videoDTO) {
		VideoEntity videoEntity = videoRepository.findOne(videoDTO.getId());
		videoEntity.LOADCALC(videoDTO);
		videoRepository.save(videoEntity);
	}
	
	
	@Override
	public void updateDuration(Long videoid, Integer duration) {
		VideoEntity videoEntity = videoRepository.findOne(videoid);
		videoEntity.setDuration(duration);
		videoRepository.save(videoEntity);
	}

	@Override
	public void updateVideoYoutubeCommentSend(Long videoid, Boolean terrabiscomment, Boolean commentSend) {
		VideoEntity videoEntity = videoRepository.findOne(videoid);
		if(terrabiscomment!=null)
			videoEntity.setTerrabiscomment(terrabiscomment);

		if(commentSend!=null)
			videoEntity.setCommentsent(commentSend);
		videoRepository.save(videoEntity);
	}

	
	@Override
	public void countLikeVideo(Long userid, Long videoid) {
		VideoEntity videoEntity = videoRepository.findOne(videoid);
		videoEntity.like();
		videoRepository.save(videoEntity);
	}
	
	@Override
	public void countPlayVideo(Long userid, Long videoid) {
		VideoEntity videoEntity = videoRepository.findOne(videoid);
		videoEntity.play();
		videoRepository.save(videoEntity);
	}

	@Override
	public void countShareVideo(Long userid, Long videoid) {
		VideoEntity videoEntity = videoRepository.findOne(videoid);
		if(videoEntity!=null) {
			videoEntity.share();
			videoRepository.save(videoEntity);
		}
	}
	

	


	@Override
	public void approveVideo(Long videoid,  Long approverid) {
		VideoEntity videoEntity = videoRepository.findOne(videoid);
		videoEntity.setApproverid(approverid);
		videoEntity.setDateapproved(new Date());		
		videoEntity.setDatestatusmodified(new Date());
		videoEntity.setDatemodified(new Date());
		videoEntity.setModifierstatusid(approverid);

		
		//IF VIDEO IS LINK FROM YOUTUBE OR VIMEO PUBLISH VIDEO INSTANTLY.
		if(videoEntity.getLink())  {
			videoEntity.setStatus(VideoPublishStatus.PUBLISHED);
		}
		else {
			videoEntity.setStatus(VideoPublishStatus.APPROVED);
		}

		videoRepository.save(videoEntity);
	}
	
	
	@Override
	public void rejectVideo(Long videoid,  Long modifierid, Long rejectionReason, String rejectionReasonText) {
		VideoEntity videoEntity = videoRepository.findOne(videoid);
		videoEntity.setStatus(VideoPublishStatus.REJECTED);
		videoEntity.setRejectreasonid(rejectionReason);
		videoEntity.setRejectReasonText(rejectionReasonText);
		videoEntity.setDatestatusmodified(new Date());
		videoEntity.setApproverid(modifierid);
		videoEntity.setDateapproved(new Date());	
		videoEntity.setDatemodified(new Date());
		videoEntity.setModifierstatusid(modifierid);		
		
		videoRepository.save(videoEntity);
	}


	@Override
	public void updateVideoStatus(Long videoid,  Long modifierid, Integer status) {
		
		VideoEntity videoEntity = videoRepository.findOne(videoid);
		videoEntity.setStatus(status);
		videoEntity.setModifierstatusid(modifierid);
		videoEntity.setDatestatusmodified(new Date());
		videoEntity.setDatemodified(new Date());		
		videoRepository.save(videoEntity);
	}
	

	
	@Override
	public void updateVideoHealthStatus(Long videoid,  Long modifierid, Integer status) {

		VideoEntity videoEntity = videoRepository.findOne(videoid);
		videoEntity.setHealthstatus(status);
		videoEntity.setDatemodified(new Date());		
		videoRepository.save(videoEntity);
	}
	
	
	
	@Override
	public void updateVideoTransferStatus(Long videoid, Integer transferstatus) {
		VideoEntity videoEntity = videoRepository.findOne(videoid);
		videoEntity.setTransferstatus(transferstatus);
		videoEntity.setDatemodified(new Date());		
		videoRepository.save(videoEntity);
	}

	
	@Override
	public void promoteVideoToSiteIndex(Long videoid, boolean promote) {
		VideoEntity videoEntity = videoRepository.findOne(videoid);
		if(promote)
			videoEntity.setPromotesiteindex(1);
		else
			videoEntity.setPromotesiteindex(0);
			
		videoRepository.save(videoEntity);
		
	}


	@Override
	public void promoteVideoToMap(Long videoid, boolean promote) {
		VideoEntity videoEntity = videoRepository.findOne(videoid);
		if(promote)
			videoEntity.setPromotemap(1);
		else
			videoEntity.setPromotemap(0);
			
		videoRepository.save(videoEntity);
		
	}
	
	@Override
	public void computeRatingCount(Long videoid) {
		VideoEntity videoEntity = videoRepository.findOne(videoid);
		
		//SELECT COUNT RATINGS AND AVG 
		Double avgRating = videoMapper.selectAvgVideoRating(videoid);
		Integer ratingscount = videoMapper.selectCountVideoRatings(videoid);
		videoEntity.setRatingscount(ratingscount);
		videoEntity.setRatingssum(avgRating);
		
		
		videoRepository.save(videoEntity);
		
		
		
	}
	
	
	@Override
	public void computeReviewCount(Long videoid) {
		VideoEntity videoEntity = videoRepository.findOne(videoid);
		
		//SELECT COUNT REVIEWS
		Integer reviewscount = videoMapper.selectCountVideoReviews(videoid);
		videoEntity.setReviewscount(reviewscount);
		videoRepository.save(videoEntity);

	}
	
	
	
	
	@Override
	public Boolean canEditVideo(CurrentUserDTO authenticatedUser, Long videoid) {
		Boolean canEdit = false;
		
		VideoDTO video =videoMapper.findVideoById(videoid);
		if(video.getUser().getId().toString().equalsIgnoreCase(authenticatedUser.getId().toString()))
			canEdit = true;
			
		return canEdit;
		
		
	}

	
	
	
	/*************************** END ACTIONS CREATE INSERT, UPDATE AND DELETES *************************************************************************/
	
	
	
	
	
	
	/*************************** SINGLE VIDEO INFO  *************************************************************************/
	@Override
    public VideoDTO findVideoById(Long videoid) {
    	return videoMapper.findVideoById(videoid);
    }

	
	@Override
    public VideoDTO findByYoutubeId(String videoid) {
		  VideoEntity vidEntity =  videoRepository.findByYoutubeId(videoid);
		  return vidEntity!=null ? vidEntity.DTO() : null;
    }

	
	@Override
    public VideoDTO findByVimeoId(String videoid) {
		  VideoEntity vidEntity =  videoRepository.findByVimeoId(videoid);
		  return vidEntity!=null ? vidEntity.DTO() : null;
    }

	
	@Override
    public VideoDTO findVideoByUUID(String uuid) {
    	return videoMapper.findVideoByUUID(uuid);
    }
	
	
	
	
	@Override
	public VideoEntity selectVideoEntity(Long videoid) {
		  VideoEntity vidEntity =  videoRepository.findOne(videoid);
		   return vidEntity!=null ? vidEntity : null;
	}
	
	@Override
	public FusionTableVideoDTO findFusionVideoById(Long videoid) {
		return videoMapper.findFusionVideoById(videoid);
	}
	
	
	
	/*************************** SINGLE VIDEO INFO  *************************************************************************/
	
	
	
	
	/*************************** SINGLE LIKED AND SHARED VIDEOS  *************************************************************************/
	
	@Override
	public List<VideoDTO> selectLikedVideos(BIQueryDTO queryDTO,PaginatorDTO paginator) {
		return videoMapper.selectLikedVideos(queryDTO,paginator.getOffset(),paginator.getLimit());
	}
	
	
	@Override
	public List<VideoDTO> selectPlayedVideos(BIQueryDTO queryDTO,PaginatorDTO paginator) {
		return videoMapper.selectPlayedVideos(queryDTO,paginator.getOffset(),paginator.getLimit());
	}
	
	
	
	@Override
	public List<VideoDTO> selectRatedVideos(BIQueryDTO queryDTO,PaginatorDTO paginator) {
		return videoMapper.selectRatedVideos(queryDTO,paginator.getOffset(),paginator.getLimit());
	}
	
	
	@Override
	public List<VideoDTO> selectSharedVideos(BIQueryDTO queryDTO,PaginatorDTO paginator) {
		return videoMapper.selectSharedVideos(queryDTO,paginator.getOffset(),paginator.getLimit());
	}
	
	
	@Override
	public List<VideoDTO> selectUploadedVideos(BIQueryDTO queryDTO,PaginatorDTO paginator) {
		return videoMapper.selectUploadedVideos(queryDTO,paginator.getOffset(),paginator.getLimit());
	}

	@Override
	public Integer selectCountPlayedVideos(BIQueryDTO queryDTO) {
		return videoMapper.selectCountPlayedVideos(queryDTO);
	}

	@Override
	public Integer selectCountSharedVideos(BIQueryDTO queryDTO) {
		return videoMapper.selectCountSharedVideos(queryDTO);
	}

	@Override
	public Integer selectCountLikedVideos(BIQueryDTO queryDTO) {
		return videoMapper.selectCountLikedVideos(queryDTO);
	}
	/*************************** SINGLE LIKED AND SHARED VIDEOS  *************************************************************************/

	

	    
	/*************************** TASK SERVICE FUNCTIONS  *************************************************************************/
	@Override
	public VideoDTO selectFileVideoForYoutubeChannelUpload(Long videoid, Integer transferstatus) {
		return videoMapper.selectFileVideoForYoutubeChannelUpload(videoid,transferstatus);
	}



	@Override
	public void setMarkAsUnwanted(Long videoid, Boolean value) {
		VideoEntity videoEntity = videoRepository.findOne(videoid);
		videoEntity.setMarkasunwanted(value);
		videoEntity.setDatemodified(new Date());	
		videoEntity.setDatestatusmodified(new Date());
		videoRepository.save(videoEntity);
		
	}

	

	
	
		
	
		
}
