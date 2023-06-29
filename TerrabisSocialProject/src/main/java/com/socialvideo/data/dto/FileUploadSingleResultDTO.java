package com.socialvideo.data.dto;

import java.io.Serializable;

public class FileUploadSingleResultDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
		private String videoid;
		private String name;
		private String error;
		private String size;
		private String thumbnailUrl;
		private String deleteUrl;
		private String deleteType;
		private String finalResult;
		
		

		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getSize() {
			return size;
		}
		public void setSize(String size) {
			this.size = size;
		}
		public String getThumbnailUrl() {
			return thumbnailUrl;
		}
		public void setThumbnailUrl(String thumbnailUrl) {
			this.thumbnailUrl = thumbnailUrl;
		}
		public String getDeleteUrl() {
			return deleteUrl;
		}
		public void setDeleteUrl(String deleteUrl) {
			this.deleteUrl = deleteUrl;
		}
		public String getDeleteType() {
			return deleteType;
		}
		public void setDeleteType(String deleteType) {
			this.deleteType = deleteType;
		}
		public String getError() {
			return error;
		}
		public void setError(String error) {
			this.error = error;
		}
		public String getVideoid() {
			return videoid;
		}
		public void setVideoid(String videoid) {
			this.videoid = videoid;
		}
		public String getFinalResult() {
			return finalResult;
		}
		public void setFinalResult(String finalResult) {
			this.finalResult = finalResult;
		}
	
} 