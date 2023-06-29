package com.socialvideo.data.dto.statistics;

import java.io.Serializable;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
	
	
	
	
@JsonIgnoreProperties(ignoreUnknown = true)
public class BIChartDTO implements Serializable {

	private static final long serialVersionUID = 1L;


	@JsonProperty("column1")
	private String column1;

	
	@JsonProperty("columnvalue")
	private Integer columnvalue;
	

     public BIChartDTO(){}


     public BIChartDTO(String column1, Integer columnvalue){
    	 this.column1 =column1;
    	 this.columnvalue = columnvalue;
    	 
     }


	public String getColumn1() {
		return column1;
	}


	public void setColumn1(String column1) {
		this.column1 = column1;
	}


	public Integer getColumnvalue() {
		return columnvalue;
	}


	public void setColumnvalue(Integer columnvalue) {
		this.columnvalue = columnvalue;
	}
     
     
	
}
