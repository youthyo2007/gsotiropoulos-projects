package com.socialvideo.data.dto.statistics;

import java.io.Serializable;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


	
@JsonIgnoreProperties(ignoreUnknown = true)
public class BIChartMultiLineDTO implements Serializable {

	private static final long serialVersionUID = 1L;


	@JsonProperty("xcolumn")
	private String xcolumn;

	
	@JsonProperty("value1")
	private Integer value1;

	
	@JsonProperty("value2")
	private Integer value2;


	@JsonProperty("value3")
	private Integer value3;
	
	
	@JsonProperty("value4")
	private Integer value4;
	
	
	@JsonProperty("value5")
	private Integer value5;
	
	
     public BIChartMultiLineDTO(){}


     public BIChartMultiLineDTO(String xcolumn, Integer value1,Integer value2){
    	 this.xcolumn = xcolumn;
    	 this.value1 =value1;
    	 this.value2 = value2;
     }


	public String getXcolumn() {
		return xcolumn;
	}


	public void setXcolumn(String xcolumn) {
		this.xcolumn = xcolumn;
	}


	public Integer getValue1() {
		return value1;
	}


	public void setValue1(Integer value1) {
		this.value1 = value1;
	}


	public Integer getValue2() {
		return value2;
	}


	public void setValue2(Integer value2) {
		this.value2 = value2;
	}


	public Integer getValue3() {
		return value3;
	}


	public void setValue3(Integer value3) {
		this.value3 = value3;
	}


	public Integer getValue4() {
		return value4;
	}


	public void setValue4(Integer value4) {
		this.value4 = value4;
	}


	public Integer getValue5() {
		return value5;
	}


	public void setValue5(Integer value5) {
		this.value5 = value5;
	}

	
}
