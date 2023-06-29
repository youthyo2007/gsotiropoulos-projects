package com.socialvideo.data.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.socialvideo.data.dto.AdminQueryDTO;
import com.socialvideo.data.dto.UserDTO;
import com.socialvideo.data.dto.UserDTOSmall;
import com.socialvideo.data.dto.VideoDTO;
import com.socialvideo.data.dto.statistics.BIQueryDTO;

public interface UserMapper {
	
	
	  public UserDTO selectUserWithAvatar(@Param("userid") Long userid);
	  public UserDTO selectUser(@Param("userid") Long userid);
	  public UserDTO selectAllUserList();
	  public UserDTO selectVideoDTO_User(@Param("userid_fk") Long userid_fk);
	  public UserDTO selectUserByUUID(@Param("uuidparam") String uuid);
	  public UserDTO fetchUserUponAuthentication(@Param("email") String email);
	  public List<String> findUserNames(@Param("username") String username,@Param("email") Integer limit);
	  public List<UserDTO> selectAdminCatalogUsers(@Param("QUERY") AdminQueryDTO queryDTO, @Param("offset") Integer offset,  @Param("limit") Integer limit);
	  public Integer selectCountAdminCatalogUsers(@Param("QUERY") AdminQueryDTO queryDTO);
	  public void updateLastLoginToNow(@Param("userid") Long userid);
	  public void assignRoleToUser(@Param("userid") Long userid, @Param("roleid") Long roleid);
	  public List<UserDTO> selectUsers(@Param("QUERY") BIQueryDTO queryDTO, @Param("offset") Integer offset,  @Param("limit") Integer limit);
	  public List<UserDTOSmall> selectFollowers(@Param("userid") Long userid);
	  public List<UserDTOSmall> selectFollowing(@Param("userid") Long userid);
	  public Integer selectCount_Followers(@Param("userid_fk") Long userid);
	  public Integer selectCount_Following(@Param("userid_fk") Long userid);
	  	  
	 
}
