function createHtml_GridResultView_Data(videoListPartions,createMarkers) {
	
	var html = '';
	
	if(terrabisApp.gridViewStyle=='DETAIL')
		html = createHtml_GridResultDetailView(videoListPartions,createMarkers);
	else if(terrabisApp.gridViewStyle=='BOX')
		html = createHtml_GridResultBoxView(videoListPartions,createMarkers);
	else if(terrabisApp.gridViewStyle=='THUMB')
		html = createHtml_GridResultThumbView(videoListPartions,createMarkers);
	else if(terrabisApp.gridViewStyle=='FULLSCREENMAP')
		html = createHtml_GridResultFullScreenMapView(videoListPartions,createMarkers);

	
	return html;
	
}




function createPromotionsOverlay(video) {
	
	var html =   [  '<div id="promooverlaypanel-'+video.id+'" data-id="'+video.id+'" class="row mt-sm mr-sm promooverlaypanel marker-animate -">',
    '<div class="col-md-12 col-xs-12 col-sm-12">',
       '<div class="panel widget bg-inverse w-MD">',
        '<div class="col-md-12 col-xs-12 col-sm-12">',
          '<div class="ph">',
             '<a class="text-gray promooverlay-close cursor-pointer" title="close"><em class="fa text-white fa-close pull-right"></em></a>',
             '<div class="h4 mt0 text-info-light promooverlay_playvidbtn cursor-pointer">'+video.title,
                '<span class="text-sm text-white"></span>',
             '</div>',
          '</div>',
             '<div class="text-sm bg-inverse"><span class="ml-sm text-white">Terrabis Top Picks 2016</span></div>',
			'</div>',
       '</div>',
    '</div>',						          
'</div>'].join('');  
	
	$('.promotionsdiv').append(html);
	
};



function clearPromotionsOverlay() {
	$('.promotionsdiv').html("");
};








function createHtml_GridResultView(videoListPartions,createMarkers) {

	
	var html = '';
	
	if(terrabisApp.gridViewStyle=='DETAIL')
		html = createHtml_GridResultDetailView(videoListPartions,createMarkers);
	else if(terrabisApp.gridViewStyle=='BOX')
		html = createHtml_GridResultBoxView(videoListPartions,createMarkers);
	else if(terrabisApp.gridViewStyle=='THUMB')
		html = createHtml_GridResultThumbView(videoListPartions,createMarkers);
	
	$('#videoGrid').html(html);
	
};



//CREATE PAGINATOR
function createHtml_PaginatorWithPageNumbers(paginator) {
	

	var pageNumbersList = paginator.pageNumbersList;
	
	
	$('.paginator').html("");
	var html = '';
	
	
			html = '<table style="border-collapse: separate; border-spacing: 2px;"><tr>';
			for (var i=0; i<pageNumbersList.length; i++) {
				if(pageNumbersList[i].pageno==(paginator.currentPage+1))
					html = html + '<td class="bg-info-dark" align="center" style="width: 30px;"><span class="text-bold">'+pageNumbersList[i].pageno+'</span></td>';
				else if((pageNumbersList[i].pageno=='AFTER_ONE'))
					html = html + '<td class="" align="center" style="width: 30px;"><span class="text-bold">&nbsp;&nbsp;&nbsp;</span></td>';
				else if((pageNumbersList[i].pageno=='DOTS'))
					html = html + '<td class="bg-gray" align="center" style="width: 30px;"><span class="text-bold">...</span></td>';
				else 
					html = html + '<td class="bg-gray" align="center" style="width: 30px;"><span class="text-bold"><a href="?page='+pageNumbersList[i].pageno+'">'+pageNumbersList[i].pageno+'</a></span></td>';
			}
			html = html +  '</tr></table>';

			$('.paginator').html(html);
	
}



//CREATE PAGINATOR
function createHtml_PaginatorWithPageNumbersStandBy(paginator) {
	$('.paginator').html("");
	var html = '';
	

	if(paginator.totalPages==1)
		html= '<li class="active"><i class=""></i>showing <span class="h4">'+paginator.recordsCount+'</span> videos</li>';
	else
		html= '<li class="active"><i class=""></i>showing <span class="h4">'+(paginator.offset+1)+'</span><span class="h4"> - '+paginator.limit+'</span> from <span class="h4">'+paginator.recordsCount+'</span> videos</li>';
		
	
	var page_html='';
	
	if(paginator.totalPages>1) {
		for (var i=1; i<=paginator.totalPages; i++) {
			if(i==paginator.currentPage)
				page_html+= '<li data-page="'+i+'" class="active animated flash paginator-page cursor-pointer h4"><a href="?page='+i+'">'+i+'</a></li>';
			else
				page_html+= '<li  data-page="'+i+'" class="paginator-page cursor-pointer h4"><a href="?page='+i+'">'+i+'</a></li>';
		}
	}

	html = html+page_html;
	$('.paginator').html(html);
	
}






//CREATE PAGINATOR
function createHtml_Paginator(paginator) {
	$('.paginator').html("");
	var html = '';
	html= '<span class="text-white text-bold">'+paginator.recordsCount+' videos total</span>';
	 $(".paginator").attr("width","20%");
	$('.paginator').html(html);
	
}






function createHtml_EmptyPaginatorPageNumbers() {
	$('.paginator').html("");
	var html = '<li class="active"><i class=""></i><span class="">no videos found...</span></li>';
	$('.paginator').html(html);
}






//LOADING
function createHtml_LoadingPaginator() {
	$('.paginator').html("");
	var html = '';
	var fontColor = terrabisApp.googleMapType=='roadmap' ? 'text-inverse' : 'text-white';

	html= '<span class="'+fontColor+' fa fa-spinner fa-spin"></span><span class="'+fontColor+' text-bold"> loading.....</span>';
	 $(".paginator").attr("width","80%");
	$('.paginator').html(html);
	
}


//FINISHED
function createHtml_FinishedPaginator() {
	$('.paginator').html("");
	var html = '';
	var fontColor = terrabisApp.googleMapType=='roadmap' ? 'text-inverse' : 'text-white';

	html= '<span class="'+fontColor+' fa fa-check"></span><span class="'+fontColor+' text-bold"> map ready</span>';
	 $(".paginator").attr("width","80%");
	$('.paginator').html(html);
	
}






//TOTAL VIDEOS
function createHtml_TotalPaginator(totalvideoscount) {
	$('.paginator').html("");
	var html = '';
	var fontColor = terrabisApp.googleMapType=='roadmap' ? 'text-inverse' : 'text-white';

	html= '<span class="'+fontColor+' text-bold">'+totalvideoscount+' videos total</span>';
	 $(".paginator").attr("width","20%");
	$('.paginator').html(html);
	
}


//SUBSET SET OF VIDEOS
function createHtml_MorePaginator(viewportvideoscount,totalvideoscount) {
	$('.paginator').html("");
	var html = '';
	var fontColor = terrabisApp.googleMapType=='roadmap' ? 'text-inverse' : 'text-white';
	
	
	
	//html= '<span class="text-white fa fa-spinner fa-spin"></span><span class="text-white text-bold"> finishing.....</span>';
	html= '<span class="'+fontColor+' text-bold">*showing '+viewportvideoscount+' from '+totalvideoscount+' videos total. zoom in to view the rest</span>';
	 $(".paginator").attr("width","80%");
	$('.paginator').html(html);
	
}




//EMPTY
function createHtml_EmptyPaginator() {
	$('.paginator').html("");
	var html = '';
	var fontColor = terrabisApp.googleMapType=='roadmap' ? 'text-inverse' : 'text-white';

	html= '<span class="'+fontColor+' text-bold">no videos found...</span>';
	 $(".paginator").attr("width","80%");
	$('.paginator').html(html);
	
}

//EMPTY
function createHtml_CleanPaginator() {
	$('.paginator').html("");
	var html = '';
	html= '<span class="text-white text-bold"></span>';
	 $(".paginator").attr("width","80%");
	$('.paginator').html(html);
	
}



//CREATE RESULT GRID BOX VIEW
function createHtml_GridResultBoxView(videoListPartions,createMarkers) {
	var grid_html = "";
	
	if(createMarkers)
		clearMapFromMarkers();

	
	for (var i=0; i<videoListPartions.length; i++) {
		var VIDEOLIST = videoListPartions[i];
		var videorow_html = "";
		var vid_html = "";
		for (var j=0; j<VIDEOLIST.length; j++) {
			var vid = VIDEOLIST[j];
			vid_html= vid_html + createHtml_BOX(vid);
			
			if(createMarkers)
				createVideoMarkerWithVideoOverlay(vid);
			
		}
		videorow_html='<div id="ITERPOINT_ROW" class="row mt-lg">'+vid_html+'</div>';
		//APPEND VIDEO ROW 
		grid_html+=videorow_html;
		

	}
		
	return grid_html; 

};




//CREATE RESULT GRID THUMB VIEW
function createHtml_GridResultThumbView(videoListPartions,createMarkers) {
	var grid_html = "";
	
	if(createMarkers)
		clearMapFromMarkers();

	
	for (var i=0; i<videoListPartions.length; i++) {
		var VIDEOLIST = videoListPartions[i];
		var videorow_html = "";
		var vid_html = "";
		for (var j=0; j<VIDEOLIST.length; j++) {
			var vid = VIDEOLIST[j];
			vid_html= vid_html + createHtml_THUMB(vid);
			
			if(createMarkers)
				createVideoMarkerWithVideoOverlay(vid);
			
		}
		videorow_html='<div id="ITERPOINT_ROW" class="row mt-lg">'+vid_html+'</div>';
		//APPEND VIDEO ROW 
		grid_html+=videorow_html;
		

	}
		
	return grid_html; 

};






//CREATE RESULT GRID DETAIL VIEW
function createHtml_GridResultDetailView(videoListPartions,createMarkers) {
	var grid_html = "";
	
	if(createMarkers)
		clearMapFromMarkers();

	
	for (var i=0; i<videoListPartions.length; i++) {
		var VIDEOLIST = videoListPartions[i];
		var videorow_html = "";
		var vid_html = "";
		for (var j=0; j<VIDEOLIST.length; j++) {
			var vid = VIDEOLIST[j];
			vid_html=  createHtml_DETAIL(vid);
			
			if(createMarkers)
				createVideoMarkerWithVideoOverlay(vid);
			
			videorow_html='<div><div id="ITERPOINT_ROW" class="row mt-lg bg-gray-lighter">'+vid_html+'</div></div>';

			//APPEND VIDEO ROW 
			grid_html+=videorow_html;			
		}
		
	
		

	}
		
	return grid_html; 

};




function gridviewstyle(panel,iframe) {
	
	var panelstyle = "";
	var iframestyle = "";

	if(terrabisApp.gridViewStyle=='BOX') { panelstyle = "w-MD"; iframestyle = "w-MD h-MD"; };
	if(terrabisApp.gridViewStyle=='DETAIL') { panelstyle = "w-SM"; iframestyle = "w-SM h-SM"; };
	if(terrabisApp.gridViewStyle=='THUMB') { panelstyle = "w-XS"; iframestyle = "w-XS h-XS"; };
	if(terrabisApp.gridViewStyle=='THUMB') { panelstyle = "w-XS"; iframestyle = "w-XS h-XS"; };
	if(terrabisApp.gridViewStyle=='XTRASMALL') { panelstyle = "w-XXS"; iframestyle = "w-XXS h-XXS"; };
	
	
	if(panel)
		return panelstyle;
	else
		return iframestyle;
	
};



function gridviewstyle_fa() {
	
	var fastyle = "";
	
	if(terrabisApp.gridViewStyle=='BOX') { fastyle = "fa-2x"; };
	if(terrabisApp.gridViewStyle=='DETAIL') { fastyle = "fa-2x"; };
	if(terrabisApp.gridViewStyle=='THUMB') { fastyle = " ";};
	if(terrabisApp.gridViewStyle=='XTRASMALL') { fastyle = " ";};
	return fastyle;
	
};



function vidthumbs_html(vid,height) {
	
	
	
	var html = '<div data-id="'+vid.id+'" class="videoimage">';
	var size  = gridviewstyle(false,true);
	
	if(vid.link && vid.vimeo) {
		html = html+'<img class="box-border-info" width="100%" height="'+height+'px" src="'+vid.thumburl+'"/>';
	}
	
	else if (vid.link && vid.youtube) {
		html = html+ '<img class="box-border-info"  width="100%" height="'+height+'px" src="'+vid.thumburl+'"/>';
	}
	else {
		html = html+'<iframe class="'+size+'" src="'+terrabisApp.videoPlayURL+vid.videoURI+'&style=\'detail\'" scrolling="no" frameborder="0" allowfullscreen></iframe>';
	}
	
	html=html+'</div>';
	
	return html;
	
};


function weathericons_html(vid) {
	
	var fasize = gridviewstyle_fa();
	
	var weather_html="";
	var limit = 1000; //SHOW ALL ICONS
	for (i=0; i<vid.weatherList.length; i++) {
		if(vid.weatherList[i]=='sunny' && i<limit) { weather_html += '<div class="col-md-4 col-xs-6 col-sm-6 hidden-sm hidden-xs"><label class="mt-sm text-yellow-dark"><span class="wi wi-day-sunny '+fasize+'"></span></label></div>';}
		if(vid.weatherList[i]=='cloudy' && i<limit) { weather_html += '<div class="col-md-4 col-xs-6 col-sm-6"><label class="mt-sm text-yellow-dark"><span class="wi wi-day-cloudy '+fasize+'"></span></label></div>';}
		if(vid.weatherList[i]=='rain' && i<limit) { weather_html += '<div class="col-md-4 col-xs-6 col-sm-6"><label class="mt-sm text-yellow-dark"><span class="wi wi-day-rain-wind '+fasize+'"></span></label></div>';}
		if(vid.weatherList[i]=='snow' && i<limit) { weather_html += '<div class="col-md-4 col-xs-6 col-sm-6"><label class="mt-sm text-yellow-dark"><span class="wi wi-day-snow '+fasize+'"></span></label></div>';}
		if(vid.weatherList[i]=='windy' && i<limit) { weather_html += '<div class="col-md-4 col-xs-6 col-sm-6"><label class="mt-sm text-yellow-dark"><span class="wi wi-day-cloudy-windy '+fasize+'"></span></label></div>';}
		if(vid.weatherList[i]=='storm' && i<limit) { weather_html += '<div class="col-md-4 col-xs-6 col-sm-6"><label class="mt-sm text-yellow-dark"><span class="wi wi-day-thunderstorm '+fasize+'"></span></label></div>';}
	}

	
	return weather_html; 
		
};


function weatherandlikebtn_html(vid,showlikebutton) {

	/*var likeactionclass = "";
	
	if(terrabisApp.isAuthenticated)
		likeactionclass = " vidaction-like cursor-pointer ";*/
	
	
var fasize = gridviewstyle_fa();




var html= [ '<div class="row text-info">',
   '<div class="col-md-4 col-xs-12 col-sm-12">',
   '<div class="row">',
   		  //weathericons_html(vid), 	
	'</div>',
	'</div>',
   '<div class="col-md-8 col-xs-12 col-sm-12">',
   '<div class="row">',
   '<div class="col-md-3 col-xs-3 col-sm-3 hidden-xs hidden-sm">',
   '<em  class="text-gray fa fa-comment-o '+fasize+'"></em>',
   '<span id="vidcommentstxt_'+vid.id+'" class="text-muted text-sm-sm text-bold">'+(vid.reviewscount > 0 ? vid.reviewscount : "")+'</span>',	               
     /*'<label class="mt-sm"><span class="fa fa-comments fa-2x"></span><br/>'+(vid.reviewscount > 0 ? vid.reviewscount : "")+'</label>',*/
   '</div>',
   '<div class="col-md-3 col-xs-3 col-sm-3 hidden-xs hidden-sm">',
           '<em  class="text-gray fa fa-star '+fasize+'"></em>',
           '<span id="vidratestxt_'+vid.id+'" class="text-muted text-sm-sm text-bold">'+(vid.ratingssum > 0 ? vid.ratingssum : "")+'</span>',	               
             /*'<label class="mt-sm"><span class="fa fa-comments fa-2x"></span><br/>'+(vid.reviewscount > 0 ? vid.reviewscount : "")+'</label>',*/
           '</div>',
            
           '<div class="col-md-3 col-xs-3 col-sm-3 hidden-xs hidden-sm">',
           '<a class="text-white" title="watched">',
           '<em  id="vidcount-play-icon_'+vid.id+'" class="text-gray fa fa-youtube-play '+fasize+' -"></em>',
           '</a><span id="vidplaystxt_'+vid.id+'" class="text-muted text-sm">'+(vid.playscount > 0 ? vid.playscount : "")+'</span>',	               
           
                /*'<label class="mt-sm"><span class="fa fa-youtube-play fa-2x"></span><br/>'+(vid.playscount > 0 ? vid.playscount : "")+'</label>',*/
           '</div>' ].join('');



if(showlikebutton) {

	
	html+= [
	               '<div class="col-md-3 col-xs-12 col-sm-12 pull-right">',
                   '<a class="text-white vidaction-like cursor-pointer" title="like this video">',
                   '<em  class="text-gray fa fa-thumbs-up '+fasize+' -"></em>',
                   '</a><span id="vidlikestxt_'+vid.id+'" class="text-muted text-sm">'+(vid.likescount > 0 ? vid.likescount : "")+'</span>',		               
/*		               '<a href="#"  title="like"><span class="fa fa-thumbs-up fa-2x text-muted vidaction-like cursor-pointer" title="like this video"></span></a>',
	                '<span class="bg-info-dark text-white text-bold ml-sm">'+(vid.likescount > 0 ? vid.likescount : "")+'</span>',
*/		               '</div>'].join('');
}

html+= [ '</div>',          
'</div>',           
'</div>' ].join('');


return html; 
};


function vidoverlay_play_html(vid) {
	
var size  = gridviewstyle(false,true);	
//var size = 'w-XXXL h-XXXL';	
	
var html = [
    		'<div class="row">',
    		'<div class="col-md-12 col-xs-12 col-sm-12">',
    		'<iframe id="vidoverlay_play_iframe'+vid.id+'" class="'+size+'" src="" frameborder="0" allowfullscreen></iframe>',	            
     		'<div class="row row-table vidoverlay_play bg-info-dark">',
            '<div class="col-md-2">',
				     '<a  href="https://www.facebook.com/sharer/sharer.php?u=http://www.terrabis.com/video/'+vid.uuid+'" class="text-blue-dark vidaction-share cursor-pointer" title="share this video">',
					'<em class="fa fa-facebook fa-2x"></em>',
					'</a>',
			  '</div>',
            '<div class="col-md-2">',
               '<a href="/video/'+vid.id+'" class="text-white vidaction-details cursor-pointer" title="details on this video">',
                  '<em class="fa fa-video-camera fa-2x"></em>',
               '</a>',
            '</div>',
        '<div class="col-md-2">',
           '<a  href="/video/map/normal/_?videouuid='+vid.uuid+'" class="text-white cursor-pointer" title="videos around this area">',
              '<em class="fa fa-map-marker fa-2x text-bold"></em>',
           '</a>',
        '</div>',
            '<div class="col-md-2">',
               '<a class="text-white vidaction-favorites cursor-pointer" title="add to favorites">',
                  '<em class="fa fa-heart fa-2x vidaction-favorites-icon"></em>',
               '</a>',
            '</div>',
            '<div class="col-md-2">',
               '<a  class="text-white vidaction-watchlater cursor-pointer" title="watch later">',
                  '<em class="fa  fa-film  fa-2x vidaction-watchlater-icon"></em>',
               '</a>',
            '</div>',		
			'</div>',
			'</div>',
			'</div>'].join(''); 	            
            


return html;


};



function vidoverlay_loadingvideo_html(vid) {

var size  = gridviewstyle(false,true);		
	


var html = [
    		'<div class="row">',
    		'<div class="col-md-12 col-xs-12 col-sm-12">',
    		'<div class="row" style="margin-top: 20% !important;">',
    	    '<div class="col-md-12 col-xs-12 col-sm-12 ">',
    	    '<div class="text-white fa fa-spinner fa-spin fa-5x"></div>',
    	     '</div>',
    	 '</div>',
    	 '</div>',
            '</div>'].join(''); 	            
      
return html;


};




function vidoverlay_action_html(vid) {

var size  = gridviewstyle(false,true);		
	


var html = [ 
            
'<div class="row">',
'<div class="col-md-12 col-xs-12 col-sm-12">',
'	<div style="position: absolute; top: 5px; right: 40px; z-index: 99; opacity:0.9;" title="map location">',
'          <div class="row">',
'    	 <div class="col-md-12  pull-right">',
'    	 <span class="cursor-pointer">',
'			 <button type="button" class="btn btn-info-dark vidaction-map"><span class="fa">&nbsp;<span class="fa  fa-map-marker"></span>&nbsp;</span></button>',
'		</span> ',
'    	 </div>',
'    	 </div> ',
'	',
'	</div>',
'	<div style="position: absolute; top: 40px; right: 40px; z-index: 99; opacity:0.9;" title="add to favorites">',
'          <div class="row">',
'    	 <div class="col-md-12  pull-right">',
 '   	 <span class="cursor-pointer">',
'			 <button type="button" class="btn btn-info-dark vidaction-favorites"><span class="fa"><span class="fa  fa-heart"></span></span></button>',
'		</span> ',
'    	 </div>',
'    	 </div> ',
	'	</div>',
'	<div style="position: absolute; top: 75px; right: 40px; z-index: 99; opacity:0.9;" title="add to watchlater">',
'          <div class="row">',
'    	 <div class="col-md-12  pull-right">',
'    	 <span class="cursor-pointer">',
'			 <button type="button" class="btn btn-info-dark vidaction-watchlater"><span class="fa"><span class="fa fa-film"></span></span></button>',
'		</span>', 
'    	 </div>',
'    	 </div> ',
'	</div>',
'	<div style="position: absolute; top: 110px; right: 40px; z-index: 99; opacity:0.9;" title="share on facebook">',
'          <div class="row">',
'    	 <div class="col-md-12  pull-right">',
'    	 <span class="cursor-pointer">',
'				 <button type="button" class="btn btn-info-dark vidaction-share"><span class="fa">&nbsp;<span class="fa  fa-facebook"></span>&nbsp;</span></button>',
'		</span>', 
'    	 </div>',
'    	 </div> ',
'	</div>',    
'	<div style="position: absolute; top: 145px; right: 40px; z-index: 99; opacity:0.9;" title="like this video">',
'          <div class="row">',
'    	 <div class="col-md-12  pull-right">',
'   	 <span class="cursor-pointer">',
'				 <button type="button" class="btn btn-pink vidaction-like"><span class="fa"><span class="fa  fa-2x fa-thumbs-up"></span></span></button>',
'		</span>', 
'    	 </div>',
'    	 </div> ',
'	</div>',    
'	 		<div  id="vidaction-result_'+vid.id+'" class="vidaction-result full-hidden" style="position: absolute; top: 70px; left: 140px; z-index: 99;">',
'				 <span class="fa fa-film fa-6x text-white"></span>',
'	 </div>',          				
'</div>',
'<div class="col-md-12 col-xs-12 col-sm-12 ml-lg" style="position:absolute; bottom:10px;">',
'<div  id="vidaction-play_'+vid.id+'" class="vidaction-play - pull-left ml-lg">',
'		<button  type="button" class="btn btn-labeled   btn-info-dark vidoverlay_playvidbtn">',
'			<span  class="h4 mt0 text-uppercase text-left text-danger">&nbsp;&nbsp;<i class="fa bg-playvidicon-dark fa-1point7x fa-play m-sm"></i>&nbsp;&nbsp;',
'			</span>',
'        </button>',						               
'		 </div>',
'</div>',
'</div>'].join(''); 	            



return html;



/*
var html = [
    		'<div class="row">',
    		'<div class="col-md-12 col-xs-12 col-sm-12">',
    		'<div class="row row-table vidoverlay_actionbar bg-videobar-dark">',
	            '<div class="col-md-2">',
	               '<a href="/video/'+vid.uuid+'" class="text-gray vidaction-details cursor-pointer" title="video information">',
	                  '<em class="fa fa-television fa-2x"></em>',
	               '</a>',
	            '</div>',
        '<div class="col-md-2">',
           '<a  href="/video/map/normal/_?videouuid='+vid.uuid+'" class="text-gray cursor-pointer" title="videos around this area">',
              '<em class="fa fa-map-marker fa-2x text-bold"></em>',
           '</a>',
        '</div>',
            '<div class="col-md-2">',
               '<a class="text-gray vidaction-favorites cursor-pointer" title="add to favorites">',
                  '<em class="fa fa fa-heart fa-2x"></em>',
               '</a>',
            '</div>',
            '<div class="col-md-2">',
               '<a  class="text-gray vidaction-watchlater cursor-pointer" title="watch later">',
                  '<em class="fa fa-film fa-2x"></em>',
               '</a>',
            '</div>',		
            '</div>',
    		'<div class="row" style="margin-top: 20% !important;">',
               '<div class="col-md-12 col-xs-12 col-sm-12 border_5x">',
     				'<button  type="button" class="btn btn-labeled bg-info-dark vidoverlay_playvidbtn">',
							'<span  class="h4 mt0 text-uppercase text-left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<i class="fa  fa-play fa-1point7x m-sm"></i>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;',
							'</span>',
			            '</button>',						               
               '</div>',
            '</div>',                
            '</div>',
            '</div>'].join(''); 	            
            


return html;
*/




};


function vidoverlay_action_mapthumb_html(vid) {

	var size  = gridviewstyle(false,true);		
	
/*	
	var favactionclass="";
	var watchactionclass="";
	
	if(terrabisApp.isAuthenticated) {
		favactionclass = " vidaction-favorites cursor-pointer ";
		watchactionclass = " vidaction-watchlater cursor-pointer ";
			
	}
	*/
		
	var html = [
	    		'<div class="row">',
	    		'<div class="col-md-12 col-xs-12 col-sm-12">',
	    		'<div class="row row-table vidoverlay_actionbar bg-videobar-dark">',
/*	            '<div class="col-md-2">',
					     '<a  href="https://www.facebook.com/sharer/sharer.php?u=http://www.terrabis.com/video/'+vid.id+'" class="text-info vidaction-share cursor-pointer" title="share this video">',
						'<em class="fa fa-facebook"></em>',
						'</a>',
				  '</div>',*/
		            '<div class="col-md-2">',
		               '<a href="/video/'+vid.uuid+'" class="text-gray vidaction-details cursor-pointer" title="video information">',
		                  '<em class="fa fa-television"></em>',
		               '</a>',
		            '</div>',
	        '<div class="col-md-2">',
	           '<a  href="/video/map/normal/_?videouuid='+vid.uuid+'" class="text-gray  cursor-pointer" title="videos around this area">',
	              '<em class="fa fa-map-marker text-bold"></em>',
	           '</a>',
	        '</div>',
	            '<div class="col-md-2">',
	               '<a class="text-gray vidaction-favorites cursor-pointer" title="add to favorites">',
	                  '<em class="fa fa-film"></em>',
	               '</a>',
	            '</div>',
	            '<div class="col-md-2">',
	               '<a  class="text-gray vidaction-watchlater cursor-pointer" title="watch later">',
	                  '<em class="fa fa-heart"></em>',
	               '</a>',
	            '</div>',		
	            '</div>',               
	            '</div>',
	            '</div>'].join(''); 	            
	            


	return html;


	};


	
	function createHtml_THUMB_SINGLECOLUMN(vid) {
		var html =   [ '<div ID="ITERPOINT_COLUMN" class="col-lg-3 col-md-3 col-xs-12 col-sm-4">',
		'<div  data-likescount="'+vid.likescount+'" data-playscount="'+vid.playscount+'" data-ratingssum="'+vid.ratingssum+'" data-title="'+vid.title+'" data.shortAndSmart_DATECREATED="'+vid.shortAndSmart_DATECREATED+'" data-description="'+vid.description+'" data-uuid="'+vid.uuid+'" data-id="'+vid.id+'" data-videourl="'+vid.videourl+'" class="panel widget w-XS h-XS videopanel" style="background: none;">',
			'<div class="text-center bg-center" id="subpanelblockA">',
		'<div class="row">',
				'<div class="col-xs-12 col-md-12 col-sm-12">',
/*			    '<div class="vidoverlay_play hidden" id="vidoverlay_play_'+vid.id+'">',
			     vidoverlay_play_html(vid),
			    '</div>',*/
			    '<div class="vidoverlay_loadingvideo hidden" id="video-overlay-loadingvideo_'+vid.id+'">',
		    	vidoverlay_loadingvideo_html(vid),
		    	'</div>',
				'<div class="vidoverlay_action bg-info-fade hidden" id="vidoverlay_action_'+vid.id+'">',
			    vidoverlay_action_html(vid),
			    '</div>',
			    vidthumbs_html(vid),
			'</div>',
		'</div>',
		'</div>',

		'<div class="row mt-sm" id="subpanelblockB">',
		 '<div class="col-md-10 col-xs-12 col-sm-12">',
		  '<div class="h4 m0 text-bold text-inverse">'+vid.title+'</div>',
		'</div>',
		'<div class="col-md-2 col-xs-12 col-sm-12">',
		 '<div class="pull-right">',
		   			'<span class="bg-info-dark text-white text-bold text-sm" title="duration">'+vid.durationtxt+'</span>',
		'</div>',				                           
		'</div>',
		'</div>',

		viduseranddate(vid,'m0 text-sm'),

		'<div class="row" id="subpanelblockD">',
		 '<div class="col-md-12 col-xs-12 col-sm-12 font14">',
		 					weatherandlikebtn_html(vid,true),
							'</div>',
		'</div>',		                    
		'</div>',
		'</div>'  ].join('');


		//alert(JSON.stringify(vid.user));
		return html;


	};
	
	




function createHtml_THUMB(vid) {
	var html =   [ '<div ID="ITERPOINT_COLUMN" class="col-lg-3 col-md-3 col-xs-12 col-sm-4">',
	'<div  data-likescount="'+vid.likescount+'" data-playscount="'+vid.playscount+'" data-ratingssum="'+vid.ratingssum+'" data-title="'+vid.title+'" data.shortAndSmart_DATECREATED="'+vid.shortAndSmart_DATECREATED+'" data-description="'+vid.description+'" data-uuid="'+vid.uuid+'" data-id="'+vid.id+'"  data-videourl="'+vid.videourl+'" class="panel widget videopanel" style="background: none; width:100%;">',
		'<div class="text-center bg-center" id="subpanelblockA">',
	'<div class="row">',
			'<div class="col-xs-12 col-md-12 col-sm-12">',
		    '<div class="vidoverlay_play hidden" id="vidoverlay_play_'+vid.id+'">',
		     vidoverlay_play_html(vid),
		    '</div>',
		    '<div class="vidoverlay_loadingvideo hidden" id="video-overlay-loadingvideo_'+vid.id+'">',
		    	vidoverlay_loadingvideo_html(vid),
		    '</div>',
		    '<div class="vidoverlay_action bg-info-fade hidden" id="vidoverlay_action_'+vid.id+'">',
		    vidoverlay_action_html(vid),
		    '</div>',
		    vidthumbs_html(vid,280),
		'</div>',
	'</div>',
	'</div>',

	'<div class="row mt-sm" id="subpanelblockB">',
	 '<div class="col-md-10 col-xs-12 col-sm-12">',
	  '<div class="h4 m0 text-bold text-inverse">'+vid.title+'</div>',
	'</div>',
	'<div class="col-md-2 col-xs-12 col-sm-12">',
	 '<div class="pull-right">',
	   			'<span class="bg-info-dark text-white text-bold text-sm" title="duration">'+vid.durationtxt+'</span>',
	'</div>',				                           
	'</div>',
	'</div>',

	viduseranddate(vid,'m0 text-sm'),

	'<div class="row" id="subpanelblockD">',
	 '<div class="col-md-12 col-xs-12 col-sm-12 font14">',
	 					weatherandlikebtn_html(vid,true),
						'</div>',
	'</div>',		                    
	'</div>',
	'</div>'  ].join('');


	//alert(JSON.stringify(vid.user));
	return html;


};
  

function viduseranddate(vid,style) {
	var html =   [
	          	'<div class="row" id="subpanelblockC">',
	       	 '<div class="col-md-12 col-sm-12 col-xs-12">',
	       	  '<p class="'+style+'">'].join('');
	
	
	var owner_html='';
	if(!vid.ownershipverified)
		owner_html = 'from <a href="/user-'+vid.user.uuid+'/"><span>'+vid.user.shortdesc+'</span> </a>';
	else
		owner_html = 'made by <a href="/user-'+vid.user.uuid+'/"><span>'+vid.user.shortdesc+'</span></a>';
		
	
	var datecreated_html = '<span class="time_badge"> added <span class="text-bold text-inverse">'+vid.shortAndSmart_DATECREATED+'</span>  </span>';
	
	var footer_html =   [
	       	  '</p>',
	       	'</div>',
	       	'</div>' 
	              ].join('');

	return html+datecreated_html+owner_html+footer_html;

};
	

function createHtml_BOX(vid) {
var html =   [ '<div ID="ITERPOINT_COLUMN" class="col-lg-4 col-md-4 col-xs-12 col-sm-6">',
'<div data-likescount="'+vid.likescount+'" data-playscount="'+vid.playscount+'" data-ratingssum="'+vid.ratingssum+'" data.shortAndSmart_DATECREATED="'+vid.shortAndSmart_DATECREATED+'" data-description="'+vid.description+'" data-uuid="'+vid.uuid+'" data-title="'+vid.title+'" data-id="'+vid.id+'" data-videourl="'+vid.videourl+'" class="panel widget videopanel" style="background: none; width: 100%">',
	'<div class="text-center bg-center" id="subpanelblockA">',
'<div class="row">',
		'<div class="col-xs-12 col-md-12 col-sm-12">',
	    '<div class="vidoverlay_play hidden" id="vidoverlay_play_'+vid.id+'">',
	     vidoverlay_play_html(vid),
	    '</div>',
	    '<div class="vidoverlay_loadingvideo hidden" id="video-overlay-loadingvideo_'+vid.id+'">',
    		vidoverlay_loadingvideo_html(vid),
    	'</div>',
	    
	    '<div class="vidoverlay_action bg-info-fade hidden" id="vidoverlay_action_'+vid.id+'">',
	    vidoverlay_action_html(vid),
	    '</div>',
	    vidthumbs_html(vid,300),
	'</div>',
'</div>',
'</div>',

'<div class="row mt-sm" id="subpanelblockB">',
 '<div class="col-md-10 col-xs-12 col-sm-12">',
  '<div class="h4 m0 text-bold text-inverse">'+vid.title+'</div>',
'</div>',
'<div class="col-md-2 col-xs-12 col-sm-12">',
 '<div class="pull-right">',
   			'<span class="bg-info-dark text-white text-bold text-sm" title="duration">'+vid.durationtxt+'</span>',
'</div>',				                           
'</div>',
'</div>',

viduseranddate(vid,'m0 text-sm'),

'<div class="row" id="subpanelblockD">',
 '<div class="col-md-12 col-xs-12 col-sm-12 font14">',
 					weatherandlikebtn_html(vid,true),
					'</div>',
'</div>',		                    
'</div>',
'</div>'  ].join('');


//alert(JSON.stringify(vid.user));
return html;


};


function createHtml_DETAIL(vid) {
/*	
	if(terrabisApp.isAuthenticated)
		likeactionclass = " vidaction-like cursor-pointer ";
	*/
	var html =   [ '<div ID="LEFT_COLUMN" class="col-lg-5 col-md-5 col-xs-12 col-sm-12">',
	'<div id="panelblock" data-likescount="'+vid.likescount+'" data-playscount="'+vid.playscount+'" data-ratingssum="'+vid.ratingssum+'" data-title="'+vid.title+'" data.shortAndSmart_DATECREATED="'+vid.shortAndSmart_DATECREATED+'" data-description="'+vid.description+'" data-uuid="'+vid.uuid+'" data-id="'+vid.id+'" data-videourl="'+vid.videourl+'" class="panel widget w-SM videopanel" style="background: none; width: 100%"">',
		'<div class="text-center bg-center" id="subpanelblockA">',
	'<div class="row">',
			'<div class="col-xs-12 col-md-12 col-sm-12">',
		    '<div class="vidoverlay_play hidden" id="vidoverlay_play_'+vid.id+'">',
		     vidoverlay_play_html(vid),
		    '</div>',
		    '<div class="vidoverlay_loadingvideo hidden" id="video-overlay-loadingvideo_'+vid.id+'">',
		    vidoverlay_loadingvideo_html(vid),
		    '</div>',
		    '<div class="vidoverlay_action bg-info-fade hidden" id="vidoverlay_action_'+vid.id+'">',
		    vidoverlay_action_html(vid),
		    '</div>',
		    vidthumbs_html(vid,340),
		'</div>',
	'</div>',
	'</div>',
	'<div class="row" id="subpanelblockB">',
	 '<div class="col-md-12 col-xs-12 col-sm-12">',
	  '<div class="pull-right">',	
     	'<a  class="vidaction-like cursor-pointer" title="like"><em class="fa fa-thumbs-up fa-2x text-gray" title="like"></em></a>',
      '<span id="vidlikestxt_'+vid.id+'" class="text-muted text-sm">'+vid.likescount+'</span>',
      '</div>',	 
	 '<div class="pull-left mt-sm">',
	   			'<span class="bg-info-dark text-white text-bold h4" title="duration">'+vid.durationtxt+'</span>',
	'</div>',
	'</div>',	
	'</div><!-- END SUBPANEL B-->',
	'</div>',
	'</div>',	
	 '<div ID="RIGHT_COLUMN" class="col-lg-7 col-md-7 col-xs-12 col-sm-12">',
		'<div class="row">',
                     '<div class="col-md-12 col-xs-12 col-sm-12">',
                      '<div class="h3 m0 text-bold text-inverse">'+vid.title+'</div>',
                   '</div>',
            '</div>',
            viduseranddate(vid,'h4 text-inverse'),
			'<div class="row mt-sm">',
                     '<div class="col-md-12 col-xs-12 col-sm-12">',
                      '<div class="tex-sm">'+vid.description+'</div>',
                   '</div>',
            '</div>',		                    
        	'<div class="row mt-sm">',
                     '<div class="col-md-12 col-xs-12 col-sm-12">',
							  weatherandlikebtn_html(vid,false),
           			'</div>',
        '</div>',
        '</div> <!-- END RIGHT COLUMN  -->',	
		].join('');
	return html;
};

