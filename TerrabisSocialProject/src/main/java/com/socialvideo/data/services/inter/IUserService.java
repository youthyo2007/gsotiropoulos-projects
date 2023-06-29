package com.socialvideo.data.services.inter;

import java.util.List;

import org.springframework.social.connect.UserProfile;

import com.socialvideo.data.dto.AdminQueryDTO;
import com.socialvideo.data.dto.PaginatorDTO;
import com.socialvideo.data.dto.PasswordResetTokenDTO;
import com.socialvideo.data.dto.UserDTO;
import com.socialvideo.data.dto.UserDTOSmall;
import com.socialvideo.data.dto.VerificationTokenDTO;
import com.socialvideo.data.dto.statistics.BIQueryDTO;


public interface IUserService  {

	
	
	public List<UserDTO> selectUsers(BIQueryDTO queryDTO, PaginatorDTO paginator);

	public List<UserDTO> selectAdminCatalogUsers(AdminQueryDTO queryDTO, PaginatorDTO paginator);
	
	public Integer selectCountAdminCatalogUsers(AdminQueryDTO queryDTO);

    UserDTO getUserByVerificationToken(String verificationToken);

    void updateUser(UserDTO user);
    
    void assignRoleToUser(Long userid, Long roleid);
    
    void updateCalc(UserDTO user);
    
    void deleteUser(UserDTO user);

    void createVerificationTokenForUser(UserDTO user, String token);

    VerificationTokenDTO getVerificationToken(String verificationToken);

    VerificationTokenDTO generateNewVerificationToken(String token);

    void createPasswordResetTokenForUser(UserDTO user, String token);

   // UserDTO findUserByEmail(String email);

    UserDTO findUserByUsername(String username);
    
    UserDTO findUserById(Long userid);
    
    PasswordResetTokenDTO getPasswordResetToken(String token);

    UserDTO getUserByPasswordResetToken(String token);

    UserDTO getUserByID(long id);
    
    UserDTO selectUser(Long id);
    
    UserDTO findUserByUUID(String UUIDUser);
    
    void changeUserPassword(Long userid, String password);

    boolean checkIfValidOldPassword(UserDTO user, String password);
	UserDTO selectUserByUUID(String UUID);

	UserDTO fetchUserUponAuthentication(String email);

	void enableUser(UserDTO userDTO);
	void disableUser(UserDTO userDTO);

	void confirmUserUponRegistration(UserDTO userDTO);

	UserDTO registerNewUserAccount(UserDTO accountDTO);

	void enableUser(Long userID);

	void disableUser(Long userID);

	void updateLastLoginToNow(Long userid);

	public UserDTO findUserByEmail(String email);
	public UserDTO loadUserFromSocialProfile(UserProfile profile);
	
	
	 public UserDTO saveBasicUserAccount(UserDTO accountDTO);

	public UserDTO selectUserWithAvatar(Long userid);

	public List<UserDTOSmall> selectFollowing(Long userid);
	public List<UserDTOSmall> selectFollowers(Long userid);
	
	public Integer selectFollowersCount(Long userid);
	public Integer  selectFollowingCount(Long userid);


	
	


}
