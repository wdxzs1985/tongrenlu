var playerInstance = null;
var userBean = null;
var SEARCH_SIZE = 15;
var LAST_PAGE;
var LAST_PAGE_ARCHOR;

var initNavbar = function() {
    var $window = $(window);
    $window.bind('beforeunload', function(event) {
		return "是否退出？"
	});
    $window.bind('hashchange', function() {
		var hash = window.location.hash;
		if(hash.match(/^#library$/)) {
			reset();
			$('#nav-library').addClass('active');
	        $('#library').show();
			LAST_PAGE = '';
		} else if(hash.match(/^#playlist$/)) {
			reset();
			$('#nav-playlist').addClass('active');
			var $playlist = $('#playlist');
			if(!$playlist.data('init')) {
				loadPlaylist();
				$playlist.data('init', true);
			}
			$playlist.show();
			scorllToArticle($playlist, LAST_PAGE_ARCHOR);
			LAST_PAGE = '#playlist';
		} else if(hash.match(/^#my-collect$/)) {
			reset();
			$('#nav-my-collect').addClass('active');
			var $mycollect = $('#my-collect');
			if(!$mycollect.data('init')) {
				loadMyCollect();
				$mycollect.data('init', true);
			}
			$mycollect.show();
			scorllToArticle($mycollect, LAST_PAGE_ARCHOR);
			LAST_PAGE = '#my-collect';
		} else if(hash.match(/^#my-playlist$/)) {
			reset();
			$('#nav-my-playlist').addClass('active');
			var $myplaylist = $('#my-playlist');
			if(!$myplaylist.data('init')) {
				loadMyPlaylist();
				$myplaylist.data('init', true);
			}
			$myplaylist.show();
			LAST_PAGE = '#my-playlist';
		} else if(hash.match(/^#album\/\d{15}/)) {
			reset();
			var articleId = hash.match(/\d{15}/)[0];
			if(articleId) {
				displayAlbum(articleId);
			}
		} else if(hash.match(/^#track\/\d{15}/)) {
			reset();
			var trackId = hash.match(/\d{15}/)[0];
			if(trackId) {
				
			}
		} else if(hash.match(/^#playlist\/\d{15}/)) {
			reset();
			var articleId = hash.match(/\d{15}/)[0];
			if(articleId) {
				displayPlaylist(articleId);
			}
		} else if(hash.match(/^#player$/)) {
			reset();
			$('#nav-player').addClass('active');
			displayPlaying();
			LAST_PAGE = '';
		} else {
			reset();
			$('#nav-album').addClass('active');
			var $album = $('#album');
			if(!$album.data('init')) {
				searchAlbum('');
				$album.data('init', true);
			}
			$album.show();
			scorllToArticle($album, LAST_PAGE_ARCHOR);
			LAST_PAGE = '#album';
		}
	});

    $('#nav-login').click(function(){
        doLogin(function(){
            alert('登录成功！');
        });
        return false;
    });
}

var scorllToArticle = function($container, articleId){
	if(articleId != '') {
		$container.find('.cover-object').each(function(index, element) {
        	var $element = $(element);
        	if ($element.data('articleId') == articleId) {
        		var p = $element.offset().top - $('.navbar').height();
        		$('html,body').animate({ scrollTop: p }, 'fast');
        	}
        });
	}
}

var initPlayer = function() {
    playerInstance = new jPlayerPlaylist({
        jPlayer: "#jquery_jplayer_N",
        cssSelectorAncestor: "#jp_container_N"
        }, [], {
        playlistOptions: {
            autoPlay: false,
            "enableRemoveControls": true,
            displayTime: 0,
            "removeTime": 0
        },
        swfPath: "/static/jplayer/js",
        supplied: "mp3",
        size: { cssClass: "jp-video-270p" },
        volume : 0.5,
        loop: true
    });
}

var initAlbum = function(){
    var $container = $('#album');
    
    $container.find('.search-form').submit(function(){
        var $this = $(this);
        var query = $this.find( 'input' ).val();
        searchAlbum(query);
        return false;
    });
    
//    $container.find('.search-form input').typeahead({
//        source: function(query, process){
//            $.get('/tag/search', {
//                q: query
//            },function(response){
//                process(response);
//            });
//        },
//        items: 10
//    });
};

var searchAlbum = function(query){
    var $container = $('#album');
    var $list = $container.find('.media-list').empty();
    var $more = $container.find('a.more');
    
    var param = {q: query, p: 1, s: SEARCH_SIZE};

    $more.unbind();
    $more.click(function(){
    	$.getJSON('/fm/music', param, function(response){
    		$list.append($( "#musicItemTemplate" ).render( response ));
            if(response.page.last){
            	$more.hide();
            } else {
            	$more.show();
            	param.p++;
            }
            
        }).error(function(){
            alert('服务器⑨了。');
        }).complete(function(){
        });
    }).click();
    
    $('#nav-album').addClass('active');
    $container.show();
};

var initLibrary = function(query){
    var $container = $('#library');
    var $content = $container.find('.content').hide();
    var $empty = $container.find('.empty').show();
    
    $container.find('.search-form').submit(function(){
        var $this = $(this);
        var query = $this.find( 'input' ).val();
        searchLibrary(query);
        return false;
    });
}

var searchLibrary = function(query){
    var $container = $('#library');
    var $content = $container.find('.content');
    var $empty = $container.find('.empty');
    var $list = $container.find('.media-list').empty();
    var $more = $container.find('a.more');
    
    if (query == '') {
    	$empty.show();
		$content.hide();
    } else {
	    var param = {q: query, p: 1, s: SEARCH_SIZE};
	    $more.unbind().click(function(){
	    	$.getJSON('/fm/library', param, function(response){
	    		if(response.page.itemCount == 0){
	    			$empty.show();
	    			$content.hide();
	    		} else {
	    			$content.show();
	    			$empty.hide();
	        		var $fragment = $($( "#libraryItemTemplate" ).render( response ));
	        		$fragment.filter('.cover-object').each(function(index, element){
	        			$(element).on('click', 'a.play', function(){
	        				playTrack(response.playlist[index]);
		                    return false;
	        			});
	        		});
	        		
	        		$list.append($fragment);
	                if(response.page.last){
	                	$more.hide();
	                } else {
	                	$more.show();
	                	param.p++;
	                }
	    		}
	        }).error(function(){
	            alert('服务器⑨了。');
	        }).complete(function(){
	        });
	    }).click();
    }
    
    $('#nav-library').addClass('active');
    $container.show();
};


var displayAlbum = function(articleId){
    $('.page').hide();
    var $container = $('#info-page').empty();
	$('html,body').animate({ scrollTop: 0 }, 'fast');
    
    $.getJSON('/fm/music/' + articleId, {}, function(response){
        $container.html($( "#musicInfoTemplate" ).render( response ));
        var isPlaying = false;
        var playingIndex = -1;
        $container.find('a.add-and-play').click(function(){
            var index = $(this).data('index');
            var trackBean = response.playlist[index];
            playTrack(trackBean);
            return false;
        });
        
        $container.find('.play-all').click(function(){
        	var index = $(this).data('index');
        	playerInstance.setPlaylist(response.playlist);
        	playerInstance.play(index);
            return false;
        });

        var $collect = $container.find('.collect');
        $collect.click(function(){
            var $this = $(this);
            var href = $this.data('href');
            $.post(href, function(response){
                if(response.result){
                	if (response.collected) {
                    	$collect.removeClass('btn-danger');
                    	$collect.addClass('btn-success');
                		$collect.text('收藏daze!');
                    } else {
                    	$collect.removeClass('btn-success');
                    	$collect.addClass('btn-danger');
                		$collect.text('收藏wo!');
                    }
                } else {
                    alert(response.error);
                }
            }).error(function(){
                alert("服务器⑨了");
            });
            return false;
        });

        if(LAST_PAGE) {
        	$container.find('.sub-nav').show();
        	LAST_PAGE_ARCHOR = articleId;
        	
        	$container.find('.back').click(function(){
        		window.location.hash = LAST_PAGE;
        	});
        } else {
        	$container.find('.sub-nav').hide();
        }
        
    }).error(function(){
        alert('服务器⑨了。');
    }).complete(function(){
    });
    
    $container.show();
};

var loadPlaylist = function(){
    var $container = $('#playlist').show();
    var $list = $container.find('.media-list').empty();
    var param = {p: 1, s: SEARCH_SIZE};

    var $more = $container.find('a.more').unbind();
    $more.click(function(){
    	$.getJSON('/fm/playlist', param, function(response){
    		$list.append($( "#playlistItemTemplate" ).render( response ));
            if(response.page.last){
            	$more.hide();
            } else {
            	param.p++;
            	$more.show();
            }
        }).error(function(){
            alert('服务器⑨了。');
        }).complete(function(){
        });
        return false;
    }).click();
};

var displayPlaylist = function(articleId){
    $('.page').hide();
    var $container = $('#info-page').empty().show();
	$('html,body').animate({ scrollTop: 0 }, 'fast');
    
    $.getJSON('/fm/playlist/' + articleId, {}, function(response){
    	if(userBean && userBean.userId == response.articleBean.userBean.userId) {
            $container.html($( "#myPlaylistInfoTemplate" ).render( response ));
    	} else {
            $container.html($( "#playlistInfoTemplate" ).render( response ));
    	}
    	
        $container.find('.cover-object').each(function(index, element){
			$(element).on('click', 'a.play', function(){
				playerInstance.setPlaylist(response.playlist);
	        	playerInstance.play(index);
                return false;
			})
        })
        
        $container.find('a.remove').click(function(){
			if(confirm('真的要删除吗?')) {
				var $this = $(this);
	            var fileId = $this.data('fileId');
	            $.post('/fm/my/playlist/removetrack', { 'articleId': articleId, 'fileId': fileId }, function( response ){
	                if(response.result) {
	                    $this.parents('li.media').remove();
	                } else {
	                    alert(response.error);
	                }
	            }).error(function(){
	                alert('服务器⑨了。');
	            }).complete(function(){
	            });
                return false;
            }
		});
        
        if(LAST_PAGE) {
        	$container.find('.sub-nav').show();
        	LAST_PAGE_ARCHOR = articleId;
        	
        	$container.find('.back').click(function(){
        		window.location.hash = LAST_PAGE;
        	});
        } else {
        	$container.find('.sub-nav').hide();
        }
    }).error(function(){
        alert('服务器⑨了。');
    }).complete(function(){
    });
};

var initMyPlaylist = function(){
    var $container = $('#my-playlist');
    
    $container.on('click', 'a.remove', function(){
        if(confirm('真的要删除吗?')) {
            var $this = $(this);
            var articleId = $this.data('articleId');
            $.post('/fm/my/playlist/remove', { 'articleId': articleId }, function( response ){
                if(response.result) {
                    $this.parents('li.media').remove();
                } else {
                    alert(response.error);
                }
            }).error(function(){
                alert('服务器⑨了。');
            }).complete(function(){
            });
        }
    });
}

var loadMyPlaylist = function(){
    var $container = $('#my-playlist').show();
    var $list = $container.find('.media-list').empty();
    var param = {p: 1, s: SEARCH_SIZE};
    
    var $more = $container.find('a.more').unbind();
    $more.click(function(){
    	$.getJSON('/fm/my/playlist', param, function(response){
    		if(response.result) {
	    		$list.append($( "#myPlaylistSearchTemplate" ).render( response ));
	            if(response.page.last){
	            	$more.hide();
	            } else {
	            	param.p++;
	            	$more.show();
	            }
            } else {
                //alert(response.error);
                doLogin(function(){
                	loadMyPlaylist();
                });
            }
        }).error(function(){
            alert('服务器⑨了。');
        }).complete(function(){
        });
        return false;
    }).click();
};

var displayMyPlaylist = function(articleId){
    $('.page').hide();
	$('html,body').animate({ scrollTop: 0 }, 'fast');
    var $container = $('#info-page').empty().show();
    $.getJSON('/fm/playlist/' + articleId, {}, function(response){
        $container.html($( "#myPlaylistInfoTemplate" ).render( response ));
        $container.find('.cover-object').each(function(index, element){
			$(element).on('click', 'a.play', function(){
				playerInstance.setPlaylist(response.playlist);
	        	playerInstance.play(index);
                return false;
			});
        });
    }).error(function(){
        alert('服务器⑨了。');
    }).complete(function(){
    });
};

var loadMyCollect = function(){
    var $container = $('#my-collect').show();
    var $list = $container.find('.media-list').empty();
    var param = {p: 1, s: SEARCH_SIZE};
    
    var $more = $container.find('a.more').unbind();
    $more.click(function(){
    	$.getJSON('/fm/my/collect', param, function(response){
    		if(response.result) {
	    		$list.append($( "#myCollectTemplate" ).render( response ));
	            if(response.page.last){
	            	$more.hide();
	            } else {
	            	param.p++;
	            	$more.show();
	            }
	            $container.find('a.remove').click(function(){
	                if(confirm('真的要删除吗?')) {
	                    var $this = $(this);
	                    var articleId = $this.data('articleId');
	                    $.post('/fm/my/collect/remove', { 'articleId': articleId }, function( response ){
	                        if(response.result) {
	                            $this.parents('li.media').remove();
	                        } else {
	                            alert(response.error);
	                        }
	                    }).error(function(){
	                        alert('服务器⑨了。');
	                    }).complete(function(){
	                    });
	                }
	            });
            } else {
                doLogin(function(){
                	loadMyCollect();
                });
            }
        }).error(function(){
            alert('服务器⑨了。');
        }).complete(function(){
        });
        return false;
    }).click();
};

var initPlaying = function() {
	$('#playing').on('click', 'a.save-playlist', function(){
	    savePlaylist();
	    return false;
	}).on('click', 'a.clear-playlist', function(){
	    playerInstance.remove();
		displayPlaying();
	    return false;
	})
}

var displayPlaying = function() {
    var $container = $('#playing');
    var $subNav = $container.find('.sub-nav');
    var $list = $container.find('.media-list');
    var $empty = $container.find('.empty');
	//console.log(playerInstance.playlist.length);
    $list.empty();
	if(playerInstance.playlist.length == 0) {
		$subNav.hide();
		$empty.show();
	} else {
		$subNav.show();
		$empty.hide();
		$list.append($( "#playingItemTemplate" ).render( playerInstance ));
        $list.find('a.remove').click(function(){
        	var index = $(this).data('index');
        	playerInstance.remove(index);
        	displayPlaying();
        	return false;
        });
        $list.find('a.play').click(function(){
        	var index = $(this).data('index');
        	playerInstance.play(index);
        	return false;
        });
	}
	$container.show();
}

var playTrack = function(trackBean) {
    var indexInPlayer = -1;
    for(var i = 0; i < playerInstance.playlist.length; i++) {
    	if(trackBean.fileId == playerInstance.playlist[i].fileId){
    		indexInPlayer = i;
    		break;
    	}
    }
    if(indexInPlayer == -1){
    	playerInstance.add(trackBean, true);
    } else {
    	playerInstance.play(indexInPlayer);
    }
}

var savePlaylist = function(){
	if(playerInstance.playlist.length == 0) {
		alert('没有选择音乐');
		return;
	}
    if(userBean == null){
        doLogin(function(){
        	savePlaylist();
        });
    } else {
        loadMyPlaylistName();
        $('#playlistDialog').modal();
    }
};

var loadMyPlaylistName = function(){
    $.getJSON('/fm/my/playlist/name', function(response){
        if(response.result) {
            $('#playlist-name-list').html( $('#playlistDialogTemplate').render(response.items) );
        } else {
            alert(response.error);
        }
    }).error(function(){
        alert('服务器⑨了。');
    }).complete(function(){
    });
}

var initNewPlaylist = function(){
    var $newPlaylistForm = $('#new-playlist-form');
    $('#new-playlist').click(function(){
        $newPlaylistForm.addClass('in');
        $newPlaylistForm.find('input').focus();
        return false;
    });
    $('#new-playlist-form-submit').click(function(){
        var $this = $(this);
        $this.prop('disabled', true);
        $.post('/fm/my/playlist', $newPlaylistForm.serialize(), function(response){
            if(response.result) {
                $newPlaylistForm.removeClass('in');
                loadMyPlaylistName();
            } else {
                alert(response.error);
            }
        }).error(function(){
            alert('服务器⑨了。');
        }).complete(function(){
            $this.prop('disabled', false);
        });
        return false;
    });
    $('#new-playlist-form-cancel').click(function(){
        $newPlaylistForm.removeClass('in');
        return false;
    });
    
    var $playlistNameList = $('#playlist-name-list');
    $playlistNameList.on('click', 'li a', function(){
        var $this = $(this);
        var articleId = $this.data('articleId');
        var fileIdArray = [];
        $.each(playerInstance.playlist, function(index, element){
        	fileIdArray.push(element.fileId);
        });
        
        var data = {
            'articleId': articleId,
            'fileId': fileIdArray
        }
        $.post('/fm/my/playlist/addtrack', data, function(response){
            if(response.result) {
                $('#playlistDialog').modal('hide');
                alert('添加成功！');
            } else {
                alert(response.error);
            }
        }).error(function(){
            alert('服务器⑨了。');
        }).complete(function(){
        });
        return false;
    });
}

var initLogin = function() {
    $.getJSON('/fm/login', function(response){
        if(response.result) {
            userBean = {
                userId: response.userId,
                nickname: response.nickname
            };
            $("#nav-login").text(response.nickname).unbind('click');
        } else {
            userBean = null;
        }
    }).error(function(){
        alert('服务器⑨了。');
    }).complete(function(){
    });
};

var doLogin = function( callback ) {
    var $dialog = $('#loginDialog').modal();
    $('#login-submit').unbind('click').click(function(){
        var $this = $(this);
        $this.prop('disabled', true);
        $("#ep").val(MD5_hexhash(MD5_hexhash($("#p").val())));
        $form = $dialog.find('form');
        $.post('/fm/login', $form.serialize(), function(response){
            if(response.result) {
                userBean = {
                    userId: response.userId,
                    nickname: response.nickname
                };
                $("#nav-login").text(response.nickname).unbind('click');
                $dialog.modal('hide');
                callback.call();
            } else if(response.error){
                alert(response.error);
            } else if(response.email_error){
                alert(response.email_error);
            } else if(response.password_error){
                alert(response.password_error);
            }
        }).error(function(){
            alert('服务器⑨了。');
        }).complete(function(){
            $this.prop('disabled', false);
        });
    });
};

var reset = function() {
    $('.page').hide();
    $('.nav li').removeClass('active');
    
    if(!$('.btn-navbar').hasClass('collapsed')) {
    	$('.btn-navbar').addClass('collapsed');
    	$('.nav-collapse').removeClass('in').height(0);
    }
}

$(function(){
	reset();
	initNavbar();
	initPlayer();
    initAlbum();
    initLibrary();
    initPlaying();
    initMyPlaylist();
    initNewPlaylist();
    initLogin();

    var $window = $(window);
    $window.trigger('hashchange');
});
