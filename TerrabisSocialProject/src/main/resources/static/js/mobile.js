
terrabisApp.searchMapField = false;


/*************************************TOGGLE SEARCH FIELD MOBILE VERSION************************************/
$(document).delegate('.sidebar-mobile-toggle-search', 'click', function() {
	if(!terrabisApp.searchMapField) {

		$("#searchform").switchClass("hidden", "-", 0, "easeInOutQuad" );
		$("#searchform").fadeIn( "slow", function() {
		  });
		terrabisApp.searchMapField = true;

		
	}
	else {
		$("#searchform").animate({
		      opacity: 'hide', // animate fadeOut
		      width: 'hide'  // animate slideLeft
		    }, 'slow', 'linear', function() {
		     
		    });
		$("#searchform").switchClass("-", "hidden", 0, "easeInOutQuad" );
		terrabisApp.searchMapField = false;


	}
});



/*************************************TOGGLE MAPTYPE  FIELD************************************/
$(document).delegate('.sidebar-mobile-toggle-maptype', 'click', function() {
	
	if(terrabisApp.googleMapType=='roadmap') {
		terrabisApp.googleMapType=='hybrid';
		terrabisApp.globalMap.setMapTypeId("hybrid");
		$( ".radio-inline" ).switchClass("text-info", "text-white", 0, "easeInOutQuad" );
		

		

	}
	else {
		terrabisApp.googleMapType=='roadmap';
		terrabisApp.globalMap.setMapTypeId("roadmap");
		$( ".radio-inline" ).switchClass("text-white", "text-info", 0, "easeInOutQuad" );

	}
});



/*************************************TOGGLE CLUSTERMAP************************************/
$(document).delegate('.sidebar-mobile-toggle-clustermap', 'click', function() {
	if(terrabisApp.clusterMap) {
		 clearClusterMapVersion();
		 updateGoogleMapClusterMode(false);


	}
	else {
		 showClusterMapVersion();
		 updateGoogleMapClusterMode(true);
	}
});










