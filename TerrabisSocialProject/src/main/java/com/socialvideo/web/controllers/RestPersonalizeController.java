package com.socialvideo.web.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.socialvideo.constant.WebConstants;
import com.socialvideo.data.dto.AjaxPersonalizeDTO;
import com.socialvideo.data.dto.AjaxResultDTO;
import com.socialvideo.data.dto.AjaxVideoDTO;
import com.socialvideo.data.dto.CurrentUserDTO;
import com.socialvideo.data.dto.PersonalizationDTO;
import com.socialvideo.data.dto.VideoDTO;
import com.socialvideo.data.services.inter.IVideoService;;

@EnableAutoConfiguration
@RestController
@RequestMapping(value = "/rest-personalization")
@SessionAttributes({WebConstants.PERSONALIZATION})
public class RestPersonalizeController {

	 private static final Logger logger = Logger.getLogger(RestPersonalizeController.class);
	
	 @Autowired
	 protected IVideoService videoService;
	 
    public RestPersonalizeController() {
    }
    
    
    @RequestMapping(value="/google-map/type", method = RequestMethod.POST)
	public ResponseEntity<AjaxResultDTO> saveGoogleMapType(@RequestBody  AjaxPersonalizeDTO ajaxDTO, @ModelAttribute(WebConstants.PERSONALIZATION) PersonalizationDTO persDTO) {

    	persDTO.setGoogleMapType(ajaxDTO.getGoogleMapType());

	    ResponseEntity<AjaxResultDTO> response = new ResponseEntity<AjaxResultDTO>(new AjaxResultDTO( HttpStatus.OK, ajaxDTO.getGoogleMapType()), HttpStatus.OK);
	    return response;
    }

    
    @RequestMapping(value="/google-map/clusterer-mode", method = RequestMethod.POST)
	public ResponseEntity<AjaxResultDTO> saveMapClustererMode(@RequestBody  AjaxPersonalizeDTO ajaxDTO, @ModelAttribute(WebConstants.PERSONALIZATION) PersonalizationDTO persDTO) {

    	persDTO.setClustererMode(ajaxDTO.getClustererMode());

	    ResponseEntity<AjaxResultDTO> response = new ResponseEntity<AjaxResultDTO>(new AjaxResultDTO( HttpStatus.OK, ajaxDTO.getClustererMode().toString()), HttpStatus.OK);
	    return response;
    }
    

    @RequestMapping(value="/google-map/search-map-field", method = RequestMethod.POST)
	public ResponseEntity<AjaxResultDTO> saveMapSearchMapField(@RequestBody  AjaxPersonalizeDTO ajaxDTO, @ModelAttribute(WebConstants.PERSONALIZATION) PersonalizationDTO persDTO) {

    	persDTO.setSearchMapField(ajaxDTO.getSearchMapField());

	    ResponseEntity<AjaxResultDTO> response = new ResponseEntity<AjaxResultDTO>(new AjaxResultDTO( HttpStatus.OK, ajaxDTO.getSearchMapField().toString()), HttpStatus.OK);
	    return response;
    }

    
    
    
    
    
    @RequestMapping(value="/google-map/advert-shown", method = RequestMethod.POST)
	public ResponseEntity<AjaxResultDTO> advertShown(@RequestBody  AjaxPersonalizeDTO ajaxDTO, @ModelAttribute(WebConstants.PERSONALIZATION) PersonalizationDTO persDTO) {
    	
    	persDTO.getPreference().put(PersonalizationDTO.KEY_ADVERT_SHOWN+ajaxDTO.getAdvertType(), new Boolean(true));
	    ResponseEntity<AjaxResultDTO> response = new ResponseEntity<AjaxResultDTO>(new AjaxResultDTO( HttpStatus.OK, ajaxDTO.getAdvertType()), HttpStatus.OK);
	    return response;
    }
    
    
    
    
    
    
    
    
    
        
}