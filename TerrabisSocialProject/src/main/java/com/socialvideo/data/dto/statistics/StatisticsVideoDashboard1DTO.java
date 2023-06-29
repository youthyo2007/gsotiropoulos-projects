package com.socialvideo.data.dto.statistics;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StatisticsVideoDashboard1DTO implements StatisticsDashboardDTO {
	
	
	

	@JsonProperty("barChartVideosPerMonth")
	public String barChartVideosPerMonth;
	
	@JsonProperty("pieChartVideosPerSource")
	public String pieChartVideosPerSource;
	
	
	@JsonProperty("lineChartVideosPerDay")
	public String lineChartVideosPerDay;
	

	
	
	@JsonProperty("total")
	public Integer total;
	
	
	@JsonProperty("maxVideosPerDay")
	public Integer maxVideosPerDay;
	
	@JsonProperty("maxVideosPerMonth")
	public Integer maxVideosPerMonth;

	
	
	public String getLineChartVideosPerDay() {
		return lineChartVideosPerDay;
	}









	public void setLineChartVideosPerDay(String lineChartVideosPerDay) {
		this.lineChartVideosPerDay = lineChartVideosPerDay;
	}









	public  KPIPerfomanceTargetDTO KPIvideosUploadedInOneMonth;
	public  KPIPerfomanceTargetDTO KPIvideosUploadedInOneDay;
	public KPIPerfomanceTargetDTO KPIvideosPublished;
	
	
	
	
	
	
	public StatisticsVideoDashboard1DTO() {}









	public KPIPerfomanceTargetDTO getKPIvideosUploadedInOneMonth() {
		return KPIvideosUploadedInOneMonth;
	}





	public void setKPIvideosUploadedInOneMonth(KPIPerfomanceTargetDTO kPIvideosUploadedInOneMonth) {
		KPIvideosUploadedInOneMonth = kPIvideosUploadedInOneMonth;
	}





	public KPIPerfomanceTargetDTO getKPIvideosUploadedInOneDay() {
		return KPIvideosUploadedInOneDay;
	}





	public void setKPIvideosUploadedInOneDay(KPIPerfomanceTargetDTO kPIvideosUploadedInOneDay) {
		KPIvideosUploadedInOneDay = kPIvideosUploadedInOneDay;
	}





	public KPIPerfomanceTargetDTO getKPIvideosPublished() {
		return KPIvideosPublished;
	}





	public void setKPIvideosPublished(KPIPerfomanceTargetDTO kPIvideosPublished) {
		KPIvideosPublished = kPIvideosPublished;
	}









	public String getBarChartVideosPerMonth() {
		return barChartVideosPerMonth;
	}









	public void setBarChartVideosPerMonth(String barChartVideosPerMonth) {
		this.barChartVideosPerMonth = barChartVideosPerMonth;
	}









	public String getPieChartVideosPerSource() {
		return pieChartVideosPerSource;
	}









	public void setPieChartVideosPerSource(String pieChartVideosPerSource) {
		this.pieChartVideosPerSource = pieChartVideosPerSource;
	}









	public Integer getTotal() {
		return total;
	}









	public void setTotal(Integer total) {
		this.total = total;
	}









	public Integer getMaxVideosPerDay() {
		return maxVideosPerDay;
	}









	public void setMaxVideosPerDay(Integer maxVideosPerDay) {
		this.maxVideosPerDay = maxVideosPerDay;
	}









	public Integer getMaxVideosPerMonth() {
		return maxVideosPerMonth;
	}









	public void setMaxVideosPerMonth(Integer maxVideosPerMonth) {
		this.maxVideosPerMonth = maxVideosPerMonth;
	}



	
	
}

