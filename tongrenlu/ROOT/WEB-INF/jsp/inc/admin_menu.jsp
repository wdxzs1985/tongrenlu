<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${login_user.adminFlg eq '1'}">
<div class="list-group">
    <div class="list-group-item "><strong>投稿</strong></div>
        <a href="<c:url value="/console/comic/input"/>" class="list-group-item ">同人志投稿</a>
        <a href="<c:url value="/console/comic"/>" class="list-group-item ">我投稿的同人志</a>
        <a href="<c:url value="/console/music/input"/>" class="list-group-item ">音乐投稿</a>
        <a href="<c:url value="/console/music"/>" class="list-group-item ">我投稿的音乐</a>
</div>
<div class="list-group">
    <div class="list-group-item "><strong>审核</strong></div>
        <a href="<c:url value="/admin/comic"/>" class="list-group-item ">同人志审核</a>
        <a href="<c:url value="/admin/music"/>" class="list-group-item ">音乐审核</a>
</div>
</c:if>