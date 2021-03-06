var tag = function(options) {
	
	var settings = $.extend({
		selector: '.tag-container',
		intpuButtonSelector: '.tag-input-btn',
		template: 'template-tag-item',
		placeholder: 'Make a selection',
		searchurl: '/tag/search'
	}, options);
	
	var that = {
		init: function() {
			that.load();
			//that.login();
		},
		load: function() {
			$.get(settings.url).done(function(response){
				var $tagContainer = $(settings.selector);
	    		$tagContainer.empty();
		    	if(response.tagList){
		    		$.each(response.tagList, function(index, element){
		    			element.tagPath = settings.tagPath;
		    			$tagContainer.append(tmpl(settings.template, element))
		    		})
		    	}
	    		$(settings.intpuButtonSelector).click(function(){
	    			that.login();
	    		});
		    });
		},
		login: function() {
			var loginUser = $(document).data('loginUser');
	        if(!loginUser || loginUser.guest) {
	            $(document).on('signin', function(event, loginUser) {
	            	that.input();
	            });
	            $('#signinModal').modal('show');
	        } else {
	        	that.input();
	        }
		},
		input: function() {
	    	var ms = $('#magicsuggest').magicSuggest({
	            name: 'tags',
	            method: 'get',
	            data: settings.searchurl,
	            value: [],
	            valueField: 'tag',
	            hideTrigger: true,
	            placeholder: settings.placeholder,
	            typeDelay: 400,
	            maxSelection: 1
	        });
	        $(ms).on('selectionchange', function(e,m){
	            var tags = ms.getValue();
                $.post(settings.url, { 'tags[]': tags }).done(function(response) {
                	that.load();
                }).always(function() {
                    ms.clear(true);
                });
	        });
		}
	};
	
	that.init();
}