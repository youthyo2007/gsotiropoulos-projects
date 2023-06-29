package com.socialvideo.web.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Joiner;
import com.socialvideo.data.dto.AjaxGenericDTO;
import com.socialvideo.data.dto.TagDTO;
import com.socialvideo.data.services.DatabaseService;
import com.socialvideo.data.services.UserService;
import com.socialvideo.data.services.inter.IVideoService;;

@EnableAutoConfiguration
@RestController
@RequestMapping(value = "/rest-data-fetch")
public class RestDataFetchController {

	 private static final Logger logger = Logger.getLogger(RestDataFetchController.class);

	 @Autowired
	 protected DatabaseService dbService;

	 @Autowired
	 protected IVideoService videoService;

	 
	 @Autowired
	 protected UserService userService;
	 
    public RestDataFetchController() {
    }


    @RequestMapping(value="/tags", method = RequestMethod.POST)
    public ResponseEntity<List<TagDTO>> fetchTagList(@RequestBody AjaxGenericDTO ajaxDTO) {
    
    	ResponseEntity<List<TagDTO>> response = null;


    	
    	//GET TAG LIST
    	List<TagDTO> tagList = dbService.findTags(ajaxDTO.getTagtext()+"%", 20);

    	
    	return new ResponseEntity<List<TagDTO>>(tagList, HttpStatus.OK);
    }
    
    

/*    @RequestMapping(value="/autocomplete/video/title", method = RequestMethod.POST)
    public ResponseEntity<String> fetchAutocompleteVideoTitles(@RequestBody AjaxGenericDTO ajaxDTO) {
  
    	ResponseEntity<List<String>> response = null;
    	
    	//GET VIDEO TITLES 
    	List<String> videoList = videoService.findVideoTitles(ajaxDTO.getAutocompletetext()+"%", 20);
    	String autocompleteString = videoList.parallelStream().map(x-> "{ value: '"+x+"', data: '"+x+"' }").collect(Collectors.joining(", "));
    	return new ResponseEntity<String>(autocompleteString, HttpStatus.OK);
    }
    
    
	    @RequestMapping(value = "autocomplete/user/username", method = RequestMethod.GET)
	    public @ResponseBody List<String> fetchAutocompleteUserNames(@RequestParam("searchTerm") String searchTerm) {
	        return userService.findUserNames(searchTerm+"%", 20);
	    }*/
    
    
    
/*    @RequestMapping(value="/autocomplete/user/username", method = RequestMethod.POST)
    public ResponseEntity<String> fetchAutocompleteUserNames(@RequestBody AjaxGenericDTO ajaxDTO) {
  
    	ResponseEntity<List<String>> response = null;
    	
    	//GET VIDEO TITLES 
    	List<String> videoList = userService.findUserNames(ajaxDTO.getAutocompletetext()+"%", 20);
    	String autocompleteString = videoList.parallelStream().map(x-> "{ value: '"+x+"', data: '"+x+"' }").collect(Collectors.joining(", "));
    	return new ResponseEntity<String>(autocompleteString, HttpStatus.OK);
    }
*/
    
    
    
    
    
    

    @RequestMapping(value="/tags-as-string", method = RequestMethod.POST)
    public ResponseEntity<String> fetchTagListAsString(@RequestBody AjaxGenericDTO ajaxDTO) {
    
    	ResponseEntity<String> response = null;


    	
    	//GET TAG LIST
    	List<String> tagList = dbService.findTagsAsString(ajaxDTO.getTagtext()+"%", 20);

    	
    	//GOOGLE GUAVA 
    	Joiner joiner = Joiner.on( "," ).skipNulls();
    	String tagsString = joiner.join( tagList );
    	
    	return new ResponseEntity<String>(tagsString, HttpStatus.OK);
    }
    
    
 
    
}
    
    	
    	