
package com.socialvideo.data.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.socialvideo.data.dto.AvatarDTO;
import com.socialvideo.data.model.AvatarEntity;
import com.socialvideo.data.repositories.AvatarRepository;
import com.socialvideo.data.repositories.AvatarRepository;



@Service
public class  AvatarService {
    
	@Autowired
	private AvatarRepository avatarRepository;
	
	
	@Autowired
	private Base64WrapperService base64WrapperService;
	
    
	public AvatarDTO create(AvatarDTO avatarDTO) {
		avatarDTO.setImagebase64(base64WrapperService.encodeB64Bytes(avatarDTO.getDatablob()));
		AvatarEntity avatarEntity = new AvatarEntity(avatarDTO);
		getAvatarRepository().save(avatarEntity);
		avatarEntity = getAvatarRepository().findOne(avatarEntity.getId());
		return avatarEntity.DTO();
	}
	
	public void delete(AvatarDTO avatarDTO) {
		AvatarEntity avatarEntity = getAvatarRepository().findOne(avatarDTO.getId());
		 getAvatarRepository().delete(avatarEntity);
	}

	public AvatarDTO update(AvatarDTO avatarDTO) {
		avatarDTO.setImagebase64(base64WrapperService.encodeB64Bytes(avatarDTO.getDatablob()));
		AvatarEntity avatarEntity = getAvatarRepository().findOne(avatarDTO.getId());
		avatarEntity.LOAD(avatarDTO);
		getAvatarRepository().save(avatarEntity);
		avatarDTO = avatarEntity.DTO();
		return avatarDTO;

	}

	
	public AvatarDTO findOne(Long id) {
		AvatarEntity avatarEntity = getAvatarRepository().findOne(id);
		AvatarDTO avatarDTO = avatarEntity.DTO();
		return avatarDTO;
	}
	
	
	public AvatarDTO findActive(Long userid) {
		AvatarDTO avatarDTO = new AvatarDTO();
		AvatarEntity avatarEntity = getAvatarRepository().findActive(userid);
		if(avatarEntity!=null)
			avatarDTO = avatarEntity.DTO();
		
		return avatarDTO;
	}
	
	

	public List<AvatarDTO> findByUserid(Long userid) {
		List<AvatarEntity> avatarEntityList = getAvatarRepository().findByUserid(userid);
		ArrayList<AvatarDTO> avatarDTOList = new ArrayList<>();
		for (AvatarEntity avatarEntity : avatarEntityList) {
			AvatarDTO avatarDTO = avatarEntity.DTO();
			avatarDTOList.add(avatarDTO);
        }

		return avatarDTOList;
	}
	
	
	private AvatarRepository getAvatarRepository() {
		return avatarRepository;
	}

	private void setAvatarRepository(AvatarRepository avatarRepository) {
		this.avatarRepository = avatarRepository;
	}
	
	
	
	
	
	
	
	
    
    
    
    
    
    
    


}
