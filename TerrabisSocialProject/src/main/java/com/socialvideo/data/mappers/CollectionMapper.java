package com.socialvideo.data.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.socialvideo.data.dto.CollectionDTO;
import com.socialvideo.data.dto.PaginatorDTO;


public interface CollectionMapper {
	
	  
	  
	public List<CollectionDTO> selectCollections(@Param("userid") Long userid, @Param("type") Integer collectiontype, @Param("title") String collectiontitle,  @Param("privacy") Integer privacy,@Param("limit") Integer limit, @Param("filter") String filter);
	public List<CollectionDTO> selectCollectionsEmpty(@Param("userid") Long userid, @Param("type") Integer collectiontype, @Param("title") String collectiontitle,  @Param("privacy") Integer privacy,@Param("limit") Integer limit, @Param("filter") String filter);
	public CollectionDTO selectOne(@Param("id") Long id);
	public CollectionDTO selectOneEmpty(@Param("id") Long id);
	public void removeCollection(@Param("collectionid") Long collectionid, @Param("userid_fk") Long userid);
	public void editCollection(CollectionDTO newcollection);
	public void updatePathSort(@Param("collectionid") Long collectionid, @Param("videoid") Long videoid, @Param("sortindex") int sortindex);
	public Integer selectCountCollections(@Param("type") Integer collectiontype);
	public List<CollectionDTO> selectPaginatedCollections(@Param("userid") Long userid,  @Param("type") Integer collectiontype, @Param("offset") Integer offset,  @Param("limit") Integer limit);

	
	 
}
