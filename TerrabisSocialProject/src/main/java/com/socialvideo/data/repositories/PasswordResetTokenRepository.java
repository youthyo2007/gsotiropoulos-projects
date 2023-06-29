package com.socialvideo.data.repositories;

import org.springframework.data.repository.CrudRepository;

import com.socialvideo.data.model.PasswordResetTokenEntity;

public interface PasswordResetTokenRepository extends CrudRepository<PasswordResetTokenEntity, Long>  {

	public PasswordResetTokenEntity findByToken(String token);

	
}
