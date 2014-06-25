var player = function(option) {
	var settings = $.extend({
		playlistOptions: {
            enableRemoveControls: true
        },
        supplied: 'mp3',
        smoothPlayBar: true,
        keyEnabled: true,
        audioFullScreen: true,
        volume : 0.5
	}, option);
	
    $.getJSON(settings.url).done(function(response){
        if(response.trackList) {
        	var playlist = [];
        	$.each(response.trackList, function(index, track) {
        		var title = track.name;
                var artist = track.artist;
                var original = track.original;
                var instrumental = (track.instrumental == '1');
                var mp3 = settings.filePath + '/f' + track.id + '.mp3';
                var poster = settings.filePath + '/cover_400.jpg';
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
            }
        }
    });
}
