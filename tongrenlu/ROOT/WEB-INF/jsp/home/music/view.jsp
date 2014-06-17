<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="/WEB-INF/jsp/inc/page_config.jsp" %>
<html xmlns:wb="http://open.weibo.com/wb">
<head>
<%@ include file="/WEB-INF/jsp/inc/meta.jsp" %>
<%@ include file="/WEB-INF/jsp/inc/home_meta.jsp" %>
<link href="<%= STATIC_PATH %>/jplayer/skin/morning.light/jplayer.morning.light.css" rel="stylesheet" type="text/css" />
<title><c:out value="${articleBean.title}"/></title>
<meta name="keywords" content="<fmt:message key="app.name"/><c:forEach items="${tagBeanList}" var="tagBean">,<c:out value="${tagBean.tag}"></c:out></c:forEach>">
<script src="http://tjs.sjs.sinajs.cn/open/api/js/wb.js" type="text/javascript" charset="utf-8"></script>
</head>
<body class="fluid">
<c:set var="NAV_MUSIC" value="true"/>
<%@ include file="/WEB-INF/jsp/inc/home_header.jsp" %>
<div class="container">
    <div class="page-header">
        <h1><c:out value="${articleBean.title}"/></h1>
        <%@ include file="/WEB-INF/jsp/inc/home_collect.jsp" %>
    </div>
    <div id="viewer" class="append-bottom">
        <div class="viewport">
            <div class="stage text-center">
                <a class="left carousel-control" href="#myCarousel" data-slide="prev" style="z-index:999;">‹</a>
                <a class="right carousel-control" href="#myCarousel" data-slide="next" style="z-index:999;">›</a>
            </div>
            <div class="remote">
               <p  class="leader text-center">- 1/ 100 -</p>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-6 " >
            <div class=" append-bottom">
                <%@ include file="/WEB-INF/jsp/inc/home_track_table.jsp" %>
            </div>
        </div>
        <div class="col-md-6 " >
            <%@ include file="/WEB-INF/jsp/inc/home_description.jsp" %>
            <%@ include file="/WEB-INF/jsp/inc/home_taglist.jsp" %>
            <div class="append-bottom">
                <wb:publish action="pubilish" type="web" language="zh_cn" button_type="red" 
                    button_size="middle" button_text="我要点32个赞！" 
                    default_text="#同人音乐# <c:out value="${articleBean.title}"/> / UP主：<c:out value="${articleBean.userBean.nickname}"/> / 来自#东方同人录# <%= FULL_PATH %><c:url value="/music/${articleBean.articleId}"/>" 
                    tag="点32个赞！" 
                    default_image="<%= FILE_PATH %>/${articleBean.articleId}/cover_400.jpg" 
                    appkey="2ZiUc5" ></wb:publish>
                <wb:publish action="pubilish" type="web" language="zh_cn" button_type="red" 
                    button_size="middle" button_text="我要求资源！" uid="2803325782" 
                    default_text="#同人音乐# <c:out value="${articleBean.title}"/> / UP主：<c:out value="${articleBean.userBean.nickname}"/> / 来自#东方同人录# <%= FULL_PATH %><c:url value="/music/${articleBean.articleId}"/>" 
                    tag="求资源！" 
                    default_image="<%= FILE_PATH %>/${articleBean.articleId}/cover_400.jpg" 
                    appkey="2ZiUc5" ></wb:publish>
            </div>
            <%@ include file="/WEB-INF/jsp/inc/home_comments.jsp" %>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/jsp/inc/footer.jsp" %>
<%@ include file="/WEB-INF/jsp/inc/scripts.jsp" %>
<%@ include file="/WEB-INF/jsp/inc/home_login.jsp" %>
<script type="text/javascript" src="<%= STATIC_PATH %>/jplayer/js/jquery.jplayer.min.js"></script>
<script type="text/javascript" src="<%= STATIC_PATH %>/jplayer/js/jplayer.playlist.min.js"></script>
<script type="text/javascript" src="<%= STATIC_PATH %>/tongrenlu/js/url.js"></script>
<script type="text/javascript" src="<%= STATIC_PATH %>/tongrenlu/js/collect.js"></script>
<script type="text/javascript" src="<%= STATIC_PATH %>/tongrenlu/js/follow.js"></script>
<script type="text/javascript" src="<%= STATIC_PATH %>/tongrenlu/js/comment.js"></script>
<script type="text/javascript" src="<%= STATIC_PATH %>/tongrenlu/js/comic_20130807.js"></script>
<script type="text/javascript" src="<%= STATIC_PATH %>/tongrenlu/js/music.js"></script>
<script type="text/javascript">
$(function(){
    $.getJSON('<c:url value="/music/${articleBean.articleId}/playlist"/>', function(response){
	    new player(response);
    });
    //init window
    var windowWidth = window.innerWidth || $(window).width();
    var $body = $("body");
    var isHD = false;
    var stageSize = {
        height: "400px",
        "line-height": "400px"
    }
    if(windowWidth > 1600) {
        isHD = true;
        stageSize = $.extend(stageSize, {
            height: "800px",
            "line-height": "800px"
        });
    } else if(windowWidth > 1200) {
        isHD = true;
        stageSize = $.extend(stageSize, {
            height: "600px",
            "line-height": "600px"
        });
    }
    $.getJSON('<c:url value="/music/${articleBean.articleId}/booklet"/>', function(response){
        new reader(response, isHD, stageSize);
    });
    new commentObject('<%= FILE_PATH %>');
});
</script>
<div class="modal fade" id="playlist-modal" tabindex="-1" role="dialog" aria-labelledby="playlist-modal-label" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h4 class="modal-title" id="playlist-modal-label">加入到</h4>
      </div>
        <div class="modal-body">
            <ul id="playlist-name-list" class="media-list clearfix">
            </ul>
            <form id="new-playlist-form" class="" method="post">
                <div class="input-group">
			      <input type="text" class="form-control" id="new-playlist-title" placeholder="新建播放列表" value="新建播放列表">
                  <span class="input-group-btn">
                    <button class="btn btn-primary" id="new-playlist-form-submit" type="button"><span class="glyphicon glyphicon-star"></span></button>
                  </span>
			    </div><!-- /input-group -->
            </form>
        </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<script type="text/x-tmpl" id="playlistDialogTemplate">
{% for (var i=0, articleBean; articleBean=o.items[i]; i++) { %}
<li class="media">
    <div class="media-body">
        <a href="javascript:;" data-article-id='{%=articleBean.articleId%}'>{%=articleBean.title%}</a>
    </div>
</li>
{% } %}
</script>
</body>
</html>