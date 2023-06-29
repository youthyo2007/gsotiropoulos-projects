package com.socialvideo.data.dto.statistics;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StatisticsUsersDashboard1DTO implements StatisticsDashboardDTO {
	

	public StatisticsUsersDashboard1DTO() {}

	@JsonProperty("barChartRegistrationsPerMonth")
	public String barChartRegistrationsPerMonth;

	@JsonProperty("lineChartRegVsVideos")
	public String lineChartRegVsVideos;

	
	@JsonProperty("lineChartUsersPerDay")
	public String lineChartUsersPerDay;
	
	
	
	@JsonProperty("donutChartVideosPerUsersCount")
	public String donutChartVideosPerUsersCount;
	

	@JsonProperty("videosPerUsersCountList")
	public List<BIChartDTO> videosPerUsersCountList;
	
	
	
	@JsonProperty("totalUsers")
	public Integer totalUsers;

	@JsonProperty("top5UsersUploadPercentage")
	public String top5UsersUploadPercentage;
	
	@JsonProperty("pieChartVideosPercetagePerUsersCount")
	public String pieChartVideosPercetagePerUsersCount;


	
	@JsonProperty("maxUsersPerMonth")
	public Integer maxUsersPerMonth;
	
	@JsonProperty("maxUsersPerDay")
	public Integer maxUsersPerDay;
	
	
	@JsonProperty("maxVideosPerDay")
	public Integer maxVideosPerDay;
	
	@JsonProperty("maxRegVsVideosPerDay")
	public Integer maxRegVsVideosPerDay;
	
	public String getBarChartRegistrationsPerMonth() {
		return barChartRegistrationsPerMonth;
	}


	public void setBarChartRegistrationsPerMonth(String barChartRegistrationsPerMonth) {
		this.barChartRegistrationsPerMonth = barChartRegistrationsPerMonth;
	}


	public String getLineChartRegVsVideos() {
		return lineChartRegVsVideos;
	}


	public void setLineChartRegVsVideos(String lineChartRegVsVideos) {
		this.lineChartRegVsVideos = lineChartRegVsVideos;
	}


	public Integer getTotalUsers() {
		return totalUsers;
	}


	public void setTotalUsers(Integer totalUsers) {
		this.totalUsers = totalUsers;
	}


	public String getPieChartVideosPercetagePerUsersCount() {
		return pieChartVideosPercetagePerUsersCount;
	}


	public void setPieChartVideosPercetagePerUsersCount(String pieChartVideosPercetagePerUsersCount) {
		this.pieChartVideosPercetagePerUsersCount = pieChartVideosPercetagePerUsersCount;
	}


	public String getDonutChartVideosPerUsersCount() {
		return donutChartVideosPerUsersCount;
	}


	public void setDonutChartVideosPerUsersCount(String donutChartVideosPerUsersCount) {
		this.donutChartVideosPerUsersCount = donutChartVideosPerUsersCount;
	}


	public List<BIChartDTO> getVideosPerUsersCountList() {
		return videosPerUsersCountList;
	}


	public void setVideosPerUsersCountList(List<BIChartDTO> videosPerUsersCountList) {
		this.videosPerUsersCountList = videosPerUsersCountList;
	}


	public String getTop5UsersUploadPercentage() {
		return top5UsersUploadPercentage;
	}


	public void setTop5UsersUploadPercentage(String top5UsersUploadPercentage) {
		this.top5UsersUploadPercentage = top5UsersUploadPercentage;
	}


	public String getLineChartUsersPerDay() {
		return lineChartUsersPerDay;
	}


	public void setLineChartUsersPerDay(String lineChartUsersPerDay) {
		this.lineChartUsersPerDay = lineChartUsersPerDay;
	}


	public Integer getMaxUsersPerMonth() {
		return maxUsersPerMonth;
	}


	public void setMaxUsersPerMonth(Integer maxUsersPerMonth) {
		this.maxUsersPerMonth = maxUsersPerMonth;
	}


	public Integer getMaxUsersPerDay() {
		return maxUsersPerDay;
	}


	public void setMaxUsersPerDay(Integer maxUsersPerDay) {
		this.maxUsersPerDay = maxUsersPerDay;
	}


	public Integer getMaxVideosPerDay() {
		return maxVideosPerDay;
	}


	public void setMaxVideosPerDay(Integer maxVideosPerDay) {
		this.maxVideosPerDay = maxVideosPerDay;
	}


	public Integer getMaxRegVsVideosPerDay() {
		return maxRegVsVideosPerDay;
	}


	public void setMaxRegVsVideosPerDay(Integer maxRegVsVideosPerDay) {
		this.maxRegVsVideosPerDay = maxRegVsVideosPerDay;
	}	
	
	
	
}

