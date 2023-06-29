package com.socialvideo.data.repositories;


import org.springframework.data.repository.CrudRepository;

import com.socialvideo.data.dto.UserDTO;
import com.socialvideo.data.model.VerificationTokenEntity;


public interface VerificationTokenRepository extends CrudRepository<VerificationTokenEntity, Long>  {

    VerificationTokenEntity findByToken(String token);
    VerificationTokenEntity findByUser(UserDTO user);

}
