var selectedItems = new Array();
var blockMouseOver = true;



$( document ).ready(function() {

	
	
	/****************************** ADD REMOVE ITEM FUNCTIONALITY ------------------------------------------------**/ 
	if (!Array.prototype.remove) {
		  Array.prototype.remove = function(val) {
		    var i = this.indexOf(val);
		         return i>-1 ? this.splice(i, 1) : [];
		  };
		}

	
	
	
	/****************************** ON CHECK PLAYLIST CHECKBOXES UPDATE FORM ------------------------------------------------**/ 
	$('.playlistcheckbox').click(function() {
		var playlistid = $(this).attr('value');
		$("#addvidtoplaylistform input[name='selectedplaylists["+playlistid+"]']").val($(this).is(':checked'));
	});
	
	
	/****************************** ON CHECK ALBUM CHECKBOXES UPDATE FORM ------------------------------------------------**/ 
	$('.albumcheckbox').click(function() {
		var albumid = $(this).attr('value');
		$("#addvidtoalbumform input[name='selectedalbums["+albumid+"]']").val($(this).is(':checked'));
	});
	
	
	/****************************** ADD VIDEOS TO PLAYLIST SUBMIT BUTTON ------------------------------------------------**/ 
	$('#playlistbtn-addvids').click(function() {
		var video_s = selectedItems.length > 1 ? "s" : "";
		fullScreenOverlay('addvidtoplaylistform','Add \''+selectedItems.length+'\'  video'+video_s + ' to the video path?','Yes I\'m sure. Add them now');
	});
	
	/****************************** ADD VIDEOS TO ALBUM SUBMIT BUTTON ------------------------------------------------**/ 
	$('#albumbtn-addvids').click(function() {
		var video_s = selectedItems.length > 1 ? "s" : "";
		fullScreenOverlay('addvidtoalbumform','Add \''+selectedItems.length+'\'  video'+video_s + ' to album?','Yes I\'m sure. Add them now');
	});	
    
	
  	/****************************** ADD VIDEOS TO WATCH LATER SUBMIT BUTTON ------------------------------------------------**/ 
	$('#watchlaterbtn-addvids').click(function() {
		$("#addvidtocollectionform input[name='selectedcollection']").val(terrabisApp.watchlatercollection);
		var video_s = selectedItems.length > 1 ? "s" : "";
		fullScreenOverlay('addvidtocollectionform','You are about to add  \''+selectedItems.length+'\'  video'+video_s + ' to \'watch later\' collection','Yes I\'ll watch them later');
	});

	/****************************** REMOVE VIDEOS FROM  WATCH LATER SUBMIT BUTTON ------------------------------------------------**/ 
	$('#watchlaterbtn-removevids').click(function() {
		$("#removevidfromcollectionform input[name='selectedcollection']").val(terrabisApp.watchlatercollection);
		var video_s = selectedItems.length > 1 ? "s" : "";
		fullScreenOverlay('removevidfromcollectionform','Remove \''+selectedItems.length+'\'  video'+video_s + ' from \'watch later\' collection?','Yes I\'ve watched them already');
	});
	
	
	
	/****************************** REMOVE VIDEOS FROM SPECIFIC PLAYLIST BUTTON ------------------------------------------------**/ 
	$('#playlistsbtn-removevids').click(function() {
		$("#removevidfromcollectionform input[name='selectedcollection']").val(this.dataset.collectionid);
		var video_s = selectedItems.length > 1 ? "s" : "";
		fullScreenOverlay('removevidfromcollectionform','Remove \''+selectedItems.length+'\'  video'+video_s + ' from  \''+this.dataset.title+'\'?','Yes I\'m sure. Remove them now');
	});

	/****************************** REMOVE VIDEOS FROM SPECIFIC ALBUM BUTTON ------------------------------------------------**/ 
	$('#albumsbtn-removevids').click(function() {
		$("#removevidfromcollectionform input[name='selectedcollection']").val(this.dataset.collectionid);
		var video_s = selectedItems.length > 1 ? "s" : "";
		fullScreenOverlay('removevidfromcollectionform','Remove \''+selectedItems.length+'\'  video'+video_s + ' from  \''+this.dataset.title+'\'?','Yes I\'m sure. Remove them now');
	});
	
	
	/****************************** DELETE PLAYLIST ------------------------------------------------**/ 
	$('.playlistsbtn-delete').click(function() {
		$("#deletecollectionform input[name='selectedcollection']").val(this.dataset.collectionid);
		var video_s = selectedItems.length > 1 ? "s" : "";
		fullScreenOverlay('deletecollectionform','Delete \''+this.dataset.title+'\'?','Yes I\'m sure. Delete it permantely.');
	});
	
	/****************************** DELETE ALBUM ------------------------------------------------**/ 
	$('.albumsbtn-delete').click(function() {
		$("#deletecollectionform input[name='selectedcollection']").val(this.dataset.collectionid);
		var video_s = selectedItems.length > 1 ? "s" : "";
		fullScreenOverlay('deletecollectionform','Delete \''+this.dataset.title+'\'?','Yes I\'m sure. Delete it permantely.');
	});

	
	
	
	
	/****************************** REMOVE VIDEOS FROM  FAVORITES BUCKET SUBMIT BUTTON ------------------------------------------------**/ 
	$('#favoritesbucketbtn-removevids').click(function() {
		$("#removevidfromcollectionform input[name='selectedcollection']").val(terrabisApp.favoritescollection);
		$("#removevidfromcollectionform").submit();
	});
	
	
	
	
	
	/****************************** CLEAR ALL ------------------------------------------------**/ 
	$('#gridbtn_clearall').click(function() {
		for(i=0; i<selectedItems.length; i++) {
			var currentdataset = selectedItems[i];

			//UPDATE FORM TO CLEAR ALL VIDEOS
			$("#addvidtoplaylistform input[name='selectedvideos["+currentdataset.videoid+"]']").val(false);
			$("#addvidtoalbumform input[name='selectedvideos["+currentdataset.videoid+"]']").val(false);
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
	
	
	
	/****************************** SELECT GRID ITEMS FUNCTIONALITY ------------------------------------------------**/ 
	$(document).delegate('.mngvidoverlaybox', 'mouseover', function() {
		$(this).children('div:first').switchClass( "vidoverlayhide", "mngvidoverlay", 0, "easeInOutQuad" );
	});

	$(document).delegate('.mngvidoverlaybox', 'mouseout', function() {
		var itemSelected = false;
		var currentdataset = this.dataset;
		
		if (selectedItems.length==0) {
			$(this).children('div:first').switchClass( "mngvidoverlay", "vidoverlayhide", 0, "easeInOutQuad" );
			return ;
		}
		for(i=0; i<selectedItems.length; i++) {
			if((selectedItems[i].videoid==currentdataset.videoid) && (selectedItems[i].collectionid==currentdataset.collectionid))
				itemSelected = true;
		}
		
		if(!itemSelected) {
			$(this).children('div:first').switchClass( "mngvidoverlay", "vidoverlayhide", 0, "easeInOutQuad" );
		}
		
		
	});

	
	$(document).delegate('.vidsettings', 'click', function(e) {
		window.location.href = $(this).attr('href');
		
	});
	
	
	
	$(document).delegate('.mngvidoverlaybox', 'click', function(e) {
		

		var currentdataset = this.dataset;

		var isSelected = false;
		var selectedIndex = -1;
		for(i=0; i<selectedItems.length; i++) {
			if((selectedItems[i].videoid==currentdataset.videoid) && (selectedItems[i].collectionid==currentdataset.collectionid)) {
				isSelected = true;
				selectedIndex = i;
			}
		}
		
		if(!isSelected) {
			
			
			selectedItems.push(currentdataset);
			var videoid = currentdataset.videoid;
			
			//UPDATE FORM
			$("#addvidtoalbumform input[name='selectedvideos["+videoid+"]']").val(true);
			$("#addvidtoplaylistform input[name='selectedvideos["+videoid+"]']").val(true);
			$("#addvidtocollectionform input[name='selectedvideos["+videoid+"]']").val(true);
			$("#removevidfromcollectionform input[name='selectedvideos["+videoid+"]']").val(true);

			
		}
		else {
			blockMouseOver = true;
			$(this).children('div:first').switchClass( "mngvidoverlay", "vidoverlayhide", 0, "easeInOutQuad" );
			selectedItems.remove(currentdataset);
			//UPDATE FORM
			$("#addvidtoalbumform input[name='selectedvideos["+videoid+"]']").val(false);
			$("#addvidtoplaylistform input[name='selectedvideos["+videoid+"]']").val(false);
			$("#addvidtocollectionform input[name='selectedvideos["+videoid+"]']").val(false);
			$("#removevidfromcollectionform input[name='selectedvideos["+videoid+"]']").val(false);


		}

		
		if(selectedItems.length>0) { 
			$(".massactionvidsbtn").switchClass( "disabled", "-d", 0, "easeInOutQuad" );
			$('#gridbtn_clearall').switchClass("disabled", "-d", 0, "easeInOutQuad" );
		}
		else {
			$('#gridbtn_clearall').switchClass( "-d", "disabled", 0, "easeInOutQuad" );
			$(".massactionvidsbtn").switchClass( "-d", "disabled", 0, "easeInOutQuad" );
		}
		
		
	});

});


