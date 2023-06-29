package com.microsoft.bot.msc.data.repository;

import java.util.List;

import com.microsoft.bot.msc.data.model.CorinthPOIDepartmentObject;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;



@SuppressWarnings("SpringDataMongoDBJsonFieldInspection")
@Repository

public interface CorinthPOIDepartmentRepository extends MongoRepository<CorinthPOIDepartmentObject, String> {

    @Query("{ 'Name' : { $regex: ?0, $options:'i' }}")
    List<CorinthPOIDepartmentObject>  findItemByName(String name);

    @Query("{$and : [{'Name': { $regex: ?0, $options:'i' }}, {'Type': { $regex: ?1, $options:'i' }}]}")
    List<CorinthPOIDepartmentObject>  findItemByNameAndType(String name, String type);

    @Query("{$and : [{'Name': { $regex: ?0, $options:'i' }}, {'Type': { $regex: ?1, $options:'i' }}, {'Location': { $regex: ?2, $options:'i' }}]}")
    List<CorinthPOIDepartmentObject>  findItemByNameAndTypeAndLocation(String name, String type,String location);

    @Query("{ 'Tags': { $in: [ { $regex: ?0, $options:'i' } ] } }")
    List<CorinthPOIDepartmentObject> findItemByTags(String name);


}