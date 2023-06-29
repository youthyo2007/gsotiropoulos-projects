package com.socialvideo.data.repositories;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.socialvideo.data.model.StatisticsDayActivityEntity;



public interface StatisticsDayActivityRepository extends CrudRepository<StatisticsDayActivityEntity, Long>  {

	

	 @Query("select c from StatisticsDayActivityEntity c where c.dateid = :dateid")
	 StatisticsDayActivityEntity findDayActivityByDateId(@Param("dateid") Long dateid);
	 	 
}
