<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="/WEB-INF/jsp/inc/page_config.jsp" %>
<html>
<head>
<%@ include file="/WEB-INF/jsp/inc/meta.jsp" %>
<%@ include file="/WEB-INF/jsp/inc/home_meta.jsp" %>
<title><fmt:message key="app.name"/> > 标签：<c:out value="${tagBean.tag}"/></title>
</head>
<body>
    <%@ include file="/WEB-INF/jsp/inc/home_header.jsp" %>
<div class="container">
    <div class="page-header ">
        <h1><small>标签：</small><c:out value="${tagBean.tag}"/></h1>
    </div>
    <ul id="spy-scroll" class="nav nav-tabs">
        <c:if test="${!empty musicLastestByTag}">
            <li class=""><a href="#music" >相关音乐</a></li>
        </c:if>
        <c:if test="${!empty comicLastestByTag}">
            <li class=""><a href="#comic" >相关同人志</a></li>
        </c:if>
    </ul>
    <div data-spy="scroll" data-target="#spy-scroll" data-offset="0" class="scrollspy-example">
        <c:if test="${!empty musicLastestByTag}">
            <div id="music" class="page clearfix">
                <div class="page-header ">
                    <a href="<c:url value="/tag/${tagBean.tagId}/music"/>" class="pull-right btn btn-default">更多>></a>
                    <h1>相关音乐</h1>
                </div>
                <div class="row cover-grid">
                    <c:forEach items="${musicLastestByTag}" var="articleBean">
                        <div class="col-sm-6 col-md-2 col-lg-2 cover-object ">
                            <a href="<c:url value="/music/${articleBean.articleId}"/>" class="thumbnail">
                                <img class="" src="<%= FILE_PATH %>/${articleBean.articleId}/cover_180.jpg">
                            </a>
                            <p class="">
                                <a href="<c:url value="/music/${articleBean.articleId}"/>"
                                    title="<c:out value="${articleBean.title}"/>"
                                    class="">
                                    <c:out value="${articleBean.title}"/>
                                </a>
                            </p>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </c:if>
        <c:if test="${!empty comicLastestByTag}">
            <div id="comic" class="page clearfix">
                <div class="page-header ">
                    <a href="<c:url value="/tag/${tagBean.tagId}/comic"/>" class="pull-right btn btn-default">更多>></a>
                    <h1>相关同人志</h1>
                </div>
                <div class="row cover-grid">
                    <c:forEach items="${comicLastestByTag}" var="articleBean">
                        <div class="col-sm-6 col-md-2 col-lg-2 cover-object <c:if test="${articleBean.redFlg eq '1'}">red</c:if>">
                            <a href='<c:url value="/comic/${articleBean.articleId}"/>' class="thumbnail">
                                <img class="" src="<%= FILE_PATH %>/${articleBean.articleId}/cover_180.jpg">
                            </a>
                            <p class= "text-center">
                                <a href="<c:url value="/comic/${articleBean.articleId}"/>"
                                    title="<c:out value="${articleBean.title}"/>"
                                    class="">
                                    <c:out value="${articleBean.title}"/>
                                </a>
                            </p>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </c:if>
    </div>
</div>
<%@ include file="/WEB-INF/jsp/inc/footer.jsp" %>
<%@ include file="/WEB-INF/jsp/inc/scripts.jsp" %>
<%@ include file="/WEB-INF/jsp/inc/home_login.jsp" %>
</body>
</html>