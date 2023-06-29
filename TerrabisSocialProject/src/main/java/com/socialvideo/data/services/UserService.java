package com.socialvideo.data.services;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.connect.UserProfile;
import org.springframework.stereotype.Service;

import com.socialvideo.constant.ConstantRoleNames;
import com.socialvideo.constant.NotificationSettingStatus;
import com.socialvideo.data.dto.AdminQueryDTO;
import com.socialvideo.data.dto.PaginatorDTO;
import com.socialvideo.data.dto.PasswordResetTokenDTO;
import com.socialvideo.data.dto.UserDTO;
import com.socialvideo.data.dto.UserDTOSmall;
import com.socialvideo.data.dto.VerificationTokenDTO;
import com.socialvideo.data.dto.statistics.BIQueryDTO;
import com.socialvideo.data.mappers.UserMapper;
import com.socialvideo.data.model.PasswordResetTokenEntity;
import com.socialvideo.data.model.RoleEntity;
import com.socialvideo.data.model.UserEntity;
import com.socialvideo.data.model.VerificationTokenEntity;
import com.socialvideo.data.repositories.PasswordResetTokenRepository;
import com.socialvideo.data.repositories.RoleRepository;
import com.socialvideo.data.repositories.UserRepository;
import com.socialvideo.data.repositories.VerificationTokenRepository;
import com.socialvideo.data.services.inter.IUserService;

@Service
public class UserService implements   IUserService {
    

	
	@Autowired
	private CollectionService collectionService;

	@Autowired
	private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

	@Autowired
	private UserMapper userMapper;

	@Autowired
    private PasswordEncoder encoder;

	@Autowired
    private VerificationTokenRepository tokenRepository;

    @Autowired
    private PasswordResetTokenRepository passwordTokenRepository;
    

	 @Autowired
	 protected NotificationsService nottsService;


    @Override
    public UserDTO selectUser(Long userid) {
    return userMapper.selectUser(userid);	
    }

    @Override
    public UserDTO selectUserWithAvatar(Long userid) {
    	 return userMapper.selectUserWithAvatar(userid);	
    }
	  
    
    
    @Override
    public List<UserDTO> selectUsers(BIQueryDTO queryDTO, PaginatorDTO paginator) {
    return userMapper.selectUsers(queryDTO,paginator.getOffset(),paginator.getLimit());	
    }
        
    
    
    
    @Override
    public UserDTO selectUserByUUID(String UUID) {
    return userMapper.selectUserByUUID(UUID);	
    }
        
    
    
    
    
    
    @Override
    public UserDTO registerNewUserAccount(UserDTO accountDTO) {
        //GET BASIC ROLE
        RoleEntity userBasicRole = roleRepository.findByName(ConstantRoleNames.ROLEUSER);
        

        //CREATE A HIBERNATE PERSISTENT OBJECT AND LOAD IT WITH REGISTRATION FORM DATA
        UserEntity userEntity = new UserEntity();
        userEntity.LOAD(accountDTO);
        
        
		userEntity.setPassword(encoder.encode(accountDTO.getPassword()));
		userEntity.setRoles(Arrays.asList(userBasicRole));
		userEntity.setUUID(UUID.randomUUID().toString().replace("-", ""));
		userEntity.setEnabled(false);
        userEntity.setActive(true);
        userEntity.setEmailvalidated(false);
        userEntity = userRepository.save(userEntity);
        
        return userEntity.DTO();
    }

    
    @Override
    public UserDTO saveBasicUserAccount(UserDTO accountDTO) {
    	
    	
        //GET BASIC ROLE
        RoleEntity userBasicRole = roleRepository.findByName(ConstantRoleNames.ROLEUSER);
        
        
        //CREATE A HIBERNATE PERSISTENT OBJECT AND LOAD IT WITH REGISTRATION FORM DATA
        UserEntity userEntity = new UserEntity();
        userEntity.LOAD(accountDTO);
    	userEntity.setRoles(Arrays.asList(userBasicRole));
        userEntity = userRepository.save(userEntity);

        return userEntity.DTO();
    }
    
    
    @Override
    public UserDTO loadUserFromSocialProfile(UserProfile profile) {
    	UserDTO userDTO = findUserByEmail(profile.getEmail());
    	
    	
    	
    	//AUTO CREATE USER FROM FACEBOOK PROFILE
    	if(userDTO==null) {
    		
            //GET BASIC ROLE
            RoleEntity userBasicRole = roleRepository.findByName(ConstantRoleNames.ROLEUSER);
    		
    		UserDTO accountDTO = new UserDTO();
        	accountDTO.setEmail(profile.getEmail());
        	accountDTO.setPassword(RandomStringUtils.randomAlphanumeric(8));
        	accountDTO.setUsername(profile.getEmail().substring(0,profile.getEmail().indexOf('@')));

        	UserEntity userEntity = new UserEntity();
            userEntity.LOAD(accountDTO);
             
             
     		userEntity.setPassword(encoder.encode(accountDTO.getPassword()));
     		userEntity.setRoles(Arrays.asList(userBasicRole));
     		userEntity.setUUID(UUID.randomUUID().toString().replace("-", ""));
     		userEntity.setEnabled(true);
            userEntity.setActive(true);
            userEntity.setEmailvalidated(true);
            userEntity = userRepository.save(userEntity);
     
            userDTO = findUserByEmail(profile.getEmail());
            
            //INITIALISE COLLECTIONS
            collectionService.initUserCollectionsUponRegistration(userDTO.getId());
            
            //INITIALISE NOTIFICATIONS
            nottsService.createInitialNotificationSettings(userDTO.getId(),NotificationSettingStatus.EMAIL);
            

            
    	}
    	
    	return userDTO;
    }

    
    @Override
    public UserDTO getUserByVerificationToken(String verificationToken) {
    	UserEntity userEntity = tokenRepository.findByToken(verificationToken).getUser();
    	
        return userEntity!=null ? userEntity.DTO() : null;
    }

    @Override
    public VerificationTokenDTO getVerificationToken(String verificationToken) {
    	VerificationTokenEntity verTokenEntity =  tokenRepository.findByToken(verificationToken);
        return verTokenEntity!=null ? verTokenEntity.DTO() : null;
        
        
    }

    @Override
    public void updateUser(UserDTO userDTO) {
    	UserEntity userEntity = userRepository.findOne(userDTO.getId());
    	userEntity.LOAD(userDTO);
        userRepository.save(userEntity);
    }

    @Override
    public void enableUser(Long userID) {
    	UserEntity userEntity = userRepository.findOne(userID);
    	userEntity.setEnabled(true);
        userRepository.save(userEntity);
    }

    @Override
    public void disableUser(Long userID) {
    	UserEntity userEntity = userRepository.findOne(userID);
    	userEntity.setEnabled(false);
        userRepository.save(userEntity);
    }
    
    @Override
    public void enableUser(UserDTO userDTO) {
    	UserEntity userEntity = userRepository.findOne(userDTO.getId());
    	userEntity.setEnabled(true);
        userRepository.save(userEntity);
    }

    @Override
    public void confirmUserUponRegistration(UserDTO userDTO) {
    	UserEntity userEntity = userRepository.findOne(userDTO.getId());
    	userEntity.setEnabled(true);
    	userEntity.setEmailvalidated(true);
    	userRepository.save(userEntity);
    }
    
    
    @Override
    public void updateLastLoginToNow(Long userid) {
    	userMapper.updateLastLoginToNow(userid);
    }
	  
    
    
    
    @Override
    public void disableUser(UserDTO userDTO) {
    	UserEntity userEntity = userRepository.findOne(userDTO.getId());
    	userEntity.setEnabled(false);
        userRepository.save(userEntity);
    }
    
    @Override
    public void updateCalc(UserDTO userDTO) {
    	UserEntity userEntity = userRepository.findOne(userDTO.getId());
    	userEntity.LOADCALC(userDTO);
        userRepository.save(userEntity);
    }
    
    

    @Override
    public void deleteUser(UserDTO userDTO) {
    	UserEntity userEntity = userRepository.findOne(userDTO.getId());
    	userRepository.delete(userEntity);
    }

    @Override
    public void createVerificationTokenForUser(UserDTO userDTO,String token) {
    	VerificationTokenEntity vEntity = new VerificationTokenEntity(token);
    	vEntity.setUser(userRepository.findOne(userDTO.getId()));
        tokenRepository.save(vEntity);
        
    }

    
    
    @Override
    public VerificationTokenDTO generateNewVerificationToken(String existingVerificationToken) {
        VerificationTokenEntity vToken = tokenRepository.findByToken(existingVerificationToken);
        vToken.updateToken(UUID.randomUUID().toString());
        vToken = tokenRepository.save(vToken);
        return vToken.DTO();
    }

    @Override
    public void createPasswordResetTokenForUser(UserDTO userDTO,String token) {
        
    	PasswordResetTokenEntity pEntity = new PasswordResetTokenEntity(token);
    	pEntity.setUser(userRepository.findOne(userDTO.getId()));
    	passwordTokenRepository.save(pEntity);
    }

    @Override
    public UserDTO fetchUserUponAuthentication(String email) {
    	UserDTO userDTO = userMapper.fetchUserUponAuthentication(email);
        return userDTO;

        
    }
    
    @Override
    public UserDTO findUserById(Long userid) {
    	 UserEntity userEntity =  userRepository.findOne(userid);
    	 return userEntity!=null ? userEntity.DTO() : null;
    }
    
	@Override
	public UserDTO findUserByUUID(String UUID) {
		  UserEntity userEntity =  userRepository.findByUUID(UUID);
		   return userEntity!=null ? userEntity.DTO() : null;
		  
	}
    
    

    @Override
    public UserDTO findUserByUsername(String username) {
    	 UserEntity userEntity =  userRepository.findByUsername(username);
    	 return userEntity!=null ? userEntity.DTO() : null;
    }
    
    @Override
    public PasswordResetTokenDTO getPasswordResetToken(String token) {
    	PasswordResetTokenEntity tokenEntity = passwordTokenRepository.findByToken(token);
    	return tokenEntity!=null ? tokenEntity.DTO() : null;

        
        
    }

    @Override
    public UserDTO getUserByPasswordResetToken(String token) {
    	PasswordResetTokenEntity tokenEntity = passwordTokenRepository.findByToken(token);
    	UserEntity userEntity = null;
    	if(tokenEntity!=null)
    		  userEntity =  tokenEntity.getUser();
    	
    	
    	return userEntity!=null ? userEntity.DTO() : null;
    }

    @Override
    public UserDTO getUserByID(long id) {
        UserEntity userEntity =  userRepository.findOne(id);
        return userEntity!=null ? userEntity.DTO() : null;
    }

    
    
    
    @Override
    public void changeUserPassword(Long userid, String password) {
    	String encodedPassword = encoder.encode(password);
    	UserEntity userEntity = userRepository.findOne(userid);
    	userEntity.setPassword(encodedPassword);
    	userRepository.save(userEntity);
    }
    
    
    @Override
    public boolean checkIfValidOldPassword( UserDTO user,  String oldPassword) {
        return encoder.matches(oldPassword, user.getPassword());
    }

    private boolean emailExist(String email) {
    	UserEntity user = userRepository.findByEmail(email);
    	
        if (user != null) {
            return true;
        }
        return false;
    }

	public List<String> findUserNames(String username, int limit) {
		return userMapper.findUserNames(username,limit);
	}

	
	
	
	
	@Override
	public List<UserDTO> selectAdminCatalogUsers(AdminQueryDTO queryDTO, PaginatorDTO paginator) {
		return userMapper.selectAdminCatalogUsers(queryDTO,paginator.getOffset(),paginator.getLimit());
	}
	
	
	@Override
	public Integer selectCountAdminCatalogUsers(AdminQueryDTO queryDTO) {
		return userMapper.selectCountAdminCatalogUsers(queryDTO);
	}

	@Override
	public UserDTO findUserByEmail(String email) {
		UserEntity user = userRepository.findByEmail(email);
		return user!=null ? user.DTO() : null;
	}


	@Override
	public void assignRoleToUser(Long userid, Long roleid) {
		userMapper.assignRoleToUser(userid, roleid);
		
	}

	@Override
	public List<UserDTOSmall> selectFollowers(Long userid) {
		return 	userMapper.selectFollowers(userid);
	}

	@Override
	public List<UserDTOSmall> selectFollowing(Long userid) {
		return 	userMapper.selectFollowing(userid);
	}

	@Override
	public Integer selectFollowersCount(Long userid) {
		return 	userMapper.selectCount_Followers(userid);
	}

	@Override
	public Integer selectFollowingCount(Long userid) {
		return userMapper.selectCount_Following(userid);
	}


	

}