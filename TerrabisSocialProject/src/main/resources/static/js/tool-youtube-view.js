function checkIfDuplicateYoutubeID() {
	

	
	
			var callback = function(data, status, xhr) {
				var response = JSON.parse(data.responseText);
				var isvalid = response.valid;
				if(isvalid=='false') {
					console.log(terrabisApp.currentVideo.youtubeid+":exists");
					$("#videotd_"+terrabisApp.currentVideo.youtubeid).switchClass( "text-gray-more", "text-danger", 0, "easeInOutQuad" );
					$("#videotd_"+terrabisApp.currentVideo.youtubeid).switchClass( "text-info-dark", "text-danger", 0, "easeInOutQuad" );
					var uuid = response.videoid;
					//swal("YouTube Video Found!", "Video <b>["+currentVideo.id+"]</b> is already uploaded. Click <a href=\"http://www.terrabis.com/video"+uuid+"\">here</a> to view", "warning");
				}
				
				else {
					$("#videotd_"+terrabisApp.currentVideo.youtubeid).switchClass( "text-gray-more", "text-info-dark", 0, "easeInOutQuad" );
					$("#videotd_"+terrabisApp.currentVideo.youtubeid).switchClass( "text-danger", "text-info-dark", 0, "easeInOutQuad" );
				}
			};

			var formData = { videoid: terrabisApp.currentVideo.youtubeid};
			restajax(terrabisApp.validateDuplicateYoutubeURI,formData,callback);		
};