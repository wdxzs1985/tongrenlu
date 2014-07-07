var timeline = function(options) {
	
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
			$('#timeline').on('click', '.previous a', function(e){
				e.preventDefault();
				that.load(settings.pageNumber - 1);
				that.scroll('#timeline');
			}).on('click', '.next a', function(e){
				e.preventDefault();
				that.load(settings.pageNumber + 1);
				that.scroll('#timeline');
			});
			

			if ( window.addEventListener )
				window.addEventListener( 'DOMMouseScroll', settings.scrollStop, false );
			window.onmousewheel = document.onmousewheel = settings.scrollStop;
			
			that.load();
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
			var targetBody;
			if ( $( 'html' ).scrollTop() > 0 ) {
				targetBody = $( 'html' );
			} else if ( $( 'body' ).scrollTop() > 0 ) {
				targetBody = $( 'body' );
			}
			return targetBody;
		},
		load: function(page){
			var $timeline = $('#timeline');
			var href = $timeline.data('href');
			var data = {
			        p : settings.pageNumber
			    }
			if(page) {
				data.p = page;
			}
			$.getJSON(href, data).done(function(response){
				if(response.page){
					settings.pageNumber = response.page.pageNumber;
					
					var $listContent = $('#timeline .timeline-list-content').addClass('hidden');
					var $empty = $('#timeline .timeline-empty').addClass('hidden');

					var $previous = $listContent.find('.previous').addClass('hidden');
					var $next = $listContent.find('.next').addClass('hidden');
					
					if(response.page.pageCount == 0) {
						$empty.removeClass('hidden');
					} else {
						var $list = $listContent.find('.cbp_tmtimeline').empty();
						for(var i = 0; i < response.page.items.length; i++){
							var item = response.page.items[i];
							item.createDate = that.formatDate(item.createDate, settings.i18n);
							$list.append(tmpl('template-timeline-item', item));
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

