var fm = function(options) {
	var settings = $.extend({
		exitConfirm: '',
		swfPath: ''
	}, options);

	var $window = $(window);
	
	var that = {
		init: function() {
			that.initEventHandler();
			that.initJPlayer();
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
		        volume: 0.5,
		        preload: 'none',
		        loop: true
		    });
		    that.playerInstance['_createListItem'] = function(file) {
                var c = this;
                var d = tmpl('template-playlist-item', { 
                	removeItemClass: this.options.playlistOptions.removeItemClass,
                	itemClass: this.options.playlistOptions.itemClass,
                	file: file
                });
                return d;
            }
		},
		initEventHandler: function() {
			/* window */
			$window.on('hashchange', that.onHashChange);
			if(settings.exitConfirm) {
			    $window.on('beforeunload', function(event) {
					return settings.exitConfirm;
				});
			}
			/* fm-music */
			var $musicPage = $('#fm-music').on('click', '.fm-play-tracks', function(e){
				var music = $musicPage.data('music');
				var playlist = [];
	        	$.each($musicPage.data('trackList'), function(index, track) {
	        		var title = track.name;
	                var artist = track.artist;
	                var original = track.original;
	                var instrumental = (track.instrumental == '1');
	                var mp3 = settings.filePath + '/m' + music.id + '/f' + track.id + '.mp3';
	                var poster = settings.filePath + '/m' + music.id + '/cover_400.jpg';
	        		var playable = {
	        				title: title,
	        				artist: artist,
	                        original: original,
	                        instrumental: instrumental,
	        				mp3: mp3,
	        				poster: poster
	        		};
	        		if(settings.filter) {
	        			settings.filter(playlist, playable)
	        		} else {
	        			playlist.push(playable);
	        		}
	        	});

				var index = $(this).data('index');
				
				that.playerInstance.setPlaylist(playlist);
				that.playerInstance.play(index ? index : 0);
				
				that.player();
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
			} else if(hash.match(/^#music\/\d+$/)) {
				/* index */
				that.music(hash.match(/\d+/)[0]);
			} else {
				/* index */
				that.index();
			}
		},
		reset: function() {
		    $('.fm-page').removeClass('fm-page-active');
		    $('.navbar li').removeClass('active');
		    
		    var $navbatToggle = $('.navbar-toggle');
		    var $toggleTarget = $($navbatToggle.data('target'));
		    if($toggleTarget.hasClass('in')) {
		    	$navbatToggle.trigger('click')
		    }
		},
		index: function() {
			that.reset();
			var $indexPage = $('#fm-index').addClass('fm-page-active');
			if(!$indexPage.data('page')) {
				that.musiclist($indexPage, 1);
			}
		},
		musiclist: function($page, p) {
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
		music: function(artcleId) {
			that.reset();
			that.index();
			var $musicPage = $('#fm-music').addClass('fm-page-active');
			$musicPage.empty();
			$.getJSON(settings.musicUrl + '/' + artcleId).done(function(response){
				$musicPage.data(response);
				$musicPage.append(tmpl('template-music-page', response));
			});
		},
		library: function() {
			that.reset();
		},
		player: function() {
			if(that.playerInstance.playlist.length > 0) {
				that.reset();
				$('#fm-player').addClass('fm-page-active');
			} else {
				window.location.hash = '#index';
			}
		}
	};
	
	that.init();
};