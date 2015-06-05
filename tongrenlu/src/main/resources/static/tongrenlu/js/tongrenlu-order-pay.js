(function(window, $){
	window.orderPayController = function(options) {
		
		var settings = $.extend({
			listUrl: '',
			addUrl: '',
			updateUrl: '',
			removeUrl: '',
			container: '#order-pay-container',
			template: 'template-order-pay'
		}, options);
		
		var render = function() {
			$.getJSON(settings.listUrl).done(function(response) {
				if(response.result) {
					$(settings.container).html(tmpl(settings.template, response));
				} else {
					alert('服务器⑨了，请重试。');
				}
			}).fail(function() {
				alert('服务器⑨了，请重试。');
			});
		}
		
		var configListener = function() {
			//add
			$(settings.container).on('submit', '.order-pay-add-form', function(e) {
				e.preventDefault();
				var $form = $(this);
				$.post(settings.addUrl, $form.serialize()).done(function(response) {
					if(response.result) {
					} else {
						alert('服务器⑨了，请重试。');
					}
				}).fail(function() {
					alert('服务器⑨了，请重试。');
				}).always(function() {
					render();
				});
			});
			
			//update
			$(settings.container).on('submit', '.order-pay-update-form', function(e) {
				e.preventDefault();
				var $form = $(this);
				$.post(settings.updateUrl, $form.serialize()).done(function(response) {
					if(response.result) {
					} else {
						alert('服务器⑨了，请重试。');
					}
				}).fail(function() {
					alert('服务器⑨了，请重试。');
				}).always(function() {
					render();
				});
			});
			
			//remove
			$(settings.container).on('click', 'a.btn-remove', function(e) {
				e.preventDefault();
				var $this = $(this);
				$.post(settings.removeUrl, $this.data()).done(function(response) {
					if(response.result) {
					} else {
						alert('服务器⑨了，请重试。');
					}
				}).fail(function() {
					alert('服务器⑨了，请重试。');
				}).always(function() {
					render();
				});
			})
		}
		
		//init
		render();
		configListener();
		
	}
	
})(window, jQuery);