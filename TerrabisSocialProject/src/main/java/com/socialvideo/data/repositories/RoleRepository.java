package com.socialvideo.data.repositories;


import org.springframework.data.repository.CrudRepository;

import com.socialvideo.data.model.RoleEntity;


public interface RoleRepository extends CrudRepository<RoleEntity, Long>  {

	
	/**
	 * Find the role with the given name. This method will be translated into a query using the
	 * {@link javax.persistence.NamedQuery} annotation at the {@link RoleEntity} class.
	 * 
	 * @param name
	 * @return
	 */
	 RoleEntity findByName(String name);

}
