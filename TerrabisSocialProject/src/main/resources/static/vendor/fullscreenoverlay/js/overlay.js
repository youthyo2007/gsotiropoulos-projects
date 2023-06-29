

/**OVERLAY JAVASCRIPT **/
function toggleOverlay() {
		if( classie.has( overlay, 'open' ) ) {
			classie.remove( overlay, 'open' );
			classie.add( overlay, 'close' );
			var onEndTransitionFn = function( ev ) {
				if( support.transitions ) {
					if( ev.propertyName !== 'visibility' ) return;
					this.removeEventListener( transEndEventName, onEndTransitionFn );
				}
				classie.remove( overlay, 'close' );
			};
			if( support.transitions ) {
				overlay.addEventListener( transEndEventName, onEndTransitionFn );
			}
			else {
				onEndTransitionFn();
			}
		}
		else if( !classie.has( overlay, 'close' ) ) {
			classie.add( overlay, 'open' );
		}
};

function fullScreenOverlayWithImage(actionform,imageurl, actiontitletext,actionbuttontext) {
    //var triggerBttn = document.getElementsByClassName( 'trigger-overlay' )[0],
	overlay = document.querySelector( 'div.overlay' ),
	closeBttn = overlay.querySelector( 'button.overlay-close' );
    closeSecondBttn = overlay.querySelector( 'button.overlay-close-second' );

    
    
    if(imageurl=="-")
    	$("#overlayactionvidimage").hide();
    else
    	$("#overlayactionvidimage").show();
    
  
     
    $("#overlayactionvidimage").attr( "src", imageurl);
    $("#overlayactiontitletext").html(actiontitletext);
    $("#overlayactionbtntext").html(actionbuttontext);
    
    

    
    
	transEndEventNames = {
		'WebkitTransition': 'webkitTransitionEnd',
		'MozTransition': 'transitionend',
		'OTransition': 'oTransitionEnd',
		'msTransition': 'MSTransitionEnd',
		'transition': 'transitionend'
	},
	transEndEventName = transEndEventNames[ Modernizr.prefixed( 'transition' ) ],
	support = { transitions : Modernizr.csstransitions };



//triggerBttn.addEventListener( 'click', toggleOverlay );
toggleOverlay();
closeBttn.addEventListener( 'click', toggleOverlay );
closeSecondBttn.addEventListener( 'click', toggleOverlay );

$(document).delegate('.overlayactionbtn', 'click', function() {
	document.getElementById(actionform).submit();
});

};




function fullScreenOverlayUpload() {
    //var triggerBttn = document.getElementsByClassName( 'trigger-overlay' )[0],
	overlay = document.querySelector( 'div.overlay-upload' ),
	closeBttn = overlay.querySelector( 'button.overlay-upload-close' );

	
    
    
	transEndEventNames = {
		'WebkitTransition': 'webkitTransitionEnd',
		'MozTransition': 'transitionend',
		'OTransition': 'oTransitionEnd',
		'msTransition': 'MSTransitionEnd',
		'transition': 'transitionend'
	},
	transEndEventName = transEndEventNames[ Modernizr.prefixed( 'transition' ) ],
	support = { transitions : Modernizr.csstransitions };


toggleOverlay();


closeBttn.addEventListener( 'click', function(){
	toggleOverlay();
});


};





function fullScreenOverlayCollectionPlay(collection) {
    //var triggerBttn = document.getElementsByClassName( 'trigger-overlay' )[0],
	overlay = document.querySelector( 'div.overlay-videoplay' ),
	closeBttn = overlay.querySelector( 'button.overlay-close' );
	title = overlay.querySelector( 'span.fullcollplay_title'); 
    description = overlay.querySelector( 'span.fullcollplay_description'); 
    gallery = overlay.querySelector( 'div.fullcollplay_gallery'); 
    

    
    
    var gallery_html="";
    
    for(i=0; i<collection.videos.length; i++) {
    	var vid = collection.videos[i];
    	var videourl = vid.videourl+"?autoplay=1&modestbranding=1&showinfo=0&rel=0&title=0&byline=0&portrait=0";
    	gallery_html = gallery_html+'<img class="rsImg" src="'+vid.thumburl+'" data-rsVideo="'+videourl+'"/>';
    };
    
    
    
    
    $(title).html(collection.title);
    $(description).html(collection.description);
    $(gallery).html(gallery_html);

    
    console.log(gallery_html);
    
    $(gallery).royalSlider({
    	// general options go gere
    	autoScaleSlider: false,
    	autoPlay: {
    		enabled: false,
    		pauseOnHover: false
    	}
    });  
    
    		
    		
    		
	transEndEventNames = {
		'WebkitTransition': 'webkitTransitionEnd',
		'MozTransition': 'transitionend',
		'OTransition': 'oTransitionEnd',
		'msTransition': 'MSTransitionEnd',
		'transition': 'transitionend'
	},
	transEndEventName = transEndEventNames[ Modernizr.prefixed( 'transition' ) ],
	support = { transitions : Modernizr.csstransitions };


toggleOverlay();



closeBttn.addEventListener( 'click', function(){
	$(gallery).html("");
	toggleOverlay();
});



};



function fullScreenOverlayVideoPlay(vid) {
    //var triggerBttn = document.getElementsByClassName( 'trigger-overlay' )[0],
	overlay = document.querySelector( 'div.overlay-videoplay' ),
	closeBttn = overlay.querySelector( 'button.overlay-close' );
    iframevideoplay = overlay.querySelector( 'iframe.fullvideoplay' ); 
    ratingsum = overlay.querySelector('span.fullvideoplay_rating'); 
    playscount = overlay.querySelector( 'span.fullvideoplay_playscount'); 
    likescount = overlay.querySelector( 'span.fullvideoplay_likescount'); 
    videotitle = overlay.querySelector( 'span.fullvideoplay_title'); 
    videodescription = overlay.querySelector( 'span.fullvideoplay_description'); 
    username = overlay.querySelector( 'span.fullvideoplay_username'); 
    datecreated = overlay.querySelector( 'span.fullvideoplay_date'); 
    socialbuttons = document.querySelector( 'div.social_buttons'),
    embedd_link = overlay.querySelector( 'span.fullvideoplay_link_text'); 
    $(".fullvideoplay_link").switchClass("-", "hidden", 0, "easeInOutQuad" );
    uploaderuri = overlay.querySelector( 'a.fullvideoplay_uploaderuri'); 		
    editvideouri = overlay.querySelector( 'a.fullvideoplay_edituri'); 
    claimofownershipdiv = overlay.querySelector( 'div.fullvideoplay_claimofownership'); 		
    claimofownershipuri = overlay.querySelector( 'a.fullvideoplay_claimofownershipuri'); 		
    
    ownerdiv = overlay.querySelector( 'div.fullvideoplay_ownerdiv'); 
    username_onwer = overlay.querySelector( 'span.fullvideoplay_ownerusername'); 
    owneruri = overlay.querySelector( 'a.fullvideoplay_owneruri'); 
    gmap = overlay.querySelector( 'img.fullvideoplay_gmap');
    
    
    if(!vid.ownershipverified && !vid.isfile && !vid.vimeo) {
   	 	$(claimofownershipdiv).switchClass("hidden", "-", 0, "easeInOutQuad" );
    } else {
   	 	$(claimofownershipdiv).switchClass("-", "hidden", 0, "easeInOutQuad" );
    }
    
    if(vid.ownershipverified) {
    	 $(ownerdiv).switchClass("hidden", "-", 0, "easeInOutQuad" );
    	 $(username_onwer).html(vid.user.username);
    	 $(owneruri).attr("href",terrabisApp.userInfoURI+vid.user.uuid);
    	 $(username).html(vid.uploader.username);
    	 $(uploaderuri).attr("href",terrabisApp.userInfoURI+vid.uploader.uuid);
    } else {
    	$(ownerdiv).switchClass("-", "hidden", 0, "easeInOutQuad" );
    	$(username).html(vid.user.username);
    	$(uploaderuri).attr("href",terrabisApp.userInfoURI+vid.user.uuid);
    }
    		
    
    $(editvideouri).attr("href",terrabisApp.adminEditVideoURI+vid.id+"?redirect="+ terrabisApp.mapURI);
    
    
    
    
    
	transEndEventNames = {
		'WebkitTransition': 'webkitTransitionEnd',
		'MozTransition': 'transitionend',
		'OTransition': 'oTransitionEnd',
		'msTransition': 'MSTransitionEnd',
		'transition': 'transitionend'
	},
	transEndEventName = transEndEventNames[ Modernizr.prefixed( 'transition' ) ],
	support = { transitions : Modernizr.csstransitions };


toggleOverlay();

$(iframevideoplay).attr( "src", vid.videourl+"?autoplay=1&modestbranding=1&showinfo=0&rel=0&title=0&byline=0&portrait=0");
$(videotitle).html(vid.title);
$(videodescription).html(vid.description);
$(datecreated).html(vid.shortAndSmart_DATECREATED);
$(ratingsum).html(vid.ratingssum);
$(playscount).html(vid.playscount);
$(likescount).html(vid.likescount);
$(socialbuttons).html(create_socialbuttons_html(vid));
$(embedd_link).html("http://www.terrabis.com/video/"+vid.id);
$(claimofownershipuri).attr("href",terrabisApp.videoClaimURI+vid.id);
$(gmap).attr( "src", vid.staticMapURI+"&zoom=8&size=900x200&scale=1&maptype=hybrid&key=AIzaSyBrqn9Rl-MX3yJPDKGXL_Ly7ej6DSC0sRw");







closeBttn.addEventListener( 'click', function(){
	$(iframevideoplay).attr( "src", "");
	$(ownerdiv).switchClass("-", "hidden", 0, "easeInOutQuad" );
	toggleOverlay();
	
	 setTimeout(stopAnimationCurrentMapMarker, 1000);
	 
	
});



};




function stopAnimationCurrentMapMarker() {
	
	terrabisApp.currentMapMarker.setAnimation(null);
	
	
}




function fullScreenOverlayReject(actionform,imageurl,actiontitletext,actionbuttontext) {
	

    
    if(imageurl=="-")
    	$("#overlayactionvidimage-reject").hide();
    else
    	$("#overlayactionvidimage-reject").show();
    
    	
    //var triggerBttn = document.getElementsByClassName( 'trigger-overlay' )[0],
	overlay = document.querySelector( 'div.overlay-reject' ),
	closeBttn = overlay.querySelector( 'button.overlay-close' );
    closeSecondBttn = overlay.querySelector( 'button.overlay-close-second' );
    
    $("#overlayactionvidimage-reject").attr( "src", imageurl);
    $("#overlayactiontitletext-reject").html(actiontitletext);
    $("#overlayactionbtntext-reject").html(actionbuttontext);
    
    
    

    
	transEndEventNames = {
		'WebkitTransition': 'webkitTransitionEnd',
		'MozTransition': 'transitionend',
		'OTransition': 'oTransitionEnd',
		'msTransition': 'MSTransitionEnd',
		'transition': 'transitionend'
	},
	transEndEventName = transEndEventNames[ Modernizr.prefixed( 'transition' ) ],
	support = { transitions : Modernizr.csstransitions };



//triggerBttn.addEventListener( 'click', toggleOverlay );
toggleOverlay();
closeBttn.addEventListener( 'click', toggleOverlay );
closeSecondBttn.addEventListener( 'click', toggleOverlay );

$(document).delegate('.overlayactionbtn', 'click', function() {
	document.getElementById(actionform).submit();
});

};




function fullScreenOverlay(actionform,actiontitletext,actionbuttontext) {
	    //var triggerBttn = document.getElementsByClassName( 'trigger-overlay' )[0],
		overlay = document.querySelector( 'div.overlay' ),
		closeBttn = overlay.querySelector( 'button.overlay-close' );
	    closeSecondBttn = overlay.querySelector( 'button.overlay-close-second' );
	    
	    
	    $("#overlayactiontitletext").html(actiontitletext);
	    $("#overlayactionbtntext").html(actionbuttontext);
	    
	    

	    $("#overlayactionvidimage").hide();
	
	    
		transEndEventNames = {
			'WebkitTransition': 'webkitTransitionEnd',
			'MozTransition': 'transitionend',
			'OTransition': 'oTransitionEnd',
			'msTransition': 'MSTransitionEnd',
			'transition': 'transitionend'
		},
		transEndEventName = transEndEventNames[ Modernizr.prefixed( 'transition' ) ],
		support = { transitions : Modernizr.csstransitions };



	//triggerBttn.addEventListener( 'click', toggleOverlay );
	toggleOverlay();
	closeBttn.addEventListener( 'click', toggleOverlay );
	closeSecondBttn.addEventListener( 'click', toggleOverlay );

	$(document).delegate('.overlayactionbtn', 'click', function() {
		document.getElementById(actionform).submit();
	});

};