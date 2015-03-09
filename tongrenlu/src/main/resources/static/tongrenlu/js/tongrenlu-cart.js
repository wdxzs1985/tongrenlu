var Cart = function(options) {
	var settings = $.extend({
	        listUrl:        '/shop/cart/list',
	        addUrl:         '/shop/cart/add',
	        removeUrl:      '/shop/cart/remove',
	        form:           '#shop-cart-form',
	        container:      '#shop-cart-container'
	}, options);
	
	var _cart = {
		items: [],
		load: function() {
			$.getJSON(settings.listUrl).done(function(response) {
				$(settings.container).html(tmpl('template-shop-cart', response))
			}).fail(function() {
				_cart.onError('Cart.load fail');
			});
		},
		add: function(titleOrUrl) {
			$.post(settings.addUrl, {titleOrUrl: titleOrUrl}).done(function(response) {
				if(response.result) {
					var $toast = $('<div class="toast">Event Created</div>');
					$toast.appendTo('body');
					$toast.fadeIn(400).delay(3000).fadeOut(400, function() {
						$toast.remove();
					});
					_cart.load();
				} else {
					_cart.onError(response.error);
				}
			}).fail(function() {
				_cart.onError('Cart.add fail');
			});
		},
		remove: function(title) {
			$.post(settings.removeUrl, {title: title}).done(function(response) {
				if(response.result) {
					_cart.load();
				} else {
					_cart.onError(response.error);
				}
			}).fail(function() {
				_cart.onError('Cart.remove fail');
			});
		},
		onError: function(error) {
			alert(error);
		}
	};
	
	$(settings.form).on('submit', function (e) {
		e.preventDefault();
		var nameOrUrl = $(this).find('input[name="nameOrUrl"]').val();
		_cart.add(nameOrUrl);
	});
	
	$(settings.container).on('click', 'a.btn-remove', function(e) {
		e.preventDefault();
		var title = $(this).data('title')
		_cart.remove(title);
	});

	_cart.load();
	return _cart;
}