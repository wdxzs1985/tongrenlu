<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="tag-area append-bottom">
    <span class="badge badge-info">标签：</span>
    <c:forEach items="${tagBeanList}" var="tagBean">
        <a href="<c:url value="/tag/${tagBean.tagId}"/>" class="btn btn-mini btn-link" target="_blank">
            <c:out value="${tagBean.tag}"></c:out> (<c:out value="${tagBean.articleCount}"></c:out>)
        </a>
    </c:forEach>
</div>