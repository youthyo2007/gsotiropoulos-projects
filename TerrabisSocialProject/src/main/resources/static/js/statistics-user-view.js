//ADMIN MENU SIDEBAR NAVIGATIONAL AND  EFFECTS
$( ".adminmenu_select_statistics" ).click(function() {
	 $('#adminmenu').hide();	
	 $('#statisticsmenu_videos').show("slide", { direction: "right" }, 1000);	
});


$( ".adminmenu_select_root" ).click(function() {
	 $('#statisticsmenu_videos').hide();	
	 $('#adminmenu').show("slide", { direction: "left" }, 1000);	
});

 


