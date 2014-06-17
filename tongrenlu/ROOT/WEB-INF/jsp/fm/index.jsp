<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="/WEB-INF/jsp/inc/page_config.jsp" %>
<html>
<head>
<title><fmt:message key="app.fm"/></title>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="apple-mobile-web-app-capable" content="yes" />
<link href="<%= STATIC_PATH %>/flat/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="<%= STATIC_PATH %>/bootstrap-2.3.2/css/bootstrap-responsive.min.css" rel="stylesheet" type="text/css" />
<link type="text/css" rel="stylesheet" href="<%= STATIC_PATH %>/jplayer/skin/blue.monday/jplayer.blue.monday.css" />
<link type="text/css" rel="stylesheet" href="<%= CSS_PATH %>/fm_20130608.css" rel="stylesheet" />
<link type="text/css" rel="stylesheet" href="<%= CSS_PATH %>/fm_mobile_20130608.css" rel="stylesheet" />
</head>
<body>
<div id="wrapper">
    <div id="navbar" class="navbar navbar-fixed-top">
        <div class="navbar-inner">
            <div class="container-fluid">
                <!-- .btn-navbar is used as the toggle for collapsed navbar content -->
                <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </a>
                <a class="brand" href="<c:url value="/"/>"><fmt:message key="app.fm"/></a>
                <div class="nav-collapse collapse">
                    <ul class="nav">
                        <li id="nav-album"><a href="#album">所有专辑</a></li>
                        <li id="nav-library"><a href="#library">搜索曲库</a></li>
                        <li class="dropdown">
                            <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown">播放列表 <b class="caret"></b></a>
                            <ul class="dropdown-menu">
                                <li id="nav-playlist"><a href="#playlist">所有播放列表</a></li>
                                <li id="nav-my-collect"><a href="#my-collect">我的收藏</a></li>
                                <li id="nav-my-playlist"><a href="#my-playlist">我的播放列表</a></li>
                            </ul>
                        </li>
                        <li id="nav-playing"><a href="#player">正在播放</a></li>
                    </ul>
                    <ul class="nav pull-right">
                        <li><a href="javascript:;" id="nav-login"><fmt:message key="app.login"/></a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
    <div id="main">
        <div id="album" class="page">
            <div class="sub-nav">
	            <form class="search-form">
	                <input type="text" class="input-block-level" placeholder="搜索专辑">
                    <button type="submit" class="btn btn-success">搜索</button>
	            </form>
            </div>
            <ul class="media-list cover-list">
            </ul>
            <div class="padding-20">
                <a href="javascript:;"  class="btn btn-block btn-large more">加载更多</a>
            </div>
        </div>
        <div id="library" class="page">
            <div class="sub-nav">
                <form class="search-form">
                    <input type="text" class="input-block-level" placeholder="搜索曲库">
                    <button type="submit" class="btn btn-success">搜索</button>
                </form>
            </div>
            <div class="content">
	            <ul class="media-list cover-list">
	            </ul>
	            <div class="padding-20">
	                <a href="javascript:;"  class="btn btn-block btn-large more">加载更多</a>
	            </div>
            </div>
            <div class="empty">
                <div class="alert alert-info text-center">
                    <h3>请输入关键词搜索</h3>
                    <p>可以按曲名，表演者，原作，原曲搜索</p>
                </div>
            </div>
        </div>
        <div id="playlist" class="page">
            <ul class="media-list cover-list-small">
            </ul>
            <div class="padding-20">
                <a href="javascript:;"  class="btn btn-block btn-large more">加载更多</a>
            </div>
        </div>
        <div id="my-collect" class="page">
            <ul class="media-list cover-list">
            </ul>
            <div class="padding-20">
                <a href="javascript:;"  class="btn btn-block btn-large more">加载更多</a>
            </div>
        </div>
        <div id="my-playlist" class="page">
            <ul class="media-list cover-list-small">
            </ul>
            <div class="padding-20">
                <a href="javascript:;"  class="btn btn-block btn-large more">加载更多</a>
            </div>
        </div>
        <div id="playing" class="page">
            <div class="sub-nav">
                <a href="javascript:;" class="fm-btn save-playlist">
                    <i class="icon icon-circle-plus"></i>
                    <span>保存列表</span>
                </a>
                <a href="javascript:;" class="fm-btn clear-playlist">
                    <i class="icon icon-circle-remove"></i>
                    <span>全部清空</span>
                </a>
            </div>
            <ul class="media-list cover-list">
            </ul>
            <div class="empty">
                <div class="alert alert-info text-center">
                    <h3>没有正在播放的音乐</h3>
	                <div class="btn-group">
	                    <a href="#album" class="btn btn-success">去曲库找歌</a>
	                </div>
                </div>
            </div>
        </div>
        <div id="info-page" class="page"></div>
    </div>
    <div id="jp_container_N" class="jp-video">
        <div class="jp-type-playlist">
            <div id="jquery_jplayer_N" class="jp-jplayer"></div>
            <div class="jp-gui">
                <div class="jp-video-play">
                    <a href="javascript:;" class="jp-video-play-icon" tabindex="1">play</a>
                </div>
                <div class="jp-interface">
                    <div class="jp-progress">
                        <div class="jp-seek-bar">
                            <div class="jp-play-bar"></div>
                        </div>
                    </div>
                    <div class="jp-current-time"></div>
                    <div class="jp-duration"></div>
                    <div class="jp-controls-holder">
                        <ul class="jp-controls">
                            <li><a href="javascript:;" class="jp-previous" tabindex="1">previous</a></li>
                            <li><a href="javascript:;" class="jp-play" tabindex="1">play</a></li>
                            <li><a href="javascript:;" class="jp-pause" tabindex="1">pause</a></li>
                            <li><a href="javascript:;" class="jp-next" tabindex="1">next</a></li>
                            <li><a href="javascript:;" class="jp-stop" tabindex="1">stop</a></li>
                            <li><a href="javascript:;" class="jp-mute" tabindex="1" title="mute">mute</a></li>
                            <li><a href="javascript:;" class="jp-unmute" tabindex="1" title="unmute">unmute</a></li>
                            <li><a href="javascript:;" class="jp-volume-max" tabindex="1" title="max volume">max volume</a></li>
                        </ul>
                        <ul class="jp-toggles">
                            <li><a href="javascript:;" class="jp-shuffle" tabindex="1" title="shuffle">shuffle</a></li>
                            <li><a href="javascript:;" class="jp-shuffle-off" tabindex="1" title="shuffle off">shuffle off</a></li>
                            <li><a href="javascript:;" class="jp-repeat" tabindex="1" title="repeat">repeat</a></li>
                            <li><a href="javascript:;" class="jp-repeat-off" tabindex="1" title="repeat off">repeat off</a></li>
                        </ul>
                        <div class="jp-volume-bar">
                            <div class="jp-volume-bar-value"></div>
                        </div>
                    </div>
                    <div class="jp-title">
                        <ul>
                            <li>没有选择音乐</li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="jp-playlist">
                <ul>
                    <!-- The method Playlist.displayPlaylist() uses this unordered list -->
                    <li></li>
                </ul>
            </div>
            <div class="jp-no-solution">
                <span>Update Required</span>
                To play the media you will need to either update your browser to a recent version or update your <a href="http://get.adobe.com/flashplayer/" target="_blank">Flash plugin</a>.
            </div>
        </div>
    </div>
</div>
<!--  -->
<div id="loginDialog" class="modal hide fade">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h3>请先登录</h3>
    </div>
    <div class="modal-body">
        <form method="post">
            <label for="email">电子邮箱</label>
            <input type="text" id="email" name="email" class="input-block-level" placeholder="电子邮箱">
            <label for="p">密码</label>
            <input type="password" id="p" class="input-block-level" placeholder="密码">
            <input type="hidden" id="ep" name="password">
            <label class="checkbox">
              <input type="checkbox" name="remember" value="1" checked> 记住我
            </label>
        </form>
    </div>
    <div class="modal-footer">
        <a href="/register" class="btn pull-left" target="_blank">注册</a>
        <a href="javascript:;" id="login-submit" class="btn btn-primary">登录</a>
    </div>
</div>
<!--  -->
<div id="playlistDialog" class="modal hide fade">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h3>加入到</h3>
    </div>
    <div class="modal-body">
        <ul id="playlist-name-list" class="media-list clearfix">
        </ul>
        <form id="new-playlist-form" class="form-inline fade" method="post">
            <input type="text" name="title" class="span2" placeholder="新建播放列表" value="新建播放列表">
            <button class="btn btn-primary" id="new-playlist-form-submit"><i class="icon-white icon-ok "></i></button>
            <button class="btn" id="new-playlist-form-cancel"><i class="icon icon-remove"></i></button>
        </form>
    </div>
    <div class="modal-footer">
        <a href="javascript:;" id="new-playlist" class="btn btn-primary">新播放列表</a>
    </div>
</div>
<!--  -->
<script type="text/javascript" src="<%= STATIC_PATH %>/jquery/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="<%= STATIC_PATH %>/bootstrap-2.3.2/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%= STATIC_PATH %>/jplayer/js/jquery.jplayer.min.js"></script>
<script type="text/javascript" src="<%= STATIC_PATH %>/jplayer/js/jplayer.playlist.min.js"></script>
<script type="text/javascript" src="<%= STATIC_PATH %>/plugins/jsrender.js"></script>
<script type="text/javascript" src="<%= STATIC_PATH %>/plugins/md5.js"></script>
<script type="text/javascript" src="<%= STATIC_PATH %>/tongrenlu/js/fm_20130608.js"></script>
<%-- 音乐列表模板 --%>
<script id="musicItemTemplate" type="text/x-jquery-tmpl">
{{for page.items}}
    <li class="media cover-object" data-article-id="{{>articleId}}">
        <a href="#album/{{>articleId}}" class="thumbnail">
            <img class="" src="<%= FILE_PATH %>/{{>articleId}}/cover_120.jpg">
        </a>
        <div class="media-body">
            <a href="#album/{{>articleId}}" class="media-heading">{{>title}}</a>
        </div>
    </li>
{{/for}}
</script>
<%-- 音乐曲目列表模板 --%>
<script id="musicInfoTemplate" type="text/x-jquery-tmpl">
    <div class="media sub-nav">
        <a href="javascript:;" class="fm-btn back">
            <i class="icon icon-circle-arrow-left"></i>
            <span>返回</span>
        </a>
    </div>
    <div id="albuminfo" class="media">
        <h2 class="media-heading">{{>articleBean.title}}</h2>
        <div class="media-body cover-object">
	        <a href="javascript:;" data-index="0" class="thumbnail play-all">
	            <img src="<%= FILE_PATH %>/{{>articleBean.articleId}}/cover_400.jpg">
	        </a>
        </div>
        <div class="btn-group">
            <a href="javascript:;" data-index="0" class="btn btn-primary play-all">播放所有曲目</a>
            {{if articleBean.collectFlg == '0'}}
                <a href="javascript:;" data-href="<c:url value="/article/{{>articleBean.articleId}}/collect" />" class="btn btn-danger collect">收藏wo!</a>
            {{/if}}
            {{if articleBean.collectFlg != '0'}}
                <a href="javascript:;" data-href="<c:url value="/article/{{>articleBean.articleId}}/collect" />" class="btn btn-success collect">收藏daze!</a>
            {{/if}}
        </div>
    </div>
    {{if playlist.length > 0}}
        <ul id="tracklist" class="cover-list-small media-list">
            {{for playlist}}
                <li class="media cover-object">
                    <div class="pull-right hidden-phone">
                        <a href="javascript:;" data-index="{{:#index}}" class="add-and-play fm-btn" title="添加到播放列表"><i class="icon icon-circle-plus"></i></a>
                    </div>
                    <div class="media-body">
	                    <a href="javascript:;" data-index="{{:#index}}" class="play-all media-heading" title="播放" >
	                       {{:#index+1}}. {{>title}} {{if artist }}<small>by {{>artist}}</small>{{/if}}
	                    </a>
                    </div>
                </li>
            {{/for}}
        </ul>
    {{/if}}
    {{if playlist.length == 0}}
    {{/if}}
</script>
<%-- 播放列表模板 --%>
<script id="playlistItemTemplate" type="text/x-jquery-tmpl">
{{for page.items}}
    <li class="media cover-object" data-article-id="{{>articleId}}">
        <div class="media-body">
            <a href="#playlist/{{>articleId}}" class="media-heading">
                {{>title}} <small>by {{>userBean.nickname}}</small>
            </a>
        </div>
    </li>
{{/for}}
</script>
<%-- 播放列表曲目列表模板 --%>
<script id="playlistInfoTemplate" type="text/x-jquery-tmpl">
    <div class="media sub-nav">
        <a href="javascript:;" class="fm-btn back">
            <i class="icon icon-circle-arrow-left"></i>
            <span>返回</span>
        </a>
    </div>
    <div id="albuminfo" class="media">
        <div class="media-body">
            <h3 class="media-heading">{{>articleBean.title}}</h3>
            <a href="<c:url value="/user"/>/{{>articleBean.userBean.userId}}" target="_blank">{{>articleBean.userBean.nickname}}</a> 创建
        </div>
    </div>
    {{if playlist.length > 0}}
        <ul id="tracklist" class="cover-list-small media-list">
            {{for playlist}}
                <li class="media cover-object">
                    <a href="javascript:;" class="play thumbnail">
                        <img class="" src="<%= FILE_PATH %>/{{>articleId}}/cover_120.jpg">
                    </a>
                    <div class="media-body">
			            <a href="javascript:;" class="media-heading play" title="播放" >
			                {{:#index+1}}. {{>title}} {{if artist }}<small>by {{>artist}}</small>{{/if}}
			            </a>
			        </div>
                </li>
            {{/for}}
        </ul>
    {{/if}}
    {{if playlist.length == 0}}
    {{/if}}
</script>
<%-- 我的播放列表模板 --%>
<script id="myPlaylistSearchTemplate" type="text/x-jquery-tmpl">
{{for page.items}}
    <li class="media cover-object" data-article-id='{{>articleId}}'>
        <div class="pull-right hidden-phone">
            <a href="javascript:;" data-article-id='{{>articleId}}' class="remove fm-btn" title="删除播放列表"><i class="icon icon-circle-remove"></i></a>
        </div>
        <div class="media-body">
            <a href="#playlist/{{>articleId}}" class="media-heading">
                {{>title}} 
            </a>
        </div>
    </li>
{{/for}}
</script>
<%-- 播放列表曲目列表模板 --%>
<script id="myPlaylistInfoTemplate" type="text/x-jquery-tmpl">
    <div class="media sub-nav">
        <a href="#my-playlist" class="fm-btn back">
            <i class="icon icon-circle-arrow-left"></i>
            <span>返回</span>
        </a>
    </div>
    <div id="albuminfo" class="media">
        <div class="media-body">
            <h3 class="media-heading">{{>articleBean.title}}</h3>
            <a href="<c:url value="/user"/>/{{>articleBean.userBean.userId}}" target="_blank">{{>articleBean.userBean.nickname}}</a> 创建
        </div>
    </div>
    {{if playlist.length > 0}}
        <ul id="tracklist" class="cover-list media-list">
            {{for playlist}}
                <li class="media cover-object">
                    <a href="javascript:;" class="play thumbnail">
                        <img class="" src="<%= FILE_PATH %>/{{>articleId}}/cover_120.jpg">
                    </a>
                    <div class="pull-right hidden-phone">
                        <a href="javascript:;" data-file-id="{{>fileId}}" class="remove fm-btn" title="从播放列表移除"><i class="icon icon-circle-remove"></i></a>
                    </div>
                    <div class="media-body">
                        <a href="javascript:;" class="media-heading play">
                            {{:#index+1}}. {{>title}} {{if artist }}<small>by {{>artist}}</small>{{/if}}
                        </a>
                    </div>
                </li>
            {{/for}}
        </ul>
    {{/if}}
</script>
<%-- 播放列表对话框模板 --%>
<script id="playlistDialogTemplate" type="text/x-jquery-tmpl">
<li class="media">
    <div class="media-body">
        <a href="javascript:;" data-article-id='{{>articleId}}'>{{>title}}</a>
    </div>
</li>
</script>
<%-- 我的收藏模板 --%>
<script id="myCollectTemplate" type="text/x-jquery-tmpl">
{{for page.items}}
    <li class="media cover-object" data-article-id='{{>articleId}}'>
        <a href="#album/{{>articleId}}" class="thumbnail">
            <img class="" src="<%= FILE_PATH %>/{{>articleId}}/cover_120.jpg">
        </a>
        <div class="media-body">
            <a href="#album/{{>articleId}}" class="media-heading">
                {{>title}} 
            </a>
        </div>
    </li>
{{/for}}
</script>
<%-- 正在播放列表项目模板 --%>
<script id="playingItemTemplate" type="text/x-jquery-tmpl">
{{for playlist}}
<li class="media cover-object">
    <a href="javascript:;" class="play thumbnail">
        <img class="" src="<%= FILE_PATH %>/{{>articleId}}/cover_120.jpg">
    </a>
    <div class="media-body">
        <div class="pull-right hidden-phone">
            <a href="javascript:;" data-index="{{:#index}}" class="remove fm-btn" title="从播放列表移除"><i class="icon icon-circle-remove"></i></a>
        </div>
        <a href="javascript:;" data-index="{{:#index}}" class="play media-heading" title="{{>title}}" >
           {{:#index+1}}. {{>title}} {{if artist }}<small>by {{>artist}}</small>{{/if}}
        </a>
    </div>
</li>
{{/for}}
</script>
<%-- 曲库搜索项目模板 --%>
<script id="libraryItemTemplate" type="text/x-jquery-tmpl">
{{for playlist}}
<li class="media cover-object">
    <a href="javascript:;" class="play thumbnail">
        <img class="" src="<%= FILE_PATH %>/{{>articleId}}/cover_120.jpg">
    </a>
    <div class="media-body">
        <a href="javascript:;" class="play media-heading" title="{{>title}}" >
           {{>title}} {{if artist }}<small>by {{>artist}}</small>{{/if}}
        </a>
    </div>
</li>
{{/for}}
</script>
</body>
</html>