function disabledEventPropagation(event)
{
   if (event.stopPropagation){
       event.stopPropagation();
   }
   else if(window.event){
      window.event.cancelBubble=true;
   }
}






function commentsHtml(reviewid, userid, avatar, username,comment,time) {
	var html =   ['<a class="list-group-item reviewitem -" id="reviewitem_'+reviewid +'" data-id="'+reviewid+'" data-userid="'+userid+'">',
	              '<div class="media-box">',
                     '<div class="pull-left">'+avatar+ '</div>',
                     '<div class="media-box-body clearfix">',
                        '<small class="pull-right">'+time+'</small>',
                        '<div class="text-center cursor-pointer hidden removereviewdiv" id="removereviewdiv_'+reviewid +'" data-id="'+reviewid+'" style="position:absolute; top:0px; right:5px; background-color: #555555;  width:30px; height:25px;">',
                        '<span class="text-sm-md text-white removereviewbtn">x</span>',
                        '</div>',
                        '<strong class="media-box-heading text-primary">',
                           '<span class="text-left"></span>'+username+'</strong>',
                        '<p class="mb-sm">',
                           '<small>'+comment+'</small>',
                        '</p>',
                     '</div>',
                  '</div>',	                
	                '</a>'].join('');  
	
	return html;
	
};





$(document).delegate('.reviewitem', 'mouseover', function() {

	var review = this.dataset;
	if(terrabisApp.currentUserID==review.userid) {
		$("#removereviewdiv_"+review.id).switchClass( "hidden", "-", 0, "easeInOutQuad" );
		
		
		
		
	}
		
	

});	

$(document).delegate('.reviewitem', 'mouseout', function() {

	var review = this.dataset;
	if(terrabisApp.currentUserID==review.userid) {
		$("#removereviewdiv_"+review.id).switchClass( "-", "hidden", 0, "easeInOutQuad" );
		
	}
	
	
	
	

});




$(document).delegate('.removereviewdiv', 'click', function(event) {
	var review = this.dataset;
	$("#reviewitem_"+review.id).fadeOut(1000);
	
	console.log(review);
	var formData = {reviewid:review.id};
	restajax(terrabisApp.reviewDeleteVideoURI,formData,callback);
	
	//DECREMENT COUNT
	terrabisApp.videoReviewsCount-=1;
	$(".videoreviewcount").html(terrabisApp.videoReviewsCount);
	
	var callback = function(data, status, xhr) {
		
	};
	

});	



	

$(document).delegate('.videoreviewtextarea', 'click', function() {
	$(".videoreviewbtnarea").switchClass( "hidden", "-", 0, "easeInOutQuad" );
});
	






$(document).delegate('.videoreviewcancelbtn', 'click', function() {
	$(".videoreviewtextarea").val("");
	$(".videoreviewbtnarea").switchClass( "-", "hidden", 0, "easeInOutQuad" );
	
});




$(document).delegate('.videoreviewsendbtn', 'click', function() {
		
	
	
	if(terrabisApp.isAuthenticated) {
		
		
		var reviewtext = $(".videoreviewtextarea").val();
		
	
		var callback = function(data, status, xhr) {
			var reviewid = JSON.parse(data.responseText).message;
			console.log(reviewid);
			var avatar  =  $(".avatar").html();
			var reviewtext = $(".videoreviewtextarea").val();
			var html = commentsHtml(reviewid, terrabisApp.currentUserID, avatar, terrabisApp.currentUserName,reviewtext,'1s');
			$(html).hide().prependTo(".videoreviewlist:first-child").fadeIn(1000);
			$(".videoreviewtextarea").val("");
			
		};
		

		if(reviewtext.length>0) {	

			var formData = { videoid: terrabisApp.currentVideo.id, reviewtext:reviewtext, time:clock.now};
			restajax(terrabisApp.reviewVideoURI,formData,callback);
			
			
			//INCREMENT COUNT
			terrabisApp.videoReviewsCount+=1;
			$(".videoreviewcount").html(terrabisApp.videoReviewsCount);
			
			
		}
		
		

		
	}
	else {
			swal("You need to sign-in", "Please sign-in to comment on this video!. Click <a href=\"http://www.terrabis.com/authenticate/signin\">here</a>", "warning");
	
	
	}
	
});	
	
	

	





/*
function createHtml_GridRelatedVideosResultView(videoListPartions) {
	
	
	
	var grid_html = "";

	
	
	for (var i=0; i<videoListPartions.length; i++) {
		var VIDEOLIST = videoListPartions[i];
		var videorow_html = "";
		var vid_html = "";
		for (var j=0; j<1; j++) {
			var vid = VIDEOLIST[j];
			vid_html=  createHtml_THUMB(vid);
			
			videorow_html='<div ID="ITERPOINT_ROW" class="row mt-lg">'+vid_html+'</div>';

			
			
			
			//APPEND VIDEO ROW 
			grid_html+=videorow_html;			
		}
		
	
	}
		
	console.log(grid_html);
	return grid_html; 

};*/









/**********************   FETCH RELATED VIDEOS  ********************************//*
function showRelatedVideosOfVideo(videoid,csrf_token) {

					var formData = { videoid: terrabisApp.currentVideo.id };
					$('#videoGrid').html('<span class="fa fa-2x fa-spinner fa-pulse"></span>');

	
				  
		        	  $.ajax({ 
		        		    url:  terrabisApp.videoRelationsDataURI, 
		        		    type: 'POST', 
		        		    dataType: 'json',
		        		    contentType: "application/json; charset=utf-8",
		        		    cache: false, // Force requested pages not to be cached by the browser
		        		    processData: false, // Avoid making query string instead of JSON		        		    
		        		    data: JSON.stringify(formData),
		        		    beforeSend: function (request)
    						{

        						request.setRequestHeader("X-CSRF-TOKEN", csrf_token);
    						},
		        		    success: function(data) { 
		        		    	var PARTITIONS = data.videolist;  
		        				if(PARTITIONS.length>0) {
		        					var gridHtml = createHtml_GridRelatedVideosResultView(PARTITIONS);
		        					$('#videoGrid').html(gridHtml);
		        					
		        				}
		        		    },
		        		    error: function (xhr, status, error) {
		        		    //$( "#loadingresults-map-overlay").switchClass( "-", "hidden", 0, "easeInOutQuad" );
		        		        console.log("showRelatedVideosOfVideo:ERROR:"+JSON.stringify(xhr));
		        		        console.log("showRelatedVideosOfVideo:ERROR:"+JSON.stringify(error));
		        		    }
		        		}); 
};
*/