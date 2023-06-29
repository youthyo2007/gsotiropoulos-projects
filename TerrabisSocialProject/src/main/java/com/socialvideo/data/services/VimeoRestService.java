package com.socialvideo.data.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

import com.socialvideo.data.dto.VimeoUploadResponseDTO;
import com.socialvideo.data.dto.VimeoResponseWrapper;
import com.socialvideo.data.services.inter.IVimeoService;

@Service
public class VimeoRestService implements IVimeoService {
	 private static final String VIMEO_URI = "https://api.vimeo.com";
	 private static final String TOKEN = "cd5c98638ba3da13c789f7f2bfa947c9";
	 private static final String TOKEN_TYPE = "bearer";
	
/*	  @Autowired
	  private AsyncRestTemplate asyncRestTemplate;*/
	  
	  @Autowired
	  private RestTemplate restTemplate;
	  
	  
	  @Override
	  public VimeoResponseWrapper vimeoEndUploadRequest(String completeURI) {
		    HttpHeaders headers = new HttpHeaders();
	        headers.add("Accept", "application/vnd.vimeo.*+json; version=3.2");
	        headers.add("Authorization", new StringBuffer(TOKEN_TYPE).append(" ").append(TOKEN).toString());
	        HttpEntity<String> httpEntity = new HttpEntity<String>("parameters", headers);
	        
		    ResponseEntity<VimeoUploadResponseDTO> responseEntity  = restTemplate.exchange(VIMEO_URI+completeURI, HttpMethod.DELETE,httpEntity,VimeoUploadResponseDTO.class);
		    VimeoResponseWrapper response = new VimeoResponseWrapper();
		    response.setBody(responseEntity.getBody());
		    response.setHeaders(responseEntity.getHeaders());
		    response.setStatusCode(responseEntity.getStatusCode());
		   return response;
	  } 
	  
	  
	  @Override
	  public VimeoResponseWrapper vimeoUploadTicketRequest(String redirectUrl, String type, boolean upgrade_to_1080p) {
		    Map<String, String> params = new HashMap<String, String>();
	        params.put("redirect_url",redirectUrl);
		    params.put("type", type);
	        params.put("upgrade_to_1080", upgrade_to_1080p ? "true" : "false");
	        
	        HttpHeaders headers = new HttpHeaders();
	        headers.add("Accept", "application/vnd.vimeo.*+json; version=3.2");
	        headers.add("Authorization", new StringBuffer(TOKEN_TYPE).append(" ").append(TOKEN).toString());
	        HttpEntity<String> httpEntity = new HttpEntity<String>("parameters", headers);
	        
		    ResponseEntity<VimeoUploadResponseDTO> responseEntity  = restTemplate.exchange(VIMEO_URI+"/me/videos", HttpMethod.POST, httpEntity,VimeoUploadResponseDTO.class,params);
		    VimeoResponseWrapper response = new VimeoResponseWrapper();
		    response.setBody(responseEntity.getBody());
		    response.setHeaders(responseEntity.getHeaders());
		    response.setStatusCode(responseEntity.getStatusCode());
		   return response;
	  } 
	  
	  
	  
	  
      /*request.addHeader("Accept", "application/vnd.vimeo.*+json; version=3.2");
      request.addHeader("Authorization", new StringBuffer(tokenType).append(" ").append(token).toString());
      
	    public VimeoResponse vimeoUploadVideo(File file, String uploadLinkSecure) {
	        return apiRequest("/me/videos", HttpPost.METHOD_NAME, params, null);
	    }

	  
*/	  
}
