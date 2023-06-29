package com.socialvideo.data.dto.vimeo;

import java.io.Serializable;

import org.json.JSONObject;

public class VimeoJsonResponseDTO implements Serializable {
	

	private static final long serialVersionUID = 1L;
	
	
	private JSONObject json;
    private int statusCode;

    public VimeoJsonResponseDTO(JSONObject json, int statusCode) {
        this.json = json;
        this.statusCode = statusCode;
    }

    public JSONObject getJson() {
        return json;
    }

    public int getStatusCode() {
        return statusCode;
    }
    
    
    

    public String toString() {
        return new StringBuffer("HTTP Status Code: \n").append(getStatusCode()).append("\nJson: \n").append(getJson().toString(2)).toString();
    }
}
