var fileupload = function(option) {
	
	var settings = $.extend({
		// The maximum width of the preview images:
        previewMaxWidth: 180,
        // The maximum height of the preview images:
        previewMaxHeight: 180,
        limitMultiFileUploads: 1,
        limitConcurrentUploads: 1,
        sequentialUploads: true,
        autoUpload: false,
        previewCrop: true,
        preload: true
	}, option);
	
	if(settings.url) {
		// Initialize the jQuery File Upload widget:
	    $('#fileupload').fileupload(settings);

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
	}
    
};