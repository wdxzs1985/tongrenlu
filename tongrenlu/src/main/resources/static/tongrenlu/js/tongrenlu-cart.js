var Cart = function(options) {
	var settings = $.extend({
	        listUrl:        '/shop/cart/list',
	        addUrl:         '/shop/cart/add',
	        updateUrl:      '/shop/cart/update',
	        removeUrl:      '/shop/cart/remove',
	        form:           '#shop-cart-form',
	        container:      '#shop-cart-container'
	}, options);
	
	var _cart = {
		items: [],
		load: function() {
			$.getJSON(settings.listUrl).done(function(response) {
				$(settings.form).find('input').val('');
				$(settings.container).html(tmpl('template-shop-cart', response));
			}).fail(function() {
				_cart.onError('Cart.load fail');
			});
		},
		add: function(titleOrUrl) {
			$.post(settings.addUrl, {titleOrUrl: titleOrUrl}).done(function(response) {
				if(response.result) {
					_cart.makeToast('Item Created');
					_cart.load();
				}
			}).fail(function() {
				_cart.onError('Cart.add fail');
			});
		},
		update: function(title, quantity) {
			$.post(settings.updateUrl, {title: title, quantity: quantity}).done(function(response) {
				if(response.result) {
					_cart.makeToast('Item Updated');
					_cart.load();
				} else {
					_cart.onError(response.error);
				}
			}).fail(function() {
				_cart.onError('Cart.update fail');
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
			_cart.makeToast(error);
		},
		makeToast: function(text) {
			var $toast = $('.toast');
			if($toast.length == 0) {
				$toast = $('<div class="toast"></div>').appendTo('body');
				$toast.text(text);
				$toast.fadeIn(400).delay(3000).fadeOut(400, function() {
					$toast.remove();
				});
			} else {
				$toast.text(text);
			}
			return $toast;
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
	}).on('change', 'input', function() {
		var title = $(this).data('title');
		var quantity = $(this).val();
		_cart.update(title, quantity);
	});

	_cart.load();
	return _cart;
}