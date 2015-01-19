var player = function(option) {
	var settings = $.extend({
		playlistOptions: {
            enableRemoveControls: true
        },
        supplied: 'mp3',
        smoothPlayBar: true,
        keyEnabled: false,
        audioFullScreen: true,
        volume: 0.5,
        preload: 'none'
	}, option);
	
    $.getJSON(settings.url).done(function(response){
        if(response.trackList) {
        	var playlist = [];
        	$.each(response.trackList, function(index, track) {
        		var id = track.id;
        		var title = track.name;
                var artist = track.artist;
                var original = track.original;
                var instrumental = (track.instrumental == '1');
                var mp3 = settings.filePath + '/f' + track.id + '.mp3';
                var poster = settings.filePath + '/cover_120.jpg';
                var rate = track.rate;
        		var playable = {
        				index: index,
        				id: id,
        				title: title,
        				artist: artist,
                        original: original,
                        instrumental: instrumental,
        				mp3: mp3,
        				poster: poster,
        				rate: rate
        		};
        		if(settings.filter) {
        			settings.filter(playlist, playable)
        		} else {
        			playlist.push(playable);
        		}
        	});
        	
        	var jPlaylist = new jPlayerPlaylist({
                jPlayer: "#jquery_jplayer_1",
                cssSelectorAncestor: "#jp_container_1"
            }, playlist, settings);
            jPlaylist['_createListItem'] = function(file) {
                var c = this;
                var d = tmpl('template-playlist-item', { 
                	removeItemClass: this.options.playlistOptions.removeItemClass,
                	itemClass: this.options.playlistOptions.itemClass,
                	file: file
                });
                return d;
            };
            
            jPlaylist['_updateControls'] = function() {
                if (this.options.playlistOptions.enableRemoveControls) {
                	$(this.cssSelector.playlist + " ." + this.options.playlistOptions.removeItemClass).show()
                } else {
                	$(this.cssSelector.playlist + " ." + this.options.playlistOptions.removeItemClass).hide();
                }
                if(this.shuffled) {
                	$(this.cssSelector.shuffleOff).show()
                	$(this.cssSelector.shuffle).hide()
                } else {
                	$(this.cssSelector.shuffleOff).hide();
                	$(this.cssSelector.shuffle).show()
                }

                if(settings.rateUrl) {
	                $('#jp_container_1 .jp-rate').each(function(index, element) {
	                	var $element = $(element);
	                	$element.html(tmpl('template-rate', $element.data()));
	                });
                } else {
                	$('#jp_container_1 .jp-rate').hide();
                }
            };
            
            if(settings.rateUrl) {
            	$('#jp_container_1').on('click', '.jp-rate > button', function(e) {
                	e.preventDefault();
                	var $this = $(this);
                	var $rateGroup = $this.closest('.jp-rate');
                	var data = {
                    		trackId: $rateGroup.data('trackId'),
                    		rate: $this.data('rate')
                    	};
                	$.post(settings.rateUrl, data).done(function(response) {
                		if(response.result) {
                			$rateGroup.data(data);
                			jPlaylist._updateControls();
                		} else {
                			alert(response.error);
                		}
                	});
                });
            }
        }
    });
}
