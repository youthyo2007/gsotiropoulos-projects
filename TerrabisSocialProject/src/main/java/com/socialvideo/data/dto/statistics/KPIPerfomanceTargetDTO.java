package com.socialvideo.data.dto.statistics;

public class KPIPerfomanceTargetDTO {
	
	
	
	
	
	public KPIPerfomanceTargetDTO(){}
	
	
	public Double current;
	public Double target;
	public Double avg;
	public Double perfomance;
	public Boolean success;
	
	
	
	public KPIPerfomanceTargetDTO(Double current, Double target) {
		this.current = current;
		this.target = target;
		this.avg = (current/target)*100;
		this.perfomance = ((current-target)/target)*100;
		this.success = current-target > 0 ? true : false;
	}
	
	
	
	public Double getCurrent() {
		return current;
	}
	public void setCurrent(Double current) {
		this.current = current;
	}
	public Double getTarget() {
		return target;
	}
	public void setTarget(Double target) {
		this.target = target;
	}
	public Double getPerfomance() {
		return perfomance;
	}
	public void setPerfomance(Double perfomance) {
		this.perfomance = perfomance;
	}
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	
	
	
	
}
