(function(window, $){
	window.orderItemController = function(options) {
		var settings = $.extend({
			url: ''
		}, options);
		
		$('.order-item-status').each(function(index, element) {
			var $element = $(element);
	        $element.html(tmpl('template-order-status', $element.data()));
		}).on('click', '.btn-status', function(e) {
			e.preventDefault();
			var $this = $(this);
	        var itemId = $this.data('itemId');
			var status = $this.data('status');
			var data = {
				"status": status,
				"itemId[]": [itemId]
			}
			
			//var url = /*[[@{/admin/order/item/status}]]*/ '';
			$.post(settings.url, data).done(function(response) {
				if(response.result) {
					$this.parents('.order-item-status').html(tmpl('template-order-status', $this.data()));
				}
			});
		});
	}
	
})(window, jQuery);