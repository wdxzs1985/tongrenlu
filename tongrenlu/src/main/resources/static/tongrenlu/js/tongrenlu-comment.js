var comment = function(options) {
	
	var settings = $.extend({
		pageNumber: 1,
		emptyText: 'no comment',
		i18n:  {
		    ONE_SECOND : 1000,
		    ONE_MINUTE : 1000 * 60,
		    ONE_HOUR : 1000 * 60 * 60,
		    ONE_DATE : 1000 * 60 * 60 * 24,
		    YEAR : "年",
		    MONTH : "月",
		    DATE : "日",
		    REP_SEARCH : "{0}",
		    HOURS_BEFORE : "{0}小时前",
		    MINUTES_BEFORE : "{0}分前",
		    SECONDS_BEFORE : "{0}秒前",
		    JUST_BEFORE : "刚刚"
		},
		duration : 1000,
		easing   : 'easeOutQuint'
	}, options);
	
	var that = {
		init: function() {
			var $comment = $('#comment').on('submit', '.comment-form', function(e){
				e.preventDefault();
				var $form = $(this);
			    var data = $form.serialize();
			    
			    if($comment.data('sending')){
			        return;
			    }
			    $comment.data('sending', true);
			    
		    	$.post(settings.url, data).done(function(response) {
		        	if(response.result) {
		        		that.load(1);
						that.scroll('#comment');
						that.reset();
		        	} else {
		        		alert(response.error)
		        	}
		        }).always(function(){
				    $comment.data('sending', false);
		        });
			}).on('click', '.previous a', function(e){
				e.preventDefault();
				that.load(settings.pageNumber - 1);
				that.scroll('#comment');
			}).on('click', '.next a', function(e){
				e.preventDefault();
				that.load(settings.pageNumber + 1);
				that.scroll('#comment');
			}).on('click', 'button.comment-reply', function(e){
				e.preventDefault();
				var commentData = $(this).closest('.media').data();
				var repo = " //@" + commentData.userBean.nickname + "#" + commentData.userBean.id + ' :' + commentData.content;
				$comment.find('.comment-form textarea').text(repo).focus();
			});
			
			that.load();
		},
		reset: function() {
			$comment.find('.comment-form').each(function(index, element) {
				element.reset();
			});
		},
		scroll: function(hash) {
			var offset = $( hash ).eq( 0 ).offset();
			if ( offset == null ) {
				return;
			}

			var wst = $( window ).scrollTop();
			if ( wst === 0 ) {
				$( window ).scrollTop( wst + 1 );
			}

			var targetBody = that.getTargetBody();
			if ( typeof targetBody === 'undefined' )
				return;
			
			targetBody.animate(
				{
					scrollTop: offset.top - 50
				},
				settings.duration,
				settings.easing
			);
		},
		scrollStop: function() {
			that.getTargetBody().stop( true );
		},
		getTargetBody: function() {
			if ( $( 'html' ).scrollTop() > 0 ) {
				targetBody = $( 'html' );
			} else if ( $( 'body' ).scrollTop() > 0 ) {
				targetBody = $( 'body' );
			}
			return targetBody;
		},
		load: function(page){
			var $commentForm = $('#comment .comment-form');
			var data = {
			        p : settings.pageNumber
			    }
			if(page) {
				data.p = page;
			}
			$.getJSON(settings.url, data).done(function(response){
				if(response.page){
					settings.pageNumber = response.page.pageNumber;
					
					var $listContent = $('#comment .comment-list-content').addClass('hidden');
					var $empty = $('#comment .comment-empty').addClass('hidden');

					var $previous = $listContent.find('.previous').addClass('hidden');
					var $next = $listContent.find('.next').addClass('hidden');
					
					if(response.page.pageCount == 0) {
						$empty.removeClass('hidden');
					} else {
						var $list = $listContent.find('.media-list').empty();
						for(var i = 0; i < response.page.items.length; i++){
							var item = response.page.items[i];
							item.createDate = that.formatDate(item.createDate, settings.i18n);
							var $listItem = $(tmpl('template-comment-item', item));
							$listItem.data(item);
							$listItem.appendTo($list);
						}
						if(!response.page.first) {
							$previous.removeClass('hidden');
						}
						if(!response.page.last) {
							$next.removeClass('hidden');
						}
						$listContent.removeClass('hidden');
					}
				}
			});
			
		},
		formatDate: function(date, i18n){
		    var now = new Date();
		    var date = new Date(date);
		    var n = now.getTime() - date.getTime();
		    if(n > i18n.ONE_DATE){
		        return date.getFullYear() + i18n.YEAR + (date.getMonth() + 1) + i18n.MONTH + date.getDate() + i18n.DATE;
		    }else if (n>i18n.ONE_HOUR){
		        return i18n.HOURS_BEFORE.replace(i18n.REP_SEARCH,parseInt(n/i18n.ONE_HOUR));
		    }else if (n>i18n.ONE_MINUTE){
		        return i18n.MINUTES_BEFORE.replace(i18n.REP_SEARCH,parseInt(n/i18n.ONE_MINUTE));
		    }else if (n>i18n.ONE_SECOND){
		        return i18n.SECONDS_BEFORE.replace(i18n.REP_SEARCH,parseInt(n/i18n.ONE_SECOND));
		    }else {
		        return i18n.JUST_BEFORE;
		    }
		}
	};
	
	that.init();
};

