var advSelectedTags = new Array();



function updateTagUICriteriaHtml() {
	
	var tag = "";
	var tag_html = "";
	for(i=0; i<advSelectedTags.length; i++) {
		tag =  '<button type="button" data-tagname="'+advSelectedTags[i]+'" class="tag-close btn btn-labeled bg-gray-dark mr-sm">'+
		'<span class="btn-label"><i class="fa fa-times"></i>'+
		'</span><span class="h4 text-bold">'+advSelectedTags[i]+
		'</span></button>';

		tag_html = tag_html+ tag;
	}
	
	
	
/*	if(terrabisApp.queryData.advfilter.userid!='') {
		tag =  '<button type="button" data-tagname="'+ terrabisApp.queryData.advfilter.username+'" class="tag-user-close btn btn-labeled bg-pink-matte mr-sm">'+
		'<span class="btn-label"><i class="fa fa-smile-o fa-2x"></i>'+
		'</span><span class="h4 text-bold">user:'+ terrabisApp.queryData.advfilter.username +
		'</span></button>';	
		
		tag_html = tag_html+ tag;
	}

	if(terrabisApp.queryData.advfilter.pathname!='') {
		tag =  '<button type="button" data-tagname="'+ terrabisApp.queryData.advfilter.pathname+'" class="tag-path-close btn btn-labeled bg-pink-matte mr-sm">'+
		'<span class="btn-label"><i class="fa fa-smile-o fa-2x"></i>'+
		'</span><span class="h4 text-bold">path:'+ terrabisApp.queryData.advfilter.pathname +
		'</span></button>';	
		
		tag_html = tag_html+ tag;
	}*/
	
	$( "#advselectedtags" ).html(tag_html);
	
};









//TAG CLICK IMPLEMENTATION
function onTagAdvCriteriaClick(tag) {
	advSelectedTags.push(tag);
	$("#advsearchqueryform input[name='advtags']").val(advSelectedTags.toString());
	updateTagUICriteriaHtml();
	
	
	//SEARCH WHEN A TAG IS CLICKED
	submitAdvancedCriteriaFilter();    

	
	
};




$( document ).ready(function() {
	
	//INITIALISE HANDLERS
	initCriteriaInputs_OnClickHandlers();	

	
	//DISABLE SUBMIT FORM
	$('#advsearchqueryform').submit(function() {
		  return false;
	});
	
	/****************************** ADD REMOVE ITEM FUNCTIONALITY FOR JAVASCRIPT ARRAYS------------------------------------------------**/ 
	if (!Array.prototype.remove) {
		  Array.prototype.remove = function(val) {
		    var i = this.indexOf(val);
		         return i>-1 ? this.splice(i, 1) : [];
		  };
	}
	
	
	/**************************************TAG CLOUD***************************************************/
	
	//KEY PRESS EVENT ON TAGS AND FILTER TAG CLOUD
	 $('#advtagsfilter').bind('input',function(event) {

		 	var callback = function(data, status, xhr) {
		 		var tagwords =JSON.parse(data.responseText);
				$('#advtagcloud').jQCloud('update', tagwords);

		 	};
		
		 	var tagfilter = $('#advtagsfilter').val();

		 	
		 	if(tagfilter.trim().length==0) {
		 		$('#advtagcloud').jQCloud('update', jQuery.parseJSON(terrabisApp.taglist));
		 	}
		 	else {
		 		var formData = { tagtext: tagfilter};
		 		restajax(terrabisApp.fetchTagsURI,formData,callback);
		 	}
	 });
	

	//TAG BTN CLICK THEN REMOVE
	$(document).delegate('.tag-close', 'click', function() {
		advSelectedTags.remove(this.dataset.tagname);
		
		$("#advsearchqueryform input[name='advtags']").val(advSelectedTags.toString());
		updateTagUICriteriaHtml();

		//SEARCH WHEN A TAG IS REMOVED
		submitAdvancedCriteriaFilter();    
		
		
	});
	
	$(document).delegate('.tag-user-close', 'click', function() {
		$("#advsearchqueryform input[name='userid']").val('');
		$("#userpindiv").switchClass( "-", "hidden", 0, "easeInOutQuad" );	

		//SEARCH WHEN A TAG IS REMOVED
		submitAdvancedCriteriaFilter();    
 
	

		
	});
	
	
	$(document).delegate('.tag-collection-close', 'click', function() {
		$("#advsearchqueryform input[name='collectionvideos']").val('');
		$("#advsearchqueryform input[name='collectionid']").val('');
		$( ".mapoverlay_collection").switchClass( "-", "hidden", 0, "easeInOutQuad" );	
		
		//SEARCH WHEN A TAG IS REMOVED
		submitAdvancedCriteriaFilter();    
	
		updateTagUICriteriaHtml();

		
	});	
	
	
	
	
	

});




/**********************  FILTER BUTTON CLICK FUNCTION  ********************************/
$(".advfilterupdatebtn").click(function() {
	submitAdvancedCriteriaFilter();                
});







/**********************  CLICK HANDLERS  ********************************/
function initCriteriaInputs_OnClickHandlers() {
	
	 
	 $(".shootingtyperadiobox input[type='radio']").click(function() {
		 //SUBMIT SEARCH ON CLICK ON ANY INPUT FOMR ELEMENT
		 submitAdvancedCriteriaFilter();
	 });

	 $(".videotyperadiobox input[type='radio']").click(function() {
		 //SUBMIT SEARCH ON CLICK ON ANY INPUT FOMR ELEMENT
		 submitAdvancedCriteriaFilter();
	 });
	 
	 
	 $(".weathercheckbox input[value='any']").click(function() {
		 if($(".weathercheckbox input[value='any']").is(':checked')) {
			 $(".weathercheckbox input[value!='any']").prop('checked', false);
		 }
		 //SUBMIT SEARCH ON CLICK ON ANY INPUT FOMR ELEMENT
		 submitAdvancedCriteriaFilter();
		 
	 });
	 

	 $(".weathercheckbox input[value!='any']").click(function() {
			 $(".weathercheckbox input[value='any']").prop('checked', false);
			 
			 //SUBMIT SEARCH ON CLICK ON ANY INPUT FOMR ELEMENT
			 submitAdvancedCriteriaFilter();
	 });
	
	 
	 $(".uploadradiobox input[type='radio']").click(function() {
		 //SUBMIT SEARCH ON CLICK ON ANY INPUT FOMR ELEMENT
		 submitAdvancedCriteriaFilter();
	 });

	 $(".footageradiobox input[type='radio']").click(function() {
		 //SUBMIT SEARCH ON CLICK ON ANY INPUT FOMR ELEMENT
		 submitAdvancedCriteriaFilter();
	 });
	 
       
	 
};


/**********************  HIGHLIGHT CRITERIA BASED ON WHAT FILTER IS SELECTED  ********************************/
function highlightCriteriaMenu(queryData) {
	

	
	var advfilter = queryData.advfilter;

	if(advfilter.uploaddate.any) {$( ".advmenu_uploaddate").switchClass( "text-pink", "-", 0, "easeInOutQuad" );} 
		else {$( ".advmenu_uploaddate").switchClass( "-", "text-pink", 0, "easeInOutQuad" );};

		
		
	if(advfilter.shootingtypeid==0) {$( ".advmenu_shoottype").switchClass( "text-pink", "-", 0, "easeInOutQuad" );} 
	else {$( ".advmenu_shoottype").switchClass( "-", "text-pink", 0, "easeInOutQuad" );};
	
	
	if(advfilter.videotypeid==0) {$( ".advmenu_videotype").switchClass( "text-pink", "-", 0, "easeInOutQuad" );} 
	else {$( ".advmenu_videotype").switchClass( "-", "text-pink", 0, "easeInOutQuad" );};
	
	
	
		
	if(advSelectedTags.length==0) {$( ".advmenu_tags").switchClass( "text-pink", "-", 0, "easeInOutQuad" );} 
	else {$( ".advmenu_tags").switchClass( "-", "text-pink", 0, "easeInOutQuad" );};
		
		
		
};


