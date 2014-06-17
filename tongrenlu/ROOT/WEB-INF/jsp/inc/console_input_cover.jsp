<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="form-group <c:if test="${!empty cover_error}">has-error</c:if>"
        data-step="1" data-intro="上传封面，支持JPG和PNG格式的图片，推荐尺寸400X400以上">
    <div class=" thumbnail append-bottom">
        <c:if test="${empty articleBean.articleId}">
            <img class="" src="<%= FILE_PATH %>/default/default_400.jpg"/>
        </c:if>
        <c:if test="${!empty articleBean.articleId}">
            <img class="" src="<%= FILE_PATH %>/${articleBean.articleId}/cover_400.jpg?cache=<%= CURRENT_TIME %>">
        </c:if>
    </div>
    <div class="">
        <label class="form-label">上传封面</label>
        <input type="file" id="cover" name="cover" class="form-control" accept="image/jpeg,image/png">
        <span class="help-block">支持JPG和PNG格式的图片，推荐尺寸400X400以上</span>
        <span class="help-block"><c:out value="${cover_error}" default=""/></span>
    </div>
</div>