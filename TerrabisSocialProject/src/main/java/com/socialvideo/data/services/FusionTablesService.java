
package com.socialvideo.data.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.services.fusiontables.Fusiontables;
import com.google.api.services.fusiontables.model.Sqlresponse;
import com.socialvideo.constant.GoogleConstants;
import com.socialvideo.constant.SysPropsConstants;
import com.socialvideo.data.dto.AdvancedFiltersDTO;
import com.socialvideo.data.dto.FusionTableResultDTO;
import com.socialvideo.data.dto.PublicVideoPointDTO;
import com.socialvideo.data.dto.maps.BoundsDTO;
import com.socialvideo.data.dto.maps.PointDTO;





@Service
public class  FusionTablesService  {
    
	
	
	

	@Autowired
	protected SystemPropertiesService systemPropertiesService;
	
	
	public FusionTableResultDTO findPointsOfViewPort(BoundsDTO bounds, PointDTO location,AdvancedFiltersDTO filters) throws Exception  {
		FusionTableResultDTO resultDTO = new FusionTableResultDTO();
		
		Integer maxViewPortPins =  Integer.parseInt(systemPropertiesService.selectSystemPropertyByKey(SysPropsConstants.SECURITY_VIEWPORT_FETCHPINS_SIZE).getValue());
		 
		Fusiontables fusionTableAPI =GoogleAuthService.initFusionTables("FusionTablesService-findPointsOfViewPort");
		String sqlStatement = "SELECT id,  UUID, title, description, latitude, longitude, markerimgid, shootingtypeid FROM "+GoogleConstants.TERRABIS_PUBLIC_FUSION_TABLE+"  WHERE status = 1 AND healthstatus = 1"+
		" AND ST_INTERSECTS(latitude, RECTANGLE(LATLNG("+bounds.getSouthwest().getLat()+", "+bounds.getSouthwest().getLng()+"), LATLNG("+bounds.getNortheast().getLat()+", "+bounds.getNortheast().getLat()+"))) LIMIT "+maxViewPortPins;
		
		//ORDER BY ST_DISTANCE(latitude, LATLNG("+location.getLat()+", "+location.getLng()+"))
		Sqlresponse response = fusionTableAPI.query().sql(sqlStatement).execute();
		List<List<Object>> rows= response.getRows();
		
		int resultTotal = rows.size();
		resultDTO.setTotal(resultTotal);
		int loopTotal = resultTotal > maxViewPortPins ? maxViewPortPins : resultTotal;
		for(int i=0; i<loopTotal; i++) {
			boolean filterOut = false;
			List<Object> currentRow = rows.get(i);
			PublicVideoPointDTO videoPointDTO = new PublicVideoPointDTO();
    		videoPointDTO.setID(currentRow.get(0).toString());
    		videoPointDTO.setUUID(currentRow.get(1).toString());
    		videoPointDTO.setTitle(currentRow.get(2).toString());
    		videoPointDTO.setDescription(currentRow.get(3).toString());
    		videoPointDTO.setLatitude(currentRow.get(4).toString());
    		videoPointDTO.setLongitude(currentRow.get(5).toString());
    		videoPointDTO.setMarkerimgid(currentRow.get(6).toString());
    		
    		
    		System.out.println("filters.getVideotypeid():"+filters.getVideotypeid());
    		System.out.println("filters.getShootingtypeid():"+filters.getShootingtypeid());
    		System.out.println("filters.getTags():"+filters.getTags());
    		
    		
    		
    		String currentVideoTypeId = currentRow.get(6).toString();
    		if(filters.getVideotypeid()!= null && filters.getVideotypeid().intValue()!=0) {
    			if(!filters.getVideotypeid().toString().equalsIgnoreCase(currentVideoTypeId))
    				filterOut = true;
    			
    		}
    		
    		
    		String currentShootingTypeId = currentRow.get(7).toString();
    		if(filters.getShootingtypeid()!= null && filters.getShootingtypeid().intValue()!=0) {
    			if(!filters.getShootingtypeid().toString().equalsIgnoreCase(currentShootingTypeId))
    				filterOut = true;
    			
    		}
    		
/*    		String currentTags = currentRow.get(8).toString();
    		if(filters.getTags()!= null && !filters.getTags().isEmpty()) {
    			if(!currentTags.contains(currentTags))
    				filterOut = true;
    			
    		}*/
    		
    		
    		System.out.println("filterOut:"+filterOut);
    		
    		
    		if(!filterOut)
    			resultDTO.getPoints().add(videoPointDTO);
		}

		
		return resultDTO;
	} 
	
	public FusionTableResultDTO findPointsOfRadious(PointDTO center, AdvancedFiltersDTO filters) throws Exception  {
		FusionTableResultDTO resultDTO = new FusionTableResultDTO();
		
		Integer maxViewPortPins =  Integer.parseInt(systemPropertiesService.selectSystemPropertyByKey(SysPropsConstants.SECURITY_VIEWPORT_FETCHPINS_SIZE).getValue());
		 
		Fusiontables fusionTableAPI =GoogleAuthService.initFusionTables("FusionTablesService-findPointsOfViewPort");
		String sqlStatement = "SELECT id,  UUID, title, description, latitude, longitude, markerimgid FROM "+GoogleConstants.TERRABIS_PUBLIC_FUSION_TABLE+"  WHERE status = 1 AND healthstatus = 1"+
		" AND ST_INTERSECTS(latitude, CIRCLE(LATLNG("+center.getLat()+", "+center.getLng()+"),5000000)) limit "+maxViewPortPins;
		
		Sqlresponse response = fusionTableAPI.query().sql(sqlStatement).execute();
		List<List<Object>> rows= response.getRows();
		
		int resultTotal = rows.size();
		resultDTO.setTotal(resultTotal);
		int loopTotal = resultTotal > maxViewPortPins ? maxViewPortPins : resultTotal;
		for(int i=0; i<loopTotal; i++) {
			List<Object> currentRow = rows.get(i);
			PublicVideoPointDTO videoPointDTO = new PublicVideoPointDTO();
    		videoPointDTO.setID(currentRow.get(0).toString());
    		videoPointDTO.setUUID(currentRow.get(1).toString());
    		videoPointDTO.setTitle(currentRow.get(2).toString());
    		videoPointDTO.setDescription(currentRow.get(3).toString());
    		videoPointDTO.setLatitude(currentRow.get(4).toString());
    		videoPointDTO.setLongitude(currentRow.get(5).toString());
    		videoPointDTO.setMarkerimgid(currentRow.get(6).toString());
    		resultDTO.getPoints().add(videoPointDTO);
		}

		
		return resultDTO;
	} 
	
	

	
	
	
		
		
}
		

		
		

