package com.socialvideo.data.dto.statistics;

public class StatisticsDTO {
	
	
	StatisticsVideoStatusDTO videostatus = new StatisticsVideoStatusDTO();
	
	StatisticsDashboardDTO dash1DTO;


	
	
	public StatisticsDTO() {
		
	}

	
	public StatisticsDTO(StatisticsDashboardDTO dash1DTO) {
		this.dash1DTO = dash1DTO;
	}


	public StatisticsDTO(StatisticsDashboardDTO dash1DTO,StatisticsVideoStatusDTO videostatus) {
		this.dash1DTO = dash1DTO;
		this.videostatus = videostatus;
	}

	
	public StatisticsDTO(StatisticsVideoStatusDTO videostatus) {
		this.videostatus = videostatus;
	}


	public StatisticsVideoStatusDTO getVideostatus() {
		return videostatus;
	}


	public void setVideostatus(StatisticsVideoStatusDTO videostatus) {
		this.videostatus = videostatus;
	}


	public StatisticsDashboardDTO getDash1DTO() {
		return dash1DTO;
	}


	public void setDash1DTO(StatisticsVideoDashboard1DTO dash1dto) {
		dash1DTO = dash1dto;
	}

}
