package com.socialvideo.web.listeners;

import com.amazonaws.event.ProgressEvent;
import com.amazonaws.services.s3.transfer.PersistableTransfer;
import com.amazonaws.services.s3.transfer.internal.S3ProgressListener;

public class MyS3ProgressListener implements S3ProgressListener{

	@Override
	public void progressChanged(ProgressEvent event) {
		System.out.println("Transfer From S3 Total Bytes Received:"+event.getBytes());
		
	}

	@Override
	public void onPersistableTransfer(PersistableTransfer event) {
		System.out.println("Transfer From S3 Started");
		
	}

}
