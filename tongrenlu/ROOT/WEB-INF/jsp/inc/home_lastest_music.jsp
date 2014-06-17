<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="">
    <div class="page-header"><h1>音乐更新</h1></div>
    <ul class="media-list cover-list-small">
        <c:forEach items="${lastestMusic}" var="articleBean" varStatus="status">
            <c:if test="${status.index lt 3}">
                <li class="media">
                    <a href="<c:url value="/music/${articleBean.articleId}"/>" class="pull-left cover">
                        <span class="center-img">
                            <img class="" src="<%= FILE_PATH %>/${articleBean.articleId}/cover_60.jpg">
                        </span>
                    </a>
                    <div class="media-body">
                        <div class="media-heading">
                            <a class="" href="<c:url value="/music/${articleBean.articleId}"/>"><c:out value="${articleBean.title}"/></a>
                        </div>
                        <p class="muted"><c:out value="${articleBean.description}"/></p>
                    </div>
                </li>
            </c:if>
            <c:if test="${status.index gt 2}">
                <li class="">
                    <a class="" href="<c:url value="/music/${articleBean.articleId}"/>"><c:out value="${articleBean.title}"/></a>
                </li>
            </c:if>
        </c:forEach>
    </ul>
</div>