<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="media append-bottom user-card">
    <div class="pull-left">
        <a href="<c:url value="/user/${articleBean.userBean.userId}"/>" class="media-object" style="margin-bottom:5px;">
            <img class="" src="<%= FILE_PATH %>/${articleBean.userBean.userId}/avatar_90.jpg" style="">
        </a>
        <c:if test="${!empty login_user && login_user.userId eq articleBean.userBean.userId}">
            <button class="btn btn-default btn-block disabled">自己</button>
        </c:if>
        <c:if test="${empty login_user || login_user.userId ne articleBean.userBean.userId}">
            <c:if test="${!hasFollowed}">
            <a href="<c:url value="/user/${articleBean.userBean.userId}/follow"/>" class="btn btn-success btn-mini btn-block follow">关注TA</a>
            </c:if>
            <c:if test="${hasFollowed}">
            <a href="<c:url value="/user/${articleBean.userBean.userId}/follow"/>" class="btn btn-danger btn-mini btn-block follow">已关注</a>
            </c:if>
        </c:if>
    </div>
    <div class="media-body">
        <h5 class="media-heading">
            <small>UP主：</small>
            <a href="<c:url value="/user/${articleBean.userBean.userId}"/>" class="">
                <span style="color:#cc4444;"><c:out value="${articleBean.userBean.nickname}"/></span>
            </a>
            / <small>发布于  <fmt:formatDate  value="${articleBean.publishDate}" pattern="yyyy'年'MM'月'dd'日'"/></small>
            / <small class="text-muted">浏览:<c:out value="${articleBean.accessCount}"/></small>
            / <small class="text-muted">吐槽:<c:out value="${articleBean.commentCount}"/></small>
            / <small class="text-muted">收藏:<c:out value="${articleBean.collectCount}"/></small>
        </h5>
        <pre id="description"><c:out value="${articleBean.description}" default="UP主很懒，神马都没有写"/></pre>
    </div>
</div>