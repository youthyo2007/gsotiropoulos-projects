package com.socialvideo.data.dto.statistics;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.socialvideo.data.dto.VideoDTOSmall;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StatisticsVideoDashboard2DTO implements StatisticsDashboardDTO {
	
	
	
	private KPIPerfomanceTargetDTO KPIvideosPublished;
    private KPIPerfomanceTargetDTO KPIvideosRejected;
    private KPIPerfomanceTargetDTO KPIvideosBroken;
    private StatisticsVideoCountDTO videosPerformanceStats;
    
    
    
    private java.util.List<VideoDTOSmall> topPlayedVideoList;
    
    
	@JsonProperty("lineChartPlaysPerDay")
	public String lineChartPlaysPerDay;
	

	

	
	
	public StatisticsVideoDashboard2DTO() {}
	
	
	
	

	@JsonProperty("lineChartPublishedVsRejectedVideos")
	public String lineChartPublishedVsRejectedVideos;


	@JsonProperty("pieChartVideosPerStatus")
	public String pieChartVideosPerStatus;


	public String getPieChartVideosPerStatus() {
		return pieChartVideosPerStatus;
	}


	public void setPieChartVideosPerStatus(String pieChartVideosPerStatus) {
		this.pieChartVideosPerStatus = pieChartVideosPerStatus;
	}


	public KPIPerfomanceTargetDTO getKPIvideosPublished() {
		return KPIvideosPublished;
	}


	public void setKPIvideosPublished(KPIPerfomanceTargetDTO kPIvideosPublished) {
		KPIvideosPublished = kPIvideosPublished;
	}


	public KPIPerfomanceTargetDTO getKPIvideosRejected() {
		return KPIvideosRejected;
	}


	public void setKPIvideosRejected(KPIPerfomanceTargetDTO kPIvideosRejected) {
		KPIvideosRejected = kPIvideosRejected;
	}


	public KPIPerfomanceTargetDTO getKPIvideosBroken() {
		return KPIvideosBroken;
	}


	public void setKPIvideosBroken(KPIPerfomanceTargetDTO kPIvideosBroken) {
		KPIvideosBroken = kPIvideosBroken;
	}


	public String getLineChartPlaysPerDay() {
		return lineChartPlaysPerDay;
	}


	public void setLineChartPlaysPerDay(String lineChartPlaysPerDay) {
		this.lineChartPlaysPerDay = lineChartPlaysPerDay;
	}


	public String getLineChartPublishedVsRejectedVideos() {
		return lineChartPublishedVsRejectedVideos;
	}


	public void setLineChartPublishedVsRejectedVideos(String lineChartPublishedVsRejectedVideos) {
		this.lineChartPublishedVsRejectedVideos = lineChartPublishedVsRejectedVideos;
	}


	public StatisticsVideoCountDTO getVideosPerformanceStats() {
		return videosPerformanceStats;
	}


	public void setVideosPerformanceStats(StatisticsVideoCountDTO videosPerformanceStats) {
		this.videosPerformanceStats = videosPerformanceStats;
	}


	public java.util.List<VideoDTOSmall> getTopPlayedVideoList() {
		return topPlayedVideoList;
	}


	public void setTopPlayedVideoList(java.util.List<VideoDTOSmall> topPlayedVideoList) {
		this.topPlayedVideoList = topPlayedVideoList;
	}



	
	
	
	
	
	
	
}

