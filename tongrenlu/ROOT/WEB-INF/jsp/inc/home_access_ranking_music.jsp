<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="">
    <div class=""><h3>音乐浏览排<span style="color:#cc0000;">行</span></h3></div>
    <ul class="media-list">
        <c:forEach items="${access10Music}" var="articleBean" varStatus="status">
            <c:if test="${status.index lt 5}">
                <li class="media">
                    <a href="<c:url value="/music/${articleBean.articleId}"/>" 
                        title="<c:out value="${articleBean.title}"/>"
                        class="thumbnail pull-left">
                            <img src="<%= FILE_PATH %>/${articleBean.articleId}/cover_60.jpg"
                                alt="<c:out value="${articleBean.title}"/>">
                    </a>
                    <div class="media-body">
                        <a href="<c:url value="/music/${articleBean.articleId}"/>"
                            title="<c:out value="${articleBean.title}"/>"
                            class="media-heading">
                            <c:out value="${articleBean.title}"/>
                        </a>
                        <p class="text-muted hidden-sm"><c:out value="${articleBean.description}"/></p>
                    </div>
                </li>
            </c:if>
            <c:if test="${status.index gt 4}">
                <li class="media">
                    <a href="<c:url value="/music/${articleBean.articleId}"/>"
                        title="<c:out value="${articleBean.title}"/>"
                        class="media-heading">
                        <c:out value="${articleBean.title}"/>
                    </a>
                </li>
            </c:if>
        </c:forEach>
    </ul>
</div>