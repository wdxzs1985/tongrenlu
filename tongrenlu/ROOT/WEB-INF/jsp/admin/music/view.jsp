<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="/WEB-INF/jsp/inc/page_config.jsp" %>
<html>
<head>
<title>博丽神社 / 音乐审核 / <c:out value="${articleBean.title}" default=""/></title>
<%@ include file="/WEB-INF/jsp/inc/meta.jsp" %>
<link href="<%= STATIC_PATH %>/jplayer/skin/morning.light/jplayer.morning.light.css" rel="stylesheet" type="text/css" />
</head>
<body class="fluid">
<%@ include file="/WEB-INF/jsp/inc/console_header.jsp" %>
<div class="container">
    <ul class="breadcrumb">
        <li>我现在的位置</li>
        <li><a href="<c:url value="/console"/>">博丽神社</a></li>
        <li><a href="<c:url value="/admin/music"/>">音乐审核</a></li>
        <li class="active"><c:out value="${articleBean.title}" default=""/></li>
    </ul>
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
    
    <div class="row ">
        <div class="col-md-6 append-bottom">
            <%@ include file="/WEB-INF/jsp/inc/home_track_table.jsp" %>
        </div>
        <div class="col-md-6 append-bottom" >
            <%@ include file="/WEB-INF/jsp/inc/admin_description.jsp" %>
            <%@ include file="/WEB-INF/jsp/inc/home_taglist.jsp" %>
            <div class="row">
                <div class="col-md-3 append-bottom" >
                    <a href="<c:url value="/admin/music/${articleBean.articleId}/booklet/upload"/>" class="btn btn-danger btn-lg btn-block">上传BK</a>
                </div>
                <div class="col-md-3 append-bottom" >
                    <a href="<c:url value="/admin/music/${articleBean.articleId}/track/upload"/>" class="btn btn-danger btn-lg btn-block">上传MP3</a>
                </div>
                <form action="<c:url value="/admin/music/${articleBean.articleId}/publish"/>" method="POST" >
                    <div class="col-md-3 append-bottom" >
                        <button type="submit" class="btn btn-primary btn-lg btn-block">发布</button>
                    </div>
                    <div class="col-md-3 append-bottom" >
                        <a href="<c:url value="/admin/music"/>" class="btn btn-default btn-lg btn-block">返回</a>
                    </div>
                </form>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/jsp/inc/footer.jsp" %>
<%@ include file="/WEB-INF/jsp/inc/scripts.jsp" %>
<script type="text/javascript" src="<%= STATIC_PATH %>/jplayer/js/jquery.jplayer.min.js"></script>
<script type="text/javascript" src="<%= STATIC_PATH %>/jplayer/js/jplayer.playlist.min.js"></script>
<script type="text/javascript" src="<%= STATIC_PATH %>/tongrenlu/js/url.js"></script>
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
        $body.addClass('media_1600');
        stageSize = $.extend(stageSize, {
            height: "800px",
            "line-height": "800px"
        });
    } else if(windowWidth > 1200) {
        isHD = true;
        $body.addClass('media_1200');
        stageSize = $.extend(stageSize, {
            height: "600px",
            "line-height": "600px"
        });
    }
    $.getJSON('<c:url value="/music/${articleBean.articleId}/booklet"/>', function(response){
        new reader(response, isHD, stageSize);
    });
});
</script>
</body>
</html>