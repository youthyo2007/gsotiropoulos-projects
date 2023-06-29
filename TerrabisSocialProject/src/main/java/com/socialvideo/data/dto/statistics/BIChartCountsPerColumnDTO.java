package com.socialvideo.data.dto.statistics;

import java.io.Serializable;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
	
	
	
	
@JsonIgnoreProperties(ignoreUnknown = true)
public class BIChartCountsPerColumnDTO implements Serializable {

	private static final long serialVersionUID = 1L;


	@JsonProperty("columnDesc")
	private String columnDesc;
    
	
	@JsonProperty("count")
	private int count;
	

     public BIChartCountsPerColumnDTO(){}


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
     * @return the columnDesc
     */
    public String getColumnDesc() {
        return columnDesc;
    }

    /**
     * @param columnDesc the columnDesc to set
     */
    public void setColumnDesc(String columnDesc) {
        this.columnDesc = columnDesc;
    }
	
}
