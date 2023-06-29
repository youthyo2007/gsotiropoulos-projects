     



function turnOnFilter(filteron) {

	//IF FILTER ENABLED
	if(filteron) {
		 $('.show_admin_filter_txt').switchClass( "text-gray-more", "text-green", 0, "easeInOutQuad" );
		 $('.hide_admin_filter_txt').switchClass( "text-gray-more", "text-green", 0, "easeInOutQuad" );
	}
	
};




$(document).ready(function() {
    	  
    	  
	  		$('#datefromfilter').datetimepicker({
				format: 'YYYY-MM-DD',
				icons: {
			      time: 'fa fa-clock-o',
			      date: 'fa fa-calendar',
			      up: 'fa fa-chevron-up',
			      down: 'fa fa-chevron-down',
			      previous: 'fa fa-chevron-left',
			      next: 'fa fa-chevron-right',
			      today: 'fa fa-crosshairs',
			      clear: 'fa fa-trash'
			    }
			});

	  		$('#datetofilter').datetimepicker({
				format: 'YYYY-MM-DD',
				icons: {
			      time: 'fa fa-clock-o',
			      date: 'fa fa-calendar',
			      up: 'fa fa-chevron-up',
			      down: 'fa fa-chevron-down',
			      previous: 'fa fa-chevron-left',
			      next: 'fa fa-chevron-right',
			      today: 'fa fa-crosshairs',
			      clear: 'fa fa-trash'
			    }
			});
		  

    		$( ".massbtn-video-filter-update" ).click(function() {
   			 $('#stats_filterform').submit();
   			});

    		
    		$( ".massbtn-video-filter-clear" ).click(function() {
    			$("form#stats_filterform :input[type=\"text\"]").each(function(){
    				 $(this).val('');
    			});
      			 $('#stats_filterform').submit();
      		});
    		
    		$( ".show_admin_filter" ).click(function() {
    			 $('#criteriaPanel').switchClass( "hidden", "-", 0, "easeInOutQuad" );
    			 $('.show_admin_filter').hide();	
    		});

    		$( ".hide_admin_filter" ).click(function() {
    			 $('#criteriaPanel').switchClass( "-", "hidden", 0, "easeInOutQuad" );
    			 $('.show_admin_filter').show();	

    		});
    		
    		
      });


