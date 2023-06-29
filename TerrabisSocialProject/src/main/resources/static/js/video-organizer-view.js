var selectedItems = new Array();
var blockMouseOver = false;


function updateFormsVideoSelectValue(currentdataset,value) {
	$("#addvidtoalbumform input[name='selectedvideos["+currentdataset.videoid+"]']").val(value);
	$("#addvidtoplaylistform input[name='selectedvideos["+currentdataset.videoid+"]']").val(value);
	$("#addvidtocollectionform input[name='selectedvideos["+currentdataset.videoid+"]']").val(value);
	$("#removevidfromcollectionform input[name='selectedvideos["+currentdataset.videoid+"]']").val(value);			
};

function isVideoSelected(videoid, collectionid) {
	
	var isSelected = false;
	var selectedIndex = -1;
	for(i=0; i<selectedItems.length; i++) {
		if((selectedItems[i].videoid==videoid) && (selectedItems[i].collectionid==collectionid)) {
			isSelected = true;
			selectedIndex = i;
		}
	}

	return isSelected;
	
};


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


$( document ).ready(function() {

	/********************** MOUSE OVER MOUSE OUT *************************************************************/
	$(document).delegate('.videopanel', 'mouseover', function() {
		console.log(JSON.stringify(selectedItems));
		
		terrabisApp.videodataset = this.dataset;
		

		if(blockMouseOver)
			return;

		$("#vidoverlay_action_"+terrabisApp.videodataset.videoid).switchClass( "hidden", "-", 0, "easeInOutQuad" );

	});
	
	
	$(document).delegate('.videopanel', 'mouseout', function() {
		var currentdataset = this.dataset;

		if(currentdataset.select=='true') {
			if(!isVideoSelected(currentdataset.videoid,currentdataset.collectionid)) {
				$("#vidoverlay_action_"+currentdataset.videoid).switchClass("-", "hidden", 0, "easeInOutQuad" );		
			}
		}
		else {
			$("#vidoverlay_action_"+currentdataset.videoid).switchClass("-", "hidden", 0, "easeInOutQuad" );
		}

		blockMouseOver = false;
	});
	
	
	/********************** PLAY VIDEO BUTTON *************************************************************/
	$(document).delegate('.vidoverlay_playvidbtn', 'click', function() {
		if(terrabisApp.currentVideoPlayingId!=-1) {
			$("#vidoverlay_play_iframe"+terrabisApp.videodataset.videoid).attr( "src", "");
			$("#vidoverlay_play_"+terrabisApp.videodataset.videoid).switchClass("-", "hidden", 0, "easeInOutQuad" );
			$("#vidoverlay_action_"+terrabisApp.videodataset.videoid).switchClass( "hidden", "-", 0, "easeInOutQuad" );
		}
		
		
		terrabisApp.currentVideoPlayingId = terrabisApp.videodataset.videoid;
		$("#vidoverlay_action_"+terrabisApp.videodataset.videoid).switchClass( "-", "hidden", 0, "easeInOutQuad" );
		$("#vidoverlay_play_"+terrabisApp.videodataset.videoid).switchClass( "hidden", "-", 0, "easeInOutQuad" );
		$("#vidoverlay_play_iframe"+terrabisApp.videodataset.videoid).attr( "src", terrabisApp.videodataset.videourl+"?autoplay=1&rel=0&modestbranding=1");	
		
		
	});

	

	/**********************  CLOSE VIDEO  ************************************************************************/
	$(document).delegate('.vidaction-close', 'click', function() {
		$("#vidoverlay_action_"+terrabisApp.currentVideoPlayingId).switchClass( "hidden", "-", 0, "easeInOutQuad" );
		$("#vidoverlay_play_iframe"+terrabisApp.currentVideoPlayingId).attr( "src", "");
		$("#vidoverlay_play_"+terrabisApp.currentVideoPlayingId).switchClass("-", "hidden", 0, "easeInOutQuad" );
	});
	
	
	/********************************************************* SELECT VIDEO ******************************************/
	$(document).delegate('.vidaction-select', 'click', function(e) {
		var currentdataset = terrabisApp.videodataset;


		var currentdataset = terrabisApp.videodataset;
		var videoSelected = isVideoSelected(currentdataset.videoid,currentdataset.collectionid);

		if(!videoSelected) {
			selectedItems.push(currentdataset);
			//UPDATE SELECTEDVIDEOS IN FORMS
			updateFormsVideoSelectValue(currentdataset,true);
		}
			
		else {
			blockMouseOver = true;
			selectedItems.remove(currentdataset);
			//UPDATE SELECTEDVIDEOS IN FORMS
			updateFormsVideoSelectValue(currentdataset,false);

		}

		/************************************ENABLE DISABLE MASSACTION BUTTONS *****************************************/
		if(selectedItems.length>0) { 
			$(".massaction").switchClass( "disabled", "-d", 0, "easeInOutQuad" );
		}
		else {
			$(".massaction").switchClass( "-d", "disabled", 0, "easeInOutQuad" );
		}
		
	});
	
	
	
	
	
	/****************************** ADD REMOVE ITEM FUNCTIONALITY ------------------------------------------------**/ 
	if (!Array.prototype.remove) {
		  Array.prototype.remove = function(val) {
		    var i = this.indexOf(val);
		         return i>-1 ? this.splice(i, 1) : [];
		  };
	}

	
	/**********************  ACTION BAR  ************************************************************************/
	
	//MOVE UP THE PATH
	$(document).delegate('.vidaction-path-move-up', 'click', function() {

		
		var collectionid = terrabisApp.videodataset.collectionid;
		var videoid = terrabisApp.videodataset.videoid;
		var thumburl = terrabisApp.videodataset.thumburl;
		
		$("#pathmoveupvideosform input[name='selectedcollection']").val(collectionid);
		$("#pathmoveupvideosform input[name='singleselection']").val(true);
		$("#pathmoveupvideosform input[name='selectedvideo']").val(videoid);
		document.getElementById('pathmoveupvideosform').submit();
		//fullScreenOverlayWithImage('pathmoveupvideosform',thumburl, 'Move UP the path this video?','Yes move it up');
	});

	
	//MOVE DOWN THE PATH
	$(document).delegate('.vidaction-path-move-down', 'click', function() {

		
		var collectionid = terrabisApp.videodataset.collectionid;
		var videoid = terrabisApp.videodataset.videoid;
		var thumburl = terrabisApp.videodataset.thumburl;
		
		
		
		$("#pathmovedownvideosform input[name='selectedcollection']").val(collectionid);
		$("#pathmovedownvideosform input[name='singleselection']").val(true);
		$("#pathmovedownvideosform input[name='selectedvideo']").val(videoid);
		//fullScreenOverlayWithImage('pathmovedownvideosform',thumburl, 'Move DOWN the path this video?','Yes move it down');
		document.getElementById('pathmovedownvideosform').submit();
	});
	
	
	
	/****************************** PLAYLIST CHECKBOX ------------------------------------------------**/ 
	$('.playlistcheckbox').click(function() {
		var playlistid = $(this).attr('value');
		$("#addvidtoplaylistform input[name='selectedplaylists["+playlistid+"]']").val($(this).is(':checked'));
	});
	
	/****************************** ON CHECK ALBUM CHECKBOXES UPDATE FORM ------------------------------------------------**/ 
	$('.albumcheckbox').click(function() {
		var albumid = $(this).attr('value');
		$("#addvidtoalbumform input[name='selectedalbums["+albumid+"]']").val($(this).is(':checked'));
	});
	
	
	/*****************************************MASS ACTION BUTTONS*****************************************************/
	$('.massbtn-playlist-add').click(function() {
		$("#addvidtoplaylistform input[name='singleselection']").val(false);
		var video_s = selectedItems.length > 1 ? "s" : "";
		var it = selectedItems.length > 1 ? "them" : "it";
		fullScreenOverlay('addvidtoplaylistform','Add \''+selectedItems.length+'\'  video'+video_s + ' to video path?','Yes I\'m sure. Add ' + it + ' now');
	});

	$('.massbtn-album-add').click(function() {
		$("#addvidtoalbumform input[name='singleselection']").val(false);
		var video_s = selectedItems.length > 1 ? "s" : "";
		var it = selectedItems.length > 1 ? "them" : "it";
		fullScreenOverlay('addvidtoalbumform','Add \''+selectedItems.length+'\'  video'+video_s + ' to album?','Yes I\'m sure. Add ' + it + ' now');
	});
	
	
	$('.massbtn-watchlater-add').click(function() {
		$("#addvidtocollectionform input[name='singleselection']").val(false);
		$("#addvidtocollectionform input[name='selectedcollection']").val(terrabisApp.watchlatercollection);
		var video_s = selectedItems.length > 1 ? "s" : "";
		var it = selectedItems.length > 1 ? "them" : "it";
		fullScreenOverlay('addvidtocollectionform','You are about to add \''+selectedItems.length+'\'  video'+video_s + ' to \'watch later\' collection','Yes I\'ll watch '+it+' later');
	});

	$('.massbtn-watchlater-remove').click(function() {
		$("#removevidfromcollectionform input[name='selectedcollection']").val(terrabisApp.watchlatercollection);
		$("#removevidfromcollectionform input[name='singleselection']").val(false);
		var video_s = selectedItems.length > 1 ? "s" : "";
		var it = selectedItems.length > 1 ? "them" : "it";
		fullScreenOverlay('removevidfromcollectionform','Remove \''+selectedItems.length+'\'  video'+video_s + ' from \'watch later\' collection','Yes I\'ve watched '+it+' already');
	});


	$('.massbtn-favorites-remove').click(function() {
		$("#removevidfromcollectionform input[name='selectedcollection']").val(terrabisApp.favoritescollection);
		$("#removevidfromcollectionform input[name='singleselection']").val(false);
		var video_s = selectedItems.length > 1 ? "s" : "";
		var it = selectedItems.length > 1 ? "them" : "it";
		fullScreenOverlay('removevidfromcollectionform','Remove \''+selectedItems.length+'\'  video'+video_s + ' from \'favorites\' collection','Yes I\'ve watched '+it+' already');
	});

	
	$('.playlistbtn-removevids').click(function() {
		$("#removevidfromcollectionform input[name='selectedcollection']").val(this.dataset.collectionid);
		var video_s = selectedItems.length > 1 ? "s" : "";
		var it = selectedItems.length > 1 ? "them" : "it";
		fullScreenOverlay('removevidfromcollectionform','Remove \''+selectedItems.length+'\'  video'+video_s + ' from  \''+this.dataset.title+'\'?','Yes I\'m sure. Remove '+it+' now');
	});

	$('.albumbtn-removevids').click(function() {
		$("#removevidfromcollectionform input[name='selectedcollection']").val(this.dataset.collectionid);
		var video_s = selectedItems.length > 1 ? "s" : "";
		var it = selectedItems.length > 1 ? "them" : "it";
		fullScreenOverlay('removevidfromcollectionform','Remove \''+selectedItems.length+'\'  video'+video_s + ' from  \''+this.dataset.title+'\'?','Yes I\'m sure. Remove '+it+' now');
	});

	
	/****************************** DELETE PLAYLIST ------------------------------------------------**/ 
	$('.playlistbtn-delete').click(function() {
		$("#deletecollectionform input[name='selectedcollection']").val(this.dataset.collectionid);
		var video_s = selectedItems.length > 1 ? "s" : "";
		fullScreenOverlay('deletecollectionform','Delete \''+this.dataset.title+'\'?','Yes I\'m sure. Delete it permanently.');
	});
	
	
	/****************************** DELETE ALBUM ------------------------------------------------**/ 
	$('.albumbtn-delete').click(function() {
		$("#deletecollectionform input[name='selectedcollection']").val(this.dataset.collectionid);
		var video_s = selectedItems.length > 1 ? "s" : "";
		fullScreenOverlay('deletecollectionform','Delete \''+this.dataset.title+'\'?','Yes I\'m sure. Delete it permanently.');
	});
	
	
	
	
	
	/****************************** CLEAR ALL ------------------------------------------------**/ 
	$('#gridbtn_clearall').click(function() {
		for(i=0; i<selectedItems.length; i++) {
			var currentdataset = selectedItems[i];

			//UPDATE FORM TO CLEAR ALL VIDEOS
			$("#addvidtoalbumform input[name='selectedvideos["+currentdataset.videoid+"]']").val(false);
			$("#addvidtoplaylistform input[name='selectedvideos["+currentdataset.videoid+"]']").val(false);
			$("#addvidtocollectionform input[name='selectedvideos["+currentdataset.videoid+"]']").val(false);
			$("#removevidfromcollectionform input[name='selectedvideos["+currentdataset.videoid+"]']").val(false);
		}

		$(".vidoverlayitem").switchClass( "mngvidoverlay", "vidoverlayhide", 0, "easeInOutQuad" );
		$("#gridbtn_clearall").switchClass( "-d", "disabled", 0, "easeInOutQuad" );
		$("#gridbtn_selectall").switchClass( "disabled", "-d", 0, "easeInOutQuad" );
		$(".massactionvidsbtn").switchClass( "-d", "disabled", 0, "easeInOutQuad" );
		selectedItems = new Array();
	});
	
	
	
	/****************************** SELECT ALL ------------------------------------------------**/ 
	$('#gridbtn_selectall').click(function() {
		
		var elementsArray = $(".mngvidoverlaybox").toArray(); 
		for(i=0; i<elementsArray.length; i++) {
			var currentdataset = elementsArray[i].dataset;
			selectedItems.push(currentdataset);
			
			//UPDATE FORM TO INCLUDE ALL VIDEOS
			$("#addvidtoalbumform input[name='selectedvideos["+currentdataset.videoid+"]']").val(true);
			$("#addvidtoplaylistform input[name='selectedvideos["+currentdataset.videoid+"]']").val(true);
			$("#addvidtocollectionform input[name='selectedvideos["+currentdataset.videoid+"]']").val(true);
			$("#removevidfromcollectionform input[name='selectedvideos["+currentdataset.videoid+"]']").val(true);
		}
		$(".vidoverlayitem").switchClass("vidoverlayhide", "mngvidoverlay", 0, "easeInOutQuad" );
		$("#gridbtn_clearall").switchClass( "disabled", "-d", 0, "easeInOutQuad" );
		$("#gridbtn_selectall").switchClass( "-d", "disabled", 0, "easeInOutQuad" );
		$(".massactionvidsbtn").switchClass( "disabled", "-d", 0, "easeInOutQuad" );
		
	});
	
	
	



});


