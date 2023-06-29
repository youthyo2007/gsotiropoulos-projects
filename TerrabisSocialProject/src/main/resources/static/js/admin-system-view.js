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

	
	/**********************************SAVE SYSTEM PROPERTIES BUTTON**************************************************/
	$(document).delegate('.syspropertiesaction-save', 'click', function() {
		var selectedid = this.dataset.syspropsid;
		var selectedvalue = $("#systempropertiesform textarea[id='"+selectedid+"']").val();

		$("#systempropertiesform input[name='selecteditem']").val(selectedid);
		$("#systempropertiesform input[name='selecteditemvalue']").val(selectedvalue);
		$("#systempropertiesform").submit();
		
	});
	
	
	
	
});
