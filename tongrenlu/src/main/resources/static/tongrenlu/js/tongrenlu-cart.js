var Cart = function(options) {
	var settings = $.extend({
		listUrl : '/shop/cart/list',
		addUrl : '/shop/cart/add',
		updateUrl : '/shop/cart/update',
		removeUrl : '/shop/cart/remove',
		clearUrl : '/shop/cart/clear',
		form : '.shop-cart-form',
		container : '#shop-cart-container',
		badge : '#shop-cart-badge',
		useUi : true
	}, options);

	var _cart = {
		items : [],
		load : function() {
			$.getJSON(settings.listUrl).done(function(response) {
				if (settings.useUi) {
					$(settings.form).find('input[type="text"]').val('');
					$(settings.form).find('input[type="number"]').val('0');
					$(settings.container).html(tmpl('template-shop-cart', response));
					
					if(response.orderBean.quantity > 15) {
						$('.btn-shipping[data-shipping-method="1"]').addClass('btn-success');
					} else if(response.orderBean.quantity >= 10) {
						$('.btn-shipping[data-shipping-method="2"]').addClass('btn-success');
					} else {
						$('.btn-shipping[data-shipping-method="0"]').addClass('btn-success');
					}
				}

				if (response.itemList && response.itemList.length > 0) {
					$(settings.badge).text(response.itemList.length).removeClass('hidden');
				} else {
					$(settings.badge).addClass('hidden');
				}
			}).fail(function() {
				_cart.onError('服务器⑨了，请重试。');
			});
		},
		login : function(callback) {
			var loginUser = $(document).data('loginUser');
			if (loginUser && !loginUser.guest) {
				callback.call();
			} else {
				$(document).on('signin', function(event, loginUser) {
					callback.call();
				});
				$('#signinModal').modal('show');
			}
		},
		add : function(action, data) {
			this.login(function() {
				$.post(action, data).done(function(response) {
					if (response.result) {
						_cart.makeToast('添加成功');
						_cart.load();
					} else {
						_cart.onError(response.error);
					}
				}).fail(function() {
					_cart.onError('服务器⑨了，请重试。');
				});
			});
		},
		update : function(title, quantity) {
			this.login(function() {
				$.post(settings.updateUrl, {
					title : title,
					quantity : quantity
				}).done(function(response) {
					if (response.result) {
						_cart.makeToast('修改成功');
						_cart.load();
					} else {
						_cart.onError(response.error);
					}
				}).fail(function() {
					_cart.onError('服务器⑨了，请重试。');
				});
			});
		},
		remove : function(title) {
			this.login(function() {
				$.post(settings.removeUrl, {
					title : title
				}).done(function(response) {
					if (response.result) {
						_cart.load();
					} else {
						_cart.onError(response.error);
					}
				}).fail(function() {
					_cart.onError('服务器⑨了，请重试。');
				});
			});
		},
		clear : function(title) {
			this.login(function() {
				$.post(settings.clearUrl).done(function(response) {
					if (response.result) {
						_cart.load();
					} else {
						_cart.onError(response.error);
					}
				}).fail(function() {
					_cart.onError('服务器⑨了，请重试。');
				});
			});
		},
		onError : function(error) {
			_cart.makeToast(error);
		},
		makeToast : function(text) {
			var $toast = $('.toast');
			if ($toast.length == 0) {
				$toast = $('<div class="toast"></div>').appendTo('body');
				$toast.text(text);
				$toast.fadeIn(400).delay(3000).fadeOut(400, function() {
					$toast.remove();
				});
			} else {
				$toast.text(text);
			}
			return $toast;
		},

		initUI : function() {
			$(settings.form).on('submit', function(e) {
				e.preventDefault();
				var $form = $(this);
				_cart.add($form.attr('action'), $form.serialize());
			});

			$(settings.container).on('click', 'a.btn-remove', function(e) {
				e.preventDefault();
				var title = $(this).data('title')
				_cart.remove(title);
			}).on('submit', 'form.form-inline', function(e) {
				e.preventDefault();
			}).on('click', 'a.btn-clear', function(e) {
				e.preventDefault();
				if(confirm('确定要全部清空吗？')) {
					_cart.clear();
				}
			}).on('change', 'input[name="quantity"]', function() {
				var title = $(this).data('title');
				var quantity = $(this).val();

				if (_cart.updatetimeout) {
					clearTimeout(_cart.updatetimeout);
				}

				_cart.updatetimeout = setTimeout(function() {
					_cart.update(title, quantity);
					_cart.updatetimeout = null;
				}, 500);
			}).on('click', 'a.btn-shipping', function(e) {
				e.preventDefault();
				var $this = $(this);
				
				if($this.hasClass('disabled')) {
					return;
				}
				
				var shippingMethod = $this.data('shippingMethod');
				var confirmText = ['确定使用 SAL团发 并下单吗？',
				                   '确定使用 SAL直发 并下单吗？',
				                   '确定使用 EMS直发 并下单吗？'];
				
				if(confirm(confirmText[shippingMethod - 1])) {
					$('a.btn-shipping').addClass('disabled');
				}
			})
		}
	};

	if (settings.useUi) {
		_cart.initUI();
		_cart.load();
	}
	return _cart;
}