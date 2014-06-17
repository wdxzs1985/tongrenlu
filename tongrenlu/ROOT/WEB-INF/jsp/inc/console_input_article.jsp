<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="form-group <c:if test="${!empty title_error}">has-error</c:if>" data-step="2" data-intro="在这里输入标题，不能超过100个字">
    <h4>标题    <small>在这里输入标题，不能超过100个字</small></h4>
    <div class="">
        <input type="text" id="title" name="title" class="form-control" value="<c:out value="${articleBean.title}" default=""/>">
        <span class="help-block"><c:out value="${title_error}" default=""/></span>
    </div>
</div>
<div class="form-group <c:if test="${!empty description_error}">has-error</c:if>" data-step="3" data-intro="在这里输入介绍，不能超过2000个字">
    <h4>介绍    <small>在这里输入介绍或者介绍，不能超过2000个字</small></h4>
    <div class="">
        <textarea id="description" name="description" class="form-control" rows="10"><c:out value="${articleBean.description}" default=""/></textarea>
        <span class="help-block"><c:out value="${description_error}" default=""/></span>
    </div>
</div>