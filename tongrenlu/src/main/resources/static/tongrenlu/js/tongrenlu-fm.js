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
			/* fm-index */
			var $indexPage = $('#fm-index').on('click', '.pager a', function(e){
				e.preventDefault();
				
				var page = $indexPage.data('page');
				that.loadIndex(page.pageNumber + 1);
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
			}).on('click', '.pager a', function(e){
				e.preventDefault();
				var query = $searchPage.data('query');
				var pageNumber = $searchPage.data('searchResult.totalPages');
				that.loadSearch(query, pageNumber);
			});
		},
		onHashChange: function() {
			var $navbatToggle = $('.navbar-toggle');
		    var $toggleTarget = $($navbatToggle.data('target'));
		    if($toggleTarget.hasClass('in')) {
		    	$navbatToggle.trigger('click')
		    }
		    
			var hash = window.location.hash;
			if(hash.match(/^#search\/q=(.*?)$/)) {
				/*...*/
				that.search(hash.match(/^#search\/q=(.*?)$/)[1]);
			} else if(hash.match(/^#player$/)) {
				/* index */
				that.player();
			} else if(hash.match(/^#music\/(\d+)$/)) {
				/* index */
				that.music(hash.match(/^#music\/(\d+)$/)[1]);
			} else {
				/* index */
				that.index();
			}
		},
		index: function() {
			$('#fm-search, #fm-music').removeClass('fm-page-active').addClass('hidden');
			that.hidePlayerWhenNotPlaying();
			var $page = $('#fm-index').addClass('fm-page-active').removeClass('hidden');
			if(!$page.data('page')) {
				that.loadIndex(1);
			}
		},
		load: function(adapter) {
			var $container = $(adapter.container);
			$.getJSON(adapter.url, adapter.params).done(function(response) {
				if(response.result) {
					$container.data(response);
					var $empty = $container.find('.empty').addClass('hidden');
					var $listContent = $container.find('.list-content').addClass('hidden');
					var $pager = $container.find('.pager').addClass('hidden');
					
					if(adapter.isEmpty(response)) {
						$empty.removeClass('hidden');
					} else {
						var $list = $listContent.find('.media-list');
						for(var i = 0; i < adapter.items(response).length; i++){
							$list.append(adapter.view(response, i));
						}
						if(!adapter.isLast(response)) {
							$pager.removeClass('hidden');
						}
						$listContent.removeClass('hidden');
					}
				}
			});
		},
		loadIndex: function(pageNumber) {
			that.load({
				container: '#fm-index',
				url: settings.musicUrl,
				params: {
					p: pageNumber
				},
				isEmpty: function(response) {
					return response.page.pageCount == 0;
				},
				isLast: function(response) {
					return response.page.last;
				},
				items: function(response) {
					return response.page.items;
				},
				view: function(response, i) {
					var item = this.items(response)[i];
					var $listItem = $(tmpl('template-music-item', item));
					$listItem.data(item);
					return $listItem;
				}
			});
		},
		loadSearch: function(query, pageNumber) {
			that.load({
				container: '#fm-search',
				url: settings.searchUrl,
				params: {
					q: query,
					p: pageNumber
				},
				isEmpty: function(response) {
					return response.searchResult.totalPages == 0;
				},
				isLast: function(response) {
					return response.searchResult.lastPage;
				},
				items: function(response) {
					return response.searchResult.content;
				},
				view: function(response, i) {
					var item = this.items(response)[i];
					var $listItem = $(tmpl('template-search-item', item));
					$listItem.data(item);
					return $listItem;
				}
			});
		},
		music: function(artcleId) {
			that.index();
			$('#fm-search, #fm-music').removeClass('fm-page-active').addClass('hidden');
			$('#fm-index').addClass('fm-page-active').removeClass('hidden');
			that.hidePlayerWhenNotPlaying();
			var $musicPage = $('#fm-music').addClass('fm-page-active').removeClass('hidden');
			$musicPage.empty();
			$.getJSON(settings.musicUrl + '/' + artcleId).done(function(response){
				$musicPage.data(response);
				$musicPage.append(tmpl('template-music-page', response));
				$musicPage.find('.likebutton').likebutton();
			});
		},
		search: function(query) {
			$('#fm-index, #fm-search, #fm-music').removeClass('fm-page-active').addClass('hidden');
			
			var $page = $('#fm-search').addClass('fm-page-active').removeClass('hidden');
			
			if(query){
				var lastQuery = $page.data('query');
				if(query != lastQuery) {
					that.loadSearch(query, 0);
				}
			} else {
				$empty.removeClass('hidden');
			}
		},
		player: function() {
			$('.fm-page').removeClass('fm-page-active');
			that.hidePlayerWhenNotPlaying();
			if(that.playerInstance.playlist.length == 0) {
				window.location.hash = '#index';
			}
		},
		hidePlayerWhenNotPlaying: function(){
			if(that.playerInstance.playlist.length > 0) {
				$('#fm-player').addClass('fm-page-active').removeClass('hidden');
			} else {
				$('#fm-player').removeClass('fm-page-active').addClass('hidden');
			}
		}
	};
	
	that.init();
};