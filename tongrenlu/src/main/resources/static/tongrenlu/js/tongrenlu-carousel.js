var carousel=function(option){
	var settings = $.extend({
		container: '#blueimp-gallery-carousel',
		fullscreenButton: '#blueimp-gallery-fullscreen',
		fullscreenContainer: '#blueimp-gallery',
        startSlideshow: false,
        links: []
	}, option);

    $.getJSON(settings.url).done(function (response){
    	if(response.fileList){
            $.each(response.fileList, function(index, file) {
            	settings.links.push(settings.filePath + "/" + file.checksum + "_1080.jpg");
            });
    	}
    }).always(function(){
    	if(settings.links.length == 0) {
    		settings.links.push(settings.filePath + "/cover_400.jpg");
    	}
        var gallery = blueimp.Gallery(settings.links, {
        	container: settings.container,
        	startSlideshow: settings.startSlideshow,
        	carousel:true
        });
        
        $(settings.fullscreenButton).click(function(){
        	var fullscreenGallery = blueimp.Gallery(settings.links, {
            	container: settings.fullscreenContainer,
            	startSlideshow: settings.startSlideshow,
            	carousel:false
            });
        });
    });
}