package com.socialvideo.web.controllers;

import org.apache.log4j.Logger;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleOAuthConstants;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.socialvideo.data.dto.CurrentUserDTO;
import com.socialvideo.data.services.GoogleAuthService;


@EnableAutoConfiguration
@Controller
@RequestMapping(value = "/oauth")

public class OAuthController {

	 private static final Logger logger = Logger.getLogger(OAuthController.class);

	 
	
	 
	 
    public OAuthController() {
    }
    

    @RequestMapping(value={"/youtube/terrabis/refresh-access-token"}, method=RequestMethod.GET)
    public String refreshOauthAccessToken() {
    	
    	
    	 String redirecturl = "";
    	 
    	 try {

    	        // init flow and load credential
    	        GoogleAuthorizationCodeFlow flow = GoogleAuthService.initYoutubeAuthorizationCodeFlow();
    	        Credential credential = flow.loadCredential(GoogleAuthService.TERRABIS_AUTH_USER);
   
    	        System.out.println("Before Refresh Expiration in s:"+credential.getExpiresInSeconds());
    	        
    	        System.out.println("Refresh Token Result:"+credential.refreshToken());
    	        
    	        System.out.println("After Refresh Expiration in s:"+credential.getExpiresInSeconds());
    	        
    	        	
    	        
    	        
    	 } catch (Exception e) {
    		 e.printStackTrace();
    		 
    	 }
 
    	 return redirecturl;
    }
    
    
    @RequestMapping(value={"/fusion/terrabis/readonly/refresh-access-token"}, method=RequestMethod.GET)
    public String refreshOauthFusionReadonlyAccessToken() {
    	
    	
    	 String redirecturl = "";
    	 
    	 try {

    	        // init flow and load credential
 	        	GoogleAuthorizationCodeFlow flow = GoogleAuthService.initFusionTablesReadOnlyFlow();
 	        	Credential credential = flow.loadCredential(GoogleAuthService.TERRABIS_FUSION_AUTH_READONLY_USER);
   
    	        System.out.println("Before Refresh Expiration in s:"+credential.getExpiresInSeconds());
    	        
    	        System.out.println("Refresh Token Result:"+credential.refreshToken());
    	        
    	        System.out.println("After Refresh Expiration in s:"+credential.getExpiresInSeconds());
    	        
    	        	
    	        
    	        
    	 } catch (Exception e) {
    		 e.printStackTrace();
    		 
    	 }
 
    	 return redirecturl;
    }
    
    
    //YOUTUBE-REQUEST-UPLOAD-ACCESS
    @RequestMapping(value={"/youtube/terrabis/request-upload-access"}, method=RequestMethod.GET)
    public String initializeYoutubeTerrabisAuth() {
    	
    	
    	 String redirecturl = "";
    	 
    	 try {

    	        // init flow and load credential
    	        GoogleAuthorizationCodeFlow flow = GoogleAuthService.initYoutubeAuthorizationCodeFlow();
    	        Credential credential = flow.loadCredential(GoogleAuthService.TERRABIS_AUTH_USER);
   
    	        if(credential==null) {
    	        	redirecturl = "redirect:"+flow.newAuthorizationUrl().setState("offline").setRedirectUri("http://www.terrabis.com/oauth/youtube/terrabis/oauthcallback").build();
    	        }
    	        
    	        else {
    	        	redirecturl = "redirect:/oauth/access-request-result?result=access-exists";
    	        	
    	        	
    	        }
    	        
    	        
    	 } catch (Exception e) {
    		 e.printStackTrace();
    		 
    	 }
 
    	 return redirecturl;
    }
    

    //FUISN TABLES READONLY
    @RequestMapping(value={"/fusion/terrabis/readonly/request-upload-access"}, method=RequestMethod.GET)
    public String initializeFusionTerrabisAuthReadOnly() {
    	
    	
    	 String redirecturl = "";
    	 
    	 try {
    		 
    	        // init flow and load credential
    	        GoogleAuthorizationCodeFlow flow = GoogleAuthService.initFusionTablesReadOnlyFlow();
    	        Credential credential = flow.loadCredential(GoogleAuthService.TERRABIS_FUSION_AUTH_READONLY_USER);
    	        String callBackURL = "http://www.terrabis.com/oauth/fusion/terrabis/readonly/oauthcallback";
    	        AuthorizationCodeRequestUrl  authorizationUrl = flow.newAuthorizationUrl().setRedirectUri(callBackURL).setApprovalPrompt("force").setAccessType("offline");
    	        redirecturl = "redirect:"+authorizationUrl.build();
    	        
    	 } catch (Exception e) {
    		 e.printStackTrace();
    		 
    	 }
 
    	 return redirecturl;
    }
    
    
    //FUSION-CALL-BACK
    @RequestMapping(value={"/fusion/terrabis/readonly/oauthcallback"}, method=RequestMethod.GET)
    public String FusionTerrabisAuthCalbackReadonly( @RequestParam(required=false) String error, @RequestParam(value="code",required=false) String authorizationCode) {
    	 String redirecturl = "";
    	 
    	 System.out.println(authorizationCode);
    	 
    	
    	 if(error!=null)
    		 redirecturl = "redirect:/oauth/access-request-result?result=access-denied";
    	 else {
    	
			   	 try {
			            // init flow and load credential
				        GoogleAuthorizationCodeFlow flow = GoogleAuthService.initFusionTablesReadOnlyFlow();
				        GoogleAuthorizationCodeTokenRequest tokenRequest = flow.newTokenRequest(authorizationCode);
				        tokenRequest.setRedirectUri("http://www.terrabis.com/oauth/fusion/terrabis/readonly/oauthcallback");
				        GoogleTokenResponse googleTokenResponse = tokenRequest.execute();
				        System.out.println("readonly accesstoken:"+googleTokenResponse.getAccessToken());
				        System.out.println("readonly refreshtoken:"+googleTokenResponse.getRefreshToken());
				        flow.createAndStoreCredential(googleTokenResponse,GoogleAuthService.TERRABIS_FUSION_AUTH_READONLY_USER);


			   	 
			   	 } catch (Exception e) {
					 e.printStackTrace();
					 
				 }
    	 }

    	 redirecturl = "redirect:/oauth/access-request-result?result=access-granded";
    	 
    	 return redirecturl;

    }
    
    
    /*//FUISN TABLES ADMIN
    @RequestMapping(value={"/fusion/terrabis/admin/request-upload-access"}, method=RequestMethod.GET)
    public String initializeFusionTerrabisAuthAdmin() {
    	
    	
    	 String redirecturl = "";
    	 
    	 try {

    	        // init flow and load credential
    	        GoogleAuthorizationCodeFlow flow = GoogleAuthService.initFusionTablesAdminFlow();
    	        Credential credential = flow.loadCredential(GoogleAuthService.TERRABIS_FUSION_AUTH_ADMIN_USER);
   
    	        if(credential==null) {
    	        	redirecturl = "redirect:"+flow.newAuthorizationUrl().setAccessType("offline").setRedirectUri("http://localhost:8080/oauth/fusion/terrabis/admin/oauthcallback").build();
    	        }
    	        
    	        else {
    	        	redirecturl = "redirect:/oauth/access-request-result?result=access-exists";
    	        	
    	        	
    	        }
    	        
    	        
    	 } catch (Exception e) {
    		 e.printStackTrace();
    		 
    	 }
 
    	 return redirecturl;
    }
    
    */
    
    

    
    
    /*
    //FUSION-CALL-BACK
    @RequestMapping(value={"/fusion/terrabis/admin/oauthcallback"}, method=RequestMethod.GET)
    public String FusionTerrabisAuthCalbackAdmin( @RequestParam(required=false) String error, @RequestParam(value="code",required=false) String authorizationCode) {
    	 String redirecturl = "";
    	
    	 if(error!=null)
    		 redirecturl = "redirect:/oauth/access-request-result?result=access-denied";
    	 else {
    	
			   	 try {
			            // init flow and load credential
				        GoogleAuthorizationCodeFlow flow = GoogleAuthService.initFusionTablesAdminFlow();
				        GoogleAuthorizationCodeTokenRequest tokenRequest = flow.newTokenRequest(authorizationCode);
				        tokenRequest.setRedirectUri("http://localhost:8080/oauth/fusion/terrabis/admin/oauthcallback");
				        GoogleTokenResponse googleTokenResponse = tokenRequest.execute();
				        System.out.println("admin accesstoken:"+googleTokenResponse.getAccessToken());
				        System.out.println("admin refreshtoken:"+googleTokenResponse.getRefreshToken());
				        flow.createAndStoreCredential(googleTokenResponse,GoogleAuthService.TERRABIS_FUSION_AUTH_ADMIN_USER);

			   	 
			   	 } catch (Exception e) {
					 e.printStackTrace();
					 
				 }
    	 }

    	 redirecturl = "redirect:/oauth/access-request-result?result=access-granded";
    	 
    	 return redirecturl;

    }
    */
    
   
    //YOUTUBE-CALL-BACK
    @RequestMapping(value={"/youtube/terrabis/oauthcallback"}, method=RequestMethod.GET)
    public String YoutubeTerrabisAuthCalback( @RequestParam(required=false) String error, @RequestParam(value="code",required=false) String authorizationCode) {
    	 String redirecturl = "";
    	
    	 if(error!=null)
    		 redirecturl = "redirect:/oauth/access-request-result?result=access-denied";
    	 else {
    	
			   	 try {
			            // init flow and load credential
				        GoogleAuthorizationCodeFlow flow = GoogleAuthService.initYoutubeAuthorizationCodeFlow();
				        GoogleAuthorizationCodeTokenRequest tokenRequest = flow.newTokenRequest(authorizationCode);
				        tokenRequest.setRedirectUri("http://www.terrabis.com/oauth/youtube/terrabis/oauthcallback");
				        GoogleTokenResponse googleTokenResponse = tokenRequest.execute();
				        System.out.println(googleTokenResponse.getAccessToken());
				        System.out.println(googleTokenResponse.getRefreshToken());
				        flow.createAndStoreCredential(googleTokenResponse,GoogleAuthService.TERRABIS_AUTH_USER);
				 
				        
				        
				        
				        
				        
				        //GoogleCredential credential = GoogleAuthService.initCredential();
				    	// Set authorized credentials.
				    	//credential.setFromTokenResponse(googleTokenResponse);
				    	// Though not necessary when first created, you can manually refresh the
				    	// token, which is needed after 60 minutes.
				    	//credential.refreshToken();

				    	
				        
				        
				        
				        
				        
				        
			   	 
			   	 } catch (Exception e) {
					 e.printStackTrace();
					 
				 }
    	 }

    	 redirecturl = "redirect:/oauth/access-request-result?result=access-granded";
    	 
    	 return redirecturl;

    }

    
}