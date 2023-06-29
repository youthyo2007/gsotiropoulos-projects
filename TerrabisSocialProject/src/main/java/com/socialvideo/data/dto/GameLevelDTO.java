package com.socialvideo.data.dto;

public class GameLevelDTO {
	
	
	private Integer level;
	private String name;
	private Integer start;
	private Integer end;
	private Integer levelProgress;
	
	
	
	
	
	public GameLevelDTO() {}
	
	
	public GameLevelDTO(Integer level, String name, Integer start, Integer end) {
		this.level = level;
		this.name = name;
		this.start = start;
		this.end = end;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Integer getStart() {
		return start;
	}


	public void setStart(Integer start) {
		this.start = start;
	}


	public Integer getEnd() {
		return end;
	}


	public void setEnd(Integer end) {
		this.end = end;
	}


	public Integer getLevelProgress() {
		return levelProgress;
	}


	public void setLevelProgress(Integer levelProgress) {
		this.levelProgress = levelProgress;
	}


	public Integer getLevel() {
		return level;
	}


	public void setLevel(Integer level) {
		this.level = level;
	}
	
	

}
