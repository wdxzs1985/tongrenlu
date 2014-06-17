<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="media append-bottom user-card">
    <div class="pull-left">
        <a href="<c:url value="/user/${articleBean.userBean.userId}"/>" class="media-object" style="margin-bottom:5px;">
            <img class="" src="<%= FILE_PATH %>/${articleBean.userBean.userId}/avatar_90.jpg" style="">
        </a>
    </div>
    <div class="media-body">
        <h5 class="media-heading">
            <small>UP主：</small>
            <a href="<c:url value="/user/${articleBean.userBean.userId}"/>" class="">
                <span style="color:#cc4444;"><c:out value="${articleBean.userBean.nickname}"/></span>
            </a>
            <c:if test="${!empty articleBean.publishDate}">
                <small>发布于  <fmt:formatDate  value="${articleBean.publishDate}" pattern="yyyy'年'MM'月'dd'日'"/></small>
            </c:if>
        </h5>
        <pre id="description"><c:out value="${articleBean.description}" default="UP主很懒，神马都没有写"/></pre>
    </div>
</div>