
package com.socialvideo.data.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.socialvideo.constant.CollectionTypes;
import com.socialvideo.constant.VideoPrivacyStatus;
import com.socialvideo.data.dto.CollectionDTO;
import com.socialvideo.data.dto.PaginatorDTO;
import com.socialvideo.data.dto.PublicVideoDTO;
import com.socialvideo.data.dto.UserDTO;
import com.socialvideo.data.dto.VideoDTO;
import com.socialvideo.data.mappers.CollectionMapper;
import com.socialvideo.data.mappers.PublicVideoMapper;
import com.socialvideo.data.mappers.VideoMapper;
import com.socialvideo.data.model.CollectionEntity;
import com.socialvideo.data.model.UserEntity;
import com.socialvideo.data.model.VideoEntity;
import com.socialvideo.data.repositories.CollectionRepository;



@Service
public class  CollectionService  {

	@Autowired
	private PublicVideoMapper videoMapper;
	
	@Autowired
	private CollectionMapper collectionMapper;
	
	@Autowired
	private CollectionRepository collectionRepository;
	

	
	

	

    public  void initUserCollectionsUponRegistration(Long userid) {
    	CollectionEntity watchLaterEntity = new CollectionEntity(userid,"WATCH LATER","A collection of videos to watch in the nearest future",CollectionTypes.WATCHLATER,VideoPrivacyStatus.PUBLIC);
    	collectionRepository.save(watchLaterEntity);
    	
    	CollectionEntity favoritesEntity = new CollectionEntity(userid,"FAVORITES BUCKET","A collection of videos to add to playlists in the nearest future",CollectionTypes.FAVORITESBUCKET,VideoPrivacyStatus.PUBLIC);
    	collectionRepository.save(favoritesEntity);
    }
	
    

    public CollectionDTO findCollectionById(Long collectionid) {
    	CollectionEntity collectionEntity =  collectionRepository.findOne(collectionid);
    	 return collectionEntity!=null ? collectionEntity.DTO() : null;
    }
    

    public void updateCalc(CollectionDTO collectionDTO) {
    	CollectionEntity collEntity = collectionRepository.findOne(collectionDTO.getId());
    	collEntity.LOADCALC(collectionDTO);
    	collectionRepository.save(collEntity);
    }
    
	
	

	public  CollectionDTO createNewCollection(CollectionDTO collectionDTO)  {
		CollectionEntity collectionEntity = new CollectionEntity();
		collectionEntity.LOAD(collectionDTO);
		collectionEntity = collectionRepository.save(collectionEntity);
		//RETURN BACK DTO WHICH HAS THE VIDEO ID NUMBER OF THE NEWLY CREATED RECORD
		return collectionEntity.DTO();			

	}

	
	
	public void removeCollection(Long userid, Long collectionid) {
		collectionMapper.removeCollection(collectionid, userid);

	}

	
	public CollectionDTO selectCollection(Long collectionid, Boolean empty) {
		if(empty)
			return collectionMapper.selectOneEmpty(collectionid);
		else
			return collectionMapper.selectOne(collectionid);
			
	}
    

	public List<CollectionDTO> selectCollections(Long userid, Integer collectiontype, String collectiontitle,  Integer privacy, Integer limit, String filter) {
    	return collectionMapper.selectCollections(userid, collectiontype, collectiontitle, privacy, limit, filter);
    }
    
	public List<CollectionDTO> selectPaginatedCollections(Long userid, Integer collectiontype, PaginatorDTO paginator) {
    	return collectionMapper.selectPaginatedCollections(userid, collectiontype,paginator.getOffset(),paginator.getLimit());
    }
    

		public List<CollectionDTO> selectCollectionsEmpty(Long userid, Integer collectiontype, String collectiontitle,  Integer privacy, Integer limit, String filter) {
	    	return collectionMapper.selectCollectionsEmpty(userid, collectiontype, collectiontitle, privacy, limit, filter);
	    	
	    	
	    }
	

	public CollectionDTO selectWatchLaterCollection(Long userid,Boolean empty) {
    	if(empty)
    	 	return collectionMapper.selectCollectionsEmpty(userid, CollectionTypes.WATCHLATER, null,null,null,null).get(0);
    	else
    		return collectionMapper.selectCollections(userid, CollectionTypes.WATCHLATER, null,null,null,null).get(0);
    }

	
	public CollectionDTO selectFavoritesCollection(Long userid,Boolean empty) {
    	if(empty)
    	 	return collectionMapper.selectCollectionsEmpty(userid, CollectionTypes.FAVORITESBUCKET, null,null,null,null).get(0);
    	else
    		return collectionMapper.selectCollections(userid, CollectionTypes.FAVORITESBUCKET, null,null,null,null).get(0);
    }
	
  
	public List<PublicVideoDTO> selectVideosOfCollection(Long userid, Long collectionid, Integer type, Integer privacy, Integer limit, String filter) {
		return videoMapper.selectVideosOfCollection(userid, collectionid, type, privacy, limit, filter);	
	}



	public List<PublicVideoDTO> selectVideosOfPlaylist(Long userid, Long collectionid, Integer privacy, Integer limit, String filter) {
		return videoMapper.selectVideosOfPlaylist(userid, collectionid, privacy, limit, filter);	
	}


	
	public void editCollection(CollectionDTO collection) {
		CollectionEntity collectionEntity = collectionRepository.findOne(collection.getId());
		collectionEntity.setTitle(collection.getTitle());
		collectionEntity.setDescription(collection.getDescription());
		collectionEntity = collectionRepository.save(collectionEntity);
	}



	public void updatePathSort(Long collectionID, Long videoID, int index) {
		collectionMapper.updatePathSort(collectionID, videoID, index);
		
	}



	public Integer selectCountCollections(Integer collectiontype) {
		return collectionMapper.selectCountCollections(collectiontype);
	}



	public void countPlayCollection(Long userid, Long collectionid) {
		CollectionEntity collectionEntity = collectionRepository.findOne(collectionid);
		collectionEntity.play();
		collectionRepository.save(collectionEntity);
		
	}
 	
 	
 	
 	
 	
	
	
	
}
