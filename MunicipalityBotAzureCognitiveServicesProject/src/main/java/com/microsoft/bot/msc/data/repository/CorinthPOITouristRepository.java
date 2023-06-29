package com.microsoft.bot.msc.data.repository;

import com.microsoft.bot.msc.data.model.CorinthPOITouristObject;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@SuppressWarnings("SpringDataMongoDBJsonFieldInspection")
@Repository
public interface CorinthPOITouristRepository extends MongoRepository<CorinthPOITouristObject, String> {



    @Query("{ 'Name' : { $regex: ?0, $options:'i' }}")
    List<CorinthPOITouristObject> findItemByName(String name);

    @Query("{$and : [{'Name': { $regex: ?0, $options:'i' }}, { Type: { $in: [ { $regex: ?1, $options:'i' }]}} ]}")
    List<CorinthPOITouristObject> findItemByNameAndType(String name, String type);


    @Query("{$and : [{'Name': { $regex: ?0, $options:'i' }}, { Type: { $in: [ { $regex: ?1, $options:'i' }]}}, {'Address.CityEn': { $regex: ?2, $options:'i' }}]}")
    List<CorinthPOITouristObject> findItemByNameAndTypeAndLocation(String name, String type,String location);

    @Query("{$and : [ { Type: { $in: [ { $regex: ?0, $options:'i' }]}}, { Cuisine: { $in: [ { $regex: ?1, $options:'i' }]}}, { Amenities: { $in: [ { $regex: ?2, $options:'i' }]}}]}")
    List<CorinthPOITouristObject> findItemByTypeCusineAmenitiesAndLocation(String type, String cuisine, String amenities, String location);
    @Query("{$and : [ { Type: { $in: [ { $regex: ?0, $options:'i' }]}}, { Cuisine: { $in: [ { $regex: ?1, $options:'i' }]}}, { Amenities: { $in: [ { $regex: ?2, $options:'i' }]}}]}")
    List<CorinthPOITouristObject> findItemByTypeCusineAndAmenities(String type, String cuisine, String amenities);

    @Query("{$and : [{ Type: { $in: [ { $regex: ?0, $options:'i' }]}}, { Cuisine: { $in: [ { $regex: ?1, $options:'i' }]}}, {'Address.CityEn': { $regex: ?2, $options:'i' }} ]}")
    List<CorinthPOITouristObject> findItemByTypeCusineAndLocation(String type, String cuisine, String location);

    @Query("{$and : [{ Type: { $in: [ { $regex: ?0, $options:'i' }]}}, { Cuisine: { $in: [ { $regex: ?1, $options:'i' }]}} ]}")
    List<CorinthPOITouristObject> findItemByTypeAndCusine(String type, String cuisine);

    @Query("{$and : [{ Type: { $in: [ { $regex: ?0, $options:'i' }]}}, {'Address.CityEn': { $regex: ?1, $options:'i' }}]}")
    List<CorinthPOITouristObject> findItemByTypeAndLocation(String type, String location);

    @Query("{$and : [{ Cuisine: { $in: [ { $regex: ?0, $options:'i' }]}}, {'Address.CityEn': { $regex: ?1, $options:'i' }}]}")
    List<CorinthPOITouristObject> findItemByCuisineAndLocation(String cuisine, String location);

    @Query("{ Type: { $in: [ { $regex: ?0, $options:'i' }]}}")
    List<CorinthPOITouristObject> findItemByType(String type);

    @Query("{ Cuisine: { $in: [ { $regex: ?0, $options:'i' }]}}")
    List<CorinthPOITouristObject> findItemByCuisine(String cuisine);

}