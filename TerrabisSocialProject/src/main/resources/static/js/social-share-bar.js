function videoShareBar(socialnetworkvar) {

			var callback = function(data, status, xhr) {
			};
	
			var formData = { videoid: -1, socialnetworkid:socialnetworkvar};
			
			restajax(terrabisApp.shareVideoURI,formData,callback);		
	
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


	
	
	
	var serverURI = 'http://www.terrabis.com';
	//var serverURI = 'http://localhost:8080/video/';
	//var serverURI = 'http://www.terrabis.com/video/map/normal?videoid=';
	
	/******************PIN IT -------------------*/
	$(document).delegate('.share-pinterest-bar', 'click', function() {
		
		videoShareBar(4);
		
   
        var urlstring = serverURI;
        var url = encodeURIComponent(urlstring);
		var desc = "Terrabis - Our planet re-discovered";
        window.open("//www.pinterest.com/pin/create/button/"+
        "?url="+url+
/*        "&media="+media+*/
        "&description="+desc,"_blank","top=0,right=0,width=750,height=320");
        return false; 
    });

	
	
	
	$(document).delegate('.share-facebook-bar', 'click', function() {

		
		videoShareBar(1);
		
		var desc = "Terrabis - Our planet re-discovered";
        var urlstring = serverURI;
        var url = urlstring;
        window.open("https://www.facebook.com/sharer/sharer.php"+
        "?u="+url
         ,"_blank","top=0,right=0,width=750,height=320");
        return false; 
    });
	
	$(document).delegate('.share-twitter-bar', 'click', function() {
		videoShareBar(2);
		
		var desc = "Terrabis - Our planet re-discovered";
        var urlstring = serverURI;
        var url = urlstring;
        window.open("http://twitter.com/share"+
        "?url="+url+
         "&text="+desc
         ,"_blank","top=0,right=0,width=750,height=320");
        return false; 
    });	
    
    
	$(document).delegate('.share-linkedin-bar', 'click', function() {
		videoShareBar(3);
		

    	var desc = "Terrabis - Our planet re-discovered";
		var urlstring = serverURI;
        var url = encodeURIComponent(urlstring);
        window.open("https://www.linkedin.com/shareArticle?mini=true"+
        "&url="+url
         ,"_blank","top=0,right=0,width=750,height=320");
        return false; 
    });	
	
	
	
	
	
	

});





	



















