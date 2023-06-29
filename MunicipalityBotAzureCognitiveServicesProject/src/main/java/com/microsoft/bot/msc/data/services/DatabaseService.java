package com.microsoft.bot.msc.data.services;

import java.util.List;


import com.microsoft.bot.msc.data.model.CorinthPOIDepartmentObject;
import com.microsoft.bot.msc.data.model.CorinthPOITouristObject;
import com.microsoft.bot.msc.data.repository.CorinthPOIDepartmentRepository;
import com.microsoft.bot.msc.data.repository.CorinthPOITouristRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatabaseService {


    @Autowired
    CorinthPOIDepartmentRepository departmentRepository;

    @Autowired
    CorinthPOITouristRepository touristRepository;



    public CorinthPOIDepartmentObject getDepartmentItemByNameTypeLocation(String name, String type, String location) {
        List<CorinthPOIDepartmentObject> item = null;
        if((name!=null) && (type!=null) && (location!=null))
                item = departmentRepository.findItemByNameAndTypeAndLocation(name,type,location);
        else if((name!=null) && (type!=null))
            item = departmentRepository.findItemByNameAndType(name,type);
        else if((name!=null) )
            item = departmentRepository.findItemByName(name);

        //IN CASE ITEM LIST IS NULL THEN DO A SECOND SEARCH ON TAGS LIST
        if(((item==null) || (item.isEmpty())) && (name!=null))
            item = departmentRepository.findItemByTags(name);


        if ((item!=null) && (!item.isEmpty()))
            return item.get(0);
        else return null;
    }



    public CorinthPOITouristObject getTouristItemByNameTypeLocation(String name, String type, String cuisine, String amenities, String location) {
        List<CorinthPOITouristObject> item = null;
        if((name!=null) && (type!=null) && (location!=null))
            item = touristRepository.findItemByNameAndTypeAndLocation(name,type,location);
        else if((name!=null) && (type!=null))
            item = touristRepository.findItemByNameAndType(name,type);
        else if((name!=null) )
            item = touristRepository.findItemByName(name);
        else if((type!=null) && (cuisine!=null) && (amenities!=null) && (location!=null))
            item = touristRepository.findItemByTypeCusineAmenitiesAndLocation(type,cuisine,amenities, location);
        else if((type!=null) && (cuisine!=null)  && (amenities!=null))
            item = touristRepository.findItemByTypeCusineAndAmenities(type,cuisine,amenities);
        else if((type!=null) && (cuisine!=null) && (location!=null))
            item = touristRepository.findItemByTypeCusineAndLocation(type,cuisine,location);
        else if((type!=null) && (cuisine!=null))
            item = touristRepository.findItemByTypeAndCusine(type,cuisine);
        else if((type!=null) && (location!=null))
            item = touristRepository.findItemByTypeAndLocation(type,location);
        else if((cuisine!=null) && (location!=null))
            item = touristRepository.findItemByCuisineAndLocation(cuisine,location);
        else if((cuisine!=null))
            item = touristRepository.findItemByCuisine(cuisine);
        else if((type!=null))
            item = touristRepository.findItemByType(type);


        if ((item!=null) && (!item.isEmpty()))
            return item.get(0);
        else return null;
    }


}
