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
        if(response.playlist) {
        	var playlist = [];
        	$.each(response.playlist, function(index, playable) {
        		playable.index = index;
    			playable.poster = settings.filePath + '/cover_120.jpg';
        		if(playable.xfd) {
        			playable.mp3 = settings.filePath + '/xfd.mp3';
        		} else {
            		playable.mp3 = (playable.id) ? settings.filePath + '/' + playable.checksum + '.mp3' : '';
        		}
        		if(settings.filter) {
        			settings.filter(playlist, playable)
        		} else {
        			playlist.push(playable);
        		}
        	});
        	
        	if(playlist.length > 0) {
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
	                	if(data.trackId) {
	                		$.post(settings.rateUrl, data).done(function(response) {
	                    		if(response.result) {
	                    			$rateGroup.data(data);
	                    			jPlaylist._updateControls();
	                    		} else {
	                    			alert(response.error);
	                    		}
	                    	});
	                	}
	                });
	            }
        	} else {
        		$('#jquery_jplayer_1, #jp_container_1').hide();
        	}
        }
    });
}
