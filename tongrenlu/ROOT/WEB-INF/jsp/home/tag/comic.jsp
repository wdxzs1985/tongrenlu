<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="/WEB-INF/jsp/inc/page_config.jsp" %>
<html>
<head>
<title><fmt:message key="app.name"/> > 标签：<c:out value="${tagBean.tag}"/> > 同人志</title>
<%@ include file="/WEB-INF/jsp/inc/meta.jsp" %>
<%@ include file="/WEB-INF/jsp/inc/home_meta.jsp" %>
<link rel="stylesheet" type="text/css" href="<%= STATIC_PATH %>/jquery.jpaginate/css/style.css" media="screen"/>
</head>
<body>
    <c:set var="NAV_COMIC" value="true"/>
    <%@ include file="/WEB-INF/jsp/inc/home_header.jsp" %>
    <div class="container">
        <div class="row">
            <div class="col-md-8 col-lg-8">
                <div class="page-header">
                    <h4>与<a href="<c:url value="/tag/${tagBean.tagId}"/>"><c:out value="${tagBean.tag}"/></a>相关的同人志</h4>
                </div>
                <div class="media-list cover-list">
                    <c:forEach items="${page.items}" var="articleBean" varStatus="status">
                        <div class="media <c:if test="${articleBean.redFlg eq '1'}">red</c:if>">
                            <a href="<c:url value="/comic/${articleBean.articleId}"/>" 
                                title="<c:out value="${articleBean.title}"/>"
                                class="thumbnail pull-left">
                                <img src="<%= FILE_PATH %>/${articleBean.articleId}/cover_120.jpg"
                                    alt="<c:out value="${articleBean.title}"/>" class="media-object"/>
                            </a>
                            <div class="media-body">
                                <div class="pull-right hidden-sm hidden-xs">
                                    <span class="label label-success">浏览:<c:out value="${articleBean.accessCount}"/></span>
                                    <span class="label label-info">吐槽:<c:out value="${articleBean.commentCount}"/></span> 
                                    <span class="label label-warning">收藏:<c:out value="${articleBean.collectCount}"/></span>
                                </div>
                                <div class="media-heading">
                                    <h5>
                                        <a href="<c:url value="/comic/${articleBean.articleId}"/>"
                                            title="<c:out value="${articleBean.title}"/>">
                                            <c:out value="${articleBean.title}"/>
                                        </a>
                                    </h5>
                                </div>
                                <p class="">
                                    <a href="<c:url value="/user/${articleBean.userBean.userId}"/>">
                                        <c:out value="${articleBean.userBean.nickname}"/>
                                    </a> / 
                                    <span class="text-muted">发布于  <fmt:formatDate value="${articleBean.publishDate}" pattern="yyyy'年'MM'月'dd'日'"/></span>
                                </p>
                                <p class="">
                                    <c:out value="${articleBean.description}"/>
                                </p>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <ul class="pagination visible-xs visible-sm clearfix">
                    <c:forEach begin="1" end="${page.pagenum}" varStatus="status">
                        <li class="<c:if test="${page.page eq status.count}">active</c:if>">
                            <a href="<c:url value="/tag/${tagBean.tagId}/comic?page=${status.count}"/>"><c:out value="${status.count}"/></a>
                        </li>
                    </c:forEach>
                </ul>
                <div id="jpaginate" class="hidden-xs hidden-sm"></div>
            </div>
            <div class="col-md-4 col-lg-4">
                <%@ include file="/WEB-INF/jsp/inc/home_access_ranking_comic.jsp" %>
            </div>
        </div>
    </div>
<%@ include file="/WEB-INF/jsp/inc/footer.jsp" %>
<%@ include file="/WEB-INF/jsp/inc/scripts.jsp" %>
<%@ include file="/WEB-INF/jsp/inc/home_login.jsp" %>
<script type="text/javascript" src="<%= STATIC_PATH %>/jquery.jpaginate/jquery.paginate.js"></script>
<script type="text/javascript">
$(function() {
    var count = $('.pagination li').length;
    $("#jpaginate").paginate({
        count       : count,
        start       : <c:out value="${page.page}"/>,
        display     : 10,
        border                  : true,
        border_color            : '#fff',
        text_color              : '#fff',
        background_color        : '#3498db',  
        border_hover_color      : '#3498db',
        text_hover_color        : '#fff',
        background_hover_color  : '#2980b9', 
        images                  : false,
        onChange                : function (currval){
            window.location.href = '<c:url value="/tag/${tagBean.tagId}/comic"/>?page=' + currval;
            return false;
        }
    });
});
</script>
</body>
</html>