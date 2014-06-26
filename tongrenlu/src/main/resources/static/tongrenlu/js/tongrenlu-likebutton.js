var likebutton = function(options) {
	
	var that = $.extend({
		selector: '#like-button',
		likeText: 'like it',
		unlikeText: 'i\'m like',
		like: false,
		init: function() {
			var $likebutton = $(that.selector);
			if($likebutton.length == 0) {
				return;
			}

			$likebutton.click(function() {
				that.post();
			});

			var loginUser = $(document).data('loginUser');
			if(loginUser && !loginUser.guest) {
				that.get();
			} else {
				that.render(false);
				$(document).on('signin', function(event, loginUser) {
					that.get();
				});
			}
		},
		render: function(like) {
			var $likebutton = $(that.selector);
			if(that.like) {
				$likebutton
					.removeClass('btn-danger')
					.addClass('btn-success');
				$likebutton.find('.like-button-icon')
					.removeClass('glyphicon glyphicon-heart-empty')
					.addClass('glyphicon glyphicon-heart');
				$likebutton.find('.like-button-text').text(that.unlikeText);
			} else {
				$likebutton
					.removeClass('btn-success')
					.addClass('btn-danger');
				$likebutton.find('.like-button-icon')
					.removeClass('glyphicon glyphicon-heart')
					.addClass('glyphicon glyphicon-heart-empty');
				$likebutton.find('.like-button-text').text(that.likeText);
			}
		},
		get: function() {
			$.getJSON(that.url).done(function(response){
				that.like = response.result;
				that.render();
			}).error(function(){
				that.like = false;
				that.render()
			});
		},
		post: function() {
			$.post(that.url).done(function(response){
				that.like = response.result;
				that.render();
			}).error(function(){
				that.like = false;
				that.render()
			});
		}
	}, options);
	
	
	that.init();
};