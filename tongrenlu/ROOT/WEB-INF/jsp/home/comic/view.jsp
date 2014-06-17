<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="/WEB-INF/jsp/inc/page_config.jsp" %>
<html xmlns:wb="http://open.weibo.com/wb">
<head>
<%@ include file="/WEB-INF/jsp/inc/meta.jsp" %>
<%@ include file="/WEB-INF/jsp/inc/home_meta.jsp" %>
<link rel="stylesheet" type="text/css" href="<%= STATIC_PATH %>/jquery.jpaginate/css/style.css" media="screen"/>
<title><c:out value="${articleBean.title}"/> - <fmt:message key="app.name"/></title>
<meta name="keywords" content="<fmt:message key="app.name"/><c:forEach items="${tagBeanList}" var="tagBean">,<c:out value="${tagBean.tag}"></c:out></c:forEach>">
<script src="http://tjs.sjs.sinajs.cn/open/api/js/wb.js" type="text/javascript" charset="utf-8"></script>
<style>
.remote {max-width: 620px; margin: 0 auto;}
</style>
</head>
<body class="fluid">
<c:set var="NAV_COMIC" value="true"/>
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
            <div class="remote"></div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-6 col-lg-6" >
            <%@ include file="/WEB-INF/jsp/inc/home_description.jsp" %>
            <%@ include file="/WEB-INF/jsp/inc/home_taglist.jsp" %>
            <div class="append-bottom">
                <wb:publish action="pubilish" type="web" language="zh_cn" button_type="red" 
                    button_size="middle" button_text="我要点32个赞！" 
                    default_text="#同人志# <c:out value="${articleBean.title}"/> / UP主：<c:out value="${articleBean.userBean.nickname}"/> / 来自#东方同人录# <%= FULL_PATH %><c:url value="/comic/${articleBean.articleId}"/>" 
                    tag="点32个赞！" 
                    default_image="<%= FILE_PATH %>/${articleBean.articleId}/cover_400.jpg" 
                    appkey="2ZiUc5" ></wb:publish>
                <wb:publish action="pubilish" type="web" language="zh_cn" button_type="red" 
                    button_size="middle" button_text="我要求资源！" uid="2803325782" 
                    default_text="#同人志# <c:out value="${articleBean.title}"/> / UP主：<c:out value="${articleBean.userBean.nickname}"/> / 来自#东方同人录# <%= FULL_PATH %><c:url value="/comic/${articleBean.articleId}"/>" 
                    tag="求资源！" 
                    default_image="<%= FILE_PATH %>/${articleBean.articleId}/cover_400.jpg" 
                    appkey="2ZiUc5" ></wb:publish>
            </div>
        </div>
        <div class="col-md-6 col-lg-6" >
            <%@ include file="/WEB-INF/jsp/inc/home_comments.jsp" %>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/jsp/inc/footer.jsp" %>
<%@ include file="/WEB-INF/jsp/inc/scripts.jsp" %>
<%@ include file="/WEB-INF/jsp/inc/home_login.jsp" %>
<script type="text/javascript" src="<%= STATIC_PATH %>/jquery.jpaginate/jquery.paginate.js"></script>
<script type="text/javascript" src="<%= STATIC_PATH %>/plugins/jquery.lazyload.min.js"></script>
<script type="text/javascript" src="<%= STATIC_PATH %>/plugins/jsrender.js"></script>
<script type="text/javascript" src="<%= STATIC_PATH %>/tongrenlu/js/url.js"></script>
<script type="text/javascript" src="<%= STATIC_PATH %>/tongrenlu/js/collect.js"></script>
<script type="text/javascript" src="<%= STATIC_PATH %>/tongrenlu/js/follow.js"></script>
<script type="text/javascript" src="<%= STATIC_PATH %>/tongrenlu/js/comment.js"></script>
<script type="text/javascript" src="<%= STATIC_PATH %>/tongrenlu/js/comic_20130929.js"></script>
<script type="text/javascript">
$(function(){
    //init window
    var windowWidth = window.innerWidth || $(window).width();
    var windowHeight = window.innerHeight || $(window).height();
    var $body = $("body");
    var isHD = windowWidth > 1200;
    var stageSize = {
        height: windowHeight + "px",
        "line-height": windowHeight + "px"
    }
    $.getJSON('<c:url value="/music/${articleBean.articleId}/booklet"/>', function(response){
        new reader(response, isHD, stageSize);
    });
    new commentObject('<%= FILE_PATH %>');
});
</script>
</body>
</html>