$(document).ready(function() {

	


	$(document).delegate('.usraction-follow', 'click', function() {
		
		
		if(terrabisApp.isAuthenticated) {
			
			var callback = function(data, status, xhr) {
		
			};
			
				var useridvar  = this.dataset.userid;
				var formData = { userid: useridvar};
				restajax(terrabisApp.followUserURI,formData,callback);
							

				$(".usraction-follow[data-userid='"+useridvar+"']").hide();
				$(".usraction-unfollow[data-userid='"+useridvar+"']").fadeIn("slow");		
		}
		else
		{
			swal("You need to sign-in", "Please sign-in to follow this user!. Click <a href=\"http://www.terrabis.com/authenticate/signin\">here</a>", "warning");
		}

	});


	//LIKE CLICK ON ACTION BAR FUNCTION
	$(document).delegate('.usraction-unfollow', 'click', function() {

		
		if(terrabisApp.isAuthenticated) {
			
			var callback = function(data, status, xhr) {
			};
			
			

			
				var useridvar  = this.dataset.userid;
				var formData = { userid: useridvar};
				restajax(terrabisApp.unFollowUserURI,formData,callback);


				$(".usraction-unfollow[data-userid='"+useridvar+"']").hide();
				$(".usraction-follow[data-userid='"+useridvar+"']").fadeIn("slow");		

		}
		else
		{
			swal("You need to sign-in", "Please sign-in to unfollow this user!. Click <a href=\"http://www.terrabis.com/authenticate/signin\">here</a>", "warning");
		}

	});

	  

	
	
	
	
	
});