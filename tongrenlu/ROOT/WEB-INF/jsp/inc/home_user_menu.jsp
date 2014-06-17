<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="media text-center ">
    <a href="<c:url value="/user/${userBean.userId}"/>" class="thumbnail">
        <img class="" src="<%= FILE_PATH %>/${userBean.userId}/avatar_400.jpg">
    </a>
    <h3><c:out value="${userBean.nickname}"/></h3>
</div>
<ul class="list-group">
    <c:if test="${userBean.musicCount gt 0}">
    <li class="list-group-item" >
        <span class="badge"><c:out value="${userBean.musicCount}"/></span>
        <a href="<c:url value="/user/${userBean.userId}/music"/>">TA的音乐投稿</a>
    </li>
    </c:if>
    <c:if test="${userBean.comicCount gt 0}">
    <li class="list-group-item" >
        <span class="badge"><c:out value="${userBean.comicCount}"/></span>
        <a href="<c:url value="/user/${userBean.userId}/comic"/>">TA的同人志投稿</a>
    </li>
    </c:if>
    <c:if test="${userBean.followCount gt 0}">
    <li class="list-group-item" >
        <span class="badge"><c:out value="${userBean.followCount}"/></span>
        <a href="<c:url value="/user/${userBean.userId}/follow"/>">TA的关注</a>
    </li>
    </c:if>
    <c:if test="${userBean.fansCount gt 0}">
    <li class="list-group-item" >
        <span class="badge"><c:out value="${userBean.fansCount}"/></span>
        <a href="<c:url value="/user/${userBean.userId}/fans"/>">TA的粉丝</a>
    </li>
    </c:if>
</ul>
<c:if test="${empty login_user || login_user.userId ne userBean.userId}">
    <div class="user-card">
        <c:if test="${!hasFollowed}">
        <a href="<c:url value="/user/${userBean.userId}/follow"/>" class="btn btn-success btn-block btn-large follow">关注TA</a>
        </c:if>
        <c:if test="${hasFollowed}">
        <a href="<c:url value="/user/${userBean.userId}/follow"/>" class="btn btn-danger btn-block btn-large follow">已关注</a>
        </c:if>
    </div>
</c:if>