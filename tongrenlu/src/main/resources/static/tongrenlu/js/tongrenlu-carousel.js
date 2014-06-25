var carousel=function(option){
	var settings = $.extend({
		container: '#blueimp-gallery-carousel',
        carousel: true,
        startSlideshow: false,
        links: []
	}, option);

    $.getJSON(settings.url).done(function (response){
    	if(response.fileList){
            $.each(response.fileList, function(index, file) {
            	settings.links.push(settings.filePath + "/f" + file.id + "_1600.jpg");
            });
    	}
    }).always(function(){
    	if(settings.links.length == 0) {
    		settings.links.push(settings.filePath + "/cover_400.jpg");
    	}
        var gallery = blueimp.Gallery(settings.links, settings);
    });
}