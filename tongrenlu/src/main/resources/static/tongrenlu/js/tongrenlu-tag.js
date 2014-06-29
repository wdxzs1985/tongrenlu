var tag = function(options) {
	
	var settings = $.extend({
		selector: '.tag-container',
		template: 'template-tag-item'
	}, options);
	
	$.get(settings.url).done(function(response){
		var $tagContainer = $(settings.selector);
    	if(response.tagList){
    		$.each(response.tagList, function(index, element){
    			element.tagPath = settings.tagPath;
    			$tagContainer.append(tmpl(settings.template, element))
    		})
    	} else {
    		$tagContainer.hide();
    	}
    });
}