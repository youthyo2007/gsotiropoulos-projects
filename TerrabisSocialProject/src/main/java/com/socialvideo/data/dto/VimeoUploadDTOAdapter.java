package com.socialvideo.data.dto;

import java.util.concurrent.ExecutionException;

import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureAdapter;



public class VimeoUploadDTOAdapter extends ListenableFutureAdapter<VimeoResponseWrapper, ResponseEntity<VimeoUploadResponseDTO>> {

    protected VimeoUploadDTOAdapter(ListenableFuture<ResponseEntity<VimeoUploadResponseDTO>> adaptee) {
		super(adaptee);

	}

    
    @Override
    protected VimeoResponseWrapper adapt(ResponseEntity<VimeoUploadResponseDTO> responseEntity) throws ExecutionException {
    	VimeoUploadResponseDTO response = responseEntity.getBody();
    	VimeoResponseWrapper adaptedResponse = new VimeoResponseWrapper();
    	//adaptedResponse.setCompleteUri(response.getCompleteUri());
    	//adaptedResponse.setUri(response.getUri());
    	//adaptedResponse.setTicketId(response.getTicketId());
    	//adaptedResponse.setLinkSecure(response.getLinkSecure());
    	return adaptedResponse;
    }
    
    
    
}