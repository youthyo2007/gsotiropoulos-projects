
package com.socialvideo.data.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.socialvideo.constant.VideoClaimStatus;
import com.socialvideo.data.dto.AdminQueryDTO;
import com.socialvideo.data.dto.PaginatorDTO;
import com.socialvideo.data.dto.UserDTO;
import com.socialvideo.data.dto.VideoClaimDTO;
import com.socialvideo.data.dto.VideoDTO;
import com.socialvideo.data.dto.youtube.YouTubeResultDTO;
import com.socialvideo.data.mappers.VideoClaimMapper;
import com.socialvideo.data.model.VideoClaimEntity;
import com.socialvideo.data.model.VideoEntity;
import com.socialvideo.data.repositories.VideoClaimRepository;
import com.socialvideo.data.repositories.VideoRepository;



@Service
public class  VideoClaimService  {
    

	@Autowired
	private VideoClaimMapper videoClaimMapper;
	

	@Autowired
	private VideoClaimRepository claimRepository;

	@Autowired
	private VideoRepository videoRepository;
	
	
	@Autowired
	private UserService userService;


	@Autowired
	private EmailService emailService;
	
	
	
	
	
	
	
		public void updateClaimStatus(Long claimid, Integer status) {
			VideoClaimEntity claimEntity = claimRepository.findOne(claimid);
			claimEntity.setStatus(status);
			claimRepository.save(claimEntity);
		}
		
		public void claimVerifiedSuccess(Long claimid) {
			VideoClaimEntity claimEntity = claimRepository.findOne(claimid);
			claimEntity.setStatus(VideoClaimStatus.SUCCESS);
			claimEntity.setAttemptscount(claimEntity.getAttemptscount()+1);
			claimRepository.save(claimEntity);
		}
		
		public int claimVerifiedFailure(Long claimid) {
			VideoClaimEntity claimEntity = claimRepository.findOne(claimid);
			claimEntity.setStatus(VideoClaimStatus.FAILED);
			claimEntity.setAttemptscount(claimEntity.getAttemptscount()+1);
			claimRepository.save(claimEntity);
			return claimEntity.getAttemptscount();
		}
		
		
		
		public List<VideoClaimDTO> findClaimsByStatus(Integer status) {
			List<VideoClaimEntity> result = claimRepository.findClaimsByStatus(status);
			return result.stream().map(x->x.DTO()).collect(Collectors.toList());
			
		}
		
		public List<VideoClaimDTO> findClaimsByNotStatus(Integer status) {
			List<VideoClaimEntity> result = claimRepository.findClaimsByNotStatus(status);
			return result.stream().map(x->x.DTO()).collect(Collectors.toList());
			
		}

		
		
		public List<VideoClaimDTO> findClaimsOfVideo(Long videoID) {
			List<VideoClaimEntity> result = claimRepository.findClaimsOfVideo(videoID);
			return result.stream().map(x->x.DTO()).collect(Collectors.toList());
			
		}
		
		
		
		public Long createClaim(Long claimerid, Long videoid) {
			
			VideoEntity videoEntity = videoRepository.findOne(videoid);
			VideoClaimEntity claimEntity = new VideoClaimEntity(videoEntity.getUserid(),videoid,claimerid,VideoClaimStatus.PENDING);
			claimRepository.save(claimEntity);
			return claimEntity.getId();
		}

		public void verifyOwnership(Long ownerid, Long originaluploaderid, Long videoid) {
			videoClaimMapper.verifyOwnership(ownerid, originaluploaderid, videoid);
		}
		
		
		public VideoClaimDTO findClaimByVideoIDAndClaimerID(Long claimerid, Long videoid) {
			VideoClaimEntity entity = claimRepository.findByClaimerIDAndVideoID(claimerid, videoid);
			return entity!=null ? entity.DTO()  : null;
		}
	
		
		
		public VideoClaimDTO findClaimByVideoID(Long videoid) {
			VideoClaimEntity entity = claimRepository.findByVideoID(videoid);
			return entity!=null ? entity.DTO()  : null;
		}
		
		


		public Integer selectCountAdminClaims(AdminQueryDTO queryDTO) {
			return videoClaimMapper.selectCountAdminClaims(queryDTO);
		}

		public List<VideoClaimDTO> selectAdminClaims(AdminQueryDTO queryDTO, PaginatorDTO paginator) {
			return videoClaimMapper.selectAdminClaims(queryDTO,paginator.getOffset(),paginator.getLimit());
		}

		public VideoClaimDTO findClaimByID(Long claimid) {
			VideoClaimEntity claimEntity = claimRepository.findOne(claimid);
			return claimEntity.DTO();
			
		}

		public void deleteClaim(Long claimid) {
			VideoClaimEntity claimEntity = claimRepository.findOne(claimid);
			 claimRepository.delete(claimEntity);
			
		}
	
		
		
		
}
		

		
		

