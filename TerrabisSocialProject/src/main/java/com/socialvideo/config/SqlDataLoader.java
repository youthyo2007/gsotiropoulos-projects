package com.socialvideo.config;

import java.util.Arrays;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.socialvideo.data.model.CollectionEntity;
import com.socialvideo.data.model.RoleEntity;
import com.socialvideo.data.model.UserEntity;
import com.socialvideo.data.repositories.CollectionRepository;
import com.socialvideo.data.repositories.RoleRepository;
import com.socialvideo.data.repositories.UserRepository;
import com.socialvideo.data.services.VideoTask;

@Component
public class SqlDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = true;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    @Autowired
    private CollectionRepository colRepository;


    @Autowired
    private PasswordEncoder encoder;
    
    
    @Autowired
    private VideoTask videoTask;
    
    
    

    // API

    @Override
    @Transactional
    public void onApplicationEvent(final ContextRefreshedEvent event) {
/*        if (alreadySetup) {
        	//videoTask.BATCH_integrateDataFromVimeoYoutube();
        	return;
        }
        
        
        
        //CREATE INITIAL ROLES
         createRoleIfNotFound(ConstantRoleNames.ROLEADMIN);
         createRoleIfNotFound(ConstantRoleNames.ROLEUSER);

         
         //FIND ROLES
         RoleEntity adminRole = roleRepository.findByName(ConstantRoleNames.ROLEADMIN);
         RoleEntity userRole = roleRepository.findByName(ConstantRoleNames.ROLEUSER);
        
         
         //CREATE INITIAL USERS
         createUserIfNotFound("gsotiropoulos2007@gmail.com","-",userRole);
         createUserIfNotFound("george1sotiropoulos@gmail.com","-",adminRole);
         createUserIfNotFound("ligerakis@hotmail.com","-",adminRole);
         createUserIfNotFound("gallis@novatron.gr","-",userRole);
         
        //CREATE INITIAL WATCH LATER COLLECTION 
         createUserCollectionIfNotFound("gsotiropoulos2007@gmail.com","WATCH LATER","A collection of videos to watch in the nearest future",CollectionTypes.WATCHLATER,VideoPrivacyStatus.PUBLIC);
         createUserCollectionIfNotFound("gsotiropoulos2007@gmail.com","FAVORITES BUCKET","A collection of videos to add to playlists in the nearest future",CollectionTypes.FAVORITESBUCKET,VideoPrivacyStatus.PUBLIC);
         createUserCollectionIfNotFound("ligerakis@hotmail.com","WATCH LATER","A collection of videos to watch in the nearest future",CollectionTypes.WATCHLATER,VideoPrivacyStatus.PUBLIC);
         createUserCollectionIfNotFound("ligerakis@hotmail.com","FAVORITES BUCKET","A collection of videos to add to playlists in the nearest future",CollectionTypes.FAVORITESBUCKET,VideoPrivacyStatus.PUBLIC);
         createUserCollectionIfNotFound("gallis@novatron.gr","WATCH LATER","A collection of videos to watch in the nearest future",CollectionTypes.WATCHLATER,VideoPrivacyStatus.PUBLIC);
         createUserCollectionIfNotFound("gallis@novatron.gr","FAVORITES BUCKET","A collection of videos to add to playlists in the nearest future",CollectionTypes.FAVORITESBUCKET,VideoPrivacyStatus.PUBLIC);
         
         
        alreadySetup = true;*/
    }

    
    @Transactional
    private  void createUserIfNotFound(String email, String password, RoleEntity roleEntity) {
    	UserEntity userEntity = userRepository.findByEmail(email);
    	if(userEntity==null) {
    		userEntity = new UserEntity();
    		userEntity.setEmail(email);
    		userEntity.setPassword(encoder.encode(password));
    		userEntity.setRoles(Arrays.asList(roleEntity));
    		userEntity.setUUID(UUID.randomUUID().toString().replace("-", ""));
    		userEntity.setEnabled(true);
    		userRepository.save(userEntity);
    	}
    }


    
    @Transactional
    private  CollectionEntity createUserCollectionIfNotFound(String email, String title, String description, Integer type, Integer privacy) {
    	CollectionEntity entity = null;
    	UserEntity userEntity = userRepository.findByEmail(email);
    	if(userEntity!=null) {
    	
    		entity = colRepository.findByTypeAndUserId(userEntity.getId(), type);
	        if (entity == null) {
	        	entity = new CollectionEntity(userEntity.getId(),title,description,type,privacy);
	        	colRepository.save(entity);
	        }
    	}
        return entity;
    }
    
    
    
    @Transactional
    private  RoleEntity createRoleIfNotFound(final String name) {
        RoleEntity roleEntity = roleRepository.findByName(name);
        if (roleEntity == null) {
        	roleEntity = new RoleEntity(name);

            roleRepository.save(roleEntity);
        }
        return roleEntity;
    }

}