var selectedItems = new Array();
var blockMouseOver = false;


/*

//ADMIN MENU SIDEBAR NAVIGATIONAL AND  EFFECTS
$( ".adminmenu_select_user" ).click(function() {
	 $('#adminmenu').hide();	
	 $('#adminmenu_users').show("slide", { direction: "right" }, 1000);	
});


$( ".adminmenu_select_root" ).click(function() {
	 $('#adminmenu_users').hide();	
	 $('#adminmenu').show("slide", { direction: "left" }, 1000);	
});
*/



$( document ).ready(function() {

	
	
	/****************************** ADD REMOVE ITEM FUNCTIONALITY ------------------------------------------------**/ 
	if (!Array.prototype.remove) {
		  Array.prototype.remove = function(val) {
		    var i = this.indexOf(val);
		         return i>-1 ? this.splice(i, 1) : [];
		  };
	}

	/*****************************************MASS ACTION BUTTONS*****************************************************/
	$('.massbtn-enable').click(function() {
		$("#adminusersform input[name='singleselection']").val(false);
		$("#adminusersform input[name='selecteditemslist']").val(selectedItems.toString());
		$("#adminusersform").attr("action", terrabisApp.adminUserURI + "enable");
		var users_s = selectedItems.length > 1 ? "s" : "";
		var it = selectedItems.length > 1 ? "them" : "it";
		fullScreenOverlay('adminusersform','Enable \''+selectedItems.length+'\'  user'+users_s + '?','Yes I\'m sure. Enable ' + it + ' now');
	});
	
	
	/*****************************************MASS ACTION BUTTONS*****************************************************/
	$('.massbtn-disable').click(function() {
		$("#adminusersform input[name='singleselection']").val(false);
		$("#adminusersform input[name='selecteditemslist']").val(selectedItems.toString());
		$("#adminusersform").attr("action",terrabisApp.adminUserURI + "disable");
		var users_s = selectedItems.length > 1 ? "s" : "";
		var it = selectedItems.length > 1 ? "them" : "it";
		fullScreenOverlay('adminusersform','Disable \''+selectedItems.length+'\'  user'+users_s + '?','Yes I\'m sure. Disable ' + it + ' now');
	});
	
	
	
	
	
	/********************************************************* SELECT USERS ******************************************/
	$(document).delegate('.useraction-select', 'click', function(e) {
	
		var currentdataset = this.dataset;

		 if($(this).is(':checked')) {
			 selectedItems.push(currentdataset.userid);

		 }
		 else {
			 selectedItems.remove(currentdataset.userid);
		 }
		 

		/************************************ENABLE DISABLE MASSACTION BUTTONS *****************************************/
		if(selectedItems.length>0) { 
			$(".massaction").switchClass( "disabled", "-d", 0, "easeInOutQuad" );
		}
		else {
			$(".massaction").switchClass( "-d", "disabled", 0, "easeInOutQuad" );
		}
		
		
		
		console.log(selectedItems.toString());
		
	});
	
	
	
});
