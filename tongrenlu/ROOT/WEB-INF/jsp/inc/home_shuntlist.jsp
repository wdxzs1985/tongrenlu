<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${!empty shuntBeanList}">
<div class="media append-bottom">
    <div class="media-body">
        <h4 class="media-heading">分流链接：</h4>
        <ul class="media-list">
            <c:forEach items="${shuntBeanList}" var="shuntBean">
                <li><a href="<c:out value="${shuntBean.link}" />"><c:out value="${shuntBean.link}"/></a></li>
            </c:forEach>
        </ul>
    </div>
</div>
</c:if>