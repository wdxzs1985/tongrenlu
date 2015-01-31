$.fn.library = function(options) {

	var $this = $(this);
	
	var settings = $.extend({
	}, options);

	var that = {
		init: function() {
			if($this.length == 0) {
				return;
			}
			$this.click(function(e) {
				e.preventDefault();
				var loginUser = $(document).data('loginUser');
				if(loginUser && !loginUser.guest) {
					that.post();
				} else {
					$(document).on('signin', function(event, loginUser) {
						that.post();
					});
					$('#signinModal').modal('show');
				}
			});
		},
		post: function() {
			$.post(settings.url).done(function(response){
				switch(response.status) {
				case 1:
					$('#modal-add').modal('show');
					break;
				case 0:
					$('#modal-auth').modal('show');
					break;
				case -1:
					alert('服务器⑨了!');
					break;
				}
				if(response.status) {
					//alert(response.error);
					$('#modal-alert .modal-body p').text(response.message);
					$('#modal-alert').modal('show');
				} else if(response.error) {
					alert(response.error);
					//$('#modal-alert').modal('show');
				}
			}).error(function(){
				alert('服务器⑨了!');
			});
		}
	}
	
	that.init();
};