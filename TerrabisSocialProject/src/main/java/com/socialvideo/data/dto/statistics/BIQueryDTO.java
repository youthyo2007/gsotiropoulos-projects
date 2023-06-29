package com.socialvideo.data.dto.statistics;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)

public class BIQueryDTO implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	
	private String userid = "";
	private String dateid = "";
	private Integer status = null;
	
	private Boolean filteron = false;
	
	
	private String dateCreatedFromString = "";
    public String getDateCreatedFromString() {
		return dateCreatedFromString;
	}
    
    
    public  BIQueryDTO() {
    }
  

    public  BIQueryDTO(Integer status) {
    	this.status = status;
    }
  
    
    
    
    public void todayTommowFilter() {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            setDateCreatedFromString(dateFormat.format(cal.getTime()));
            cal.add(Calendar.DATE, +1);
            setDateCreatedToString(dateFormat.format(cal.getTime()));
    }

    
    public void thisMonthFilter() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        setDateCreatedToString(dateFormat.format(cal.getTime()));
        cal.set(Calendar.DAY_OF_MONTH, 1);
        setDateCreatedFromString(dateFormat.format(cal.getTime()));
    }

    
    public void previousMonthFilter() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        setDateCreatedFromString(dateFormat.format(cal.getTime()));
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        setDateCreatedToString(dateFormat.format(cal.getTime()));
    }

    
    
    
    
    
    

    
    
    public  BIQueryDTO(String dateCreatedFrom, String dateCreatedTo) {
    	this.dateCreatedFromString = dateCreatedFrom;
    	this.dateCreatedToString = dateCreatedTo;
    }
    
    
    
    public Boolean getFilteron() {
		boolean result = false;
		if((dateCreatedFromString!=null)&& (dateCreatedFromString.trim().length()>0))
			result =true;
		if((dateCreatedToString!=null)&& (dateCreatedToString.trim().length()>0))
			result =true;

		this.filteron = result;
		
		return result;
		
	}
    
    
    
    public  BIQueryDTO(String dateid) {
    	this.dateid = dateid;
    }
    
	public void setDateCreatedFromString(String dateCreatedFromString) {
		this.dateCreatedFromString = dateCreatedFromString;
	}
	public String getDateCreatedToString() {
		return dateCreatedToString;
	}
	public void setDateCreatedToString(String dateCreatedToString) {
		this.dateCreatedToString = dateCreatedToString;
	}
	public Date getDateCreatedFrom() {
		return dateCreatedFrom;
	}
	public void setDateCreatedFrom(Date dateCreatedFrom) {
		this.dateCreatedFrom = dateCreatedFrom;
	}
	public Date getDateCreatedTo() {
		return dateCreatedTo;
	}
	public void setDateCreatedTo(Date dateCreatedTo) {
		this.dateCreatedTo = dateCreatedTo;
	}
	private String dateCreatedToString;
    
	private Date dateCreatedFrom;
    private Date dateCreatedTo;	
    
    
    
    
    
    
    public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getDateid() {
		return dateid;
	}
	public void setDateid(String dateid) {
		this.dateid = dateid;
	}


	public void setFilteron(Boolean filteron) {
		this.filteron = filteron;
	}


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}
	
    
    
    
    
    
    
}
