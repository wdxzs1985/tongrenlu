<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="list-group">
    <div class="list-group-item "><strong>我</strong></div>
    <a href="<c:url value="/console/user/setting"/>" class="list-group-item ">个人资料</a>
    <a href="<c:url value="/console/user/password"/>" class="list-group-item ">修改密码</a>
    <a href="<c:url value="/console/user/follow"/>" class="list-group-item ">我的关注</a>
    <a href="<c:url value="/console/user/fans"/>" class="list-group-item ">我的粉丝</a>
</div>
<div class="list-group">
    <div class="list-group-item "><strong>收藏</strong></div>
    <a href="<c:url value="/console/comic/collect"/>" class="list-group-item ">我收藏的同人志</a>
    <a href="<c:url value="/console/music/collect"/>" class="list-group-item ">我收藏的音乐</a>
</div>
<c:if test="${login_user.adminFlg ne '1'}">
<div class="alert alert-info">
投稿请加群联系：197781767
</div>
</c:if>