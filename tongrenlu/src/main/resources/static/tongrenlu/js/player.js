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
	
	var dataUrl = option.root + 'music/' + option.articleId + '/track';
    $.getJSON(dataUrl).done(function(response){
        if(response.trackList) {
        	var playlist = [];
        	$.each(response.trackList, function(index, track) {
        		var title = track.name;
                var artist = track.artist;
                var original = track.original;
                var mp3 = option.root + 'files/m' + option.articleId + '/f' + track.id + '.mp3';
        		var playable = {
        				title: title,
        				artist: artist,
                        original: original,
        				mp3: mp3
        		};
        		playlist.push(playable);
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
    
    $('#jp_container_N').on('click', '.jp-add-playlist', function(){
    	if(window.user) {
    		loadMyPlaylistName();
    	} else {
    		window.dologin();
    	}
    })
}

var loadMyPlaylistName = function(){
    $.getJSON('/fm/my/playlist/name', function(response){
        if(response.result) {
            $('#playlist-name-list').html( tmpl('playlistDialogTemplate', response) );
            $('#playlist-modal').modal();
        } else {
            alert(response.error);
        }
    }).error(function(){
        alert('服务器⑨了。');
    }).complete(function(){
    });
}