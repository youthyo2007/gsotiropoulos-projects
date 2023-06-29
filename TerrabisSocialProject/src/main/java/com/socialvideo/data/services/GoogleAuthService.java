package com.socialvideo.data.services;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.CredentialRefreshListener;
import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.auth.oauth2.TokenErrorResponse;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.fusiontables.Fusiontables;
import com.google.api.services.fusiontables.FusiontablesScopes;
import com.google.api.services.youtube.YouTubeScopes;
import com.google.common.collect.Lists;
import com.socialvideo.constant.GoogleConstants;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

/**
 * Shared class used by every sample. Contains methods for authorizing a user and caching credentials.
 */

@Service
public class GoogleAuthService {

    /**
     * Define a global instance of the HTTP transport.
     */
    public static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

    /**
     * Define a global instance of the JSON factory.
     */
    public static final JsonFactory JSON_FACTORY = new JacksonFactory();

    /**
     * This is the directory that will be used under the user's home directory where OAuth tokens will be stored.
     */
    public static final String CREDENTIALS_DIRECTORY = "oauth-credentials";
    public static final String TERRABIS_AUTH_USER = "terrabisoauthusr";
    public static final String TERRABIS_FUSION_AUTH_READONLY_USER = "fusiontbl_readonlyusr";
    public static final String TERRABIS_FUSION_AUTH_ADMIN_USER = "_fusiontbl_adminusr";
    public static final String YOUTUBE_ADMIN_CREDENTIALS_FILE = "youtube";
    public static final String FUSION_READONLY_CREDENTIALS_FILE = "fusiontable_readonly";    
    public static final String FUSION_ADMIN_CREDENTIALS_FILE = "fusiontable_admin";    
    
    
    
    public static Fusiontables initFusionTables(String applicationName) throws IOException {

    	 GoogleAuthorizationCodeFlow flow = GoogleAuthService.initFusionTablesReadOnlyFlow();
	     Credential credential = flow.loadCredential(GoogleAuthService.TERRABIS_FUSION_AUTH_READONLY_USER);
	    
	     if(credential!=null) {
 			Long expiresInSeconds = credential.getExpiresInSeconds();  
 			System.out.println("token expires in:"+expiresInSeconds);
 			if(expiresInSeconds<60) {
 				//	OUTPUT THAT TOKEN EXPIRES IN 60 SECONDS
 				System.out.println("token expires in 60 seconds");
 				
 				//REFRESH TOKEN
 	  	    	boolean tokenRefreshed = credential.refreshToken();    				
 				if(!tokenRefreshed)
 					System.out.println("failed to refresh token");
 				else
 		  	    	System.out.println("token successfully refreshed ok");
 					
 				
 			}
	     }
	     
	     
	     Fusiontables fusiontables = new Fusiontables.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential).setApplicationName(applicationName).build(); 
	     
	     return fusiontables;
   	
    }
    
    
    
    public static GoogleAuthorizationCodeFlow initYoutubeAuthorizationCodeFlow() throws IOException {

        Resource credentialsdirectory = new ClassPathResource(CREDENTIALS_DIRECTORY);

        
    	
   	 	List<String> scopes = Lists.newArrayList(YouTubeScopes.YOUTUBE_UPLOAD, YouTubeScopes.YOUTUBE_FORCE_SSL,YouTubeScopes.YOUTUBE);
    	       
        // This creates the credentials datastore at ~/.oauth-credentials/${credentialDatastore}
        FileDataStoreFactory fileDataStoreFactory = new FileDataStoreFactory(credentialsdirectory.getFile());
        DataStore<StoredCredential> datastore = fileDataStoreFactory.getDataStore(YOUTUBE_ADMIN_CREDENTIALS_FILE);

        
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, GoogleConstants.OAUTH_YOUTUBE_CLIENT_ID, GoogleConstants.OAUTH_YOUTUBE_CLIENT_SECRET, scopes).setCredentialDataStore(datastore).setAccessType("offline")
                .build();


        
        
        return flow;
        

    }

    
    
    

    
    
    
    
    
/*    public static GoogleAuthorizationCodeFlow initFusionTablesAdminFlow() throws IOException {

        Resource credentialsdirectory = new ClassPathResource(CREDENTIALS_DIRECTORY);

        
    	
   	 	List<String> scopes = Lists.newArrayList(FusiontablesScopes.FUSIONTABLES);
    	       
        // This creates the credentials datastore at ~/.oauth-credentials/${credentialDatastore}
        FileDataStoreFactory fileDataStoreFactory = new FileDataStoreFactory(credentialsdirectory.getFile());
        DataStore<StoredCredential> datastore = fileDataStoreFactory.getDataStore(FUSION_ADMIN_CREDENTIALS_FILE);


        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, GoogleConstants.OAUTH_FUSIONTABLE_CLIENT_ID, GoogleConstants.OAUTH_FUSIONTABLE_CLIENT_SECRET, scopes).setCredentialDataStore(datastore).setAccessType("offline")
                .build();

        
        return flow;
        

    }*/
    
    
    
    public static GoogleAuthorizationCodeFlow initFusionTablesReadOnlyFlow() throws IOException {

        Resource credentialsdirectory = new ClassPathResource(CREDENTIALS_DIRECTORY);

        
    	
   	 	List<String> scopes = Lists.newArrayList(FusiontablesScopes.FUSIONTABLES_READONLY);
    	       
        // This creates the credentials datastore at ~/.oauth-credentials/${credentialDatastore}
        FileDataStoreFactory fileDataStoreFactory = new FileDataStoreFactory(credentialsdirectory.getFile());
        DataStore<StoredCredential> datastore = fileDataStoreFactory.getDataStore(FUSION_READONLY_CREDENTIALS_FILE);


        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, GoogleConstants.OAUTH_FUSIONTABLE_CLIENT_ID, GoogleConstants.OAUTH_FUSIONTABLE_CLIENT_SECRET, scopes).setCredentialDataStore(datastore).setAccessType("offline")
                .build();


        
        return flow;
        

    }
    
    
    
    public static GoogleCredential initCredential() throws IOException {
    	
    	 //List<String> scopes = Lists.newArrayList(YouTubeScopes.YOUTUBE_UPLOAD);
    	
        //FileDataStoreFactory fileDataStoreFactory = new FileDataStoreFactory(new File(CREDENTIALS_DIRECTORY));
        //DataStore<StoredCredential> datastore = fileDataStoreFactory.getDataStore(YOUTUBE_CREDENTIALS_DIRECTORY);


    	
    	GoogleCredential credential = new GoogleCredential.Builder()
    		    .setTransport(HTTP_TRANSPORT)
    		    .setJsonFactory(JSON_FACTORY)
    		    .setClientSecrets(GoogleConstants.OAUTH_YOUTUBE_CLIENT_ID,  GoogleConstants.OAUTH_YOUTUBE_CLIENT_SECRET)
    		    .addRefreshListener(new CredentialRefreshListener() {
    		      @Override
    		      public void onTokenResponse(Credential credential, TokenResponse tokenResponse) {
    		        // Handle success.
    		        System.out.println("Credential was refreshed successfully.");
    		      }

    		      @Override
    		      public void onTokenErrorResponse(Credential credential,
    		          TokenErrorResponse tokenErrorResponse) {
    		        // Handle error.
    		        System.err.println("Credential was not refreshed successfully. "
    		            + "Redirect to error page or login screen.");
    		      }
    		    })
    		    // You can also add a credential store listener to have credentials
    		    // stored automatically.
    		    //.addRefreshListener(new CredentialStoreRefreshListener(userId, credentialStore))
    		    .build();
    	
 
    	return credential;
           

       }
    
    
    
    
    
    /**
     * Authorizes the installed application to access user's protected data.
     *
     * @param scopes              list of scopes needed to run youtube upload.
     * @param credentialDatastore name of the credential datastore to cache OAuth tokens
     */
    public static Credential authorizeCmdLine(List<String> scopes, String credentialDatastore) throws IOException {

        // Load client secrets.
        Reader clientSecretReader = new InputStreamReader(GoogleAuthService.class.getResourceAsStream("/client_secrets.json"));
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, clientSecretReader);

        // Checks that the defaults have been replaced (Default = "Enter X here").
        if (clientSecrets.getDetails().getClientId().startsWith("Enter")
                || clientSecrets.getDetails().getClientSecret().startsWith("Enter ")) {
            System.out.println(
                    "Enter Client ID and Secret from https://console.developers.google.com/project/_/apiui/credential "
                            + "into src/main/resources/client_secrets.json");
            System.exit(1);
        }

        // This creates the credentials datastore at ~/.oauth-credentials/${credentialDatastore}
        FileDataStoreFactory fileDataStoreFactory = new FileDataStoreFactory(new File(CREDENTIALS_DIRECTORY));
        DataStore<StoredCredential> datastore = fileDataStoreFactory.getDataStore(credentialDatastore);

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, scopes).setCredentialDataStore(datastore)
                .build();


        // Build the local server and bind it to port 8080
        LocalServerReceiver localReceiver = new LocalServerReceiver.Builder().setPort(8080).build();

        // Authorize.
        return new AuthorizationCodeInstalledApp(flow, localReceiver).authorize("user");
    }
}