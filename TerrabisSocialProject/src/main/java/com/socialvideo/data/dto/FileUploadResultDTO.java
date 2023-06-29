package com.socialvideo.data.dto;

import java.io.Serializable;
import java.util.ArrayList;

public class FileUploadResultDTO implements Serializable {

	private static final long serialVersionUID = 1L;


	private ArrayList<FileUploadSingleResultDTO> files = new ArrayList<FileUploadSingleResultDTO>();
	
	
	public void put(FileUploadSingleResultDTO singleResultDO) {
		this.files.add(singleResultDO);
	}
	
	public void successVideo(VideoDTO videoDTO) {
		FileUploadSingleResultDTO singleResultDTO = new FileUploadSingleResultDTO();
		singleResultDTO.setName(videoDTO.getFilename());
		singleResultDTO.setSize(videoDTO.getVideosize().toString());
		singleResultDTO.setDeleteUrl("/user-"+videoDTO.getUserid()+"/video-"+videoDTO.getId()+"/delete");
		singleResultDTO.setThumbnailUrl("user-"+videoDTO.getUserid()+"/video-"+videoDTO.getId()+"/thumbnail");
		singleResultDTO.setDeleteType("DELETE");
		singleResultDTO.setFinalResult("SUCCESS");
		singleResultDTO.setVideoid(videoDTO.getId()+"");
		
		getFiles().add(singleResultDTO);
	}	
		
	
	public void successAvatar(AvatarDTO avatarDTO) {
		FileUploadSingleResultDTO singleResultDTO = new FileUploadSingleResultDTO();
		singleResultDTO.setName(avatarDTO.getId().toString()+"."+avatarDTO.getExtension());
		singleResultDTO.setSize(avatarDTO.getSize().toString());
		singleResultDTO.setDeleteUrl("user-"+avatarDTO.getUserid()+"/avatar-"+avatarDTO.getId()+"/remove");
		singleResultDTO.setThumbnailUrl("user-"+avatarDTO.getUserid()+"/avatar-"+avatarDTO.getId()+"/thumbnail");
		singleResultDTO.setDeleteType("DELETE");
		getFiles().add(singleResultDTO);
	}
	
	
	
	
	
	
	
	public void error(String fileName, String error) {
		FileUploadSingleResultDTO singleResultDTO = new FileUploadSingleResultDTO();
		singleResultDTO.setName(fileName);
		singleResultDTO.setError(error);
		singleResultDTO.setFinalResult("ERROR");
		getFiles().add(singleResultDTO);
	}
	
	public ArrayList<FileUploadSingleResultDTO> getFiles() {
		return files;
	}


	public void setFiles(ArrayList<FileUploadSingleResultDTO> files) {
		this.files = files;
	}



}