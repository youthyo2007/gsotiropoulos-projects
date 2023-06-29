package com.socialvideo.data.dto.statistics;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.socialvideo.constant.StatisticsCountType;



@JsonIgnoreProperties(ignoreUnknown = true)
public class StatisticsVideoCountDTO implements Serializable {

	private static final long serialVersionUID = 1L;

		


        private int sharecount;
        private int ratingcount;
        private int reviewcount;
        private int likecount;
        private int playcount;
   
        
        

     public StatisticsVideoCountDTO(){}




	public int getSharecount() {
		return sharecount;
	}




	public void setSharecount(int sharecount) {
		this.sharecount = sharecount;
	}




	public int getRatingcount() {
		return ratingcount;
	}




	public void setRatingcount(int ratingcount) {
		this.ratingcount = ratingcount;
	}




	public int getReviewcount() {
		return reviewcount;
	}




	public void setReviewcount(int reviewcount) {
		this.reviewcount = reviewcount;
	}




	public int getLikecount() {
		return likecount;
	}




	public void setLikecount(int likecount) {
		this.likecount = likecount;
	}




	public int getPlaycount() {
		return playcount;
	}




	public void setPlaycount(int playcount) {
		this.playcount = playcount;
	}

   

}
