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
			/* navbar */
			$('.navbar').on('click', 'a', function(){
			});
			/* fm-music */
			var $musicPage = $('#fm-music').on('click', '.fm-play-tracks', function(e){
				e.preventDefault();
				
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
				
				window.location.hash = '#player';
			});
			/* search form */
			var $searchForm = $('#fm-search-form');
			$searchForm.on('submit', function(e) {
				e.preventDefault();
				var data = $(this).serialize();
				window.location.hash='#search/' + data;
			});
			/* fm-search */
			var $searchPage = $('#fm-search').on('click', '.fm-play-track', function(e){
				e.preventDefault();
				
				var searchResult = $searchPage.data('searchResult');
				
				var track = $(this).closest('.media').data();
        		var title = track.track;
                var artist = track.artist ? track.artist.join(',') : '';
                var instrumental = (track.instrumental);
                var mp3 = settings.filePath + '/m' + track.articleId + '/f' + track.fileId + '.mp3';
                var poster = settings.filePath + '/m' + track.articleId + '/cover_400.jpg';
				var playable = {
						title: title,
        				artist: artist,
                        instrumental: instrumental,
        				mp3: mp3,
        				poster: poster
				};
				var index = $(this).data('index');
				that.playerInstance.add(playable);
				that.playerInstance.play(-1);

				window.location.hash = '#player';
			});
		},
		onHashChange: function() {
			var $navbatToggle = $('.navbar-toggle');
		    var $toggleTarget = $($navbatToggle.data('target'));
		    if($toggleTarget.hasClass('in')) {
		    	$navbatToggle.trigger('click')
		    }
		    
			var hash = window.location.hash;
			if(hash.match(/^#search\/q=(.*?)&p=(\d+)$/)) {
				/*...*/
				var q = hash.match(/^#search\/q=(.*?)&p=(\d+)$/)[1];
				var p = hash.match(/^#search\/q=(.*?)&p=(\d+)$/)[2];
				that.search({q: q, p: p - 1});
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
		reset: function(levels) {
		    $.each(levels, function(index, element){
		    	$(element).removeClass('fm-page-active');
		    })
		},
		index: function() {
			that.reset(['.fm-page-1','.fm-page-2','.fm-page-3']);
			var $page = $('#fm-index').addClass('fm-page-active');
			if(!$page.data('page')) {
				that.musiclist($page, 1);
			}
		},
		musiclist: function($page, p) {
			var params = {
					p: p
			};
			$.getJSON(settings.musicUrl, params).done(function(response) {
				if(response.page) {
					$page.data(response);
					
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
							var $listItem = $(tmpl('template-music-item', item));
							$listItem.data(item);
							$list.append($listItem);
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
			that.reset(['.fm-page-2','.fm-page-3']);
			var $musicPage = $('#fm-music').addClass('fm-page-active');
			$musicPage.empty();
			$.getJSON(settings.musicUrl + '/' + artcleId).done(function(response){
				$musicPage.data(response);
				$musicPage.append(tmpl('template-music-page', response));
				
				$musicPage.find('.likebutton').likebutton();
			});
		},
		search: function(data) {
			that.reset(['.fm-page-1','.fm-page-2','.fm-page-3']);
			var $page = $('#fm-search').addClass('fm-page-active');
			
			var $listContent = $page.find('.list-content').addClass('hidden');
			var $empty = $page.find('.empty').addClass('hidden');

			if(data.q){
				$.getJSON(settings.searchUrl, data).done(function(response){
					$page.data(response);
						var $previous = $listContent.find('.previous').addClass('hidden');
						var $next = $listContent.find('.next').addClass('hidden');
						
						if(response.searchResult.totalPages == 0) {
							$empty.removeClass('hidden');
						} else {
							var $list = $listContent.find('.media-list').empty();
							for(var i = 0; i < response.searchResult.content.length; i++){
								var item = response.searchResult.content[i];
								var $listItem = $(tmpl('template-search-item', item));
								$listItem.data(item);
								$list.append($listItem);
							}
							if(!response.searchResult.firstPage) {
								$previous.removeClass('hidden');
							}
							if(!response.searchResult.lastPage) {
								$next.removeClass('hidden');
							}
							$listContent.removeClass('hidden');
						}
				});
			} else {
				$empty.removeClass('hidden');
			}
		},
		player: function() {
			if(that.playerInstance.playlist.length > 0) {
				$('#fm-player').addClass('fm-page-active');
			} else {
				window.location.hash = '#index';
			}
		}
	};
	
	that.init();
};