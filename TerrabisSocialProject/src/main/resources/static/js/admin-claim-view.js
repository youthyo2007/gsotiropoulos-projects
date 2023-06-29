var selectedItems = new Array();
var blockMouseOver = false;





$( document ).ready(function() {

	
	
	/****************************** ADD REMOVE ITEM FUNCTIONALITY ------------------------------------------------**/ 
	if (!Array.prototype.remove) {
		  Array.prototype.remove = function(val) {
		    var i = this.indexOf(val);
		         return i>-1 ? this.splice(i, 1) : [];
		  };
	}

	

	
	
	
	//UNPUBLISH
	$(document).delegate('.vidaction-forceclaim', 'click', function() {


		var claimId = terrabisApp.currentVideo.claimid;
		var thumburl = terrabisApp.currentVideo.thumburl;
		
		$("#forceclaimform input[name='singleselection']").val(true);
		$("#forceclaimform input[name='selecteditem']").val(claimId);
		$("#forceclaimform").submit();
		
	});
	
	

	
	
	
});
