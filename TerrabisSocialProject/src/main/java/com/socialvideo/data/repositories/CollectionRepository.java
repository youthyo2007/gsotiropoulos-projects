package com.socialvideo.data.repositories;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.socialvideo.data.model.CollectionEntity;


public interface CollectionRepository extends CrudRepository<CollectionEntity, Long>  {

	
	/**
	 * Find the collection with the given title. This method will be translated into a query using the
	 * {@link javax.persistence.NamedQuery} annotation at the {@link ColectionEntity} class.
	 * 
	 * @param name
	 * @return
	 */
	 CollectionEntity findByTitle(String title);
	 
	 @Query("select t from CollectionEntity t where t.type = :type and t.userid = :userid")
	CollectionEntity findByTypeAndUserId(@Param("userid") Long userid,@Param("type") Integer type);

	 

}
