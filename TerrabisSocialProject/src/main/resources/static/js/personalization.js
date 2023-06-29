//PERSONALIZATION
function updateGoogleMapType() {
	
	var callback = function(data, status, xhr) {
	};

	var newMapType = terrabisApp.globalMap.getMapTypeId();
	var formData = { googleMapType: newMapType};
	terrabisApp.googleMapType = newMapType; 
	restajax(terrabisApp.PERSGoogleMapTypeURI,formData,callback);	
	
	
}







//PERSONALIZATION
function updateGoogleMapClusterMode(clustererModeValue) {
	var callback = function(data, status, xhr) {
	};

	var formData = { clustererMode: clustererModeValue};
	restajax(terrabisApp.PERSGoogleMapClusterModeURI,formData,callback);	

	
}



function updateGoogleMapShowSearchField(showSearchMapFieldValue) {
	var callback = function(data, status, xhr) {
	};

	var formData = { searchMapField: showSearchMapFieldValue};
	restajax(terrabisApp.PERSGoogleMapShowSearchMapFieldURI,formData,callback);	

	
}