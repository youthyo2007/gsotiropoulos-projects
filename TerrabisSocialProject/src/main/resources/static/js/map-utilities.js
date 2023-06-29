


/**********************  CREATE CIRCLE AROUND A POINT  ********************************/
function createCirlceAroundPoint(map,lat,lng,radius) {
	
	 var cityCircle = new google.maps.Circle({
         strokeColor: '#2B547E',
         strokeOpacity: 0.8,
         strokeWeight: 1,
         fillColor: '#6790BA',
         fillOpacity: 0.35,
         map: map,
         center: new google.maps.LatLng(lat,lng),
         radius: radius

});
	
};





/**********************  CREAT MAP BASED ON SEARCH RESULT OF THE WEB INTERFACE  ********************************/
function createMapFromGMAPRESULT(GMAPRESULT,mapType,mapID) {

	

	
	var map;
	
	var address = GMAPRESULT.address;
	var zoomLevel = GMAPRESULT.zoomlevel;
	var emptyStatus = GMAPRESULT.emptyStatus;
	var isCountry = GMAPRESULT.isCountry;

	var fitBounds = GMAPRESULT.fitBounds;
	var reverseGeocode = GMAPRESULT.reverseGeocode;
	var mapCenterLatlng = new google.maps.LatLng(GMAPRESULT.location.lat,GMAPRESULT.location.lng);
	var southWest = new google.maps.LatLng(GMAPRESULT.viewport.southwest.lat, GMAPRESULT.viewport.southwest.lng);
	var northEast = new google.maps.LatLng(GMAPRESULT.viewport.northeast.lat, GMAPRESULT.viewport.northeast.lng);
	var viewport = new google.maps.LatLngBounds(southWest,northEast);


	
	
	
	var mapOptions = {
	  center: mapCenterLatlng,
	  zoom:zoomLevel,
	  minZoom: parseInt(terrabisApp.minZoomLevel),
	  streetViewControl: false,
	  styles: mapStyleArrayWater,
	  mapTypeId: mapType,
	  gestureHandling: 'greedy',
	  disableDefaultUI: true
	  };

	  map = new google.maps.Map(document.getElementById(mapID), mapOptions);
	  
	  
	  //PRINT CONSOLE LOG
	  console.log("fitBounds:"+fitBounds);
	  
	  //FIT BOUNDS CORRECTS THE PROBLEMS WITH THE ZOOM LEVEL
	  if(fitBounds)
		  map.fitBounds(viewport);

	  
return map;
};


/**********************  CREAT GOOGLE MAP  ********************************/
function createGoogleMap(lat,lng,mapType,mapDivID) {

	
	var map;
	var zoomLevel = 15;
	var mapCenterLatlng = new google.maps.LatLng(lat,lng);
        try {
	         

	        var mapOptions = {
	          zoom:zoomLevel,
	          minZoom: 3,
	          streetViewControl: false,
	          center: mapCenterLatlng,
	          styles: mapStyleArrayWater,
	          gestureHandling: 'greedy',
	          mapTypeId: mapType
              };
	            
	       
	        map = new google.maps.Map(document.getElementById(mapDivID), mapOptions);
            
    } catch (err){
        alert(err);
    }		

	return map;
	
};



/*******************************CREATE MARKER **********************************/
function createGoogleMapMarker(markerPosition,currentmap,markericon,isDraggable) {
	
	var markerImage = new google.maps.MarkerImage(markericon);

	
	var marker = new google.maps.Marker({
            position: markerPosition,  
            map: currentmap, 
            icon: markerImage,
            draggable:isDraggable
   });

	return marker;
	
};


function createDynamicGoogleMapMarker(markerPosition,markerpath,markercolor,markericon,isDraggable) {

	var marker = new Marker({
		map: terrabisApp.globalMap,
		position: markerPosition,
		draggable:isDraggable,
		icon: {
			path: markerpath,
			fillColor: markercolor,
			fillOpacity: 1,
			strokeColor: '',
			strokeWeight: 0
		},
		map_icon_label: '<span class="map-icon '+markericon+'"></span>'
	});


return marker;
	
	
	
}



function createDynamicGoogleMapMarkerWithInfoWindow(markerPosition,markerpath,markercolor,markericon,contentString) {

	
	
	var marker = new Marker({
		map: terrabisApp.globalMap,
		position: markerPosition,
		icon: {
			path: markerpath,
			fillColor: markercolor,
			fillOpacity: 1,
			strokeColor: '',
			strokeWeight: 0
		},
		map_icon_label: '<span class="map-icon '+markericon+'"></span>'
	});
	
		
		var infowindow = new google.maps.InfoWindow({
		    content: contentString
		  });
		
		
		marker.addListener('click', function() {
		    infowindow.open(terrabisApp.globalMap, marker);
		  });
		
		
		
		return marker;
		
	};

	
	
	

function createGoogleMapMarkerWithInfoWindow(markerPosition,currentmap,markericon,contentString) {
	
	var markerImage = new google.maps.MarkerImage(markericon);

	
	var marker = new google.maps.Marker({
            position: markerPosition,  
            map: currentmap, 
            icon: markerImage
   });
	
	
	var infowindow = new google.maps.InfoWindow({
	    content: contentString
	  });
	
	
	marker.addListener('click', function() {
	    infowindow.open(currentmap, marker);
	  });
	
	
	
	return marker;
	
};




/**********************************************************************************************************/
function extractCoordinates(locationtxt) {
	var lat;
	var lng;
	if(locationtxt.indexOf(",") > -1) {
	 lat = locationtxt.substring(0, locationtxt.indexOf(","));
	 lng = locationtxt.substring(locationtxt.indexOf(",")+1,locationtxt.length);
	} else 	if(location.indexOf(" ") > -1) {
		 lat = locationtxt.substring(0, locationtxt.indexOf(" "));
		 lng = locationtxt.substring(locationtxt.indexOf(" ")+1,locationtxt.length);
	}
		
	var location = new google.maps.LatLng(lat,lng);
	return location;
	
};


/******************************************UPDATE FORM COORDINATES OF GEO SEARCH TOOL******************************************/
function updateFormCoordinates(location) {
	$('#latitude').val(location.lat());
	$('#longitude').val(location.lng());
	//$('#searchLocation').val(location.lat()+" "+location.lng());	    

};


/**************************************GEO SEARCH TOOL**************************************************/
function initGeoSearchTool(lat,lng,mapType,mapDivID) {
		//CREATE GOOGLE MAP
		terrabisApp.globalMap = createGoogleMap(lat,lng,mapType,mapDivID);
		
		//CREATE GEOCODER
		terrabisApp.geocoder = new google.maps.Geocoder();
		
		//CREATE MARKER ON MAP CENTER
		terrabisApp.globalMarker = createDynamicGoogleMapMarker(terrabisApp.globalMap.getCenter(),MAP_PIN,'#C082A0','map-icon-circle',true);


		//ADD EVENTS FOR DRAG AND MAP CLICK
		google.maps.event.addListener(terrabisApp.globalMap, "click", function (e) {
			    var location = e.latLng;
			    terrabisApp.globalMarker.setPosition(location);
			    updateFormCoordinates(location);
			});
		
		
		terrabisApp.globalMarker.addListener('dragend', function(e) {
		    var location = e.latLng;
		    updateFormCoordinates(location);
		});
		
	
		/******************* ADD PLACES FUNCTIONALITY ******************************************************************/
		var input =  document.getElementById('footageloctxt');
		var autocomplete = new google.maps.places.Autocomplete(input);
		
		google.maps.event.addListener(autocomplete, 'place_changed', function() {
			terrabisApp.globalPlace = autocomplete.getPlace();
			if(terrabisApp.globalPlace.geometry) 
				showGooglePlaceLocation();
			else
				return;
	    });
		
		
		
		
		/********************ADD GEOCODE FUNCTIONALITY ON ENTER KEY PRESSED OR MOUSE CLICK SEARCH **************************************************************************/
		//ALSO LISTEN TO KEY PRESS EVENT AND IF ENTER ALSO SEARCH LOCATION
		 $('#footageloctxt').keypress(function(event) {
			 if ( event.which == 13 ) {
			     event.preventDefault();

			     //GEOCODE ADDRESS
			     terrabisApp.geocoder.geocode( { 'address': $('#footageloctxt').val()}, function(results, status) {
			         if (status == google.maps.GeocoderStatus.OK) {
			       	  terrabisApp.globalPlace = results[0];
			       	  if(terrabisApp.globalPlace.geometry) 
						showGooglePlaceLocation();			       	  
			         }			     
			     
			     });
			 }

		 });
		 

		 $('#footagelocbutton').click(function()	{	
		    
			 //GEOCODE ADDRESS
		     terrabisApp.geocoder.geocode( { 'address': $('#footageloctxt').val()}, function(results, status) {
		         if (status == google.maps.GeocoderStatus.OK) {
		       	  terrabisApp.globalPlace = results[0];
		       	  if(terrabisApp.globalPlace.geometry) 
					showGooglePlaceLocation();			       	  
		         }			     
		     
		     });
			 
			
		});
		 
		
		
		var showGooglePlaceLocation = function() {
			 var bounds = new google.maps.LatLngBounds();
			 if (terrabisApp.globalPlace.geometry.viewport) {
			        bounds.union(terrabisApp.globalPlace.geometry.viewport);
			 } else {
			        bounds.extend(terrabisApp.globalPlace.geometry.location);
			 }
			terrabisApp.globalMap.fitBounds(bounds);
			terrabisApp.globalMarker.setPosition(terrabisApp.globalMap.getCenter());
		    updateFormCoordinates(terrabisApp.globalMap.getCenter());
			 
		 };		
		
		
		

		
		
	 /*	var zoomMapLocation = function() {
			var location =  extractCoordinates($("#footageloctxt").val().trim());
			terrabisApp.globalMap.setCenter(location);
			terrabisApp.globalMarker.setPosition(location);
		    updateFormCoordinates(location);
			

	};*/



		
	
};







