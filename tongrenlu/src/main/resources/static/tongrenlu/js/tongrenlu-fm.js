var fm = function(options) {
	var settings = $.extend({
		exitConfirm: '',
		swfPath: ''
	}, options);
	
	var that = {
		init: function() {
			var $window = $(window);
			if(settings.exitConfirm) {
			    $window.bind('beforeunload', function(event) {
					return settings.exitConfirm;
				});
			}
			
			that.initJPlayer();
			
			$window.bind('hashchange', that.onHashChange)
		    $window.trigger('hashchange');
		},
		initJPlayer: function() {
		    that.playerInstance = new jPlayerPlaylist({
		        jPlayer: '#jquery_jplayer_1',
		        cssSelectorAncestor: '#jp_container_1'
		        }, [], {
		        playlistOptions: {
		            autoPlay: false,
		            enableRemoveControls: true,
		            displayTime: 0,
		            removeTime: 0
		        },
		        swfPath: settings.swfPath,
		        smoothPlayBar: true,
		        keyEnabled: true,
		        audioFullScreen: true,
		        volume : 0.5,
		        loop: true
		    });
		},
		onHashChange: function() {
			var hash = window.location.hash;
			if(hash.match(/^#library$/)) {
				/*...*/
				that.library();
			} else if(hash.match(/^#player$/)) {
				/* index */
				that.player();
			} else {
				/* index */
				that.index();
			}
		},
		reset: function() {
		    $('.fm-page').addClass('hidden');
		    $('.navbar li').removeClass('active');
		    
		    var $navbatToggle = $('.navbar-toggle');
		    var $toggleTarget = $($navbatToggle.data('target'));
		    if($toggleTarget.hasClass('in')) {
		    	$navbatToggle.trigger('click')
		    }
		},
		index: function() {
			that.reset();
			var $indexPage = $('#fm-index').removeClass('hidden');
			if(!$indexPage.data('page')) {
				that.music($indexPage, 1);
			}
		},
		music: function($page, p) {
			var params = {
					p: p
			};
			$.getJSON(settings.musicUrl, params).done(function(response) {
				if(response.page) {
					$page.data('fm-page', response.page);
					
					var $listContent = $page.find('.list-content').addClass('hidden');
					var $empty = $page.find('.empty').addClass('hidden');

					var $previous = $listContent.find('.previous').addClass('hidden');
					var $next = $listContent.find('.next').addClass('hidden');
					
					if(response.page.pageCount == 0) {
						$empty.removeClass('hidden');
					} else {
						var $list = $listContent.find('.media-list').empty();
						for(var i = 0; i < response.page.items.length; i++){
							var item = response.page.items[i];
							$list.append(tmpl('template-music-item', item));
							$list.append(tmpl('template-music-item', item));
							$list.append(tmpl('template-music-item', item));
							$list.append(tmpl('template-music-item', item));
							$list.append(tmpl('template-music-item', item));
							$list.append(tmpl('template-music-item', item));
							$list.append(tmpl('template-music-item', item));
							$list.append(tmpl('template-music-item', item));
							$list.append(tmpl('template-music-item', item));
							$list.append(tmpl('template-music-item', item));
							$list.append(tmpl('template-music-item', item));
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
		library: function() {
			that.reset();
		},
		player: function() {
			that.reset();
			$('#fm-player').removeClass('hidden');
		}
	};
	
	
	that.init();
};