/*$(function () {
    $('#fileupload').fileupload({
        dataType: 'json',
 
        done: function (e, data) {
            $("tr:has(td)").remove();
            $.each(data.result, function (index, file) {
 
                $("#uploaded-files").append(
                        $('<tr/>')
                        .append($('<td/>').text(file.fileName))
                        .append($('<td/>').text(file.fileSize))
                        .append($('<td/>').text(file.fileType))
                        .append($('<td/>').html("<a href='rest/controller/get/"+index+"'>Click</a>"))
                        )//end $("#uploaded-files").append()
            }); 
        },
 
        progressall: function (e, data) {
            var progress = parseInt(data.loaded / data.total * 100, 10);
            $('#progress .bar').css(
                'width',
                progress + '%'
            );
        },
 
        dropZone: $('#avatardropzone')
    });
});*/

$( document ).ready(function() {
	avatarAjax();
});



 function avatarAjax() {
	 $.ajax({
		url: "http://localhost:8080/user-10000/avatar/imagebase64"
		}).then(function(data) {
		$('#avatar-image').attr("src", "data:image/png;base64,"+data);
	});
 }
        									     




(function(window, document, $, undefined){

    $(function () {
        'use strict';

        // Initialize the jQuery File Upload widget:
        $('#fileupload').fileupload({
            // Uncomment the following to send cross-domain cookies:
            //xhrFields: {withCredentials: true},
            // url: 'server/upload'
        	//acceptFileTypes: /(\.|\/)(jpg)$/i,
            //maxFileSize: 400000000, // 40 MB
        	disableImageResize: /Android(?!.*Chrome)|Opera/
                .test(window.navigator.userAgent), 	
            previewMaxWidth: 300,
            previewMaxHeight: 200,
            previewCrop: true,
            success: function (e, data) {
        
           	 $.ajax({
         		url: "http://localhost:8080/user-10000/avatar/imagebase64"
         		}).then(function(data) {
         		$('#avatar-image').attr("src", "data:image/png;base64,"+data);
         	});
            			
            }
                
        });

        // Enable iframe cross-domain access via redirect option:
        $('#fileupload').fileupload(
            'option',
            'redirect',
            window.location.href.replace(
                /\/[^\/]*$/,
                '/cors/result.html?%s'
            )
        );

        // Load existing files:
        $('#fileupload').addClass('fileupload-processing');
        
        $.ajax({
            // Uncomment the following to send cross-domain cookies:
            //xhrFields: {withCredentials: true},
            url: $('#fileupload').fileupload('option', 'url'),
            dataType: 'json',
            context: $('#fileupload')[0]
        }).always(function () {
            $(this).removeClass('fileupload-processing');
        }).done(function (result) {
            $(this).fileupload('option', 'done')
                .call(this, $.Event('done'), {result: result});
        });

    });

})(window, document, window.jQuery);
