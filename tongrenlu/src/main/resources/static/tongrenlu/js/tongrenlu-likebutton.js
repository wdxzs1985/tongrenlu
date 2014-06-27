$.fn.likebutton = function() {

	var $this = $(this);
	
	var settings = $.extend({
		notlikeText: 'like it',
		likeText: 'i\'m like',
		unlikeText: 'unlike'
	}, $this.data());
	
	var constants = {
			LIKE: 1,
			NOT_LIKE: 0,
			NEED_SIGN: -1,
			SELF: -2,
			ERROR: -3
	}

	var that = {
			like: 0,
			init: function() {
				if($this.length == 0) {
					return;
				}
				
				var loginUser = $(document).data('loginUser');
				if(loginUser && !loginUser.guest) {
					that.get();
				} else {
					that.render(false);
					$(document).on('signin', function(event, loginUser) {
						that.get();
					});
				}
				
				$this.click(that.post);
				$this.hover(function(){
					if(that.like == constants.LIKE) {
						that.render(constants.NOT_LIKE);
						$this.find('.like-button-text').text(settings.unlikeText);
					}
				}, function(){
					that.render(that.like);
				});
			},
			render: function(like) {
				switch(like) {
				case constants.LIKE:
					$this.removeClass('hidden')
						.removeClass('btn-danger')
						.addClass('btn-success');
					$this.find('.like-button-icon')
						.removeClass('glyphicon glyphicon-heart-empty')
						.addClass('glyphicon glyphicon-heart');
					$this.find('.like-button-text').text(settings.likeText);
					break;
				case constants.NEED_SIGN:
					$('#signinModal').modal('show');
				case constants.NOT_LIKE:
					$this.removeClass('hidden')
						.removeClass('btn-success')
						.addClass('btn-danger');
					$this.find('.like-button-icon')
						.removeClass('glyphicon glyphicon-heart')
						.addClass('glyphicon glyphicon-heart-empty');
					$this.find('.like-button-text').text(settings.notlikeText);
					break;
				default:
					$this.addClass('hidden');
					break;
				}
			},
			get: function() {
				$.getJSON(settings.url).done(function(response){
					that.like = response.result;
					that.render(that.like);
				}).error(function(){
					that.like = constants.ERROR;
					that.render(that.like)
				});
			},
			post: function() {
				$.post(settings.url).done(function(response){
					that.like = response.result;
					that.render(that.like);
				}).error(function(){
					that.like = constants.ERROR;
					that.render(that.like)
				});
			}
	}
	
	that.init();
};