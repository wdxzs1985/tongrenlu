<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="media">
    <a href="<c:url value="/console/user/setting"/>" class="thumbnail">
        <img class="" src="<%= FILE_PATH %>/${login_user.userId}/avatar_400.jpg">
    </a>
</div>
<ul class="media list-group">
    <c:if test="${login_user.musicCount gt 0}">
    <li class="list-group-item" >
        <span class="badge"><c:out value="${login_user.musicCount}"/></span>
        <a href="<c:url value="/console/music"/>">我投稿的音乐</a>
    </li>
    </c:if>
    <c:if test="${login_user.comicCount gt 0}">
    <li class="list-group-item" >
        <span class="badge"><c:out value="${login_user.comicCount}"/></span>
        <a href="<c:url value="/console/comic"/>">我投稿的同人志</a>
    </li>
    </c:if>
    <c:if test="${login_user.gameCount gt 0}">
    <li class="list-group-item" >
        <span class="badge"><c:out value="${login_user.gameCount}"/></span>
        <a href="<c:url value="/console/game"/>">我投稿的游戏</a>
    </li>
    </c:if>
    <c:if test="${login_user.followCount gt 0}">
    <li class="list-group-item" >
        <span class="badge"><c:out value="${login_user.followCount}"/></span>
        <a href="<c:url value="/console/user/follow"/>">我的关注</a>
    </li>
    </c:if>
    <c:if test="${login_user.fansCount gt 0}">
    <li class="list-group-item" >
        <span class="badge"><c:out value="${login_user.fansCount}"/></span>
        <a href="<c:url value="/console/user/fans"/>">我的粉丝</a>
    </li>
    </c:if>
</ul>