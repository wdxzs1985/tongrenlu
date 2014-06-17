<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="">
<div class="page-header"><h1>同人志更新</h1></div>
<ul class="media-list cover-list-small">
    <c:forEach items="${lastestComic}" var="comic" varStatus="status">
        <c:if test="${status.index lt 3}">
            <li class="media <c:if test="${comic.redFlg eq '1'}">red</c:if>">
                <a href="<c:url value="/comic/${comic.articleId}"/>" class="pull-left cover">
                    <span class="center-img">
                        <img class="" src="<%= FILE_PATH %>/${comic.articleId}/cover_60.jpg">
                    </span>
                </a>
                <div class="media-body">
                    <div class="media-heading">
                        <a class="" href="<c:url value="/comic/${comic.articleId}"/>"><c:out value="${comic.title}"/></a>
                    </div>
                    <p class="muted"><c:out value="${comic.description}"/></p>
                </div>
            </li>
        </c:if>
        <c:if test="${status.index gt 2}">
            <li class="<c:if test="${comic.redFlg eq '1'}">red</c:if>">
                <a class="" href="<c:url value="/comic/${comic.articleId}"/>"><c:out value="${comic.title}"/></a>
            </li>
        </c:if>
    </c:forEach>
</ul>
</div>