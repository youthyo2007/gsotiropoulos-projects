var selectedItems = new Array();
var blockMouseOver = false;


//SET GRID VIEW STYLE
function initializeAdminGridViewControls(gridviewstyle) {
		$("#gridviewstyle-control-BOX").switchClass("fa-2x animated animated-repeat flash text-info0", "-",0, "easeInOutQuad" );
		$("#gridviewstyle-control-THUMB").switchClass("fa-2x animated animated-repeat flash text-info0", "-",0, "easeInOutQuad" );
		$("#gridviewstyle-control-DETAIL").switchClass("fa-2x animated animated-repeat flash text-info0", "-",0, "easeInOutQuad" );
		$("#gridviewstyle-control-ANALYTIC").switchClass("fa-2x animated animated-repeat flash text-info0", "-",0, "easeInOutQuad" );
		
	
		
		if(gridviewstyle=='BOX') {
			$("#gridviewstyle-control-BOX").switchClass( "-", "fa-2x animated animated-repeat flash text-info0", 0, "easeInOutQuad" );
			
		}
		
		if(gridviewstyle=='THUMB')
			$("#gridviewstyle-control-THUMB").switchClass( "-", "fa-2x animated animated-repeat flash text-info0", 0, "easeInOutQuad" );
		
		if(gridviewstyle=='DETAIL')
			$("#gridviewstyle-control-DETAIL").switchClass( "-", "fa-2x animated animated-repeat flash text-info0", 0, "easeInOutQuad" );	
		
		
		if(gridviewstyle=='ANALYTIC')
			$("#gridviewstyle-control-DETAIL").switchClass( "-", "fa-2x animated animated-repeat flash text-info0", 0, "easeInOutQuad" );
		
		
};








//ADMIN MENU SIDEBAR FILTERS AND SUBMIT BUTTON
$(".uploadradiobox input[type='radio']").click(function() {

	 $('#advfilter\\.uploaddateany').val($(".uploadradiobox input[value='any']").is(':checked'));
	 $('#advfilter\\.uploaddatetoday').val($(".uploadradiobox input[value='today']").is(':checked'));
	 $('#advfilter\\.uploaddateyesterday').val($(".uploadradiobox input[value='yesterday']").is(':checked'));
	 
	 
	
});





//CREATE PAGINATOR
function createHtml_Paginator(paginator) {
	

	var pageNumbersList = paginator.pageNumbersList;
	
	
	$('.paginator').html("");
	var html = '';
	
	
			html = '<table style="border-collapse: separate; border-spacing: 2px;"><tr>';
			for (var i=0; i<pageNumbersList.length; i++) {
				if(pageNumbersList[i].pageno==(paginator.currentPage+1))
					html = html + '<td class="bg-info-dark" align="center" style="width: 30px;"><span class="text-bold">'+pageNumbersList[i].pageno+'</span></td>';
				else if((pageNumbersList[i].pageno=='AFTER_ONE'))
					html = html + '<td class="" align="center" style="width: 30px;"><span class="text-bold">&nbsp;&nbsp;&nbsp;</span></td>';
				else if((pageNumbersList[i].pageno=='DOTS'))
					html = html + '<td class="bg-gray" align="center" style="width: 30px;"><span class="text-bold">...</span></td>';
				else 
					html = html + '<td class="bg-gray" align="center" style="width: 30px;"><span class="text-bold"><a href="?page='+pageNumbersList[i].pageno+'">'+pageNumbersList[i].pageno+'</a></span></td>';
			}
			html = html +  '</tr></table>';

			$('.paginator').html(html);
	
}


function createHtml_EmptyPaginator() {
	$('.paginator').html("");
	var html = '<li class="active"><i class=""></i><span class="">no videos found...</span></li>';
	$('.paginator').html(html);
}


function updateFormsVideoSelectValue(videoid,value) {
	$("#deletevideosform input[name='selectedvideos["+videoid+"]']").val(value);
	$("#upublishvideosform input[name='selectedvideos["+videoid+"]']").val(value);
	$("#publishvideosform input[name='selectedvideos["+videoid+"]']").val(value);
	$("#rejectvideosform input[name='selectedvideos["+videoid+"]']").val(value);			
	$("#approvevideosform input[name='selectedvideos["+videoid+"]']").val(value);
	$("#promotesiteindexvideosform input[name='selectedvideos["+videoid+"]']").val(value);
	$("#unpromotesiteindexvideosform input[name='selectedvideos["+videoid+"]']").val(value);
	$("#promotemapvideosform input[name='selectedvideos["+videoid+"]']").val(value);
	$("#unpromotemapvideosform input[name='selectedvideos["+videoid+"]']").val(value);
};



function isVideoSelected(videoid) {
	
	
	
	
	var isSelected = false;
	var selectedIndex = -1;
	for(i=0; i<selectedItems.length; i++) {
		/*if((selectedItems[i].id==videoid) && (selectedItems[i].collectionid==collectionid)) {*/
		if(selectedItems[i]==videoid) {
			isSelected = true;
			selectedIndex = i;
		

		}
	}

	
	
	return isSelected;
	
};


$( document ).ready(function() {

	

	
	
	
	/*******************************************OVERLAY ACTION BUTTONS  **********************************/
	//LIKE CLICK ON ACTION BAR FUNCTION
	$(document).delegate('.vidaction-like', 'click', function() {
		

		if(terrabisApp.isAuthenticated) {
			
				
			var videoDTO = terrabisApp.currentVideo;
			terrabisApp.tempVideo = terrabisApp.currentVideo;
			$("#vidaction-result_"+videoDTO.id).html( "<span class=\"fa fa-thumbs-up fa-10x text-white\"></span" );
			$("#vidaction-play_"+videoDTO.id).fadeOut();
			$("#vidaction-result_"+videoDTO.id).fadeIn("slow");
	
				
				var callback = function(data, status, xhr) {	
					$("#vidaction-result_"+videoDTO.id).fadeOut();
					$("#vidaction-play_"+videoDTO.id).fadeIn();
					$("#vidlikestxt_"+ terrabisApp.tempVideo.id).html(JSON.parse(data.responseText).message);

				};
				
				
				var formData = { videoid: terrabisApp.currentVideo.id };
				restajax(terrabisApp.countVideoLikeURI,formData,callback);
			
				
				
		}
		
		else
			
		{
			
			swal("You need to sign-in", "Please sign-in to like this video!. Click <a href=\"http://www.terrabis.com/authenticate/signin\">here</a>", "warning");
			
		}
		

	});
	



	//WATCH LATER CLICK ON ACTION BAR FUNCTION
	$(document).delegate('.vidaction-watchlater', 'click', function() {

		
		if(terrabisApp.isAuthenticated) {
				
			var videoDTO = terrabisApp.currentVideo;
			$("#vidaction-result_"+videoDTO.id).html( "<span class=\"fa fa-film fa-10x text-white\"></span" );
			$("#vidaction-play_"+videoDTO.id).fadeOut();
			$("#vidaction-result_"+videoDTO.id).fadeIn("slow");
	
				
			var callback = function(data, status, xhr) {	
				$("#vidaction-result_"+videoDTO.id).fadeOut();
				$("#vidaction-play_"+videoDTO.id).fadeIn();

			};
				
				
				var formData = { videoid: terrabisApp.currentVideo.id };
				restajax(terrabisApp.addToWatchLaterURI,formData,callback);
			
				
				
		}
		
		else
			
		{
			
			swal("You need to sign-in", "Please sign-in to add this video to your watch later collection!. Click <a href=\"http://www.terrabis.com/authenticate/signin\">here</a>", "warning");
			
		}
		
	});

	
	//ACTION BUTTON CLICK ON MAP
	$(document).delegate('.vidaction-map', 'click', function() {

		var videoDTO = terrabisApp.currentVideo;
		
		$("#video-overlay-loadingvideo_"+videoDTO.id).switchClass( "hidden", "-", 0, "easeInOutQuad" );
		setTimeout(function (){
			window.location.href = terrabisApp.mapURI+'?videouuid='+videoDTO.uuid;
			$("#video-overlay-loadingvideo_"+videoDTO.id).switchClass( "-", "hidden", 0, "easeInOutQuad" );
		}, 1000);
		
	});

	//ACTION BUTTON CLICK ON MAP
	$(document).delegate('.vidaction-one', 'click', function() {
		

		
		
		$("#video-overlay-loadingvideo_"+videoDTO.id).switchClass( "hidden", "-", 0, "easeInOutQuad" );
		setTimeout(function (){
			window.location.href = terrabisApp.videoOneViewURI+'/'+videoDTO.uuid;
			$("#video-overlay-loadingvideo_"+videoDTO.id).switchClass( "-", "hidden", 0, "easeInOutQuad" );
		}, 1000);
		
	});
	

	//ADD TO FAVORITES CLICK ACTION
	$(document).delegate('.vidaction-favorites', 'click', function() {
		
		
		if(terrabisApp.isAuthenticated) { 
			
			var videoDTO = terrabisApp.currentVideo;
			$("#vidaction-result_"+videoDTO.id).html( "<span class=\"fa fa-heart text-maroon fa-10x\"></span" );
			$("#vidaction-play_"+videoDTO.id).fadeOut();
			$("#vidaction-result_"+videoDTO.id).fadeIn("slow");
	
				
			var callback = function(data, status, xhr) {	
				$("#vidaction-result_"+videoDTO.id).fadeOut();
				$("#vidaction-play_"+videoDTO.id).fadeIn();

			};
			
			
			var formData = { videoid: terrabisApp.currentVideo.id };
			restajax(terrabisApp.addToFavoritesURI,formData,callback);			

				
		} 
		
		else
		
		{
			
			swal("You need to sign-in", "Please sign-in to add this video to your favorites collection!. Click <a href=\"http://www.terrabis.com/authenticate/signin\">here</a>", "warning");
			
		}
		
	});
	
	
	
	
	/****************************** ADD REMOVE ITEM FUNCTIONALITY ------------------------------------------------**/ 
	if (!Array.prototype.remove) {
		  Array.prototype.remove = function(val) {
		    var i = this.indexOf(val);
		         return i>-1 ? this.splice(i, 1) : [];
		  };
	}
	
	/********************** MOUSE OVER MOUSE OUT *************************************************************/
	$(document).delegate('.videopanel', 'mouseover', function() {
		terrabisApp.currentVideo = this.dataset;
		
		if(blockMouseOver)
			return;

		
		if(this.dataset.link=='true')
		$("#vidoverlay_action_"+terrabisApp.currentVideo.id).switchClass( "hidden", "-", 0, "easeInOutQuad" );
		else
		$("#vidoverlay_action_html5_"+terrabisApp.currentVideo.id).switchClass( "hidden", "-", 0, "easeInOutQuad" );
		
		

	});
	
	
	$(document).delegate('.videopanel', 'mouseout', function() {
		
		var currentdataset = this.dataset;

		
		if(currentdataset.select=='true') {
			if(!isVideoSelected(currentdataset.id)) {
				if(currentdataset.link=='true')
				$("#vidoverlay_action_"+currentdataset.id).switchClass("-", "hidden", 0, "easeInOutQuad" );	
				else
				$("#vidoverlay_action_html5_"+currentdataset.id).switchClass("-", "hidden", 0, "easeInOutQuad" );	

			}
		}
		else {
			if(currentdataset.link=='true')
			$("#vidoverlay_action_"+currentdataset.id).switchClass("-", "hidden", 0, "easeInOutQuad" );
			else
			$("#vidoverlay_action_html5_"+currentdataset.id).switchClass("-", "hidden", 0, "easeInOutQuad" );
				
		}

		blockMouseOver = false;
	});


	
	/********************** PLAY VIDEO BUTTON ON BOOTSTRAP MODAL *************************************************************/
	$(document).delegate('.vidaction-play', 'click', function() {
		terrabisApp.currentVideoPlayingId = terrabisApp.currentVideo.id;
		if(terrabisApp.currentVideo.link=='true') {
			$("#videomodal").modal();
			$("#videomodal_iframe").attr( "src", terrabisApp.currentVideo.videourl+"?autoplay=1&rel=0&modestbranding=1");
			$("#videomodal_title").html(terrabisApp.currentVideo.id);
		}
	});


	/**********************  CLOSE VIDEO  ************************************************************************/
	$(document).delegate('.videomodalclose', 'click', function() {
		$("#videomodal_iframe").attr( "src", " ");
		$("#videomodal_title").html("");
	});
	

	
	
	/********************************************************* SELECT VIDEO ******************************************/
	$(document).delegate('.vidaction-select', 'click', function(e) {
		//var currentdataset = terrabisApp.currentVideo;
		var videoSelected = isVideoSelected(terrabisApp.currentVideo.id);


		if(!videoSelected) {
			selectedItems.push(terrabisApp.currentVideo.id);
			//UPDATE SELECTEDVIDEOS IN FORMS
			updateFormsVideoSelectValue(terrabisApp.currentVideo.id,true);
		}
			
		else {
			blockMouseOver = true;
			selectedItems.remove(terrabisApp.currentVideo.id);
			//UPDATE SELECTEDVIDEOS IN FORMS
			updateFormsVideoSelectValue(terrabisApp.currentVideo.id,false);

		}

		/************************************ENABLE DISABLE MASSACTION BUTTONS *****************************************/
		if(selectedItems.length>0) { 
			$(".massaction").switchClass( "disabled", "-d", 0, "easeInOutQuad" );
		}
		else {
			$(".massaction").switchClass( "-d", "disabled", 0, "easeInOutQuad" );
		}
		
	});
	
	/*************************************REJECT REASON SELECT ON CHANGE *********************************************************/
	$('#rejectionreason-select').change(function() {
		console.log('rejection-reason change:'+this.value);
		$("#rejectvideosform input[name='rejectionreason']").val(this.value);
		if(this.value==-1) 
			$("#rejectionreason-text").prop('disabled', false);
		else
			$("#rejectionreason-text").prop('disabled', true);
				
			
	});
	
	
	$('#rejectionreason-text').change(function() {
		console.log('rejection-reason change:'+this.value);
		$("#rejectvideosform input[name='rejectionreasontxt']").val(this.value);
			
	});
	
	
	/*****************************************MASS ACTION BUTTONS*****************************************************/
	$('.massbtn-selectall').click(function() {
		 selectedItems = new Array();
		 
		 for(i=0; i<terrabisApp.data.length; i++ ) {
			 var currentPartitionArray = terrabisApp.data[i];
			 for(j=0; j<currentPartitionArray.length; j++) {
				 var currentVideo = currentPartitionArray[j];
			     selectedItems.push( currentVideo.id+"");
			     $("#vidoverlay_action_"+currentVideo.id).switchClass( "hidden", "-", 0, "easeInOutQuad" );
			     updateFormsVideoSelectValue( currentVideo.id+"",true);
			 }
			 
		 }
		 $(".massaction").switchClass( "disabled", "-d", 0, "easeInOutQuad" );
	});


	$('.massbtn-unselectall').click(function() {
		 selectedItems = new Array();
		 
		 for(i=0; i<terrabisApp.data.length;  i++ ) {
			 var currentPartitionArray = terrabisApp.data[i];
			 for(j=0; j<currentPartitionArray.length; j++) {
				 var currentVideo = currentPartitionArray[j];
			     $("#vidoverlay_action_"+currentVideo.id).switchClass( "-", "hidden", 0, "easeInOutQuad" );
			     updateFormsVideoSelectValue( currentVideo.id+"",false);
			 }
			 
		 }
		 $(".massaction").switchClass( "-d", "disabled", 0, "easeInOutQuad" );
	});


	
	$('.massbtn-selectapprove').click(function() {
		document.getElementById("approvevideosform").submit();
	});

	
	
	
	
	
	
	
	
	$('.massbtn-delete').click(function() {
		$("#deletevideosform input[name='singleselection']").val(false);
		var video_s = selectedItems.length > 1 ? "s" : "";
		var it = selectedItems.length > 1 ? "them" : "it";
		fullScreenOverlay('deletevideosform','Delete \''+selectedItems.length+'\'  video'+video_s + '?','Yes I\'m sure. Delete ' + it + ' now');
	});

	$('.massbtn-promotesiteindex').click(function() {
		$("#promotesiteindexvideosform input[name='singleselection']").val(false);
		var video_s = selectedItems.length > 1 ? "s" : "";
		var it = selectedItems.length > 1 ? "them" : "it";
		fullScreenOverlay('promotesiteindexvideosform','Promote \''+selectedItems.length+'\'  video'+video_s + '? to index page','Yes I\'m sure. Promote ' + it + ' now');
	});
	
	$('.massbtn-promotemap').click(function() {
		$("#promotemapvideosform input[name='singleselection']").val(false);
		var video_s = selectedItems.length > 1 ? "s" : "";
		var it = selectedItems.length > 1 ? "them" : "it";
		fullScreenOverlay('promotemapvideosform','Promote \''+selectedItems.length+'\'  video'+video_s + '? to map search','Yes I\'m sure. Promote ' + it + ' now');
	});	

	$('.massbtn-unpromotesiteindex').click(function() {
		$("#unpromotesiteindexvideosform input[name='singleselection']").val(false);
		var video_s = selectedItems.length > 1 ? "s" : "";
		var it = selectedItems.length > 1 ? "them" : "it";
		fullScreenOverlay('unpromotesiteindexvideosform','Un-Promote \''+selectedItems.length+'\'  video'+video_s + '? from index page','Yes I\'m sure. Un-Promote ' + it + ' now');
	});
	
	$('.massbtn-unpromotemap').click(function() {
		$("#unpromotemapvideosform input[name='singleselection']").val(false);
		var video_s = selectedItems.length > 1 ? "s" : "";
		var it = selectedItems.length > 1 ? "them" : "it";
		fullScreenOverlay('unpromotemapvideosform','Un-Promote \''+selectedItems.length+'\'  video'+video_s + '? from map search','Yes I\'m sure. Un-Promote ' + it + ' now');
	});	
	
	
	
	$('.massbtn-unpublish').click(function() {
		$("#upublishvideosform input[name='singleselection']").val(false);
		var video_s = selectedItems.length > 1 ? "s" : "";
		var it = selectedItems.length > 1 ? "them" : "it";
		fullScreenOverlay('upublishvideosform','Un-publish \''+selectedItems.length+'\'  video'+video_s + '?','Yes I\'m sure. Un-publish ' + it + ' now');
	});

	$('.massbtn-reject').click(function() {
		$("#rejectvideosform input[name='singleselection']").val(false);
		var video_s = selectedItems.length > 1 ? "s" : "";
		var it = selectedItems.length > 1 ? "them" : "it";
		fullScreenOverlayReject('rejectvideosform','-', 'Reject \''+selectedItems.length+'\'  video'+video_s + '?','Yes I\'m sure. Reject ' + it + ' now');
	});
	

	$('.massbtn-publish').click(function() {
		$("#publishvideosform input[name='singleselection']").val(false);
		var video_s = selectedItems.length > 1 ? "s" : "";
		var it = selectedItems.length > 1 ? "them" : "it";
		fullScreenOverlay('publishvideosform','Publish \''+selectedItems.length+'\'  video'+video_s + '?','Yes I\'m sure. Publish ' + it + ' now');
	});	
	
	
	
	$('.massbtn-approve').click(function() {
		$("#approvevideosform input[name='singleselection']").val(false);
		var video_s = selectedItems.length > 1 ? "s" : "";
		var it = selectedItems.length > 1 ? "them" : "it";
		fullScreenOverlay('approvevideosform','Approve \''+selectedItems.length+'\'  video'+video_s + '?','Yes I\'m sure. Approve ' + it + ' now');
	});		
	
	

	
	
	
	/**********************  ACTION BAR  ************************************************************************/
	
	
	
	//UNPUBLISH
	$(document).delegate('.vidaction-unpublish', 'click', function() {

		var videoid = terrabisApp.currentVideo.id;
		var thumburl = terrabisApp.currentVideo.thumburl;
		
		$("#upublishvideosform input[name='singleselection']").val(true);
		$("#upublishvideosform input[name='selectedvideo']").val(videoid);
		fullScreenOverlayWithImage('upublishvideosform',thumburl, 'Un-publish this video?','Yes un-publish it');
	});

	//UNPUBLISH
	$(document).delegate('.vidaction-approve', 'click', function() {

		var videoid = terrabisApp.currentVideo.id;
		var thumburl = terrabisApp.currentVideo.thumburl;
		
		$("#approvevideosform input[name='singleselection']").val(true);
		$("#approvevideosform input[name='selectedvideo']").val(videoid);
		fullScreenOverlayWithImage('approvevideosform',thumburl, 'approve this video?','Yes approve it');
	});
	
	//REJECT
	$(document).delegate('.vidaction-reject', 'click', function() {

		var videoid = terrabisApp.currentVideo.id;
		var thumburl = terrabisApp.currentVideo.thumburl;
		
		$("#rejectvideosform input[name='singleselection']").val(true);
		$("#rejectvideosform input[name='selectedvideo']").val(videoid);
		fullScreenOverlayReject('rejectvideosform',thumburl, 'Reject this video?','Yes reject it');
	});


	//PUBLISH
	$(document).delegate('.vidaction-publish', 'click', function() {

		var videoid = terrabisApp.currentVideo.id;
		var thumburl = terrabisApp.currentVideo.thumburl;
		
		$("#publishvideosform input[name='singleselection']").val(true);
		$("#publishvideosform input[name='selectedvideo']").val(videoid);
		fullScreenOverlayWithImage('publishvideosform',thumburl, 'Publish this video?','Yes publish it');
	});

	
	//DELETE
	$(document).delegate('.vidaction-delete', 'click', function() {

		var videoid = terrabisApp.currentVideo.id;
		var thumburl = terrabisApp.currentVideo.thumburl;
		
		$("#deletevideosform input[name='singleselection']").val(true);
		$("#deletevideosform input[name='selectedvideo']").val(videoid);
		fullScreenOverlayWithImage('deletevideosform',thumburl, 'Delete this video? The action cannot be undone','Yes delete it');
	});

	//EDIT
	$(document).delegate('.vidaction-edit', 'click', function(e) {
		window.location.href = $(this).attr('href');
		
	});
	
	

});


