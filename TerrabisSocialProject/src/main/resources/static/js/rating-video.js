$( document ).ready(function() {


	/****************** INIT VIDEO RATING -------------------*/
    $('#videoratingselect').barrating('show', {
         theme: 'fontawesome-stars', //' bars-movie fontawesome-stars'
         onSelect: function(value, text, event) {
             if (typeof(event) !== 'undefined') {
             	 videoRate(value);
             }
         }
         
     });
  	

	

});





	



















