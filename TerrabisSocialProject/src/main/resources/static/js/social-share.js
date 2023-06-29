function videoShare(socialnetworkvar) {

			var callback = function(data, status, xhr) {
			};
	
			var formData = { videoid: terrabisApp.currentVideo.id, socialnetworkid:socialnetworkvar};
			
			restajax(terrabisApp.shareVideoURI,formData,callback);		
	
};





function create_socialbuttons_html(vid) {

		
var attributes_html = 'data-id="'+vid.id+'" data-image="'+vid.thumburl+'" data-title="'+vid.title+'"';
var imageuri_html = 'https://cache.addthiscdn.com/icons/v2/thumbs/32x32/';
	
var html = [
    		'<img class="share-facebook cursor-pointer mr-sm"'+attributes_html+'src="'+imageuri_html+'facebook.png" border="0" alt="Facebook"/>',
    		'<img class="share-twitter cursor-pointer mr-sm"'+attributes_html+'src="'+imageuri_html+'twitter.png" border="0" alt="Twitter"/>',
    		'<img class="share-pinterest cursor-pointer mr-sm"'+attributes_html+'src="'+imageuri_html+'pinterest.png" border="0" alt="Pinterest"/>',
    		'<img class="share-linkedin cursor-pointer"'+attributes_html+'src="'+imageuri_html+'linkedin.png" border="0" alt="Linkedin"/>'
    		].join(''); 	            
            


return html;

};


/*
 * 	public static Integer FACEBOOK = 1;
	public static Integer TWITTER = 2;
	public static Integer LINKEDIN = 3;
	public static Integer PINTEREST = 4;
 * 
 * 
 * */



$( document ).ready(function() {

	
	
	
	
	var serverURI = 'http://www.terrabis.com/video/';
	//var serverURI = 'http://localhost:8080/video/';
	//var serverURI = 'http://www.terrabis.com/video/map/normal?videoid=';
	
	/******************PIN IT -------------------*/
	$(document).delegate('.share-pinterest', 'click', function() {
		
		videoShare(4);
		
        var videoID = $(this).attr('data-id');
        var urlstring = serverURI+videoID;
        var url = encodeURIComponent(urlstring);
        var media = $(this).attr('data-image');
        var desc = $(this).attr('data-title');
        window.open("//www.pinterest.com/pin/create/button/"+
        "?url="+url+
        "&media="+media+
        "&description="+desc,"_blank","top=0,right=0,width=750,height=320");
        return false; 
    });

	
	
	
	$(document).delegate('.share-facebook', 'click', function() {
		
		videoShare(1);
		
		var videoID = $(this).attr('data-id');
        var urlstring = serverURI+videoID;
        var url = urlstring;
        window.open("https://www.facebook.com/sharer/sharer.php"+
        "?u="+url
         ,"_blank","top=0,right=0,width=750,height=320");
        return false; 
    });
	
	$(document).delegate('.share-twitter', 'click', function() {
		videoShare(2);
		
			var videoID = $(this).attr('data-id');
			var desc = $(this).attr('data-title');
			var tags = $(this).attr('data-tags');
        var urlstring = serverURI+videoID;
        var url = urlstring;
        window.open("http://twitter.com/share"+
        "?url="+url+
         "&text="+desc
         ,"_blank","top=0,right=0,width=750,height=320");
        return false; 
    });	
    
    
	$(document).delegate('.share-linkedin', 'click', function() {
		videoShare(3);
		
    		var videoID = $(this).attr('data-id');
			var desc = $(this).attr('data-title');
		    var urlstring = serverURI+videoID;
        var url = encodeURIComponent(urlstring);
        window.open("https://www.linkedin.com/shareArticle?mini=true"+
        "&url="+url
         ,"_blank","top=0,right=0,width=750,height=320");
        return false; 
    });	
	
	
	
	
	
	

});





	



















