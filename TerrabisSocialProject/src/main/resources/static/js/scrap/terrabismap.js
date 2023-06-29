var tableId = '1SZx-TgNmiJgJtcrbAQdd1F-N7QZqJhvJZzkjqRML';


var createMarker = function(coordinate, title, id, markerid) {
    var marker = new google.maps.Marker({
      map: terrabisApp.globalMap,
      position: coordinate,
      icon: new google.maps.MarkerImage('http://www.terrabis.com/images/markers-list/marker-'+markerid+'.png')
    });
    
   /* google.maps.event.addListener(marker, 'click', function(event) {
      infoWindow.setPosition(coordinate);
      infoWindow.setContent(title);
      infoWindow.open(terrabisApp.globalMap);
    });*/
};


function onDataFetchedCallback(response) {
	var rows = response.responseJSON.rows;
    var iconUrl;
    var content;
    var coordinate;

    for (var i in rows) {
      coordinate = new google.maps.LatLng(rows[i][0],rows[i][1]);
      title = rows[i][2];
      id = rows[i][3];
      markerid = rows[i][4];
      if(!markerid)
    	  markerid = 1;
      createMarker(coordinate, title, id, markerid);
    }
};



function initDataMarkers(token) {
	var query = "SELECT latitude, longitude, title, id, markerimgid FROM "+tableId;
    var encodedQuery = encodeURIComponent(query);
	var URL = 'https://www.googleapis.com/fusiontables/v2/query?sql='+encodedQuery+'&access_token='+token;
	
	$.ajax({ 
	    url:  URL, 
	    type: 'GET', 
	    dataType: 'json',
	    contentType: "application/json; charset=utf-8",
	    cache: false, // Force requested pages not to be cached by the browser
	    processData: false, // Avoid making query string instead of JSON	
	    crossDomain :true,
		complete: onDataFetchedCallback,
	    error: function (xhr, status, error) {
	        console.log("restajax:ERROR:"+JSON.stringify(xhr));
	        console.log("restajax:ERROR:"+JSON.stringify(error));
	    }
	}); 
};


/**********************  CREAT MAP BASED ON SEARCH RESULT  ********************************/
function createMapBasedOnSearchResult(GMAPRESULT,mapType,mapID) {


	var map;
	
	var address = GMAPRESULT.address;
	var zoomLevel = GMAPRESULT.zoomlevel;
	var emptyStatus = GMAPRESULT.emptyStatus;
	var fitBounds = GMAPRESULT.fitBounds;
	var reverseGeocode = GMAPRESULT.reverseGeocode;
	var mapCenterLatlng = new google.maps.LatLng(GMAPRESULT.location.lat,GMAPRESULT.location.lng);
	
	if(emptyStatus)
		zoomLevel = 5;
	
		
	var southWest = new google.maps.LatLng(GMAPRESULT.viewport.southwest.lat, GMAPRESULT.viewport.southwest.lng);
	var northEast = new google.maps.LatLng(GMAPRESULT.viewport.northeast.lat, GMAPRESULT.viewport.northeast.lng);
	var viewport = new google.maps.LatLngBounds(southWest,northEast);


	var mapOptions = {
	  center: mapCenterLatlng,
	  zoom:zoomLevel,
	  minZoom: 3,
	  streetViewControl: false,
	  styles: mapStyleArrayWater,
	  mapTypeId: mapType
	  };

	  map = new google.maps.Map(document.getElementById(mapID), mapOptions);

	  
return map;

};



















