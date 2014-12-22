// ==UserScript==
// @name       tongrenlu smartSearch
// @namespace  www.rpsg-team.com
// @version    1.0
// @description 東方同人录 智能搜索
// @match      http://www.tongrenlu.info/
// @copyright  rpsg-team
// @author 学生_(:3」∠)_
// ==/UserScript==
$(function(){
	var settings = {
		searchUrl: "http://www.tongrenlu.info/fm/search/music",
		imgPrefix: "http://files.tongrenlu.info/m",
		imgSurfix: "/cover_60.jpg",
		musicUrl: "http://www.tongrenlu.info/music/"
	};
    var $input = $("#inputMusicSearch").blur(function(){
        var timeout= setTimeout(function(){
        	$searchResult.empty();
        }, 200);
    });
    
    var $searchResult = $("<div class='search-result' id='search-result'></div>").insertAfter($input);
	$searchResult.width($input.width());
    $input.on("input", function(e){
        if($input.val().length===0) {
        	$searchResult.empty();
        	return;
        }
        $.getJSON(settings.searchUrl, {q : $input.val()}).done(function(response) {
        	$searchResult.empty();
        	var trackList = response.searchResult.content;
        	$.each(trackList, function(index, track) {
        		var imgUrl = settings.imgPrefix + track.articleId + settings.imgSurfix;
        		var linkUrl = settings.musicUrl + track.articleId;
        		var html = '<div class="media" data-link="' + linkUrl + '">'
        					+ '<a class="media-left">'
        						+ '<img src="' + imgUrl + '" class="img-60"/>'
        					+ '</a>'
        					+ '<div class="media-body">'
        						+ '<a >' + track.title + '</a>'
        					+ '</div>'
            			+ '</div>';
        		$searchResult.append(html);
        	});
        });
    });
    
    $(document).on('click', '#search-result .media', function() {
    	var link = $(this).data("link");
    	location.href = link;
    })
});
