var markersArray = new Array();


/****************************** ADD REMOVE ITEM FUNCTIONALITY ------------------------------------------------**/ 
if (!Array.prototype.remove) {
	  Array.prototype.remove = function(val) {
	    var i = this.indexOf(val);
	         return i>-1 ? this.splice(i, 1) : [];
	  };
}


//CREATE MARKERS ON MAP
function createMarkersFromList(map,markerslist) {
	
		/******************CLEAR PREVIOUS MARKERS*************************/
		for (var i=0; i<markersArray.length; i++) {
			var vidMarker = markersArray[i];
			vidMarker.setMap(null);
		};
		
		markersArray = new Array();
		/******************CLEAR PREVIOUS MARKERS*************************/
		
		
		/************************CREATE MARKERS***************************************************/
		for (var i=0; i<markerslist.length; i++) {
			var vid = markerslist[i];
			var markerImage = "";
			markerImage = new google.maps.MarkerImage(terrabisApp.googleMarkerImgDynamicURL+vid.categoryid+".png");
			var infowindow_title = vid.title;
			var markerPosition = new google.maps.LatLng(vid.latitude,vid.longitude);
			

			var marker = new google.maps.Marker({
		            position: markerPosition,  
		            map: map, 
		            draggable: false,
		            icon: markerImage,
		            title:infowindow_title
		   });
			marker.vid = vid;	
			markersArray.push(marker); 
		}
		/************************CREATE MARKERS***************************************************/
	
}


//**********************  QUERY CRITERIA AJAX FORM FOR MARKERS  ********************************/
function init_viewPortAjaxQuery(map,limit) {

		var pageview = terrabisApp.pageView; 
		var northeast = map.getBounds().getNorthEast();
		var southwest = map.getBounds().getSouthWest();
		var currentzoomlevel = map.getZoom();
		var currentmaplocation = map.getCenter();


   var queryData = {
  		 	 pageview: pageview,
  		 	 limit:100,
		 	 fitBounds:false,
		 	 zoomlevel:currentzoomlevel,
		 	 gridstyle:terrabisApp.gridViewStyle,
		 	 location:{
 			 	lat:currentmaplocation.lat(),
 			 	lng:currentmaplocation.lng()
 			 },
		 	 bounds: {
    		 	 	northeast: {
    		 	 			lat:northeast.lat(),
    		 	 			lng:northeast.lng()
    		 	 	},
    		 	 	southwest: {
    		 	 			lat:southwest.lat(),
    		 	 			lng:southwest.lng()
    		 	 	}
    		 }		             			 
  };

   
  terrabisApp.queryData = queryData;
  return queryData; 

};



/**********************  MAP FETCH MARKERS  ********************************/
function fetchMarkerDataAndRender(event,map,limit) {
	
	
					  var queryData = init_viewPortAjaxQuery(map,limit);
			
					  $.ajax({ 
		        		    url:  terrabisApp.mapDataMarkersStrictURI, 
		        		    type: 'POST', 
		        		    dataType: 'json',
		        		    contentType: "application/json; charset=utf-8",
		        		    cache: false, // Force requested pages not to be cached by the browser
		        		    processData: false, // Avoid making query string instead of JSON		        		    
		        		    data: JSON.stringify(queryData),
		        		    beforeSend: function (request)
    						{
        						request.setRequestHeader("X-CSRF-TOKEN", terrabisApp.csrf_token);
    						},
		        		    success: function(data) { 
		        		    	var markerslist = data.markerslist;  
		        				if(markerslist.length>0) {
		        					createMarkersFromList(map,markerslist);
		        				}
		        		    },
		        		    error: function (xhr, status, error) {
		        		    //$( "#loadingresults-map-overlay").switchClass( "-", "hidden", 0, "easeInOutQuad" );
		        		        console.log("ajaxFetchMapData:ERROR:"+JSON.stringify(xhr));
		        		        console.log("ajaxFetchMapData:ERROR:"+JSON.stringify(error));
		        		    }
		        		}); 
};






	



















