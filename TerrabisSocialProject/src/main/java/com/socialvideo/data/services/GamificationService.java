package com.socialvideo.data.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.socialvideo.constant.GamificationRewards;
import com.socialvideo.data.dto.GameLevelDTO;
import com.socialvideo.data.dto.UserDTO;
import com.socialvideo.data.dto.mobile.MobileUserDTO;
import com.socialvideo.utilities.WebUtility;

@Service
public class GamificationService {
	
	public GameLevelDTO findGameLevel(UserDTO userDTO) {
		GameLevelDTO gameLevel = null;
	
		
		List<GameLevelDTO> levelList = WebUtility.gameLevels();
		
	
		//CALCULATE TOTAL POINTS
		Integer totalPoints = 	userDTO.getVideoscount()*GamificationRewards.VIDEOS +
								userDTO.getLikescount()*GamificationRewards.VIDEOLIKES +
								userDTO.getPlayscount()*GamificationRewards.VIDEOPLAYS +
								userDTO.getPlaylistscount()*GamificationRewards.PLAYLISTS +
								userDTO.getAlbumscount()*GamificationRewards.ALBUMS +
								userDTO.getTweetscount()*GamificationRewards.VIDEOSHARES;
		

		
		//FIND CURRENT LEVEL
		for (GameLevelDTO curlevel : levelList) {
		    if(totalPoints>=curlevel.getStart() && totalPoints<=curlevel.getEnd())
		    	gameLevel = curlevel;
		}

		Integer levelProgress = 100*(totalPoints - gameLevel.getStart())/(gameLevel.getEnd() - gameLevel.getStart());
		gameLevel.setLevelProgress(levelProgress);
		return gameLevel;
	}


	
	public GameLevelDTO findGameLevel(MobileUserDTO userDTO) {
		GameLevelDTO gameLevel = null;
	
		
		List<GameLevelDTO> levelList = WebUtility.gameLevels();
		
	
		//CALCULATE TOTAL POINTS
		Integer totalPoints = 	userDTO.getVideoscount()*GamificationRewards.VIDEOS +
								userDTO.getLikescount()*GamificationRewards.VIDEOLIKES +
								userDTO.getPlayscount()*GamificationRewards.VIDEOPLAYS +
								userDTO.getPlaylistscount()*GamificationRewards.PLAYLISTS +
								userDTO.getAlbumscount()*GamificationRewards.ALBUMS +
								userDTO.getTweetscount()*GamificationRewards.VIDEOSHARES;
		

		
		//FIND CURRENT LEVEL
		for (GameLevelDTO curlevel : levelList) {
		    if(totalPoints>=curlevel.getStart() && totalPoints<=curlevel.getEnd())
		    	gameLevel = curlevel;
		}

		Integer levelProgress = 100*(totalPoints - gameLevel.getStart())/(gameLevel.getEnd() - gameLevel.getStart());
		gameLevel.setLevelProgress(levelProgress);
		return gameLevel;
	}


	
	
	
	
}
