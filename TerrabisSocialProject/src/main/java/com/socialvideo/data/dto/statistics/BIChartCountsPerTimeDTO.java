package com.socialvideo.data.dto.statistics;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.socialvideo.constant.StatisticsCountType;



@JsonIgnoreProperties(ignoreUnknown = true)
public class BIChartCountsPerTimeDTO implements Serializable {

	private static final long serialVersionUID = 1L;

		
	
		private StatisticsCountType counttype;
        private String yaxistype;
        private int hour;
        private int day;
        private String dateString;
        private String dayString;
        private int dateNumber;
        private int month;
        private String monthString;
		private int year;
        private int count;
		private int videocount;
        private int usercount;
        private int sharecount;
        private int ratingcount;
        private int reviewcount;
        private int likecount;
        private int playcount;
        private Date dateCreated;
    	private Long userid;
    	private Long videoid;
    	        
        
        

     public BIChartCountsPerTimeDTO(){}

     
     public BIChartCountsPerTimeDTO(Date dateCreated, String dateString,String dayString, String monthString, int day, int month, int year){
    	 this.dateCreated = dateCreated;
    	 this.dateString = dateString;
    	 this.dayString = dayString;
    	 this.monthString = monthString;
    	 this.day = day;
    	 this.month = month;
    	 this.year = year;
    	 
     }

     
     
    /**
     * @return the month
     */
    public int getMonth() {
        return month;
    }

    /**
     * @param month the month to set
     */
    public void setMonth(int month) {
        this.month = month;
    }

    /**
     * @return the year
     */
    public int getYear() {
        return year;
    }

    /**
     * @param year the year to set
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * @return the count
     */
    public int getCount() {
        return count;
    }

    /**
     * @param count the count to set
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * @return the monthString
     */
    public String getMonthString() {
        return monthString;
    }

    /**
     * @param monthString the monthString to set
     */
    public void setMonthString(String monthString) {
        this.monthString = monthString;
    }

    /**
     * @return the hour
     */
    public int getHour() {
        return hour;
    }

    /**
     * @param hour the hour to set
     */
    public void setHour(int hour) {
        this.hour = hour;
    }

    /**
     * @return the day
     */
    public int getDay() {
        return day;
    }

    /**
     * @param day the day to set
     */
    public void setDay(int day) {
        this.day = day;
    }

    /**
     * @return the dayString
     */
    public String getDayString() {
        return dayString;
    }

    /**
     * @param dayString the dayString to set
     */
    public void setDayString(String dayString) {
        this.dayString = dayString;
    }

    /**
     * @return the yaxistype
     */
    public String getYaxistype() {
        return yaxistype;
    }

    /**
     * @param yaxistype the yaxistype to set
     */
    public void setYaxistype(String yaxistype) {
        this.yaxistype = yaxistype;
    }

	public String getDateString() {
		return dateString;
	}


	public void setDateString(String dateString) {
		this.dateString = dateString;
	}


	public void setDateNumber(int dateNumber) {
		this.dateNumber = dateNumber;
	}


	public int getDateNumber() {
		return dateNumber;
	}


	public Date getDateCreated() {
		return dateCreated;
	}


	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}


	public StatisticsCountType getCounttype() {
		return counttype;
	}


	public void setCounttype(StatisticsCountType counttype) {
		this.counttype = counttype;
	}


	public int getVideocount() {
		return videocount;
	}


	public void setVideocount(int videocount) {
		this.videocount = videocount;
	}


	public int getUsercount() {
		return usercount;
	}


	public void setUsercount(int usercount) {
		this.usercount = usercount;
	}


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


	public Long getUserid() {
		return userid;
	}


	public void setUserid(Long userid) {
		this.userid = userid;
	}


	public Long getVideoid() {
		return videoid;
	}


	public void setVideoid(Long videoid) {
		this.videoid = videoid;
	}



}
