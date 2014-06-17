<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="/WEB-INF/jsp/inc/page_config.jsp" %>
<html>
<head>
    <title>博丽神社 / 同人志审核 / <c:out value="${articleBean.title}" default=""/></title>
    <%@ include file="/WEB-INF/jsp/inc/meta.jsp" %>
</head>
<body class="fluid">
<%@ include file="/WEB-INF/jsp/inc/console_header.jsp" %>
<div class="container">
    <ul class="breadcrumb">
        <li>我现在的位置</li>
        <li><a href="<c:url value="/console"/>">博丽神社</a></li>
        <li><a href="<c:url value="/admin/comic"/>">同人志审核</a></li>
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

    <div class="row">
        <div class="col-md-6">
            <%@ include file="/WEB-INF/jsp/inc/admin_description.jsp" %>
            <%@ include file="/WEB-INF/jsp/inc/home_taglist.jsp" %>
        </div>
        <div class="col-md-6">
            <div class="row">
                <form action="<c:url value="/admin/comic/${articleBean.articleId}/publish"/>" method="POST" >
                    <div class="col-md-6 append-bottom" >
                        <a href="<c:url value="/admin/comic/${articleBean.articleId}/upload"/>" class="btn btn-danger btn-lg btn-block">上传漫画</a>
                    </div>
                    <div class="col-md-3 append-bottom" >
                        <button type="submit" class="btn btn-primary btn-lg btn-block">发布</button>
                    </div>
                    <div class="col-md-3 append-bottom" >
                        <a href="<c:url value="/admin/comic"/>" class="btn btn-default btn-lg btn-block">返回</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/jsp/inc/footer.jsp" %>
<%@ include file="/WEB-INF/jsp/inc/scripts.jsp" %>
<script type="text/javascript" src="<%= STATIC_PATH %>/tongrenlu/js/url.js"></script>
<script type="text/javascript" src="<%= STATIC_PATH %>/tongrenlu/js/comic_20130807.js"></script>
<script type="text/javascript">
$(function(){
    //init window
    var windowWidth = window.innerWidth || $(window).width();
    var $body = $("body");
    var isHD = false;
    var stageSize = {
        height: "800px",
        "line-height": "800px"
    }
    if(windowWidth > 1600) {
        isHD = true;
        stageSize = $.extend(stageSize, {
            height: "1600px",
            "line-height": "1600px"
        });
    } else if(windowWidth > 1200) {
        isHD = true;
        stageSize = $.extend(stageSize, {
            height: "1200px",
            "line-height": "1200px"
        });
    }
    $.getJSON('<c:url value="/music/${articleBean.articleId}/booklet"/>', function(response){
        new reader(response, isHD, stageSize);
    });
});
</script>
</body>
</html>