package com.socialvideo.data.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.socialvideo.data.dto.IDDescrDTO;
import com.socialvideo.data.dto.TagDTO;
import com.socialvideo.data.mappers.LookupMapper;
import com.socialvideo.data.mappers.RejectReasonMapper;
import com.socialvideo.data.mappers.TagCloudMapper;



@Service
public class DatabaseService {
	
	
	@Autowired
	private LookupMapper lookupMapper;
	

	@Autowired
	private TagCloudMapper tagCloudMapper;
	
	@Autowired
	private RejectReasonMapper rejectReasonMapper;
	

	public List<IDDescrDTO> lookupVideoTypeListOfVideo(Long videoid) {
		return lookupMapper.lookupVideoTypeListOfVideo(videoid);
	}

	public IDDescrDTO lookupSocialNetwork(Long id) {
		return lookupMapper.lookupSocialNetwork(id);
	}

	public IDDescrDTO lookupRating(Long id) {
		return lookupMapper.lookupRating(id);
	}

	

	 public IDDescrDTO lookupRejectionReason(Long id) {
		 return rejectReasonMapper.lookupRejectionReason(id);
	 }

	
	public List<IDDescrDTO> getRejectReasonsList() {
		return rejectReasonMapper.selectRejectReasonsList();
	}

	
	public List<IDDescrDTO> getVideoTypelist() {
		return lookupMapper.selectVideoTypeList();
	}
	
	public List<IDDescrDTO> getVideoShootingTypelist() {
		return lookupMapper.selectVideoShootingTypeList();
	}

	
	public List<TagDTO> findTags(String tagtext, Integer limit) {
		return tagCloudMapper.selectTags(tagtext, limit);
	}

	
	public List<TagDTO> getAllTagsList(Integer limit) {
		return tagCloudMapper.selectAllTags(limit);
	}
	
	

	public List<String> findTagsAsString(String tagtext, Integer limit) {
		return tagCloudMapper.selectTagsString(tagtext, limit);
	}
	
	
	
	
	
	

}
