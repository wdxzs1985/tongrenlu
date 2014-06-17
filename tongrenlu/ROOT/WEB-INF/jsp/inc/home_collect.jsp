<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${articleBean.collectFlg eq '0'}">
    <a data-href="<c:url value="/article/${articleBean.articleId}/collect" />" 
        class="collect btn btn-lg btn-danger" rel="nofollow">
        <i class="glyphicon glyphicon-heart"></i>
        <span>收藏WO！</span>
    </a>
</c:if>
<c:if test="${articleBean.collectFlg ne '0'}">
    <a data-href="<c:url value="/article/${articleBean.articleId}/collect" />" 
        class="collect collected btn btn-lg btn-success" rel="nofollow">
        <i class="glyphicon glyphicon-heart"></i>
        <span>收藏DAZE！</span>
    </a>
</c:if>
