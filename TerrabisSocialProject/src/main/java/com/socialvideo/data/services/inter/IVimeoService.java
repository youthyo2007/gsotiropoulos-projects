package com.socialvideo.data.services.inter;

import com.socialvideo.data.dto.VimeoResponseWrapper;

public interface IVimeoService {

	public VimeoResponseWrapper vimeoUploadTicketRequest(String redirectUrl, String type, boolean upgrade_to_1080p);

	VimeoResponseWrapper vimeoEndUploadRequest(String completeURI);
	
	
	
	

}
