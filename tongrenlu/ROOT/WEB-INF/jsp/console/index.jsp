<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="/WEB-INF/jsp/inc/page_config.jsp" %>
<html>
<head>
    <title><fmt:message key="app.name"/> / 博丽神社</title>
    <%@ include file="/WEB-INF/jsp/inc/meta.jsp" %>
</head>
<body>
    <%@ include file="/WEB-INF/jsp/inc/console_header.jsp" %>
    <div class="container">
        <div class="row">
            <div class="col-md-3 col-lg-3">
                <%@ include file="/WEB-INF/jsp/inc/console_menu.jsp" %>
                <%@ include file="/WEB-INF/jsp/inc/admin_menu.jsp" %>
            </div>
            <div class="col-md-6 col-lg-6">
                <c:if test="${!empty login_user && (unpublishMusicCount gt 0 || unpublishComicCount gt 0 || unpublishGameCount gt 0)}">
                    <div class="alert alert-block alert-warning">
                        <c:if test="${unpublishMusicCount gt 0}">
                            <p>
                                <span class="glyphicon glyphicon-info-sign"></span>
                                <strong>有<c:out value="${unpublishMusicCount}"/>个音乐投稿等待审核。</strong>
                                <a href="<c:url value="/admin/music"/>">音乐审核</a>
                            </p>
                        </c:if>
                        <c:if test="${unpublishComicCount gt 0}">
                            <p>
                                <span class="glyphicon glyphicon-info-sign"></span>
                                <strong>有<c:out value="${unpublishComicCount}"/>个同人志投稿等待审核。</strong>
                                <a href="<c:url value="/admin/comic"/>">同人志审核</a>
                            </p>
                        </c:if>
                        <c:if test="${unpublishGameCount gt 0}">
                            <p>
                                <span class="glyphicon glyphicon-info-sign"></span>
                                <strong>有<c:out value="${unpublishGameCount}"/>个游戏投稿等待审核。</strong>
                                <a href="<c:url value="/admin/game"/>">游戏审核</a>
                            </p>
                        </c:if>
                    </div>
                </c:if>
                <div id="timeline" class="append-bottom" >
                    <ul class="cbp_tmtimeline" data-empty-text="今天又是平和的一天"></ul>
                    <div class="empty-text alert">今天又是平和的一天</div>
                    <a href="javascript:;"
                        data-href="<c:url value="/console/user/timeline"/>"
                        class="btn btn-block btn-info load-more">加载更多</a>
                </div>
            </div>
            <div class="col-md-3 col-lg-3">
                <%@ include file="/WEB-INF/jsp/inc/console_user_menu.jsp" %>
            </div>
        </div>
    </div>
<%@ include file="/WEB-INF/jsp/inc/footer.jsp" %>
<%@ include file="/WEB-INF/jsp/inc/scripts.jsp" %>
<script type="text/javascript" src="<%= STATIC_PATH %>/plugins/jsrender.js"></script>
<script type="text/javascript" src="<%= STATIC_PATH %>/tongrenlu/js/timeline.js"></script>
<script type="text/javascript">
$(function(){
    new timelineObject('<%= FILE_PATH %>');
});
</script>
<%-- 音乐列表模板 --%>
<script id="publishMusicTemplate" type="text/x-jquery-tmpl">
    <li class="" data-article-id="{{>articleId}}">
        <a href="<c:url value="/music"/>/{{>articleId}}" class="cbp_tmicon" target="_blank">
            <img class="" src="<%= FILE_PATH %>/{{>articleId}}/cover_60.jpg">
        </a>
        <div class="cbp_tmlabel">
            <a href="<c:url value="/user"/>/{{>userBean.userId}}" target="_blank">{{>userBean.nickname}}</a> 发布了音乐：
            <a href="<c:url value="/music"/>/{{>articleId}}" target="_blank">{{>title}}</a>
            <p>{{>content}}</p>
        </div>
    </li>
</script>
<script id="publishComicTemplate" type="text/x-jquery-tmpl">
    <li class="" data-article-id="{{>articleId}}">
        <a href="<c:url value="/comic"/>/{{>articleId}}" class="cbp_tmicon" target="_blank">
            <img class="" src="<%= FILE_PATH %>/{{>articleId}}/cover_60.jpg">
        </a>
        <div class="cbp_tmlabel">
            <a href="<c:url value="/user"/>/{{>userBean.userId}}" target="_blank">{{>userBean.nickname}}</a> 发布了同人志：
            <a href="<c:url value="/comic"/>/{{>articleId}}" target="_blank">{{>title}}</a>
            <p>{{>content}}</p>
        </div>
    </li>
</script>
<script id="publishGameTemplate" type="text/x-jquery-tmpl">
    <li class="" data-article-id="{{>articleId}}">
        <a href="<c:url value="/game"/>/{{>articleId}}" class="cbp_tmicon" target="_blank">
            <img class="" src="<%= FILE_PATH %>/{{>articleId}}/cover_60.jpg">
        </a>
        <div class="cbp_tmlabel">
            <a href="<c:url value="/user"/>/{{>userBean.userId}}" target="_blank">{{>userBean.nickname}}</a> 发布了游戏：
            <a href="<c:url value="/game"/>/{{>articleId}}" target="_blank">{{>title}}</a>
            <p>{{>content}}</p>
        </div>
    </li>
</script>
<script id="commentMusicTemplate" type="text/x-jquery-tmpl">
    <li class="" data-article-id="{{>articleId}}">
        <a href="<c:url value="/user"/>/{{>userBean.userId}}" class="cbp_tmicon" target="_blank">
            <img class="" src="<%= FILE_PATH %>/{{>userBean.userId}}/avatar_60.jpg">
        </a>
        <div class="cbp_tmlabel">
            <a href="<c:url value="/user"/>/{{>userBean.userId}}" target="_blank">{{>userBean.nickname}}</a> 评论了音乐：
            <a href="<c:url value="/music"/>/{{>articleId}}" target="_blank">{{>title}}</a>
            <p>{{>content}}</p>
        </div>
    </li>
</script>
<script id="commentComicTemplate" type="text/x-jquery-tmpl">
    <li class="" data-article-id="{{>articleId}}">
        <a href="<c:url value="/user"/>/{{>userBean.userId}}" class="cbp_tmicon" target="_blank">
            <img class="" src="<%= FILE_PATH %>/{{>userBean.userId}}/avatar_60.jpg">
        </a>
        <div class="cbp_tmlabel">
            <a href="<c:url value="/user"/>/{{>userBean.userId}}" target="_blank">{{>userBean.nickname}}</a> 评论了同人志：
            <a href="<c:url value="/comic"/>/{{>articleId}}" target="_blank">{{>title}}</a>
            <p>{{>content}}</p>
        </div>
    </li>
</script>
<script id="commentGameTemplate" type="text/x-jquery-tmpl">
    <li class="" data-article-id="{{>articleId}}" target="_blank">
        <a href="<c:url value="/user"/>/{{>userBean.userId}}" class="cbp_tmicon">
            <img class="" src="<%= FILE_PATH %>/{{>userBean.userId}}/avatar_60.jpg">
        </a>
        <div class="cbp_tmlabel">
            <a href="<c:url value="/user"/>/{{>userBean.userId}}" target="_blank">{{>userBean.nickname}}</a> 评论了游戏：
            <a href="<c:url value="/game"/>/{{>articleId}}" target="_blank">{{>title}}</a>
            <p>{{>content}}</p>
        </div>
    </li>
</script>
<script id="commentUserTemplate" type="text/x-jquery-tmpl">
    <li class="" data-article-id="{{>articleId}}" target="_blank">
        <a href="<c:url value="/user"/>/{{>userBean.userId}}" class="cbp_tmicon">
            <img class="" src="<%= FILE_PATH %>/{{>userBean.userId}}/avatar_60.jpg">
        </a>
        <div class="cbp_tmlabel">
            <a href="<c:url value="/user"/>/{{>userBean.userId}}" target="_blank">{{>userBean.nickname}}</a> 给你留言：
            <a href="<c:url value="/user"/>/{{>articleId}}" target="_blank">点击查看</a>
            <p>{{>content}}</p>
        </div>
    </li>
</script>
</body>
</html>