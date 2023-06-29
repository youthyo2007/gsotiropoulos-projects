package com.socialvideo.web.propertyeditors;

import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.List;

import com.socialvideo.data.dto.TagDTO;

public class TagsEditor extends PropertyEditorSupport {


    @Override
    // Converts a comma separated String of tagNames to a List<Tag> (when submitting form)
    public void setAsText(String stringOfTagNames) {
        List<TagDTO> tagsList = new ArrayList<TagDTO>();

        String[] tagNames = stringOfTagNames.split(",");
        for(String tagName : tagNames) {
        	tagsList.add(new TagDTO(tagName));
        }       

        this.setValue(tagsList);
    }
}
